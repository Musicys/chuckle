package com.yupi.springbootinit.service.impi;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.model.domain.ArticleTags;
import com.yupi.springbootinit.service.ArticleTagsService;
import com.yupi.springbootinit.mapper.ArticleTagsMapper;
import org.springframework.stereotype.Service;

/**
* @author 黎旺
* @description 针对表【article_tags(文章-标签关联表)】的数据库操作Service实现
* @createDate 2026-07-07 14:07:09
*/
@Service
public class ArticleTagsServiceImpl extends ServiceImpl<ArticleTagsMapper, ArticleTags>
    implements ArticleTagsService{

}




