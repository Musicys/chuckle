<template>
   <div class="archive-page">
      <!-- 顶部统计卡片 -->
      <div class="archive-header bordr">
         <div class="header-left">
            <h1>文章总览 - {{ totalCount }}</h1>
            <div class="search-box">
               <input
                  v-model="searchQuery"
                  type="text"
                  placeholder="搜索文章标题..."
                  class="search-input"
                  @input="handleSearch" />
               <span
                  v-if="searchQuery"
                  class="search-clear"
                  @click="clearSearch"
                  >×</span
               >
            </div>
         </div>
         <div class="header-right">
            <div class="chart-container">
               <h3>文章发布统计图</h3>
               <div class="bar-chart">
                  <div
                     v-for="(item, index) in monthlyData"
                     :key="index"
                     class="bar-item">
                     <div
                        class="bar"
                        :style="{
                           height: (item.count / maxCount) * 100 + '%'
                        }"></div>
                     <span class="bar-label">{{ item.label }}</span>
                     <span class="bar-value" v-if="item.count > 0">{{
                        item.count
                     }}</span>
                  </div>
               </div>
            </div>
         </div>
      </div>

      <!-- 归档列表 -->
      <div
         class="archive-content bordr"
         :ref="el => setContentRef(el as HTMLElement)"
         :class="{ 'content-visible': contentVisible }">
         <div
            v-for="(group, gIndex) in filteredArchives"
            :key="group.year"
            class="year-section"
            :class="{ 'year-visible': contentVisible }"
            :style="{ '--delay': gIndex * 0.2 + 's' }">
            <h2 class="year-title">{{ group.year }}</h2>
            <div class="articles-grid">
               <div
                  v-for="(article, idx) in group.articles"
                  :key="article.id"
                  class="article-card"
                  :class="{ 'card-visible': contentVisible }"
                  :style="{ '--delay': gIndex * 0.2 + idx * 0.08 + 's' }"
                  @click="goToArticle(article.id)">
                  <div class="article-cover" v-if="article.cover">
                     <img v-lazy="article.cover" :alt="article.title" />
                  </div>
                  <div class="article-info">
                     <span class="article-index">{{
                        String(idx + 1).padStart(2, '0')
                     }}</span>
                     <h3 class="article-title">{{ article.title }}</h3>
                     <div class="article-meta">
                        <span class="meta-item">
                           <svg class="icon" aria-hidden="true">
                              <use xlink:href="#icon-csdn"></use>
                           </svg>
                           学习笔记
                        </span>
                        <span class="meta-item">
                           <svg class="icon" aria-hidden="true">
                              <use xlink:href="#icon-github"></use>
                           </svg>
                           前端
                        </span>
                     </div>
                     <span class="article-date">{{
                        formatDate(article.createdAt)
                     }}</span>
                  </div>
               </div>
            </div>
         </div>
      </div>

      <!-- 空状态 -->
      <div
         v-if="!loading && filteredArchives.length === 0"
         class="empty-state bordr">
         <p>{{ searchQuery ? '未找到匹配的文章' : '暂无归档文章' }}</p>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onBeforeUnmount, nextTick } from 'vue';
import { useRouter } from 'vue-router';
import { fetchArchives, type ArchiveYearGroup } from '@/api/home';

const router = useRouter();
const loading = ref(true);
const archives = ref<ArchiveYearGroup[]>([]);

const searchQuery = ref('');
const debouncedQuery = ref('');
let debounceTimer: ReturnType<typeof setTimeout> | null = null;

const contentRef = ref<HTMLElement | null>(null);
const contentVisible = ref(false);
const hasAnimated = ref(false);
let observer: IntersectionObserver | null = null;

const handleSearch = () => {
   if (debounceTimer) clearTimeout(debounceTimer);
   debounceTimer = setTimeout(() => {
      debouncedQuery.value = searchQuery.value.trim().toLowerCase();
   }, 300);
};

const clearSearch = () => {
   searchQuery.value = '';
   debouncedQuery.value = '';
};

const filteredArchives = computed(() => {
   if (!debouncedQuery.value) return archives.value;
   return archives.value
      .map(group => ({
         ...group,
         articles: group.articles.filter(article =>
            article.title.toLowerCase().includes(debouncedQuery.value)
         )
      }))
      .filter(group => group.articles.length > 0);
});

const setContentRef = (el: HTMLElement | null) => {
   contentRef.value = el;
};

const initObserver = async () => {
   await nextTick();

   if (!contentRef.value) return;

   observer = new IntersectionObserver(
      entries => {
         entries.forEach(entry => {
            if (entry.isIntersecting && !hasAnimated.value) {
               hasAnimated.value = true;
               contentVisible.value = true;
            }
         });
      },
      { threshold: 0.1, rootMargin: '50px' }
   );

   observer.observe(contentRef.value);
};

const totalCount = computed(() => {
   return archives.value.reduce((sum, g) => sum + g.count, 0);
});

const monthlyData = computed(() => {
   const data: { label: string; count: number }[] = [];
   const now = new Date();
   for (let i = 11; i >= 0; i--) {
      const date = new Date(now.getFullYear(), now.getMonth() - i, 1);
      const label = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}`;
      let count = 0;
      archives.value.forEach(group => {
         group.articles.forEach(article => {
            const articleDate = new Date(article.createdAt);
            if (
               articleDate.getFullYear() === date.getFullYear() &&
               articleDate.getMonth() === date.getMonth()
            ) {
               count++;
            }
         });
      });
      data.push({ label, count });
   }
   return data;
});

const maxCount = computed(() => {
   const max = Math.max(...monthlyData.value.map(d => d.count));
   return max || 1;
});

const formatDate = (dateStr: string) => {
   if (!dateStr) return '';
   const date = new Date(dateStr);
   const y = date.getFullYear();
   const m = String(date.getMonth() + 1).padStart(2, '0');
   const d = String(date.getDate()).padStart(2, '0');
   return `${y}-${m}-${d}`;
};

const goToArticle = (id: number) => {
   router.push(`/desc/${id}`);
};

const loadData = async () => {
   try {
      loading.value = true;
      console.log('[Archive] Loading archives...');
      const res = await fetchArchives();
      archives.value = res?.data || [];
      console.log(
         '[Archive] Archives loaded:',
         archives.value.length,
         'groups'
      );
      console.log('[Archive] Total articles:', totalCount.value);
      console.log('[Archive] First group:', archives.value[0]);
      setTimeout(() => {
         initObserver();
      }, 100);
   } catch (error) {
      console.error('[Archive] 加载归档数据失败:', error);
   } finally {
      loading.value = false;
   }
};

onMounted(() => {
   loadData();
});

onBeforeUnmount(() => {
   observer?.disconnect();
});
</script>

<style scoped lang="scss">
.archive-page {
   min-height: 100vh;
   padding: 50px 20px;
   max-width: 1200px;
   margin: 0 auto;
}

.bordr {
   color: var(--bk-font-color);
   border-radius: 10px;
   background: var(--cart-back-color);
   border: 3px solid var(--cart-border-color);
   padding: 16px;
}

.archive-header {
   display: flex;
   justify-content: space-between;
   align-items: flex-start;
   flex-direction: column;
   margin-bottom: 20px;
   gap: 20px;

   .header-left {
      flex-shrink: 0;
      h1 {
         font-size: 2rem;
         color: var(--bk-font-color);
         margin: 0 0 16px;
         font-weight: 700;
      }

      .search-box {
         position: relative;
         width: 300px;

         .search-input {
            width: 100%;
            padding: 10px 36px 10px 14px;
            border: 1px solid var(--cart-border-color);
            border-radius: 8px;
            background: var(--mine-back-color, rgba(255, 255, 255, 0.8));
            color: var(--bk-font-color);
            font-size: 0.95rem;
            outline: none;
            transition: border-color 0.3s;

            &::placeholder {
               color: var(--bk-font-color);
               opacity: 0.5;
            }

            &:focus {
               border-color: var(--cart-border-color);
               box-shadow: 0 0 0 2px rgba(0, 0, 0, 0.1);
            }
         }

         .search-clear {
            position: absolute;
            right: 12px;
            top: 50%;
            transform: translateY(-50%);
            width: 20px;
            height: 20px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
            background: rgba(0, 0, 0, 0.1);
            cursor: pointer;
            font-size: 14px;
            line-height: 1;
            transition: background 0.3s;

            &:hover {
               background: rgba(0, 0, 0, 0.2);
            }
         }
      }
   }

   .header-right {
      flex: 1;
      min-width: 0;
      width: 100%;
      padding-bottom: 20px;

      .chart-container {
         width: 100%;
         box-sizing: border-box;

         h3 {
            text-align: center;
            color: var(--bk-font-color);
            margin: 0 0 20px;
            font-size: 1.1rem;
         }

         .bar-chart {
            display: flex;
            align-items: flex-end;
            justify-content: space-between;
            height: 150px;
            width: 100%;
            box-sizing: border-box;
            padding: 0 10px;
            border-bottom: 2px solid var(--cart-border-color);

            .bar-item {
               display: flex;
               flex-direction: column;
               align-items: center;
               flex: 1;
               height: 100%;
               justify-content: flex-end;
               position: relative;

               .bar {
                  width: 60%;
                  max-width: 30px;
                  background: linear-gradient(
                     to top,
                     var(--cart-border-color),
                     #6ee7b7
                  );
                  border-radius: 4px 4px 0 0;
                  transition: height 0.3s ease;
               }

               .bar-label {
                  position: absolute;
                  bottom: -25px;
                  font-size: 10px;
                  color: var(--bk-font-color);
                  white-space: nowrap;
               }

               .bar-value {
                  position: absolute;
                  top: -20px;
                  font-size: 12px;
                  color: var(--cart-border-color);
                  font-weight: bold;
               }
            }
         }
      }
   }
}

.archive-content {
   min-height: 200px;

   .year-section {
      opacity: 0;
      transform: translateY(40px);
      transition:
         opacity 0.6s ease,
         transform 0.6s ease;
      transition-delay: var(--delay, 0s);
      margin-bottom: 24px;

      &.year-visible {
         opacity: 1;
         transform: translateY(0);
      }

      &:last-child {
         margin-bottom: 0;
      }

      .year-title {
         font-size: 2.5rem;
         color: var(--bk-font-color);
         margin: 0 0 16px;
         padding-left: 16px;
         border-left: 5px solid var(--cart-border-color);
         font-weight: 700;
      }

      .articles-grid {
         display: grid;
         grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
         gap: 16px;
      }

      .article-card {
         display: flex;
         background: var(--mine-back-color, rgba(255, 255, 255, 0.6));
         border-radius: 12px;
         overflow: hidden;
         cursor: pointer;
         position: relative;
         border: 1px solid var(--cart-border-color);
         opacity: 0;
         transform: translateY(20px);
         min-height: 140px;
         transition:
            opacity 0.5s ease,
            transform 0.5s ease,
            box-shadow 0.3s ease;
         transition-delay: var(--delay, 0s);

         &.card-visible {
            opacity: 1;
            transform: translateY(0);
         }

         &:hover {
            transform: translateY(-4px);
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
         }

         .article-cover {
            width: 140px;
            height: 140px;
            flex-shrink: 0;
            overflow: hidden;
            border-radius: 12px 0 0 12px;

            img {
               width: 100%;
               height: 100%;
               object-fit: cover;
               transition: transform 0.3s ease;
               cursor: pointer;

               &:hover {
                  transform: scale(1.1);
               }
            }
         }

         .article-info {
            flex: 1;
            padding: 15px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            position: relative;

            .article-index {
               position: absolute;
               top: -10px;
               right: 10px;
               font-size: 4rem;
               font-weight: 900;
               color: rgba(0, 0, 0, 0.08);
               line-height: 1;
            }

            .article-title {
               font-size: 1rem;
               color: var(--bk-font-color);
               margin: 0;
               font-weight: 600;
               line-height: 1.4;
               display: -webkit-box;
               -webkit-line-clamp: 2;
               -webkit-box-orient: vertical;
               overflow: hidden;
            }

            .article-meta {
               display: flex;
               gap: 10px;
               margin-top: 8px;

               .meta-item {
                  display: flex;
                  align-items: center;
                  gap: 4px;
                  font-size: 0.8rem;
                  color: var(--bk-font-color);
                  opacity: 0.7;

                  .icon {
                     width: 14px;
                     height: 14px;
                  }
               }
            }

            .article-date {
               font-size: 0.85rem;
               color: var(--bk-font-color);
               opacity: 0.6;
               margin-top: 8px;
            }
         }
      }
   }
}

.empty-state {
   text-align: center;
   padding: 100px 0;
   color: var(--bk-font-color);
   opacity: 0.6;
   margin-top: 30px;

   p {
      margin: 0;
   }
}

@media (max-width: 768px) {
   .archive-page {
      padding: 20px 15px;
   }

   .archive-header {
      flex-direction: column;

      .header-right {
         width: 100%;
         flex: 1;
      }
   }

   .archive-content {
      .year-section {
         .year-title {
            font-size: 1.8rem;
         }

         .articles-grid {
            grid-template-columns: 1fr;
         }

         .article-card {
            .article-cover {
               width: 100px;
               height: 80px;
            }
         }
      }
   }
}
</style>
