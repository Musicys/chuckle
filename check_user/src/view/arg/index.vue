<template>
   <div class="page">
      <h1>标签</h1>
      <div class="arg">
         <div
            v-for="tag in tags"
            :key="tag.id"
            class="tag-item"
            :style="{ '--tag-color': tag.color }"
            @click="handleTagClick(tag)">
            <span class="tag-name">{{ tag.name }}</span>
            <span class="tag-count">{{ tag.articleCount }}</span>
         </div>
      </div>
      <div style="width: 100%; height: 400px">
         <Echarts
            v-if="IsCharts"
            :data="option"
            :onChartClick="handleChartClick"></Echarts>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { getTagList, type Tag } from '@/api/arg';
import Echarts from '@/components/Echarts.vue';

const router = useRouter();
const tags = ref<Tag[]>([]);
const IsCharts = ref(false);

const sortedTags = computed(() => {
   return [...tags.value].sort((a, b) => b.articleCount - a.articleCount);
});

const option = computed(() => {
   return {
      tooltip: {
         trigger: 'axis',
         axisPointer: { type: 'shadow' }
      },
      grid: {
         left: '3%',
         right: '4%',
         bottom: '3%',
         containLabel: true
      },
      xAxis: {
         type: 'category',
         data: sortedTags.value.map(t => t.name),
         axisLabel: {
            interval: 0,
            rotate: sortedTags.value.length > 10 ? 45 : 0,
            color: 'var(--bk-font-color)'
         },
         axisLine: { lineStyle: { color: 'var(--bk-font-color)' } }
      },
      yAxis: {
         type: 'value',
         name: '文章数',
         nameTextStyle: { color: 'var(--bk-font-color)' },
         axisLabel: { color: 'var(--bk-font-color)' },
         axisLine: { lineStyle: { color: 'var(--bk-font-color)' } },
         splitLine: { lineStyle: { type: 'dashed' } }
      },
      series: [
         {
            data: sortedTags.value.map(t => ({
               value: t.articleCount,
               itemStyle: {
                  color: t.color || '#06c0b4',
                  borderRadius: [4, 4, 0, 0]
               }
            })),
            type: 'bar',
            barMaxWidth: 60,
            emphasis: {
               itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.3)'
               }
            },
            label: {
               show: true,
               position: 'top',
               color: 'var(--bk-font-color)',
               fontWeight: 'bold'
            }
         }
      ]
   };
});

const loadTags = async () => {
   try {
      const response = await getTagList();
      if (response.code === 0 && response.data) {
         tags.value = response.data;
      }
   } catch (error) {
      console.error('加载标签列表失败:', error);
   }
};

const handleTagClick = (tag: Tag) => {
   router.push({
      path: '/datail',
      query: { tagId: tag.id, tagName: tag.name }
   });
};

const handleChartClick = (params: any) => {
   const index = params.dataIndex;
   const tag = sortedTags.value[index];
   if (tag) {
      router.push({
         path: '/datail',
         query: { tagId: tag.id, tagName: tag.name }
      });
   }
};

onMounted(() => {
   loadTags();
   setTimeout(() => {
      IsCharts.value = true;
   }, 1000);
});
</script>

<style lang="scss" scoped>
.bordr {
   color: var(--bk-font-color);
   border-radius: 10px;
   background: var(--cart-back-color);
   border: 3px solid var(--cart-border-color);
   padding: 5px;
}

.page {
   margin-top: 50px;
   margin-bottom: 2em;

   & > h1 {
      text-align: center;
      margin: 0.5em auto;
   }

   @extend .bordr;

   .arg {
      width: 100%;
      display: flex;
      flex-wrap: wrap;
      gap: 12px;
      padding: 16px;

      .tag-item {
         display: inline-flex;
         align-items: center;
         gap: 8px;
         padding: 8px 16px;
         background: var(--cart-back-color);
         border: 2px solid var(--tag-color);
         border-radius: 20px;
         cursor: pointer;
         transition: all 0.3s ease;
         color: var(--tag-color);
         font-size: 16px;

         .tag-name {
            font-weight: 500;
         }

         .tag-count {
            font-size: 13px;
            opacity: 0.8;
         }

         &:hover {
            background: var(--tag-color);
            color: white;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);

            .tag-count {
               opacity: 1;
            }
         }
      }
   }
}
</style>
