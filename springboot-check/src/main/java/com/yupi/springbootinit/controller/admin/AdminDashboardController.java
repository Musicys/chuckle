package com.yupi.springbootinit.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.domain.Articles;
import com.yupi.springbootinit.model.domain.Comments;
import com.yupi.springbootinit.model.domain.FriendLinks;
import com.yupi.springbootinit.model.domain.Images;
import com.yupi.springbootinit.model.vo.DashboardStatsVO;
import com.yupi.springbootinit.model.vo.VisitStatsVO;
import com.yupi.springbootinit.service.*;
import com.yupi.springbootinit.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.var;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 管理端-仪表盘
 */
@RestController
@RequestMapping("/admin/dashboard")
@Api(tags = "管理端-仪表盘")
public class AdminDashboardController {

    @Resource
    private ArticlesService articlesService;

    @Resource
    private CommentsService commentsService;

    @Resource
    private PortfolioService portfolioService;

    @Resource
    private FriendLinksService friendLinksService;

    @Resource
    private VisitLogsService visitLogsService;

    @Resource
    private DailyStatsService dailyStatsService;

    private void checkAdmin(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (!JwtUtils.verify(authHeader.substring(7))) {
            throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
        }
    }

    /**
     * 获取仪表盘统计数据
     */
    @GetMapping("/stats")
    @ApiOperation(value = "获取仪表盘统计数据")
    public BaseResponse<DashboardStatsVO> getDashboardStats(HttpServletRequest request) {
        checkAdmin(request);

        DashboardStatsVO vo = new DashboardStatsVO();

        // 文章总数
        Long articleCount = articlesService.count(
                new LambdaQueryWrapper<Articles>().eq(Articles::getIsDelete, 0));
        vo.setArticleCount(articleCount.intValue());

        // 评论总数
        Long commentCount = commentsService.count();
        vo.setCommentCount(commentCount.intValue());

        // 作品总数
        Long portfolioCount = portfolioService.count(
                new LambdaQueryWrapper<Images.Portfolio>().eq(Images.Portfolio::getStatus, 1));
        vo.setPortfolioCount(portfolioCount.intValue());

        // 友链总数
        Long friendLinkCount = friendLinksService.count(
                new LambdaQueryWrapper<FriendLinks>().eq(FriendLinks::getStatus, 1));
        vo.setFriendLinkCount(friendLinkCount.intValue());

        // 访问统计
        var visitStats = visitLogsService.getVisitStats();
        vo.setTodayPv(visitStats.getTodayPv());
        vo.setTodayUv(visitStats.getTodayUv());
        vo.setTotalPv(visitStats.getTotalPv());

        // 最近5篇文章
        List<DashboardStatsVO.RecentArticle> recentArticles = articlesService.lambdaQuery()
                .eq(Articles::getIsDelete, 0)
                .orderByDesc(Articles::getCreatedAt)
                .last("LIMIT 5")
                .list()
                .stream()
                .map(article -> {
                    DashboardStatsVO.RecentArticle item = new DashboardStatsVO.RecentArticle();
                    item.setId(article.getId().longValue());
                    item.setTitle(article.getTitle());
                    item.setStatus(article.getStatus());
                    item.setCreatedAt(article.getCreatedAt().toString());
                    return item;
                })
                .collect(Collectors.toList());
        vo.setRecentArticles(recentArticles);

        // 最近5条评论
        List<DashboardStatsVO.RecentComment> recentComments = commentsService.lambdaQuery()
                .orderByDesc(Comments::getCreatedAt)
                .last("LIMIT 5")
                .list()
                .stream()
                .map(comment -> {
                    DashboardStatsVO.RecentComment item = new DashboardStatsVO.RecentComment();
                    item.setId(comment.getId().longValue());
                    item.setNickname(comment.getNickname());
                    item.setContent(comment.getContent());
                    item.setCreatedAt(comment.getCreatedAt().toString());
                    return item;
                })
                .collect(Collectors.toList());
        vo.setRecentComments(recentComments);

        return ResultUtils.success(vo);
    }

    /**
     * 获取访问趋势数据（最近7天）
     */
    @GetMapping("/visit-trend")
    @ApiOperation(value = "获取访问趋势数据")
    public BaseResponse<Map<String, Object>> getVisitTrend(HttpServletRequest request) {
        checkAdmin(request);

        Map<String, Object> result = new HashMap<>();

        // 获取最近7天的统计数据
        Date sevenDaysAgo = new Date(System.currentTimeMillis() - 7L * 24 * 60 * 60 * 1000);
        List<Map<String, Object>> trendData = dailyStatsService.lambdaQuery()
                .ge(com.yupi.springbootinit.model.domain.DailyStats::getStatDate, sevenDaysAgo)
                .orderByAsc(com.yupi.springbootinit.model.domain.DailyStats::getStatDate)
                .list()
                .stream()
                .map(stats -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("date", stats.getStatDate().toString());
                    item.put("pv", stats.getPv());
                    item.put("uv", stats.getUv());
                    return item;
                })
                .collect(Collectors.toList());

        result.put("trend", trendData);
        return ResultUtils.success(result);
    }

    /**
     * 获取内容统计数据
     */
    @GetMapping("/content-stats")
    @ApiOperation(value = "获取内容统计数据")
    public BaseResponse<Map<String, Object>> getContentStats(HttpServletRequest request) {
        checkAdmin(request);

        Map<String, Object> result = new HashMap<>();

        // 按状态统计文章数
        Map<String, Long> articleStatusStats = new HashMap<>();
        List<Articles> allArticles = articlesService.lambdaQuery()
                .eq(Articles::getIsDelete, 0)
                .list();
        articleStatusStats.put("published", allArticles.stream()
                .filter(a -> a.getStatus() == 1).count());
        articleStatusStats.put("draft", allArticles.stream()
                .filter(a -> a.getStatus() == 0).count());
        result.put("articleStatus", articleStatusStats);

        // 按分类统计文章数（Top 5）
        List<Map<String, Object>> categoryStats = articlesService.listMaps(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Articles>()
                        .select("category_id", "COUNT(*) as count")
                        .eq("is_delete", 0)
                        .groupBy("category_id")
                        .orderByDesc("count")
                        .last("LIMIT 5")
        );
        result.put("categoryStats", categoryStats);

        return ResultUtils.success(result);
    }
}
