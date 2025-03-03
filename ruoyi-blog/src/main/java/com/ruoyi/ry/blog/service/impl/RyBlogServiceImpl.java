package com.ruoyi.ry.blog.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.ry.blog.domain.RyBlogTag;
import com.ruoyi.ry.blog.domain.RyBlogType;
import com.ruoyi.ry.blog.mapper.RyBlogTagMapper;
import com.ruoyi.ry.blog.mapper.RyBlogTypeMapper;
import com.ruoyi.ry.tag.domain.RyTag;
import com.ruoyi.ry.tag.mapper.RyTagMapper;
import com.ruoyi.ry.type.domain.RyType;
import com.ruoyi.ry.type.mapper.RyTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ry.blog.mapper.RyBlogMapper;
import com.ruoyi.ry.blog.domain.RyBlog;
import com.ruoyi.ry.blog.service.IRyBlogService;

/**
 * 博客信息Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-26
 */
@Service
public class RyBlogServiceImpl implements IRyBlogService
{
    @Autowired
    private RyBlogMapper ryBlogMapper;
    @Autowired
    private RyTagMapper ryTagMapper;
    @Autowired
    private RyTypeMapper ryTypeMapper;
    @Autowired
    private RyBlogTagMapper ryBlogTagMapper;
    @Autowired
    private RyBlogTypeMapper ryBlogTypeMapper;

    /**
     * 查询博客信息
     *
     * @param id 博客信息主键
     * @return 博客信息
     */
    @Override
    public RyBlog selectRyBlogById(Long id)
    {
        RyBlog ryBlog = ryBlogMapper.selectRyBlogById(id);
        List<RyBlogTag> ryBlogTags = ryBlogTagMapper.selectRyBlogTagList(id);

        List<RyTag> tags = new ArrayList<>();
        List<RyType> types = new ArrayList<>();
        Long[] tagIds = new Long[ryBlogTags.size()];

        //标签
        for (int i = 0; i < ryBlogTags.size(); i++) {
            RyBlogTag ryBlogTag = ryBlogTags.get(i);
            Long tagId = ryBlogTag.getTagId();
            tagIds[i] = tagId;
            RyTag ryTag = ryTagMapper.selectRyTagByTagId(tagId);
            tags.add(ryTag);
        }
        ryBlog.setTags(tags);
        ryBlog.setTagIds(tagIds);

        //分类
        List<RyBlogType> ryBlogTypes = ryBlogTypeMapper.selectRyBlogTypeList(id);
        Long[] typeIds = new Long[ryBlogTypes.size()];
        for (int i = 0; i < ryBlogTypes.size(); i++) {
            RyBlogType ryBlogType = ryBlogTypes.get(i);
            Long typeId = ryBlogType.getTypeId();
            typeIds[i] = typeId;
            RyType ryType = ryTypeMapper.selectRyTypeByTypeId(typeId);
            types.add(ryType);
        }
        ryBlog.setTypes(types);
        ryBlog.setTypeIds(typeIds);
        return ryBlog;
    }

    /**
     * 查询博客信息列表
     *
     * @param ryBlog 博客信息
     * @return 博客信息
     */
    @Override
    public List<RyBlog> selectRyBlogList(RyBlog ryBlog)
    {
        List<RyBlog> ryBlogList = ryBlogMapper.selectRyBlogList(ryBlog);
        List<RyBlog> blogList = BlogListAddTypeAndTag(ryBlogList);
        return blogList;
    }

    /**
     * 新增博客信息
     *
     * @param ryBlog 博客信息
     * @return 结果
     */
    @Override
    public Long insertRyBlog(RyBlog ryBlog)
    {
        ryBlog.setCreateTime(DateUtils.getNowDate());
        return ryBlogMapper.insertRyBlog(ryBlog);
    }

    /**
     * 修改博客信息
     *
     * @param ryBlog 博客信息
     * @return 结果
     */
    @Override
    public int updateRyBlog(RyBlog ryBlog)
    {
        //修改
        ryBlog.setUpdateTime(DateUtils.getNowDate());
        Long blogId = ryBlog.getId();
        //1.修改关联
        //清空原本的标签
        ryBlogTagMapper.deleteRyBlogTagByBlogId(blogId);
        //清空原本的类型
        ryBlogTypeMapper.deleteRyBlogTypeByBlogId(blogId);

        //添加标签关联
        Long[] tagIds = ryBlog.getTagIds();
        if(tagIds != null && tagIds.length > 0){
            List<RyBlogTag> ryTagList = new ArrayList<>();
            for (Long tagId : tagIds) {
                RyBlogTag ryBlogTag = new RyBlogTag();
                ryBlogTag.setBlogId(blogId);
                ryBlogTag.setTagId(tagId);
                ryTagList.add(ryBlogTag);
            }
            //添加标签到数据库
            //批量插入
            ryBlogTagMapper.batchInsertRyBlogTag(ryTagList);
        }

        //添加分类关联
        Long[] typeIds = ryBlog.getTypeIds();
        if(typeIds != null && typeIds.length > 0){
            List<RyBlogType> ryTypeList = new ArrayList<>();
            for (Long typeId : typeIds) {
                RyBlogType ryBlogType = new RyBlogType();
                ryBlogType.setBlogId(blogId);
                ryBlogType.setTypeId(typeId);
                ryTypeList.add(ryBlogType);
            }
            //添加标签到数据库
            //批量插入
            ryBlogTypeMapper.batchInsertRyBlogType(ryTypeList);
        }


        //2.修改标签和分类
        return ryBlogMapper.updateRyBlog(ryBlog);
    }

    /**
     * 批量删除博客信息
     *
     * @param ids 需要删除的博客信息主键
     * @return 结果
     */
    @Override
    public int deleteRyBlogByIds(Long[] ids)
    {
        return ryBlogMapper.deleteRyBlogByIds(ids);
    }

    /**
     * 删除博客信息信息
     *
     * @param id 博客信息主键
     * @return 结果
     */
    @Override
    public int deleteRyBlogById(Long id)
    {
        return ryBlogMapper.deleteRyBlogById(id);
    }


    public List<RyBlog> BlogListAddTypeAndTag(List<RyBlog> ryBlogList){
        if(ryBlogList == null || ryBlogList.size() == 0){
            return ryBlogList;
        }
        //添加标签和分类
        for (RyBlog ryBlog : ryBlogList) {
            Long blogId = ryBlog.getId();
            //查询标签
            List<RyBlogTag> ryBlogTags = ryBlogTagMapper.selectRyBlogTagList(blogId);
            List<RyTag> ryTagList = new ArrayList<>();
            //得到标签
            ryBlogTags.forEach(ryBlogTag -> {
                RyTag ryTag = ryTagMapper.selectRyTagByTagId(ryBlogTag.getTagId());
                ryTagList.add(ryTag);
            });
            //放入标签
            ryBlog.setTags(ryTagList);



            //查询分类
            List<RyBlogType> ryBlogTypes = ryBlogTypeMapper.selectRyBlogTypeList(blogId);
            List<RyType> ryTypeList = new ArrayList<>();
            ryBlogTypes.forEach(ryBlogType -> {
                Long typeId = ryBlogType.getTypeId();
                RyType ryType = ryTypeMapper.selectRyTypeByTypeId(typeId);
                ryTypeList.add(ryType);
            });
            //放入标签信息
            ryBlog.setTypes(ryTypeList);

        }
        return ryBlogList;
    }

}
