<template>
   <div class="arg" :class="{ 'lazy-visible': isVisible }" ref="containerRef">
      <div
         :style="{ color: getTagColor(i.tiltle) }"
         v-for="i in home_data.home_arg"
         :key="i.id"
         @click="handleTagClick(i.tiltle)">
         {{ i.tiltle }}
         <div class="arg-cart-nuber">{{ i.number }}</div>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { storeToRefs } from 'pinia';
import { useHomeStore } from '@/store/home';
import { useLazyVisible } from './useLazyVisible';
import { getTagList, type Tag } from '@/api/arg';

const router = useRouter();
const { isVisible, containerRef } = useLazyVisible();
const { home_data } = storeToRefs(useHomeStore());

const tags = ref<Tag[]>([]);
const tagMap = ref<Map<string, Tag>>(new Map());

const loadTags = async () => {
   try {
      const response = await getTagList();
      if (response.code === 0 && response.data) {
         tags.value = response.data;
         const map = new Map<string, Tag>();
         response.data.forEach(tag => {
            map.set(tag.name, tag);
         });
         tagMap.value = map;
      }
   } catch (error) {
      console.error('加载标签列表失败:', error);
   }
};

function getTagColor(tagName: string): string {
   const tag = tagMap.value.get(tagName);
   if (tag && tag.color) {
      return tag.color;
   }
   return getRandomColor();
}

function getRandomColor(): string {
   const r = Math.floor(Math.random() * 256);
   const g = Math.floor(Math.random() * 256);
   const b = Math.floor(Math.random() * 256);
   return `rgb(${r}, ${g}, ${b})`;
}

const handleTagClick = (tagName: string) => {
   const tag = tagMap.value.get(tagName);
   if (tag) {
      router.push({
         path: '/datail',
         query: { tagId: tag.id, tagName: tag.name }
      });
   }
};

onMounted(() => {
   loadTags();
});
</script>

<style lang="scss" scoped>
.arg {
   font-family: MyserverFonts, sans-serif;
   display: flex;
   flex-wrap: wrap;
   padding: 1em;
   opacity: 0;
   transform: translateY(30px);
   transition:
      opacity 0.5s ease,
      transform 0.5s ease;

   &.lazy-visible {
      opacity: 1;
      transform: translateY(0);
   }

   & > div {
      cursor: pointer;
      margin-right: 0.5em;
      line-height: 1.2em;
      margin-bottom: 0.2em;
      font-size: 1.5em;
      position: relative;
      padding-right: 0.4em;
      padding-top: 0.4em;

      .arg-cart-nuber {
         position: absolute;
         top: -0.4em;
         right: 0;
         font-size: 0.5em;
      }
   }

   & > div:hover {
      background: var(--bk-font-color);
   }
}
</style>
