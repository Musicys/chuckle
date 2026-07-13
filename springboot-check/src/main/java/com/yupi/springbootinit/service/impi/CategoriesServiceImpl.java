package com.yupi.springbootinit.service.impi;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.model.domain.Categories;
import com.yupi.springbootinit.service.CategoriesService;
import com.yupi.springbootinit.mapper.CategoriesMapper;
import org.springframework.stereotype.Service;

/**
* @author 黎旺
* @description 针对表【categories(文章分类表)】的数据库操作Service实现
* @createDate 2026-07-07 14:07:09
*/
@Service
public class CategoriesServiceImpl extends ServiceImpl<CategoriesMapper, Categories>
    implements CategoriesService{

}




