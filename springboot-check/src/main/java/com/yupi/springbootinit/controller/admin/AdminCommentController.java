package com.yupi.springbootinit.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.DeleteRequest;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.domain.Articles;
import com.yupi.springbootinit.model.domain.Comments;
import com.yupi.springbootinit.model.dto.comment.CommentQueryRequest;
import com.yupi.springbootinit.model.dto.comment.CommentReplyRequest;
import com.yupi.springbootinit.model.dto.comment.CommentReviewRequest;
import com.yupi.springbootinit.model.vo.CommentAdminVO;
import com.yupi.springbootinit.service.ArticlesService;
import com.yupi.springbootinit.service.CommentsService;
import com.yupi.springbootinit.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/comment")
@Api(tags = "管理端-评论管理")
public class AdminCommentController {

    @Resource
    private CommentsService commentsService;

    @Resource
    private ArticlesService articlesService;

    /**
     * 校验管理员登录状态
     */
    private void checkAdmin(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        String token = authHeader.substring(7);
        if (!JwtUtils.verify(token)) {
            throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
        }
    }

    @PostMapping("/list")
    @ApiOperation(value = "分页查询评论列表（管理端）")
    public BaseResponse<Page<CommentAdminVO>> listComments(@RequestBody CommentQueryRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        LambdaQueryWrapper<Comments> wrapper = new LambdaQueryWrapper<>();
        if (request.getArticleId() != null) {
            wrapper.eq(Comments::getArticleId, request.getArticleId());
        }
        if (request.getStatus() != null) {
            wrapper.eq(Comments::getStatus, request.getStatus());
        }
        if (StringUtils.hasText(request.getNickname())) {
            wrapper.like(Comments::getNickname, request.getNickname());
        }
        wrapper.orderByDesc(Comments::getCreatedAt);

        Page<Comments> page = commentsService.page(
                new Page<>(request.getCurrent(), request.getPageSize()), wrapper);

        Page<CommentAdminVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<CommentAdminVO> records = page.getRecords().stream().map(comment -> {
            CommentAdminVO vo = new CommentAdminVO();
            BeanUtils.copyProperties(comment, vo);

            // 文章标题
            if (comment.getArticleId() != null && comment.getArticleId() > 0) {
                Articles article = articlesService.getById(comment.getArticleId());
                if (article != null) {
                    vo.setArticleTitle(article.getTitle());
                }
            } else {
                vo.setArticleTitle("留言板");
            }
            return vo;
        }).collect(Collectors.toList());

        voPage.setRecords(records);
        return ResultUtils.success(voPage);
    }

    @PostMapping("/review")
    @ApiOperation(value = "审核评论")
    public BaseResponse<Boolean> reviewComment(@RequestBody CommentReviewRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (request.getId() == null || request.getStatus() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "评论ID和审核状态不能为空");
        }

        Comments comment = commentsService.getById(request.getId());
        if (comment == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        comment.setStatus(request.getStatus());
        boolean updated = commentsService.updateById(comment);
        return ResultUtils.success(updated);
    }

    @PostMapping("/reply")
    @ApiOperation(value = "管理员回复评论")
    public BaseResponse<Integer> replyComment(@RequestBody CommentReplyRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (request.getArticleId() == null || request.getReplyToCommentId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文章ID和被回复评论ID不能为空");
        }
        if (!StringUtils.hasText(request.getContent())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "回复内容不能为空");
        }

        Comments reply = new Comments();
        reply.setArticleId(request.getArticleId());
        reply.setParentId(request.getReplyToCommentId());
        reply.setReplyToCommentId(request.getReplyToCommentId());
        reply.setNickname("管理员");
        reply.setContent(request.getContent());
        reply.setStatus(1);
        reply.setIpAddress("");
        reply.setUserAgent("");

        commentsService.save(reply);

        // 更新文章评论数
        if (request.getArticleId() > 0) {
            Articles article = articlesService.getById(request.getArticleId());
            if (article != null) {
                article.setCommentCount(article.getCommentCount() + 1);
                articlesService.updateById(article);
            }
        }

        return ResultUtils.success(reply.getId());
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除评论")
    public BaseResponse<Boolean> deleteComment(@RequestBody DeleteRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (request.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "评论ID不能为空");
        }

        Comments comment = commentsService.getById(request.getId());
        if (comment == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        commentsService.removeById(request.getId());

        // 更新文章评论数
        if (comment.getArticleId() != null && comment.getArticleId() > 0) {
            Articles article = articlesService.getById(comment.getArticleId());
            if (article != null) {
                article.setCommentCount(Math.max(0, article.getCommentCount() - 1));
                articlesService.updateById(article);
            }
        }

        return ResultUtils.success(true);
    }
}
