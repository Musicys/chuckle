package com.ruoyi.ry.blog.mapper;

import java.util.List;
import com.ruoyi.ry.blog.domain.RyBlogType;

/**
 * blog分类关联Mapper接口
 *
 * @author ruoyi
 * @date 2025-02-28
 */
public interface RyBlogTypeMapper
{
    /**
     * 查询blog分类关联
     *
     * @param typeId blog分类关联主键
     * @return blog分类关联
     */
    public List<RyBlogType> selectRyBlogTypeByTypeId(Long typeId);

    /**
     * 查询blog分类关联列表
     *
     * @param blogId blog分类关联
     * @return blog分类关联集合
     */
    public List<RyBlogType> selectRyBlogTypeList(Long blogId);

    /**
     * 新增blog分类关联
     *
     * @param ryBlogType blog分类关联
     * @return 结果
     */
    public int insertRyBlogType(RyBlogType ryBlogType);

    /**
     * 批量增加blog分类关联
     */
    public int batchInsertRyBlogType(List<RyBlogType> ryBlogTypeList);

    public int deleteRyBlogTypeByBlogId(Long BlogId);
    /**
     * 删除blog分类关联
     *
     * @param typeId blog分类关联主键
     * @return 结果
     */
    public int deleteRyBlogTypeByTypeId(Long typeId);

    /**
     * 批量删除blog分类关联
     *
     * @param typeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRyBlogTypeByTypeIds(Long[] typeIds);

    //查询该type关联的博客数量
    public int selectCountRyBlogTypeByTypeId(Long typeId);
}
