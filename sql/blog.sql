DROP TABLE IF EXISTS `ry_blog`;
CREATE TABLE `ry_blog`  (
                            `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                            `create_by` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
                            `create_time` DATETIME(0) DEFAULT NULL COMMENT '创建时间',
                            `update_by` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
                            `update_time` DATETIME(0) DEFAULT NULL COMMENT '更新时间',
                            `title` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
                            `type` CHAR(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型 1是文章2是笔记 3公告',
                            `content_type` CHAR(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文本编辑器类型',
                            `content` LONGBLOB COMMENT '内容',
                            `content_markdown` LONGBLOB NULL COMMENT 'Markdown格式内容',
                            `Top` CHAR(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '置顶（0否 1是）',
                            `views` INT(11) DEFAULT 0 COMMENT '阅读',
                            `status` CHAR(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0暂存 1发布）',
                            `blog_pic_type` CHAR(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '首页图片类型（0地址 1上传）',
                            `blog_pic` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '首页图片（ 1上传）',
                            `blog_pic_link` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '首页图片（ 0地址）',
                            `blog_desc` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '简介',
                            `blog_files` TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '附件列表',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = INNODB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '博客信息表' ROW_FORMAT = DYNAMIC;

CREATE TABLE `ry_blog_file`  (
                                 `file_id` BIGINT(20) NOT NULL COMMENT '文件ID',
                                 `blog_id` BIGINT(20) NOT NULL COMMENT 'blogID',
                                 PRIMARY KEY (`file_id`, `blog_id`) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'blog文件表' ROW_FORMAT = DYNAMIC;


CREATE TABLE `ry_blog_tag`  (
                                `tag_id` BIGINT(20) NOT NULL COMMENT '标签ID',
                                `blog_id` BIGINT(20) NOT NULL COMMENT 'blogID',
                                PRIMARY KEY (`tag_id`, `blog_id`) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'blog标签关联表' ROW_FORMAT = DYNAMIC;


CREATE TABLE `ry_blog_type`  (
                                 `type_id` BIGINT(20) NOT NULL COMMENT '类型ID',
                                 `blog_id` BIGINT(20) NOT NULL COMMENT 'blogID',
                                 PRIMARY KEY (`type_id`, `blog_id`) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'blog分类关联表' ROW_FORMAT = DYNAMIC;


CREATE TABLE `ry_comment`  (
                               `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                               `parent_id` BIGINT(20) DEFAULT NULL COMMENT '父评论id',
                               `main_id` BIGINT(20) DEFAULT NULL COMMENT '主评论id(第一级评论)',
                               `like_num` INT(11) DEFAULT NULL COMMENT '点赞数量',
                               `content` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '内容',
                               `type` CHAR(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评论类型：对人评论，对项目评论，对资源评论',
                               `blog_id` BIGINT(20) DEFAULT NULL COMMENT '被评论者id，可以是人、项目、资源',
                               `del_flag` CHAR(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
                               `user_id` BIGINT(20) DEFAULT NULL COMMENT '评论者id',
                               `create_by` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
                               `create_time` DATETIME(0) DEFAULT NULL COMMENT '创建时间',
                               `update_by` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
                               `update_time` DATETIME(0) DEFAULT NULL COMMENT '更新时间',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = INNODB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评论表' ROW_FORMAT = DYNAMIC;


CREATE TABLE `ry_comment_like`  (
                                    `comment_id` BIGINT(20) NOT NULL COMMENT '点赞评论ID',
                                    `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
                                    `create_by` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
                                    `create_time` DATETIME(0) DEFAULT NULL COMMENT '创建时间',
                                    `update_by` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
                                    `update_time` DATETIME(0) DEFAULT NULL COMMENT '更新时间',
                                    PRIMARY KEY (`comment_id`, `user_id`) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '留言点赞表' ROW_FORMAT = DYNAMIC;



CREATE TABLE `ry_message`  (
                               `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                               `parent_id` BIGINT(20) DEFAULT NULL COMMENT '父留言id',
                               `main_id` BIGINT(20) DEFAULT NULL COMMENT '主留言id(第一级留言)',
                               `like_num` INT(11) DEFAULT NULL COMMENT '点赞数量',
                               `content` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '内容',
                               `type` CHAR(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '留言类型：对人评论，对项目评论，对资源评论（0代表留言 1代表回复）',
                               `blog_id` BIGINT(20) DEFAULT NULL COMMENT '被留言者id，可以是人、项目、资源',
                               `del_flag` CHAR(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
                               `user_id` BIGINT(20) DEFAULT NULL COMMENT '留言者id',
                               `create_by` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
                               `create_time` DATETIME(0) DEFAULT NULL COMMENT '创建时间',
                               `update_by` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
                               `update_time` DATETIME(0) DEFAULT NULL COMMENT '更新时间',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = INNODB AUTO_INCREMENT = 55 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '留言表' ROW_FORMAT = DYNAMIC;


CREATE TABLE `ry_message_like`  (
                                    `message_id` BIGINT(20) NOT NULL COMMENT '点赞留言ID',
                                    `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
                                    `create_by` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
                                    `create_time` DATETIME(0) DEFAULT NULL COMMENT '创建时间',
                                    `update_by` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
                                    `update_time` DATETIME(0) DEFAULT NULL COMMENT '更新时间',
                                    PRIMARY KEY (`message_id`, `user_id`) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '留言点赞表' ROW_FORMAT = DYNAMIC;


CREATE TABLE `ry_type`  (
                            `type_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
                            `create_by` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
                            `create_time` DATETIME(0) DEFAULT NULL COMMENT '创建时间',
                            `update_by` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
                            `update_time` DATETIME(0) DEFAULT NULL COMMENT '更新时间',
                            `type_name` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类名称',
                            `type_pic` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '分类图像（ 1上传）',
                            `type_pic_type` CHAR(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '分类图像类型（0地址 1上传）',
                            `type_pic_link` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '分类图像（ 0地址）',
                            PRIMARY KEY (`type_id`) USING BTREE
) ENGINE = INNODB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分类信息表' ROW_FORMAT = DYNAMIC;

CREATE TABLE `ry_tag`  (
                           `tag_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '标签ID',
                           `create_by` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
                           `create_time` DATETIME(0) DEFAULT NULL COMMENT '创建时间',
                           `update_by` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
                           `update_time` DATETIME(0) DEFAULT NULL COMMENT '更新时间',
                           `tag_name` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标签名称',
                           PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = INNODB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标签信息表' ROW_FORMAT = DYNAMIC;
