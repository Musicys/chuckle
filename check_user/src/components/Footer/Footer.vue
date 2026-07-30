<template>
   <!-- 直接渲染，无需等待视口检测 -->
   <transition name="footer-fade" appear>
      <div
         ref="containerRef"
         class="footer-container"
         :class="{ 'footer-visible': isAnimated }">
         <!-- 社交媒体图标 + 中间头像 -->
         <div class="social-icons-wrapper">
            <!-- 左侧图标组 -->
            <div class="social-icons">
               <a
                  v-if="user.GitHubUrl"
                  :href="user.GitHubUrl"
                  target="_blank"
                  title="GitHub">
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-github"></use>
                  </svg>
               </a>
               <a
                  v-if="user.gitURL"
                  :href="user.gitURL"
                  target="_blank"
                  title="Gitee">
                  <img
                     src="https://gitee.com/favicon.ico"
                     alt="Gitee"
                     class="icon" />
               </a>
            </div>

            <!-- 中间个人头像 -->
            <div class="center-avatar">
               <img
                  :src="
                     user.userImg ||
                     'https://tse3-mm.cn.bing.net/th/id/OIP-C.U1UG7FN50qzrntU8he3s9wAAAA?rs=1&pid=ImgDetMain'
                  "
                  :alt="user.username"
                  class="avatar-img" />
            </div>

            <!-- 右侧图标组 -->
            <div class="social-icons">
               <a
                  v-if="user.Bilbilurl"
                  :href="user.Bilbilurl"
                  target="_blank"
                  title="哔哩哔哩">
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-Bzhan"></use>
                  </svg>
               </a>
               <a
                  v-if="user.CsdnUrl"
                  :href="user.CsdnUrl"
                  target="_blank"
                  title="CSDN">
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-csdn"></use>
                  </svg>
               </a>
               <a
                  v-if="user.QqUrl"
                  :href="user.QqUrl"
                  target="_blank"
                  title="QQ">
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-QQ"></use>
                  </svg>
               </a>
            </div>
         </div>

         <!-- 版权信息 -->
         <div class="copyright">
            <p>
               © {{ currentYear }} - {{ currentYear }} By
               <span class="author">{{ user.username }}</span>
               粤ICP备2022076449号
            </p>
            <p class="slogan">
               「Another branch will grow and flourish in the future!」
            </p>
         </div>

         <!-- 运行时间 -->
         <div class="runtime">
            <span>
               自 {{ startTime }} 以来已运行 <b>{{ runtime.days }}</b> 天
               <b>{{ runtime.hours }}</b> 小时 <b>{{ runtime.minutes }}</b> 分
               <b>{{ runtime.seconds }}</b> 秒
            </span>
         </div>

         <!-- 技术徽章 -->
         <div class="badges">
            <span class="badge badge-frame">Frame Vue</span>
            <span class="badge badge-theme">Theme ChuckHe</span>
            <span class="badge badge-source">Source GitHub</span>
            <span class="badge badge-icp">粤ICP备 2022076449</span>
            <span class="badge badge-server">Server 腾讯云</span>
            <span class="badge badge-cdn">CDN 又拍云</span>
         </div>
      </div>
   </transition>
</template>

<script setup lang="ts">
import { storeToRefs } from 'pinia';
import { useUserStore } from '@/store/user';
import { ref, onMounted, onUnmounted, nextTick } from 'vue';

const { user } = storeToRefs(useUserStore());

const isAnimated = ref(false);
const containerRef = ref<HTMLElement | null>(null);

const currentYear = new Date().getFullYear();
const startTime = '2024-01-01';
const email = 'example@email.com';

const runtime = ref({
   days: 0,
   hours: 0,
   minutes: 0,
   seconds: 0
});

let timer: number | null = null;

const updateRuntime = () => {
   const start = new Date(startTime).getTime();
   const now = Date.now();
   const diff = now - start;

   const days = Math.floor(diff / (1000 * 60 * 60 * 24));
   const hours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
   const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
   const seconds = Math.floor((diff % (1000 * 60)) / 1000);

   runtime.value = { days, hours, minutes, seconds };
};

onMounted(() => {
   // 直接渲染动画和启动定时器
   nextTick(() => {
      isAnimated.value = true;
      updateRuntime();
      timer = window.setInterval(updateRuntime, 1000);
   });
});

onUnmounted(() => {
   if (timer) {
      clearInterval(timer);
   }
});
</script>

<style lang="scss" scoped>
.footer-fade-enter-active,
.footer-fade-leave-active {
   transition: all 0.6s ease;
}

.footer-fade-enter-from {
   opacity: 0;
   transform: translateY(40px);
}

.footer-fade-leave-to {
   opacity: 0;
   transform: translateY(-40px);
}

.footer-container {
   width: 99vw;
   max-height: 300px;
   padding: 40px 50px 30px;

   text-align: center;
   opacity: 0;
   transform: translateY(30px);
   transition:
      opacity 0.8s ease,
      transform 0.8s ease;
   box-sizing: border-box;
   overflow: hidden;

   &.footer-visible {
      opacity: 1;
      transform: translateY(0);
   }
}

.social-icons-wrapper {
   display: flex;
   justify-content: center;
   align-items: center;
   gap: 20px;
   margin-bottom: 20px;
}

.social-icons {
   display: flex;
   justify-content: center;
   align-items: center;
   gap: 15px;

   a {
      width: 40px;
      height: 40px;
      display: flex;
      justify-content: center;
      align-items: center;
      border-radius: 50%;
      background: rgba(0, 0, 0, 0.06);
      border: 1px solid rgba(0, 0, 0, 0.1);
      color: var(--bk-font-color);
      transition: all 0.3s ease;

      &:hover {
         background: var(--bk-draw-back-color);
         color: #fff;
         border-color: var(--bk-draw-back-color);
         transform: translateY(-3px);
         box-shadow: 0 6px 16px rgba(28, 190, 252, 0.35);
      }
   }

   .icon {
      width: 20px;
      height: 20px;
      fill: currentColor;
   }
}

.center-avatar {
   position: relative;
   width: 70px;
   height: 70px;
   flex-shrink: 0;

   &::before {
      content: '';
      position: absolute;
      top: -4px;
      left: -4px;
      right: -4px;
      bottom: -4px;
      border-radius: 50%;
      background: linear-gradient(
         45deg,
         var(--cart-border-color),
         var(--bk-draw-back-color),
         var(--el-but-back)
      );
      z-index: 0;
      animation: avatarGlow 3s ease-in-out infinite;
   }
}

.avatar-img {
   position: relative;
   z-index: 1;
   width: 100%;
   height: 100%;
   border-radius: 50%;
   object-fit: cover;
   border: 3px solid #fff;
   transition: transform 0.3s ease;
   box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);

   &:hover {
      transform: scale(1.05);
   }
}

@keyframes avatarGlow {
   0%,
   100% {
      opacity: 0.8;
   }
   50% {
      opacity: 1;
   }
}

.copyright {
   margin-bottom: 15px;

   p {
      margin: 5px 0;
      color: var(--bk-font-color);
      opacity: 0.85;
      font-size: 14px;
   }

   .author {
      color: var(--cart-border-color);
      font-weight: bold;
      margin: 0 5px;
      text-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
   }

   .slogan {
      font-size: 13px;
      color: var(--bk-font-color);
      opacity: 0.6;
      font-style: italic;
   }
}

.runtime {
   margin-bottom: 20px;

   span {
      color: var(--bk-font-color);
      opacity: 0.85;
      font-size: 13px;

      b {
         color: var(--cart-border-color);
         font-weight: bold;
         margin: 0 3px;
         text-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
      }
   }
}

.badges {
   display: flex;
   justify-content: center;
   align-items: center;
   flex-wrap: wrap;
   gap: 8px;
   max-width: 800px;
   margin: 0 auto;
}

.badge {
   display: inline-block;
   padding: 4px 12px;
   border-radius: 15px;
   font-size: 12px;
   color: #fff;
   font-weight: 500;
   transition: all 0.3s ease;

   &:hover {
      transform: translateY(-2px);
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
   }
}

.badge-frame {
   background: linear-gradient(135deg, #1cbefc, #49b1f5);
}

.badge-theme {
   background: linear-gradient(135deg, #ff6b6b, #ee5a24);
}

.badge-source {
   background: linear-gradient(135deg, #333, #555);
}

.badge-icp {
   background: linear-gradient(135deg, #ff9a56, #ff6b6b);
}

.badge-server {
   background: linear-gradient(135deg, #0052d9, #007fff);
}

.badge-cdn {
   background: linear-gradient(135deg, #ffa500, #ff8c00);
}

.badge-copyright {
   background: linear-gradient(135deg, #6c5ce7, #a29bfe);
}

/* 夜间主题覆盖 */
:deep([data-theme='dark']) .footer-container {
   .social-icons {
      a {
         background: rgba(255, 255, 255, 0.06);
         border: 1px solid rgba(255, 255, 255, 0.1);

         &:hover {
            background: #2eada7;
            color: #fff;
            border-color: #2eada7;
            box-shadow: 0 6px 16px rgba(46, 173, 167, 0.35);
         }
      }
   }

   .avatar-img {
      border-color: #0b2f40;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.4);
   }

   .badge-frame {
      background: linear-gradient(135deg, #2eada7, #1f1f1f);
   }
}

/* 响应式样式 */
@media (max-width: 768px) {
   .footer-container {
      padding: 30px 20px 20px;
   }

   .social-icons-wrapper {
      gap: 10px;
      flex-wrap: wrap;
   }

   .social-icons {
      gap: 10px;

      a {
         width: 35px;
         height: 35px;
      }

      .icon {
         width: 18px;
         height: 18px;
      }
   }

   .center-avatar {
      width: 60px;
      height: 60px;
      order: -1;
      width: 100%;
      display: flex;
      justify-content: center;
   }
}
</style>
