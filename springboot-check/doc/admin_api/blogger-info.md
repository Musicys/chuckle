# 博主信息管理

## 新增博主信息

**请求方式**：`POST`

**接口路径**：`/api/admin/blogger/add`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
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
  "tags": "[\"Java\", \"Vue\", \"Spring Boot\"]",
  "hobbies": "[\"编程\", \"阅读\", \"摄影\"]",
  "games": "[\"原神\", \"星穹铁道\"]",
  "latitude": 30.5728150,
  "longitude": 104.0668010,
  "address": "四川省成都市",
  "announcement": "欢迎来到我的小站 🎉",
  "config": "{\"theme\":\"default\",\"layout\":\"blog\",\"commentEnabled\":true}"
}
```

> `nickname` 为必填，其余字段可选

**响应示例**：

```json
{
  "code": 0,
  "data": 1,
  "message": "ok"
}
```

> `data` 为新增记录的主键 ID

---

## 更新博主信息

**请求方式**：`POST`

**接口路径**：`/api/admin/blogger/update`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "id": 1,
  "nickname": "轻笑Chuckle",
  "blogTitle": "轻笑Chuckle的个人博客(新版)",
  "config": "{\"theme\":\"dark\",\"layout\":\"blog\",\"commentEnabled\":true}"
}
```

> `id` 为必填，只传需要修改的字段即可

**响应示例**：

```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```

---

## 获取博主信息详情（管理端）

**请求方式**：`GET`

**接口路径**：`/api/admin/blogger/get?id=1`

**请求头**：`Authorization: Bearer <token>`

**响应示例**：

```json
{
  "code": 0,
  "data": {
    "id": 1,
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
    "announcement": "欢迎来到我的小站",
    "config": "{\"theme\":\"default\",\"layout\":\"blog\",\"commentEnabled\":true}",
    "isUsed": 1,
    "createdAt": "2026-07-11T10:00:00",
    "updatedAt": "2026-07-11T10:00:00"
  },
  "message": "ok"
}
```

---

## 分页查询博主信息列表（管理端）

**请求方式**：`POST`

**接口路径**：`/api/admin/blogger/list`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "current": 1,
  "pageSize": 10,
  "nickname": "轻笑",
  "blogTitle": "博客",
  "isUsed": 1
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
        "announcement": "欢迎来到我的小站",
        "config": "{\"theme\":\"default\",\"layout\":\"blog\",\"commentEnabled\":true}",
        "isUsed": 1
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
| id | int | 否 | 主键精确匹配 |
| nickname | string | 否 | 昵称模糊搜索 |
| blogTitle | string | 否 | 博客标题模糊搜索 |
| isUsed | int | 否 | 是否启用筛选（0=不使用 1=使用） |

---

## 删除博主信息

**请求方式**：`POST`

**接口路径**：`/api/admin/blogger/delete`

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

---

## 切换配置使用状态

**请求方式**：`POST`

**接口路径**：`/api/admin/blogger/setUsed?id=1`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| id | int | 是 | 要设为启用的博主信息ID |

> 此接口会将指定 ID 的记录设为 `is_used=1`（启用），同时将其他所有记录设为 `is_used=0`（不启用），数据库中保证只有一条启用记录。

**响应示例**：

```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```
