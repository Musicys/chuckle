package com.ruoyi.ry.file.service;

import java.util.List;
import com.ruoyi.ry.file.domain.RyBlogFile;

/**
 * blog文件Service接口
 *
 * @author ruoyi
 * @date 2025-02-26
 */
public interface IRyBlogFileService
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
     * 批量删除blog文件
     *
     * @param fileIds 需要删除的blog文件主键集合
     * @return 结果
     */
    public int deleteRyBlogFileByFileIds(Long[] fileIds);

    /**
     * 删除blog文件信息
     *
     * @param fileId blog文件主键
     * @return 结果
     */
    public int deleteRyBlogFileByFileId(Long fileId);
}
