# 作品展示

## 获取已发布的作品列表

**接口说明**：获取所有已发布（status=1）的作品，按 sort_order 升序排列

**请求方式**：`GET`

**接口路径**：`/api/portfolio/list`

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

**响应字段说明**：

| 字段 | 类型 | 说明 |
|------|------|------|
| [].id | int | 作品ID |
| [].title | string | 作品名称 |
| [].description | string | 作品描述（前端可映射为 desc） |
| [].category | string | 分类标签 |
| [].cover | string | 封面图片URL（前端可映射为 image） |
| [].projectUrl | string | 项目链接 |

**注意事项**：
- 仅返回已发布（status=1）的作品
- 结果按 sort_order 升序排列
- 无需登录即可访问
