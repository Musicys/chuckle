package com.yupi.springbootinit.aop;

import cn.hutool.core.util.StrUtil;
import com.yupi.springbootinit.annotation.AuthCheck;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.constant.UserConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.service.UsersService;
import com.yupi.springbootinit.utils.JwtUtils;
import com.yupi.springbootinit.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 权限校验 AOP 拦截器
 * 对标注 @AuthCheck 的方法进行 JWT 解析 + 角色校验
 */
@Aspect
@Component
@Slf4j
public class AuthInterceptor {

    @Resource
    private UsersService usersService;

    /**
     * 拦截带有 @AuthCheck 注解的方法
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        // 获取当前请求
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        // 提取 Token
        String authHeader = request.getHeader(UserConstant.AUTHORIZATION_HEADER);
        if (StrUtil.isBlank(authHeader) || !authHeader.startsWith(UserConstant.TOKEN_PREFIX)) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        String token = authHeader.substring(UserConstant.TOKEN_PREFIX.length());

        // 验证 Token 有效性
        if (!JwtUtils.verify(token)) {
            throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
        }

        // 从 Token 中获取用户 ID，加载用户
        Long userId = JwtUtils.getUserId(token);
        Users user = usersService.getById(userId);
        if (user == null || user.getIsDeleted() == 1) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "用户不存在");
        }
        if (UserConstant.STATUS_DISABLED.equals(user.getStatus())) {
            throw new BusinessException(ErrorCode.ACCOUNT_DISABLED);
        }

        // 校验角色
        String mustRole = authCheck.mustRole();
        if (StrUtil.isNotBlank(mustRole)) {
            Integer roleRequired = "admin".equals(mustRole) ? UserConstant.ROLE_ADMIN : UserConstant.ROLE_USER;
            boolean hasPermission;
            if (roleRequired.equals(UserConstant.ROLE_ADMIN)) {
                // 超级管理员(3)和管理员(1)均可通过 admin 权限校验
                hasPermission = user.getRole().equals(UserConstant.ROLE_ADMIN)
                        || user.getRole().equals(UserConstant.ROLE_SUPER_ADMIN);
            } else {
                hasPermission = roleRequired.equals(user.getRole());
            }
            if (!hasPermission) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "需要管理员权限");
            }
        }

        // 设置当前用户到 ThreadLocal，执行业务方法
        UserHolder.set(user);
        try {
            return joinPoint.proceed();
        } finally {
            UserHolder.remove();
        }
    }
}
