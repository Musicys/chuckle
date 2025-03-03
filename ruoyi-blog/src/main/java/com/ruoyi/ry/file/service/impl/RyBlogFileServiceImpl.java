package com.ruoyi.ry.file.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ry.file.mapper.RyBlogFileMapper;
import com.ruoyi.ry.file.domain.RyBlogFile;
import com.ruoyi.ry.file.service.IRyBlogFileService;

/**
 * blog文件Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-26
 */
@Service
public class RyBlogFileServiceImpl implements IRyBlogFileService
{
    @Autowired
    private RyBlogFileMapper ryBlogFileMapper;

    /**
     * 查询blog文件
     *
     * @param fileId blog文件主键
     * @return blog文件
     */
    @Override
    public RyBlogFile selectRyBlogFileByFileId(Long fileId)
    {
        return ryBlogFileMapper.selectRyBlogFileByFileId(fileId);
    }

    /**
     * 查询blog文件列表
     *
     * @param ryBlogFile blog文件
     * @return blog文件
     */
    @Override
    public List<RyBlogFile> selectRyBlogFileList(RyBlogFile ryBlogFile)
    {
        return ryBlogFileMapper.selectRyBlogFileList(ryBlogFile);
    }

    /**
     * 新增blog文件
     *
     * @param ryBlogFile blog文件
     * @return 结果
     */
    @Override
    public int insertRyBlogFile(RyBlogFile ryBlogFile)
    {
        return ryBlogFileMapper.insertRyBlogFile(ryBlogFile);
    }

    /**
     * 批量删除blog文件
     *
     * @param fileIds 需要删除的blog文件主键
     * @return 结果
     */
    @Override
    public int deleteRyBlogFileByFileIds(Long[] fileIds)
    {
        return ryBlogFileMapper.deleteRyBlogFileByFileIds(fileIds);
    }

    /**
     * 删除blog文件信息
     *
     * @param fileId blog文件主键
     * @return 结果
     */
    @Override
    public int deleteRyBlogFileByFileId(Long fileId)
    {
        return ryBlogFileMapper.deleteRyBlogFileByFileId(fileId);
    }
}
