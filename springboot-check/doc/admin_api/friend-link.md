# 友链管理

## 新增友链

**请求方式**：`POST`

**接口路径**：`/api/admin/friendLink/add`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "name": "轻笑Chuckle",
  "url": "https://www.cnblogs.com/chuckle/",
  "avatar": "https://example.com/avatar.jpg",
  "description": "个人技术博客",
  "email": "admin@example.com",
  "sortOrder": 0,
  "status": 1
}
```

> `avatar`、`description`、`email` 不传时默认为空字符串，`sortOrder` 默认 0，`status` 默认 1

**响应示例**：

```json
{
  "code": 0,
  "data": 1,
  "message": "ok"
}
```

---

## 更新友链

**请求方式**：`POST`

**接口路径**：`/api/admin/friendLink/update`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "id": 1,
  "name": "轻笑Chuckle",
  "url": "https://www.cnblogs.com/chuckle/",
  "avatar": "https://example.com/new-avatar.jpg",
  "description": "更新的描述",
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

## 分页查询友链列表（管理端）

**请求方式**：`POST`

**接口路径**：`/api/admin/friendLink/list`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "current": 1,
  "pageSize": 10,
  "name": "Chuckle",
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
        "name": "轻笑Chuckle",
        "url": "https://www.cnblogs.com/chuckle/",
        "avatar": "https://example.com/avatar.jpg",
        "description": "个人技术博客",
        "email": "admin@example.com",
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
| name | string | 否 | 友站名称模糊搜索 |
| status | int | 否 | 按状态筛选（0=隐藏 1=展示） |

---

## 删除友链

**请求方式**：`POST`

**接口路径**：`/api/admin/friendLink/delete`

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

## 获取所有友链

**请求方式**：`GET`

**接口路径**：`/api/admin/friendLink/all`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：无

**响应示例**：

```json
{
  "code": 0,
  "data": [
    {
      "id": 1,
      "name": "轻笑Chuckle",
      "url": "https://www.cnblogs.com/chuckle/",
      "avatar": "https://example.com/avatar.jpg",
      "description": "个人技术博客",
      "email": "admin@example.com",
      "sortOrder": 0,
      "status": 1,
      "createdAt": "2026-07-25 12:00:00",
      "updatedAt": "2026-07-25 12:00:00"
    }
  ],
  "message": "ok"
}
```
