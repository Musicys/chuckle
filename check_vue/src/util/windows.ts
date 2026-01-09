import { ref, onMounted, onUnmounted, Ref } from 'vue';
import debounce from 'lodash/debounce';
let PCWiht: number = 928;

let Pcflex: number = 990;
// 创建响应式数据
const windowWidth: Ref<number> = ref(window.innerWidth);
const windowHeight: Ref<number> = ref(window.innerHeight);

export const Ispc: Ref<boolean> = ref(true);
export const Isjc: Ref<boolean> = ref(true);

// 处理 resize 事件的函数
const handleResize = debounce(() => {
   windowWidth.value = window.innerWidth;
   windowHeight.value = window.innerHeight;

   if (windowWidth.value >= Pcflex) {
      if (!Isjc.value) {
         Isjc.value = true;
      }
   } else if (windowWidth.value < Pcflex) {
      if (Isjc.value) {
         Isjc.value = false;
      }
   }

   if (windowWidth.value >= PCWiht) {
      if (!Ispc.value) {
         Ispc.value = true;
      }
   } else if (windowWidth.value < PCWiht) {
      if (Ispc.value) {
         Ispc.value = false;
      }
   }
}, 100);
handleResize();

// 在组件挂载时添加事件监听
console.log('开启监听');

window.addEventListener('resize', handleResize);

// 在组件卸载时移除事件监听
// window.removeEventListener('resize', handleResize);
