package com.yupi.springbootinit.service;

import com.yupi.springbootinit.model.domain.BloggerInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 黎旺
* @description 针对表【blogger_info(博主信息表)】的数据库操作Service
* @createDate 2026-07-07 14:07:09
*/
public interface BloggerInfoService extends IService<BloggerInfo> {

    /**
     * 切换配置使用状态
     * 将指定ID的记录设为启用（is_used=1），其他所有记录设为不启用（is_used=0）
     *
     * @param id 要启用的博主信息ID
     */
    void setUsed(Integer id);
}
