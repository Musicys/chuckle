package com.yupi.springbootinit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.springbootinit.model.domain.Articles;
import com.yupi.springbootinit.model.dto.article.ArticleUserQueryRequest;
import com.yupi.springbootinit.model.vo.ArticleUserVO;

/**
* @author 黎旺
* @description 针对表【articles(文章表)】的数据库操作Service
* @createDate 2026-07-07 14:07:09
*/
public interface ArticlesService extends IService<Articles> {

    /**
     * 分页获取已发布文章列表（用户端）
     */
    Page<ArticleUserVO> getArticleUserPage(ArticleUserQueryRequest request);

    /**
     * 获取文章详情（用户端，含正文内容，阅读量+1）
     */
    ArticleUserVO getArticleDetail(Integer id);
}
