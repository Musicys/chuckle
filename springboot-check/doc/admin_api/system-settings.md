# 系统设置管理

## 新增系统设置

**请求方式**：`POST`

**接口路径**：`/api/admin/system-settings/add`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "settingKey": "site.name",
  "settingValue": "我的博客",
  "description": "站点名称"
}
```

**响应示例**：

```json
{
  "code": 0,
  "data": 1,
  "message": "ok"
}
```

**请求参数说明**：

| 字段         | 类型   | 必填 | 说明           |
| ------------ | ------ | ---- | -------------- |
| settingKey   | string | 是   | 配置键（唯一） |
| settingValue | string | 是   | 配置值         |
| description  | string | 否   | 配置描述       |

---

## 更新系统设置

**请求方式**：`POST`

**接口路径**：`/api/admin/system-settings/update`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "id": 1,
  "settingKey": "site.name",
  "settingValue": "新博客名称",
  "description": "站点名称"
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

**请求参数说明**：

| 字段         | 类型   | 必填 | 说明     |
| ------------ | ------ | ---- | -------- |
| id           | long   | 是   | 设置ID   |
| settingKey   | string | 否   | 配置键   |
| settingValue | string | 否   | 配置值   |
| description  | string | 否   | 配置描述 |

---

## 分页查询系统设置列表

**请求方式**：`POST`

**接口路径**：`/api/admin/system-settings/list`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "current": 1,
  "pageSize": 10,
  "settingKey": "site"
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
        "settingKey": "site.name",
        "settingValue": "我的博客",
        "description": "站点名称",
        "createdAt": "2026-06-01T10:00:00",
        "updatedAt": "2026-06-20T15:30:00"
      },
      {
        "id": 2,
        "settingKey": "site.description",
        "settingValue": "一个技术博客",
        "description": "站点描述",
        "createdAt": "2026-06-01T10:00:00",
        "updatedAt": "2026-06-01T10:00:00"
      }
    ],
    "total": 5,
    "current": 1,
    "size": 10
  },
  "message": "ok"
}
```

**请求参数说明**：

| 字段       | 类型   | 必填 | 说明               |
| ---------- | ------ | ---- | ------------------ |
| current    | int    | 否   | 当前页（默认1）    |
| pageSize   | int    | 否   | 每页条数（默认10） |
| settingKey | string | 否   | 配置键模糊搜索     |

---

## 获取单个设置详情

**请求方式**：`GET`

**接口路径**：`/api/admin/system-settings/get?id=1`

**请求头**：`Authorization: Bearer <token>`

**响应示例**：

```json
{
  "code": 0,
  "data": {
    "id": 1,
    "settingKey": "site.name",
    "settingValue": "我的博客",
    "description": "站点名称",
    "createdAt": "2026-06-01T10:00:00",
    "updatedAt": "2026-06-20T15:30:00"
  },
  "message": "ok"
}
```

**请求参数说明**：

| 字段 | 类型 | 必填 | 说明                    |
| ---- | ---- | ---- | ----------------------- |
| id   | long | 是   | 设置ID（URL query参数） |

---

## 按配置键获取设置

**请求方式**：`GET`

**接口路径**：`/api/admin/system-settings/get-by-key?settingKey=site.name`

**请求头**：`Authorization: Bearer <token>`

**响应示例**：

```json
{
  "code": 0,
  "data": {
    "id": 1,
    "settingKey": "site.name",
    "settingValue": "我的博客",
    "description": "站点名称",
    "createdAt": "2026-06-01T10:00:00",
    "updatedAt": "2026-06-20T15:30:00"
  },
  "message": "ok"
}
```

**请求参数说明**：

| 字段       | 类型   | 必填 | 说明                    |
| ---------- | ------ | ---- | ----------------------- |
| settingKey | string | 是   | 配置键（URL query参数） |

---

## 删除系统设置

**请求方式**：`POST`

**接口路径**：`/api/admin/system-settings/delete`

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

**请求参数说明**：

| 字段 | 类型 | 必填 | 说明   |
| ---- | ---- | ---- | ------ |
| id   | long | 是   | 设置ID |

---

## 获取所有设置

**请求方式**：`GET`

**接口路径**：`/api/admin/system-settings/all`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：无

**响应示例**：

```json
{
  "code": 0,
  "data": [
    {
      "id": 1,
      "settingKey": "site.name",
      "settingValue": "我的博客",
      "description": "站点名称",
      "createdAt": "2026-06-01T10:00:00",
      "updatedAt": "2026-06-20T15:30:00"
    },
    {
      "id": 2,
      "settingKey": "site.description",
      "settingValue": "一个技术博客",
      "description": "站点描述",
      "createdAt": "2026-06-01T10:00:00",
      "updatedAt": "2026-06-01T10:00:00"
    },
    {
      "id": 3,
      "settingKey": "site.icp",
      "settingValue": "京ICP备XXXXXXXX号",
      "description": "ICP备案号",
      "createdAt": "2026-06-01T10:00:00",
      "updatedAt": "2026-06-01T10:00:00"
    }
  ],
  "message": "ok"
}
```
