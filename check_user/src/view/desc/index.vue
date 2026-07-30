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
         <div class="left-sticky">
            <div v-if="toc.length > 0" class="box ml-box">
               <div class="toc-title">
                  <el-icon> <Memo /> </el-icon>目录
               </div>
               <div class="toc-scroll">
                  <nav class="toc-nav">
                     <ul>
                        <li
                           v-for="item in toc"
                           :key="item.id"
                           :class="[
                              'toc-item',
                              { active: activeId === item.id }
                           ]"
                           :style="{
                              paddingLeft: `${(item.level - 1) * 16}px`
                           }"
                           @click="scrollToHeading(item.id)">
                           {{ item.text }}
                        </li>
                     </ul>
                  </nav>
               </div>
            </div>
            <div class="box cart-gx">
               <div class="flex">
                  <div class="flex-tile">
                     <el-icon>
                        <PieChart />
                     </el-icon>
                     最新文章
                  </div>
                  <div class="cart-arg-title-left">
                     <div></div>
                     <div></div>
                     <div></div>
                  </div>
               </div>

               <div class="cart-gx-test">
                  <div
                     class="cart-gx-test-kp"
                     v-for="item in latestArticles"
                     :key="item.id"
                     :class="{ active: item.id === Number(id) }"
                     @click="goToArticle(item.id)">
                     <div class=""><img v-lazy="item.cover" alt="" /></div>
                     <div>
                        <div>{{ item.title }}</div>
                        <div>{{ formatDate(item.createdAt) }}</div>
                     </div>
                  </div>
               </div>
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

            <!-- 上一篇/下一篇 -->
            <div v-if="prevArticle || nextArticle" class="article-nav">
               <a
                  v-if="prevArticle"
                  :href="`/desc/${prevArticle.id}`"
                  class="nav-item nav-prev">
                  <span class="nav-label">← 上一篇</span>
                  <span class="nav-title">{{ prevArticle.title }}</span>
               </a>
               <a
                  v-if="nextArticle"
                  :href="`/desc/${nextArticle.id}`"
                  class="nav-item nav-next">
                  <span class="nav-label">下一篇 →</span>
                  <span class="nav-title">{{ nextArticle.title }}</span>
               </a>
            </div>

            <!-- 推荐文章 -->
            <div v-if="recommendArticles.length > 0" class="recommend-articles">
               <h3 class="recommend-title">
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-tuijian"></use>
                  </svg>
                  推荐文章
               </h3>
               <div class="recommend-scroll-container">
                  <div class="jt-left" @click="scrollLeft">
                     <svg class="icons" aria-hidden="true">
                        <use xlink:href="#icon-zuojiantou"></use>
                     </svg>
                     <svg class="icons" aria-hidden="true">
                        <use xlink:href="#icon-zuojiantou"></use>
                     </svg>
                  </div>
                  <div
                     ref="recommendListRef"
                     class="recommend-list"
                     @wheel="handleRecommendScroll">
                     <a
                        v-for="item in recommendArticles"
                        :key="item.id"
                        :href="`/desc/${item.id}`"
                        class="recommend-item">
                        <div class="recommend-cover">
                           <img :src="item.cover" :alt="item.title" />
                        </div>
                        <div class="recommend-info">
                           <span class="recommend-title-text">{{
                              item.title
                           }}</span>
                           <span class="recommend-summary">
                              <svg class="icon" aria-hidden="true">
                                 <use xlink:href="#icon-desc"></use>
                              </svg>
                              {{ item.summary }}
                           </span>
                           <span class="recommend-meta">
                              <svg class="icon" aria-hidden="true">
                                 <use xlink:href="#icon-yuedu"></use>
                              </svg>
                              {{ item.readCount }}
                              <span class="dot">·</span>
                              <svg class="icon" aria-hidden="true">
                                 <use xlink:href="#icon-pinglun"></use>
                              </svg>
                              {{ item.commentCount }}
                           </span>
                        </div>
                     </a>
                  </div>
                  <div class="jt-right" @click="scrollRight">
                     <svg class="icons" aria-hidden="true">
                        <use xlink:href="#icon-youjiantou"></use>
                     </svg>
                     <svg class="icons" aria-hidden="true">
                        <use xlink:href="#icon-youjiantou"></use>
                     </svg>
                  </div>
               </div>
            </div>

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
import { ref, onMounted, watch, nextTick, onBeforeUnmount } from 'vue';
import { PieChart } from '@element-plus/icons-vue';
import Usercart from '../home/usercart.vue';
import Announcement from './Announcement.vue';
import { MdPreview } from 'md-editor-v3';
import 'md-editor-v3/lib/preview.css';
import MarkTop from './MarkTop.vue';
import { descIsFlex, desc_mr } from '@/util/scrse';
import MessageBoard from './MessageBoard.vue';
import util from '@/util/function';
import { useRoute } from 'vue-router';
import {
   fetchArticleById,
   submitComment,
   type ArticleDetail
} from '@/api/desc';
import { fetchArticles, type ArticleItem } from '@/api/home';

const SITE_NAME = '我的技术博客';
const SITE_URL = 'https://www.myblog.com';

function setMeta(name: string, content: string) {
   let el = document.querySelector(
      `meta[name="${name}"], meta[property="${name}"]`
   ) as HTMLMetaElement | null;
   if (!el) {
      el = document.createElement('meta');
      if (name.startsWith('og:')) {
         el.setAttribute('property', name);
      } else {
         el.setAttribute('name', name);
      }
      document.head.appendChild(el);
   }
   el.setAttribute('content', content);
}

function removeMeta(name: string) {
   const el = document.querySelector(
      `meta[name="${name}"], meta[property="${name}"]`
   );
   if (el) el.remove();
}

function setCanonical(href: string) {
   let el = document.querySelector(
      'link[rel="canonical"]'
   ) as HTMLLinkElement | null;
   if (!el) {
      el = document.createElement('link');
      el.setAttribute('rel', 'canonical');
      document.head.appendChild(el);
   }
   el.setAttribute('href', href);
}

function setJsonLd(json: object) {
   const id = 'seo-json-ld';
   let el = document.getElementById(id) as HTMLScriptElement | null;
   if (!el) {
      el = document.createElement('script');
      el.id = id;
      el.setAttribute('type', 'application/ld+json');
      document.head.appendChild(el);
   }
   el.textContent = JSON.stringify(json);
}

function clearSeo() {
   document.title = SITE_NAME;
   removeMeta('description');
   removeMeta('keywords');
   removeMeta('og:title');
   removeMeta('og:description');
   removeMeta('og:image');
   removeMeta('og:url');
   removeMeta('og:type');
   removeMeta('og:site_name');
   removeMeta('twitter:card');
   removeMeta('twitter:title');
   removeMeta('twitter:description');
   removeMeta('twitter:image');
   const ld = document.getElementById('seo-json-ld');
   if (ld) ld.remove();
}

function setArticleSeo(article: ArticleDetail) {
   const url = `${SITE_URL}/desc/${article.id}`;
   const title = `${article.title} - ${article.category?.name || '文章'} - ${SITE_NAME}`;
   const description =
      article.summary ||
      article.content?.replace(/<[^>]+>/g, '').slice(0, 200) ||
      '';
   const keywords = article.tags?.map(t => t.name).join(',') || '';
   const cover = article.cover || '';

   document.title = title;
   setMeta('description', description);
   if (keywords) setMeta('keywords', keywords);

   // Open Graph
   setMeta('og:title', article.title);
   setMeta('og:description', description);
   if (cover) setMeta('og:image', cover);
   setMeta('og:url', url);
   setMeta('og:type', 'article');
   setMeta('og:site_name', SITE_NAME);

   // Twitter Card
   setMeta('twitter:card', 'summary_large_image');
   setMeta('twitter:title', article.title);
   setMeta('twitter:description', description);
   if (cover) setMeta('twitter:image', cover);

   // Canonical
   setCanonical(url);

   // JSON-LD
   setJsonLd({
      '@context': 'https://schema.org',
      '@type': 'Article',
      headline: article.title,
      description: description,
      image: cover || undefined,
      datePublished: article.createdAt,
      dateModified: article.updatedAt || article.createdAt,
      author: { '@type': 'Person', name: '博主' },
      publisher: {
         '@type': 'Organization',
         name: SITE_NAME,
         logo: { '@type': 'ImageObject', url: `${SITE_URL}/logo.png` }
      },
      mainEntityOfPage: { '@type': 'WebPage', '@id': url }
   });
}

const route = useRoute();
const id = ref(route.params.id);
const article = ref<any>(null);
const markdownContent = ref('');
const loading = ref(true);

const toc = ref<{ id: string; text: string; level: number }[]>([]);
const activeId = ref('');
let scrollTimeout: number | null = null;

// 推荐文章
const recommendArticles = ref<ArticleItem[]>([]);
const recommendListRef = ref<HTMLElement | null>(null);

// 最新文章
const latestArticles = ref<ArticleItem[]>([]);

const loadLatestArticles = async () => {
   try {
      const response = await fetchArticles({
         current: 1,
         pageSize: 4,
         sortField: 'createTime',
         sortOrder: 'desc'
      });
      if (response.code === 0 && response.data) {
         latestArticles.value = response.data.records;
      }
   } catch (error) {
      console.error('获取最新文章失败:', error);
   }
};

const goToArticle = (articleId: number) => {
   window.location.href = `/desc/${articleId}`;
};

const formatDate = (dateStr: string) => {
   if (!dateStr) return '';
   const date = new Date(dateStr);
   const m = String(date.getMonth() + 1).padStart(2, '0');
   const d = String(date.getDate()).padStart(2, '0');
   return `${m}-${d}`;
};

// 上一篇/下一篇
const prevArticle = ref<ArticleItem | null>(null);
const nextArticle = ref<ArticleItem | null>(null);

const loadNavArticles = async () => {
   try {
      const response = await fetchArticles({
         current: 1,
         pageSize: 9999,
         sortField: 'id',
         sortOrder: 'asc'
      });
      if (response.code === 0 && response.data) {
         const list = response.data.records;
         const currentIndex = list.findIndex(
            item => item.id === Number(id.value)
         );
         if (currentIndex > 0) {
            prevArticle.value = list[currentIndex - 1];
         }
         if (currentIndex < list.length - 1 && currentIndex >= 0) {
            nextArticle.value = list[currentIndex + 1];
         }
      }
   } catch (error) {
      console.error('获取导航文章失败:', error);
   }
};

// 滚动函数
const scrollLeft = () => {
   if (recommendListRef.value) {
      recommendListRef.value.scrollBy({ left: -340, behavior: 'smooth' });
   }
};

const scrollRight = () => {
   if (recommendListRef.value) {
      recommendListRef.value.scrollBy({ left: 340, behavior: 'smooth' });
   }
};

// 推荐文章滚轮事件处理
const handleRecommendScroll = (event: WheelEvent) => {
   event.preventDefault();
   event.stopPropagation();

   if (recommendListRef.value) {
      recommendListRef.value.scrollBy({
         left: event.deltaY < 0 ? -50 : 50,
         behavior: 'smooth'
      });
   }
};

const messageBoardRef = ref<InstanceType<typeof MessageBoard> | null>(null);

// 获取推荐文章
const loadRecommendArticles = async () => {
   try {
      const response = await fetchArticles({
         current: 1,
         pageSize: 10,
         sortField: 'readCount',
         sortOrder: 'desc'
      });
      if (response.code === 0 && response.data) {
         recommendArticles.value = response.data.records;
      }
   } catch (error) {
      console.error('获取推荐文章失败:', error);
   }
};

watch(
   () => route.params.id,
   newId => {
      id.value = newId;
      if (newId) {
         loadArticle(Number(newId));
         loadNavArticles();
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
         setArticleSeo(result.data);
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
   // 加载推荐文章
   loadRecommendArticles();
   // 加载最新文章
   loadLatestArticles();
   // 加载上一篇/下一篇
   loadNavArticles();

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

onBeforeUnmount(() => {
   clearSeo();
});
</script>

<style lang="scss" scoped>
.left-sticky {
   position: sticky;
   top: 20px;
   max-height: calc(100vh - 40px);
   overflow-y: auto;
   overflow-x: hidden;
   display: flex;
   flex-direction: column;
   gap: 1em;

   &::-webkit-scrollbar-track {
      background: var(--cart-back-color);
   }

   &::-webkit-scrollbar {
      width: 5px;
   }
}

.ml-box {
   flex-shrink: 0;
   display: flex;
   flex-direction: column;
   max-height: 300px;
}

.toc-title {
   display: flex;
   justify-content: flex-start;
   align-items: center;
   margin-bottom: 0.5em;
   padding-bottom: 0.5em;
   border-bottom: 1px solid var(--cart-border-color);
   flex-shrink: 0;

   .el-icon {
      margin-right: 0.3em;
      font-size: 1.1em;
   }
}

.toc-scroll {
   flex: 1;
   overflow-y: auto;
   overflow-x: hidden;

   &::-webkit-scrollbar-track {
      background: var(--cart-back-color);
   }

   &::-webkit-scrollbar {
      width: 5px;
   }
}

::v-deep(.md-editor-preview pre) {
   margin: 0 !important;
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

            .icon {
               margin-right: 0.3em;
               font-size: 1.1em;
            }
         }

         .ml {
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
      max-width: 1000px;
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

.flex {
   display: flex;
   justify-content: space-between;
   align-items: center;

   .flex-tile {
      display: flex;
      justify-content: center;
      align-items: center;
   }
}

.cart-arg-title-left {
   display: flex;

   & > div {
      width: 10px;
      height: 10px;
      border-radius: 50%;
      margin-right: 0.8em;
   }

   & > div:nth-child(1) {
      background: red;
   }

   & > div:nth-child(2) {
      background: #f5cf51;
   }

   & > div:nth-child(3) {
      background: #5ed164;
   }
}

.cart-gx {
   .cart-gx-test {
      display: flex;
      flex-direction: column;
      margin: 1em 0;

      .cart-gx-test-kp {
         display: flex;
         margin-bottom: 0.5em;
         padding: 4px;
         border-radius: 8px;
         cursor: pointer;
         transition: all 0.3s ease;

         &:hover {
            background: rgba(129, 209, 239, 0.3);
         }

         &.active {
            background: rgba(6, 192, 180, 0.2);
         }

         & > div:first-child {
            img {
               width: 80px;
               height: 70px;
               border-radius: 8px;
               margin-right: 1em;
               object-fit: cover;
            }
         }

         & > div:last-child {
            display: flex;
            flex-direction: column;
            justify-content: center;
            font-size: 0.85em;
            gap: 4px;

            & > div:first-child {
               color: var(--bk-font-color);
               overflow: hidden;
               text-overflow: ellipsis;
               display: -webkit-box;
               -webkit-line-clamp: 1;
               -webkit-box-orient: vertical;
            }

            & > div:first-child:hover {
               color: #06c0b4;
            }

            & > div:last-child {
               color: var(--cart-home-time-color);
               font-size: 0.85em;
            }
         }
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

/* 上一篇/下一篇导航样式 */
.article-nav {
   display: flex;
   justify-content: space-between;
   gap: 1em;
   margin-top: 2em;

   .nav-item {
      flex: 1;
      display: flex;
      flex-direction: column;
      padding: 1em 1.2em;
      background: var(--cart-back-color);
      border: 1px solid var(--cart-border-color);
      border-radius: 8px;
      text-decoration: none;
      transition: all 0.3s ease;
      cursor: pointer;

      &:hover {
         border-color: var(--bk-draw-back-color);
         transform: translateY(-2px);
         box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
      }

      &.nav-prev {
         border-left: 3px solid var(--bk-draw-back-color);
         text-align: left;

         .nav-label {
            color: var(--bk-draw-back-color);
         }
      }

      &.nav-next {
         border-right: 3px solid var(--bk-draw-back-color);
         text-align: right;
         align-items: flex-end;

         .nav-label {
            color: var(--bk-draw-back-color);
         }
      }

      .nav-label {
         font-size: 0.85em;
         margin-bottom: 0.4em;
         opacity: 0.8;
      }

      .nav-title {
         font-size: 1em;
         color: var(--bk-font-color);
         font-weight: 500;
         overflow: hidden;
         text-overflow: ellipsis;
         white-space: nowrap;
         max-width: 100%;
      }
   }
}

/* 推荐文章样式 */
.recommend-articles {
   margin-top: 2em;
   padding-top: 1.5em;
   border-top: 1px solid var(--cart-border-color);

   .recommend-title {
      display: flex;
      align-items: center;
      font-size: 1.2em;
      font-weight: bold;
      color: var(--bk-font-color);
      margin-bottom: 1em;

      .icon {
         margin-right: 0.5em;
         color: var(--bk-draw-back-color);
      }
   }

   .recommend-scroll-container {
      overflow: hidden;
      position: relative;
      width: 100%;
      display: flex;
      align-items: center;
   }

   .jt-left {
      position: absolute;
      left: 0;
      color: var(--bk-font-color);
      z-index: 3;
      width: 35px;
      font-size: 1.2em;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      cursor: pointer;
      transition: color 0.3s ease;

      &:hover {
         color: var(--bk-draw-back-color);
      }

      .icons {
         margin: 0 2px;
      }
   }

   .jt-right {
      position: absolute;
      right: 0;
      color: var(--bk-font-color);
      z-index: 3;
      width: 35px;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      cursor: pointer;
      transition: color 0.3s ease;

      &:hover {
         color: var(--bk-draw-back-color);
      }

      .icons {
         margin: 0 2px;
      }
   }

   .recommend-list {
      display: flex;
      flex-direction: row;
      gap: 1em;
      overflow-x: auto;
      padding: 0 2em;
      width: 100%;
      scrollbar-width: none;

      &::-webkit-scrollbar {
         display: none;
      }
   }

   .recommend-item {
      display: flex;
      flex-direction: column;
      width: 160px;
      flex-shrink: 0;
      padding: 0.8em;
      background: var(--cart-back-color);
      border-radius: 8px;
      border: 1px solid var(--cart-border-color);
      transition: all 0.3s ease;
      text-decoration: none;

      &:hover {
         border-color: var(--bk-draw-back-color);
         transform: translateY(-3px);
      }

      .recommend-cover {
         width: 100%;
         height: 100px;
         border-radius: 6px;
         overflow: hidden;
         margin-bottom: 0.6em;

         img {
            width: 100%;
            height: 100%;
            object-fit: cover;
         }
      }

      .recommend-info {
         display: flex;
         flex-direction: column;

         .recommend-title-text {
            font-size: 0.85em;
            color: var(--bk-font-color);
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            margin-bottom: 0.3em;
            line-height: 1.4;
         }

         .recommend-summary {
            font-size: 0.75em;
            color: var(--bk-font-color);
            opacity: 0.7;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            margin-bottom: 0.3em;
            line-height: 1.4;
            display: flex;
            align-items: center;

            .icon {
               margin-right: 0.2em;
               font-size: 0.9em;
            }
         }

         .recommend-meta {
            font-size: 0.75em;
            color: var(--bk-font-color);
            opacity: 0.6;
            display: flex;
            align-items: center;
            justify-content: center;

            .icon {
               margin-right: 0.2em;
            }

            .dot {
               margin: 0 0.4em;
            }
         }
      }
   }
}
</style>
