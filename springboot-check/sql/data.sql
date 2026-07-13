-- ============================================================
-- 个人博客系统 · 测试数据
-- ============================================================

USE `check_blog`;

-- ============================================================
-- 1. 博主信息
-- ============================================================
INSERT IGNORE INTO `blogger_info` (`id`, `avatar`, `nickname`, `blog_title`, `qq`, `github`, `gitee`, `bilibili`, `birth_date`, `gender`, `occupation`, `tags`, `hobbies`, `games`, `latitude`, `longitude`, `address`, `announcement`, `config`, `is_used`)
VALUES (1,
        'https://www.dmoe.cc/random.php',
        '轻笑Chuckle',
        '轻笑Chuckle的个人博客',
        '2825424566@qqcom',
        'https://github.com/liwang',
        'https://gitee.com/liwang',
        'https://space.bilibili.com/123456',
        '1998-06-15',
        1,
        '全栈开发工程师',
        '["Java", "Vue", "Spring Boot", "MySQL", "热爱技术"]',
        '["编程", "阅读", "摄影", "羽毛球", "旅行"]',
        '["原神", "星穹铁道", "塞尔达传说"]',
        30.5728150,
        104.0668010,
        '四川省成都市',
        '欢迎来到 **『轻笑Chuckle』** 的小站 🎉\n\n如果有什么加载不出来或者其它小 bug，「刷新网页 / Ctrl+F5」应该、也许能解决。\n\n这里记录着技术、生活和一切有趣的东西。',
        '{"theme":"default","layout":"blog","commentEnabled":true,"musicEnabled":false}',
        1);

-- ============================================================
-- 2. 文章分类
-- ============================================================
INSERT IGNORE INTO `categories` (`id`, `name`, `description`, `sort_order`)
VALUES (1, '技术', '技术相关文章，涵盖后端、前端、数据库等', 1),
       (2, '生活', '日常生活分享与感悟', 2),
       (3, '随笔', '随手记，短篇随笔', 3),
       (4, '教程', '详细教程类文章', 4),
       (5, '项目分享', '开源项目与实战经验分享', 5);

-- ============================================================
-- 3. 标签
-- ============================================================
INSERT IGNORE INTO `tags` (`id`, `name`, `color`)
VALUES (1, 'Java', '#E76F00'),
       (2, 'Spring Boot', '#6DB33F'),
       (3, 'Vue', '#4FC08D'),
       (4, 'MySQL', '#4479A1'),
       (5, 'Redis', '#DC382D'),
       (6, '前端', '#42B883'),
       (7, '后端', '#00A65A'),
       (8, '架构设计', '#F39C12'),
       (9, 'Linux', '#FCC624'),
       (10, 'Docker', '#2496ED'),
       (11, '读书笔记', '#9B59B6'),
       (12, '开源项目', '#E74C3C');

-- ============================================================
-- 4. 文章
-- ============================================================
INSERT IGNORE INTO `articles` (`id`, `title`, `content`, `summary`, `category_id`, `word_count`, `read_count`, `comment_count`, `status`, `is_delete`, `created_at`, `updated_at`)
VALUES
(1, 'Spring Boot 项目最佳实践',
'# Spring Boot 项目最佳实践\n\n## 项目结构\n\n一个好的项目结构应该清晰分层，职责分明。推荐以下结构：\n\n```\nsrc/main/java/com/example\n├── config        -- 配置类\n├── controller    -- 控制器\n├── service       -- 业务接口\n│   └── impl      -- 业务实现\n├── mapper        -- 数据访问\n├── model\n│   ├── domain    -- 实体\n│   ├── dto       -- 传输对象\n│   └── vo        -- 视图对象\n├── utils         -- 工具类\n└── exception     -- 异常处理\n```\n\n## 统一响应体\n\n定义统一的 `BaseResponse<T>`，让前端对接更规范：\n\n```java\n@Data\npublic class BaseResponse<T> {\n    private int code;\n    private T data;\n    private String message;\n}\n```\n\n## 异常处理\n\n全局异常处理器捕获业务异常，避免堆栈信息泄露。\n\n## 总结\n\n好的规范能减少沟通成本，提升代码可维护性。',
'Spring Boot 项目的最佳实践总结，包括项目结构、统一响应体和异常处理等方面的经验分享。',
1, 580, 328, 5, 1, 0, '2026-06-01 10:00:00', '2026-06-20 15:30:00'),

(2, 'Vue3 + Pinia 状态管理入门',
'# Vue3 + Pinia 状态管理入门\n\n## 为什么用 Pinia\n\nPinia 是 Vue3 官方推荐的状态管理库，相比 Vuex 更简洁：\n\n- 完整的 TypeScript 支持\n- 去掉了 mutations，只有 state、getters、actions\n- 支持组合式 API\n\n## 快速上手\n\n```javascript\nimport { defineStore } from \"pinia\"\n\nexport const useUserStore = defineStore(\"user\", () => {\n  const name = ref(\"\")\n  const login = (userName) => {\n    name.value = userName\n  }\n  return { name, login }\n})\n```\n\n## 在组件中使用\n\n直接在组件中调用 store，无需 mapState 等辅助函数。\n\nPinia 的出现让 Vue3 的状态管理变得前所未有的简单。',
'Vue3 推荐的状态管理方案 Pinia 入门教程，对比 Vuex 的优势及基本用法。',
1, 420, 256, 3, 1, 0, '2026-06-05 14:00:00', '2026-06-18 09:20:00'),

(3, 'MySQL 索引优化实战',
'# MySQL 索引优化实战\n\n## 最左前缀原则\n\n联合索引 (a, b, c)，以下查询能命中索引：\n\n- `WHERE a = 1`\n- `WHERE a = 1 AND b = 2`\n- `WHERE a = 1 AND b = 2 AND c = 3`\n\n以下查询**不能**命中：\n\n- `WHERE b = 2`\n- `WHERE c = 3`\n\n## 覆盖索引\n\n如果查询的列都在索引中，MySQL 无需回表，性能大幅提升。\n\n## 慢查询优化步骤\n\n1. 开启慢查询日志\n2. 使用 EXPLAIN 分析 SQL\n3. 关注 type、rows、Extra 字段\n4. 优化索引或改写 SQL\n\n## 实战案例\n\n某查询原本 3 秒，加联合索引后降到 5 毫秒。',
'MySQL 索引优化的实战经验，涵盖最左前缀原则、覆盖索引和慢查询优化流程。',
1, 650, 412, 7, 1, 0, '2026-06-10 09:00:00', '2026-06-22 11:45:00'),

(4, '2026 上半年读书总结',
'# 2026 上半年读书总结\n\n## 技术类\n\n### 《重构：改善既有代码的设计》\nMartin Fowler 经典之作。读完最大的收获是：**不要为了重构而重构**，每次重构要有明确的动机。\n\n### 《Redis 设计与实现》\n深入理解了 Redis 的数据结构底层实现，对 SDS、跳跃表、压缩列表有了更清晰的认识。\n\n## 文学类\n\n### 《百年孤独》\n马尔克斯的魔幻现实主义杰作。第二遍读，感受到了更多关于命运与循环的隐喻。\n\n### 《三体》系列\n第三次重读，仍然被大刘的想象力震撼。\n\n## 总结\n\n上半年读了 12 本书，比计划少了 3 本。下半年加油！',
'2026 年上半年读书总结，涵盖技术类和文学类书籍，附个人感悟。',
2, 780, 189, 4, 1, 0, '2026-06-15 08:30:00', '2026-06-25 10:10:00'),

(5, '从零搭建个人博客系统',
'# 从零搭建个人博客系统\n\n## 技术选型\n\n| 组件 | 技术 |\n|------|------|\n| 前端框架 | Vue3 + Vite |\n| UI 组件 | Element Plus |\n| 后端框架 | Spring Boot 2.7 |\n| 数据库 | MySQL + Redis |\n| 对象存储 | 阿里云 OSS |\n\n## 整体架构\n\n前端通过 Axios 调用后端 API，后端采用分层架构，使用 JWT 进行鉴权。\n\n## 遇到的坑\n\n1. **跨域问题**：配置 CorsConfig 解决\n2. **Long 精度丢失**：Jackson 序列化时将 Long 转 String\n3. **图片上传**：大文件需要分片上传\n\n## 效果\n\n就是你现在看到的这个博客站啦！',
'从零搭建个人博客系统的技术选型、架构设计和踩坑记录。',
5, 520, 534, 10, 1, 0, '2026-06-20 20:00:00', '2026-07-01 16:30:00'),

(6, 'Docker 常用命令速查',
'# Docker 常用命令速查\n\n## 镜像操作\n\n```bash\ndocker images                  # 列出镜像\ndocker pull nginx              # 拉取镜像\ndocker rmi nginx               # 删除镜像\ndocker build -t myapp .        # 构建镜像\n```\n\n## 容器操作\n\n```bash\ndocker ps                      # 运行中容器\ndocker ps -a                   # 所有容器\ndocker start/stop/restart xxx  # 启停\ndocker logs -f xxx             # 查看日志\ndocker exec -it xxx bash       # 进入容器\n```\n\n## Docker Compose\n\n```yaml\nservices:\n  app:\n    build: .\n    ports:\n      - \"8080:8080\"\n    depends_on:\n      - mysql\n```\n\n掌握这些命令足以应对日常开发。',
'Docker 日常开发中最常用的命令和 Docker Compose 配置速查表。',
4, 380, 301, 2, 1, 0, '2026-06-22 11:00:00', '2026-06-28 09:00:00'),

(7, '关于程序员职业发展的几点思考',
'# 关于程序员职业发展的几点思考\n\n## 技术的深度与广度\n\n刚入行时追求广度，什么都想学。后来发现深度才是核心竞争力。\n\n在一个领域深耕到能够解决别人解决不了的问题，这就是不可替代性。\n\n## 软技能同样重要\n\n- 沟通能力：能讲清楚技术方案\n- 文档能力：写得好代码也要写得好文档\n- 时间管理：合理安排任务优先级\n\n## 保持学习的节奏\n\n技术更新迭代快，但不必焦虑。打好基础（数据结构、网络、操作系统），掌握核心框架的原理，新东西上手自然就快了。\n\n## 写在最后\n\n程序员是一个需要终身学习的职业，保持热爱，保持好奇。',
'关于程序员技术成长、软技能和职业发展的个人思考与建议。',
3, 620, 276, 6, 1, 0, '2026-06-28 19:30:00', '2026-07-02 22:00:00'),

(8, 'Spring Boot 整合 Redis 缓存',
'# Spring Boot 整合 Redis 缓存\n\n## 添加依赖\n\n```xml\n<dependency>\n    <groupId>org.springframework.boot</groupId>\n    <artifactId>spring-boot-starter-data-redis</artifactId>\n</dependency>\n```\n\n## 配置连接\n\n```yaml\nspring:\n  redis:\n    host: localhost\n    port: 6379\n    database: 0\n```\n\n## 使用缓存注解\n\n```java\n@Cacheable(value = \"user\", key = \"#id\")\npublic User getUserById(Long id) {\n    return userMapper.selectById(id);\n}\n\n@CacheEvict(value = \"user\", key = \"#user.id\")\npublic User updateUser(User user) {\n    userMapper.updateById(user);\n    return user;\n}\n```\n\n## 缓存穿透与雪崩\n\n- 穿透：布隆过滤器或缓存空值\n- 雪崩：设置随机过期时间\n\nRedis 缓存用得好，系统性能翻倍。',
'Spring Boot 整合 Redis 缓存完整教程，包括注解使用、缓存穿透和雪崩处理。',
4, 480, 367, 8, 1, 0, '2026-07-01 10:00:00', '2026-07-05 14:20:00'),

(9, 'GitHub Actions 自动化部署',
'# GitHub Actions 自动化部署\n\n## 工作流配置\n\n```yaml\nname: Deploy\n\non:\n  push:\n    branches: [ main ]\n\njobs:\n  deploy:\n    runs-on: ubuntu-latest\n    steps:\n      - uses: actions/checkout@v3\n      - name: Build\n        run: mvn clean package\n      - name: Deploy\n        run: scp target/*.jar user@server:/app/\n```\n\n## 优势\n\n- 免费额度充足\n- 与 GitHub 深度集成\n- 市场上有大量现成 Action\n\n## 最佳实践\n\n1. 敏感信息用 Secrets 存储\n2. 构建产物用 cache 加速\n3. 失败时发送通知\n\nCI/CD 是现代开发的标配，值得投入时间配置。',
'GitHub Actions 自动化部署配置教程，含工作流示例和 CI/CD 最佳实践。',
4, 350, 198, 1, 1, 0, '2026-07-03 16:00:00', '2026-07-06 08:50:00'),

(10, '丽江旅行记',
'# 丽江旅行记\n\n上个月去了趟丽江，记录一下感受。\n\n## 古城漫步\n\n丽江古城比想象中大得多。清晨的古城很安静，石板路上几乎没有游客，和白天人山人海的样子判若两地。\n\n## 玉龙雪山\n\n坐缆车到 4506 米，再爬栈道到 4680 米。高反确实有，但慢点走还能接受。山顶的风景值得所有辛苦。\n\n## 美食推荐\n\n- 腊排骨火锅 —— 必吃\n- 鸡豆凉粉 —— 清爽开胃\n- 鲜花饼 —— 伴手礼首选\n\n## 总结\n\n丽江是一个适合放空的地方，下次还来。',
'丽江旅行记录，分享古城漫步、玉龙雪山和当地美食的体验。',
2, 450, 432, 9, 1, 0, '2026-07-05 09:00:00', '2026-07-06 21:10:00');

-- ============================================================
-- 5. 文章-标签关联
-- ============================================================
INSERT IGNORE INTO `article_tags` (`article_id`, `tag_id`)
VALUES (1, 1),
       (1, 2),
       (1, 7),
       (2, 3),
       (2, 6),
       (3, 4),
       (3, 7),
       (4, 11),
       (5, 1),
       (5, 2),
       (5, 3),
       (5, 6),
       (5, 7),
       (5, 12),
       (6, 9),
       (6, 10),
       (7, 11),
       (8, 1),
       (8, 2),
       (8, 5),
       (8, 7),
       (9, 9),
       (9, 10),
       (10, 11);

-- ============================================================
-- 6. 评论（树状嵌套：楼中楼）
-- ============================================================
-- 文章1 的评论
INSERT IGNORE INTO `comments` (`id`, `article_id`, `parent_id`, `reply_to_comment_id`, `nickname`, `email`, `avatar`, `content`, `status`, `ip_address`, `user_agent`, `created_at`)
VALUES (1, 1, NULL, NULL, '小明', 'xiaoming@example.com', 'https://www.dmoe.cc/random.php', '非常实用的文章，项目结构那块很有帮助！', 1, '192.168.1.1', 'Mozilla/5.0 Chrome/120', '2026-06-02 10:30:00'),
       (2, 1, 1, 1, '黎旺', 'liwang@example.com', 'https://www.dmoe.cc/random.php', '谢谢支持，后续还会写更多干货~', 1, '192.168.1.2', 'Mozilla/5.0 Chrome/120', '2026-06-02 11:00:00'),
       (3, 1, 1, 1, '张三', 'zhangsan@example.com', 'https://www.dmoe.cc/random.php', '请问统一响应体里 code 怎么定义比较好？', 1, '10.0.0.1', 'Mozilla/5.0 Firefox/120', '2026-06-03 14:20:00'),
       (4, 1, 3, 3, '黎旺', 'liwang@example.com', 'https://www.dmoe.cc/random.php', '建议用枚举定义，比如 SUCCESS=0, ERROR=50000 这样', 1, '192.168.1.2', 'Mozilla/5.0 Chrome/120', '2026-06-03 15:00:00'),
       (5, 1, 3, 3, '李四', 'lisi@example.com', 'https://www.dmoe.cc/random.php', '学到了，感谢！', 2, '10.0.0.2', 'Mozilla/5.0 Safari/17', '2026-06-04 09:15:00');

-- 文章3 的评论
INSERT IGNORE INTO `comments` (`id`, `article_id`, `parent_id`, `reply_to_comment_id`, `nickname`, `email`, `avatar`, `content`, `status`, `ip_address`, `user_agent`, `created_at`)
VALUES (6, 3, NULL, NULL, '小王', 'wang@example.com', 'https://www.dmoe.cc/random.php', '之前有个查询跑了几秒，加了联合索引瞬间变快，深有体会！', 1, '172.16.0.1', 'Mozilla/5.0 Edge/120', '2026-06-11 08:00:00'),
       (7, 3, NULL, NULL, '赵六', 'zhao@example.com', 'https://www.dmoe.cc/random.php', 'EXPLAIN 的 type 字段能详细讲讲吗？', 1, '172.16.0.2', 'Mozilla/5.0 Chrome/120', '2026-06-12 20:30:00'),
       (8, 3, 7, 7, '黎旺', 'liwang@example.com', 'https://www.dmoe.cc/random.php', '好的，下一篇就安排上', 1, '192.168.1.2', 'Mozilla/5.0 Chrome/120', '2026-06-12 21:00:00');

-- 文章5 的评论
INSERT IGNORE INTO `comments` (`id`, `article_id`, `parent_id`, `reply_to_comment_id`, `nickname`, `email`, `avatar`, `content`, `status`, `ip_address`, `user_agent`, `created_at`)
VALUES (9, 5, NULL, NULL, '小白', 'bai@example.com', 'https://www.dmoe.cc/random.php', '博客做得真不错！源码开源吗？', 1, '192.168.1.10', 'Mozilla/5.0 Chrome/120', '2026-06-21 09:00:00'),
       (10, 5, 9, 9, '黎旺', 'liwang@example.com', 'https://www.dmoe.cc/random.php', '开源的，GitHub 上可以找到~', 1, '192.168.1.2', 'Mozilla/5.0 Chrome/120', '2026-06-21 09:30:00'),
       (11, 5, NULL, NULL, '访客9527', 'guest@example.com', 'https://www.dmoe.cc/random.php', '请问用的是什么评论区方案？', 0, '10.10.0.1', 'Mozilla/5.0 Firefox/120', '2026-07-06 22:15:00');

-- 文章7 的评论
INSERT IGNORE INTO `comments` (`id`, `article_id`, `parent_id`, `reply_to_comment_id`, `nickname`, `email`, `avatar`, `content`, `status`, `ip_address`, `user_agent`, `created_at`)
VALUES (12, 7, NULL, NULL, '在路上', 'onroad@example.com', 'https://www.dmoe.cc/random.php', '深度那一块说得太对了，广度容易深度难', 1, '192.168.1.5', 'Mozilla/5.0 Chrome/120', '2026-06-29 20:00:00'),
       (13, 7, 12, 12, '黎旺', 'liwang@example.com', 'https://www.dmoe.cc/random.php', '共勉！保持学习~', 1, '192.168.1.2', 'Mozilla/5.0 Chrome/120', '2026-06-29 20:30:00');

-- 文章8 的评论
INSERT IGNORE INTO `comments` (`id`, `article_id`, `parent_id`, `reply_to_comment_id`, `nickname`, `email`, `avatar`, `content`, `status`, `ip_address`, `user_agent`, `created_at`)
VALUES (14, 8, NULL, NULL, 'Redis新手', 'redis@example.com', 'https://www.dmoe.cc/random.php', '想问下缓存和数据库一致性怎么保证？', 1, '10.0.0.5', 'Mozilla/5.0 Chrome/120', '2026-07-02 11:00:00'),
       (15, 8, 14, 14, '黎旺', 'liwang@example.com', 'https://www.dmoe.cc/random.php', '推荐用「先更新数据库，再删缓存」的策略，简单有效', 1, '192.168.1.2', 'Mozilla/5.0 Chrome/120', '2026-07-02 11:30:00'),
       (16, 8, NULL, NULL, '缓存爱好者', 'cache@example.com', 'https://www.dmoe.cc/random.php', '延时双删了解一下，比直接删更稳', 1, '10.0.0.6', 'Mozilla/5.0 Edge/120', '2026-07-03 15:20:00');

-- 文章10 的评论
INSERT IGNORE INTO `comments` (`id`, `article_id`, `parent_id`, `reply_to_comment_id`, `nickname`, `email`, `avatar`, `content`, `status`, `ip_address`, `user_agent`, `created_at`)
VALUES (17, 10, NULL, NULL, '旅行爱好者', 'travel@example.com', 'https://www.dmoe.cc/random.php', '丽江确实是个好地方，推荐也去大理看看', 1, '192.168.1.8', 'Mozilla/5.0 Chrome/120', '2026-07-05 18:00:00'),
       (18, 10, 17, 17, '黎旺', 'liwang@example.com', 'https://www.dmoe.cc/random.php', '大理去过啦，洱海骑行太舒服了', 1, '192.168.1.2', 'Mozilla/5.0 Chrome/120', '2026-07-05 19:00:00'),
       (19, 10, NULL, NULL, '游客', 'tourist@example.com', 'https://www.dmoe.cc/random.php', '腊排骨火锅确实好吃！', 0, '192.168.1.9', 'Mozilla/5.0 Safari/17', '2026-07-06 20:00:00');

-- ============================================================
-- 7. 友情链接
-- ============================================================
INSERT IGNORE INTO `friend_links` (`id`, `name`, `url`, `avatar`, `description`, `sort_order`, `status`)
VALUES (1, '编程导航', 'https://www.code-nav.cn/', 'https://www.dmoe.cc/random.php', '编程导航，学编程不迷路', 1, 1),
       (2, '阮一峰的网络日志', 'https://www.ruanyifeng.com/blog/', 'https://www.dmoe.cc/random.php', '科技爱好者周刊，每周五更新', 2, 1),
       (3, '小林 Coding', 'https://xiaolincoding.com/', 'https://www.dmoe.cc/random.php', '图解计算机基础', 3, 1),
       (4, 'JavaGuide', 'https://javaguide.cn/', 'https://www.dmoe.cc/random.php', 'Java 学习 & 面试指南', 4, 1),
       (5, '小伙伴的博客', 'https://friend-blog.example.com/', 'https://www.dmoe.cc/random.php', '一个热爱技术的同学', 5, 0);

-- ============================================================
-- 8. 访问日志
-- ============================================================
INSERT IGNORE INTO `visit_logs` (`ip`, `user_agent`, `page_url`, `visit_date`, `created_at`)
VALUES ('192.168.1.1', 'Mozilla/5.0 Chrome/120', '/', '2026-07-01', '2026-07-01 08:00:00'),
       ('192.168.1.1', 'Mozilla/5.0 Chrome/120', '/article/1', '2026-07-01', '2026-07-01 08:01:00'),
       ('192.168.1.1', 'Mozilla/5.0 Chrome/120', '/article/3', '2026-07-01', '2026-07-01 08:05:00'),
       ('10.0.0.1', 'Mozilla/5.0 Firefox/120', '/', '2026-07-01', '2026-07-01 10:00:00'),
       ('10.0.0.1', 'Mozilla/5.0 Firefox/120', '/article/5', '2026-07-01', '2026-07-01 10:02:00'),
       ('172.16.0.1', 'Mozilla/5.0 Edge/120', '/', '2026-07-02', '2026-07-02 09:00:00'),
       ('172.16.0.1', 'Mozilla/5.0 Edge/120', '/article/1', '2026-07-02', '2026-07-02 09:10:00'),
       ('172.16.0.1', 'Mozilla/5.0 Edge/120', '/article/2', '2026-07-02', '2026-07-02 09:15:00'),
       ('172.16.0.2', 'Mozilla/5.0 Chrome/120', '/', '2026-07-02', '2026-07-02 14:00:00'),
       ('10.0.0.2', 'Mozilla/5.0 Safari/17', '/article/8', '2026-07-03', '2026-07-03 11:00:00'),
       ('192.168.1.5', 'Mozilla/5.0 Chrome/120', '/', '2026-07-03', '2026-07-03 15:00:00'),
       ('192.168.1.5', 'Mozilla/5.0 Chrome/120', '/article/7', '2026-07-03', '2026-07-03 15:30:00'),
       ('10.0.0.5', 'Mozilla/5.0 Chrome/120', '/article/8', '2026-07-04', '2026-07-04 10:00:00'),
       ('10.0.0.6', 'Mozilla/5.0 Edge/120', '/article/8', '2026-07-04', '2026-07-04 16:00:00'),
       ('192.168.1.8', 'Mozilla/5.0 Chrome/120', '/article/10', '2026-07-05', '2026-07-05 18:00:00'),
       ('192.168.1.9', 'Mozilla/5.0 Safari/17', '/article/10', '2026-07-06', '2026-07-06 20:00:00'),
       ('10.10.0.1', 'Mozilla/5.0 Firefox/120', '/article/5', '2026-07-06', '2026-07-06 22:00:00'),
       ('192.168.1.2', 'Mozilla/5.0 Chrome/120', '/admin', '2026-07-06', '2026-07-06 23:00:00');

-- ============================================================
-- 9. 每日统计
-- ============================================================
INSERT IGNORE INTO `daily_stats` (`stat_date`, `pv`, `uv`)
VALUES ('2026-07-01', 5, 2),
       ('2026-07-02', 4, 2),
       ('2026-07-03', 3, 2),
       ('2026-07-04', 2, 2),
       ('2026-07-05', 1, 1),
       ('2026-07-06', 3, 3);

-- ============================================================
-- 10. 系统设置
-- ============================================================
INSERT IGNORE INTO `system_settings` (`setting_key`, `setting_value`, `description`)
VALUES ('mail_config', '{"host":"smtp.qq.com","port":465,"username":"liwang@qq.com","password":"encrypted_password","fromAddress":"liwang@qq.com"}', '邮件 SMTP 配置'),
       ('comment_toggle', '{"globalEnabled":true,"reviewEnabled":true}', '评论全局开关 + 审核开关');
