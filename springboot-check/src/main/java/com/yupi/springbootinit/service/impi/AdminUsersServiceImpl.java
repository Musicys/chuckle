package com.yupi.springbootinit.service.impi;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.model.domain.AdminUsers;
import com.yupi.springbootinit.service.AdminUsersService;
import com.yupi.springbootinit.mapper.AdminUsersMapper;
import org.springframework.stereotype.Service;

/**
* @author 黎旺
* @description 针对表【admin_users(管理员用户表)】的数据库操作Service实现
*/
@Service
public class AdminUsersServiceImpl extends ServiceImpl<AdminUsersMapper, AdminUsers>
    implements AdminUsersService {

}
