# 博客信息

## 获取博主公开信息

**接口说明**：获取前端首页展示的博主个人信息

**请求方式**：`GET`

**接口路径**：`/api/blogger/info`

**请求参数**：无

**响应示例**：

```json
{
  "code": 0,
  "data": {
    "avatar": "https://www.dmoe.cc/random.php",
    "nickname": "轻笑Chuckle",
    "blogTitle": "轻笑Chuckle的个人博客",
    "qq": "2825424566@qqcom",
    "github": "https://github.com/liwang",
    "gitee": "https://gitee.com/liwang",
    "bilibili": "https://space.bilibili.com/123456",
    "birthDate": "1998-06-15",
    "gender": 1,
    "occupation": "全栈开发工程师",
    "tags": "[\"Java\", \"Vue\", \"Spring Boot\", \"MySQL\", \"热爱技术\"]",
    "hobbies": "[\"编程\", \"阅读\", \"摄影\", \"羽毛球\", \"旅行\"]",
    "games": "[\"原神\", \"星穹铁道\", \"塞尔达传说\"]",
    "latitude": 30.5728150,
    "longitude": 104.0668010,
    "address": "四川省成都市",
    "announcement": "欢迎来到 **『轻笑Chuckle』** 的小站",
    "config": "{\"theme\":\"default\",\"layout\":\"blog\",\"commentEnabled\":true}",
    "isUsed": 1
  },
  "message": "ok"
}
```

**响应字段说明**：

| 字段 | 类型 | 说明 |
|------|------|------|
| avatar | string | 头像URL |
| nickname | string | 昵称 |
| blogTitle | string | 博客标题 |
| qq | string | QQ号 |
| github | string | GitHub链接 |
| gitee | string | Gitee链接 |
| bilibili | string | Bilibili链接 |
| birthDate | date | 出生年月 |
| gender | int | 性别（0=保密 1=男 2=女） |
| occupation | string | 现职业 |
| tags | string | 个性标签（JSON数组字符串） |
| hobbies | string | 爱好（JSON数组字符串） |
| games | string | 爱好游戏（JSON数组字符串） |
| latitude | decimal | 纬度 |
| longitude | decimal | 经度 |
| address | string | 文字地址 |
| announcement | string | 博客公告（Markdown格式） |
| config | string | 个人配置（JSON格式） |
| isUsed | int | 是否启用（0=不使用 1=使用） |

---

## 获取首页聚合数据

**接口说明**：获取前端首页展示的聚合数据，包含最近文章、标签统计、文章归档和站点概览

**请求方式**：`GET`

**接口路径**：`/api/blogger/home`

**请求参数**：无

**响应示例**：

```json
{
  "code": 0,
  "data": {
    "recentArticles": [
      {
        "id": 1,
        "title": "Spring Boot 项目最佳实践",
        "cover": "https://example.com/cover1.jpg",
        "time": "2026-07-10"
      },
      {
        "id": 2,
        "title": "Vue3 + Pinia 状态管理入门",
        "cover": "https://example.com/cover2.jpg",
        "time": "2026-07-08"
      },
      {
        "id": 3,
        "title": "MySQL 索引优化实战",
        "cover": "https://example.com/cover3.jpg",
        "time": "2026-07-05"
      }
    ],
    "tags": {
      "Java": 15,
      "Spring Boot": 12,
      "Vue": 10,
      "MySQL": 9,
      "Redis": 7,
      "前端": 6,
      "后端": 6,
      "Docker": 5,
      "Linux": 5,
      "架构设计": 4,
      "读书笔记": 3,
      "开源项目": 2
    },
    "articlesByYear": {
      "2026": 45,
      "2025": 120,
      "2024": 98,
      "2023": 76,
      "2022": 54
    },
    "hotArticles": [
      {
        "id": 1,
        "title": "Spring Boot 项目最佳实践",
        "cover": "https://example.com/cover1.jpg"
      },
      {
        "id": 2,
        "title": "MySQL 索引优化实战",
        "cover": "https://example.com/cover3.jpg"
      },
      {
        "id": 3,
        "title": "Vue3 + Pinia 状态管理入门",
        "cover": "https://example.com/cover2.jpg"
      }
    ],
    "siteInfo": {
      "siteUptime": "2022-01-01",
      "totalArticles": 393,
      "lastUpdated": "2026-07-10",
      "totalWords": 1250000
    }
  },
  "message": "ok"
}
```

**响应字段说明**：

| 字段 | 类型 | 说明 |
|------|------|------|
| recentArticles | array | 最近更新的文章列表（取前5篇已发布的文章，按时间倒序） |
| recentArticles[].id | int | 文章ID |
| recentArticles[].title | string | 文章标题 |
| recentArticles[].cover | string | 封面URL |
| recentArticles[].time | string | 发布时间（yyyy-MM-dd） |
| hotArticles | array | 热门文章列表（取前5篇已发布的文章，按阅读量倒序） |
| hotArticles[].id | int | 文章ID |
| hotArticles[].title | string | 文章标题 |
| hotArticles[].cover | string | 封面URL |
| tags | object | 标签及其文章数量映射（Key=标签名，Value=文章数） |
| articlesByYear | object | 按年份统计文章数量（Key=年份，Value=文章数） |
| siteInfo | object | 网站基本信息 |
| siteInfo.siteUptime | string | 网站上线日期 |
| siteInfo.totalArticles | int | 文章总篇数 |
| siteInfo.lastUpdated | string | 最后更新日期 |
| siteInfo.totalWords | int | 文章总字数 |
