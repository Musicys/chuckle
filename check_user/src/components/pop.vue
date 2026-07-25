<template>
   <div class="pop-modal" :class="{ active: Ispop }">
      <div class="pop-mask" @click="endpop"></div>
      <div class="pop-content">
         <div class="pop-header">
            <span class="pop-title">在线搜索</span>
            <button class="pop-close" @click="close">
               <svg
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  stroke-width="2">
                  <line x1="18" y1="6" x2="6" y2="18" />
                  <line x1="6" y1="6" x2="18" y2="18" />
               </svg>
            </button>
         </div>
         <div class="search-wrapper">
            <svg
               class="search-icon"
               viewBox="0 0 24 24"
               fill="none"
               stroke="currentColor"
               stroke-width="2">
               <circle cx="11" cy="11" r="8" />
               <line x1="21" y1="21" x2="16.65" y2="16.65" />
            </svg>
            <input
               v-model="keyword"
               @keyup.enter="handleSearch"
               placeholder="搜索文章关键词..."
               type="text"
               class="search-input" />
            <button v-if="keyword" class="clear-btn" @click="clearSearch">
               <svg
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  stroke-width="2">
                  <line x1="18" y1="6" x2="6" y2="18" />
                  <line x1="6" y1="6" x2="18" y2="18" />
               </svg>
            </button>
         </div>
         <div class="divider"></div>
         <div class="search-results">
            <div v-if="loading" class="loading-state">
               <div class="loading-dots">
                  <span></span>
                  <span></span>
                  <span></span>
               </div>
               <span>搜索中...</span>
            </div>
            <div
               v-else-if="results.length === 0 && !loading"
               class="empty-state">
               <svg
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  stroke-width="2">
                  <circle cx="11" cy="11" r="8" />
                  <line x1="21" y1="21" x2="16.65" y2="16.65" />
               </svg>
               <span>{{
                  keyword ? '未找到相关文章' : '请输入关键词搜索'
               }}</span>
            </div>
            <div v-else class="results-list">
               <div
                  v-for="(item, index) in results"
                  :key="item.id"
                  class="result-item"
                  :style="{ animationDelay: `${index * 50}ms` }"
                  @click="goToArticle(item.id)">
                  <div
                     class="result-title"
                     v-html="item.titleHighlight || item.title"></div>
                  <div
                     class="result-summary"
                     v-html="item.summaryHighlight || item.summary"></div>
                  <div class="result-meta">
                     <span
                        class="category"
                        :style="{
                           color: item.category?.name
                              ? '#49b1f5'
                              : 'transparent'
                        }">
                        {{ item.category?.name || '' }}
                     </span>
                     <span class="read-count">{{ item.readCount }} 阅读</span>
                     <span class="date">{{ formatDate(item.createdAt) }}</span>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import { searchArticles, type ArticleItem } from '@/api/home';
import { useAppStore } from '@/store';
import { storeToRefs } from 'pinia';

const router = useRouter();
const appStore = useAppStore();
const { Ispop } = storeToRefs(appStore);
const { endpop } = appStore;

const keyword = ref('');
const results = ref<ArticleItem[]>([]);
const loading = ref(false);
let debounceTimer: ReturnType<typeof setTimeout> | null = null;

const debounce = <T extends (...args: any[]) => void>(
   fn: T,
   delay: number
): ((...args: Parameters<T>) => void) => {
   return (...args: Parameters<T>) => {
      if (debounceTimer) {
         clearTimeout(debounceTimer);
      }
      debounceTimer = setTimeout(() => {
         fn(...args);
      }, delay);
   };
};

const handleSearch = async () => {
   if (!keyword.value.trim()) {
      results.value = [];
      return;
   }

   loading.value = true;

   try {
      const response = await searchArticles({
         keyword: keyword.value.trim(),
         current: 1,
         pageSize: 20
      });

      if (response.code === 0) {
         results.value = response.data.records;
      }
   } catch (error) {
      console.error('搜索失败:', error);
   } finally {
      loading.value = false;
   }
};

const debouncedSearch = debounce(handleSearch, 300);

const clearSearch = () => {
   keyword.value = '';
   results.value = [];
};

const goToArticle = (id: number) => {
   close();
   router.push(`/desc/${id}`);
};

const close = () => {
   endpop();
};

const formatDate = (dateStr: string) => {
   if (!dateStr) return '';
   const date = new Date(dateStr);
   return `${date.getMonth() + 1}/${date.getDate()}`;
};

const handleKeydown = (e: KeyboardEvent) => {
   if (e.key === 'Escape' && Ispop.value) {
      close();
   }
};

onMounted(() => {
   document.addEventListener('keydown', handleKeydown);
});

onUnmounted(() => {
   document.removeEventListener('keydown', handleKeydown);
   if (debounceTimer) {
      clearTimeout(debounceTimer);
   }
});

watch(keyword, () => {
   debouncedSearch();
});
</script>

<style lang="scss" scoped>
.pop-modal {
   position: fixed;
   top: 0;
   left: 0;
   right: 0;
   bottom: 0;
   z-index: 99999;
   opacity: 0;
   pointer-events: none;
   transition: opacity 0.3s ease;

   &.active {
      opacity: 1;
      pointer-events: auto;

      .pop-mask {
         background: rgba(0, 0, 0, 0.5);
      }

      .pop-content {
         transform: translateX(-50%) translateY(0) scale(1);
         opacity: 1;
      }
   }

   .pop-mask {
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background: rgba(0, 0, 0, 0);
      transition: background 0.3s ease;
   }

   .pop-content {
      position: absolute;
      top: 80px;
      left: 50%;
      transform: translateX(-50%) translateY(-20px) scale(0.95);
      width: 720px;
      max-height: 75vh;
      background: var(--cart-back-color);
      border-radius: 12px;
      border: 3px solid var(--cart-border-color);
      box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
      overflow: hidden;
      opacity: 0;
      transition: all 0.3s cubic-bezier(0.34, 1.56, 0.64, 1);

      .pop-header {
         display: flex;
         justify-content: space-between;
         align-items: center;
         padding: 16px 20px;
         background: var(--bk-draw-back-color);
         border-bottom: 1px solid var(--cart-border-color);

         .pop-title {
            font-size: 16px;
            font-weight: 600;
            color: var(--bk-font-color);
         }

         .pop-close {
            display: flex;
            align-items: center;
            justify-content: center;
            width: 28px;
            height: 28px;
            border: none;
            background: transparent;
            cursor: pointer;
            border-radius: 6px;
            transition: all 0.2s;

            svg {
               width: 16px;
               height: 16px;
               color: var(--bk-font-color);
            }

            &:hover {
               background: rgba(0, 0, 0, 0.1);
               transform: rotate(90deg);
            }
         }
      }

      .search-wrapper {
         display: flex;
         align-items: center;
         padding: 14px 20px;
         background: var(--cart-back-color);

         .search-icon {
            width: 16px;
            height: 16px;
            color: #999;
            margin-right: 12px;
            flex-shrink: 0;
         }

         .search-input {
            flex: 1;
            padding: 10px 14px;
            border: 2px solid var(--cart-border-color);
            border-radius: 8px;
            font-size: 14px;
            background: white;
            color: var(--bk-font-color);
            outline: none;
            transition: all 0.2s;

            &::placeholder {
               color: #bbb;
            }

            &:focus {
               border-color: #49b1f5;
               box-shadow: 0 0 0 3px rgba(73, 177, 245, 0.1);
            }
         }

         .clear-btn {
            display: flex;
            align-items: center;
            justify-content: center;
            width: 28px;
            height: 28px;
            border: none;
            background: transparent;
            cursor: pointer;
            margin-left: 8px;
            border-radius: 6px;
            transition: all 0.2s;

            svg {
               width: 14px;
               height: 14px;
               color: #999;
            }

            &:hover {
               background: rgba(0, 0, 0, 0.05);
               svg {
                  color: var(--bk-font-color);
               }
            }
         }
      }

      .divider {
         height: 2px;
         background: repeating-linear-gradient(
            90deg,
            #49b1f5 0,
            #49b1f5 8px,
            transparent 8px,
            transparent 16px
         );
         margin: 0 20px;
      }

      .search-results {
         padding: 12px 20px;
         max-height: calc(70vh - 160px);
         overflow-y: auto;

         &::-webkit-scrollbar {
            width: 4px;
         }

         &::-webkit-scrollbar-thumb {
            background: var(--cart-border-color);
            border-radius: 2px;
         }

         .loading-state {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            padding: 40px 0;
            color: #999;
            font-size: 14px;

            .loading-dots {
               display: flex;
               gap: 6px;
               margin-bottom: 12px;

               span {
                  width: 8px;
                  height: 8px;
                  background: #49b1f5;
                  border-radius: 50%;
                  animation: loading 1.4s infinite ease-in-out both;

                  &:nth-child(1) {
                     animation-delay: -0.32s;
                  }
                  &:nth-child(2) {
                     animation-delay: -0.16s;
                  }
               }
            }
         }

         .empty-state {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            padding: 40px 0;
            color: #999;
            font-size: 14px;

            svg {
               width: 40px;
               height: 40px;
               margin-bottom: 12px;
               opacity: 0.5;
            }
         }

         .results-list {
            .result-item {
               padding: 14px 0;
               border-bottom: 1px solid var(--cart-border-color);
               cursor: pointer;
               animation: fadeInUp 0.3s ease-out forwards;
               opacity: 0;
               transform: translateY(10px);

               &:last-child {
                  border-bottom: none;
               }

               &:hover {
                  background: rgba(73, 177, 245, 0.05);
                  margin: 0 -20px;
                  padding: 14px 20px;
                  border-radius: 8px;
               }

               .result-title {
                  font-size: 15px;
                  font-weight: 500;
                  color: var(--bk-font-color);
                  margin-bottom: 6px;
                  line-height: 1.5;

                  :deep(.highlight) {
                     color: #ef4444;
                     font-weight: 600;
                  }
               }

               .result-summary {
                  font-size: 13px;
                  color: #888;
                  line-height: 1.6;
                  display: -webkit-box;
                  -webkit-line-clamp: 2;
                  -webkit-box-orient: vertical;
                  overflow: hidden;

                  :deep(.highlight) {
                     color: #ef4444;
                  }
               }

               .result-meta {
                  display: flex;
                  align-items: center;
                  gap: 12px;
                  margin-top: 8px;
                  font-size: 12px;
                  color: #aaa;

                  .category {
                     padding: 2px 8px;
                     background: rgba(73, 177, 245, 0.1);
                     border-radius: 4px;
                     font-weight: 500;
                  }

                  .read-count,
                  .date {
                     opacity: 0.7;
                  }
               }
            }
         }
      }
   }
}

@keyframes loading {
   0%,
   80%,
   100% {
      transform: scale(0);
   }
   40% {
      transform: scale(1);
   }
}

@keyframes fadeInUp {
   from {
      opacity: 0;
      transform: translateY(10px);
   }
   to {
      opacity: 1;
      transform: translateY(0);
   }
}

@media (max-width: 768px) {
   .pop-content {
      width: 90vw;
      top: 60px;
   }
}
</style>
