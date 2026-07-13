# 管理员认证

## 管理员登录

**接口说明**：管理员账号密码登录，返回 JWT Token 和刷新令牌

**请求方式**：`POST`

**接口路径**：`/api/auth/login`

**请求参数**：

```json
{
  "userName": "Admin",
  "password": "123456"
}
```

**响应示例**：

```json
{
  "code": 0,
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "a1b2c3d4e5f6..."
  },
  "message": "ok"
}
```

**响应字段说明**：

| 字段 | 类型 | 说明 |
|------|------|------|
| token | string | JWT 访问令牌（7天有效） |
| refreshToken | string | 刷新令牌（用于续期） |

---

## 获取当前管理员信息

**接口说明**：根据 JWT Token 获取当前登录管理员信息

**请求方式**：`GET`

**接口路径**：`/api/auth/getUserInfo`

**请求头**：`Authorization: Bearer <token>`

**响应示例**：

```json
{
  "code": 0,
  "data": {
    "userId": "2",
    "userName": "Admin",
    "roles": ["R_ADMIN"],
    "buttons": ["*:*:*"]
  },
  "message": "ok"
}
```

**响应字段说明**：

| 字段 | 类型 | 说明 |
|------|------|------|
| userId | string | 用户ID |
| userName | string | 用户名 |
| roles | string[] | 角色列表（R_ADMIN=管理员 R_SUPER=超级管理员） |
| buttons | string[] | 按钮权限列表 |

---

## 刷新 Token

**接口说明**：使用刷新令牌换取新的 JWT Token

**请求方式**：`POST`

**接口路径**：`/api/auth/refreshToken`

**请求参数**：

```json
{
  "refreshToken": "a1b2c3d4e5f6..."
}
```

**响应示例**：

```json
{
  "code": 0,
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "f6e5d4c3b2a1..."
  },
  "message": "ok"
}
```
