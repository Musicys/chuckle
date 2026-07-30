# 作品管理

## 新增作品

**请求方式**：`POST`

**接口路径**：`/api/admin/portfolio/add`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "title": "个人博客系统",
  "description": "基于SpringBoot + Vue3 的博客平台",
  "category": "Java",
  "cover": "https://images.unsplash.com/photo-1587620962725-abab7fe55159",
  "projectUrl": "https://github.com/example/blog",
  "sortOrder": 0,
  "status": 1
}
```

> `cover`、`projectUrl` 不传时默认为空字符串，`sortOrder` 默认 0，`status` 默认 1

**响应示例**：

```json
{
  "code": 0,
  "data": 1,
  "message": "ok"
}
```

---

## 更新作品

**请求方式**：`POST`

**接口路径**：`/api/admin/portfolio/update`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "id": 1,
  "title": "个人博客系统 V2",
  "description": "基于SpringBoot + Vue3 重构的博客平台",
  "category": "Vue",
  "cover": "https://example.com/new-cover.jpg",
  "projectUrl": "https://github.com/example/blog-v2",
  "sortOrder": 1,
  "status": 0
}
```

> 只传需要修改的字段即可

**响应示例**：

```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```

---

## 分页查询作品列表（管理端）

**请求方式**：`POST`

**接口路径**：`/api/admin/portfolio/list`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "current": 1,
  "pageSize": 10,
  "title": "博客",
  "category": "Java",
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
        "title": "个人博客系统",
        "description": "基于SpringBoot + Vue3 的博客平台",
        "category": "Java",
        "cover": "https://images.unsplash.com/photo-1587620962725-abab7fe55159",
        "projectUrl": "",
        "sortOrder": 0,
        "status": 1,
        "createdAt": "2026-07-25 12:00:00",
        "updatedAt": "2026-07-25 12:00:00"
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
| title | string | 否 | 作品名称模糊搜索 |
| category | string | 否 | 分类标签搜索 |
| status | int | 否 | 按状态筛选（0=隐藏 1=展示） |

---

## 删除作品

**请求方式**：`POST`

**接口路径**：`/api/admin/portfolio/delete`

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

---

## 获取所有作品

**请求方式**：`GET`

**接口路径**：`/api/admin/portfolio/all`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：无

**响应示例**：

```json
{
  "code": 0,
  "data": [
    {
      "id": 1,
      "title": "个人博客系统",
      "description": "基于SpringBoot + Vue3 的博客平台",
      "category": "Java",
      "cover": "https://images.unsplash.com/photo-1587620962725-abab7fe55159",
      "projectUrl": "",
      "sortOrder": 0,
      "status": 1,
      "createdAt": "2026-07-25 12:00:00",
      "updatedAt": "2026-07-25 12:00:00"
    }
  ],
  "message": "ok"
}
```
