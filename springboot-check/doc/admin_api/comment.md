# 评论管理

## 分页查询评论列表（管理端）

**请求方式**：`POST`

**接口路径**：`/api/admin/comment/list`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "current": 1,
  "pageSize": 10,
  "articleId": 1,
  "status": 0,
  "nickname": "小明"
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
        "articleId": 1,
        "articleTitle": "Spring Boot 项目最佳实践",
        "parentId": null,
        "replyToCommentId": null,
        "nickname": "小明",
        "email": "xiaoming@example.com",
        "avatar": "https://www.dmoe.cc/random.php",
        "content": "非常实用的文章，项目结构那块很有帮助！",
        "status": 1,
        "ipAddress": "192.168.1.1",
        "createdAt": "2026-06-02T10:30:00"
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
| articleId | int | 否 | 按文章筛选 |
| status | int | 否 | 状态筛选（0=待审核 1=已通过 2=垃圾评论） |
| nickname | string | 否 | 评论者昵称模糊搜索 |

---

## 审核评论

**请求方式**：`POST`

**接口路径**：`/api/admin/comment/review`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "id": 1,
  "status": 1
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

## 管理员回复评论

**请求方式**：`POST`

**接口路径**：`/api/admin/comment/reply`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "articleId": 1,
  "replyToCommentId": 1,
  "content": "谢谢支持！"
}
```

**响应示例**：

```json
{
  "code": 0,
  "data": 20,
  "message": "ok"
}
```

---

## 删除评论

**请求方式**：`POST`

**接口路径**：`/api/admin/comment/delete`

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
