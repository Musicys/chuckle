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
