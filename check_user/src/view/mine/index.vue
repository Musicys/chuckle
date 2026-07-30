<script setup lang="ts">
import MapContainer from '@/components/MapContainer.vue';
import { useUserStore } from '@/store/user';
import { getVisitsStats, type VisitsStats } from '@/api/mine';
import { getFriendLinks, type FriendLinkItem } from '@/api/start';
import { ref, onMounted, onUnmounted } from 'vue';
import { useLazyVisible } from '@/view/home/useLazyVisible';

const userStore = useUserStore();

// 追求轮询索引
const currentPursuitIndex = ref(0);
let pursuitTimer: ReturnType<typeof setInterval> | null = null;

// 访问统计数据
const visitsStats = ref<VisitsStats>({
   todayPv: 0,
   todayUv: 0,
   monthPv: 0,
   totalPv: 0
});
const visitsLoading = ref(false);

// 获取访问统计
const loadVisitsStats = async () => {
   visitsLoading.value = true;
   try {
      const response = await getVisitsStats();
      if (response.code === 0 && response.data) {
         visitsStats.value = response.data;
      }
   } catch (error) {
      console.error('获取访问统计失败:', error);
   } finally {
      visitsLoading.value = false;
   }
};

// 友链数据
const friendLinks = ref<FriendLinkItem[]>([]);
const friendLinksLoading = ref(false);

// 获取友链列表
const loadFriendLinks = async () => {
   friendLinksLoading.value = true;
   try {
      const response = await getFriendLinks();
      if (response.code === 0 && response.data) {
         // 获取最新的10个友链
         friendLinks.value = (response.data as FriendLinkItem[]).slice(0, 8);
      }
   } catch (error) {
      console.error('获取友链失败:', error);
   } finally {
      friendLinksLoading.value = false;
   }
};

onMounted(() => {
   // 每2秒切换一个追求
   pursuitTimer = setInterval(() => {
      const pursuits = userStore.config.pursuits || [];
      if (pursuits.length > 0) {
         currentPursuitIndex.value =
            (currentPursuitIndex.value + 1) % pursuits.length;
      }
   }, 1000);

   // 加载访问统计
   loadVisitsStats();

   // 加载友链
   loadFriendLinks();
});

onUnmounted(() => {
   if (pursuitTimer) {
      clearInterval(pursuitTimer);
   }
});

// 当前显示的追求
const currentPursuit = () => {
   const pursuits = userStore.config.pursuits || [];
   return pursuits[currentPursuitIndex.value] || '乐趣·兴趣';
};

// 懒加载可见性控制
const { isVisible, containerRef } = useLazyVisible('200px');
const hasLoaded = ref(false);

// 当组件进入视口时加载数据
import { watch } from 'vue';
watch(isVisible, visible => {
   if (visible && !hasLoaded.value) {
      hasLoaded.value = true;
      loadVisitsStats();
      loadFriendLinks();
   }
});
</script>

<template>
   <div class="mine-page" ref="containerRef">
      <template v-if="isVisible">
         <div class="top">
            <!-- left -->
            <div class="top-futer-txt">
               <div class="animate-item" style="--delay: 0.1s">
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-yuanshen-wa"></use>
                  </svg>
                  <span>神里绫华的狗</span>
               </div>
               <div class="animate-item" style="--delay: 0.2s">
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-CVV"></use>
                  </svg>
                  <span>专业复制粘贴用户</span>
               </div>
               <div class="animate-item" style="--delay: 0.3s">
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-csdn"></use>
                  </svg>
                  <span> CSDN高级访问用户</span>
               </div>
               <div class="animate-item" style="--delay: 0.4s">
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-github"></use>
                  </svg>
                  <span> github高级访问用户</span>
               </div>
            </div>
            <!-- center -->
            <div class="box animate-item" style="--delay: 0.3s">
               <div class="img"></div>
               <img
                  :src="
                     userStore.user.userImg ||
                     'https://tse3-mm.cn.bing.net/th/id/OIP-C.U1UG7FN50qzrntU8he3s9wAAAA?rs=1&pid=ImgDetMain'
                  "
                  :alt="userStore.user.username" />
            </div>
            <!-- right -->

            <div class="top-futer-txt02">
               <div class="animate-item" style="--delay: 0.1s">
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-CVV"></use>
                  </svg>
                  <span>英魂之刃王者选手</span>
               </div>
               <div class="animate-item" style="--delay: 0.2s">
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-CVV"></use>
                  </svg>
                  <span> 毕业失业的人员之一</span>
               </div>
               <div class="animate-item" style="--delay: 0.3s">
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-daxuesheng"></use>
                  </svg>
                  <span> 自认天赋型游戏玩家</span>
               </div>

               <div class="animate-item" style="--delay: 0.4s">
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-rengongzhinengjiqiren"></use>
                  </svg>
                  <span> AI深度依赖者</span>
               </div>
            </div>
         </div>

         <!-- guanyu benz -->
         <h1 style="text-align: center; margin: 0.5em auto">关于本站</h1>

         <!-- cart -->

         <div class="grid">
            <div
               class="animate-item"
               style="
                  grid-column: 1 / 4;
                  display: flex;
                  flex-direction: column;
                  justify-content: center;
                  background: linear-gradient(to right, #3039cf, #1e7ece);
                  color: wheat;
                  opacity: 0.7;
                  font-size: 1.1em;
                  --delay: 0.1s;
               ">
               <span>这里~这里~</span>
               <span>网名 {{ userStore.user.username || '轻笑Chuckle' }}</span>
               <span>有了备案就是 实名制上网啦QAQ</span>
               <span>{{
                  userStore.user.occupation ||
                  '是一只还在求学路上的 大学生、个人博主'
               }}</span>
            </div>
            <div
               class="animate-item"
               style="
                  grid-column: 4/ 6;
                  background: #1d1b26;
                  background: var(--mine-back-zq-back);
                  display: flex;
                  flex-direction: column;
                  justify-content: center;
                  --delay: 0.2s;
               ">
               <span style="font-size: 0.8em">追求</span>
               <span style="font-size: 1.8em">源于</span>
               <span style="font-size: 1.8em">技术</span>
               <div class="dom">
                  <span>{{ currentPursuit() }}</span>
               </div>
            </div>

            <div
               class="animate-item"
               style="
                  grid-column: 1/3;
                  grid-row: 2/4;
                  color: white;
                  --delay: 0.3s;
               ">
               <div style="margin-top: 1em; margin-left: 1em">数据</div>
               <div style="font-size: 2em; margin-left: 1em">访问统计</div>

               <div class="dom-grid">
                  <div class="animate-item" style="--delay: 0.4s">
                     <span>今日访问</span>
                     <span>{{
                        visitsLoading ? '--' : visitsStats.todayPv
                     }}</span>
                  </div>

                  <div class="animate-item" style="--delay: 0.5s">
                     <span>访问人数</span>
                     <span>{{
                        visitsLoading ? '--' : visitsStats.todayUv
                     }}</span>
                  </div>
                  <div class="animate-item" style="--delay: 0.6s">
                     <span>本月访问</span>
                     <span>{{
                        visitsLoading ? '--' : visitsStats.monthPv
                     }}</span>
                  </div>
                  <div class="animate-item" style="--delay: 0.7s">
                     <span>总访问量</span>
                     <span>{{
                        visitsLoading ? '--' : visitsStats.totalPv
                     }}</span>
                  </div>

                  <div></div>
               </div>
            </div>
            <div
               class="animate-item"
               style="grid-column: 3/6; overflow: hidden; --delay: 0.4s">
               <MapContainer></MapContainer>
            </div>
            <div
               class="animate-item xinx"
               style="
                  grid-column: 3/6;
                  overflow: hidden;
                  background: var(--mine-back-zq-back);
                  display: flex;
                  justify-content: space-around;
                  align-items: center;
                  height: 100%;
                  --delay: 0.5s;
               ">
               <div class="animate-item" style="--delay: 0.6s">
                  <span>年龄</span
                  ><span style="color: #43a6c6">{{
                     userStore.config.age || '2003'
                  }}</span>
               </div>
               <div class="animate-item" style="--delay: 0.7s">
                  <span>专业</span
                  ><span style="color: #c69043">{{
                     userStore.config.major || '计算机科学与技术'
                  }}</span>
               </div>
               <div class="animate-item" style="--delay: 0.8s">
                  <span>现在职业</span
                  ><span style="color: #b04fe6">{{
                     userStore.config.currentOccupation || '大三学生'
                  }}</span>
               </div>
            </div>
         </div>

         <div class="flex-cart">
            <div
               v-for="(game, index) in userStore.config.games"
               :key="index"
               class="animate-item"
               :style="{ color: 'white', '--delay': 0.1 + index * 0.1 + 's' }">
               <div class="cart-tilte">
                  <span>爱好游戏</span>
                  <span>{{ game.title }}</span>
               </div>
               <img
                  v-if="game.icon"
                  :src="game.icon"
                  style="width: 100%; height: 100%"
                  :alt="game.title" />
               <div
                  v-else
                  style="
                     width: 100%;
                     height: 100%;
                     background: #1d1b26;
                     display: flex;
                     align-items: center;
                     justify-content: center;
                     font-size: 3em;
                  ">
                  🎮
               </div>
            </div>
            <!-- 默认游戏卡片（当配置为空时显示） -->
            <div
               v-if="userStore.config.games.length === 0"
               class="animate-item"
               style="color: white; --delay: 0.1s">
               <div class="cart-tilte">
                  <span>爱好游戏</span>
                  <span>英魂之刃</span>
               </div>
               <video
                  src="https://wjdown.99.com/games/yhzr/act/2020/znq/znq-index.mp4"
                  autoplay="true"
                  loop="true"
                  muted="true"
                  style="width: 100%; position: absolute; top: 0"></video>
            </div>
            <div
               v-if="userStore.config.games.length === 0"
               class="animate-item"
               style="color: white; --delay: 0.2s">
               <div class="cart-tilte">
                  <span>爱好游戏</span>
                  <span>原神</span>
               </div>
               <img
                  src="https://upload-bbs.miyoushe.com/upload/2022/11/28/17949827/2266190b99bece98ebc3bfb05212ca01_5809411558282465606.jpg?x-oss-process=image//resize,s_600/quality,q_80/auto-orient,0/interlace,1/format,jpg"
                  style="width: 100%; height: 100%"
                  alt="" />
            </div>
            <div
               v-if="userStore.config.games.length === 0"
               class="animate-item"
               style="color: white; --delay: 0.3s">
               <div class="cart-tilte">
                  <span>爱好游戏</span>
                  <span>永杰无间</span>
               </div>
               <img
                  src="https://web-uns.oss-cn-beijing.aliyuncs.com/use.png"
                  style="width: 100%; height: 100%"
                  alt="" />
            </div>
            <div
               v-if="userStore.config.games.length === 0"
               class="animate-item"
               style="color: white; --delay: 0.4s">
               <div class="cart-tilte">
                  <span>爱好游戏</span>
                  <span>英雄联盟</span>
               </div>
               <img
                  src="https://ts1.cn.mm.bing.net/th/id/R-C.78c17b06aa8e5df5f392034973ca7484?rik=b0RrvtImKRUi4g&riu=http%3a%2f%2fimg.likebizhi.com%2fuploads%2flikebizhi%2fup%2f2022%2f01%2f28d3df888cd09f2bda25a8f9586b2211125.jpg&ehk=%2fAWQ74FBd62DEYslIy0O2CK0X1g2nGRVQeMTMowf%2fvw%3d&risl=&pid=ImgRaw&r=0"
                  style="width: 100%; height: 100%"
                  alt="" />
            </div>
         </div>

         <!-- 为什么建站 -->
         <div class="why-build-site animate-item" style="--delay: 0.1s">
            <img class="why-build-bg" src="@/static/bg.webp" alt="" />
            <div class="why-build-content">
               <div class="why-build-tag">
                  <span>💡 历历程</span>
               </div>
               <h2 class="why-build-title">为什么建站</h2>
               <p class="why-build-text">
                  以前尝试过搭自己的网站，但当时技术力低，视野不够广阔，互联网也未进入新时代，最终无果而终。
               </p>
               <p class="why-build-text">
                  如今建此站，一方面是记录自己的学习与生活，另一方面，是在这日新月异的互联网上，留下一点点的足迹。当然，作为一个计科专业的人来说，也理应有一个博客。
               </p>
               <p class="why-build-highlight">
                  日月忽其不淹兮，春与秋其代序，此地也会成为回忆之所。
               </p>
               <p class="why-build-text">这是我所做的，感到快乐的事。</p>
               <p class="why-build-text">
                  这是一个个人博客，亦是一叶轻舟，欢迎在互联网上的旅人来此做客，与各位交流和分享。
               </p>
            </div>
         </div>

         <!-- 友链展示区域 -->
         <div v-if="friendLinks.length > 0" class="friend-links-section">
            <h2 class="section-title">友情链接</h2>
            <div class="friend-links-grid">
               <a
                  v-for="(link, index) in friendLinks"
                  :key="link.id"
                  :href="link.url"
                  target="_blank"
                  class="friend-link-card animate-item"
                  :style="{ '--delay': 0.1 + index * 0.1 + 's' }">
                  <div class="friend-card-inner">
                     <div class="friend-avatar">
                        <img
                           :src="
                              link.avatar ||
                              'https://tse3-mm.cn.bing.net/th/id/OIP-C.U1UG7FN50qzrntU8he3s9wAAAA?rs=1&pid=ImgDetMain'
                           "
                           :alt="link.name" />
                     </div>
                     <div class="friend-info">
                        <span class="friend-name">{{ link.name }}</span>
                        <span class="friend-desc">{{ link.description }}</span>
                     </div>
                     <div class="friend-url">
                        <svg class="icon" aria-hidden="true">
                           <use xlink:href="#icon-CVV"></use>
                        </svg>
                     </div>
                  </div>
               </a>
            </div>
         </div>
      </template>
   </div>
</template>

<style lang="scss" scoped>
.icon {
   margin: auto 0.3em;
   font-size: 0.9em;
}

.xinx {
   & > div {
      & > span {
         display: block;

         font-size: 1em;
      }

      & > span:nth-child(2) {
         font-size: 2em;
         margin-top: 0.5em;
      }
   }
}

/* 懒加载动画 */
@keyframes fadeInUp {
   from {
      opacity: 0;
      transform: translateY(30px);
   }
   to {
      opacity: 1;
      transform: translateY(0);
   }
}

.animate-item {
   opacity: 0;
   transform: translateY(30px);
   animation: fadeInUp 0.6s ease forwards;
   animation-delay: var(--delay, 0s);
}

@keyframes transfroms {
   to {
      transform: translateY(-100%);
   }

   from {
      transform: translateY(0%);
   }
}

.grid {
   & > div {
      .dom {
         font-size: 1.8em;
         margin-left: 3em;

         width: 3em;

         overflow: hidden;

         & > span {
            animation: transfroms 1s infinite;
            display: block;
            color: green;
         }
      }

      & > span {
         margin-left: 3em;
         display: block;
         font-size: 1.2em;
      }
   }
}

@media (min-width: 700px) {
   .dom-grid {
      width: 80%;
      height: 70%;

      display: grid;
      grid-template-columns: 1fr 1fr;
      margin: 1em auto;

      & > div {
         width: 100%;
         display: flex;
         flex-direction: column;
         height: 100%;

         & > span:nth-child(2) {
            font-size: 1.5em;
            margin-top: 0.5em;
            margin-left: 0.5em;
         }
      }
   }

   .grid {
      display: grid;
      grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
      width: 100%;
      height: 500px;
      gap: 5px;

      & > div {
         width: 100%;

         height: 100%;
         justify-self: end;
         border-radius: 15px;
         background: url('https://img.shetu66.com/2023/07/14/1689302077000124.png');
      }
   }

   .flex-cart {
      margin-top: 10px;
      display: grid;
      grid-template-columns: 1fr 1fr;

      gap: 10px;

      & > div {
         width: 100%;
         height: 100%;
         border-radius: 10px;
         border: 1px solid black;
         overflow: hidden;
         position: relative;
         min-height: 300px;

         .cart-tilte {
            position: absolute;
            z-index: 3;
            color: white;

            & > span {
               display: block;
               margin-left: 1em;
            }

            & > span:nth-child(1) {
               font-size: 0.8em;
               margin-top: 0.5em;
            }

            & > span:nth-child(2) {
               margin-top: 0.5em;
               font-size: 2em;
            }
         }
      }
   }
}

@media (max-width: 700px) {
   /* 这里写小于500px屏幕宽度时要应用的样式 */

   .dom-grid {
      width: 80%;
      height: 50%;

      display: grid;
      grid-template-columns: 1fr 1fr;
      margin: 1em auto;

      & > div {
         width: 100%;
         display: flex;
         flex-direction: column;
         height: 100%;
      }
   }

   .grid {
      display: flex;
      flex-direction: column;
      flex-wrap: wrap;
      width: 100%;

      & > div {
         min-height: 200px;
         justify-self: end;
         border-radius: 15px;
         background: url('https://img.shetu66.com/2023/07/14/1689302077000124.png');
         margin-top: 10px;
         height: 200px;
      }
   }

   .flex-cart {
      margin-top: 10px;
      display: flex;
      grid-template-columns: 1fr 1fr;
      flex-direction: column;
      height: 500px;
      gap: 10px;

      & > div {
         width: 100%;
         height: 100%;
         border-radius: 10px;
         border: 1px solid black;
         overflow: hidden;
         position: relative;
         min-height: 250px;

         .cart-tilte {
            position: absolute;
            z-index: 3;
            color: white;

            & > span {
               display: block;
               margin-left: 1em;
            }

            & > span:nth-child(1) {
               font-size: 0.8em;
               margin-top: 0.5em;
            }

            & > span:nth-child(2) {
               margin-top: 0.5em;
               font-size: 2em;
            }
         }
      }
   }

   .why-build-site {
      min-height: auto;

      .why-build-content {
         max-width: 100%;
         padding: 25px 20px;

         .why-build-title {
            font-size: 1.5em;
            margin-bottom: 14px;
         }

         .why-build-text {
            font-size: 0.9em;
            line-height: 1.6;
            margin-bottom: 10px;
         }

         .why-build-highlight {
            font-size: 0.9em;
            padding: 10px 15px;
            margin: 12px 0;
         }
      }
   }
}

@keyframes rotate {
   from {
      transform: rotate(0deg);
   }

   to {
      transform: rotate(360deg);
   }
}

@keyframes tranformtop {
   0% {
      transform: translateY(0%);
   }

   25% {
      transform: translateY(15%);
   }

   50% {
      transform: translateY(0%);
   }

   75% {
      transform: translateY(-15%);
   }

   100% {
      transform: translateY(0%);
   }
}

.mine-page {
   margin-bottom: 200px;
   margin-top: 50px;

   .top {
      display: flex;
      justify-content: center;
      align-items: center;

      .box {
         min-width: 200px;
         min-height: 200px;
         border-radius: 50%;

         position: relative;
         background: white;

         &::after {
            content: '';
            /* 开始引号 */
            position: absolute;

            width: 20px;
            height: 20px;
            background: #6bdf8f;
            border-radius: 50%;
            border: 4px solid white;
            right: 25px;
            bottom: 5px;
            color: #ccc;
         }

         .img {
            position: absolute;

            width: 100%;
            height: 100%;
            animation: rotate 5s linear infinite;
            background: linear-gradient(to right, #19a3e7, #b0e0e6, #9a78f9);
            border-radius: 50%;
         }

         & > img {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            width: 91%;
            height: 91%;

            border-radius: 50%;
         }
      }

      .top-futer-txt {
         & .animate-item {
            opacity: 1;
            transform: none;
            animation: tranformtop 3s linear infinite;
            animation-delay: var(--delay, 0s);
            padding: 5px 15px;
            background: var(--mine-back-color);
            border: wheat 0.1px solid;
            border-radius: 15px;
            margin-top: 1em;
            width: 160px;
            margin-right: var(--l);
            display: flex;
            justify-content: end;
            align-items: center;
         }
      }

      .top-futer-txt02 {
         margin-left: 10px;

         & .animate-item {
            opacity: 1;
            transform: none;
            animation: tranformtop 3s linear infinite;
            animation-delay: var(--delay, 0s);
            padding: 5px 15px;
            background: var(--mine-back-color);
            border: wheat 0.1px solid;
            border-radius: 15px;
            margin-top: 1em;
            width: 160px;
            margin-left: var(--1);
            display: flex;
            justify-content: start;
            flex-direction: row-reverse;
            align-items: center;
         }
      }
   }

   // 为什么建站样式
   .why-build-site {
      position: relative;
      width: 100%;

      margin: 50px auto 0;
      min-height: 280px;
      border-radius: 16px;
      overflow: hidden;
      box-shadow: 0 8px 32px rgba(0, 0, 0, 0.15);

      .why-build-bg {
         position: absolute;
         top: 0;
         left: 0;
         width: 100%;
         height: 100%;
         object-fit: cover;
         z-index: 1;
      }

      .why-build-site::before {
         content: '';
         position: absolute;
         top: 0;
         left: 0;
         width: 100%;
         height: 100%;
         z-index: 2;
         background: linear-gradient(
            to right,
            var(--why-build-overlay-start) 0%,
            var(--why-build-overlay-mid) 50%,
            var(--why-build-overlay-end) 100%
         );
      }

      .why-build-content {
         position: relative;
         z-index: 2;
         max-width: 600px;
         padding: 40px 50px;
         color: var(--why-build-text);

         .why-build-tag {
            display: inline-block;
            padding: 4px 12px;
            background: var(--why-build-tag-bg);
            border: 1px solid var(--why-build-tag-border);
            border-radius: 12px;
            font-size: 0.85em;
            color: var(--color-title);
            margin-bottom: 16px;
         }

         .why-build-title {
            font-size: 2em;
            font-weight: 700;
            margin: 0 0 20px 0;
            color: var(--why-build-title);
            letter-spacing: 2px;
         }

         .why-build-text {
            font-size: 0.95em;
            line-height: 1.8;
            margin: 0 0 14px 0;
            color: var(--why-build-text-secondary);
         }

         .why-build-highlight {
            font-size: 1em;
            line-height: 1.8;
            margin: 16px 0;
            padding: 12px 20px;
            border-left: 3px solid var(--color-title);
            background: var(--why-build-highlight-bg);
            color: var(--why-build-highlight-text);
            font-style: italic;
            border-radius: 0 8px 8px 0;
         }
      }
   }

   // 友链展示区域样式
   .friend-links-section {
      padding: 20px;

      .section-title {
         text-align: center;
         font-size: 1.5em;
         color: var(--bk-font-color);
         margin-bottom: 20px;
         position: relative;

         &::after {
            content: '';
            position: absolute;
            bottom: -5px;
            left: 50%;
            transform: translateX(-50%);
            width: 60px;
            height: 3px;
            background: var(--color-title);
            border-radius: 2px;
         }
      }

      .friend-links-grid {
         display: grid;
         grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
         gap: 20px;
         max-width: 1200px;
         margin: 0 auto;
      }

      .friend-link-card {
         display: block;
         text-decoration: none;
         color: inherit;
         cursor: pointer;

         .friend-card-inner {
            position: relative;
            padding: 20px;
            background: var(--back-op-color);
            backdrop-filter: blur(10px);
            border-radius: 16px;
            border: 1px solid var(--bk-draw-back-color);
            transition: all 0.3s ease;

            &:hover {
               transform: translateY(-8px);
               box-shadow: 0 15px 40px rgba(28, 190, 252, 0.2);
               border-color: var(--color-title);
            }
         }

         .friend-avatar {
            width: 80px;
            height: 80px;
            border-radius: 50%;
            overflow: hidden;
            margin: 0 auto 15px;
            border: 3px solid var(--bk-draw-back-color);
            transition: all 0.3s ease;

            &:hover {
               transform: scale(1.05);
               border-color: var(--color-title);
            }

            img {
               width: 100%;
               height: 100%;
               object-fit: cover;
            }
         }

         .friend-info {
            text-align: center;

            .friend-name {
               display: block;
               font-size: 1.2em;
               font-weight: 600;
               color: var(--bk-font-color);
               margin-bottom: 8px;
            }

            .friend-desc {
               display: block;
               font-size: 0.9em;
               color: var(--bk-draw-back-color);
               overflow: hidden;
               text-overflow: ellipsis;
               white-space: nowrap;
            }
         }

         .friend-url {
            position: absolute;
            top: 15px;
            right: 15px;
            width: 32px;
            height: 32px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
            background: rgba(28, 190, 252, 0.1);
            color: var(--color-title);
            opacity: 0;
            transition: all 0.3s ease;

            .friend-card-inner:hover & {
               opacity: 1;
            }
         }
      }
   }
}
</style>
