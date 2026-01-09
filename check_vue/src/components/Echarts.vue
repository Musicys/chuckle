<template>
   <div ref="index" class="index">
      <div ref="main" class="main"></div>
   </div>
</template>

<script setup>
import * as echarts from 'echarts';
import { ref, onMounted } from 'vue';
const index = ref();
const main = ref();

let { data } = defineProps(['data']);

const startCharts = () => {
   // 自适应
   index.value.style.maxWidthdth = index.value.offsetWidth + 'px';
   index.value.style.maxHeight = index.value.offsetHeight + 'px';
   main.value.style.width = `${index.value.offsetWidth}px`;
   main.value.style.height = `${index.value.offsetHeight}px`;
   let myChart = echarts.init(main.value);
   // 使用刚指定的配置项和数据显示图表。

   myChart.setOption(data);

   window.addEventListener('resize', () => {
      index.value.style.maxWidthdth = index.value.offsetWidth + 'px';
      index.value.style.maxHeight = index.value.offsetHeight + 'px';
      myChart.resize({
         width: index.value.offsetWidth,
         height: index.value.offsetHeight
      });
   });
};

onMounted(() => {
   startCharts();
});
</script>

<style lang="scss" scoped>
.index {
   width: 100%;
   height: 100%;

   position: relative;
   .main {
      position: absolute;
      left: 0;
      top: 0;
   }
}
</style>
