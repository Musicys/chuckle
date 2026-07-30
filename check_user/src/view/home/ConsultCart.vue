<template>
   <div
      class="cunsult"
      :class="{ 'lazy-visible': isVisible }"
      ref="containerRef">
      <div>
         <span>文章数目 :</span
         ><span>{{ home_data.home_information.articles }}</span>
      </div>
      <div>
         <span>已运行时间 :</span
         ><span>{{ home_data.home_information.days }}</span>
      </div>
      <div>
         <span>本站总字数 :</span
         ><span>{{ home_data.home_information.words }}</span>
      </div>
      <div>
         <span>最后更新时间 :</span
         ><span>{{ home_data.home_information.updated }}</span>
      </div>
   </div>
</template>

<script setup lang="ts">
import { storeToRefs } from 'pinia';
import { useHomeStore } from '@/store/home';
import { useLazyVisible } from './useLazyVisible';

const { isVisible, containerRef } = useLazyVisible();
const { home_data } = storeToRefs(useHomeStore());
</script>

<style scoped>
.cunsult {
   display: flex;
   flex-direction: column;
   margin-top: 1em;
   padding: 0.5em;
   opacity: 0;
   transform: translateY(30px);
   transition:
      opacity 0.5s ease,
      transform 0.5s ease;

   &.lazy-visible {
      opacity: 1;
      transform: translateY(0);
   }

   & > div {
      flex: 1;
      display: flex;
      justify-content: space-between;
      margin-bottom: 1em;
   }
}
</style>
