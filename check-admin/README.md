<div align="center">
	<img src="./public/favicon.svg" width="160" />
	<h1>QXchuckle Admin</h1>
	<span>中文 | <a href="./README.en_US.md">English</a></span>
</div>

---

## 简介

QXchuckle Admin 是一个基于 **SoybeanAdmin** 二次开发的博客管理后台系统，采用最新的前端技术栈构建，提供清新优雅的界面设计和强大的管理功能。

## 技术栈

| 技术       | 版本   | 说明                       |
| ---------- | ------ | -------------------------- |
| Vue        | 3.5.x  | 渐进式 JavaScript 框架     |
| Vite       | 8.0.x  | 下一代前端构建工具         |
| TypeScript | 6.0.x  | 类型安全的 JavaScript 超集 |
| NaiveUI    | 2.44.x | Vue 3 组件库               |
| UnoCSS     | ^66.x  | 高性能原子化 CSS 引擎      |
| Pinia      | 3.0.x  | Vue 状态管理库             |
| Vue Router | 5.0.x  | Vue 路由管理器             |
| ECharts    | 6.0.x  | 数据可视化图表库           |

## 项目结构

```
├── build/                  # 构建配置
│   ├── config/            # 代理、时间等配置
│   └── plugins/           # Vite 插件配置
├── packages/              # Monorepo 子包
│   ├── alova/             # Alova 请求封装
│   ├── axios/             # Axios 请求封装
│   ├── color/             # 颜色处理工具
│   ├── hooks/             # 自定义 Hooks
│   ├── materials/         # 布局组件
│   ├── scripts/           # 命令行工具 (sa)
│   ├── uno-preset/        # UnoCSS 预设
│   └── utils/             # 通用工具函数
├── public/                # 静态资源
├── src/                   # 源代码
│   ├── assets/            # 资源文件
│   ├── components/        # 公共组件
│   ├── constants/         # 常量定义
│   ├── enum/              # 枚举定义
│   ├── hooks/             # 业务 Hooks
│   ├── layouts/           # 布局组件
│   ├── locales/           # 国际化配置
│   ├── plugins/           # 插件注册
│   ├── router/            # 路由配置
│   ├── service/           # API 服务
│   ├── store/             # Pinia 状态管理
│   ├── styles/            # 全局样式
│   ├── theme/             # 主题配置
│   ├── typings/           # TypeScript 类型定义
│   ├── utils/             # 工具函数
│   └── views/             # 页面视图
└── README.md              # 项目文档
```

## 环境准备

确保你的环境满足以下要求：

- **NodeJS**: >= 20.19.0
- **pnpm**: >= 10.5.0

## 快速开始

```bash
# 安装依赖
pnpm install

# 启动开发服务器（测试环境）
pnpm dev

# 启动开发服务器（生产环境）
pnpm dev:prod

# 构建项目（生产环境）
pnpm build

# 构建项目（测试环境）
pnpm build:test

# 预览构建结果
pnpm preview
```

## 脚本命令

| 命令              | 说明                       |
| ----------------- | -------------------------- |
| `pnpm dev`        | 启动开发服务器（测试环境） |
| `pnpm dev:prod`   | 启动开发服务器（生产环境） |
| `pnpm build`      | 构建生产环境               |
| `pnpm build:test` | 构建测试环境               |
| `pnpm preview`    | 预览构建结果               |
| `pnpm lint`       | 代码检查并自动修复         |
| `pnpm typecheck`  | TypeScript 类型检查        |
| `pnpm fmt`        | 代码格式化                 |
| `pnpm commit`     | 规范化提交代码             |
| `pnpm commit:zh`  | 中文规范化提交代码         |
| `pnpm gen-route`  | 生成路由配置               |
| `pnpm release`    | 版本发布                   |
| `pnpm cleanup`    | 清理构建产物               |

## 代码规范

### 代码检查

项目集成了以下工具进行代码质量保证：

- **ESLint**: JavaScript/TypeScript 代码检查
- **oxlint**: 高性能 Rust 编写的代码检查器
- **oxfmt**: 代码格式化工具
- **vue-tsc**: Vue TypeScript 类型检查

### Git Hooks

通过 `simple-git-hooks` 配置了以下钩子：

- **pre-commit**: 提交前自动执行类型检查、代码检查、格式化
- **commit-msg**: 提交信息规范化验证

### 编码规范

- **缩进**: 2 个空格
- **字符集**: UTF-8
- **换行符**: LF
- **移除尾随空格**: 启用
- **文件末尾添加空行**: 启用

## Monorepo 子包

项目采用 pnpm monorepo 架构，包含以下子包：

| 子包             | 说明             |
| ---------------- | ---------------- |
| `@sa/alova`      | Alova 请求库封装 |
| `@sa/axios`      | Axios 请求库封装 |
| `@sa/color`      | 颜色处理工具     |
| `@sa/hooks`      | 自定义 Vue Hooks |
| `@sa/materials`  | 通用布局组件     |
| `@sa/scripts`    | 命令行工具 `sa`  |
| `@sa/uno-preset` | UnoCSS 预设配置  |
| `@sa/utils`      | 通用工具函数     |

## 环境变量

项目支持多环境配置：

- `.env`: 默认环境变量
- `.env.test`: 测试环境变量
- `.env.prod`: 生产环境变量

### 核心配置项

| 配置项                     | 说明         | 默认值            |
| -------------------------- | ------------ | ----------------- |
| `VITE_BASE_URL`            | 应用基础 URL | `/`               |
| `VITE_APP_TITLE`           | 应用标题     | `QXchuckle Admin` |
| `VITE_SERVICE_BASE_URL`    | 后端服务地址 | -                 |
| `VITE_AUTH_ROUTE_MODE`     | 鉴权模式     | `static`          |
| `VITE_ROUTER_HISTORY_MODE` | 路由模式     | `history`         |
| `VITE_HTTP_PROXY`          | 是否启用代理 | `Y`               |

## 路由系统

采用 **Elegant Router** 自动化文件路由系统：

- 自动生成路由导入、声明和类型
- 支持静态路由和动态路由
- 文件命名即路由路径

## 主题配置

项目内置丰富的主题配置：

- 多种主题色预设（azir、compact、dark、default）
- 支持暗色/亮色模式切换
- 可自定义圆角、布局模式等

## 浏览器支持

| 浏览器  | 支持情况      |
| ------- | ------------- |
| Chrome  | 推荐          |
| Edge    | 最新 2 个版本 |
| Firefox | 最新 2 个版本 |
| Safari  | 最新 2 个版本 |

## 开源协议

项目基于 [MIT](./LICENSE) 协议。
