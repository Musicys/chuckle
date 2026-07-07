import { createApp } from 'vue';
import './style.scss';
import { router } from './router';
import App from '@/App.vue';
import * as ElementPlusIconsVue from '@element-plus/icons-vue';
//阿里图标
import '//at.alicdn.com/t/c/font_4683552_bspq59wgf6a.js';
// //加载工具
import VueLazyload from 'vue-lazyload';
//1 预览组件以及样式

//El
import 'element-plus/dist/index.css';

// highlight 的样式，依赖包，组件
import 'highlight.js/styles/atom-one-dark.css';
import 'highlight.js/lib/common';
import hljsVuePlugin from '@highlightjs/vue-plugin';

//注册组件

//APlayer

//echarts

import * as echarts from 'echarts/core';
import { BarChart, LineChart } from 'echarts/charts';
import {
   TitleComponent,
   TooltipComponent,
   GridComponent,
   // 数据集组件
   DatasetComponent,
   // 内置数据转换器组件 (filter, sort)
   TransformComponent
} from 'echarts/components';
import { LabelLayout, UniversalTransition } from 'echarts/features';
import { CanvasRenderer } from 'echarts/renderers';
import type {
   // 系列类型的定义后缀都为 SeriesOption
   BarSeriesOption,
   LineSeriesOption
} from 'echarts/charts';
import type {
   // 组件类型的定义后缀都为 ComponentOption
   TitleComponentOption,
   TooltipComponentOption,
   GridComponentOption,
   DatasetComponentOption
} from 'echarts/components';
import type { ComposeOption } from 'echarts/core';

// 通过 ComposeOption 来组合出一个只有必须组件和图表的 Option 类型
type ECOption = ComposeOption<
   | BarSeriesOption
   | LineSeriesOption
   | TitleComponentOption
   | TooltipComponentOption
   | GridComponentOption
   | DatasetComponentOption
>;

// 注册必须的组件
echarts.use([
   TitleComponent,
   TooltipComponent,
   GridComponent,
   DatasetComponent,
   TransformComponent,
   BarChart,
   LineChart,
   LabelLayout,
   UniversalTransition,
   CanvasRenderer
]);

const app = createApp(App);

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
   app.component(key, component);
}
app.use(router);

app.use(hljsVuePlugin);
app.use(VueLazyload, {
   loading: 'https://img.zcool.cn/community/0115bb5715c7356ac725134349d24b.gif', // 加载中的图片
   error: 'https://img.zcool.cn/community/0115bb5715c7356ac725134349d24b.gif' // 加载失败的图片
});

app.mount('#app');
