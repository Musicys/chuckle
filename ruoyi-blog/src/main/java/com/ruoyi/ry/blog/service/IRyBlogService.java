package com.ruoyi.ry.blog.service;

import java.util.List;
import com.ruoyi.ry.blog.domain.RyBlog;

/**
 * 博客信息Service接口
 *
 * @author ruoyi
 * @date 2025-02-26
 */
public interface IRyBlogService
{
    /**
     * 查询博客信息
     *
     * @param id 博客信息主键
     * @return 博客信息
     */
    public RyBlog selectRyBlogById(Long id);

    /**
     * 查询博客信息列表
     *
     * @param ryBlog 博客信息
     * @return 博客信息集合
     */
    public List<RyBlog> selectRyBlogList(RyBlog ryBlog);

    /**
     * 新增博客信息
     *
     * @param ryBlog 博客信息
     * @return 结果
     */
    public Long insertRyBlog(RyBlog ryBlog);

    /**
     * 修改博客信息
     *
     * @param ryBlog 博客信息
     * @return 结果
     */
    public int updateRyBlog(RyBlog ryBlog);

    /**
     * 批量删除博客信息
     *
     * @param ids 需要删除的博客信息主键集合
     * @return 结果
     */
    public int deleteRyBlogByIds(Long[] ids);

    /**
     * 删除博客信息信息
     *
     * @param id 博客信息主键
     * @return 结果
     */
    public int deleteRyBlogById(Long id);
}
