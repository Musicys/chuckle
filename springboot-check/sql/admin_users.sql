-- ============================================================
-- 管理员用户表
-- 管理端后台登录使用，支持 JWT Token 认证
-- ============================================================

CREATE TABLE IF NOT EXISTS `admin_users` (
    `id`              INT UNSIGNED  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username`        VARCHAR(50)   NOT NULL COMMENT '用户名（唯一）',
    `password`        VARCHAR(255)  NOT NULL COMMENT '密码（BCrypt加密）',
    `nickname`        VARCHAR(50)   NOT NULL DEFAULT '' COMMENT '显示昵称',
    `avatar`          VARCHAR(500)  NOT NULL DEFAULT '' COMMENT '头像URL',
    `email`           VARCHAR(200)  NOT NULL DEFAULT '' COMMENT '邮箱',
    `role`            TINYINT       NOT NULL DEFAULT 1 COMMENT '角色（1=管理员 3=超级管理员）',
    `status`          TINYINT       NOT NULL DEFAULT 1 COMMENT '状态（0=禁用 1=正常）',
    `refresh_token`   VARCHAR(500)  NOT NULL DEFAULT '' COMMENT '当前刷新令牌',
    `last_login_ip`   VARCHAR(45)   NOT NULL DEFAULT '' COMMENT '最后登录IP',
    `last_login_time` DATETIME      DEFAULT NULL COMMENT '最后登录时间',
    `created_at`      DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`      DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员用户表';


-- ============================================================
-- 测试数据
-- 密码统一为 123456（BCrypt 加密）
-- 生成方式：cn.hutool.crypto.digest.BCrypt.hashpw("123456")
-- ============================================================
INSERT IGNORE INTO `admin_users` (`id`, `username`, `password`, `nickname`, `email`, `role`, `status`)
VALUES (1,
        'Super',
        '$2a$10$7GVyob5ffgiK/AM7/.PDcuTmU8n7.GAyU/z9WHPOgTX0vUaXKnMEm',
        '超级管理员',
        'super@example.com',
        3,
        1);

INSERT IGNORE INTO `admin_users` (`id`, `username`, `password`, `nickname`, `email`, `role`, `status`)
VALUES (2,
        'Admin',
        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
        '系统管理员',
        'admin@example.com',
        1,
        1);

INSERT IGNORE INTO `admin_users` (`id`, `username`, `password`, `nickname`, `email`, `role`, `status`)
VALUES (3,
        'User',
        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
        '普通运营',
        'user@example.com',
        1,
        1);
