package com.ruoyi.ry.comment.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.ry.blog.domain.RyBlog;
import com.ruoyi.ry.blog.mapper.RyBlogMapper;
import com.ruoyi.ry.message.domain.RyMessage;
import com.ruoyi.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ry.comment.mapper.RyCommentMapper;
import com.ruoyi.ry.comment.domain.RyComment;
import com.ruoyi.ry.comment.service.IRyCommentService;

/**
 * 评论Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-26
 */
@Service
public class RyCommentServiceImpl implements IRyCommentService
{
    @Autowired
    private RyCommentMapper ryCommentMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RyBlogMapper ryBlogMapper;
    /**
     * 查询评论
     *
     * @param id 评论主键
     * @return 评论
     */
    @Override
    public RyComment selectRyCommentById(Long id)
    {
        return ryCommentMapper.selectRyCommentById(id);
    }

    /**
     * 查询评论列表
     *
     * @param ryComment 评论
     * @return 评论
     */
    @Override
    public List<RyComment> selectRyCommentList(RyComment ryComment)
    {
        List<RyComment> ryComments = new ArrayList<>();
        //判断用户权限
        String createBy = ryComment.getCreateBy();
        List<RyComment> allComment = ryCommentMapper.selectRyCommentList(null);
        if(createBy != null && !"".equals(createBy)){
            SysUser user = sysUserMapper.selectUserByUserName(createBy);
            if(user != null){
                //存在此用户
                //找出此用户所有没有被删除的留言，以及给他留言的留言

                ryComment.setDelFlag("0");
                List<RyComment> RyCommentList = ryCommentMapper.selectRyCommentList(ryComment);
                //查询子评论 (没有查询父留言 只从当前留言查询子留言)
                ryComments = RyCommentList.stream().map(comment -> {
                    //映射操作 设置子分类
                    comment.setChildComments(getChildComment(comment, allComment));
                    return comment;
                }).collect(Collectors.toList());
            }
        }else {
            //查询所有评论
            ryComments = ryCommentMapper.selectRyCommentList(ryComment);
        }
        //添加头像
        ryComments.forEach(comment -> {
            Long userId = comment.getUserId();
            if(userId != null){
                SysUser user = sysUserMapper.selectUserById(userId);
                comment.setAvatar(user.getAvatar());
            }
            //添加父留言
            Long parentId = comment.getParentId();
            if(parentId != null){
                RyComment parentComment = ryCommentMapper.selectRyCommentById(parentId);
                comment.setParentCreateBy(parentComment.getCreateBy());
            }

            //添加博客信息 -》导航过去
            Long blogId = comment.getBlogId();
            if(blogId != null){
                RyBlog ryBlog = ryBlogMapper.selectRyBlogById(blogId);
                comment.setBlogTitle(ryBlog.getTitle());
            }


        });

        return ryComments;
    }

    /**
     * 新增评论
     *
     * @param ryComment 评论
     * @return 结果
     */
    @Override
    public int insertRyComment(RyComment ryComment)
    {
        ryComment.setCreateTime(DateUtils.getNowDate());
        return ryCommentMapper.insertRyComment(ryComment);
    }

    /**
     * 修改评论
     *
     * @param ryComment 评论
     * @return 结果
     */
    @Override
    public int updateRyComment(RyComment ryComment)
    {
        ryComment.setUpdateTime(DateUtils.getNowDate());
        return ryCommentMapper.updateRyComment(ryComment);
    }

    /**
     * 批量删除评论
     *
     * @param ids 需要删除的评论主键
     * @return 结果
     */
    @Override
    public int deleteRyCommentByIds(Long[] ids)
    {
        return ryCommentMapper.deleteRyCommentByIds(ids);
    }

    /**
     * 删除评论信息
     *
     * @param id 评论主键
     * @return 结果
     */
    @Override
    public int deleteRyCommentById(Long id)
    {
        return ryCommentMapper.deleteRyCommentById(id);
    }

    private List<RyComment> getChildComment(RyComment ryComment, List<RyComment> allComment) {
        List<RyComment> collect = allComment.stream().filter(comment -> {
            return comment.getParentId().equals(ryComment.getId()) && comment.getType().equals("1");
        }).map(comment -> {
            comment.setChildComments(getChildComment(comment, allComment));
            return comment;
        }).sorted((comment1, comment2) -> {
            return comment1.getUpdateTime().compareTo(comment2.getUpdateTime());
        }).collect(Collectors.toList());
        return collect;
    }
}
