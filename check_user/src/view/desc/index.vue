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
               <nav class="toc-nav">
                  <ul>
                     <li
                        v-for="item in toc"
                        :key="item.id"
                        :class="['toc-item', { active: activeId === item.id }]"
                        :style="{ paddingLeft: `${(item.level - 1) * 16}px` }"
                        @click="scrollToHeading(item.id)">
                        {{ item.text }}
                     </li>
                  </ul>
               </nav>
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
               :model-value="markdownContent"
               :style="customTheme"
               :codeFoldable="false" />
            <MessageBoard
               ref="messageBoardRef"
               :articleId="Number(id)"
               @submit="handleSubmitComment"
               @reply="handleReplyComment" />
         </template>
      </div>
   </div>
</template>

<script setup lang="ts">
import { Ispc } from '@/util/windows';
import { ref, onMounted, watch, nextTick } from 'vue';
import Usercart from '../home/usercart.vue';
import Announcement from './Announcement.vue';
import { MdPreview } from 'md-editor-v3';
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

const toc = ref<{ id: string; text: string; level: number }[]>([]);
const activeId = ref('');
let scrollTimeout: number | null = null;

const messageBoardRef = ref<InstanceType<typeof MessageBoard> | null>(null);

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
            extractTocFromDom();
         }, 500);
      }
   } catch (error) {
      console.error('加载文章失败:', error);
      markdownContent.value = '# 文章加载失败';
   } finally {
      loading.value = false;
   }
}

function extractTocFromDom() {
   console.log('=== extractTocFromDom ===');
   const mdEditor = document.querySelector('.desc .right .md-editor');
   let headings;
   if (mdEditor) {
      headings = mdEditor.querySelectorAll('h1, h2, h3');
   } else {
      headings = document.querySelectorAll('h1, h2, h3');
   }
   console.log('headings found:', headings.length);
   headings.forEach((heading, index) => {
      const h = heading as HTMLElement;
      console.log(
         `heading ${index}: tag=${h.tagName}, id=${h.id}, text=${h.textContent}, innerText=${h.innerText}`
      );
   });
   toc.value = [];
   headings.forEach((heading, index) => {
      const h = heading as HTMLElement;
      let headingId = h.id || `heading-${index}-${Date.now()}`;
      headingId = headingId.replace(/\s+/g, '-').replace(/[^\w-]/g, '');
      h.id = headingId;
      const level = parseInt(h.tagName.replace('H', ''));
      toc.value.push({
         id: headingId,
         text: h.textContent || '',
         level
      });
   });
   console.log('toc generated:', toc.value);
}

function scrollToHeading(headingId: string) {
   console.log('=== scrollToHeading ===');
   console.log('headingId:', headingId);
   activeId.value = headingId;

   let element = document.getElementById(headingId);
   console.log('element by id:', element);

   if (!element) {
      const mdEditor = document.querySelector('.desc .right .md-editor');
      if (mdEditor) {
         element = mdEditor.querySelector(`[id="${headingId}"]`);
         console.log('element found in mdEditor:', element);
      }
   }

   if (!element) {
      const allHeadings = document.querySelectorAll(
         '.md-editor h1, .md-editor h2, .md-editor h3'
      );
      console.log('all headings available:', allHeadings.length);
      allHeadings.forEach((h, i) => {
         const el = h as HTMLElement;
         console.log(`heading ${i}: id=${el.id}, text=${el.textContent}`);
      });
      return;
   }

   element.scrollIntoView({ behavior: 'smooth', block: 'start' });
   setTimeout(() => {
      const scrollContainer = document.querySelector(
         '.page-zqdongz'
      ) as HTMLElement;
      if (scrollContainer) {
         scrollContainer.scrollTop = scrollContainer.scrollTop - 80;
      } else {
         window.scrollBy(0, -80);
      }
   }, 100);
}

function handleScroll() {
   console.log('=== handleScroll ===');

   const headings = toc.value;
   if (!headings || headings.length === 0) {
      console.log('no headings to check');
      return;
   }

   let currentActiveId = '';
   const scrollTop = window.scrollY || document.documentElement.scrollTop;
   console.log('scrollTop:', scrollTop);

   for (let i = headings.length - 1; i >= 0; i--) {
      const element = document.getElementById(headings[i].id);
      if (element) {
         const rect = element.getBoundingClientRect();
         console.log(
            `heading ${i}: id=${headings[i].id}, text=${element.textContent}, rect.top=${rect.top}`
         );
         if (rect.top <= 120) {
            currentActiveId = headings[i].id;
            break;
         }
      }
   }

   console.log('currentActiveId:', currentActiveId);
   activeId.value = currentActiveId;
}

async function handleSubmitComment(commentData: {
   nickname: string;
   email?: string;
   content: string;
   avatar?: string;
}) {
   try {
      const result = await submitComment({
         articleId: Number(id.value),
         ...commentData
      });
      console.log('评论提交成功:', result);
      if (messageBoardRef.value) {
         messageBoardRef.value.loadComments();
      }
   } catch (error) {
      console.error('评论提交失败:', error);
   }
}

async function handleReplyComment(commentData: {
   nickname: string;
   email?: string;
   content: string;
   avatar?: string;
   replyToCommentId: number;
}) {
   try {
      const result = await submitComment({
         articleId: Number(id.value),
         ...commentData
      });
      console.log('回复提交成功:', result);
      if (messageBoardRef.value) {
         messageBoardRef.value.loadComments();
      }
   } catch (error) {
      console.error('回复提交失败:', error);
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

onMounted(async () => {
   if (id.value) {
      loadArticle(Number(id.value));
   }

   await nextTick();
   setTimeout(() => {
      const scrollContainer = document.querySelector(
         '.page-zqdongz'
      ) as HTMLElement;
      if (scrollContainer) {
         scrollContainer.addEventListener('scroll', handleScroll);
         console.log('scroll listener registered on .page-zqdongz');
      } else {
         window.addEventListener('scroll', handleScroll);
         console.log('scroll listener registered on window');
      }
   }, 100);
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

.toc-nav {
   padding: 0;

   ul {
      list-style: none;
      padding-left: 0;
      margin: 0;
   }

   .toc-item {
      padding: 4px 6px;
      cursor: pointer;
      border-radius: 4px;
      transition: all 0.3s ease;
      color: var(--bk-font-color);
      font-size: 14px;

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
      scroll-margin-top: 80px;
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
