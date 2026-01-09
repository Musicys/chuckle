<template>
   <div class="page-start">
      <div class="top">
         <img
            src="https://tse3-mm.cn.bing.net/th/id/OIP-C.U1UG7FN50qzrntU8he3s9wAAAA?rs=1&pid=ImgDetMain"
            alt="" />
      </div>
      <div class="center">
         <div>{{ currentTitle }}</div>
         <div><元神启动!></div>
      </div>
      <div class="botom">
         <div class="icons">
            <a :href="music.GitHubUrl" target="_blank">
               <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-github"></use>
               </svg>
            </a>
         </div>
         <div class="icons">
            <a :href="music.CsdnUrl" target="_blank">
               <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-csdn"></use>
               </svg>
            </a>
         </div>
         <div class="icons">
            <a :href="music.QqUrl" target="_blank">
               <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-QQ"></use>
               </svg>
            </a>
         </div>
         <div class="icons">
            <a :href="music.Bilbilurl" target="_blank">
               <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-Bzhan"></use>
               </svg>
            </a>
         </div>
         <div class="icons">
            <a href="/muisc">
               <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-rengongzhinengjiqiren"></use>
               </svg>
            </a>
         </div>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue';
import { music } from '@/util/user.ts';
// 定义要显示的完整文本和当前显示的文本
const title = music.value.StartTilte;
const currentTitle = ref('');
let intervalId = null; // 用于存储定时器ID

// 打字机效果函数
const typeWriter = (text, speed) => {
   let index = 0;

   intervalId = setInterval(() => {
      if (index < text.length) {
         // 每次添加一个字符到currentTitle
         currentTitle.value += text.charAt(index);
         index++;
      } else {
         // 完成后清除定时器
         currentTitle.value = text.charAt(1);
         index = 1;
      }
   }, speed);
};

// 清理函数，用于在组件卸载前停止定时器
const cleanup = () => {
   if (intervalId !== null) {
      clearInterval(intervalId);
      intervalId = null;
   }
};

onMounted(() => {
   // 调用typeWriter函数，开始打字机效果
   typeWriter(title, 200); // 你可以调整速度参数以改变打字速度
});

onBeforeUnmount(() => {
   // 在组件卸载前调用清理函数
   cleanup();
});
</script>

<style scoped>
.page-start {
   width: 100%;
   height: 70vh;
   overflow: hidden;

   display: flex;
   justify-content: center;
   align-items: center;
   flex-direction: column;

   & > div {
      margin-top: 1em;
   }

   .top {
      & > img {
         width: 150px;
         height: 150px;
         border-radius: 50%;
         box-shadow: 0 0 black 0.5;
      }
   }

   .center {
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;

      & > div:nth-child(1) {
         font-size: 1.5em;
      }

      & > div:nth-child(2) {
         font-size: 1em;
         margin-top: 0.5em;
      }
   }

   .botom {
      width: 300px;
      display: grid;
      grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
      --h: 30px;

      .icons {
         padding: 1px;
         width: 50px;
         height: 50px;
         border-radius: 50%;
         display: flex;
         justify-content: center;
         align-items: center;

         .icon {
            font-size: 1.5em;
         }
      }

      & img {
         width: var(--h);
         height: var(--h);
         border-radius: 50%;

         border: 8px solid rgba(255, 255, 255, 0.5);
      }
   }
}
</style>
