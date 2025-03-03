package com.ruoyi.ry.blog.mapper;

import java.util.List;
import com.ruoyi.ry.blog.domain.RyBlogTag;
import com.ruoyi.ry.blog.domain.RyBlogType;

/**
 * blog标签关联Mapper接口
 *
 * @author ruoyi
 * @date 2025-02-28
 */
public interface RyBlogTagMapper
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
     * @param blogId blog标签关联
     * @return blog标签关联集合
     */
    public List<RyBlogTag> selectRyBlogTagList(Long blogId);

    public int deleteRyBlogTagByBlogId(Long blogId);

    /**
     * 新增blog标签关联
     *
     * @param ryBlogTag blog标签关联
     * @return 结果
     */
    public int insertRyBlogTag(RyBlogTag ryBlogTag);

    // /**
    //  * 修改blog标签关联
    //  *
    //  * @param ryBlogTag blog标签关联
    //  * @return 结果
    //  */
    // public int updateRyBlogTag(RyBlogTag ryBlogTag);
    public int batchInsertRyBlogTag(List<RyBlogTag> ryBlogTagList);

    /**
     * 删除blog标签关联
     *
     * @param tagId blog标签关联主键
     * @return 结果
     */
    public int deleteRyBlogTagByTagId(Long tagId);

    /**
     * 批量删除blog标签关联
     *
     * @param tagIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRyBlogTagByTagIds(Long[] tagIds);

    /**查询该标签下有多少个博客*/
    public int selectCountBlogByTagId(Long tagId);
}
