# 标签管理

## 新增标签

**请求方式**：`POST`

**接口路径**：`/api/admin/tag/add`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "name": "Java",
  "color": "#E76F00"
}
```

> `color` 不传时默认 `#409EFF`

**响应示例**：

```json
{
  "code": 0,
  "data": 1,
  "message": "ok"
}
```

---

## 更新标签

**请求方式**：`POST`

**接口路径**：`/api/admin/tag/update`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "id": 1,
  "name": "Java 17",
  "color": "#E76F00"
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

## 分页查询标签列表（管理端）

**请求方式**：`POST`

**接口路径**：`/api/admin/tag/list`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "current": 1,
  "pageSize": 10,
  "name": "Java"
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
        "name": "Java",
        "color": "#E76F00"
      },
      {
        "id": 2,
        "name": "Spring Boot",
        "color": "#6DB33F"
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
| name | string | 否 | 标签名称模糊搜索 |

---

## 删除标签

**请求方式**：`POST`

**接口路径**：`/api/admin/tag/delete`

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
