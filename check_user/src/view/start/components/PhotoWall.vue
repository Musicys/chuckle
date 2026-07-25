<template>
   <div class="screen2-content">
      <div class="screen2-left">
         <div class="section-title">照片墙</div>
         <div class="screen2-desc">记录生活中的美好瞬间</div>
      </div>
      <div class="screen2-right">
         <div class="gallery">
            <div
               v-for="(photo, index) in photoWall"
               :key="index"
               ref="cardRefs"
               class="photo-card"
               :class="{ visible: visiblePhotos[index] }"
               :style="{
                  '--tx': photo.tx + 'vw',
                  '--ty': photo.ty + 'vh',
                  '--rot': photo.rot + 'deg',
                  zIndex: index + 1 // 初始静态层级，基于 index
               }"
               @mouseenter="handleMouseEnter(index)"
               @mouseleave="handleMouseLeave(index)"
               @click="openModal(photo)">
               <img
                  :src="
                     loadedImages[index] ||
                     'https://img.zcool.cn/community/0115bb5715c7356ac725134349d24b.gif'
                  "
                  :alt="photo.title"
                  @load="onImageLoad(index)" />
            </div>
         </div>
      </div>

      <div
         class="modal-overlay"
         :class="{ active: showModal }"
         @click="closeModal">
         <div class="modal-content" @click.stop>
            <div class="modal-photo">
               <img :src="selectedPhoto?.url" :alt="selectedPhoto?.title" />
            </div>
            <div class="modal-info">
               <h3>{{ selectedPhoto?.title }}</h3>
               <p>{{ selectedPhoto?.msg }}</p>
               <button class="close-btn" @click="closeModal">关闭</button>
            </div>
         </div>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onBeforeUnmount } from 'vue';

// DOM 引用
const cardRefs = ref<HTMLElement[]>([]);

// 状态管理
const loadedImages = ref<string[]>([]);
const visiblePhotos = ref<boolean[]>([]);
const showModal = ref(false);
const selectedPhoto = ref<(typeof photoWall.value)[0] | null>(null);

// 核心层级控制：一个不断递增的计数器
const maxZIndex = ref(0);

// 定时器清理池，防止组件销毁时内存泄漏
const timerIds = ref<number[]>([]);

// 生成随机坐标和角度
const generateRandomOffset = () => {
   return {
      tx: (Math.random() - 0.5) * 60,
      ty: (Math.random() - 0.5) * 50,
      rot: (Math.random() - 0.5) * 40
   };
};

// 照片数据源
const photoWall = computed(() => {
   const defaultPhotos = [
      {
         url: 'https://images.unsplash.com/photo-1490750967868-88aa4486c946',
         title: '风景',
         msg: '这是一张关于旅行的美好回忆...'
      },
      {
         url: 'https://images.unsplash.com/photo-1506905925346-21bda4d32df4',
         title: '山川',
         msg: '那天下午的阳光特别好。'
      },
      {
         url: 'https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05',
         title: '星空',
         msg: '和好朋友们的聚会合影！'
      },
      {
         url: 'https://images.unsplash.com/photo-1441974231531-c6227db76b6e',
         title: '森林',
         msg: '海边的风很大，但是很开心。'
      },
      {
         url: 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e',
         title: '海洋',
         msg: '这只猫咪太可爱了忍不住拍下来。'
      },
      {
         url: 'https://images.unsplash.com/photo-1447752875215-b2761acb3c5d',
         title: '云朵',
         msg: '毕业那天的留念，青春万岁。'
      },
      {
         url: 'https://images.unsplash.com/photo-1501785888041-af3ef285b470',
         title: '日出',
         msg: '第一次去游乐园，吓死宝宝了。'
      },
      {
         url: 'https://images.unsplash.com/photo-1469474968028-56623f02e42e',
         title: '自然',
         msg: '简单的早餐，开启美好的一天。'
      },
      {
         url: 'https://images.unsplash.com/photo-1500534314209-a25ddb2bd429',
         title: '城市',
         msg: '繁华都市的夜景。'
      },
      {
         url: 'https://images.unsplash.com/photo-1472214103451-9374bd1c798e',
         title: '花海',
         msg: '春天的花海美不胜收。'
      },
      {
         url: 'https://images.unsplash.com/photo-1504608524841-42fe6f032b4b',
         title: '雪山',
         msg: '壮丽的雪山风景。'
      },
      {
         url: 'https://images.unsplash.com/photo-1502082553048-f009c37129b9',
         title: '沙漠',
         msg: '广袤无垠的沙漠。'
      }
   ];

   return defaultPhotos.map(photo => ({
      ...photo,
      ...generateRandomOffset()
   }));
});

// 交互：鼠标移入，层级瞬间提升 + 放大归正
const handleMouseEnter = (index: number) => {
   const el = cardRefs.value[index];
   if (!el) return;

   // 1. 提升层级 (使用计数器保证它永远高于当前所有层级)
   maxZIndex.value++;
   el.style.zIndex = maxZIndex.value.toString();

   // 2. 视觉放大并回正 (去除旋转)
   el.style.transform = `
    translate(calc(-50% + var(--tx)), calc(-50% + var(--ty))) 
    rotate(0deg) scale(1.3)
  `;
   el.style.boxShadow = '0 20px 40px rgba(0, 0, 0, 0.4)';
};

// 交互：鼠标移出，恢复原状
const handleMouseLeave = (index: number) => {
   const el = cardRefs.value[index];
   if (!el) return;

   // 1. 恢复初始层级 (基于原来的 index + 1)
   el.style.zIndex = (index + 1).toString();

   // 2. 恢复原始旋转和大小
   el.style.transform = `
    translate(calc(-50% + var(--tx)), calc(-50% + var(--ty))) 
    rotate(var(--rot)) scale(1)
  `;
   el.style.boxShadow = '0 4px 6px rgba(0, 0, 0, 0.1)';
};

const onImageLoad = (index: number) => {
   const nextIndex = index + 1;
   if (nextIndex < photoWall.value.length && !loadedImages.value[nextIndex]) {
      const timer = setTimeout(() => {
         loadedImages.value[nextIndex] = photoWall.value[nextIndex].url;
      }, 100);
      timerIds.value.push(timer);
   }
};

const openModal = (photo: (typeof photoWall.value)[0]) => {
   selectedPhoto.value = photo;
   showModal.value = true;
};

const closeModal = () => {
   showModal.value = false;
   selectedPhoto.value = null;
};

const loadPhotosOneByOne = (index: number) => {
   if (index >= photoWall.value.length) return;
   const timer = setTimeout(() => {
      visiblePhotos.value[index] = true;
      loadedImages.value[index] = photoWall.value[index].url;
      loadPhotosOneByOne(index + 1);
   }, 200);
   timerIds.value.push(timer);
};

onMounted(() => {
   // 初始化最顶层级的计数器基础值
   maxZIndex.value = photoWall.value.length + 10;

   visiblePhotos.value = new Array(photoWall.value.length).fill(false);
   loadPhotosOneByOne(0);
});

// 销毁组件时清理所有定时器，防止内存泄漏
onBeforeUnmount(() => {
   timerIds.value.forEach(timer => clearTimeout(timer));
   timerIds.value = [];
});
</script>

<style scoped>
.screen2-content {
   width: 90%;
   gap: 30px;
   display: flex;
   margin: auto;
   height: 80%;
}

.screen2-left {
   width: 25%;
   display: flex;
   flex-direction: column;
   align-items: flex-start;
   justify-content: center;
}

.screen2-right {
   flex: 1;
   display: flex;
   align-items: center;
   justify-content: center;
   position: relative;
}

.screen2-desc {
   font-size: 1.1rem;
   opacity: 0.7;
   margin-top: 10px;
}

.section-title {
   font-size: 2.5rem;
   font-weight: 700;
   margin-bottom: 40px;
}

.gallery {
   position: relative;
   width: 100%;
   height: 100%;
}

.photo-card {
   position: absolute;
   top: 50%;
   left: 50%;
   width: 200px;
   height: 100px;
   transform: translate(calc(-50% + var(--tx)), calc(-50% + var(--ty)))
      rotate(var(--rot)) scale(0);
   opacity: 0;
   background-color: #fff;
   padding: 6px;
   box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
   border-radius: 8px;
   transition: all 0.4s cubic-bezier(0.25, 0.8, 0.25, 1);
   cursor: pointer;
}

.photo-card.visible {
   transform: translate(calc(-50% + var(--tx)), calc(-50% + var(--ty)))
      rotate(var(--rot)) scale(1);
   opacity: 1;
}

.photo-card img {
   width: 100%;
   height: 100%;
   object-fit: cover;
   display: block;
   border: 1px solid #eee;
}

.modal-overlay {
   position: fixed;
   top: 0;
   left: 0;
   width: 100%;
   height: 100%;
   background: rgba(0, 0, 0, 0.7);
   display: flex;
   justify-content: center;
   align-items: center;
   opacity: 0;
   pointer-events: none;
   transition: opacity 0.3s;
   z-index: 2000;
}

.modal-overlay.active {
   opacity: 1;
   pointer-events: auto;
}

.modal-content {
   background: white;
   border-radius: 16px;
   width: 80vw;
   max-width: 800px;
   height: 40vw;
   max-height: 400px;
   display: flex;
   flex-direction: row;
   overflow: hidden;
   transform: translateY(20px);
   transition: transform 0.3s;
   box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
}

.modal-overlay.active .modal-content {
   transform: translateY(0);
}

.modal-photo {
   flex: 2;
   width: 100%;
   height: 100%;
   overflow: hidden;
}

.modal-photo img {
   width: 100%;
   height: 100%;
   object-fit: cover;
}

.modal-info {
   flex: 1;
   display: flex;
   flex-direction: column;
   padding: 24px;
   justify-content: center;
   background: var(--cart-back-color);
}

.modal-info h3 {
   margin-bottom: 12px;
   font-size: 1.5rem;
   color: var(--bk-font-color);
}

.modal-info p {
   color: var(--bk-font-color);
   opacity: 0.7;
   line-height: 1.8;
   flex: 1;
}

.close-btn {
   padding: 10px 30px;
   background: var(--el-but-back);
   color: white;
   border: none;
   border-radius: 20px;
   cursor: pointer;
   font-size: 1rem;
   transition: all 0.3s;
   align-self: flex-start;
}

.close-btn:hover {
   opacity: 0.8;
   transform: translateY(-2px);
}

/* 移动端适配优化 */
@media (max-width: 900px) {
   .screen2-content {
      flex-direction: column;
      padding: 20px;
      height: 100%;
      box-sizing: border-box;
   }
   .screen2-left {
      width: 100%;
      align-items: center;
      margin-bottom: 10px;
   }
   .section-title {
      margin-bottom: 10px;
   }
   .photo-card {
      width: 150px;
      height: 75px;
      padding: 4px;
   }
   .modal-content {
      flex-direction: column;
      width: 90vw;
      height: auto;
      max-height: 80vh;
   }
   .modal-photo {
      flex: none;
      height: 200px;
   }
}
</style>
