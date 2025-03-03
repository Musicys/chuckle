package com.ruoyi.ry.blog.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ry.blog.mapper.RyBlogTagMapper;
import com.ruoyi.ry.blog.domain.RyBlogTag;
import com.ruoyi.ry.blog.service.IRyBlogTagService;

/**
 * blog标签关联Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-28
 */
@Service
public class RyBlogTagServiceImpl implements IRyBlogTagService
{
    @Autowired
    private RyBlogTagMapper ryBlogTagMapper;

    /**
     * 查询blog标签关联
     *
     * @param tagId blog标签关联主键
     * @return blog标签关联
     */
    @Override
    public RyBlogTag selectRyBlogTagByTagId(Long tagId)
    {
        return ryBlogTagMapper.selectRyBlogTagByTagId(tagId);
    }

    @Override
    public List<RyBlogTag> selectRyBlogTagList(Long BlogId) {
        return ryBlogTagMapper.selectRyBlogTagList(BlogId);
    }


    /**
     * 查询blog标签关联列表
     *
     * @param ryBlogTag blog标签关联
     * @return blog标签关联
     */

    /**
     * 新增blog标签关联
     *
     * @param ryBlogTag blog标签关联
     * @return 结果
     */
    @Override
    public int insertRyBlogTag(RyBlogTag ryBlogTag)
    {
        return ryBlogTagMapper.insertRyBlogTag(ryBlogTag);
    }

    /**
     * 批量删除blog标签关联
     *
     * @param tagIds 需要删除的blog标签关联主键
     * @return 结果
     */
    @Override
    public int deleteRyBlogTagByTagIds(Long[] tagIds)
    {
        return ryBlogTagMapper.deleteRyBlogTagByTagIds(tagIds);
    }

    /**
     * 删除blog标签关联信息
     *
     * @param tagId blog标签关联主键
     * @return 结果
     */
    @Override
    public int deleteRyBlogTagByTagId(Long tagId)
    {
        return ryBlogTagMapper.deleteRyBlogTagByTagId(tagId);
    }
}
