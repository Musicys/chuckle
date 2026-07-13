package com.yupi.springbootinit.service.impi;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.model.domain.SystemSettings;
import com.yupi.springbootinit.service.SystemSettingsService;
import com.yupi.springbootinit.mapper.SystemSettingsMapper;
import org.springframework.stereotype.Service;

/**
* @author 黎旺
* @description 针对表【system_settings(系统设置表)】的数据库操作Service实现
* @createDate 2026-07-07 14:07:09
*/
@Service
public class SystemSettingsServiceImpl extends ServiceImpl<SystemSettingsMapper, SystemSettings>
    implements SystemSettingsService{

}




