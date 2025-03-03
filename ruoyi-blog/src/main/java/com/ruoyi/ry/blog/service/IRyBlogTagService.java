package com.ruoyi.ry.blog.service;

import java.util.List;
import com.ruoyi.ry.blog.domain.RyBlogTag;

/**
 * blog标签关联Service接口
 *
 * @author ruoyi
 * @date 2025-02-28
 */
public interface IRyBlogTagService
{
    /**
     * 查询blog标签关联
     *
     * @param tagId blog标签关联主键
     * @return blog标签关联
     */
    public RyBlogTag selectRyBlogTagByTagId(Long tagId);

    /**
     * 查询blog标签关联列表
     *
     * @param ryBlogTag blog标签关联
     * @return blog标签关联集合
     */
    public List<RyBlogTag> selectRyBlogTagList(Long BlogId);

    /**
     * 新增blog标签关联
     *
     * @param ryBlogTag blog标签关联
     * @return 结果
     */
    public int insertRyBlogTag(RyBlogTag ryBlogTag);

    /**
     * 批量删除blog标签关联
     *
     * @param tagIds 需要删除的blog标签关联主键集合
     * @return 结果
     */
    public int deleteRyBlogTagByTagIds(Long[] tagIds);

    /**
     * 删除blog标签关联信息
     *
     * @param tagId blog标签关联主键
     * @return 结果
     */
    public int deleteRyBlogTagByTagId(Long tagId);
}
