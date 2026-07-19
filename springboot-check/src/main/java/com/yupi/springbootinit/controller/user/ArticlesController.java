package com.yupi.springbootinit.controller.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.domain.Articles;
import com.yupi.springbootinit.model.domain.Comments;
import com.yupi.springbootinit.model.dto.article.ArticleUserQueryRequest;
import com.yupi.springbootinit.model.dto.comment.CommentAddRequest;
import com.yupi.springbootinit.model.vo.ArticleUserVO;
import com.yupi.springbootinit.service.ArticlesService;
import com.yupi.springbootinit.service.CommentsService;
import com.yupi.springbootinit.utils.NetUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/articles")
@Api(tags = "文章展示")
public class ArticlesController {

    @Resource
    private ArticlesService articlesService;

    @Resource
    private CommentsService commentsService;

    @PostMapping("/list")
    @ApiOperation(value = "分页获取已发布文章列表（支持关键词搜索、标签/分类筛选）")
    public BaseResponse<Page<ArticleUserVO>> listArticles(@RequestBody ArticleUserQueryRequest request) {
        Page<ArticleUserVO> page = articlesService.getArticleUserPage(request);
        return ResultUtils.success(page);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "获取文章详情（含 Markdown 正文，自动增加阅读量）")
    public BaseResponse<ArticleUserVO> getArticle(@ApiParam(value = "文章ID", required = true) @PathVariable Integer id) {
        ArticleUserVO vo = articlesService.getArticleDetail(id);
        return ResultUtils.success(vo);
    }

    @PostMapping("/comment")
    @ApiOperation(value = "提交评论（或回复评论）")
    public BaseResponse<Integer> addComment(@RequestBody CommentAddRequest request, HttpServletRequest httpRequest) {
        // 参数校验
        if (request.getArticleId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文章ID不能为空");
        }
        if (!StringUtils.isNotBlank(request.getNickname())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "评论者昵称不能为空");
        }
        if (!StringUtils.isNotBlank(request.getContent())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "评论内容不能为空");
        }

        // 校验文章是否存在
        Articles article = articlesService.getById(request.getArticleId());
        if (article == null || article.getIsDelete() == 1 || article.getStatus() != 1) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "文章不存在或未发布");
        }

        // 如果是回复，校验被回复的评论是否存在
        if (request.getReplyToCommentId() != null) {
            Comments parent = commentsService.getById(request.getReplyToCommentId());
            if (parent == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "被回复的评论不存在");
            }
        }

        Comments comment = new Comments();
        comment.setArticleId(request.getArticleId());
        comment.setParentId(request.getReplyToCommentId());
        comment.setReplyToCommentId(request.getReplyToCommentId());
        comment.setNickname(request.getNickname());
        comment.setEmail(request.getEmail() != null ? request.getEmail() : "");
        comment.setAvatar(request.getAvatar() != null ? request.getAvatar() : "");
        comment.setContent(request.getContent());
        comment.setStatus(0); // 待审核
        comment.setIpAddress(NetUtils.getIpAddress(httpRequest));
        comment.setUserAgent(httpRequest.getHeader("User-Agent") != null ? httpRequest.getHeader("User-Agent") : "");

        commentsService.save(comment);

        // 更新文章评论数
        article.setCommentCount(article.getCommentCount() + 1);
        articlesService.updateById(article);

        return ResultUtils.success(comment.getId());
    }
}
