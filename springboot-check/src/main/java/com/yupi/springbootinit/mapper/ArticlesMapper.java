package com.yupi.springbootinit.mapper;

import com.yupi.springbootinit.model.domain.Articles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
* @author 黎旺
* @description 针对表【articles(文章表)】的数据库操作Mapper
* @createDate 2026-07-07 14:07:09
* @Entity com.yupi.springbootinit.model.domain.Articles
*/
public interface ArticlesMapper extends BaseMapper<Articles> {

    /**
     * 按年份统计文章数量（仅已发布、未删除）
     */
    List<Map<String, Object>> listArticleCountByYear();

    /**
     * 获取站点统计信息（上线时间、文章数、最后更新、总字数）
     */
    Map<String, Object> getSiteInfo();
}




