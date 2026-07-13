


# 客户端接口文档

## 博客信息

### 获取博主公开信息

**接口说明**：获取前端首页展示的博主个人信息

**请求方式**：`GET`

**接口路径**：`/api/blogger/info`

**请求参数**：无

**响应示例**：

```json

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
