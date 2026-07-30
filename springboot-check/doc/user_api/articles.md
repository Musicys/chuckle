# 文章展示

## 分页获取已发布文章列表

**接口说明**：分页查询已发布的文章列表，支持关键词模糊搜索（标题+正文）、按分类筛选、按标签筛选

**请求方式**：`POST`

**接口路径**：`/articles/list`

**请求参数**（JSON Body）：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| keyword | string | 否 | 搜索关键词（模糊匹配标题+正文） |
| categoryId | int | 否 | 分类ID |
| tagId | int | 否 | 标签ID |
| current | int | 否 | 当前页号（默认 1） |
| pageSize | int | 否 | 页面大小（默认 10） |
| sortField | string | 否 | 排序字段 |
| sortOrder | string | 否 | 排序顺序（ascend / descend） |

**请求示例**：

```json
{
  "keyword": "Spring Boot",
  "categoryId": 1,
  "tagId": 2,
  "current": 1,
  "pageSize": 10
}
```

**响应示例**：

```json
{
  "code": 0,
  "data": {
    "records": [
      {
        "id": 1,
        "title": "Spring Boot 项目最佳实践",
        "summary": "本文总结了 Spring Boot 项目开发中的最佳实践...",
        "cover": "https://example.com/cover1.jpg",
        "category": {
          "id": 1,
          "name": "后端开发"
        },
        "tags": [
          { "id": 1, "name": "Java", "color": "#409EFF" },
          { "id": 2, "name": "Spring Boot", "color": "#67C23A" }
        ],
        "wordCount": 3500,
        "readCount": 1280,
        "commentCount": 15,
        "isNew": true,
        "createdAt": "2026-07-15 10:30:00",
        "updatedAt": "2026-07-18 14:20:00"
      }
    ],
    "total": 50,
    "current": 1,
    "size": 10,
    "pages": 5
  },
  "message": "ok"
}
```

**响应字段说明**：

| 字段 | 类型 | 说明 |
|------|------|------|
| records[].id | int | 文章ID |
| records[].title | string | 文章标题 |
| records[].summary | string | 文章简介 |
| records[].cover | string | 封面URL |
| records[].category | object | 分类信息（JSON对象） |
| records[].category.id | int | 分类ID |
| records[].category.name | string | 分类名称 |
| records[].tags | array | 标签列表 |
| records[].tags[].id | int | 标签ID |
| records[].tags[].name | string | 标签名称 |
| records[].tags[].color | string | 标签颜色 |
| records[].wordCount | int | 字数 |
| records[].readCount | int | 阅读量（PV） |
| records[].commentCount | int | 评论数 |
| records[].isNew | bool | 是否最新文章（一周内发布为 true） |
| records[].createdAt | datetime | 发布时间 |
| records[].updatedAt | datetime | 更新时间 |
| total | int | 总记录数 |
| current | int | 当前页号 |
| size | int | 每页条数 |
| pages | int | 总页数 |

---

## 获取文章详情

**接口说明**：根据文章ID获取已发布文章的详细信息，包含 Markdown 正文内容，同时自动增加该文章的阅读量（+1）

**请求方式**：`GET`

**接口路径**：`/articles/{id}`

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| id | int | 是 | 文章ID（路径参数） |

**响应示例**：

```json
{
  "code": 0,
  "data": {
    "id": 1,
    "title": "Spring Boot 项目最佳实践",
    "summary": "本文总结了 Spring Boot 项目开发中的最佳实践...",
    "cover": "https://example.com/cover1.jpg",
    "content": "# Spring Boot 项目最佳实践\n\n## 目录结构\n...（Markdown 全文）",
    "category": {
      "id": 1,
      "name": "后端开发"
    },
    "tags": [
      { "id": 1, "name": "Java", "color": "#409EFF" },
      { "id": 2, "name": "Spring Boot", "color": "#67C23A" }
    ],
    "wordCount": 3500,
    "readCount": 1281,
    "commentCount": 15,
    "isNew": true,
    "createdAt": "2026-07-15 10:30:00",
    "updatedAt": "2026-07-18 14:20:00"
  },
  "message": "ok"
}
```

**响应字段说明**：

| 字段 | 类型 | 说明 |
|------|------|------|
| id | int | 文章ID |
| title | string | 文章标题 |
| summary | string | 文章简介 |
| cover | string | 封面URL |
| content | string | Markdown 正文内容 |
| category | object | 分类信息（JSON对象） |
| category.id | int | 分类ID |
| category.name | string | 分类名称 |
| tags | array | 标签列表 |
| tags[].id | int | 标签ID |
| tags[].name | string | 标签名称 |
| tags[].color | string | 标签颜色 |
| wordCount | int | 字数 |
| readCount | int | 阅读量（PV，每次访问 +1） |
| commentCount | int | 评论数 |
| isNew | bool | 是否最新文章（一周内发布为 true） |
| createdAt | datetime | 发布时间 |
| updatedAt | datetime | 更新时间 |

**注意事项**：
- 每次调用该接口会将对应文章的阅读量（readCount）增加 1
- 仅返回已发布（status=1）、未逻辑删除（is_delete=0）的文章
- 若文章不存在或已下架，返回 40400 错误码

---

## 提交评论（或回复评论）

**接口说明**：用户提交文章评论，或回复已有评论。提交后状态为待审核，需管理员审核通过后展示

**请求方式**：`POST`

**接口路径**：`/articles/comment`

**请求参数**（JSON Body）：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| articleId | int | 是 | 所属文章ID |
| nickname | string | 是 | 评论者昵称 |
| email | string | 否 | 评论者邮箱（用于回复通知） |
| avatar | string | 否 | 评论者头像URL |
| content | string | 是 | 评论内容 |
| replyToCommentId | int | 否 | 被回复的评论ID（不传则为一级评论） |

**请求示例**（一级评论）：

```json
{
  "articleId": 1,
  "nickname": "游客小明",
  "email": "xiaoming@example.com",
  "avatar": "https://example.com/avatar.jpg",
  "content": "写的很好，学习了！"
}
```

**请求示例**（回复评论）：

```json
{
  "articleId": 1,
  "nickname": "游客小红",
  "email": "xiaohong@example.com",
  "content": "同意！确实是这样的",
  "replyToCommentId": 1
}
```

**响应示例**：

```json
{
  "code": 0,
  "data": 3,
  "message": "ok"
}
```

**响应字段说明**：

| 字段 | 类型 | 说明 |
|------|------|------|
| data | int | 评论ID |

**注意事项**：
- 评论提交后状态为 `待审核（0）`，需管理员审核通过后才会在前端展示
- 回复评论时需传入 `replyToCommentId` 指定被回复的评论ID
- 评论时会自动记录评论者的 IP 地址和 User-Agent
- 提交评论后对应文章的评论数（commentCount）会自动 +1
- 若文章不存在或未发布，返回 40400 错误码

---

## 获取文章评论列表

**接口说明**：获取指定文章的已通过评论，返回树状层级结构（一级评论嵌套子回复）

**请求方式**：`GET`

**接口路径**：`/articles/{id}/comments`

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| id | int | 是 | 文章ID（路径参数） |

**响应示例**：

```json
{
  "code": 0,
  "data": [
    {
      "id": 1,
      "nickname": "游客小明",
      "email": "xiaoming@example.com",
      "avatar": "https://example.com/avatar.jpg",
      "content": "写的很好，学习了！",
      "replyToCommentId": null,
      "createdAt": "2026-07-18 10:30:00",
      "children": [
        {
          "id": 2,
          "nickname": "博主",
          "email": "",
          "avatar": "https://example.com/avatar2.jpg",
          "content": "感谢支持！",
          "replyToCommentId": 1,
          "createdAt": "2026-07-18 11:00:00",
          "children": []
        }
      ]
    },
    {
      "id": 3,
      "nickname": "游客小红",
      "email": "xiaohong@example.com",
      "avatar": "",
      "content": "有个问题想请教一下",
      "replyToCommentId": null,
      "createdAt": "2026-07-19 14:00:00",
      "children": []
    }
  ],
  "message": "ok"
}
```

**响应字段说明**：

| 字段 | 类型 | 说明 |
|------|------|------|
| [].id | int | 评论ID |
| [].nickname | string | 评论者昵称 |
| [].email | string | 评论者邮箱 |
| [].avatar | string | 评论者头像URL |
| [].content | string | 评论内容 |
| [].replyToCommentId | int | 被回复的评论ID（null 为一级评论） |
| [].createdAt | datetime | 评论时间 |
| [].children | array | 子回复列表（与父结构相同，递归嵌套） |

**注意事项**：
- 仅返回已审核通过（status=1）的评论
- 一级评论按时间倒序排列（最新的在前），子回复按时间正序排列
- `children` 字段始终为数组，无子回复时返回空数组`[]`
- 通过 `replyToCommentId` 可判断是否为回复以及被回复的目标

---

## 搜索文章（含高亮）

**接口说明**：搜索已发布的文章，对匹配的关键词进行后端高亮处理，返回带 `<em class="highlight">` 标记的标题和简介。

**请求方式**：`POST`

**接口路径**：`/articles/search`

**请求参数**（JSON Body）：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| keyword | string | 否 | 搜索关键词（模糊匹配标题+正文，多个词用空格分隔） |
| categoryId | int | 否 | 分类ID |
| tagId | int | 否 | 标签ID |
| current | int | 否 | 当前页号（默认 1） |
| pageSize | int | 否 | 页面大小（默认 10） |
| sortField | string | 否 | 排序字段 |
| sortOrder | string | 否 | 排序顺序（ascend / descend） |

**请求示例**：

```json
{
  "keyword": "人工智能 深度学习",
  "current": 1,
  "pageSize": 10
}
```

**响应示例**：

```json
{
  "code": 0,
  "data": {
    "records": [
      {
        "id": 1,
        "title": "人工智能在医疗领域的应用",
        "titleHighlight": "<em class=\"highlight\">人工智能</em>在医疗领域的应用",
        "summary": "本文探讨了人工智能技术与深度学习的结合",
        "summaryHighlight": "本文探讨了<em class=\"highlight\">人工智能</em>技术与<em class=\"highlight\">深度学习</em>的结合",
        "cover": "https://example.com/cover1.jpg",
        "category": {
          "id": 1,
          "name": "人工智能"
        },
        "tags": [
          { "id": 1, "name": "AI", "color": "#409EFF" }
        ],
        "wordCount": 3500,
        "readCount": 1280,
        "commentCount": 15,
        "isNew": false,
        "createdAt": "2026-07-10 10:30:00",
        "updatedAt": "2026-07-12 14:20:00"
      }
    ],
    "total": 10,
    "current": 1,
    "size": 10,
    "pages": 1
  },
  "message": "ok"
}
```

**响应字段说明**：

| 字段 | 类型 | 说明 |
|------|------|------|
| records[].id | int | 文章ID |
| records[].title | string | 文章标题（原始文本） |
| records[].titleHighlight | string | 高亮标题（含 `<em class="highlight">` 标记） |
| records[].summary | string | 文章简介（原始文本） |
| records[].summaryHighlight | string | 高亮简介（含 `<em class="highlight">` 标记） |
| records[].cover | string | 封面URL |
| records[].category | object | 分类信息 |
| records[].category.id | int | 分类ID |
| records[].category.name | string | 分类名称 |
| records[].tags | array | 标签列表 |
| records[].tags[].id | int | 标签ID |
| records[].tags[].name | string | 标签名称 |
| records[].tags[].color | string | 标签颜色 |
| records[].wordCount | int | 字数 |
| records[].readCount | int | 阅读量 |
| records[].commentCount | int | 评论数 |
| records[].isNew | bool | 是否最新文章 |
| records[].createdAt | datetime | 发布时间 |
| records[].updatedAt | datetime | 更新时间 |
| total | int | 总记录数 |
| current | int | 当前页号 |
| size | int | 每页条数 |
| pages | int | 总页数 |

**高亮规则说明**：
- 关键词按空格拆分为多个词，逐个进行不区分大小写的高亮匹配
- 长词优先匹配，避免短词先匹配导致长词匹配失败
- 先对文本做 HTML 转义（`&` → `&amp;`，`<` → `&lt;`，`>` → `&gt;`，`"` → `&quot;`），再包裹 `<em class="highlight">` 标签，防止 XSS 注入
- 仅对标题（title）和简介（summary）做高亮，正文（content）不在搜索结果中返回
- 若未传入 keyword 或 keyword 为空，则不进行高亮处理，titleHighlight / summaryHighlight 与 title / summary 内容相同

---

## 获取文章归档列表

**接口说明**：获取所有已发布文章，按年份分组归档。每年度最多返回 100 篇文章，按发布时间倒序排列。

**请求方式**：`GET`

**接口路径**：`/api/articles/archives`

**请求参数**：无

**响应示例**：

```json
{
  "code": 0,
  "data": [
    {
      "year": 2026,
      "count": 15,
      "articles": [
        {
          "id": 42,
          "title": "Vue3 组合式 API 入门教程",
          "summary": "详细讲解Vue3组合式API的使用方法，包括setup函数、ref、reactive、computed等核心概念。",
          "cover": "https://example.com/cover.jpg",
          "readCount": 1024,
          "commentCount": 8,
          "createdAt": "2026-07-28 10:30:00"
        }
      ]
    },
    {
      "year": 2025,
      "count": 8,
      "articles": [
        {
          "id": 10,
          "title": "Spring Boot 入门指南",
          "summary": "从零开始搭建 Spring Boot 项目，涵盖常用配置和最佳实践。",
          "cover": "https://example.com/spring-boot.jpg",
          "readCount": 2048,
          "commentCount": 15,
          "createdAt": "2025-03-15 14:20:00"
        }
      ]
    }
  ],
  "message": "ok"
}
```

**响应字段说明**：

| 字段 | 类型 | 说明 |
|------|------|------|
| [].year | int | 年份 |
| [].count | int | 该年文章数量 |
| [].articles | array | 该年文章列表（最多 100 条） |
| [].articles[].id | int | 文章ID |
| [].articles[].title | string | 文章标题 |
| [].articles[].summary | string | 文章简介 |
| [].articles[].cover | string | 封面URL |
| [].articles[].readCount | int | 阅读量 |
| [].articles[].commentCount | int | 评论数 |
| [].articles[].createdAt | datetime | 发布时间 |

**注意事项**：
- 仅返回已发布（status=1）且未删除的文章
- 按年份倒序排列（最新的年份在前），同一年内按发布时间倒序
- 每年最多返回 100 篇文章
- 无需登录即可访问
- 适合归档页面的"时间轴"展示场景
