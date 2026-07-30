<template>
   <div class="screen-content">
      <div class="section-title">作品集</div>
      <div v-if="loading" class="loading">
         <svg class="spinner" viewBox="0 0 50 50">
            <circle
               class="path"
               cx="25"
               cy="25"
               r="20"
               fill="none"
               stroke-width="5"></circle>
         </svg>
         <span>加载中...</span>
      </div>
      <div v-else class="portfolio-container">
         <div
            v-for="(project, index) in currentPageData"
            :key="project.id"
            class="portfolio-item"
            :class="{ show: visibleItems[index] }">
            <div class="portfolio-image">
               <img :src="project.cover" :alt="project.title" />
               <div class="portfolio-overlay">
                  <span class="portfolio-title">{{ project.title }}</span>
                  <span class="portfolio-desc">{{ project.description }}</span>
               </div>
            </div>
            <div class="portfolio-info">
               <div class="portfolio-name">{{ project.title }}</div>
               <div class="portfolio-category">{{ project.category }}</div>
            </div>
         </div>
      </div>
      <div v-if="!loading && allPortfolio.length === 0" class="empty-tip">
         暂无作品数据
      </div>
      <div
         v-if="!loading && allPortfolio.length > 0"
         class="pagination-wrapper">
         <div class="pagination">
            <button
               class="pagination-btn"
               :disabled="currentPage === 1"
               @click.stop="prevPage">
               <svg
                  width="16"
                  height="16"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  stroke-width="2">
                  <path d="M15 18l-6-6 6-6" />
               </svg>
            </button>
            <span class="pagination-info">
               {{ currentPage }} / {{ totalPages }}
            </span>
            <button
               class="pagination-btn"
               :disabled="currentPage === totalPages"
               @click.stop="nextPage">
               <svg
                  width="16"
                  height="16"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  stroke-width="2">
                  <path d="M9 18l6-6-6-6" />
               </svg>
            </button>
         </div>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onBeforeUnmount, watch } from 'vue';
import { getPortfolioList, type PortfolioItem } from '@/api/start';

const visibleItems = ref<boolean[]>([]);
const timerIds = ref<number[]>([]);
const allPortfolio = ref<PortfolioItem[]>([]);
const loading = ref(false);
const currentPage = ref(1);
const pageSize = 8;

const totalPages = computed(() =>
   Math.ceil(allPortfolio.value.length / pageSize)
);

const currentPageData = computed(() => {
   const start = (currentPage.value - 1) * pageSize;
   const end = start + pageSize;
   return allPortfolio.value.slice(start, end);
});

const loadPortfolio = async () => {
   loading.value = true;
   try {
      const res = await getPortfolioList();
      if (res.code === 0) {
         allPortfolio.value = res.data;
         currentPage.value = 1;
      }
   } catch (error) {
      console.error('加载作品列表失败:', error);
   } finally {
      loading.value = false;
   }
};

const preloadImages = () => {
   currentPageData.value.forEach(item => {
      const img = new Image();
      img.src = item.cover;
   });
};

const animateItems = (index: number) => {
   if (index >= currentPageData.value.length) return;
   const timer = setTimeout(() => {
      visibleItems.value[index] = true;
      animateItems(index + 1);
   }, 120);
   timerIds.value.push(timer);
};

const prevPage = () => {
   console.log(
      '[Portfolio] prevPage clicked, currentPage:',
      currentPage.value,
      'totalPages:',
      totalPages.value
   );
   if (currentPage.value > 1) {
      currentPage.value--;
      console.log(
         '[Portfolio] prevPage success, new currentPage:',
         currentPage.value
      );
   } else {
      console.log('[Portfolio] prevPage disabled, already at page 1');
   }
};

const nextPage = () => {
   console.log(
      '[Portfolio] nextPage clicked, currentPage:',
      currentPage.value,
      'totalPages:',
      totalPages.value
   );
   if (currentPage.value < totalPages.value) {
      currentPage.value++;
      console.log(
         '[Portfolio] nextPage success, new currentPage:',
         currentPage.value
      );
   } else {
      console.log('[Portfolio] nextPage disabled, already at last page');
   }
};

watch(currentPage, () => {
   timerIds.value.forEach(timer => clearTimeout(timer));
   timerIds.value = [];
   visibleItems.value = new Array(currentPageData.value.length).fill(false);
   preloadImages();
   animateItems(0);
});

onMounted(() => {
   loadPortfolio().then(() => {
      visibleItems.value = new Array(currentPageData.value.length).fill(false);
      preloadImages();
      animateItems(0);
   });
});

onBeforeUnmount(() => {
   timerIds.value.forEach(timer => clearTimeout(timer));
   timerIds.value = [];
});
</script>

<style scoped>
.screen-content {
   width: 100%;
   padding: 50px 40px 100px 40px;
   text-align: center;

   display: flex;
   align-items: flex-start;
   justify-content: flex-start;
   flex-direction: column;

   overflow: hidden;
   box-sizing: border-box;
   position: relative;
}

.section-title {
   font-size: 2.5rem;
   font-weight: 700;
   margin-bottom: 40px;
}

.portfolio-container {
   display: grid;
   grid-template-columns: repeat(4, 1fr);
   gap: 12px;
   width: 90vw;
}

.portfolio-item {
   background: var(--cart-back-color);
   border: 1px solid var(--cart-border-color);
   border-radius: 16px;
   overflow: hidden;
   opacity: 0;
   transform: translateY(20px) translateZ(0);
   transition:
      opacity 0.3s ease-out,
      transform 0.3s ease-out;
   will-change: opacity, transform;
}

.portfolio-item.show {
   opacity: 1;
   transform: translateY(0) translateZ(0);
}

.portfolio-item:hover {
   transform: translateY(-6px) translateZ(0);
   box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
}

.portfolio-image {
   position: relative;
   width: 100%;
   aspect-ratio: 16/10;
   overflow: hidden;
   background: var(--back-op-color);
}

.portfolio-image img {
   width: 100%;
   height: 100%;
   object-fit: cover;
   transition: transform 0.4s ease-out;
}

.portfolio-item:hover .portfolio-image img {
   transform: scale(1.05);
}

.portfolio-overlay {
   position: absolute;
   inset: 0;
   background: linear-gradient(to top, rgba(0, 0, 0, 0.6) 0%, transparent 100%);
   display: flex;
   flex-direction: column;
   align-items: center;
   justify-content: flex-end;
   padding: 15px;
   opacity: 0;
   transition: opacity 0.3s ease;
}

.portfolio-item:hover .portfolio-overlay {
   opacity: 1;
}

.portfolio-overlay .portfolio-title {
   color: #fff;
   font-size: 1.1rem;
   font-weight: 600;
   margin-bottom: 4px;
}

.portfolio-overlay .portfolio-desc {
   color: rgba(255, 255, 255, 0.8);
   font-size: 0.85rem;
}

.portfolio-info {
   padding: 15px;
   text-align: left;
}

.portfolio-name {
   font-size: 1rem;
   font-weight: 600;
   margin-bottom: 4px;
}

.portfolio-category {
   font-size: 0.85rem;
   opacity: 0.6;
   display: inline-block;
   padding: 2px 10px;
   background: var(--back-op-color);
   border-radius: 10px;
}

.empty-tip {
   font-size: 1.2rem;
   opacity: 0.6;
   margin-top: 40px;
}

.loading {
   display: flex;
   flex-direction: column;
   align-items: center;
   justify-content: center;
   gap: 16px;
   padding: 40px;
}

.loading .spinner {
   width: 40px;
   height: 40px;
   animation: spin 1s linear infinite;
}

.loading .spinner .path {
   stroke: var(--bk-font-color);
   stroke-linecap: round;
   animation: spinner 1.5s ease-in-out infinite;
}

@keyframes spin {
   100% {
      transform: rotate(360deg);
   }
}

@keyframes spinner {
   0% {
      stroke-dasharray: 1, 150;
      stroke-dashoffset: 0;
   }
   50% {
      stroke-dasharray: 90, 150;
      stroke-dashoffset: -35;
   }
   100% {
      stroke-dasharray: 90, 150;
      stroke-dashoffset: -124;
   }
}

.pagination-wrapper {
   position: sticky;
   left: 50%;
   transform: translateX(-50%);
   z-index: 9999;
   margin-top: 40px;
}

.pagination {
   display: flex;
   align-items: center;
   justify-content: center;
   gap: 20px;
   background: rgba(255, 255, 255, 0.15);
   border: 1px solid rgba(255, 255, 255, 0.2);
   padding: 12px 28px;
   border-radius: 32px;
   backdrop-filter: blur(20px);
   box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
}

.pagination-btn {
   width: 40px;
   height: 40px;
   border-radius: 10px;
   border: none;
   background: rgba(255, 255, 255, 0.2);
   color: #fff;
   display: flex;
   align-items: center;
   justify-content: center;
   cursor: pointer;
   transition: all 0.3s ease;
}

.pagination-btn:hover:not(:disabled) {
   background: #49b1f5;
   transform: translateY(-2px);
}

.pagination-btn:disabled {
   opacity: 0.3;
   cursor: not-allowed;
}

.pagination-info {
   font-size: 1rem;
   color: #fff;
   font-weight: 500;
   min-width: 80px;
}

@media (max-width: 768px) {
   .screen-content {
      padding: 20px;
   }
   .section-title {
      font-size: 2rem;
   }
}
</style>
