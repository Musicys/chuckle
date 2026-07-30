<template>
   <div class="scroll-wrapper">
      <div
         class="scroll-content"
         :style="{ transform: `translateY(-${currentIndex * 100}vh)` }">
         <section
            class="screen screen1"
            :class="{ active: currentIndex === 0 }">
            <div class="screen-content">
               <div class="top">
                  <img :src="user.userImg" alt="头像" />
               </div>
               <div class="center">
                  <div class="title">{{ currentTitle }}</div>
                  <div class="subtitle">{{ user.drawtilte }}</div>
               </div>
               <div class="bottom">
                  <div class="icons">
                     <a
                        :href="user.GitHubUrl"
                        target="_blank"
                        v-if="user.GitHubUrl">
                        <svg class="icon" aria-hidden="true">
                           <use xlink:href="#icon-github"></use>
                        </svg>
                     </a>
                  </div>
                  <div class="icons">
                     <a
                        :href="user.CsdnUrl"
                        target="_blank"
                        v-if="user.CsdnUrl">
                        <svg class="icon" aria-hidden="true">
                           <use xlink:href="#icon-csdn"></use>
                        </svg>
                     </a>
                  </div>
                  <div class="icons">
                     <a :href="user.QqUrl" target="_blank" v-if="user.QqUrl">
                        <svg class="icon" aria-hidden="true">
                           <use xlink:href="#icon-QQ"></use>
                        </svg>
                     </a>
                  </div>
                  <div class="icons">
                     <a
                        :href="user.Bilbilurl"
                        target="_blank"
                        v-if="user.Bilbilurl">
                        <svg class="icon" aria-hidden="true">
                           <use xlink:href="#icon-Bzhan"></use>
                        </svg>
                     </a>
                  </div>
                  <div class="icons">
                     <a href="/muisc">
                        <svg class="icon" aria-hidden="true">
                           <use xlink:href="#icon-rengongzhinengjiqiren"></use>
                        </svg>
                     </a>
                  </div>
               </div>
               <div class="scroll-hint">
                  <svg
                     class="arrow"
                     viewBox="0 0 24 24"
                     fill="none"
                     stroke="currentColor"
                     stroke-width="2">
                     <path d="M12 5v14M5 12l7 7 7-7" />
                  </svg>
               </div>
            </div>
         </section>

         <section
            class="screen screen2"
            :class="{ active: currentIndex === 1 }">
            <Portfolio />
         </section>

         <section
            class="screen screen3"
            :class="{ active: currentIndex === 2 }">
            <FriendLinks />
         </section>
      </div>
      <img src="@/static/lding.gif" alt="" class="blog-bg" />
   </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue';
import { storeToRefs } from 'pinia';
import { useUserStore } from '@/store/user';
import Portfolio from './components/Portfolio.vue';
import FriendLinks from './components/FriendLinks.vue';

const { user } = storeToRefs(useUserStore());

const currentTitle = ref('');
const currentIndex = ref(0);
const lastScrollTime = ref(new Date());
const animationDuration = 700;
const totalScreens = 3;
let intervalId: ReturnType<typeof setInterval> | null = null;

const typeWriter = (text: string, speed: number) => {
   let index = 0;
   intervalId = setInterval(() => {
      if (index < text.length) {
         currentTitle.value += text.charAt(index);
         index++;
      } else {
         currentTitle.value = text.charAt(1);
         index = 1;
      }
   }, speed);
};

const cleanup = () => {
   if (intervalId !== null) {
      clearInterval(intervalId);
      intervalId = null;
   }
};

const handleWheel = (e: WheelEvent) => {
   if (
      new Date().getTime() - lastScrollTime.value.getTime() <
      animationDuration
   )
      return;

   const delta = e.deltaY || -e.wheelDeltaY;

   if (delta > 0 && currentIndex.value < totalScreens - 1) {
      currentIndex.value++;
   } else if (delta < 0 && currentIndex.value > 0) {
      currentIndex.value--;
   } else {
      return;
   }

   lastScrollTime.value = new Date();
};

onMounted(() => {
   const title = user.value.StartTilte;
   typeWriter(title, 200);
   window.addEventListener('wheel', handleWheel);
});

onBeforeUnmount(() => {
   cleanup();
   window.removeEventListener('wheel', handleWheel);
});
</script>

<style scoped>
.scroll-wrapper {
   height: 100vh;
   overflow: hidden;
   position: absolute;
   left: 0;
   top: 0;
   width: 100vw;
}

.scroll-content {
   transition: transform 0.5s cubic-bezier(0.25, 0.8, 0.25, 1);
   transform: translateZ(0);
   overflow: hidden;
   -webkit-transform: translateZ(0);
   -webkit-backface-visibility: hidden;
   backface-visibility: hidden;
   will-change: transform;
}

.screen {
   height: 100vh;
   display: flex;
   align-items: flex-start;
   justify-content: flex-start;
   position: relative;
   color: var(--bk-font-color);
   overflow: visible;
}

.screen-content {
   width: 100%;

   text-align: center;
   display: flex;
   align-items: center;
   justify-content: flex-start;
   flex-direction: column;
   height: 100%;

   overflow-y: hidden;
   box-sizing: border-box;
}

.screen.active .section-title {
   animation: fadeInUp 0.5s ease-out forwards;
   transform: translateZ(0);
   will-change: transform, opacity;
}

.screen.active .portfolio-item,
.screen.active .link-item {
   animation: fadeInUp 0.4s ease-out forwards;
   transform: translateZ(0);
   -webkit-transform: translateZ(0);
   -webkit-backface-visibility: hidden;
   backface-visibility: hidden;
   will-change: transform, opacity;
}

@keyframes fadeInUp {
   from {
      opacity: 0;
      transform: translateY(20px) translateZ(0);
   }
   to {
      opacity: 1;
      transform: translateY(0) translateZ(0);
   }
}

.screen1 {
   display: flex;
   justify-content: center;
   align-items: center;
}

.screen1 .screen-content {
   justify-content: center;
   height: auto;
   max-height: none;
   overflow-y: visible;
}

.screen1 .top img {
   width: 150px;
   height: 150px;
   border-radius: 50%;
   border: 4px solid rgba(255, 255, 255, 0.5);
   box-shadow: 0 8px 32px rgba(0, 0, 0, 0.2);
}

.screen1 .center {
   margin-top: 30px;
}

.screen1 .center .title {
   font-size: 2rem;
   font-weight: 600;
   margin-bottom: 10px;
}

.screen1 .center .subtitle {
   font-size: 1.2rem;
   opacity: 0.8;
}

.screen1 .bottom {
   display: flex;
   justify-content: center;
   gap: 20px;
   margin-top: 40px;
}

.screen1 .bottom .icons {
   width: 50px;
   height: 50px;
   border-radius: 50%;
   background: var(--back-op-color);
   display: flex;
   align-items: center;
   justify-content: center;
   transition: all 0.3s ease;
   cursor: pointer;
}

.screen1 .bottom .icons:hover {
   background: rgba(255, 255, 255, 0.4);
   transform: translateY(-5px);
}

.screen1 .bottom .icons .icon {
   font-size: 1.5rem;
}

.scroll-hint {
   position: absolute;
   bottom: 40px;
   animation: bounce 2s infinite;
}

.scroll-hint .arrow {
   width: 30px;
   height: 30px;
}

@keyframes bounce {
   0%,
   20%,
   50%,
   80%,
   100% {
      transform: translateY(0);
   }
   40% {
      transform: translateY(10px);
   }
   60% {
      transform: translateY(5px);
   }
}

@media (max-width: 768px) {
   .screen-content {
      padding: 20px;
   }

   .screen1 .top img {
      width: 100px;
      height: 100px;
   }

   .screen1 .center .title {
      font-size: 1.5rem;
   }

   .screen1 .center .subtitle {
      font-size: 1rem;
   }

   .screen1 .bottom {
      gap: 15px;
   }

   .screen1 .bottom .icons {
      width: 40px;
      height: 40px;
   }
}
.blog-bg {
   position: absolute;
   z-index: -1;
   opacity: 0.1;
   width: 100vw;
   inset: 0;
   height: 100vh;
   pointer-events: none;
}
</style>
