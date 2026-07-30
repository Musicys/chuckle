# 访问日志管理

## 分页查询访问日志列表

**请求方式**：`POST`

**接口路径**：`/api/admin/visit-logs/list`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "current": 1,
  "pageSize": 10,
  "ip": "192.168.1.1",
  "pageUrl": "/article/1",
  "startDate": "2026-07-01 00:00:00",
  "endDate": "2026-07-28 23:59:59"
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
        "ip": "192.168.1.1",
        "pageUrl": "/article/1",
        "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36",
        "referer": "https://example.com",
        "createdAt": "2026-07-28 12:00:00"
      }
    ],
    "total": 100,
    "current": 1,
    "size": 10
  },
  "message": "ok"
}
```

**请求参数说明**：

| 字段      | 类型   | 必填 | 说明                            |
| --------- | ------ | ---- | ------------------------------- |
| current   | int    | 否   | 当前页（默认1）                 |
| pageSize  | int    | 否   | 每页条数（默认10）              |
| ip        | string | 否   | IP地址模糊搜索                  |
| pageUrl   | string | 否   | 访问页面路径模糊搜索            |
| startDate | string | 否   | 起始时间（yyyy-MM-dd HH:mm:ss） |
| endDate   | string | 否   | 结束时间（yyyy-MM-dd HH:mm:ss） |

---

## 删除访问日志

**请求方式**：`POST`

**接口路径**：`/api/admin/visit-logs/delete`

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
| id   | long | 是   | 日志ID |

---

## 批量删除访问日志

**请求方式**：`POST`

**接口路径**：`/api/admin/visit-logs/batch-delete`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "ids": [1, 2, 3, 4, 5]
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

| 字段 | 类型        | 必填 | 说明       |
| ---- | ----------- | ---- | ---------- |
| ids  | array<long> | 是   | 日志ID数组 |

---

## 获取访问统计汇总

**请求方式**：`GET`

**接口路径**：`/api/admin/visit-logs/stats`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：无

**响应示例**：

```json
{
  "code": 0,
  "data": {
    "todayPv": 128,
    "todayUv": 56,
    "monthPv": 3680,
    "totalPv": 52860
  },
  "message": "ok"
}
```

**响应字段说明**：

| 字段    | 类型 | 说明                 |
| ------- | ---- | -------------------- |
| todayPv | int  | 今日PV（页面浏览量） |
| todayUv | int  | 今日UV（独立访客数） |
| monthPv | int  | 本月PV               |
| totalPv | int  | 总PV                 |
