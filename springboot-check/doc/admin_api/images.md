# 图床管理

## 上传图片

**请求方式**：`POST`

**接口路径**：`/api/admin/image/upload`

**请求头**：`Authorization: Bearer <token>`

**请求格式**：`multipart/form-data`

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| file | File | 是 | 上传的图片文件 |
| source | string | 否 | 图片来源，默认 `blog`（如 `article`/`avatar`/`blog`） |
| remark | string | 否 | 备注 |

> 支持所有常见图片格式。图片上传至阿里云 OSS，自动计算 MD5 去重，已存在的图片直接返回记录。上传后自动获取图片宽高存储。

**响应示例**：

```json
{
  "code": 0,
  "data": {
    "id": 1,
    "originalName": "example.png",
    "url": "https://web-scoke.oss-cn-beijing.aliyuncs.com/blog/2026-07-13/example.png",
    "fileSize": 102400,
    "fileType": "image/png",
    "width": 1920,
    "height": 1080,
    "source": "blog",
    "remark": "",
    "createdAt": "2026-07-13T10:00:00"
  },
  "message": "ok"
}
```

---

## 更新图片信息

**请求方式**：`POST`

**接口路径**：`/api/admin/image/update`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "id": 1,
  "remark": "文章封面图"
}
```

> `id` 为必填，目前支持修改 `remark`（备注）

**响应示例**：

```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```

---

## 获取图片详情

**请求方式**：`GET`

**接口路径**：`/api/admin/image/get?id=1`

**请求头**：`Authorization: Bearer <token>`

**响应示例**：

```json
{
  "code": 0,
  "data": {
    "id": 1,
    "originalName": "example.png",
    "url": "https://web-scoke.oss-cn-beijing.aliyuncs.com/blog/2026-07-13/example.png",
    "storagePath": "blog/2026-07-13/example.png",
    "fileSize": 102400,
    "fileType": "image/png",
    "width": 1920,
    "height": 1080,
    "md5": "d41d8cd98f00b204e9800998ecf8427e",
    "source": "blog",
    "remark": "",
    "createdAt": "2026-07-13T10:00:00",
    "updatedAt": "2026-07-13T10:00:00"
  },
  "message": "ok"
}
```

---

## 分页查询图片列表（管理端）

**请求方式**：`POST`

**接口路径**：`/api/admin/image/list`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "current": 1,
  "pageSize": 10,
  "originalName": "example",
  "fileType": "image/png",
  "source": "blog"
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
        "originalName": "example.png",
        "url": "https://web-scoke.oss-cn-beijing.aliyuncs.com/blog/2026-07-13/example.png",
        "fileSize": 102400,
        "fileType": "image/png",
        "width": 1920,
        "height": 1080,
        "source": "blog",
        "remark": "",
        "createdAt": "2026-07-13T10:00:00"
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
| originalName | string | 否 | 原始文件名模糊搜索 |
| fileType | string | 否 | 文件类型精确匹配，如 `image/png` |
| source | string | 否 | 来源精确匹配 |

---

## 删除图片

**请求方式**：`POST`

**接口路径**：`/api/admin/image/delete`

**请求头**：`Authorization: Bearer <token>`

**请求参数**：

```json
{
  "id": 1
}
```

> 删除时会同步删除 OSS 上的文件，不可恢复

**响应示例**：

```json
{
  "code": 0,
  "data": true,
  "message": "ok"
}
```
