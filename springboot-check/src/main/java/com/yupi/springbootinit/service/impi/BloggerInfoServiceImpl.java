package com.yupi.springbootinit.service.impi;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.model.domain.BloggerInfo;
import com.yupi.springbootinit.service.BloggerInfoService;
import com.yupi.springbootinit.mapper.BloggerInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author 黎旺
* @description 针对表【blogger_info(博主信息表)】的数据库操作Service实现
* @createDate 2026-07-07 14:07:09
*/
@Service
public class BloggerInfoServiceImpl extends ServiceImpl<BloggerInfoMapper, BloggerInfo>
    implements BloggerInfoService{

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setUsed(Integer id) {
        baseMapper.update(null, new UpdateWrapper<BloggerInfo>().setSql("is_used = 0"));
        baseMapper.update(null, new UpdateWrapper<BloggerInfo>()
                .eq("id", id)
                .setSql("is_used = 1"));
    }
}




