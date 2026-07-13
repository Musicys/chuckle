


# 志愿资料获取系统 (lipao-backend)

基于 Spring Boot 2.7 的志愿资料管理及分发后端服务。管理员上传各省份资料（如高考志愿填报资料），用户通过卡密兑换解锁下载权限，支持 OSS 云端存储与 Elasticsearch 全文检索。

## 技术栈

| 框架/中间件     | 版本    | 用途               |
| --------------- | ------- | ------------------ |
| Spring Boot     | 2.7.2   | 基础框架           |
| Java            | 8       | 运行环境           |
| MyBatis-Plus    | 3.5.2   | ORM + 分页         |
| MySQL           | -       | 关系数据库         |
| Redis           | -       | 会话缓存（未启用） |
| Elasticsearch   | -       | 帖子全文检索       |
| Aliyun OSS      | 3.17.4  | 文件云端存储       |
| JWT (Hutool)    | 5.8.8   | Token 鉴权         |
| Knife4j/Swagger | 3.0.3   | 接口文档           |
| Freemarker      | -       | 代码生成模板       |
| 微信开放平台    | 4.4.0   | 微信登录           |
| EasyExcel       | 3.1.1   | Excel 处理         |
| Docker          | -       | 多阶段镜像构建     |

## 项目结构

```
src/main/java/com/yupi/springbootinit
├── annotation/              # 自定义注解
│   └── AuthCheck.java            # 权限校验注解
├── aop/                     # AOP 切面
│   ├── AuthInterceptor.java      # JWT 鉴权 + 角色校验
│   └── LogInterceptor.java       # 请求响应日志（计时）
├── common/                  # 通用组件
│   ├── BaseResponse.java         # 统一响应体 {code, data, message}
│   ├── ErrorCode.java            # 错误码枚举
│   ├── ResultUtils.java          # 响应构造工具
│   ├── PageRequest.java          # 分页请求基类
│   └── DeleteRequest.java        # 删除请求基类
├── config/                  # 配置类
│   ├── CorsConfig.java           # 全局跨域
│   ├── JsonConfig.java           # Long 转 String 精度处理
│   ├── Knife4jConfig.java        # Swagger 接口文档
│   ├── MyBatisPlusConfig.java    # 分页插件
│   ├── OssConfig.java            # 阿里云 OSS 客户端
│   └── WxOpenConfig.java         # 微信开放平台
├── constant/                # 常量定义
│   ├── UserConstant.java         # 用户角色、状态、校验规则
│   ├── CardConstant.java         # 卡密类型、状态、格式
│   ├── CommonConstant.java       # 排序常量
│   └── FileConstant.java         # 文件常量
├── exception/               # 异常处理
│   ├── BusinessException.java    # 自定义业务异常
│   ├── GlobalExceptionHandler.java # 全局异常处理器
│   └── ThrowUtils.java           # 条件抛异常工具
├── mapper/                  # MyBatis-Plus Mapper
│   ├── UsersMapper.java
│   ├── CardKeysMapper.java       # 含分页关联查询
│   ├── MaterialsMapper.java      # 含管理端/用户端分页
│   ├── OssFilesMapper.java
│   ├── DownloadLogsMapper.java
│   ├── OperationLogsMapper.java
│   ├── RedeemLogsMapper.java     # 含分页关联查询
│   └── UserUnlocksMapper.java
├── model/
│   ├── dto/                     # 请求/传输对象
│   │   ├── card/                # 卡密相关 DTO
│   │   ├── file/                # 文件上传 DTO
│   │   ├── material/            # 资料相关 DTO
│   │   ├── oss/                 # OSS 文件 DTO
│   │   ├── post/                # 帖子相关 DTO
│   │   ├── user/                # 用户相关 DTO
│   │   ├── redeem/              # 兑换记录 DTO
│   │   ├── postfavour/          # 帖子收藏 DTO
│   │   └── postthumb/           # 帖子点赞 DTO
│   ├── enums/
│   │   ├── FileUploadBizEnum.java
│   │   └── UserRoleEnum.java
│   └── vo/                      # 视图对象
│       ├── CardVO.java          # 卡密视图
│       ├── LoginUserVO.java     # 登录用户视图（含 Token）
│       ├── MaterialVO.java      # 资料视图（管理端）
│       ├── UserMaterialVO.java  # 资料视图（用户端，不含路径）
│       ├── OssFileVO.java       # OSS 文件视图
│       ├── PostVO.java          # 帖子视图
│       ├── RedeemLogVO.java     # 兑换记录视图
│       ├── RedeemResultVO.java  # 兑换结果视图
│       └── UserVO.java          # 用户信息视图
├── utils/
│   ├── JwtUtils.java            # JWT 生成/验证
│   ├── UserHolder.java          # ThreadLocal 用户上下文
│   ├── OssUtil.java             # OSS 上传（含分片）/删除
│   ├── NetUtils.java            # 客户端 IP 获取
│   ├── SqlUtils.java            # SQL 排序字段校验
│   └── SpringContextUtils.java  # Spring 上下文获取
├── esdao/
│   └── PostEsDao.java           # Elasticsearch 帖子 DAO
├── generate/
│   └── CodeGenerator.java       # Freemarker 代码生成器
└── MainApplication.java         # 启动入口
```

## 数据库表

| 表名              | 说明                     |
| ----------------- | ------------------------ |
| `users`           | 用户表（角色/额度/状态） |
| `card_keys`       | 卡密表（类型/状态/有效期）|
| `materials`       | 资料表（按省份分类）     |
| `oss_files`       | OSS 文件记录表           |
| `download_logs`   | 下载记录表               |
| `operation_logs`  | 操作日志表（审计）       |
| `redeem_logs`     | 卡密兑换记录             |
| `user_unlocks`    | 用户解锁资料记录         |

## 核心业务流程

```
管理员上传资料 → 阿里云 OSS 存储 → 生成卡密
      ↓
用户注册 → 兑换卡密 → 获得解锁次数/全通权限
      ↓
用户浏览资料 → 解锁 → 下载
```

## 启动

### 环境要求
- JDK 8+
- MySQL
- Maven

### 配置

修改 `application.yml` 中的数据库连接配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/voluntary_db
    username: root
    password: ${DB_PASSWORD:123456}
```

### 运行

```bash
mvn spring-boot:run
```

### Docker

```bash
docker build -t lipao-backend .
docker run -p 8088:8088 lipao-backend
```

### 接口文档

启动后访问：`http://localhost:8088/api/doc.html`

## API 前缀

所有接口以 `/api` 开头，默认端口 `8088`。

## 鉴权方式

JWT Bearer Token，请求头格式：

```
Authorization: Bearer <token>
```
