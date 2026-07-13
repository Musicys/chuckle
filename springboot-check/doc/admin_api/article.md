# 文章管理

## 新增文章

**请求方式**：`POST`

**接口路径**：`/api/admin/article/add`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "title": "文章标题",
  "content": "Markdown 正文内容",
  "summary": "文章简介",
  "categoryId": 1,
  "status": 1,
  "tagIds": [1, 2, 3]
}
```

**响应示例**：

```json
{
  "code": 0,
  "data": 1,
  "message": "ok"
}
```

---

## 更新文章

**请求方式**：`POST`

**接口路径**：`/api/admin/article/update`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "id": 1,
  "title": "新标题",
  "content": "新内容",
  "summary": "新简介",
  "categoryId": 2,
  "status": 1,
  "tagIds": [2, 4]
}
```

> 传 `tagIds` 会全量替换标签；不传则不修改标签

**响应示例**：

```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```

---

## 获取文章详情（管理端）

**请求方式**：`GET`

**接口路径**：`/api/admin/article/get?id=1`

**请求头**：`Authorization: Bearer <token>`

**响应示例**：

```json
{
  "code": 0,
  "data": {
    "id": 1,
    "title": "文章标题",
    "content": "Markdown 正文",
    "summary": "文章简介",
    "categoryId": 1,
    "categoryName": "技术",
    "tags": [
      { "id": 1, "name": "Java", "color": "#E76F00" },
      { "id": 2, "name": "Spring Boot", "color": "#6DB33F" }
    ],
    "wordCount": 580,
    "readCount": 328,
    "commentCount": 5,
    "status": 1,
    "createdAt": "2026-06-01T10:00:00",
    "updatedAt": "2026-06-20T15:30:00"
  },
  "message": "ok"
}
```

---

## 分页查询文章列表（管理端）

**请求方式**：`POST`

**接口路径**：`/api/admin/article/list`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "current": 1,
  "pageSize": 10,
  "title": "Spring",
  "categoryId": 1,
  "status": 1
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
        "summary": "Spring Boot 项目的最佳实践总结...",
        "categoryId": 1,
        "categoryName": "技术",
        "tags": [{ "id": 1, "name": "Java", "color": "#E76F00" }],
        "wordCount": 580,
        "readCount": 328,
        "commentCount": 5,
        "status": 1,
        "createdAt": "2026-06-01T10:00:00",
        "updatedAt": "2026-06-20T15:30:00"
      }
    ],
    "total": 1,
    "current": 1,
    "size": 10
  },
  "message": "ok"
}
```

**请求参数说明**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| current | int | 否 | 当前页（默认1） |
| pageSize | int | 否 | 每页条数（默认10） |
| title | string | 否 | 标题模糊搜索 |
| categoryId | int | 否 | 分类筛选 |
| status | int | 否 | 状态筛选（0=草稿 1=已发布 2=下架） |

---

## 删除文章（逻辑删除）

**请求方式**：`POST`

**接口路径**：`/api/admin/article/delete`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "id": 1
}
```

**响应示例**：

```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```
