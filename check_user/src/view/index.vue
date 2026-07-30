<script setup lang="ts">
import Bgtop from '@/components/Bgtop/Bgtop.vue';
import Footer from '@/components/Footer/Footer.vue';
import { storeToRefs } from 'pinia';
import { useAppStore } from '@/store';
import { ref, computed } from 'vue';
import { useRoute } from 'vue-router';
const { Isindex } = storeToRefs(useAppStore());
//监视scroll组件
import Monitor from '@/components/monitor/monitor.vue';
import { scrollProgress } from '@/util/scrse';

const route = useRoute();
const pageRef = ref<HTMLElement | null>(null);

// 只在主页和关于页面显示 Footer
const showFooter = computed(() => {
   const path = route.path;
   return path === '/home' || path.startsWith('/home/page') || path === '/mine';
});

// 直接处理滚动事件，更新全局滚动进度
const handlePageScroll = () => {
   if (!pageRef.value) return;
   const scrollTop = pageRef.value.scrollTop;
   const scrollHeight = pageRef.value.scrollHeight - pageRef.value.clientHeight;
   if (scrollHeight > 0) {
      scrollProgress.value = Math.floor((scrollTop / scrollHeight) * 100);
   } else {
      scrollProgress.value = 0;
   }
};
</script>

<template>
   <!-- 头 -->
   <div
      ref="pageRef"
      class="page"
      style="height: 100vh; overflow: auto"
      @scroll="handlePageScroll">
      <aPlayer></aPlayer>
      <Bgtop></Bgtop>

      <!-- 一级路由+路由切换动画 -->
      <div class="box">
         <transition name="slide-fade">
            <keep-alive>
               <router-view v-show="Isindex" />
            </keep-alive>
         </transition>
      </div>
      <!-- 底部导航栏 - 仅在主页和关于页面显示 -->
      <Footer v-if="showFooter && Isindex" />
      <Monitor />
   </div>
   <!-- 公共组件 -->
</template>

<style lang="scss" scoped>
//进入动画的生效状态         //离开动画的生效状态
.slide-fade-enter-active,
.slide-fade-leave-active {
   transition:
      transform 0.5s ease,
      opacity 1s ease;
}

//离开动画的结束状态。
.slide-fade-enter-from,
.slide-fade-leave-to {
   transform: translateY(10%);
   opacity: 0;
}

.box {
   width: 80%;
   margin: auto;
   max-width: 1300px;
   font-family: 'MyCustomFonts', sans-serif;
   opacity: 0.9;
}
</style>
