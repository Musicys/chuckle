<template>
   <div class="loding fade-in">
      <img class="img" src="/src/static/t.jpg" alt="" />
      <div style="width: 100px"></div>
      <span>加载中.....</span>
   </div>
</template>

<script setup lang="ts"></script>

<style scoped>
.loding {
   width: 100vw;
   height: 100vh;
   position: fixed;
   top: 0;
   left: 0;
   display: flex;
   justify-content: center;
   align-items: center;
   background: #1a191b;
   flex-direction: column;
   font-family: 'MyCustomFonts', sans-serif;

   .img {
      width: 100px;
      height: 100px;
      border-radius: 50%;
      border: 0.1px solid var(--color-title);
   }

   span {
      color: #fff;
      margin-top: 1.2em;
   }
}
</style>
