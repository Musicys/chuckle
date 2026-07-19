# 整屏滚动实现方案：CSS Scroll Snap 与 JS Transform 对比

## 1. CSS Scroll Snap 方案

### 1.1 核心原理
利用 CSS 原生的滚动吸附特性，在滚动容器上定义吸附类型，在子元素上定义吸附对齐点。浏览器会自动处理滚动停止时的对齐逻辑，无需手动计算位移。

### 1.2 关键代码示例

```html
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>CSS Scroll Snap 整屏滚动</title>
  <style>
    * { margin: 0; padding: 0; box-sizing: border-box; }
    html { scroll-behavior: smooth; }
    
    /* 1. 隐藏 body 滚动，使用内部容器接管 */
    html, body { height: 100%; overflow: hidden; }

    /* 2. 滚动容器配置 */
    .scroll-container {
      height: 100%;
      overflow-y: scroll;
      scroll-snap-type: y mandatory; /* Y轴强制吸附 */
    }

    /* 3. 子元素对齐配置 */
    .screen {
      scroll-snap-align: start; /* 顶部对齐 */
      height: 100vh;
      height: 100dvh; /* 移动端动态视口高度适配 */
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 3rem;
      color: #fff;
    }

    .screen1 { background: #ff6b6b; }
    .screen2 { background: #4ecdc4; }
    .screen3 { background: #45b7d1; }
    .screen4 { background: #96ceb4; }
  </style>
</head>
<body>
  <div class="scroll-container">
    <section class="screen screen1">第一屏</section>
    <section class="screen screen2">第二屏</section>
    <section class="screen screen3">第三屏</section>
    <section class="screen screen4">第四屏</section>
  </div>
</body>
</html>
```

### 1.3 关键技术点
1.  **容器隔离**：必须设置 `body { overflow: hidden }`，将 `scroll-snap-type` 应用在内部容器（如 `.scroll-container`）上，避免浏览器对 `body` 滚动吸附支持不一致的问题。
2.  **强制吸附**：使用 `scroll-snap-type: y mandatory` 确保每次滚动停止时必然对齐到吸附点。
3.  **移动端适配**：使用 `height: 100dvh` (Dynamic Viewport Height) 替代 `100vh`，解决移动浏览器地址栏显隐导致的高度跳动问题。
4.  **平滑过渡**：配合 `scroll-behavior: smooth` 实现原生平滑滚动动画。

---

## 2. JS Transform 方案

### 2.1 核心原理
通过 JavaScript 监听鼠标滚轮事件，计算滚动方向，利用 CSS `transform: translateY()` 移动整个内容容器。配合 CSS `transition` 实现平滑动画，通过节流逻辑防止快速滚动导致的页面闪烁或跳屏。

### 2.2 关键代码示例

```html
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>JS Transform 整屏滚动</title>
  <style>
    * { margin: 0; padding: 0; }
    body { overflow: hidden; }
    
    .main {
      width: 100vw;
      transition: all 0.7s ease-in-out; /* 动画时长需与节流时间匹配 */
    }
    
    .list {
      width: 100vw;
      height: 100vh;
    }
    /* 背景色示例 */
    .list:nth-of-type(1) { background-color: #ff6b6b; }
    .list:nth-of-type(2) { background-color: #4ecdc4; }
    .list:nth-of-type(3) { background-color: #45b7d1; }
    .list:nth-of-type(4) { background-color: #96ceb4; }
  </style>
</head>
<body>
  <div class="main">
    <div class="list"></div>
    <div class="list"></div>
    <div class="list"></div>
    <div class="list"></div>
  </div>

  <script>
    let main = document.querySelector(".main");
    let currentIndex = 0;
    let lastScrollTime = new Date();
    const totalScreens = document.querySelectorAll('.list').length;
    const animationDuration = 700; // 与 CSS transition 保持一致

    window.onwheel = (e) => {
      // 1. 节流控制：动画未完成前忽略新事件
      if (new Date() - lastScrollTime < animationDuration) return;
      
      // 2. 兼容性方向判断
      const delta = e.deltaY || -e.wheelDeltaY;
      
      // 3. 边界检查与索引更新
      if (delta > 0 && currentIndex < totalScreens - 1) {
        currentIndex++;
      } else if (delta < 0 && currentIndex > 0) {
        currentIndex--;
      } else {
        return; // 到达边界，不执行后续逻辑
      }

      // 4. 执行位移
      main.style.transform = `translateY(-${currentIndex * 100}vh)`;
      lastScrollTime = new Date();
    };
  </script>
</body>
</html>
```

### 2.3 核心逻辑分析
1.  **节流机制**：使用 `new Date() - lastScrollTime < animationDuration` 进行时间戳节流。节流时间必须大于或等于 CSS `transition` 的动画时长，否则会在动画中途触发新的滚动，导致画面抖动。
2.  **事件兼容**：推荐使用标准 `wheel` 事件及 `e.deltaY`，避免使用非标准的 `onmousewheel` 或 `wheelDeltaY`（Firefox 不支持）。
3.  **位移计算**：通过 `translateY(-${index * 100}vh)` 精确控制容器位置，不依赖文档流滚动，性能较好（GPU 加速）。

### 2.4 优缺点
*   **优点**：逻辑完全可控，不依赖浏览器原生滚动行为，兼容性极佳（支持老旧浏览器）。
*   **缺点**：仅响应鼠标滚轮，默认不支持触控板惯性滚动、键盘方向键、拖拽滚动条及移动端触摸滑动（需额外补充 `touchstart/touchend` 逻辑）；代码量相对较多。

---

## 3. 方案对比总结

| 维度 | CSS Scroll Snap | JS Transform |
| :--- | :--- | :--- |
| **实现复杂度** | 低（纯 CSS，少量 JS 辅助） | 中（需处理事件、节流、边界、兼容性） |
| **交互体验** | 原生滚动体验，支持触控板惯性、键盘、触摸 | 仅响应鼠标滚轮，缺乏惯性，需手动补充交互 |
| **性能** | 浏览器原生优化，流畅 | GPU 加速位移，流畅，但需防止重排/重绘 |
| **兼容性** | 现代浏览器支持良好，IE 不支持 | 全浏览器兼容（包括 IE） |
| **适用场景** | 现代 Web 应用、移动端 H5、注重原生体验 | 老旧项目维护、特殊交互需求、非滚动容器模拟 |
| **维护成本** | 低 | 高（需处理各种输入设备和边界情况） |

**建议**：
*   在绝大多数现代开发场景中，**优先选择 CSS Scroll Snap**。它代码简洁、体验原生、易于维护。
*   仅在需要支持 IE 浏览器，或需要在非滚动容器（如固定高度弹窗内）模拟滚动效果时，才考虑 **JS Transform** 方案。