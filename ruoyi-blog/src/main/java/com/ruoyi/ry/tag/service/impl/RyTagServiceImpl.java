package com.ruoyi.ry.tag.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.ry.blog.mapper.RyBlogTagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ry.tag.mapper.RyTagMapper;
import com.ruoyi.ry.tag.domain.RyTag;
import com.ruoyi.ry.tag.service.IRyTagService;

/**
 * 标签信息Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-26
 */
@Service
public class RyTagServiceImpl implements IRyTagService
{
    @Autowired
    private RyTagMapper ryTagMapper;

    @Autowired
    private RyBlogTagMapper ryBlogTagMapper;

    /**
     * 查询标签信息
     *
     * @param tagId 标签信息主键
     * @return 标签信息
     */
    @Override
    public RyTag selectRyTagByTagId(Long tagId)
    {
        return ryTagMapper.selectRyTagByTagId(tagId);
    }

    /**
     * 查询标签信息列表
     *
     * @param ryTag 标签信息
     * @return 标签信息
     */
    @Override
    public List<RyTag> selectRyTagList(RyTag ryTag)
    {
        List<RyTag> ryTagList = ryTagMapper.selectRyTagList(ryTag);
        for (RyTag tag : ryTagList) {
            int blogNum = ryBlogTagMapper.selectCountBlogByTagId(tag.getTagId());
            tag.setBlogNum(blogNum);
        }

        //查询所在标签有的博客数量
        return ryTagList;
    }

    /**
     * 新增标签信息
     *
     * @param ryTag 标签信息
     * @return 结果
     */
    @Override
    public int insertRyTag(RyTag ryTag)
    {
        ryTag.setCreateTime(DateUtils.getNowDate());
        return ryTagMapper.insertRyTag(ryTag);
    }

    /**
     * 修改标签信息
     *
     * @param ryTag 标签信息
     * @return 结果
     */
    @Override
    public int updateRyTag(RyTag ryTag)
    {
        ryTag.setUpdateTime(DateUtils.getNowDate());
        return ryTagMapper.updateRyTag(ryTag);
    }

    /**
     * 批量删除标签信息
     *
     * @param tagIds 需要删除的标签信息主键
     * @return 结果
     */
    @Override
    public int deleteRyTagByTagIds(Long[] tagIds)
    {
        return ryTagMapper.deleteRyTagByTagIds(tagIds);
    }

    /**
     * 删除标签信息信息
     *
     * @param tagId 标签信息主键
     * @return 结果
     */
    @Override
    public int deleteRyTagByTagId(Long tagId)
    {
        return ryTagMapper.deleteRyTagByTagId(tagId);
    }
}
