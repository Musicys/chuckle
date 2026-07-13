<div align="center">
	<img src="./public/favicon.svg" width="160" />
	<h1>QXchuckle Admin</h1>
	<span><a href="./README.md">中文</a> | English</span>
</div>

---

## Introduction

QXchuckle Admin is a blog management backend system based on **SoybeanAdmin**, built with the latest frontend technology stack, providing a fresh and elegant UI design and powerful management features.

## Technology Stack

| Technology | Version | Description |
| --- | --- | --- |
| Vue | 3.5.x | Progressive JavaScript Framework |
| Vite | 8.0.x | Next-generation Frontend Build Tool |
| TypeScript | 6.0.x | Type-safe JavaScript Superset |
| NaiveUI | 2.44.x | Vue 3 Component Library |
| UnoCSS | ^66.x | High-performance Atomic CSS Engine |
| Pinia | 3.0.x | Vue State Management Library |
| Vue Router | 5.0.x | Vue Routing Manager |
| ECharts | 6.0.x | Data Visualization Chart Library |

## Project Structure

```
├── build/                  # Build Configuration
│   ├── config/            # Proxy, Time and Other Configurations
│   └── plugins/           # Vite Plugin Configuration
├── packages/              # Monorepo Sub-packages
│   ├── alova/             # Alova Request Wrapper
│   ├── axios/             # Axios Request Wrapper
│   ├── color/             # Color Processing Utilities
│   ├── hooks/             # Custom Hooks
│   ├── materials/         # Layout Components
│   ├── scripts/           # Command Line Tool (sa)
│   ├── uno-preset/        # UnoCSS Preset
│   └── utils/             # General Utilities
├── public/                # Static Assets
├── src/                   # Source Code
│   ├── assets/            # Asset Files
│   ├── components/        # Common Components
│   ├── constants/         # Constant Definitions
│   ├── enum/              # Enum Definitions
│   ├── hooks/             # Business Hooks
│   ├── layouts/           # Layout Components
│   ├── locales/           # Internationalization Configuration
│   ├── plugins/           # Plugin Registration
│   ├── router/            # Route Configuration
│   ├── service/           # API Services
│   ├── store/             # Pinia State Management
│   ├── styles/            # Global Styles
│   ├── theme/             # Theme Configuration
│   ├── typings/           # TypeScript Type Definitions
│   ├── utils/             # Utility Functions
│   └── views/             # Page Views
└── README.md              # Project Documentation
```

## Environment Preparation

Ensure your environment meets the following requirements:

- **NodeJS**: >= 20.19.0
- **pnpm**: >= 10.5.0

## Quick Start

```bash
# Install dependencies
pnpm install

# Start development server (test environment)
pnpm dev

# Start development server (production environment)
pnpm dev:prod

# Build project (production environment)
pnpm build

# Build project (test environment)
pnpm build:test

# Preview build result
pnpm preview
```

## Script Commands

| Command | Description |
| --- | --- |
| `pnpm dev` | Start development server (test environment) |
| `pnpm dev:prod` | Start development server (production environment) |
| `pnpm build` | Build for production |
| `pnpm build:test` | Build for test |
| `pnpm preview` | Preview build result |
| `pnpm lint` | Code linting and auto-fix |
| `pnpm typecheck` | TypeScript type checking |
| `pnpm fmt` | Code formatting |
| `pnpm commit` | Standardized commit |
| `pnpm commit:zh` | Standardized commit (Chinese) |
| `pnpm gen-route` | Generate route configuration |
| `pnpm release` | Version release |
| `pnpm cleanup` | Clean build artifacts |

## Code Standards

### Code Linting

The project integrates the following tools for code quality assurance:

- **ESLint**: JavaScript/TypeScript code linting
- **oxlint**: High-performance Rust-based code linter
- **oxfmt**: Code formatting tool
- **vue-tsc**: Vue TypeScript type checking

### Git Hooks

Configured with `simple-git-hooks`:

- **pre-commit**: Auto-run type checking, linting, and formatting before commit
- **commit-msg**: Commit message validation

### Coding Standards

- **Indentation**: 2 spaces
- **Charset**: UTF-8
- **Line Ending**: LF
- **Trim Trailing Whitespace**: Enabled
- **Insert Final Newline**: Enabled

## Monorepo Sub-packages

The project uses pnpm monorepo architecture with the following sub-packages:

| Package | Description |
| --- | --- |
| `@sa/alova` | Alova request library wrapper |
| `@sa/axios` | Axios request library wrapper |
| `@sa/color` | Color processing utilities |
| `@sa/hooks` | Custom Vue Hooks |
| `@sa/materials` | Common layout components |
| `@sa/scripts` | Command line tool `sa` |
| `@sa/uno-preset` | UnoCSS preset configuration |
| `@sa/utils` | General utility functions |

## Environment Variables

The project supports multi-environment configuration:

- `.env`: Default environment variables
- `.env.test`: Test environment variables
- `.env.prod`: Production environment variables

### Core Configuration

| Configuration | Description | Default |
| --- | --- | --- |
| `VITE_BASE_URL` | Application base URL | `/` |
| `VITE_APP_TITLE` | Application title | `QXchuckle Admin` |
| `VITE_SERVICE_BASE_URL` | Backend service URL | - |
| `VITE_AUTH_ROUTE_MODE` | Auth mode | `static` |
| `VITE_ROUTER_HISTORY_MODE` | Router mode | `history` |
| `VITE_HTTP_PROXY` | Enable proxy | `Y` |

## Route System

Adopts **Elegant Router** automated file routing system:

- Auto-generate route imports, declarations, and types
- Support static and dynamic routes
- File naming equals route path

## Theme Configuration

Built-in rich theme configuration:

- Multiple theme presets (azir, compact, dark, default)
- Support dark/light mode switching
- Customizable radius, layout mode, etc.

## Browser Support

| Browser | Support Status |
| --- | --- |
| Chrome | Recommended |
| Edge | Last 2 versions |
| Firefox | Last 2 versions |
| Safari | Last 2 versions |

## License

This project is based on the [MIT](./LICENSE) license.
