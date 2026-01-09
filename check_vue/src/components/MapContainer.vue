<script setup>
import { onMounted, onUnmounted } from 'vue';
import AMapLoader from '@amap/amap-jsapi-loader';

let map = null;

onMounted(() => {
   setTimeout(() => {
      let arr = [113.79094, 27.88354];
      AMapLoader.load({
         key: 'd539ba806d011cb0f76fd7810cd6c86b', // 申请好的Web端开发者Key，首次调用 load 时必填
         version: '2.0', // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
         plugins: ['AMap.Scale'] // 需要使用的的插件列表，如比例尺'AMap.Scale'等
      })
         .then(AMap => {
            map = new AMap.Map('container', {
               // 设置地图容器id
               viewMode: '3D', // 是否为3D地图模式
               zoom: 11, // 初始化地图级别
               center: arr // 初始化地图中心点位置
            });
         })
         .catch(e => {
            console.log('错误s', e);
         });
   }, 800);
});

onUnmounted(() => {
   map?.destroy();
});
</script>

<template>
   <div id="container"></div>
</template>

<style scoped>
#container {
   width: 100%;
   height: 100%;
}
</style>
