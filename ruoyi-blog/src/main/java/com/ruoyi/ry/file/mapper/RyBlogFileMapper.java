package com.ruoyi.ry.file.mapper;

import java.util.List;
import com.ruoyi.ry.file.domain.RyBlogFile;

/**
 * blog文件Mapper接口
 * 
 * @author ruoyi
 * @date 2025-02-26
 */
public interface RyBlogFileMapper 
{
    /**
     * 查询blog文件
     * 
     * @param fileId blog文件主键
     * @return blog文件
     */
    public RyBlogFile selectRyBlogFileByFileId(Long fileId);

    /**
     * 查询blog文件列表
     * 
     * @param ryBlogFile blog文件
     * @return blog文件集合
     */
    public List<RyBlogFile> selectRyBlogFileList(RyBlogFile ryBlogFile);

    /**
     * 新增blog文件
     * 
     * @param ryBlogFile blog文件
     * @return 结果
     */
    public int insertRyBlogFile(RyBlogFile ryBlogFile);

    /**
     * 修改blog文件
     * 
     * @param ryBlogFile blog文件
     * @return 结果
     */
    public int updateRyBlogFile(RyBlogFile ryBlogFile);

    /**
     * 删除blog文件
     * 
     * @param fileId blog文件主键
     * @return 结果
     */
    public int deleteRyBlogFileByFileId(Long fileId);

    /**
     * 批量删除blog文件
     * 
     * @param fileIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRyBlogFileByFileIds(Long[] fileIds);
}
