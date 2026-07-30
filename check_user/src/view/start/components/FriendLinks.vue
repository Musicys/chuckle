<template>
   <div class="screen-content">
      <div class="section-title">友情链接</div>
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
      <div v-else class="links-container">
         <div
            v-for="(link, index) in currentPageData"
            :key="link.id"
            class="link-item"
            :style="{ animationDelay: `${index * 0.12}s` }"
            :class="{ show: isVisible }">
            <div class="link-icon">
               <img
                  :src="
                     loadedImages[index] ||
                     'https://img.zcool.cn/community/0115bb5715c7356ac725134349d24b.gif'
                  "
                  :alt="link.name"
                  @load="onImageLoad(index)" />
            </div>
            <div class="link-info">
               <div class="link-name">{{ link.name }}</div>
               <div class="link-desc">{{ link.description }}</div>
            </div>
            <a :href="link.url" target="_blank" class="link-btn">访问</a>
         </div>
      </div>
      <div v-if="!loading && allFriendLinks.length === 0" class="empty-tip">
         暂无友链数据
      </div>
      <div
         v-if="!loading && allFriendLinks.length > 0"
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
import { ref, computed, onMounted, watch } from 'vue';
import { getFriendLinks, type FriendLinkItem } from '@/api/start';

const isVisible = ref(false);
const loadedImages = ref<string[]>([]);
const allFriendLinks = ref<FriendLinkItem[]>([]);
const loading = ref(false);
const currentPage = ref(1);
const pageSize = 8;

const totalPages = computed(() =>
   Math.ceil(allFriendLinks.value.length / pageSize)
);

const currentPageData = computed(() => {
   const start = (currentPage.value - 1) * pageSize;
   const end = start + pageSize;
   return allFriendLinks.value.slice(start, end);
});

const loadFriendLinks = async () => {
   loading.value = true;
   try {
      const res = await getFriendLinks();
      if (res.code === 0) {
         allFriendLinks.value = res.data;
         currentPage.value = 1;
      }
   } catch (error) {
      console.error('加载友链列表失败:', error);
   } finally {
      loading.value = false;
   }
};

const onImageLoad = (index: number) => {
   const nextIndex = index + 1;
   if (
      nextIndex < currentPageData.value.length &&
      !loadedImages.value[nextIndex]
   ) {
      setTimeout(() => {
         loadedImages.value[nextIndex] =
            currentPageData.value[nextIndex].avatar;
      }, 100);
   }
};

const prevPage = () => {
   console.log(
      '[FriendLinks] prevPage clicked, currentPage:',
      currentPage.value,
      'totalPages:',
      totalPages.value
   );
   if (currentPage.value > 1) {
      currentPage.value--;
      console.log(
         '[FriendLinks] prevPage success, new currentPage:',
         currentPage.value
      );
   } else {
      console.log('[FriendLinks] prevPage disabled, already at page 1');
   }
};

const nextPage = () => {
   console.log(
      '[FriendLinks] nextPage clicked, currentPage:',
      currentPage.value,
      'totalPages:',
      totalPages.value
   );
   if (currentPage.value < totalPages.value) {
      currentPage.value++;
      console.log(
         '[FriendLinks] nextPage success, new currentPage:',
         currentPage.value
      );
   } else {
      console.log('[FriendLinks] nextPage disabled, already at last page');
   }
};

watch(currentPage, () => {
   loadedImages.value = [];
   isVisible.value = false;
   setTimeout(() => {
      isVisible.value = true;
      if (currentPageData.value.length > 0) {
         loadedImages.value[0] = currentPageData.value[0].avatar;
      }
   }, 100);
});

onMounted(() => {
   loadFriendLinks().then(() => {
      isVisible.value = true;
      if (currentPageData.value.length > 0) {
         loadedImages.value[0] = currentPageData.value[0].avatar;
      }
   });
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
   min-height: 100%;
   overflow-y: hidden;
   box-sizing: border-box;
   position: relative;
}

.section-title {
   font-size: 2.5rem;
   font-weight: 700;
   margin-bottom: 40px;
}

.links-container {
   display: grid;
   grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
   gap: 20px;
   max-width: 900px;
   margin: 0 auto;
}

.link-item {
   background: var(--cart-back-color);
   border: 1px solid var(--cart-border-color);
   border-radius: 16px;
   padding: 20px;
   display: flex;
   align-items: center;
   gap: 15px;
   opacity: 0;
   transform: translateY(20px);
   transition:
      opacity 0.4s ease-out,
      transform 0.4s ease-out;
}

.link-item.show {
   opacity: 1;
   transform: translateY(0);
}

.link-item:hover {
   transform: translateY(-5px);
   box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
}

.link-icon {
   width: 50px;
   height: 50px;
   border-radius: 12px;
   overflow: hidden;
   flex-shrink: 0;
   background: rgba(255, 255, 255, 0.1);
}

.link-icon img {
   width: 100%;
   height: 100%;
   object-fit: contain;
   padding: 5px;
}

.link-info {
   flex: 1;
   text-align: left;
}

.link-name {
   font-size: 1.1rem;
   font-weight: 600;
   margin-bottom: 4px;
}

.link-desc {
   font-size: 0.85rem;
   opacity: 0.7;
}

.link-btn {
   padding: 8px 16px;
   background: var(--el-but-back);
   color: #fff;
   border-radius: 8px;
   text-decoration: none;
   font-size: 0.85rem;
   font-weight: 500;
   transition: background 0.3s ease;
}

.link-btn:hover {
   opacity: 0.8;
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
   margin-top: 100px;
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
   .links-container {
      grid-template-columns: 1fr;
   }
   .link-item {
      padding: 15px;
      gap: 12px;
   }
   .link-icon {
      width: 40px;
      height: 40px;
   }
}
</style>
