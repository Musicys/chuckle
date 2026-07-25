<script setup lang="ts">
import StickyNoteBoard from './StickyNoteBoard.vue';
</script>
<template>
   <div class="page">
      <div class="pege-top">留言板</div>
      <div class="page-top2">想说的·想问的·吐槽·交流</div>

      <hr />

      <div class="tilte">
         <span>便签留言 </span
         ><el-icon>
            <List />
         </el-icon>
      </div>
      <StickyNoteBoard />
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
   margin-top: 50px;

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
}
</style>
