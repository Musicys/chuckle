<template>
   <div class="mackpage">
      <img
         v-lazy="
            article?.cover ||
            'https://pic1.zhimg.com/v2-4fd3f2bb57d4bcc195e3d3cf092a7dd7_r.jpg'
         "
         alt="" />

      <div class="mackpage-zz">
         <div>
            <div>
               <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-yuedu"></use>
               </svg>
               {{ article?.category?.name || '学习笔记' }}
            </div>
            <div style="margin-left: 1em">
               #{{ article?.tags?.[0]?.name || '备忘录' }}
            </div>
         </div>
         <div>
            {{ article?.title || '编码备忘录' }}

            <svg class="icon" aria-hidden="true">
               <use xlink:href="#icon-lianjie2"></use>
            </svg>
         </div>
         <div class="mackpage-zz-flex">
            <div class="mackpage-zz-flex-top">
               <div>
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-shalou"></use>
                  </svg>
                  发表于: {{ article?.createdAt || '2023-10-18' }}
               </div>

               <div>
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-bi"></use>
                  </svg>
                  更新于 {{ article?.updatedAt || '2023-10-18' }}
               </div>
            </div>

            <div class="mackpage-zz-flex-but">
               <div>
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-shujutongji"></use>
                  </svg>
                  字数总计:{{ formatNumber(article?.wordCount || 0) }}
               </div>
               <div>
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-shujutongji"></use>
                  </svg>
                  阅读时长:{{ Math.ceil((article?.wordCount || 0) / 300) }}分钟
               </div>

               <div>
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-icons_yueduliang"></use>
                  </svg>
                  阅读量:{{ article?.readCount || 0 }}
               </div>
               <div>
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-pinglun"></use>
                  </svg>
                  评论数:{{ article?.commentCount || 0 }}
               </div>
            </div>
         </div>
      </div>
   </div>
</template>

<script setup lang="ts">
defineProps<{
   article?: any;
}>();

function formatNumber(num: number): string {
   if (num >= 10000) {
      return (num / 10000).toFixed(1) + 'w';
   } else if (num >= 1000) {
      return (num / 1000).toFixed(1) + 'k';
   }
   return num.toString();
}
</script>

<style lang="scss" scoped>
.mackpage {
   padding: 0.5em;
   position: relative;

   & > img {
      width: 100%;
      border-radius: 15px;
      z-index: 2;
      height: 350px;
   }

   .mackpage-zz {
      position: absolute;

      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      z-index: 3;
      background: rgba(0, 0, 0, 0.5);
      border-radius: 15px;
      color: white;
      display: flex;
      justify-content: start;
      flex-direction: column;
      align-items: start;
      font-size: 1.3em;

      & > div {
         margin-left: 1.5em;
         display: flex;
         margin-top: 1.5em;

         & > div {
            display: flex;
            justify-content: center;
            align-items: center;
         }
      }

      .mackpage-zz-flex {
         flex-wrap: wrap;

         .mackpage-zz-flex-but {
            display: flex;
            justify-content: center;
            align-items: center;
            margin-top: 0.5em;
            flex-wrap: wrap;

            & > div {
               display: flex;
               justify-content: center;
               align-items: center;
               margin-right: 1.5em;
               margin-top: 0.5em;
            }
         }

         .mackpage-zz-flex-top {
            margin-top: 1em;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-wrap: wrap;

            & > div {
               margin-top: 0.5em;
               display: flex;
               justify-content: center;
               align-items: center;
               margin-right: 1em;
            }
         }
      }
   }
}

.icon {
   font-size: 1em;
   margin-right: 0.3em;
}
</style>
