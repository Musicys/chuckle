package com.yupi.springbootinit.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.DeleteRequest;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.domain.ArticleTags;
import com.yupi.springbootinit.model.domain.Articles;
import com.yupi.springbootinit.model.domain.Categories;
import com.yupi.springbootinit.model.domain.Tags;
import com.yupi.springbootinit.model.dto.article.ArticleAddRequest;
import com.yupi.springbootinit.model.dto.article.ArticleQueryRequest;
import com.yupi.springbootinit.model.dto.article.ArticleUpdateRequest;
import com.yupi.springbootinit.model.vo.ArticleAdminVO;
import com.yupi.springbootinit.model.vo.TagVO;
import com.yupi.springbootinit.service.ArticleTagsService;
import com.yupi.springbootinit.service.ArticlesService;
import com.yupi.springbootinit.service.CategoriesService;
import com.yupi.springbootinit.service.TagsService;
import com.yupi.springbootinit.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/article")
@Api(tags = "管理端-文章管理")
public class AdminArticleController {

    @Resource
    private ArticlesService articlesService;

    @Resource
    private CategoriesService categoriesService;

    @Resource
    private TagsService tagsService;

    @Resource
    private ArticleTagsService articleTagsService;

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

    @PostMapping("/add")
    @ApiOperation(value = "新增文章")
    public BaseResponse<Integer> addArticle(@RequestBody ArticleAddRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (!StringUtils.hasText(request.getTitle())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文章标题不能为空");
        }
        if (!StringUtils.hasText(request.getContent())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文章内容不能为空");
        }

        Articles article = new Articles();
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setSummary(request.getSummary() != null ? request.getSummary() : "");
        article.setCategoryId(request.getCategoryId());
        article.setStatus(request.getStatus() != null ? request.getStatus() : 0);
        article.setWordCount(request.getContent().length());
        article.setReadCount(0);
        article.setCommentCount(0);
        article.setIsDelete(0);
        articlesService.save(article);

        // 保存标签关联
        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            List<ArticleTags> list = request.getTagIds().stream().map(tagId -> {
                ArticleTags at = new ArticleTags();
                at.setArticleId(article.getId());
                at.setTagId(tagId);
                return at;
            }).collect(Collectors.toList());
            articleTagsService.saveBatch(list);
        }

        return ResultUtils.success(article.getId());
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新文章")
    public BaseResponse<Boolean> updateArticle(@RequestBody ArticleUpdateRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        Integer id = request.getId();
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文章ID不能为空");
        }

        Articles article = articlesService.getById(id);
        if (article == null || article.getIsDelete() == 1) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        if (StringUtils.hasText(request.getTitle())) {
            article.setTitle(request.getTitle());
        }
        if (StringUtils.hasText(request.getContent())) {
            article.setContent(request.getContent());
            article.setWordCount(request.getContent().length());
        }
        if (request.getSummary() != null) {
            article.setSummary(request.getSummary());
        }
        if (request.getCategoryId() != null) {
            article.setCategoryId(request.getCategoryId());
        }
        if (request.getStatus() != null) {
            article.setStatus(request.getStatus());
        }

        boolean updated = articlesService.updateById(article);

        // 更新标签关联（全量替换）
        if (request.getTagIds() != null) {
            articleTagsService.lambdaQuery()
                    .eq(ArticleTags::getArticleId, id)
                    .list()
                    .forEach(at -> articleTagsService.removeById(at.getId()));

            if (!request.getTagIds().isEmpty()) {
                List<ArticleTags> list = request.getTagIds().stream().map(tagId -> {
                    ArticleTags at = new ArticleTags();
                    at.setArticleId(id);
                    at.setTagId(tagId);
                    return at;
                }).collect(Collectors.toList());
                articleTagsService.saveBatch(list);
            }
        }

        return ResultUtils.success(updated);
    }

    @GetMapping("/get")
    @ApiOperation(value = "获取文章详情（管理端）")
    public BaseResponse<ArticleAdminVO> getArticle(@RequestParam Integer id, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        Articles article = articlesService.getById(id);
        if (article == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        ArticleAdminVO vo = new ArticleAdminVO();
        BeanUtils.copyProperties(article, vo);

        // 分类名称
        if (article.getCategoryId() != null) {
            Categories category = categoriesService.getById(article.getCategoryId());
            if (category != null) {
                vo.setCategoryName(category.getName());
            }
        }

        // 标签列表
        List<ArticleTags> relations = articleTagsService.lambdaQuery()
                .eq(ArticleTags::getArticleId, id).list();
        if (!relations.isEmpty()) {
            List<Integer> tagIds = relations.stream().map(ArticleTags::getTagId).collect(Collectors.toList());
            List<Tags> tagList = tagsService.listByIds(tagIds);
            List<TagVO> tagVOs = tagList.stream().map(tag -> {
                TagVO t = new TagVO();
                t.setId(tag.getId());
                t.setName(tag.getName());
                t.setColor(tag.getColor());
                return t;
            }).collect(Collectors.toList());
            vo.setTags(tagVOs);
        } else {
            vo.setTags(new ArrayList<>());
        }

        return ResultUtils.success(vo);
    }

    @PostMapping("/list")
    @ApiOperation(value = "分页查询文章列表（管理端）")
    public BaseResponse<Page<ArticleAdminVO>> listArticles(@RequestBody ArticleQueryRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        LambdaQueryWrapper<Articles> wrapper = new LambdaQueryWrapper<>();
        if (request.getId() != null) {
            wrapper.eq(Articles::getId, request.getId());
        }
        if (StringUtils.hasText(request.getTitle())) {
            wrapper.like(Articles::getTitle, request.getTitle());
        }
        if (request.getCategoryId() != null) {
            wrapper.eq(Articles::getCategoryId, request.getCategoryId());
        }
        if (request.getStatus() != null) {
            wrapper.eq(Articles::getStatus, request.getStatus());
        }
        wrapper.eq(Articles::getIsDelete, request.getIsDelete() != null ? request.getIsDelete() : 0);
        wrapper.orderByDesc(Articles::getCreatedAt);

        Page<Articles> page = articlesService.page(
                new Page<>(request.getCurrent(), request.getPageSize()), wrapper);

        Page<ArticleAdminVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<ArticleAdminVO> records = page.getRecords().stream().map(article -> {
            ArticleAdminVO vo = new ArticleAdminVO();
            BeanUtils.copyProperties(article, vo);

            if (article.getCategoryId() != null) {
                Categories category = categoriesService.getById(article.getCategoryId());
                if (category != null) {
                    vo.setCategoryName(category.getName());
                }
            }

            List<ArticleTags> relations = articleTagsService.lambdaQuery()
                    .eq(ArticleTags::getArticleId, article.getId()).list();
            if (!relations.isEmpty()) {
                List<Integer> tagIds = relations.stream().map(ArticleTags::getTagId).collect(Collectors.toList());
                List<Tags> tagList = tagsService.listByIds(tagIds);
                List<TagVO> tagVOs = tagList.stream().map(tag -> {
                    TagVO t = new TagVO();
                    t.setId(tag.getId());
                    t.setName(tag.getName());
                    t.setColor(tag.getColor());
                    return t;
                }).collect(Collectors.toList());
                vo.setTags(tagVOs);
            } else {
                vo.setTags(new ArrayList<>());
            }
            return vo;
        }).collect(Collectors.toList());

        voPage.setRecords(records);
        return ResultUtils.success(voPage);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除文章（逻辑删除）")
    public BaseResponse<Boolean> deleteArticle(@RequestBody DeleteRequest request, HttpServletRequest httpRequest) {
        checkAdmin(httpRequest);

        if (request.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文章ID不能为空");
        }

        Articles article = articlesService.getById(request.getId());
        if (article == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        article.setIsDelete(1);
        boolean removed = articlesService.updateById(article);
        return ResultUtils.success(removed);
    }
}
