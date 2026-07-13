# 分类管理

## 新增分类

**请求方式**：`POST`

**接口路径**：`/api/admin/category/add`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "name": "技术",
  "description": "技术相关文章",
  "sortOrder": 1
}
```

> `description` 不传时默认 `""`，`sortOrder` 不传时默认 `0`

**响应示例**：

```json
{
  "code": 0,
  "data": 1,
  "message": "ok"
}
```

---

## 更新分类

**请求方式**：`POST`

**接口路径**：`/api/admin/category/update`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "id": 1,
  "name": "后端技术",
  "description": "后端开发相关文章",
  "sortOrder": 1
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

## 分页查询分类列表（管理端）

**请求方式**：`POST`

**接口路径**：`/api/admin/category/list`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "current": 1,
  "pageSize": 10,
  "name": "技术"
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
        "name": "技术",
        "description": "技术相关文章",
        "sortOrder": 1,
        "createdAt": "2026-06-01T10:00:00"
      },
      {
        "id": 2,
        "name": "生活",
        "description": "生活随笔",
        "sortOrder": 2,
        "createdAt": "2026-06-05T14:00:00"
      }
    ],
    "total": 2,
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
| name | string | 否 | 分类名称模糊搜索 |

---

## 删除分类

**请求方式**：`POST`

**接口路径**：`/api/admin/category/delete`

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
