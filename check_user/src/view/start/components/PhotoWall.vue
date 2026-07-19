<template>
   <div class="screen2-content">
      <div class="screen2-left">
         <div class="section-title">照片墙</div>
         <div class="screen2-desc">记录生活中的美好瞬间</div>
      </div>
      <div class="screen2-right">
         <div class="photos-card">
            <div class="photos-grid">
               <div
                  v-for="(photo, index) in photoWall"
                  :key="index"
                  class="photo-item">
                  <div class="photo-wrapper">
                     <img
                        :src="
                           loadedImages[index] ||
                           'https://img.zcool.cn/community/0115bb5715c7356ac725134349d24b.gif'
                        "
                        :alt="photo.title"
                        @load="onImageLoad(index)" />
                     <div class="photo-overlay">
                        <span class="photo-icon">🔍</span>
                        <span class="photo-title">{{ photo.title }}</span>
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';

const loadedImages = ref<string[]>([]);

const photoWall = computed(() => {
   const defaultPhotos = [
      {
         url: 'https://images.unsplash.com/photo-1490750967868-88aa4486c946',
         title: '风景',
         ratio: '4/3'
      },
      {
         url: 'https://images.unsplash.com/photo-1506905925346-21bda4d32df4',
         title: '山川',
         ratio: '1/1'
      },
      {
         url: 'https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05',
         title: '星空',
         ratio: '16/9'
      },
      {
         url: 'https://images.unsplash.com/photo-1441974231531-c6227db76b6e',
         title: '森林',
         ratio: '3/4'
      },
      {
         url: 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e',
         title: '海洋',
         ratio: '1/1'
      },
      {
         url: 'https://images.unsplash.com/photo-1447752875215-b2761acb3c5d',
         title: '云朵',
         ratio: '4/3'
      },
      {
         url: 'https://images.unsplash.com/photo-1501785888041-af3ef285b470',
         title: '日出',
         ratio: '16/9'
      },
      {
         url: 'https://images.unsplash.com/photo-1469474968028-56623f02e42e',
         title: '自然',
         ratio: '3/4'
      }
   ];
   return defaultPhotos;
});

const onImageLoad = (index: number) => {
   const nextIndex = index + 1;
   if (nextIndex < photoWall.value.length && !loadedImages.value[nextIndex]) {
      setTimeout(() => {
         loadedImages.value[nextIndex] = photoWall.value[nextIndex].url;
      }, 100);
   }
};

onMounted(() => {
   if (photoWall.value.length > 0) {
      loadedImages.value[0] = photoWall.value[0].url;
   }
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
}

.screen2-desc {
   font-size: 1.1rem;
   opacity: 0.7;
   margin-top: 10px;
}

@media (max-width: 900px) {
   .screen2-content {
      flex-direction: column;
      padding: 20px;
   }
   .screen2-left {
      width: 100%;
      align-items: center;
      margin-bottom: 20px;
   }
}

.section-title {
   font-size: 2.5rem;
   font-weight: 700;
   margin-bottom: 40px;
}

.photos-card {
   width: 90vw;
   margin-top: 100px;
   background: linear-gradient(
      135deg,
      var(--cart-back-color) 0%,
      rgba(188, 222, 215, 0.3) 100%
   );
   border: 1px solid var(--cart-border-color);
   border-radius: 24px;
   padding: 24px;
   box-shadow:
      0 4px 6px -1px rgba(0, 0, 0, 0.1),
      0 2px 4px -1px rgba(0, 0, 0, 0.06),
      inset 0 1px 0 rgba(255, 255, 255, 0.2);
   flex: 1;
   min-height: 0;
   overflow: hidden;
}

.photos-grid {
   display: grid;
   grid-template-columns: repeat(4, 1fr);
   gap: 12px;
   height: 90%;
}

@media (max-width: 900px) {
   .photos-grid {
      grid-template-columns: repeat(4, 1fr);
   }
}

@media (max-width: 700px) {
   .photos-grid {
      grid-template-columns: repeat(3, 1fr);
   }
}

@media (max-width: 500px) {
   .photos-grid {
      grid-template-columns: repeat(2, 1fr);
   }
}

.photo-item {
   display: flex;
}

.photo-wrapper {
   position: relative;
   width: 100%;
   aspect-ratio: 1;
   border-radius: 16px;
   overflow: hidden;
   cursor: pointer;
   transition: transform 0.25s ease-out;
   box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
   transform: translateZ(0);
   -webkit-transform: translateZ(0);
   background: var(--back-op-color);
}

.photo-wrapper:hover {
   transform: translateY(-4px) scale(1.03);
   box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
}

.photo-wrapper img {
   width: 100%;
   height: 100%;
   object-fit: cover;
   transition: transform 0.4s ease-out;
   transform: translateZ(0);
   -webkit-transform: translateZ(0);
}

.photo-wrapper:hover img {
   transform: scale(1.08);
}

.photo-overlay {
   position: absolute;
   inset: 0;
   background: linear-gradient(
      to bottom,
      rgba(0, 0, 0, 0.1) 0%,
      rgba(0, 0, 0, 0.4) 50%,
      rgba(0, 0, 0, 0.7) 100%
   );
   display: flex;
   flex-direction: column;
   align-items: center;
   justify-content: flex-end;
   padding-bottom: 12px;
   opacity: 0;
   transition: opacity 0.3s ease;
}

.photo-wrapper:hover .photo-overlay {
   opacity: 1;
}

.photo-icon {
   font-size: 1.5rem;
   margin-bottom: 6px;
}

.photo-title {
   color: #fff;
   font-size: 1rem;
   font-weight: 600;
   text-shadow: 0 2px 8px rgba(0, 0, 0, 0.5);
   letter-spacing: 1px;
}
</style>
