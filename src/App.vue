<script setup lang="ts">

import index from "@/view/index.vue"
import Lloding from "@/components/lodding/lloding.vue";
import { Isindex } from "@/router/index";
import Draw from "@/components/draw/draw.vue";
import "@/util/windows";

import { handleScroll } from "@/util/scrse"




</script>

<template>
  <Draw />
  <div class="page">

    <div class="page-zz" @scroll="handleScroll">
      <index></index>

    </div>


  </div>
  <Lloding v-if="!Isindex"></Lloding>
</template>

<style lang="scss" scoped>
// 主页样式
.page {
  top: 0;
  left: 0;
  width: 100vw;
  overflow: hidden;
  height: 100vh;
  position: absolute;


  background-image:
    linear-gradient(to left, #0095C2, #5FD6C9),
    /* 渐变背景 */
    repeating-linear-gradient(
      /* 网格背景 */
      0deg,
      rgba(0, 0, 0, 0) 0px,
      rgba(0, 0, 0, 0) 10px,
      /* 增大网格线宽度 */
      rgba(0, 0, 0, 0.5) 10px,
      /* 增加网格线的颜色对比 */
      rgba(0, 0, 0, 0.5) 11px);
  // background-image: url("https://img.yanlutong.com/uploadimg/ico/2020/1222/1608630331988227.jpg");

  background-size:
    100% 100%,
    /* 渐变背景大小 */
    40px 40px;
  /* 网格背景大小（增大网格线间距） */


  background-size: cover;
  /* 背景图片覆盖整个元素 */
  background-position: center center;
  /* 背景图片居中对齐 */
  background-repeat: no-repeat;
  /* 防止背景图片重复 */
  background-attachment: fixed;

  /* 背景图片固定在视口，不随内容滚动 */
  .page-zz {
    background: var(--page);
    width: 100vw;
    overflow-y: auto;
    padding-top: 60px;
    height: 100vh;

  }
}
</style>
