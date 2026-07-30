<template>
   <div class="pack" :class="{ 'lazy-visible': isVisible }" ref="containerRef">
      <div
         class="pack-item"
         v-for="i in home_data.home_archiving"
         :key="i.id"
         @click="goToArchive">
         <div class="pack-icon">
            <svg
               viewBox="0 0 24 24"
               fill="none"
               stroke="currentColor"
               stroke-width="2">
               <path
                  d="M22 19a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5l2 3h9a2 2 0 0 1 2 2z"></path>
            </svg>
         </div>
         <div class="pack-info">
            <div class="pack-year">{{ i.year }}</div>
            <div class="pack-count">{{ i.number }} <span>篇文章</span></div>
         </div>
      </div>
   </div>
</template>

<script setup lang="ts">
import { storeToRefs } from 'pinia';
import { useRouter } from 'vue-router';
import { useHomeStore } from '@/store/home';
import { useLazyVisible } from './useLazyVisible';

const router = useRouter();
const { isVisible, containerRef } = useLazyVisible();
const { home_data } = storeToRefs(useHomeStore());

const goToArchive = () => {
   router.push('/archive');
};
</script>

<style scoped>
.pack {
   display: flex;
   flex-direction: column;
   margin: 1em 0;
   gap: 0.5em;
   opacity: 0;
   transform: translateY(30px);
   transition:
      opacity 0.5s ease,
      transform 0.5s ease;
}

.pack.lazy-visible {
   opacity: 1;
   transform: translateY(0);
}

.pack-item {
   display: flex;
   align-items: center;
   cursor: pointer;
   padding: 0.8em;
   border-radius: 10px;
   transition: all 0.3s ease;

   &:hover {
      background: var(--back-op-color);
      transform: translateX(5px);
   }
}

.pack-icon {
   width: 40px;
   height: 40px;
   border-radius: 8px;
   background: var(--cart-back-color);
   border: 1px solid var(--cart-border-color);
   display: flex;
   align-items: center;
   justify-content: center;
   margin-right: 1em;
   flex-shrink: 0;

   svg {
      width: 20px;
      height: 20px;
      color: var(--bk-font-color);
   }
}

.pack-info {
   display: flex;
   flex-direction: column;
   align-items: flex-start;
   flex: 1;
}

.pack-year {
   font-size: 0.95em;
   font-weight: 600;
   color: var(--bk-font-color);
   margin-bottom: 0.2em;

   &:hover {
      color: #06c0b4;
   }
}

.pack-count {
   font-size: 0.8em;
   color: var(--cart-home-time-color);

   span {
      font-size: 0.85em;
   }
}
</style>
