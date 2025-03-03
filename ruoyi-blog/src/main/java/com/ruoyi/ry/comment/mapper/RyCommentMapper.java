package com.ruoyi.ry.comment.mapper;

import java.util.List;
import com.ruoyi.ry.comment.domain.RyComment;

/**
 * 评论Mapper接口
 * 
 * @author ruoyi
 * @date 2025-02-26
 */
public interface RyCommentMapper 
{
    /**
     * 查询评论
     * 
     * @param id 评论主键
     * @return 评论
     */
    public RyComment selectRyCommentById(Long id);

    /**
     * 查询评论列表
     * 
     * @param ryComment 评论
     * @return 评论集合
     */
    public List<RyComment> selectRyCommentList(RyComment ryComment);

    /**
     * 新增评论
     * 
     * @param ryComment 评论
     * @return 结果
     */
    public int insertRyComment(RyComment ryComment);

    /**
     * 修改评论
     * 
     * @param ryComment 评论
     * @return 结果
     */
    public int updateRyComment(RyComment ryComment);

    /**
     * 删除评论
     * 
     * @param id 评论主键
     * @return 结果
     */
    public int deleteRyCommentById(Long id);

    /**
     * 批量删除评论
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRyCommentByIds(Long[] ids);
}
