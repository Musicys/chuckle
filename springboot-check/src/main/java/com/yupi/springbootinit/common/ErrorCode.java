package com.yupi.springbootinit.common;

/**
 * 自定义错误码
 */
public enum ErrorCode {

    SUCCESS(0, "ok"),
    PARAMS_ERROR(40000, "请求参数错误"),
    NOT_LOGIN_ERROR(40100, "未登录"),
    NO_AUTH_ERROR(40101, "无权限"),
    NOT_FOUND_ERROR(40400, "请求数据不存在"),
    FORBIDDEN_ERROR(40300, "禁止访问"),
    SYSTEM_ERROR(50000, "系统内部异常"),
    OPERATION_ERROR(50001, "操作失败"),

    // ========== 用户模块 ==========
    USERNAME_EXIST(40001, "用户名已存在"),
    USER_NOT_EXIST(40002, "用户不存在"),
    PASSWORD_ERROR(40003, "密码错误"),
    ACCOUNT_DISABLED(40004, "账号已被禁用"),
    PHONE_EXIST(40005, "手机号已被绑定"),
    EMAIL_EXIST(40006, "邮箱已被绑定"),
    TOKEN_EXPIRED(40102, "Token已过期，请重新登录");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
