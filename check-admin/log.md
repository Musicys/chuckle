# QXchuckle Admin 开发日志

## 2026-07-10

### 新增功能

#### 1. 文章管理模块

**API 接口**

- 创建 `src/service/api/article.ts`
- 实现 5 个接口：新增文章、更新文章、获取文章详情、分页查询文章列表、删除文章
- 接口路径：`/api/admin/article/*`

**类型定义**

- 创建 `src/typings/api/article.d.ts`
- 定义 `Tag`、`ArticleInfo`、`AddArticleParams`、`UpdateArticleParams`、`ArticleListParams`、`ArticleListResult` 等接口

**页面组件**

- 创建 `src/views/article/index.vue`
- 功能：文章列表展示、分页、搜索（标题、分类、状态）、新增跳转、编辑跳转、删除
- 使用 NaiveUI 组件：NDataTable、NForm、NSelect、NTag 等

**文章编辑页面**

- 创建 `src/views/article/edit.vue`
- 功能：文章标题编辑、分类选择、状态选择、标签选择（支持自定义标签）、Markdown 编辑器、简介编辑
- 使用 MdEditor-V3 作为 Markdown 编辑器
- 使用 NaiveUI 组件：NForm、NInput、NSelect、NTag、NModal 等

#### 2. 评论管理模块

**API 接口**

- 创建 `src/service/api/comment.ts`
- 实现 4 个接口：分页查询评论列表、审核评论、回复评论、删除评论
- 接口路径：`/admin/comment/*`

#### 3. 标签管理模块

**API 接口**

- 创建 `src/service/api/tag.ts`
- 实现 4 个接口：新增标签、更新标签、分页查询标签列表、删除标签
- 接口路径：`/admin/tag/*`

**类型定义**

- 创建 `src/typings/api/tag.d.ts`
- 定义 `TagInfo`、`AddTagParams`、`UpdateTagParams`、`TagListParams`、`TagListResult` 等接口

**页面组件**

- 创建 `src/views/tag/index.vue`
- 功能：标签列表展示、分页、搜索（名称）、新增、编辑、删除、颜色预览
- 使用 NaiveUI 组件：NDataTable、NForm、NModal、NTag 等

**类型定义**

- 创建 `src/typings/api/comment.d.ts`
- 定义 `CommentInfo`、`CommentListParams`、`CommentListResult`、`ReplyCommentParams` 等接口

**页面组件**

- 创建 `src/views/comment/index.vue`
- 功能：评论列表展示、分页、搜索（文章ID、状态、昵称）、审核（通过/拒绝）、回复、删除
- 使用 NaiveUI 组件：NDataTable、NForm、NModal、NSelect、NTag 等

#### 4. 分类管理模块

**API 接口**

- 创建 `src/service/api/category.ts`
- 实现 4 个接口：新增分类、更新分类、分页查询分类列表、删除分类
- 接口路径：`/admin/category/*`

**类型定义**

- 创建 `src/typings/api/category.d.ts`
- 定义 `CategoryInfo`、`AddCategoryParams`、`UpdateCategoryParams`、`CategoryListParams`、`CategoryListResult` 等接口

**页面组件**

- 创建 `src/views/category/index.vue`
- 功能：分类列表展示、分页、搜索（名称）、新增、编辑、删除、排序管理
- 使用 NaiveUI 组件：NDataTable、NForm、NModal、NInput 等

#### 3. 路由配置

- 路由已通过 elegant-router 自动生成
- 文章管理：`/article`
- 评论管理：`/comment`

#### 4. 国际化

- 更新中文翻译 `src/locales/langs/zh-cn.ts`
- 更新英文翻译 `src/locales/langs/en-us.ts`
- 添加文章管理、评论管理、标签管理、分类管理路由名称

### 修改内容

#### 1. API 导出

- 更新 `src/service/api/index.ts`
- 导出 article、comment、tag、category 模块

#### 2. 开发规范文档

- 更新 `agnet.md`
- 添加文章管理、评论管理、标签管理、分类管理接口文档

### 技术栈

- Vue 3 + TypeScript
- NaiveUI 组件库
- UnoCSS 样式
- SoybeanAdmin 框架

### 代码规范

- 使用 `fetch` 前缀命名 API 函数
- 类型定义使用 `Api.模块名.接口名` 命名空间
- 请求参数和响应数据类型完整定义
- 错误处理统一使用 NaiveUI `useMessage`

### 待办事项

- [x] 文章管理页面：添加分类下拉选择（需要分类接口）
- [x] 文章管理页面：添加标签选择器（关联标签管理）
- [x] 文章管理页面：添加富文本编辑器（Markdown）
- [ ] 评论管理页面：添加评论树状展示
- [ ] 评论管理页面：添加批量审核功能
