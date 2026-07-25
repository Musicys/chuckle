<template>
   <div class="screen-content">
      <div class="section-title">作品集</div>
      <div class="portfolio-container">
         <div
            v-for="(project, index) in portfolio"
            :key="index"
            class="portfolio-item"
            :class="{ show: visibleItems[index] }">
            <div class="portfolio-image">
               <img :src="project.image" :alt="project.title" />
               <div class="portfolio-overlay">
                  <span class="portfolio-title">{{ project.title }}</span>
                  <span class="portfolio-desc">{{ project.desc }}</span>
               </div>
            </div>
            <div class="portfolio-info">
               <div class="portfolio-name">{{ project.title }}</div>
               <div class="portfolio-category">{{ project.category }}</div>
            </div>
         </div>
      </div>
      <div v-if="portfolio.length === 0" class="empty-tip">暂无作品数据</div>
   </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onBeforeUnmount } from 'vue';

const visibleItems = ref<boolean[]>([]);
const timerIds = ref<number[]>([]);

const portfolio = computed(() => {
   return [
      {
         title: '个人博客系统',
         desc: '基于SpringBoot + Vue3 的博客平台',
         category: 'Java',
         image: 'https://images.unsplash.com/photo-1587620962725-abab7fe55159'
      },
      {
         title: '在线商城',
         desc: '前后端分离的电商平台',
         category: 'Vue',
         image: 'https://images.unsplash.com/photo-1542831371-29b0f74f9713'
      },
      {
         title: '管理后台',
         desc: '权限管理系统',
         category: 'React',
         image: 'https://images.unsplash.com/photo-1551288049-bebda4e38f71'
      },
      {
         title: '数据可视化',
         desc: '大屏数据展示系统',
         category: 'ECharts',
         image: 'https://images.unsplash.com/photo-1551288049-bebda4e38f71'
      },
      {
         title: '移动端App',
         desc: '跨平台移动应用',
         category: 'Flutter',
         image: 'https://images.unsplash.com/photo-1512976790489-bd63fc0785d5'
      },
      {
         title: '微信小程序',
         desc: '小程序商城',
         category: 'Taro',
         image: 'https://images.unsplash.com/photo-1535224206242-87dfbf2f6a71'
      },
      {
         title: '音乐',
         desc: '基于大模型的智能问答系统',
         category: 'Python',
         image: 'https://images.unsplash.com/photo-1677442136506-72140f387635'
      },
      {
         title: '爬虫工具',
         desc: '数据采集与分析平台',
         category: 'Go',
         image: 'https://images.unsplash.com/photo-1633356122544-f134324a6cee'
      }
   ];
});

const preloadImages = () => {
   portfolio.value.forEach(item => {
      const img = new Image();
      img.src = item.image;
   });
};

const animateItems = (index: number) => {
   if (index >= portfolio.value.length) return;
   const timer = setTimeout(() => {
      visibleItems.value[index] = true;
      animateItems(index + 1);
   }, 120);
   timerIds.value.push(timer);
};

onMounted(() => {
   visibleItems.value = new Array(portfolio.value.length).fill(false);
   preloadImages();
   animateItems(0);
});

onBeforeUnmount(() => {
   timerIds.value.forEach(timer => clearTimeout(timer));
   timerIds.value = [];
});
</script>

<style scoped>
.screen-content {
   width: 100%;
   padding: 50px 40px 100px 40px;
   text-align: center;
   display: flex;
   align-items: center;
   justify-content: center;
   flex-direction: column;
   height: 100%;
   box-sizing: border-box;
}

.section-title {
   font-size: 2.5rem;
   font-weight: 700;
   margin-bottom: 40px;
}

.portfolio-container {
   display: grid;
   grid-template-columns: repeat(4, 1fr);
   gap: 12px;
   width: 90vw;
}

.portfolio-item {
   background: var(--cart-back-color);
   border: 1px solid var(--cart-border-color);
   border-radius: 16px;
   overflow: hidden;
   opacity: 0;
   transform: translateY(20px) translateZ(0);
   transition:
      opacity 0.3s ease-out,
      transform 0.3s ease-out;
   will-change: opacity, transform;
}

.portfolio-item.show {
   opacity: 1;
   transform: translateY(0) translateZ(0);
}

.portfolio-item:hover {
   transform: translateY(-6px) translateZ(0);
   box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
}

.portfolio-image {
   position: relative;
   width: 100%;
   aspect-ratio: 16/10;
   overflow: hidden;
   background: var(--back-op-color);
}

.portfolio-image img {
   width: 100%;
   height: 100%;
   object-fit: cover;
   transition: transform 0.4s ease-out;
}

.portfolio-item:hover .portfolio-image img {
   transform: scale(1.05);
}

.portfolio-overlay {
   position: absolute;
   inset: 0;
   background: linear-gradient(to top, rgba(0, 0, 0, 0.6) 0%, transparent 100%);
   display: flex;
   flex-direction: column;
   align-items: center;
   justify-content: flex-end;
   padding: 15px;
   opacity: 0;
   transition: opacity 0.3s ease;
}

.portfolio-item:hover .portfolio-overlay {
   opacity: 1;
}

.portfolio-overlay .portfolio-title {
   color: #fff;
   font-size: 1.1rem;
   font-weight: 600;
   margin-bottom: 4px;
}

.portfolio-overlay .portfolio-desc {
   color: rgba(255, 255, 255, 0.8);
   font-size: 0.85rem;
}

.portfolio-info {
   padding: 15px;
   text-align: left;
}

.portfolio-name {
   font-size: 1rem;
   font-weight: 600;
   margin-bottom: 4px;
}

.portfolio-category {
   font-size: 0.85rem;
   opacity: 0.6;
   display: inline-block;
   padding: 2px 10px;
   background: var(--back-op-color);
   border-radius: 10px;
}

.empty-tip {
   font-size: 1.2rem;
   opacity: 0.6;
   margin-top: 40px;
}

@media (max-width: 768px) {
   .screen-content {
      padding: 20px;
   }
   .section-title {
      font-size: 2rem;
   }
}
</style>
