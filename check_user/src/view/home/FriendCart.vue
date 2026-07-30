<template>
   <div
      class="friend-cart"
      :class="{ 'lazy-visible': isVisible }"
      ref="containerRef">
      <div class="friend-header">
         <div class="friend-title">
            <el-icon>
               <User />
            </el-icon>
            通讯录
         </div>
      </div>

      <div class="friend-body">
         <div class="friend-tabs">
            <div
               class="friend-tab"
               :class="{ active: activeYear === 'all' }"
               @click="switchYear('all')">
               全部
            </div>
            <div
               v-for="year in years"
               :key="year"
               class="friend-tab"
               :class="{ active: activeYear === year }"
               @click="switchYear(year)">
               {{ year }}
            </div>
         </div>

         <div class="friend-content">
            <div v-if="loading" class="friend-loading">
               <svg class="spinner" viewBox="0 0 50 50">
                  <circle
                     class="path"
                     cx="25"
                     cy="25"
                     r="20"
                     fill="none"
                     stroke-width="5"></circle>
               </svg>
            </div>
            <div
               v-else-if="filteredFriendLinks.length === 0"
               class="friend-empty">
               暂无友链
            </div>
            <div v-else class="friend-scroll">
               <div
                  class="friend-item"
                  v-for="link in paginatedFriendLinks"
                  :key="link.id"
                  @click="openLink(link.url)"
                  @mouseenter="hoveredItem = link.id"
                  @mouseleave="hoveredItem = null">
                  <div class="friend-icon">
                     <img v-lazy="link.avatar" :alt="link.name" />
                     <div
                        v-if="!loadedAvatars.includes(link.id)"
                        class="avatar-placeholder">
                        <el-icon><User /></el-icon>
                     </div>
                  </div>
                  <div class="friend-info">
                     <div class="friend-name">{{ link.name }}</div>
                     <div class="friend-desc">{{ link.description }}</div>
                  </div>
                  <div
                     class="friend-arrow"
                     :class="{ visible: hoveredItem === link.id }">
                     <svg
                        viewBox="0 0 24 24"
                        fill="none"
                        stroke="currentColor"
                        stroke-width="2">
                        <path d="M9 18l6-6-6-6" />
                     </svg>
                  </div>
               </div>
            </div>

            <div
               v-if="filteredFriendLinks.length > pageSize"
               class="friend-pagination">
               <button
                  class="pagination-btn"
                  :class="{ disabled: currentPage === 1 }"
                  @click="prevPage">
                  <svg
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
                  :class="{ disabled: currentPage === totalPages }"
                  @click="nextPage">
                  <svg
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
   </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { getFriendLinks, type FriendLinkItem } from '@/api/start';
import { User } from '@element-plus/icons-vue';
import { useLazyVisible } from './useLazyVisible';

const { isVisible, containerRef } = useLazyVisible();

const loading = ref(false);
const friendLinks = ref<FriendLinkItem[]>([]);
const hoveredItem = ref<number | null>(null);
const loadedAvatars = ref<number[]>([]);
const activeYear = ref('all');
const currentPage = ref(1);
const pageSize = ref(8);

const switchYear = (year: string) => {
   activeYear.value = year;
   currentPage.value = 1;
};

const openLink = (url: string) => {
   if (url) {
      window.open(url, '_blank');
   }
};

const years = computed(() => {
   const yearSet = new Set<string>();
   friendLinks.value.forEach(link => {
      if (link.createdAt) {
         yearSet.add(link.createdAt.split(' ')[0].split('-')[0]);
      }
   });
   return Array.from(yearSet).sort((a, b) => b.localeCompare(a));
});

const filteredFriendLinks = computed(() => {
   let filtered = friendLinks.value;
   if (activeYear.value !== 'all') {
      filtered = filtered.filter(link => {
         if (!link.createdAt) return false;
         return link.createdAt.split(' ')[0].split('-')[0] === activeYear.value;
      });
   }
   return filtered;
});

const paginatedFriendLinks = computed(() => {
   const start = (currentPage.value - 1) * pageSize.value;
   const end = start + pageSize.value;
   return filteredFriendLinks.value.slice(start, end);
});

const totalPages = computed(() => {
   return Math.ceil(filteredFriendLinks.value.length / pageSize.value);
});

const prevPage = () => {
   if (currentPage.value > 1) {
      currentPage.value--;
   }
};

const nextPage = () => {
   if (currentPage.value < totalPages.value) {
      currentPage.value++;
   }
};

const loadFriendLinks = async () => {
   loading.value = true;
   try {
      const res = await getFriendLinks();
      if (res.code === 0) {
         friendLinks.value = res.data;
      }
   } catch (error) {
      console.error('加载友链失败:', error);
   } finally {
      loading.value = false;
   }
};

onMounted(() => {
   loadFriendLinks();
});
</script>

<style scoped>
.friend-cart {
   @extend .bordr;
   overflow: hidden;
   opacity: 0;
   transform: translateY(30px);
   transition:
      opacity 0.5s ease,
      transform 0.5s ease;
}

.friend-cart.lazy-visible {
   opacity: 1;
   transform: translateY(0);
}

.friend-header {
   display: flex;
   justify-content: space-between;
   align-items: center;
   padding: 1em;
}

.friend-title {
   display: flex;
   align-items: center;
   gap: 8px;
   font-weight: 600;
   font-size: 1em;
}

.friend-body {
   overflow: hidden;
}

.friend-tabs {
   display: flex;
   flex-wrap: wrap;
   gap: 0.5em;
   padding: 1em;
   border-bottom: 1px solid var(--cart-border-color);
}

.friend-tab {
   padding: 0.3em 0.8em;
   border-radius: 15px;
   font-size: 0.85em;
   cursor: pointer;
   transition: all 0.3s ease;
   background: var(--cart-back-color);
   border: 1px solid var(--cart-border-color);

   &:hover {
      background: var(--back-op-color);
   }

   &.active {
      background: #06c0b4;
      border-color: #06c0b4;
      color: white;
   }
}

.friend-content {
   min-height: 200px;
   display: flex;
   flex-direction: column;
}

.friend-loading {
   display: flex;
   justify-content: center;
   padding: 2em;
}

.friend-loading .spinner {
   width: 30px;
   height: 30px;
   animation: spin 1s linear infinite;
}

.friend-loading .spinner .path {
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

.friend-empty {
   text-align: center;
   padding: 2em;
   opacity: 0.6;
   font-size: 0.9em;
}

.friend-scroll {
   flex: 1;
   max-height: 350px;
   overflow-y: auto;
   padding: 0.5em 1em;

   &::-webkit-scrollbar {
      width: 4px;
   }

   &::-webkit-scrollbar-track {
      background: transparent;
   }

   &::-webkit-scrollbar-thumb {
      background: var(--cart-border-color);
      border-radius: 2px;
   }
}

.friend-item {
   display: flex;
   align-items: center;
   padding: 0.8em;
   border-radius: 10px;
   cursor: pointer;
   transition: all 0.3s ease;
   margin-bottom: 0.5em;

   &:hover {
      background: var(--back-op-color);
      transform: translateX(5px);
   }
}

.friend-icon {
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
   overflow: hidden;
   position: relative;

   img {
      width: 100%;
      height: 100%;
      object-fit: cover;
   }
}

.avatar-placeholder {
   position: absolute;
   inset: 0;
   display: flex;
   align-items: center;
   justify-content: center;
   color: var(--bk-font-color);
   opacity: 0.5;
}

.friend-info {
   display: flex;
   flex-direction: column;
   align-items: flex-start;
   flex: 1;
   overflow: hidden;
}

.friend-name {
   font-size: 0.95em;
   font-weight: 600;
   color: var(--bk-font-color);
   margin-bottom: 0.2em;
   white-space: nowrap;
   overflow: hidden;
   text-overflow: ellipsis;

   &:hover {
      color: #06c0b4;
   }
}

.friend-desc {
   font-size: 0.8em;
   color: var(--cart-home-time-color);
   white-space: nowrap;
   overflow: hidden;
   text-overflow: ellipsis;

   span {
      font-size: 0.85em;
   }
}

.friend-arrow {
   opacity: 0;
   transition: opacity 0.3s ease;

   &.visible {
      opacity: 1;
   }

   svg {
      width: 16px;
      height: 16px;
      color: var(--bk-font-color);
   }
}

.friend-pagination {
   display: flex;
   align-items: center;
   justify-content: center;
   gap: 1em;
   padding: 1em;
   border-top: 1px solid var(--cart-border-color);
}

.pagination-btn {
   width: 30px;
   height: 30px;
   border-radius: 50%;
   border: 1px solid var(--cart-border-color);
   background: var(--cart-back-color);
   display: flex;
   align-items: center;
   justify-content: center;
   cursor: pointer;
   transition: all 0.3s ease;

   &:hover:not(.disabled) {
      background: var(--back-op-color);
      transform: translateY(-2px);
   }

   &.disabled {
      opacity: 0.4;
      cursor: not-allowed;
   }

   svg {
      width: 14px;
      height: 14px;
      color: var(--bk-font-color);
   }
}

.pagination-info {
   font-size: 0.9em;
   color: var(--bk-font-color);
}
</style>
