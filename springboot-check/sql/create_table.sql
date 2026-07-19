-- ============================================================
-- 个人博客系统 · 数据库表结构
-- 基于 需求文档 设计
-- ============================================================

CREATE DATABASE IF NOT EXISTS `check_blog` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `check_blog`;

-- 1. 博主信息表（个人设置）

create table blogger_info
(
    id           int unsigned auto_increment comment '主键'
        primary key,
    avatar       varchar(500)  default ''                not null comment '头像URL',
    nickname     varchar(50)   default ''                not null comment '昵称',
    blog_title   varchar(200)  default ''                not null comment '博客标题',
    qq           varchar(50)   default ''                not null comment 'QQ',
    github       varchar(500)  default ''                not null comment 'GitHub 链接',
    gitee        varchar(500)  default ''                not null comment 'Gitee 链接',
    bilibili     varchar(500)  default ''                not null comment 'Bilibili 链接',
    birth_date   date                                    null comment '出生年月',
    gender       tinyint       default 0                 not null comment '性别（0=保密 1=男 2=女）',
    occupation   varchar(100)  default ''                not null comment '现职业',
    tags         varchar(500)  default ''                not null comment '个性标签（JSON数组）',
    hobbies      varchar(500)  default ''                not null comment '爱好（JSON数组）',
    games        varchar(1000) default ''                not null comment '爱好游戏（JSON数组）',
    latitude     decimal(10, 7)                          null comment '纬度',
    longitude    decimal(10, 7)                          null comment '经度',
    address      varchar(200)  default ''                not null comment '文字地址',
    announcement text                                    not null comment '博客公告（Markdown格式）',
    config       json                                    not null comment '个人配置（JSON格式，如主题、布局、功能开关等）',
    is_used      tinyint       default 0                 not null comment '是否启用（0=不使用 1=使用）',
    created_at   datetime      default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at   datetime      default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '博主信息表' charset = utf8mb4;








-- 3. 标签表
CREATE TABLE `tags` (
    `id`          INT UNSIGNED  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        VARCHAR(50)   NOT NULL COMMENT '标签名称',
    `color`       VARCHAR(20)   NOT NULL DEFAULT '#409EFF' COMMENT '标签颜色',
    `created_at`  DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签表';


-- 4. 文章表
CREATE TABLE `articles` (
    `id`            INT UNSIGNED  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `title`         VARCHAR(200)  NOT NULL COMMENT '文章标题',
    `content`       LONGTEXT      NOT NULL COMMENT 'Markdown 正文',
    `summary`       VARCHAR(500)  NOT NULL DEFAULT '' COMMENT '文章简介',
    `cover`         VARCHAR(500)  NOT NULL DEFAULT '' COMMENT '封面URL',
    `category_id`   INT UNSIGNED  DEFAULT NULL COMMENT '分类ID',
    `word_count`    INT UNSIGNED  NOT NULL DEFAULT 0 COMMENT '字数',
    `read_count`    INT UNSIGNED  NOT NULL DEFAULT 0 COMMENT '阅读量（PV）',
    `comment_count` INT UNSIGNED  NOT NULL DEFAULT 0 COMMENT '评论数',
    `status`        TINYINT       NOT NULL DEFAULT 0 COMMENT '状态（0=草稿 1=已发布 2=下架）',
    `is_delete`     TINYINT       NOT NULL DEFAULT 0 COMMENT '逻辑删除（0=未删 1=已删）',
    `created_at`    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    `updated_at`    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_category` (`category_id`),
    KEY `idx_status` (`status`),
    KEY `idx_created` (`created_at`),
    FULLTEXT KEY `ft_title_content` (`title`, `content`) COMMENT '全文检索（标题+内容）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章表';


-- 5. 文章-标签关联表（多对多）
CREATE TABLE `article_tags` (
    `id`         INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `article_id` INT UNSIGNED NOT NULL COMMENT '文章ID',
    `tag_id`     INT UNSIGNED NOT NULL COMMENT '标签ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_article_tag` (`article_id`, `tag_id`),
    KEY `idx_tag` (`tag_id`),
    CONSTRAINT `fk_at_article` FOREIGN KEY (`article_id`) REFERENCES `articles`(`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_at_tag`     FOREIGN KEY (`tag_id`)     REFERENCES `tags`(`id`)     ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章-标签关联表';


-- 6. 评论表（树状嵌套：楼中楼）
CREATE TABLE `comments` (
    `id`                INT UNSIGNED  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `article_id`        INT UNSIGNED  NOT NULL COMMENT '所属文章ID（0=留言板留言）',
    `parent_id`         INT UNSIGNED  DEFAULT NULL COMMENT '父评论ID（NULL=一级评论）',
    `reply_to_comment_id` INT UNSIGNED DEFAULT NULL COMMENT '被回复的评论ID',
    `nickname`          VARCHAR(50)   NOT NULL COMMENT '评论者昵称',
    `email`             VARCHAR(200)  NOT NULL DEFAULT '' COMMENT '评论者邮箱（用于回复通知）',
    `avatar`            VARCHAR(500)  NOT NULL DEFAULT '' COMMENT '评论者头像URL',
    `content`           TEXT          NOT NULL COMMENT '评论内容',
    `status`            TINYINT       NOT NULL DEFAULT 0 COMMENT '状态（0=待审核 1=已通过 2=垃圾评论）',
    `ip_address`        VARCHAR(45)   NOT NULL DEFAULT '' COMMENT '评论者IP',
    `user_agent`        VARCHAR(500)  NOT NULL DEFAULT '' COMMENT '评论者UA',
    `created_at`        DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
    PRIMARY KEY (`id`),
    KEY `idx_article` (`article_id`),
    KEY `idx_parent`  (`parent_id`),
    KEY `idx_status`  (`status`),
    KEY `idx_created` (`created_at`),
    CONSTRAINT `fk_cmt_article` FOREIGN KEY (`article_id`) REFERENCES `articles`(`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_cmt_parent`  FOREIGN KEY (`parent_id`) REFERENCES `comments`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表（树状嵌套）';


-- 7. 友情链接表
CREATE TABLE `friend_links` (
    `id`          INT UNSIGNED  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        VARCHAR(100)  NOT NULL COMMENT '友站名称',
    `url`         VARCHAR(500)  NOT NULL COMMENT '友站URL',
    `avatar`      VARCHAR(500)  NOT NULL DEFAULT '' COMMENT '友站头像URL',
    `description` VARCHAR(200)  NOT NULL DEFAULT '' COMMENT '站点描述',
    `sort_order`  INT           NOT NULL DEFAULT 0 COMMENT '排序（越小越靠前）',
    `status`      TINYINT       NOT NULL DEFAULT 1 COMMENT '状态（0=隐藏 1=展示）',
    `created_at`  DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_sort` (`sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='友情链接表';


-- 8. 访问日志表（原始访问记录）
CREATE TABLE `visit_logs` (
    `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `ip`         VARCHAR(45)     NOT NULL DEFAULT '' COMMENT '访问者IP',
    `user_agent` VARCHAR(500)    NOT NULL DEFAULT '' COMMENT 'User-Agent',
    `page_url`   VARCHAR(500)    NOT NULL DEFAULT '' COMMENT '访问页面URL',
    `visit_date` DATE            NOT NULL COMMENT '访问日期（yyyy-MM-dd）',
    `created_at` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '访问时间',
    PRIMARY KEY (`id`),
    KEY `idx_date` (`visit_date`),
    KEY `idx_ip_date` (`ip`, `visit_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='访问日志表';


-- 9. 每日统计汇总表
CREATE TABLE `daily_stats` (
    `id`         INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `stat_date`  DATE         NOT NULL COMMENT '统计日期',
    `pv`         INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '页面访问量',
    `uv`         INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '独立访客数',
    `created_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_date` (`stat_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='每日统计汇总表';


-- 10. 系统设置表（KV 配置）
CREATE TABLE `system_settings` (
    `id`            INT UNSIGNED  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `setting_key`   VARCHAR(100)  NOT NULL COMMENT '配置键',
    `setting_value` TEXT          NOT NULL COMMENT '配置值（JSON格式存储）',
    `description`   VARCHAR(200)  NOT NULL DEFAULT '' COMMENT '配置描述',
    `created_at`    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_key` (`setting_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统设置表';


-- 11. 图床文件表（图片管理）



