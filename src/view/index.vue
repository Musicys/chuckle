<script setup lang="ts">
import Bgtop from "@/components/Bgtop/Bgtop.vue";

import { Isindex } from "@/router";
//监视scroll组件
import Monitor from "@/components/monitor/monitor.vue";


// 定义类型

</script>

<template>
  <!-- 头 -->

  <Bgtop></Bgtop>
  <!-- 一级路由+路由切换动画 -->


  <Monitor></Monitor>
<div class="box">
 
  <transition name="slide-fade">
  <router-view v-if="Isindex" />
</transition>

</div>
  <Monitor/>

  <!-- 公共组件 -->

</template>

<style lang="scss" scoped>


//进入动画的生效状态         //离开动画的生效状态
.slide-fade-enter-active, .slide-fade-leave-active {
  transition: transform .5s ease, opacity 1s ease;
}                           
                  //离开动画的结束状态。
.slide-fade-enter-from, .slide-fade-leave-to {
  transform: translateY(10%);
  opacity: 0;
}
.box{
  padding-top: 10px;
  width: 80%;
  margin: auto;
  max-width: 1300px;
  font-family: 'MyCustomFonts', sans-serif;
  padding-bottom: 50px;



}
</style>
