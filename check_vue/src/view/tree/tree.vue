<script setup lang="ts">
import CommentBox from '../desc/CommentBox.vue';

import Cart from './cart.vue';
</script>
<template>
   <div class="page">
      <div class="pege-top">留言板</div>
      <div class="page-top2">想说的·想问的·吐槽·交流</div>

      <hr />
      <div class="tilte">
         <span>评论 </span
         ><el-icon>
            <Comment />
         </el-icon>
      </div>
      <div>
         <CommentBox></CommentBox>
      </div>
      <div class="tilte">
         <span>3评论 </span>
      </div>

      <!-- /***/
     
    
    
    -->
      <div class="pr-list">
         <span style="border: none" class="pr_list_img">
            <img
               v-lazy="
                  'https://tse2-mm.cn.bing.net/th/id/OIP-C.NcBmhZg6-PBSrbbo2SVssgAAAA?rs=1&pid=ImgDetMain'
               "
               alt="" />
         </span>
         <div>
            <div class="pr-list-top">
               <div>
                  <span style="font-size: 1.2em; color: #49b1f5">music</span>
                  <span>2024-12-4</span>
               </div>
               <div>
                  <el-icon>
                     <Promotion />
                  </el-icon>
               </div>
            </div>
            <div>{{ '评论内容' }}</div>

            <Cart></Cart>
            <CommentBox></CommentBox>
         </div>
      </div>
   </div>
</template>

<style lang="scss" scoped>
.tilte {
   margin: 1em auto;
   display: flex;
   justify-content: start;
   align-items: center;
   font-size: 1.2em;

   & > span {
      margin: auto 0.5em;
   }
}

.bordr {
   color: var(--bk-font-color);
   border-radius: 10px;
   background: var(--cart-back-color);
   border: 3px solid var(--cart-border-color);
   padding: 5px;
}

.page {
   @extend .bordr;
   padding-bottom: 3em;

   .pege-top {
      width: 100%;
      text-align: center;
      font-size: 2em;
      margin: 0.5em auto;
   }

   .page-top2 {
      width: 100%;
      text-align: center;
      font-size: 1.2em;
      margin: 0.5em auto;
   }

   .pr-list {
      display: flex;

      .pr_list_img {
         display: inline-block;
         width: 50px;
         height: 100%;
         border: none;
         display: flex;
         justify-content: center;
         align-items: center;
         margin-right: 1em;

         & > img {
            width: 100%;
            border-radius: 50%;
            height: 50px;
            margin: auto 0.5em;
         }
      }

      & > div {
         width: 100%;
         display: flex;
         flex-direction: column;

         .pr-list-top {
            display: flex;
            justify-content: space-between;
            align-items: center;

            width: 100%;
            margin-bottom: 1em;
         }

         border-radius: 5px;
         padding: 1em 0.5em;
         background: none;
         box-shadow: 0.1px 0.1px 1px var(--bk-font-color);
      }

      margin-bottom: 1.2em;
   }
}
</style>
