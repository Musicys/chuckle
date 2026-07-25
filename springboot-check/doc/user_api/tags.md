# 标签展示

## 获取标签列表（含文章数量）

**接口说明**：获取所有标签及其关联的已发布文章数量，按文章数量降序排列

**请求方式**：`GET`

**接口路径**：`/tags/list`

**请求参数**：无

**响应示例**：

```json
{
  "code": 0,
  "data": [
    {
      "id": 1,
      "name": "Java",
      "color": "#409EFF",
      "articleCount": 12
    },
    {
      "id": 2,
      "name": "Spring Boot",
      "color": "#67C23A",
      "articleCount": 8
    },
    {
      "id": 3,
      "name": "前端",
      "color": "#E6A23C",
      "articleCount": 5
    }
  ],
  "message": "ok"
}
```

**响应字段说明**：

| 字段 | 类型 | 说明 |
|------|------|------|
| [].id | int | 标签ID |
| [].name | string | 标签名称 |
| [].color | string | 标签颜色（十六进制，含 #） |
| [].articleCount | int | 该标签下已发布的文章数量 |

**注意事项**：
- 仅统计已发布（status=1）且未删除（is_delete=0）的文章
- 结果按文章数量降序排列（文章最多的标签排在最前）
- 无文章的标签也会返回，articleCount 为 0
