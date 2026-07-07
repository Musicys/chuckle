import { ref, Ref } from 'vue';

export const desc_mr: Ref<Document> = ref();

// 定义类型

export const scrollProgress = ref<number>(0);

// Istab
export const descIsFlex: Ref<boolean> = ref(false);

export const IsScroll = ref(false);

export const handleScroll = (e: any) => {
   if (desc_mr.value && !descIsFlex.value) {
      // @ts-ignore
      if (desc_mr.value.getBoundingClientRect().y >= 55) {
         descIsFlex.value = true;
      }
   }

   if (desc_mr.value && descIsFlex.value) {
      // @ts-ignore
      if (desc_mr.value.getBoundingClientRect().y < 55) {
         descIsFlex.value = false;
      }
   }

   IsScroll.value = e.target.scrollTop == 0 ? false : true;
   const scrollTop = e.target.scrollTop;
   const scrollHeight = e.target.scrollHeight - e.target.clientHeight;

   // 计算滚动百分比
   scrollProgress.value = Math.floor((scrollTop / scrollHeight) * 100) | 0;
   console.log(scrollProgress.value);
};
