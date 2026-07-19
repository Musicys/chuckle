package com.yupi.springbootinit.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Map;

/**
 * 首页数据视图
 */
@ApiModel(description = "首页数据视图")
public class HomepageVO {

    @ApiModelProperty(value = "最近更新的文章列表")
    private List<RecentArticleVO> recentArticles;

    @ApiModelProperty(value = "标签及其文章数量")
    private Map<String, Integer> tags;

    @ApiModelProperty(value = "按年份划分的文章统计")
    private Map<String, Integer> articlesByYear;

    @ApiModelProperty(value = "热门文章列表（按阅读量排序）")
    private List<HotArticleVO> hotArticles;

    @ApiModelProperty(value = "网站基本信息")
    private SiteInfoVO siteInfo;

    public List<RecentArticleVO> getRecentArticles() {
        return recentArticles;
    }

    public void setRecentArticles(List<RecentArticleVO> recentArticles) {
        this.recentArticles = recentArticles;
    }

    public Map<String, Integer> getTags() {
        return tags;
    }

    public void setTags(Map<String, Integer> tags) {
        this.tags = tags;
    }

    public Map<String, Integer> getArticlesByYear() {
        return articlesByYear;
    }

    public void setArticlesByYear(Map<String, Integer> articlesByYear) {
        this.articlesByYear = articlesByYear;
    }

    public List<HotArticleVO> getHotArticles() {
        return hotArticles;
    }

    public void setHotArticles(List<HotArticleVO> hotArticles) {
        this.hotArticles = hotArticles;
    }

    public SiteInfoVO getSiteInfo() {
        return siteInfo;
    }

    public void setSiteInfo(SiteInfoVO siteInfo) {
        this.siteInfo = siteInfo;
    }

    @ApiModel(description = "最近文章")
    public static class RecentArticleVO {
        @ApiModelProperty(value = "文章ID")
        private Integer id;

        @ApiModelProperty(value = "文章标题")
        private String title;

        @ApiModelProperty(value = "封面URL")
        private String cover;

        @ApiModelProperty(value = "发布时间")
        private String time;

        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getCover() { return cover; }
        public void setCover(String cover) { this.cover = cover; }
        public String getTime() { return time; }
        public void setTime(String time) { this.time = time; }
    }

    @ApiModel(description = "热门文章")
    public static class HotArticleVO {
        @ApiModelProperty(value = "文章ID")
        private Integer id;

        @ApiModelProperty(value = "文章标题")
        private String title;

        @ApiModelProperty(value = "封面URL")
        private String cover;

        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getCover() { return cover; }
        public void setCover(String cover) { this.cover = cover; }
    }

    @ApiModel(description = "网站基本信息")
    public static class SiteInfoVO {
        @ApiModelProperty(value = "网站运行起始时间")
        private String siteUptime;

        @ApiModelProperty(value = "文章总篇数")
        private Integer totalArticles;

        @ApiModelProperty(value = "最后更新时间")
        private String lastUpdated;

        @ApiModelProperty(value = "文章总字数")
        private Integer totalWords;

        public String getSiteUptime() { return siteUptime; }
        public void setSiteUptime(String siteUptime) { this.siteUptime = siteUptime; }
        public Integer getTotalArticles() { return totalArticles; }
        public void setTotalArticles(Integer totalArticles) { this.totalArticles = totalArticles; }
        public String getLastUpdated() { return lastUpdated; }
        public void setLastUpdated(String lastUpdated) { this.lastUpdated = lastUpdated; }
        public Integer getTotalWords() { return totalWords; }
        public void setTotalWords(Integer totalWords) { this.totalWords = totalWords; }
    }
}
