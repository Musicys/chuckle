package com.ruoyi.ry.blog.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ry.blog.mapper.RyBlogTypeMapper;
import com.ruoyi.ry.blog.domain.RyBlogType;
import com.ruoyi.ry.blog.service.IRyBlogTypeService;

/**
 * blog分类关联Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-28
 */
@Service
public class RyBlogTypeServiceImpl implements IRyBlogTypeService
{
    @Autowired
    private RyBlogTypeMapper ryBlogTypeMapper;

    /**
     * 查询blog分类关联
     *
     * @param typeId blog分类关联主键
     * @return blog分类关联
     */
    @Override
    public List<RyBlogType> selectRyBlogTypeByTypeId(Long typeId)
    {
        return ryBlogTypeMapper.selectRyBlogTypeByTypeId(typeId);
    }

    /**
     * 查询blog分类关联列表
     *
     * @param  blogId blogId分类关联
     * @return blog分类关联
     */

    @Override
    public List<RyBlogType> selectRyBlogTypeList(Long blogId) {
        return ryBlogTypeMapper.selectRyBlogTypeList(blogId);
    }



    /**
     * 新增blog分类关联
     *
     * @param ryBlogType blog分类关联
     * @return 结果
     */
    @Override
    public int insertRyBlogType(RyBlogType ryBlogType)
    {
        return ryBlogTypeMapper.insertRyBlogType(ryBlogType);
    }


    /**
     * 批量删除blog分类关联
     *
     * @param typeIds 需要删除的blog分类关联主键
     * @return 结果
     */
    @Override
    public int deleteRyBlogTypeByTypeIds(Long[] typeIds)
    {
        return ryBlogTypeMapper.deleteRyBlogTypeByTypeIds(typeIds);
    }

    /**
     * 删除blog分类关联信息
     *
     * @param typeId blog分类关联主键
     * @return 结果
     */
    @Override
    public int deleteRyBlogTypeByTypeId(Long typeId)
    {
        return ryBlogTypeMapper.deleteRyBlogTypeByTypeId(typeId);
    }
}
