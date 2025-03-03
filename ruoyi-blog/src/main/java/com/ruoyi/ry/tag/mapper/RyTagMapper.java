package com.ruoyi.ry.tag.mapper;

import java.util.List;
import com.ruoyi.ry.tag.domain.RyTag;

/**
 * 标签信息Mapper接口
 * 
 * @author ruoyi
 * @date 2025-02-26
 */
public interface RyTagMapper 
{
    /**
     * 查询标签信息
     * 
     * @param tagId 标签信息主键
     * @return 标签信息
     */
    public RyTag selectRyTagByTagId(Long tagId);

    /**
     * 查询标签信息列表
     * 
     * @param ryTag 标签信息
     * @return 标签信息集合
     */
    public List<RyTag> selectRyTagList(RyTag ryTag);

    /**
     * 新增标签信息
     * 
     * @param ryTag 标签信息
     * @return 结果
     */
    public int insertRyTag(RyTag ryTag);

    /**
     * 修改标签信息
     * 
     * @param ryTag 标签信息
     * @return 结果
     */
    public int updateRyTag(RyTag ryTag);

    /**
     * 删除标签信息
     * 
     * @param tagId 标签信息主键
     * @return 结果
     */
    public int deleteRyTagByTagId(Long tagId);

    /**
     * 批量删除标签信息
     * 
     * @param tagIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRyTagByTagIds(Long[] tagIds);
}
