# 轻笑Chuckle - 个人博客系统

一个基于前后端分离架构的个人博客系统，支持 Markdown 文章发布、标签分类、评论互动、友链、访问统计等功能。配有后台管理系统，方便管理内容。

## 项目架构

```
chuckle/
├── check_user/              # 前端用户端（博客前台）
├── check-admin/             # 前端管理端（博客后台管理）
└── springboot-check/        # 后端服务
```

---

## 前端用户端 (check_user)

基于 **Vue 3 + TypeScript + Vite 5** 构建的个人博客前台。

### 技术栈

| 技术 | 用途 |
|------|------|
| Vue 3 (Composition API) | 前端框架 |
| Vite 5 | 构建工具 |
| TypeScript | 类型安全 |
| Pinia | 状态管理 |
| Vue Router 4 | 路由管理 |
| Element Plus | UI 组件库 |
| Axios | HTTP 请求 |
| ECharts | 数据可视化 |
| highlight.js + v-md-editor | Markdown 渲染与代码高亮 |
| oh-my-live2d | 看板娘（Live2D） |
| vue-lazyload | 图片懒加载 |

### 页面结构

| 路由 | 页面 | 说明 |
|------|------|------|
| `/` | 博客入口 | 启动引导页 |
| `/home` | 博客首页 | 文章列表、轮播图、公告、标签云、归档、网站信息 |
| `/desc` | 博文详情 | Markdown 渲染展示、目录导航、评论区 |
| `/arg` | 标签页 | 按标签分类查看文章 |
| `/tree` | 留言板 | 访客留言互动 |
| `/muisc` | 问问 | 咨询/问答页 |
| `/mine` | 关于 | 博主信息展示 |
| `/datail` | 详情 | 分类详情页 |

### 启动

```bash
cd check_user
yarn dev
```

---

## 前端管理端 (check-admin)

基于 **Soybean Admin** 的中后台管理模板，使用 **Vue 3 + Vite 8 + NaiveUI + UnoCSS**。

### 技术栈

| 技术 | 用途 |
|------|------|
| Vue 3 | 前端框架 |
| Vite 8 | 构建工具 |
| TypeScript | 类型安全 |
| NaiveUI | UI 组件库 |
| Pinia | 状态管理 |
| Vue Router 5 | 路由管理 |
| UnoCSS | 原子化 CSS |
| Vue I18n | 国际化 |
| ECharts | 数据可视化 |
| elegant-router | 文件路由系统 |

### 功能模块

- 登录/注册（密码登录、验证码登录、微信绑定）
- 工作台首页（数据卡片、折线图、饼图、项目动态）
- 主题配置（亮/暗模式、主题色、布局模式、水印设置）
- 多语言支持
- 多布局模式（垂直、水平、混合）

### 启动

```bash
cd check-admin
pnpm dev
```

---

## 后端服务 (springboot-check)

基于 **Spring Boot 2.7.2** 的博客后端服务。

### 技术栈

| 技术 | 版本 | 用途 |
|------|------|------|
| Spring Boot | 2.7.2 | 基础框架 |
| Java | 8 | 运行环境 |
| MyBatis-Plus | 3.5.2 | ORM + 分页 |
| MySQL | 8 | 关系数据库 |
| Redis | - | 缓存/会话 |
| Elasticsearch | - | 全文检索 |
| 阿里云 OSS | 3.17.4 | 文件云端存储 |
| Knife4j/Swagger | 3.0.3 | 接口文档 |
| Hutool | 5.8.8 | Java 工具库 |
| EasyExcel | 3.1.1 | Excel 处理 |
| 微信开放平台 | 4.4.0 | 微信登录 |

### 数据库设计 (check_blog)

| 表名 | 说明 |
|------|------|
| `blogger_info` | 博主信息（头像、昵称、社交链接、个人标签等） |
| `articles` | 文章（标题、Markdown 正文、分类、阅读量、评论数、全文索引） |
| `categories` | 文章分类 |
| `tags` | 标签（含颜色） |
| `article_tags` | 文章-标签多对多关联 |
| `comments` | 树状嵌套评论（楼中楼，支持审核） |
| `friend_links` | 友情链接 |
| `visit_logs` | 访问日志（IP、UA、页面、日期） |
| `daily_stats` | 每日 PV/UV 统计 |
| `system_settings` | KV 系统设置（邮件配置、评论开关等） |

### 项目结构

```
src/main/java/com/yupi/springbootinit
├── annotation/        # 自定义注解
├── aop/               # AOP 切面（日志、鉴权）
├── common/            # 通用组件（统一响应体、错误码、分页）
├── config/            # 配置类（跨域、JSON、Swagger、MyBatis-Plus、OSS）
├── constant/          # 常量定义
├── controller/
│   ├── user/          # 用户端 API
│   └── admin/         # 管理端 API
├── exception/         # 异常处理
├── mapper/            # MyBatis-Plus Mapper
├── model/
│   ├── domain/        # 实体类
│   ├── dto/           # 请求/传输对象
│   └── vo/            # 视图对象
├── service/           # 业务逻辑层
│   └── impl/          # 业务实现
├── utils/             # 工具类（JWT、OSS、IP、SQL）
└── MainApplication.java
```

### API 接口

- 所有接口以 `/api` 开头
- 默认端口 `8088`
- 启动后访问 Swagger 文档：`http://localhost:8088/api/doc.html`

### 启动

```bash
cd springboot-check

# 1. 创建数据库并导入表结构
mysql -u root -p < sql/create_table.sql

# 2. 导入测试数据（可选）
mysql -u root -p < sql/data.sql

# 3. 修改 application.yml 中的数据库配置

# 4. 启动服务
mvn spring-boot:run
```

### Docker 部署

```bash
docker build -t chuckle-blog .
docker run -p 8088:8088 chuckle-blog
```

---

## 核心业务流程

```
博主写文章（Markdown） → 发布到博客
         ↓
访客浏览首页 → 查看文章详情 → 评论互动
         ↓
后台管理 → 文章管理 → 评论审核 → 数据统计
```

## 博客地址

[https://musicbklog.pages.dev](https://musicbklog.pages.dev)（个人博客）

## 参考设计

[https://www.qcqx.cn/](https://www.qcqx.cn/)
