import { createApp } from 'vue'
import './style.css'
import {router} from './router'
import App from '@/App.vue'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
//阿里图标
import '//at.alicdn.com/t/c/font_4683552_bog4rq477tv.js'
// //加载工具
import VueLazyload from 'vue-lazyload';
//1 预览组件以及样式



const app = createApp(App)




for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
app.use( router )


app.use(VueLazyload, {
  loading: 'https://img.zcool.cn/community/0115bb5715c7356ac725134349d24b.gif', // 加载中的图片
  error: 'https://img.zcool.cn/community/0115bb5715c7356ac725134349d24b.gif'      // 加载失败的图片
});

app.mount('#app')
