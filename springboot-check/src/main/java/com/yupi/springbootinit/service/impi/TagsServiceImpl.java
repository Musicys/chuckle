package com.yupi.springbootinit.service.impi;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.model.domain.Tags;
import com.yupi.springbootinit.service.TagsService;
import com.yupi.springbootinit.mapper.TagsMapper;
import org.springframework.stereotype.Service;

/**
* @author 黎旺
* @description 针对表【tags(标签表)】的数据库操作Service实现
* @createDate 2026-07-07 14:07:09
*/
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags>
    implements TagsService{

}




