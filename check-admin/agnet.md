# QXchuckle Admin API 开发规范

## 目录

- [1. 项目架构](#1-项目架构)
- [2. API 模块划分](#2-api-模块划分)
- [3. 请求封装约定](#3-请求封装约定)
- [4. 环境变量配置](#4-环境变量配置)
- [5. 类型定义规范](#5-类型定义规范)
- [6. 认证模块对接](#6-认证模块对接)
- [7. 文章管理模块](#7-文章管理模块)
- [8. 评论管理模块](#8-评论管理模块)
- [9. 标签管理模块](#9-标签管理模块)
- [10. 分类管理模块](#10-分类管理模块)
- [11. API 命名规范](#11-api-命名规范)
- [12. 错误处理机制](#12-错误处理机制)

---

## 1. 项目架构

```
src/service/
├── api/              # API 接口定义（按模块划分）
│   ├── auth.ts       # 认证模块
│   ├── route.ts      # 路由模块
│   └── xxx.ts        # 其他业务模块
├── request/          # 请求封装
│   ├── index.ts      # 请求实例创建
│   ├── shared.ts     # 共享工具函数
│   └── type.ts       # 请求类型定义
└── index.ts          # API 导出入口
```

---

## 2. API 模块划分

### 模块命名规则

| 模块 | 文件          | 说明                           |
| ---- | ------------- | ------------------------------ |
| 认证 | `auth.ts`     | 登录、获取用户信息、刷新 Token |
| 路由 | `route.ts`    | 路由相关接口                   |
| 文章 | `article.ts`  | 文章 CRUD                      |
| 评论 | `comment.ts`  | 评论 CRUD                      |
| 标签 | `tag.ts`      | 标签 CRUD                      |
| 分类 | `category.ts` | 分类 CRUD                      |
| 用户 | `user.ts`     | 用户管理                       |
| 其他 | `xxx.ts`      | 按业务模块命名                 |

### 新增模块流程

1. 在 `src/service/api/` 下创建新文件 `xxx.ts`
2. 在 `src/service/api/index.ts` 中导出新模块
3. 在 `src/typings/api/` 下创建对应类型定义文件

---

## 3. 请求封装约定

### 请求实例

项目使用 `@sa/axios` 提供的 `createFlatRequest` 创建请求实例：

```typescript
import { createFlatRequest } from "@sa/axios";

export const request = createFlatRequest(
  { baseURL },
  {
    transform(response) {
      return response.data.data;
    },
    async onRequest(config) {
      const Authorization = getAuthorization();
      Object.assign(config.headers, { Authorization });
      return config;
    },
    isBackendSuccess(response) {
      return (
        String(response.data.code) === import.meta.env.VITE_SERVICE_SUCCESS_CODE
      );
    },
    async onBackendFail(response, instance) {
      // 处理后端错误（登出、Token 过期等）
    },
    onError(error) {
      // 处理请求错误
    },
  },
);
```

### 请求方法

| 方法     | 说明                    |
| -------- | ----------------------- |
| `GET`    | 查询数据                |
| `POST`   | 提交数据（新增/登录等） |
| `PUT`    | 更新数据                |
| `DELETE` | 删除数据                |

### API 路径规范

- API 路径**不需要** `/api` 前缀
- 后端 baseURL 已包含 `/api`，前端直接写业务路径
- 示例：`/admin/article/list` 而非 `/api/admin/article/list`

---

## 4. 环境变量配置

### 核心配置项

| 配置项                             | 说明             | 默认值           |
| ---------------------------------- | ---------------- | ---------------- |
| `VITE_SERVICE_BASE_URL`            | 后端服务地址     | -                |
| `VITE_SERVICE_SUCCESS_CODE`        | 成功状态码       | `0`              |
| `VITE_SERVICE_LOGOUT_CODES`        | 登出状态码       | `8888,8889`      |
| `VITE_SERVICE_MODAL_LOGOUT_CODES`  | 弹窗登出状态码   | `7777,7778`      |
| `VITE_SERVICE_EXPIRED_TOKEN_CODES` | Token 过期状态码 | `9999,9998,3333` |
| `VITE_HTTP_PROXY`                  | 是否启用代理     | `Y`              |
| `VITE_ROUTER_HISTORY_MODE`         | 路由模式         | `history`        |

### 环境文件优先级

```
.env                # 默认配置
.env.test           # 测试环境
.env.prod           # 生产环境
.env.test.local     # 本地测试（最高优先级）
.env.prod.local     # 本地生产（最高优先级）
```

### 本地开发配置

创建 `.env.test.local` 文件：

```env
# 本地开发环境 - 代理到本地后端 8088 端口
VITE_SERVICE_BASE_URL=http://localhost:8088/api

# 后端响应 code 为 0
VITE_SERVICE_SUCCESS_CODE=0
```

---

## 5. 类型定义规范

### API 类型命名空间

所有 API 类型定义在 `Api` 命名空间下，按模块划分：

```typescript
declare namespace Api {
  namespace Auth {
    interface LoginToken {
      token: string;
      refreshToken: string;
    }

    interface UserInfo {
      userId: string;
      userName: string;
      roles: string[];
      buttons: string[];
    }
  }
}
```

### 类型文件结构

```
src/typings/api/
├── auth.d.ts        # 认证模块类型
├── common.d.ts      # 通用类型
├── route.d.ts       # 路由模块类型
├── article.d.ts     # 文章模块类型
├── comment.d.ts     # 评论模块类型
├── tag.d.ts         # 标签模块类型
└── category.d.ts    # 分类模块类型
```

---

## 6. 认证模块对接

### 接口列表

对接后端接口文档 `springboot-check/doc/admin_api/auth.md`

| 接口             | 路径                     | 方法 | 文件      |
| ---------------- | ------------------------ | ---- | --------- |
| 管理员登录       | `/api/auth/login`        | POST | `auth.ts` |
| 获取当前用户信息 | `/api/auth/getUserInfo`  | GET  | `auth.ts` |
| 刷新 Token       | `/api/auth/refreshToken` | POST | `auth.ts` |

### 请求参数

**管理员登录**

```typescript
interface LoginParams {
  userName: string;
  password: string;
}
```

**刷新 Token**

```typescript
interface RefreshTokenParams {
  refreshToken: string;
}
```

### 响应结构

**登录响应**

```typescript
interface LoginToken {
  token: string; // JWT 访问令牌（7天有效）
  refreshToken: string; // 刷新令牌（用于续期）
}
```

**用户信息响应**

```typescript
interface UserInfo {
  userId: string; // 用户ID
  userName: string; // 用户名
  roles: string[]; // 角色列表（R_ADMIN=管理员 R_SUPER=超级管理员）
  buttons: string[]; // 按钮权限列表
}
```

### 状态码约定

| 状态码           | 说明       | 处理方式              |
| ---------------- | ---------- | --------------------- |
| `0`              | 请求成功   | 返回数据              |
| `8888/8889`      | 登出       | 清除状态，跳转登录页  |
| `7777/7778`      | 弹窗登出   | 弹窗提示后登出        |
| `9999/9998/3333` | Token 过期 | 刷新 Token 并重发请求 |

---

## 7. 文章管理模块

### 接口列表

对接后端接口文档 `springboot-check/doc/admin_api/article.md`

| 接口         | 路径                        | 方法 | 文件         |
| ------------ | --------------------------- | ---- | ------------ |
| 新增文章     | `/api/admin/article/add`    | POST | `article.ts` |
| 更新文章     | `/api/admin/article/update` | POST | `article.ts` |
| 获取文章详情 | `/api/admin/article/get`    | GET  | `article.ts` |
| 分页查询文章 | `/api/admin/article/list`   | POST | `article.ts` |
| 删除文章     | `/api/admin/article/delete` | POST | `article.ts` |

### 请求参数

**新增文章**

```typescript
interface AddArticleParams {
  title: string; // 文章标题
  content: string; // Markdown 正文内容
  summary: string; // 文章简介
  categoryId: number; // 分类ID
  status: number; // 状态（0=草稿 1=已发布 2=下架）
  tagIds?: number[]; // 标签ID列表
}
```

**更新文章**

```typescript
interface UpdateArticleParams {
  id: number; // 文章ID
  title?: string; // 文章标题
  content?: string; // Markdown 正文内容
  summary?: string; // 文章简介
  categoryId?: number; // 分类ID
  status?: number; // 状态
  tagIds?: number[]; // 标签ID列表（传则全量替换）
}
```

**分页查询文章**

```typescript
interface ArticleListParams {
  current?: number; // 当前页（默认1）
  pageSize?: number; // 每页条数（默认10）
  title?: string; // 标题模糊搜索
  categoryId?: number; // 分类筛选
  status?: number; // 状态筛选
}
```

### 响应结构

**文章详情**

```typescript
interface Tag {
  id: number; // 标签ID
  name: string; // 标签名称
  color: string; // 标签颜色
}

interface ArticleInfo {
  id: number; // 文章ID
  title: string; // 文章标题
  content: string; // Markdown 正文
  summary: string; // 文章简介
  categoryId: number; // 分类ID
  categoryName: string; // 分类名称
  tags: Tag[]; // 标签列表
  wordCount: number; // 字数
  readCount: number; // 阅读数
  commentCount: number; // 评论数
  status: number; // 状态
  createdAt: string; // 创建时间
  updatedAt: string; // 更新时间
}
```

**分页查询结果**

```typescript
interface ArticleListResult {
  records: ArticleInfo[]; // 文章列表
  total: number; // 总条数
  current: number; // 当前页
  size: number; // 每页条数
}
```

---

## 8. 评论管理模块

### 接口列表

对接后端接口文档 `springboot-check/doc/admin_api/comment.md`

| 接口         | 路径                        | 方法 | 文件         |
| ------------ | --------------------------- | ---- | ------------ |
| 分页查询评论 | `/api/admin/comment/list`   | POST | `comment.ts` |
| 审核评论     | `/api/admin/comment/review` | POST | `comment.ts` |
| 回复评论     | `/api/admin/comment/reply`  | POST | `comment.ts` |
| 删除评论     | `/api/admin/comment/delete` | POST | `comment.ts` |

### 请求参数

**分页查询评论**

```typescript
interface CommentListParams {
  current?: number; // 当前页（默认1）
  pageSize?: number; // 每页条数（默认10）
  articleId?: number; // 按文章筛选
  status?: number; // 状态筛选（0=待审核 1=已通过 2=垃圾评论）
  nickname?: string; // 评论者昵称模糊搜索
}
```

**审核评论**

```typescript
// 参数：id（评论ID）, status（审核状态）
```

**回复评论**

```typescript
interface ReplyCommentParams {
  articleId: number; // 文章ID
  replyToCommentId: number; // 回复的评论ID
  content: string; // 回复内容
}
```

### 响应结构

**评论信息**

```typescript
interface CommentInfo {
  id: number; // 评论ID
  articleId: number; // 文章ID
  articleTitle: string; // 文章标题
  parentId: number | null; // 父评论ID
  replyToCommentId: number | null; // 回复的评论ID
  nickname: string; // 评论者昵称
  email: string; // 评论者邮箱
  avatar: string; // 头像URL
  content: string; // 评论内容
  status: number; // 状态（0=待审核 1=已通过 2=垃圾评论）
  ipAddress: string; // IP地址
  createdAt: string; // 创建时间
}
```

**分页查询结果**

```typescript
interface CommentListResult {
  records: CommentInfo[]; // 评论列表
  total: number; // 总条数
  current: number; // 当前页
  size: number; // 每页条数
}
```

---

## 9. 标签管理模块

### 接口列表

对接后端接口文档 `springboot-check/doc/admin_api/tag.md`

| 接口         | 路径                | 方法 | 文件     |
| ------------ | ------------------- | ---- | -------- |
| 新增标签     | `/admin/tag/add`    | POST | `tag.ts` |
| 更新标签     | `/admin/tag/update` | POST | `tag.ts` |
| 分页查询标签 | `/admin/tag/list`   | POST | `tag.ts` |
| 删除标签     | `/admin/tag/delete` | POST | `tag.ts` |

### 请求参数

**新增标签**

```typescript
interface AddTagParams {
  name: string; // 标签名称
  color?: string; // 标签颜色（默认 #409EFF）
}
```

**更新标签**

```typescript
interface UpdateTagParams {
  id: number; // 标签ID
  name?: string; // 标签名称
  color?: string; // 标签颜色
}
```

**分页查询标签**

```typescript
interface TagListParams {
  current?: number; // 当前页（默认1）
  pageSize?: number; // 每页条数（默认10）
  name?: string; // 标签名称模糊搜索
}
```

### 响应结构

**标签信息**

```typescript
interface TagInfo {
  id: number; // 标签ID
  name: string; // 标签名称
  color: string; // 标签颜色
}
```

**分页查询结果**

```typescript
interface TagListResult {
  records: TagInfo[]; // 标签列表
  total: number; // 总条数
  current: number; // 当前页
  size: number; // 每页条数
}
```

---

## 10. 分类管理模块

### 接口列表

对接后端接口文档 `springboot-check/doc/admin_api/category.md`

| 接口         | 路径                     | 方法 | 文件          |
| ------------ | ------------------------ | ---- | ------------- |
| 新增分类     | `/admin/category/add`    | POST | `category.ts` |
| 更新分类     | `/admin/category/update` | POST | `category.ts` |
| 分页查询分类 | `/admin/category/list`   | POST | `category.ts` |
| 删除分类     | `/admin/category/delete` | POST | `category.ts` |

### 请求参数

**新增分类**

```typescript
interface AddCategoryParams {
  name: string; // 分类名称
  description?: string; // 分类描述（默认 ""）
  sortOrder?: number; // 排序值（默认 0）
}
```

**更新分类**

```typescript
interface UpdateCategoryParams {
  id: number; // 分类ID
  name?: string; // 分类名称
  description?: string; // 分类描述
  sortOrder?: number; // 排序值
}
```

**分页查询分类**

```typescript
interface CategoryListParams {
  current?: number; // 当前页（默认1）
  pageSize?: number; // 每页条数（默认10）
  name?: string; // 分类名称模糊搜索
}
```

### 响应结构

**分类信息**

```typescript
interface CategoryInfo {
  id: number; // 分类ID
  name: string; // 分类名称
  description: string; // 分类描述
  sortOrder: number; // 排序值
  createdAt: string; // 创建时间
}
```

**分页查询结果**

```typescript
interface CategoryListResult {
  records: CategoryInfo[]; // 分类列表
  total: number; // 总条数
  current: number; // 当前页
  size: number; // 每页条数
}
```

---

## 11. API 命名规范

### 函数命名

使用 `fetch` 前缀 + 动作 + 资源名：

```typescript
// 正确
fetchLogin(userName, password);
fetchGetUserInfo();
fetchRefreshToken(refreshToken);

// 错误
login(userName, password);
getUserInfo();
refreshToken(refreshToken);
```

### 文件命名

使用小写 + 连字符（kebab-case）：

```typescript
// 正确
auth.ts;
user - management.ts;

// 错误
Auth.ts;
UserManagement.ts;
userManagement.ts;
```

---

## 12. 错误处理机制

### 请求拦截

- **请求前**：自动添加 `Authorization` 请求头
- **响应后**：根据状态码处理不同业务逻辑

### 错误处理流程

```
请求失败
    ↓
判断错误类型
    ├── HTTP 错误（4xx/5xx）→ 显示错误消息
    └── 后端业务错误
            ├── 登出状态码 → 清除状态，跳转登录
            ├── 弹窗登出状态码 → 弹窗提示后登出
            ├── Token 过期状态码 → 刷新 Token 并重发
            └── 其他错误 → 显示错误消息
```

### 错误消息显示

使用 NaiveUI 的 `$message.error()` 显示错误消息，避免重复显示：

```typescript
export function showErrorMsg(state, message) {
  if (!state.errMsgStack?.includes(message)) {
    state.errMsgStack.push(message);
    window.$message?.error(message, {
      onLeave: () => {
        state.errMsgStack = state.errMsgStack.filter((msg) => msg !== message);
      },
    });
  }
}
```

---

## 13. 页面开发规范

### 页面组件结构

```
src/views/
├── article/
│   └── index.vue       # 文章管理页面
│   └── edit.vue        # 文章编辑页面
├── comment/
│   └── index.vue       # 评论管理页面
├── tag/
│   └── index.vue       # 标签管理页面
├── category/
│   └── index.vue       # 分类管理页面
└── xxx/
    └── index.vue       # 其他业务页面
```

### 页面组件规范

#### 1. 模板结构

```vue
<template>
  <NCard :bordered="false">
    <template #header>
      <!-- 页面标题和操作按钮 -->
    </template>

    <!-- 搜索表单 -->
    <NForm :model="searchForm" inline>
      <!-- 搜索字段 -->
    </NForm>

    <!-- 数据表格 -->
    <NDataTable
      :columns="columns"
      :data="data"
      :loading="loading"
      :pagination="pagination"
    />
  </NCard>

  <!-- 弹窗表单（新增/编辑） -->
  <NModal v-model:show="showModal">
    <NForm :model="form">
      <!-- 表单字段 -->
    </NForm>
  </NModal>
</template>
```

#### 2. 脚本规范

- 使用 `<script setup lang="ts">` 语法
- 导入 NaiveUI 组件使用命名导入
- API 函数使用 `fetch` 前缀
- 表单数据使用 `reactive` 定义
- 列表数据使用 `ref` 定义

#### 3. 列定义规范

```typescript
const columns = [
  { title: "字段名", key: "fieldKey" },
  {
    title: "状态",
    key: "status",
    render(row) {
      return h(
        NTag,
        { type: getStatusType(row.status) },
        getStatusLabel(row.status),
      );
    },
  },
  {
    title: "操作",
    key: "actions",
    render(row) {
      return h(NSpace, null, {
        default: () => [
          h(NButton, { onClick: () => handleEdit(row) }, "编辑"),
          h(
            NButton,
            { type: "error", onClick: () => handleDelete(row) },
            "删除",
          ),
        ],
      });
    },
  },
];
```

#### 4. 分页处理

```typescript
const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
});

function handlePageChange(page: number) {
  pagination.current = page;
  loadData();
}

function handlePageSizeChange(pageSize: number) {
  pagination.pageSize = pageSize;
  pagination.current = 1;
  loadData();
}
```

#### 5. 搜索处理

```typescript
function handleSearch() {
  pagination.current = 1;
  loadData();
}

function handleReset() {
  // 重置搜索表单
  handleSearch();
}
```

#### 6. 错误处理

```typescript
const message = useMessage();

async function loadData() {
  loading.value = true;
  try {
    const { data: result, error } = await fetchArticleList(params);
    if (!error && result) {
      data.value = result.records;
      pagination.total = result.total;
    }
  } catch (error) {
    message.error("加载失败");
  } finally {
    loading.value = false;
  }
}
```

### 类型检查

- 运行 `pnpm typecheck` 检查类型错误
- 确保所有 API 调用返回值正确解构
- 表单数据类型与接口参数类型一致
- NInput 组件 v-model 值使用字符串类型

---

## 附录

### 接口对接检查清单

- [ ] 接口路径正确（包含 `/api` 前缀）
- [ ] 请求方法正确（GET/POST/PUT/DELETE）
- [ ] 请求参数类型定义完整
- [ ] 响应数据类型定义完整
- [ ] 状态码处理逻辑正确
- [ ] 错误处理完善
- [ ] 文档同步更新

### 页面开发检查清单

- [ ] 使用 NaiveUI 组件
- [ ] 表单数据使用 reactive 定义
- [ ] 列表数据使用 ref 定义
- [ ] 分页逻辑完整
- [ ] 搜索功能实现
- [ ] 新增/编辑弹窗实现
- [ ] 删除确认提示
- [ ] 加载状态处理
- [ ] 错误提示完善
- [ ] 类型检查通过
