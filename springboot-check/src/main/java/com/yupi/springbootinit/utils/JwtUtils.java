package com.yupi.springbootinit.utils;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 令牌工具类
 */
public class JwtUtils {

    /**
     * JWT 签名密钥
     */
    private static final String SECRET = "voluntary-system-secret-key-2026";

    /**
     * Token 有效期（7天），单位：毫秒
     */
    private static final long EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;

    /**
     * 载荷 Key：用户 ID
     */
    private static final String CLAIM_USER_ID = "userId";

    /**
     * 载荷 Key：用户角色
     */
    private static final String CLAIM_ROLE = "role";

    /**
     * 生成 JWT Token
     *
     * @param userId 用户 ID
     * @param role   用户角色（0=普通用户 1=管理员）
     * @return JWT Token 字符串
     */
    public static String generateToken(Long userId, Integer role) {
        Map<String, Object> payload = new HashMap<>();
        // 签发时间
        payload.put(JWT.ISSUED_AT, System.currentTimeMillis());
        // 过期时间
        payload.put(JWT.EXPIRES_AT, System.currentTimeMillis() + EXPIRE_TIME);
        // 用户 ID
        payload.put(CLAIM_USER_ID, userId);
        // 用户角色
        payload.put(CLAIM_ROLE, role);
        return JWTUtil.createToken(payload, SECRET.getBytes());
    }

    /**
     * 验证 Token 是否有效（签名正确且未过期）
     *
     * @param token JWT Token
     * @return true=有效 false=无效/过期
     */
    public static boolean verify(String token) {
        try {
            return JWTUtil.verify(token, SECRET.getBytes());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从 Token 中获取用户 ID
     *
     * @param token JWT Token
     * @return 用户 ID
     */
    public static Long getUserId(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        return Long.valueOf(jwt.getPayload(CLAIM_USER_ID).toString());
    }

    /**
     * 从 Token 中获取用户角色
     *
     * @param token JWT Token
     * @return 角色值（0=普通用户 1=管理员）
     */
    public static Integer getRole(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        return Integer.valueOf(jwt.getPayload(CLAIM_ROLE).toString());
    }
}
