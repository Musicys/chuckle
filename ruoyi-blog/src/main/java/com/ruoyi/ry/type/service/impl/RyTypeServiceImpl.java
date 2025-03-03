package com.ruoyi.ry.type.service.impl;

import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.ry.blog.domain.RyBlogType;
import com.ruoyi.ry.blog.mapper.RyBlogTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ry.type.mapper.RyTypeMapper;
import com.ruoyi.ry.type.domain.RyType;
import com.ruoyi.ry.type.service.IRyTypeService;

/**
 * 分类信息Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-26
 */
@Service
public class RyTypeServiceImpl implements IRyTypeService
{
    @Autowired
    private RyTypeMapper ryTypeMapper;
    @Autowired
    private RyBlogTypeMapper ryBlogTypeMapper;

    /**
     * 查询分类信息
     *
     * @param typeId 分类信息主键
     * @return 分类信息
     */
    @Override
    public RyType selectRyTypeByTypeId(Long typeId)
    {
        return ryTypeMapper.selectRyTypeByTypeId(typeId);
    }

    /**
     * 查询分类信息列表
     *
     * @param ryType 分类信息
     * @return 分类信息
     */
    @Override
    public List<RyType> selectRyTypeList(RyType ryType)
    {
        List<RyType> ryTypeList = ryTypeMapper.selectRyTypeList(ryType);
        for (RyType type : ryTypeList) {
            int count = ryBlogTypeMapper.selectCountRyBlogTypeByTypeId(type.getTypeId());
            type.setBlogNum(count);
        }
        return ryTypeList;
    }

    /**
     * 新增分类信息
     *
     * @param ryType 分类信息
     * @return 结果
     */
    @Override
    public int insertRyType(RyType ryType)
    {
        //先看看新增的分类名是否已存在
        RyType tape = ryTypeMapper.selectRYTypeByTypeName(ryType.getTypeName());
        if(tape != null){
            //已存在就返回错误信息
            throw new ServiceException("分类名已存在");
        }
        ryType.setCreateTime(DateUtils.getNowDate());
        return ryTypeMapper.insertRyType(ryType);
    }

    /**
     * 修改分类信息
     *
     * @param ryType 分类信息
     * @return 结果
     */
    @Override
    public int updateRyType(RyType ryType)
    {
        ryType.setUpdateTime(DateUtils.getNowDate());
        //修改之后的要看是否存在
        RyType type = ryTypeMapper.selectRYTypeByTypeName(ryType.getTypeName());
        if(type != null){
            //已存在不能修改
            throw new ServiceException("分类名称已存在");
        }
        //得到当前分类的图片并且上传阿里云 并删除原来图片路径
        //???????还没写
        return ryTypeMapper.updateRyType(ryType);
    }

    /**
     * 批量删除分类信息
     *
     * @param typeIds 需要删除的分类信息主键
     * @return 结果
     */
    @Override
    public int deleteRyTypeByTypeIds(Long[] typeIds)
    {
        //顺便删除掉阿里云的图片
        //还没写????
        return ryTypeMapper.deleteRyTypeByTypeIds(typeIds);
    }

    /**
     * 删除分类信息信息
     *
     * @param typeId 分类信息主键
     * @return 结果
     */
    @Override
    public int deleteRyTypeByTypeId(Long typeId)
    {
        //删除图片
        //?????没写
        return ryTypeMapper.deleteRyTypeByTypeId(typeId);
    }
}
