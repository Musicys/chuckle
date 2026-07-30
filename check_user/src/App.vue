<script setup lang="ts">
import index from '@/view/index.vue';
import Lloding from '@/components/lodding/lloding.vue';
import { storeToRefs } from 'pinia';
import { useAppStore } from '@/store';
const { Isindex } = storeToRefs(useAppStore());
import Draw from '@/components/draw/draw.vue';
import RightMenu from '@/components/RightMenu/index.vue';
import GlobalMusicPlayer from '@/components/GlobalMusicPlayer.vue';
import AiChatModal from '@/components/AiChatModal.vue';
import '@/util/windows';
import { handleScroll } from '@/util/scrse';
import { onMounted, computed, ref } from 'vue';
import { useUserStore } from '@/store/user';
import { updateBaseUrl } from '@/htpps/request';
import { useRoute } from 'vue-router';

const route = useRoute();

// 判断当前是否在音乐页面
const isMusicPage = computed(() => {
   return route.path.includes('/muisc');
});

// AI弹窗状态（全局共享）
const showAiChat = ref(false);

// 提供给子组件使用的方法
const openAiChat = () => {
   showAiChat.value = true;
};

const closeAiChat = () => {
   showAiChat.value = false;
};

// 暴露给全局使用
(window as any).openAiChat = openAiChat;

onMounted(async () => {
   console.log('欢迎来到我的博客');
   const userStore = useUserStore();
   await userStore.fetchBloggerInfo();
   // 如果配置了后端地址，更新 axios baseURL
   if (userStore.config.url) {
      updateBaseUrl(userStore.config.url);
   }
});
</script>

<template>
   <Draw />
   <RightMenu />
   <div class="page">
      <div class="page-zqdongz" @scroll="handleScroll">
         <index></index>
      </div>
   </div>
   <Lloding v-if="!Isindex"></Lloding>
   <GlobalMusicPlayer v-if="!isMusicPage" />
   <AiChatModal :visible="showAiChat" @close="closeAiChat" />
</template>

<style lang="scss" scoped>
// 主页样式
.page {
   top: 0;
   left: 0;
   width: 100vw;
   overflow: hidden;
   height: 100vh;
   position: absolute;

   /* 白天背景（注释掉）
   background-image:
      linear-gradient(to left, #0095c2, #5fd6c9),
      repeating-linear-gradient(
            0deg,
            rgba(0, 0, 0, 0) 0px,
            rgba(0, 0, 0, 0) 10px,
            rgba(0, 0, 0, 0.5) 10px,
            rgba(0, 0, 0, 0.5) 11px
         );
   background-size:
      100% 100%,
      40px 40px;
   */

   /* 使用 CSS 变量实现白天/夜晚背景切换 */
   background-image:
      var(--app-bg-gradient),
      /* 渐变背景 */ var(--app-bg-grid);
   /* 网格背景 */

   background-size:
      100% 100%,
      40px 40px;
   background-position: center center;
   background-repeat: no-repeat;
   background-attachment: fixed;

   /* 背景图片固定在视口，不随内容滚动 */
   .page-zqdongz {
      background: var(--page);
      width: 100vw;
      overflow-y: auto;
      padding-top: 60px;
      height: 100vh;
   }
}
</style>
