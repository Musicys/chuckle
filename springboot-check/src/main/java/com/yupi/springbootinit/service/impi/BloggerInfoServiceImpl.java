package com.yupi.springbootinit.service.impi;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.mapper.ArticlesMapper;
import com.yupi.springbootinit.mapper.TagsMapper;
import com.yupi.springbootinit.model.domain.Articles;
import com.yupi.springbootinit.model.domain.BloggerInfo;
import com.yupi.springbootinit.service.BloggerInfoService;
import com.yupi.springbootinit.mapper.BloggerInfoMapper;
import com.yupi.springbootinit.model.vo.HomepageVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author 黎旺
* @description 针对表【blogger_info(博主信息表)】的数据库操作Service实现
* @createDate 2026-07-07 14:07:09
*/
@Service
public class BloggerInfoServiceImpl extends ServiceImpl<BloggerInfoMapper, BloggerInfo>
    implements BloggerInfoService{

    @Resource
    private ArticlesMapper articlesMapper;

    @Resource
    private TagsMapper tagsMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setUsed(Integer id) {
        baseMapper.update(null, new UpdateWrapper<BloggerInfo>().setSql("is_used = 0"));
        baseMapper.update(null, new UpdateWrapper<BloggerInfo>()
                .eq("id", id)
                .setSql("is_used = 1"));
    }

    @Override
    public HomepageVO getHomepage() {
        HomepageVO vo = new HomepageVO();

        // 1. 最近文章（取前10篇已发布、未删除）
        List<HomepageVO.RecentArticleVO> recentArticles = getRecentArticles();
        vo.setRecentArticles(recentArticles);

        // 2. 标签及其文章数量
        Map<String, Integer> tags = getTagCounts();
        vo.setTags(tags);

        // 3. 按年份统计文章
        Map<String, Integer> articlesByYear = getArticlesByYear();
        vo.setArticlesByYear(articlesByYear);

        // 4. 热门文章（按阅读量排序，取前10篇已发布、未删除）
        List<HomepageVO.HotArticleVO> hotArticles = getHotArticles();
        vo.setHotArticles(hotArticles);

        // 5. 站点概览
        HomepageVO.SiteInfoVO siteInfo = getSiteInfo();
        vo.setSiteInfo(siteInfo);

        return vo;
    }

    private List<HomepageVO.RecentArticleVO> getRecentArticles() {
        LambdaQueryWrapper<Articles> wrapper = new LambdaQueryWrapper<Articles>()
                .eq(Articles::getStatus, 1)
                .eq(Articles::getIsDelete, 0)
                .orderByDesc(Articles::getCreatedAt)
                .last("LIMIT 5");

        List<Articles> list = articlesMapper.selectList(wrapper);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return list.stream().map(a -> {
            HomepageVO.RecentArticleVO r = new HomepageVO.RecentArticleVO();
            r.setId(a.getId());
            r.setTitle(a.getTitle());
            r.setCover(a.getCover());
            r.setTime(a.getCreatedAt() != null ? sdf.format(a.getCreatedAt()) : "");
            return r;
        }).collect(Collectors.toList());
    }

    private Map<String, Integer> getTagCounts() {
        List<Map<String, Object>> tagCounts = tagsMapper.listTagCounts();
        Map<String, Integer> result = new LinkedHashMap<>();
        for (Map<String, Object> row : tagCounts) {
            result.put((String) row.get("name"), ((Number) row.get("article_count")).intValue());
        }
        return result;
    }

    private Map<String, Integer> getArticlesByYear() {
        List<Map<String, Object>> rows = articlesMapper.listArticleCountByYear();
        Map<String, Integer> result = new LinkedHashMap<>();
        for (Map<String, Object> row : rows) {
            result.put(String.valueOf(row.get("year")), ((Number) row.get("article_count")).intValue());
        }
        return result;
    }

    private List<HomepageVO.HotArticleVO> getHotArticles() {
        LambdaQueryWrapper<Articles> wrapper = new LambdaQueryWrapper<Articles>()
                .eq(Articles::getStatus, 1)
                .eq(Articles::getIsDelete, 0)
                .orderByDesc(Articles::getReadCount)
                .last("LIMIT 5");

        List<Articles> list = articlesMapper.selectList(wrapper);

        return list.stream().map(a -> {
            HomepageVO.HotArticleVO h = new HomepageVO.HotArticleVO();
            h.setId(a.getId());
            h.setTitle(a.getTitle());
            h.setCover(a.getCover());
            return h;
        }).collect(Collectors.toList());
    }

    private HomepageVO.SiteInfoVO getSiteInfo() {
        Map<String, Object> info = articlesMapper.getSiteInfo();
        HomepageVO.SiteInfoVO siteInfo = new HomepageVO.SiteInfoVO();

        if (info != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            siteInfo.setSiteUptime(formatDate(info.get("site_uptime"), sdf));
            siteInfo.setTotalArticles(info.get("total_articles") != null ? ((Number) info.get("total_articles")).intValue() : 0);
            siteInfo.setLastUpdated(formatDate(info.get("last_updated"), sdf));
            siteInfo.setTotalWords(info.get("total_words") != null ? ((Number) info.get("total_words")).intValue() : 0);
        }

        return siteInfo;
    }

    /**
     * 兼容 java.util.Date 和 java.time.LocalDateTime 两种日期类型
     */
    private String formatDate(Object obj, SimpleDateFormat sdf) {
        if (obj == null) return "";
        if (obj instanceof Date) {
            return sdf.format((Date) obj);
        }
        if (obj instanceof LocalDateTime) {
            return ((LocalDateTime) obj).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return String.valueOf(obj).substring(0, 10);
    }
}




