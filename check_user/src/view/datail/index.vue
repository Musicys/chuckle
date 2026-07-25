<template>
   <div class="bordr page">
      <div class="cart-input">
         <div @click="goBack">
            <el-icon class="icons">
               <DArrowLeft />
            </el-icon>
         </div>

         <div class="input-arg">
            <div
               style="background: #25c2fe; padding-right: 5px"
               @click="goHome">
               首页
            </div>
            <div
               v-for="tag in tags"
               :key="tag.id"
               :class="{ 'tag-active': tag.id === tagId }"
               @click="goToTag(tag.id, tag.name)">
               {{ tag.name }}
            </div>
         </div>
         <div>更多</div>
      </div>
      <div class="tilte">标签 - {{ tagName }}</div>

      <div v-if="loading" class="loading-state">
         <el-icon class="loading-icon"><Loading /></el-icon>
         <span>加载中...</span>
      </div>

      <div v-else-if="articles.length === 0" class="empty-state">
         <el-icon><Document /></el-icon>
         <span>该标签下暂无文章</span>
      </div>

      <div v-else>
         <div
            v-for="(article, index) in articles"
            :key="article.id"
            class="dacat">
            <div class="dacat-time" v-if="shouldShowYear(index)">
               {{ getYear(article.createdAt) }}
            </div>
            <div class="dacat-cart" @click="goToArticle(article.id)">
               <div class="dacat-cart-img">
                  <img
                     :src="
                        article.cover ||
                        'https://tse4-mm.cn.bing.net/th/id/OIP-C.i0PxkbYvoZKnJbU4U0vJHQHaEK?rs=1&pid=ImgDetMain'
                     "
                     :alt="article.title" />
               </div>
               <div class="cart-right">
                  <div class="taion-nr">{{ article.title }}</div>
                  <div class="article-summary">{{ article.summary }}</div>
                  <div class="article-tags">
                     <span
                        v-for="tag in article.tags.slice(0, 3)"
                        :key="tag.id"
                        class="article-tag"
                        :style="{ borderColor: tag.color, color: tag.color }">
                        {{ tag.name }}
                     </span>
                  </div>
               </div>
               <div class="dacat-cart-num">{{ article.readCount }}</div>
               <div class="dacat-cart-time">
                  {{ formatDate(article.createdAt) }}
               </div>
            </div>
         </div>
      </div>
   </div>
</template>

<script setup lang="ts">
import { onMounted, ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { fetchArticles, type ArticleItem } from '@/api/home';
import { getTagList, type Tag } from '@/api/arg';
import { DArrowLeft, Loading, Document } from '@element-plus/icons-vue';

const route = useRoute();
const router = useRouter();

const tagId = ref<number | null>(null);
const tagName = ref('');
const articles = ref<ArticleItem[]>([]);
const loading = ref(false);
const tags = ref<Tag[]>([]);
const tagsLoading = ref(false);

const goBack = () => {
   router.back();
};

const goHome = () => {
   router.push('/home');
};

const goToTag = (id: number, name: string) => {
   router.push({
      path: '/datail',
      query: { tagId: id, tagName: name }
   });
};

const goToArticle = (id: number) => {
   router.push(`/desc/${id}`);
};

const getYear = (dateStr: string) => {
   if (!dateStr) return '';
   return dateStr.split('-')[0];
};

const formatDate = (dateStr: string) => {
   if (!dateStr) return '';
   const date = new Date(dateStr);
   return `${date.getMonth() + 1}-${date.getDate()}`;
};

const shouldShowYear = (index: number) => {
   if (index === 0) return true;
   const currentYear = getYear(articles.value[index].createdAt);
   const prevYear = getYear(articles.value[index - 1].createdAt);
   return currentYear !== prevYear;
};

const loadTags = async () => {
   tagsLoading.value = true;
   try {
      const response = await getTagList();
      if (response.code === 0 && response.data) {
         tags.value = response.data;
      }
   } catch (error) {
      console.error('加载标签列表失败:', error);
   } finally {
      tagsLoading.value = false;
   }
};

const loadArticles = async () => {
   if (!tagId.value) return;

   loading.value = true;
   articles.value = [];

   try {
      const response = await fetchArticles({
         tagId: tagId.value,
         current: 1,
         pageSize: 100
      });

      if (response.code === 0 && response.data) {
         articles.value = response.data.records;
      }
   } catch (error) {
      console.error('加载文章列表失败:', error);
   } finally {
      loading.value = false;
   }
};

watch(
   () => [route.query.tagId, route.query.tagName],
   ([newTagId, newTagName]) => {
      tagId.value = newTagId ? Number(newTagId) : null;
      tagName.value = newTagName || '';
      loadArticles();
   },
   { immediate: true }
);

onMounted(() => {
   loadTags();
});
</script>

<style lang="scss" scoped>
.bordr {
   color: var(--bk-font-color);
   border-radius: 10px;
   background: var(--cart-back-color);
   border: 3px solid var(--cart-border-color);
   padding: 5px;
   margin-top: 50px;
}

.taion-nr:hover {
   transform: translateX(1em);
}

.taion-nr {
   transition: transform 0.5s;
}

.page {
   padding: 10px;

   .cart-input {
      height: 25px;
      display: flex;
      justify-content: space-between;

      & > div:nth-child(1) {
         display: flex;
         justify-content: center;
         align-items: center;
         cursor: pointer;
         padding: 0 10px;
      }

      & > div:nth-child(2) {
         margin: auto 10px;
         height: 100%;

         & > div {
            margin-left: 0.5em;
         }
      }

      & > div:nth-child(3) {
         cursor: pointer;
         width: 50px;
         display: flex;
         justify-content: center;
         align-items: center;
         white-space: nowrap;
      }

      & > div:nth-child(3):hover {
         color: #25c2fe;
      }

      .input-arg {
         display: flex;
         justify-content: space-between;
         align-items: center;
         overflow-y: hidden;
         overflow-x: auto;

         &::-webkit-scrollbar {
            display: none;
         }

         & > div {
            cursor: pointer;
            border-radius: 5px;
            padding: 4px 8px;
            transition: color 0.3s;
            position: relative;
            white-space: nowrap;

            & > span {
               width: 15px;
               position: absolute;
               font-size: 0.5em;
            }
         }

         & > div:hover {
            background: #25c2fe;
         }

         .tag-active {
            background: rgba(73, 177, 245, 0.2);
            color: #49b1f5;
            font-weight: 500;
         }
      }
   }

   .tilte {
      font-size: 1.5em;
      margin: 0.5em auto;
   }

   .loading-state {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 60px 20px;
      color: var(--bk-font-color);
      opacity: 0.5;

      .loading-icon {
         font-size: 48px;
         margin-bottom: 15px;
         animation: spin 1s linear infinite;
      }
   }

   .empty-state {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 60px 20px;
      color: var(--bk-font-color);
      opacity: 0.4;

      el-icon {
         font-size: 48px;
         margin-bottom: 15px;
      }
   }

   /* 屏幕宽度大于500px且小于600px时，每行显示两个子元素 */
   @media (min-width: 800px) {
      .dacat {
         transition: all 0.3s;
         width: 100%;
         margin: 20px auto;
         display: grid;
         grid-template-columns: repeat(auto-fill, minmax(500px, 1fr));
         gap: 10px;
         font-size: 1.5em;
      }

      .dacat-cart-num {
         position: absolute;
         right: 3px;
         top: 5px;
         font-size: 2em;
      }

      .dacat-cart-time {
         position: absolute;
         right: 3px;
         bottom: 5px;
         font-size: 1.2em;
      }
   }

   /* 屏幕宽度小于500px时，每行显示一个子元素 */
   @media (max-width: 800px) {
      .dacat {
         transition: all 0.3s;
         width: 100%;
         margin: 20px auto;
         display: grid;
         grid-template-columns: repeat(auto-fill, minmax(60%, 1fr));
         gap: 10px;
         font-size: 1.2em;
      }

      .dacat-cart-num {
         position: absolute;
         right: 3px;
         top: 5px;
         font-size: 1em;
      }

      .dacat-cart-time {
         position: absolute;
         right: 3px;
         bottom: 5px;
         font-size: 1em;
      }
   }

   .dacat-time {
      font-size: 2em;
      margin-top: 1em;
   }

   .dacat {
      .dacat-cart {
         display: flex;
         height: 120px;
         position: relative;
         flex: 1 1 calc(50% - 20px);
         border-radius: 15px;
         background: var(--datail-back-color);
         padding: 5px;
         cursor: pointer;
         transition: all 0.3s;

         &:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
         }

         .dacat-cart-img {
            position: relative;
            height: 100%;
            width: 120px;
            border-radius: 15px;
            margin-right: 10px;
            overflow: hidden;
            flex-shrink: 0;
         }

         & img {
            transition: all 0.5s;
            position: relative;
            height: 100%;
            width: 100%;
            border-radius: 15px;
            object-fit: cover;
         }

         .cart-right {
            display: flex;
            flex-direction: column;
            justify-content: start;
            flex: 1;
            min-width: 0;

            .article-summary {
               font-size: 0.8em;
               color: var(--bk-font-color);
               opacity: 0.6;
               margin-top: 4px;
               overflow: hidden;
               text-overflow: ellipsis;
               white-space: nowrap;
            }

            .article-tags {
               display: flex;
               gap: 6px;
               margin-top: 8px;

               .article-tag {
                  font-size: 0.7em;
                  padding: 2px 6px;
                  border: 1px solid;
                  border-radius: 4px;
               }
            }
         }
      }
   }
}

@keyframes spin {
   from {
      transform: rotate(0deg);
   }
   to {
      transform: rotate(360deg);
   }
}
</style>
