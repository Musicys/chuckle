package com.ruoyi.ry.type.service;

import java.util.List;
import com.ruoyi.ry.type.domain.RyType;

/**
 * 分类信息Service接口
 * 
 * @author ruoyi
 * @date 2025-02-26
 */
public interface IRyTypeService 
{
    /**
     * 查询分类信息
     * 
     * @param typeId 分类信息主键
     * @return 分类信息
     */
    public RyType selectRyTypeByTypeId(Long typeId);

    /**
     * 查询分类信息列表
     * 
     * @param ryType 分类信息
     * @return 分类信息集合
     */
    public List<RyType> selectRyTypeList(RyType ryType);

    /**
     * 新增分类信息
     * 
     * @param ryType 分类信息
     * @return 结果
     */
    public int insertRyType(RyType ryType);

    /**
     * 修改分类信息
     * 
     * @param ryType 分类信息
     * @return 结果
     */
    public int updateRyType(RyType ryType);

    /**
     * 批量删除分类信息
     * 
     * @param typeIds 需要删除的分类信息主键集合
     * @return 结果
     */
    public int deleteRyTypeByTypeIds(Long[] typeIds);

    /**
     * 删除分类信息信息
     * 
     * @param typeId 分类信息主键
     * @return 结果
     */
    public int deleteRyTypeByTypeId(Long typeId);
}
