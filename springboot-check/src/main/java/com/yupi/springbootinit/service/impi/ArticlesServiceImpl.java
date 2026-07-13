package com.yupi.springbootinit.service.impi;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.model.domain.Articles;
import com.yupi.springbootinit.service.ArticlesService;
import com.yupi.springbootinit.mapper.ArticlesMapper;
import org.springframework.stereotype.Service;

/**
* @author 黎旺
* @description 针对表【articles(文章表)】的数据库操作Service实现
* @createDate 2026-07-07 14:07:09
*/
@Service
public class ArticlesServiceImpl extends ServiceImpl<ArticlesMapper, Articles>
    implements ArticlesService{

}




