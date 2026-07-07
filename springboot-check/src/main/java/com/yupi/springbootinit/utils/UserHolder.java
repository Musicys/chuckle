package com.yupi.springbootinit.utils;

/**
 * 当前登录用户持有者（ThreadLocal）
 */
public class UserHolder {

    private static final ThreadLocal<Users> USER_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 设置当前线程的用户
     */
    public static void set(Users user) {
        USER_THREAD_LOCAL.set(user);
    }

    /**
     * 获取当前线程的用户
     */
    public static Users get() {
        return USER_THREAD_LOCAL.get();
    }

    /**
     * 移除当前线程的用户（防止内存泄漏）
     */
    public static void remove() {
        USER_THREAD_LOCAL.remove();
    }
}
