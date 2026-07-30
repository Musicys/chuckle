<template>
   <div ref="index" class="index">
      <div ref="main" class="main"></div>
   </div>
</template>

<script setup>
import * as echarts from 'echarts';
import { ref, onMounted, watch, onUnmounted } from 'vue';

const props = defineProps(['data', 'onChartClick']);

const index = ref();
const main = ref();
let myChart = null;

const startCharts = () => {
   index.value.style.maxWidthdth = index.value.offsetWidth + 'px';
   index.value.style.maxHeight = index.value.offsetHeight + 'px';
   main.value.style.width = `${index.value.offsetWidth}px`;
   main.value.style.height = `${index.value.offsetHeight}px`;
   myChart = echarts.init(main.value);
   myChart.setOption(props.data);

   if (props.onChartClick) {
      myChart.on('click', params => {
         props.onChartClick(params);
      });
   }

   window.addEventListener('resize', handleResize);
};

const handleResize = () => {
   if (!myChart) return;
   index.value.style.maxWidthdth = index.value.offsetWidth + 'px';
   index.value.style.maxHeight = index.value.offsetHeight + 'px';
   myChart.resize({
      width: index.value.offsetWidth,
      height: index.value.offsetHeight
   });
};

watch(
   () => props.data,
   newData => {
      if (myChart && newData) {
         myChart.setOption(newData, true);
      }
   }
);

watch(
   () => props.onChartClick,
   (newHandler, oldHandler) => {
      if (!myChart) return;
      if (oldHandler) {
         myChart.off('click', oldHandler);
      }
      if (newHandler) {
         myChart.on('click', newHandler);
      }
   }
);

onMounted(() => {
   startCharts();
});

onUnmounted(() => {
   window.removeEventListener('resize', handleResize);
   if (myChart) {
      myChart.dispose();
      myChart = null;
   }
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
