# 每日统计管理

## 分页查询每日统计列表

**请求方式**：`POST`

**接口路径**：`/api/admin/daily-stats/list`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "current": 1,
  "pageSize": 10,
  "startDate": "2026-07-01",
  "endDate": "2026-07-31"
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
        "statDate": "2026-07-28",
        "pv": 1520,
        "uv": 328,
        "createdAt": "2026-07-28T23:59:59",
        "updatedAt": "2026-07-28T23:59:59"
      },
      {
        "id": 2,
        "statDate": "2026-07-27",
        "pv": 1432,
        "uv": 312,
        "createdAt": "2026-07-27T23:59:59",
        "updatedAt": "2026-07-27T23:59:59"
      }
    ],
    "total": 30,
    "current": 1,
    "size": 10
  },
  "message": "ok"
}
```

**请求参数说明**：

| 字段      | 类型   | 必填 | 说明                         |
| --------- | ------ | ---- | ---------------------------- |
| current   | int    | 否   | 当前页（默认1）              |
| pageSize  | int    | 否   | 每页条数（默认10）           |
| startDate | string | 否   | 开始日期（格式：yyyy-MM-dd） |
| endDate   | string | 否   | 结束日期（格式：yyyy-MM-dd） |

---

## 获取单条统计详情

**请求方式**：`GET`

**接口路径**：`/api/admin/daily-stats/get?id=1`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

| 字段 | 类型 | 必填 | 说明       |
| ---- | ---- | ---- | ---------- |
| id   | long | 是   | 统计记录ID |

**响应示例**：

```json
{
  "code": 0,
  "data": {
    "id": 1,
    "statDate": "2026-07-28",
    "pv": 1520,
    "uv": 328,
    "createdAt": "2026-07-28T23:59:59",
    "updatedAt": "2026-07-28T23:59:59"
  },
  "message": "ok"
}
```

---

## 删除统计记录

**请求方式**：`POST`

**接口路径**：`/api/admin/daily-stats/delete`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "id": 1
}
```

**请求参数说明**：

| 字段 | 类型 | 必填 | 说明       |
| ---- | ---- | ---- | ---------- |
| id   | long | 是   | 统计记录ID |

**响应示例**：

```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```

---

## 获取统计汇总

**请求方式**：`GET`

**接口路径**：`/api/admin/daily-stats/summary`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：无

**响应示例**：

```json
{
  "code": 0,
  "data": {
    "totalPv": 45680,
    "totalUv": 9820,
    "todayPv": 1520,
    "todayUv": 328
  },
  "message": "ok"
}
```

**响应字段说明**：

| 字段    | 类型 | 说明                 |
| ------- | ---- | -------------------- |
| totalPv | long | 历史总访问量（PV）   |
| totalUv | long | 历史独立访客数（UV） |
| todayPv | long | 今日访问量（PV）     |
| todayUv | long | 今日独立访客数（UV） |
