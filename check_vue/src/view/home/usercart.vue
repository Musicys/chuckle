<template>
   <div class="box" :style="{ backgroundImage: `url(${music.drawURL})` }">
      <img v-lazy="music.userImg" alt="" />
      <span>{{ music.username }}</span>
      <span style="font-size: 1.2em">{{ music.drawtilte }}</span>

      <div style="width: 100%; display: flex; margin-top: 0.5em">
         <div class="flex-center" style="flex: 1; text-align: center">
            <span>标签</span>
            <span>15</span>
         </div>
         <div class="flex-center" style="flex: 1; text-align: center">
            <span>标签</span>
            <span>15</span>
         </div>
         <div class="flex-center" style="flex: 1; text-align: center">
            <span>标签</span>
            <span>15</span>
         </div>
      </div>

      <div
         class="but"
         style="width: 80%; border-radius: 15px; margin-top: 1em"
         type="primary">
         <el-icon>
            <Platform />
         </el-icon>

         Follow Me
      </div>

      <div class="grid">
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
import { music } from '@/util/user';
</script>

<style scoped>
.but:hover {
   background: #00c4b6;
}

.but {
   cursor: pointer;
   display: flex;
   justify-content: center;
   align-items: center;
   padding: 10px 5px;
   background: var(--el-but-back);
}

.box {
   width: 100%;

   display: flex;
   justify-content: start;
   align-items: center;
   flex-direction: column;
   padding-bottom: 1em;
   background-size: 100% 100%;

   img {
      width: 80px;
      border-radius: 25%;
      margin-top: 1em;
   }

   & > span {
      margin-top: 0.5em;
      font-size: 1.5em;

      font-family: 'MyCustomFont', sans-serif;
   }

   .grid {
      display: grid;
      grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
      gap: 15px;

      margin-top: 1em;
      width: 80%;

      & > img {
         width: 100%;
         height: 30px;
      }
   }

   .flex-center {
      display: flex;
      flex-direction: column;
      align-items: center;
      height: 50px;
      justify-content: center;

      & > span {
         font-family: 'MyCustomFonts', sans-serif;
         font-weight: bold;
      }

      & > span:nth-child(2) {
         margin-top: 0.5em;
      }
   }

   .flex-center:nth-child(1) {
      border-right: 0.5px solid var(--bk-font-color);
   }

   .flex-center:nth-child(3) {
      border-left: 0.5px solid var(--bk-font-color);
   }
}
</style>
