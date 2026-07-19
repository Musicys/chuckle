<template>
   <div class="desc">
      <div class="left" v-if="Ispc">
         <div
            class="box"
            style="margin-bottom: 1em; padding: 0; overflow: hidden">
            <Usercart></Usercart>
         </div>
         <div class="box" style="margin-bottom: 1em">
            <Announcement></Announcement>
         </div>
         <div class="box ml-box">
            <div class="left-top">
               <el-icon> <Memo /> </el-icon>目录
            </div>
            <div class="ml">
               <MdCatalog
                  :key="scrollElementKey"
                  :editorId="editorId"
                  :scrollElement="currentScrollElement"
                  :headings="['h1', 'h2', 'h3']"
                  :indent="16"
                  :offsetTop="60" />
            </div>
         </div>
      </div>
      <div class="right">
         <div v-if="loading" style="text-align: center; padding: 2em">
            <svg
               class="icon"
               aria-hidden="true"
               style="font-size: 2em; animation: spin 1s linear infinite">
               <use xlink:href="#icon-loading"></use>
            </svg>
            <div style="margin-top: 1em">加载中...</div>
         </div>
         <template v-else>
            <MarkTop :article="article" />
            <MdPreview
               :editorId="editorId"
               :model-value="markdownContent"
               :style="customTheme"
               :codeFoldable="false" />
            <MessageBoard
               :articleId="Number(id)"
               @submit="handleSubmitComment" />
         </template>
      </div>
   </div>
</template>

<script setup lang="ts">
import { Ispc } from '@/util/windows';
import { ref, onMounted, watch, computed, nextTick } from 'vue';
import Usercart from '../home/usercart.vue';
import Announcement from './Announcement.vue';
import { MdPreview, MdCatalog } from 'md-editor-v3';
import 'md-editor-v3/lib/preview.css';
import MarkTop from './MarkTop.vue';
import { descIsFlex, desc_mr } from '@/util/scrse';
import MessageBoard from './MessageBoard.vue';
import util from '@/util/function';
import { useRoute } from 'vue-router';
import { fetchArticleById, submitComment } from '@/api/desc';

const route = useRoute();
const id = ref(route.params.id);
const article = ref<any>(null);
const markdownContent = ref('');
const loading = ref(true);
const editorId = 'desc-preview-' + Date.now();

const scrollContainerRef = ref<HTMLElement | null>(null);
let cachedScrollElement: HTMLElement | null = null;
const scrollElementKey = ref(0);

const currentScrollElement = computed(() => {
   return cachedScrollElement;
});

watch(
   () => route.params.id,
   newId => {
      id.value = newId;
      if (newId) {
         loadArticle(Number(newId));
      }
   }
);

async function loadArticle(articleId: number) {
   loading.value = true;
   try {
      const result = await fetchArticleById(articleId);
      if (result && result.data) {
         article.value = result.data;
         markdownContent.value = result.data.content || '';
         await nextTick();
         setTimeout(() => {
            setupScrollContainer();
         }, 300);
      }
   } catch (error) {
      console.error('加载文章失败:', error);
      markdownContent.value = '# 文章加载失败';
   } finally {
      loading.value = false;
   }
}

function setupScrollContainer() {
   const container = document.querySelector('.page-zqdongz') as HTMLElement;
   if (container) {
      cachedScrollElement = container;
      scrollElementKey.value++;
      console.log('scroll container found:', container);
   } else {
      console.log('scroll container not found');
   }
}

async function handleSubmitComment(commentData: {
   nickname: string;
   email?: string;
   content: string;
   replyToCommentId?: number;
}) {
   try {
      const result = await submitComment({
         articleId: Number(id.value),
         ...commentData
      });
      console.log('评论提交成功:', result);
   } catch (error) {
      console.error('评论提交失败:', error);
   }
}

const customTheme: Record<string, string> = {
   '--md-bk-color': 'var(--datail-back-color)',
   '--md-color': 'var(--bk-font-color)',
   '--md-border-color': 'var(--cart-border-color)',
   '--md-color-primary': 'var(--bk-draw-back-color)',
   '--md-blockquote-bk-color': 'var(--cart-back-color)',
   '--md-blockquote-border-color': 'var(--bk-draw-back-color)',
   '--md-table-bk-color': 'var(--cart-back-color)',
   '--md-table-border-color': 'var(--cart-border-color)',
   '--md-table-th-bk-color': 'var(--bk-draw-back-color)',
   '--md-editor-font-size': '16px'
};

onMounted(() => {
   nextTick(() => {
      setupScrollContainer();
   });

   if (id.value) {
      loadArticle(Number(id.value));
   }
});
</script>

<style lang="scss" scoped>
.ml-box {
   height: 85vh;
   position: sticky;
   top: 0;
}

.bordr {
   color: var(--bk-font-color);
   border-radius: 10px;
   background: var(--cart-back-color);
   border: 3px solid var(--cart-border-color);
   padding: 5px;
}

.desc {
   display: flex;
   padding-top: 50px;

   .left {
      position: relative;
      flex: 1;

      .box {
         @extend .bordr;

         .left-top {
            display: flex;
            justify-content: start;
            align-items: center;
            margin-bottom: 0.5em;
         }

         .ml {
            height: 80vh;
            overflow-y: auto;
            overflow-x: hidden;

            &::-webkit-scrollbar-track {
               background: var(--cart-back-color);
            }

            &::-webkit-scrollbar {
               width: 5px;
            }
         }
      }
   }

   .right {
      margin-left: 1em;
      margin-bottom: 2em;
      flex: 4;
      @extend .bordr;
   }
}

:deep(.md-editor-catalog) {
   padding: 0;

   ul {
      list-style: none;
      padding-left: 0;
      margin: 0;
   }

   li {
      padding: 2px 0;
   }

   a {
      display: block;
      padding: 4px 6px;
      text-decoration: none;
      color: var(--bk-font-color);
      cursor: pointer;
      border-radius: 4px;
      transition: all 0.3s ease;

      &:hover {
         background: rgba(129, 209, 239, 0.5);
      }

      &.active {
         background: var(--el-but-back);
         color: #fff;
      }
   }
}

:deep(.md-editor-preview) {
   line-height: 2.2;

   p {
      margin-bottom: 24px;
   }

   h1,
   h2,
   h3,
   h4,
   h5,
   h6 {
      margin-top: 1.5em;
      margin-bottom: 0.8em;
      color: var(--bk-font-color);
   }

   h1 {
      font-size: 2em;
      border-bottom: 2px solid var(--cart-border-color);
      padding-bottom: 0.3em;
   }

   h2 {
      font-size: 1.6em;
      border-bottom: 1px solid var(--cart-border-color);
      padding-bottom: 0.2em;
   }

   h3 {
      font-size: 1.4em;
   }

   a {
      color: var(--bk-draw-back-color);
      text-decoration: none;

      &:hover {
         text-decoration: underline;
         color: var(--div-hover-color);
      }
   }

   ul,
   ol {
      padding-left: 1.5em;
      margin-bottom: 1em;
   }

   li {
      margin-bottom: 0.5em;
   }

   blockquote {
      border-left: 4px solid var(--bk-draw-back-color);
      padding: 10px 20px;
      margin: 1em 0;
      background: var(--cart-back-color);
      border-radius: 0 4px 4px 0;
   }

   pre {
      margin: 1em 0;
   }

   table {
      width: 100%;
      border-collapse: collapse;
      margin: 1em 0;
      border: 1px solid var(--cart-border-color);
      border-radius: 8px;
      overflow: hidden;
   }

   th {
      background: var(--bk-draw-back-color);
      color: #fff;
      padding: 12px;
      text-align: left;
      font-weight: bold;
      border-bottom: 2px solid var(--cart-border-color);
   }

   td {
      padding: 12px;
      border-bottom: 1px solid var(--cart-border-color);
      border-right: 1px solid var(--cart-border-color);

      &:last-child {
         border-right: none;
      }
   }

   tr:nth-child(even) {
      background: rgba(0, 0, 0, 0.05);
   }

   tr:hover {
      background: rgba(129, 209, 239, 0.2);
   }

   img {
      max-width: 100%;
      border-radius: 8px;
      margin: 1em 0;
   }

   hr {
      border: none;
      border-top: 1px solid var(--cart-border-color);
      margin: 2em 0;
   }

   strong {
      font-weight: bold;
      color: var(--bk-font-color);
   }

   em {
      font-style: italic;
   }
}
</style>
