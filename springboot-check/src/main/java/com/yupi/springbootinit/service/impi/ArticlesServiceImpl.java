package com.yupi.springbootinit.service.impi;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.mapper.ArticlesMapper;
import com.yupi.springbootinit.model.domain.ArticleTags;
import com.yupi.springbootinit.model.domain.Articles;
import com.yupi.springbootinit.model.domain.Categories;
import com.yupi.springbootinit.model.domain.Tags;
import com.yupi.springbootinit.model.dto.article.ArticleUserQueryRequest;
import com.yupi.springbootinit.model.vo.ArticleSearchVO;
import com.yupi.springbootinit.model.vo.ArticleUserVO;
import com.yupi.springbootinit.model.vo.TagVO;
import com.yupi.springbootinit.service.ArticleTagsService;
import com.yupi.springbootinit.service.ArticlesService;
import com.yupi.springbootinit.service.CategoriesService;
import com.yupi.springbootinit.service.TagsService;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
* @author 黎旺
* @description 针对表【articles(文章表)】的数据库操作Service实现
* @createDate 2026-07-07 14:07:09
*/
@Service
public class ArticlesServiceImpl extends ServiceImpl<ArticlesMapper, Articles>
    implements ArticlesService{

    @Resource
    private CategoriesService categoriesService;

    @Resource
    private TagsService tagsService;

    @Resource
    private ArticleTagsService articleTagsService;

    @Override
    public Page<ArticleUserVO> getArticleUserPage(ArticleUserQueryRequest request) {
        // 1. 构建查询条件：仅查询已发布、未删除的文章
        LambdaQueryWrapper<Articles> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Articles::getStatus, 1)
               .eq(Articles::getIsDelete, 0);

        // 关键词模糊搜索（标题 + 正文）
        if (StringUtils.isNotBlank(request.getKeyword())) {
            wrapper.and(w ->
                    w.like(Articles::getTitle, request.getKeyword())
                     .or()
                     .like(Articles::getContent, request.getKeyword())
            );
        }

        // 分类筛选
        if (request.getCategoryId() != null) {
            wrapper.eq(Articles::getCategoryId, request.getCategoryId());
        }

        // 标签筛选：先查出关联的文章ID集合
        if (request.getTagId() != null) {
            List<ArticleTags> articleTags = articleTagsService.lambdaQuery()
                    .eq(ArticleTags::getTagId, request.getTagId())
                    .list();
            if (articleTags.isEmpty()) {
                return new Page<>(request.getCurrent(), request.getPageSize(), 0);
            }
            Set<Integer> articleIds = articleTags.stream()
                    .map(ArticleTags::getArticleId)
                    .collect(Collectors.toSet());
            wrapper.in(Articles::getId, articleIds);
        }

        wrapper.orderByDesc(Articles::getCreatedAt);

        // 2. 分页查询
        Page<Articles> page = this.page(
                new Page<>(request.getCurrent(), request.getPageSize()), wrapper);

        if (page.getRecords().isEmpty()) {
            return new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        }

        // 3. 批量加载分类
        Set<Integer> categoryIds = page.getRecords().stream()
                .map(Articles::getCategoryId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Map<Integer, Categories> categoryMap;
        if (!categoryIds.isEmpty()) {
            categoryMap = categoriesService.listByIds(categoryIds).stream()
                    .collect(Collectors.toMap(Categories::getId, c -> c));
        } else {
            categoryMap = Collections.emptyMap();
        }

        // 4. 批量加载标签
        List<Integer> articleIdList = page.getRecords().stream()
                .map(Articles::getId)
                .collect(Collectors.toList());

        List<ArticleTags> allRelations = articleTagsService.lambdaQuery()
                .in(ArticleTags::getArticleId, articleIdList)
                .list();

        Set<Integer> allTagIds = allRelations.stream()
                .map(ArticleTags::getTagId)
                .collect(Collectors.toSet());
        Map<Integer, Tags> tagMap;
        if (!allTagIds.isEmpty()) {
            tagMap = tagsService.listByIds(allTagIds).stream()
                    .collect(Collectors.toMap(Tags::getId, t -> t));
        } else {
            tagMap = Collections.emptyMap();
        }

        // 按文章ID分组标签
        Map<Integer, List<Tags>> articleTagsMap = allRelations.stream()
                .collect(Collectors.groupingBy(
                        ArticleTags::getArticleId,
                        Collectors.mapping(at -> tagMap.get(at.getTagId()), Collectors.toList())
                ));

        // 5. 组装 VO
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -7);
        Date weekAgo = cal.getTime();

        List<ArticleUserVO> voList = page.getRecords().stream().map(article -> {
            ArticleUserVO vo = new ArticleUserVO();
            BeanUtils.copyProperties(article, vo);

            // 分类 JSON 数据
            Categories category = categoryMap.get(article.getCategoryId());
            if (category != null) {
                ArticleUserVO.CategoryVO categoryVO = new ArticleUserVO.CategoryVO();
                categoryVO.setId(category.getId());
                categoryVO.setName(category.getName());
                vo.setCategory(categoryVO);
            }

            // 标签列表
            List<Tags> tags = articleTagsMap.getOrDefault(article.getId(), new ArrayList<>());
            List<TagVO> tagVOs = tags.stream().filter(Objects::nonNull).map(tag -> {
                TagVO t = new TagVO();
                t.setId(tag.getId());
                t.setName(tag.getName());
                t.setColor(tag.getColor());
                return t;
            }).collect(Collectors.toList());
            vo.setTags(tagVOs);

            // 判断是否为一周内发布的最新文章
            vo.setIsNew(article.getCreatedAt() != null && article.getCreatedAt().after(weekAgo));

            return vo;
        }).collect(Collectors.toList());

        // 6. 返回分页结果
        Page<ArticleUserVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        voPage.setRecords(voList);
        return voPage;
    }

    @Override
    public ArticleUserVO getArticleDetail(Integer id) {
        // 1. 查询文章（已发布、未删除）
        Articles article = this.lambdaQuery()
                .eq(Articles::getId, id)
                .eq(Articles::getStatus, 1)
                .eq(Articles::getIsDelete, 0)
                .one();
        if (article == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "文章不存在或已下架");
        }

        // 2. 阅读量 +1
        article.setReadCount(article.getReadCount() + 1);
        this.updateById(article);

        // 3. 组装 VO
        ArticleUserVO vo = new ArticleUserVO();
        BeanUtils.copyProperties(article, vo);

        // 分类
        if (article.getCategoryId() != null) {
            Categories category = categoriesService.getById(article.getCategoryId());
            if (category != null) {
                ArticleUserVO.CategoryVO categoryVO = new ArticleUserVO.CategoryVO();
                categoryVO.setId(category.getId());
                categoryVO.setName(category.getName());
                vo.setCategory(categoryVO);
            }
        }

        // 标签
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

        // 是否最新
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -7);
        vo.setIsNew(article.getCreatedAt() != null && article.getCreatedAt().after(cal.getTime()));

        return vo;
    }

    @Override
    public Page<ArticleSearchVO> searchArticles(ArticleUserQueryRequest request) {
        // 1. 构建查询条件：仅查询已发布、未删除的文章
        LambdaQueryWrapper<Articles> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Articles::getStatus, 1)
               .eq(Articles::getIsDelete, 0);

        // 关键词模糊搜索（标题 + 正文）
        if (StringUtils.isNotBlank(request.getKeyword())) {
            wrapper.and(w ->
                    w.like(Articles::getTitle, request.getKeyword())
                     .or()
                     .like(Articles::getContent, request.getKeyword())
            );
        }

        // 分类筛选
        if (request.getCategoryId() != null) {
            wrapper.eq(Articles::getCategoryId, request.getCategoryId());
        }

        // 标签筛选
        if (request.getTagId() != null) {
            List<ArticleTags> articleTags = articleTagsService.lambdaQuery()
                    .eq(ArticleTags::getTagId, request.getTagId())
                    .list();
            if (articleTags.isEmpty()) {
                return new Page<>(request.getCurrent(), request.getPageSize(), 0);
            }
            Set<Integer> articleIds = articleTags.stream()
                    .map(ArticleTags::getArticleId)
                    .collect(Collectors.toSet());
            wrapper.in(Articles::getId, articleIds);
        }

        wrapper.orderByDesc(Articles::getCreatedAt);

        // 2. 分页查询
        Page<Articles> page = this.page(
                new Page<>(request.getCurrent(), request.getPageSize()), wrapper);

        if (page.getRecords().isEmpty()) {
            return new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        }

        // 3. 批量加载分类和标签
        Set<Integer> categoryIds = page.getRecords().stream()
                .map(Articles::getCategoryId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Map<Integer, Categories> categoryMap;
        if (!categoryIds.isEmpty()) {
            categoryMap = categoriesService.listByIds(categoryIds).stream()
                    .collect(Collectors.toMap(Categories::getId, c -> c));
        } else {
            categoryMap = Collections.emptyMap();
        }

        List<Integer> articleIdList = page.getRecords().stream()
                .map(Articles::getId)
                .collect(Collectors.toList());

        List<ArticleTags> allRelations = articleTagsService.lambdaQuery()
                .in(ArticleTags::getArticleId, articleIdList)
                .list();

        Set<Integer> allTagIds = allRelations.stream()
                .map(ArticleTags::getTagId)
                .collect(Collectors.toSet());
        Map<Integer, Tags> tagMap;
        if (!allTagIds.isEmpty()) {
            tagMap = tagsService.listByIds(allTagIds).stream()
                    .collect(Collectors.toMap(Tags::getId, t -> t));
        } else {
            tagMap = Collections.emptyMap();
        }

        Map<Integer, List<Tags>> articleTagsMap = allRelations.stream()
                .collect(Collectors.groupingBy(
                        ArticleTags::getArticleId,
                        Collectors.mapping(at -> tagMap.get(at.getTagId()), Collectors.toList())
                ));

        // 4. 准备高亮关键词（按空格分词，按长度降序排列）
        List<String> keywords;
        if (StringUtils.isNotBlank(request.getKeyword())) {
            keywords = Arrays.stream(request.getKeyword().split("\\s+"))
                    .filter(StringUtils::isNotBlank)
                    .sorted(Comparator.comparingInt(String::length).reversed())
                    .collect(Collectors.toList());
        } else {
            keywords = Collections.emptyList();
        }

        // 5. 组装 VO
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -7);
        Date weekAgo = cal.getTime();

        List<ArticleSearchVO> voList = page.getRecords().stream().map(article -> {
            ArticleSearchVO vo = new ArticleSearchVO();
            BeanUtils.copyProperties(article, vo);

            // 高亮标题和简介
            vo.setTitleHighlight(highlightText(article.getTitle(), keywords));
            vo.setSummaryHighlight(highlightText(article.getSummary(), keywords));

            // 分类
            Categories category = categoryMap.get(article.getCategoryId());
            if (category != null) {
                ArticleUserVO.CategoryVO categoryVO = new ArticleUserVO.CategoryVO();
                categoryVO.setId(category.getId());
                categoryVO.setName(category.getName());
                vo.setCategory(categoryVO);
            }

            // 标签
            List<Tags> tags = articleTagsMap.getOrDefault(article.getId(), new ArrayList<>());
            List<TagVO> tagVOs = tags.stream().filter(Objects::nonNull).map(tag -> {
                TagVO t = new TagVO();
                t.setId(tag.getId());
                t.setName(tag.getName());
                t.setColor(tag.getColor());
                return t;
            }).collect(Collectors.toList());
            vo.setTags(tagVOs);

            // 是否为一周内发布
            vo.setIsNew(article.getCreatedAt() != null && article.getCreatedAt().after(weekAgo));

            return vo;
        }).collect(Collectors.toList());

        // 6. 返回分页结果
        Page<ArticleSearchVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        voPage.setRecords(voList);
        return voPage;
    }

    /**
     * 对文本中的关键词添加高亮标记（<em class="highlight">）
     */
    private String highlightText(String text, List<String> keywords) {
        if (StringUtils.isBlank(text) || keywords == null || keywords.isEmpty()) {
            return text;
        }

        // 先对文本做 HTML 转义，防止 XSS
        String escaped = escapeHtml(text);

        String result = escaped;
        for (String kw : keywords) {
            String escapedKw = escapeHtml(kw);
            Pattern pattern = Pattern.compile(Pattern.quote(escapedKw), Pattern.CASE_INSENSITIVE);
            String prefix = Matcher.quoteReplacement("<em class=\"highlight\">");
            String suffix = Matcher.quoteReplacement("</em>");
            result = pattern.matcher(result).replaceAll(prefix + "$0" + suffix);
        }

        return result;
    }

    /**
     * HTML 转义（防止 XSS）
     */
    private static String escapeHtml(String text) {
        if (text == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            switch (c) {
                case '&':
                    sb.append("&amp;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '"':
                    sb.append("&quot;");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }
}




