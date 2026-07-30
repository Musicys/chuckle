# 友链展示

## 获取已发布的友链列表

**接口说明**：获取所有已发布（status=1）的友链，按 sort_order 升序排列

**请求方式**：`GET`

**接口路径**：`/api/friendLinks/list`

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
      "email": "",
      "sortOrder": 0,
      "status": 1,
      "createdAt": "2026-07-25 12:00:00",
      "updatedAt": "2026-07-25 12:00:00"
    }
  ],
  "message": "ok"
}
```

**响应字段说明**：

| 字段 | 类型 | 说明 |
|------|------|------|
| [].id | int | 友链ID |
| [].name | string | 友站名称 |
| [].url | string | 友站URL |
| [].avatar | string | 友站头像URL（前端可映射为 icon） |
| [].description | string | 站点描述 |
| [].sortOrder | int | 排序值 |

**注意事项**：
- 仅返回已发布（status=1）的友链
- 结果按 sort_order 升序排列
- 无需登录即可访问
