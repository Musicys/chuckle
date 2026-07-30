package com.yupi.springbootinit.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 仪表盘统计数据视图
 */
@ApiModel(description = "仪表盘统计数据")
public class DashboardStatsVO {

    @ApiModelProperty(value = "文章总数")
    private Integer articleCount;

    @ApiModelProperty(value = "评论总数")
    private Integer commentCount;

    @ApiModelProperty(value = "作品总数")
    private Integer portfolioCount;

    @ApiModelProperty(value = "友链总数")
    private Integer friendLinkCount;

    @ApiModelProperty(value = "今日 PV")
    private Integer todayPv;

    @ApiModelProperty(value = "今日 UV")
    private Integer todayUv;

    @ApiModelProperty(value = "总访问量")
    private Integer totalPv;

    @ApiModelProperty(value = "最近文章")
    private List<RecentArticle> recentArticles;

    @ApiModelProperty(value = "最近评论")
    private List<RecentComment> recentComments;

    public Integer getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getPortfolioCount() {
        return portfolioCount;
    }

    public void setPortfolioCount(Integer portfolioCount) {
        this.portfolioCount = portfolioCount;
    }

    public Integer getFriendLinkCount() {
        return friendLinkCount;
    }

    public void setFriendLinkCount(Integer friendLinkCount) {
        this.friendLinkCount = friendLinkCount;
    }

    public Integer getTodayPv() {
        return todayPv;
    }

    public void setTodayPv(Integer todayPv) {
        this.todayPv = todayPv;
    }

    public Integer getTodayUv() {
        return todayUv;
    }

    public void setTodayUv(Integer todayUv) {
        this.todayUv = todayUv;
    }

    public Integer getTotalPv() {
        return totalPv;
    }

    public void setTotalPv(Integer totalPv) {
        this.totalPv = totalPv;
    }

    public List<RecentArticle> getRecentArticles() {
        return recentArticles;
    }

    public void setRecentArticles(List<RecentArticle> recentArticles) {
        this.recentArticles = recentArticles;
    }

    public List<RecentComment> getRecentComments() {
        return recentComments;
    }

    public void setRecentComments(List<RecentComment> recentComments) {
        this.recentComments = recentComments;
    }

    /**
     * 最近文章
     */
    @ApiModel(description = "最近文章")
    public static class RecentArticle {

        @ApiModelProperty(value = "文章 ID")
        private Long id;

        @ApiModelProperty(value = "文章标题")
        private String title;

        @ApiModelProperty(value = "状态：0-草稿，1-已发布")
        private Integer status;

        @ApiModelProperty(value = "创建时间")
        private String createdAt;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }

    /**
     * 最近评论
     */
    @ApiModel(description = "最近评论")
    public static class RecentComment {

        @ApiModelProperty(value = "评论 ID")
        private Long id;

        @ApiModelProperty(value = "评论者昵称")
        private String nickname;

        @ApiModelProperty(value = "评论内容")
        private String content;

        @ApiModelProperty(value = "创建时间")
        private String createdAt;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }
}
