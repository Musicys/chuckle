# 留言板

## 获取留言列表

**接口说明**：获取留言板的所有已通过留言，返回树状层级结构（一级留言嵌套子回复）

**请求方式**：`GET`

**接口路径**：`/messages/list`

**请求参数**：无

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
      "content": "博主你好，请问这个博客用的什么技术栈？",
      "replyToCommentId": null,
      "createdAt": "2026-07-20 10:30:00",
      "children": [
        {
          "id": 2,
          "nickname": "博主",
          "email": "",
          "avatar": "https://example.com/avatar2.jpg",
          "content": "用的 Spring Boot + Vue 全家桶",
          "replyToCommentId": 1,
          "createdAt": "2026-07-20 11:00:00",
          "children": []
        }
      ]
    },
    {
      "id": 3,
      "nickname": "游客小红",
      "email": "xiaohong@example.com",
      "avatar": "",
      "content": "来踩个场~",
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
| [].id | int | 留言ID |
| [].nickname | string | 留言者昵称 |
| [].email | string | 留言者邮箱 |
| [].avatar | string | 留言者头像URL |
| [].content | string | 留言内容 |
| [].replyToCommentId | int | 被回复的留言ID（null 为一级留言） |
| [].createdAt | datetime | 留言时间 |
| [].children | array | 子回复列表（与父结构相同，递归嵌套） |

**注意事项**：
- 仅返回已审核通过（status=1）的留言
- 一级留言按时间倒序排列（最新的在前），子回复按时间正序排列
- `children` 字段始终为数组，无子回复时返回空数组 `[]`
- 通过 `replyToCommentId` 可判断是否为回复以及被回复的目标
- 留言板数据使用 `article_id = 0` 标识，区别于文章评论

---

## 提交留言（或回复留言）

**接口说明**：用户提交留言，或回复已有留言。留言提交后状态为已通过，直接在前端展示

**请求方式**：`POST`

**接口路径**：`/messages/comment`

**请求参数**（JSON Body）：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| nickname | string | 是 | 留言者昵称 |
| email | string | 否 | 留言者邮箱（用于回复通知） |
| avatar | string | 否 | 留言者头像URL |
| content | string | 是 | 留言内容 |
| replyToCommentId | int | 否 | 被回复的留言ID（不传则为一级留言） |

**请求示例**（一级留言）：

```json
{
  "nickname": "游客小明",
  "email": "xiaoming@example.com",
  "avatar": "https://example.com/avatar.jpg",
  "content": "博主你好，请问这个博客用的什么技术栈？"
}
```

**请求示例**（回复留言）：

```json
{
  "nickname": "博主",
  "email": "",
  "content": "用的 Spring Boot + Vue 全家桶",
  "replyToCommentId": 1
}
```

**响应示例**：

```json
{
  "code": 0,
  "data": 4,
  "message": "ok"
}
```

**响应字段说明**：

| 字段 | 类型 | 说明 |
|------|------|------|
| data | int | 留言ID |

**注意事项**：
- 回复留言时需传入 `replyToCommentId` 指定被回复的留言ID
- 留言时会自动记录留言者的 IP 地址和 User-Agent
- 不能回复文章评论区的评论，仅能回复留言板本身的留言
- 留言板数据使用 `article_id = 0` 标识（数据库字段为 `INT UNSIGNED`，不能使用负数或 NULL）
