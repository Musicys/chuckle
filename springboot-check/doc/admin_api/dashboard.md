# 仪表盘管理

## 获取仪表盘统计数据

**请求方式**：`GET`

**接口路径**：`/api/admin/dashboard/stats`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：无

**响应示例**：

```json
{
  "code": 0,
  "data": {
    "articleCount": 128,
    "commentCount": 560,
    "portfolioCount": 24,
    "friendLinkCount": 18,
    "todayPv": 128,
    "todayUv": 56,
    "totalPv": 52860,
    "recentArticles": [
      {
        "id": 1,
        "title": "Spring Boot 实战指南",
        "status": 1,
        "createdAt": "2026-07-28 12:00:00"
      }
    ],
    "recentComments": [
      {
        "id": 1,
        "nickname": "张三",
        "content": "写得非常好，受益匪浅！",
        "createdAt": "2026-07-28 12:30:00"
      }
    ]
  },
  "message": "ok"
}
```

**响应字段说明**：

| 字段 | 类型 | 说明 |
|------|------|------|
| articleCount | int | 文章总数 |
| commentCount | int | 评论总数 |
| portfolioCount | int | 作品总数 |
| friendLinkCount | int | 友链总数 |
| todayPv | int | 今日PV（页面浏览量） |
| todayUv | int | 今日UV（独立访客数） |
| totalPv | int | 总访问量 |
| recentArticles | array | 最近5篇文章 |
| recentArticles[].id | long | 文章ID |
| recentArticles[].title | string | 文章标题 |
| recentArticles[].status | int | 状态：0-草稿，1-已发布 |
| recentArticles[].createdAt | string | 创建时间 |
| recentComments | array | 最近5条评论 |
| recentComments[].id | long | 评论ID |
| recentComments[].nickname | string | 评论者昵称 |
| recentComments[].content | string | 评论内容 |
| recentComments[].createdAt | string | 创建时间 |

---

## 获取访问趋势数据（最近7天）

**请求方式**：`GET`

**接口路径**：`/api/admin/dashboard/visit-trend`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：无

**响应示例**：

```json
{
  "code": 0,
  "data": {
    "trend": [
      {
        "date": "2026-07-22 00:00:00",
        "pv": 120,
        "uv": 45
      },
      {
        "date": "2026-07-23 00:00:00",
        "pv": 135,
        "uv": 52
      },
      {
        "date": "2026-07-24 00:00:00",
        "pv": 142,
        "uv": 58
      },
      {
        "date": "2026-07-25 00:00:00",
        "pv": 168,
        "uv": 65
      },
      {
        "date": "2026-07-26 00:00:00",
        "pv": 175,
        "uv": 70
      },
      {
        "date": "2026-07-27 00:00:00",
        "pv": 192,
        "uv": 78
      },
      {
        "date": "2026-07-28 00:00:00",
        "pv": 128,
        "uv": 56
      }
    ]
  },
  "message": "ok"
}
```

**响应字段说明**：

| 字段 | 类型 | 说明 |
|------|------|------|
| trend | array | 趋势数据数组 |
| trend[].date | string | 统计日期 |
| trend[].pv | int | 页面访问量 |
| trend[].uv | int | 独立访客数 |

---

## 获取内容统计数据

**请求方式**：`GET`

**接口路径**：`/api/admin/dashboard/content-stats`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：无

**响应示例**：

```json
{
  "code": 0,
  "data": {
    "articleStatus": {
      "published": 100,
      "draft": 28
    },
    "categoryStats": [
      {
        "category_id": 1,
        "count": 45
      },
      {
        "category_id": 2,
        "count": 32
      },
      {
        "category_id": 3,
        "count": 28
      },
      {
        "category_id": 4,
        "count": 15
      },
      {
        "category_id": 5,
        "count": 8
      }
    ]
  },
  "message": "ok"
}
```

**响应字段说明**：

| 字段 | 类型 | 说明 |
|------|------|------|
| articleStatus | object | 文章状态统计 |
| articleStatus.published | long | 已发布文章数 |
| articleStatus.draft | long | 草稿文章数 |
| categoryStats | array | 分类统计（Top 5） |
| categoryStats[].category_id | int | 分类ID |
| categoryStats[].count | long | 文章数量 |
