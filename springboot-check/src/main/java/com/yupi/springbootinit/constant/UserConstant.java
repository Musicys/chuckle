package com.yupi.springbootinit.constant;

/**
 * 用户常量
 */
public interface UserConstant {

    /**
     * 用户登录态 Key
     */
    String USER_LOGIN_STATE = "user_login";

    /**
     * JWT Token 请求头
     */
    String AUTHORIZATION_HEADER = "Authorization";

    /**
     * Token 前缀
     */
    String TOKEN_PREFIX = "Bearer ";

    // ========== 角色 ==========

    /**
     * 普通用户
     */
    Integer ROLE_USER = 0;

    /**
     * 管理员
     */
    Integer ROLE_ADMIN = 1;

    /**
     * 超级管理员
     */
    Integer ROLE_SUPER_ADMIN = 3;

    // ========== 状态 ==========

    /**
     * 禁用
     */
    Integer STATUS_DISABLED = 0;

    /**
     * 正常
     */
    Integer STATUS_NORMAL = 1;

    // ========== 全通标识 ==========

    /**
     * 非全通
     */
    Integer FULL_ACCESS_NO = 0;

    /**
     * 全通
     */
    Integer FULL_ACCESS_YES = 1;

    // ========== 用户名/密码规则 ==========

    /**
     * 用户名最小长度
     */
    int USERNAME_MIN_LENGTH = 4;

    /**
     * 用户名最大长度
     */
    int USERNAME_MAX_LENGTH = 20;

    /**
     * 密码最小长度
     */
    int PASSWORD_MIN_LENGTH = 6;

    /**
     * 密码最大长度
     */
    int PASSWORD_MAX_LENGTH = 32;
}
