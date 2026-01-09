<script setup lang="ts">
import { onMounted, ref } from 'vue';
import util from '@/util/funtion';
import { Ispc } from '@/util/windows';
import { handleClose } from '@/util/slot';
import Pop from '@/components/pop.vue';
import { IsScroll } from '@/util/scrse';
import { startpop, Ispop } from '@/util/com/pop';
// IsTab 下滑css

const IsTab = ref(true);

onMounted(() => {
   const currentTheme = document.documentElement.getAttribute('data-theme');
   if (currentTheme == 'dark') {
      IsTab.value = false;
   } else {
      IsTab.value = true;
   }
});

const setIsTab: Function = () => {
   IsTab.value = !IsTab.value;

   const currentTheme = document.documentElement.getAttribute('data-theme');
   if (currentTheme == 'dark') {
      IsTab.value = true;
   } else {
      IsTab.value = false;
   }
   console.log('==》', currentTheme);

   const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
   document.documentElement.setAttribute('data-theme', newTheme);
};

// 动画钩子函数
const beforeEnter = el => {
   el.style.opacity = 0;
   el.style.transform = 'translateY(20px)';
};

const enter = (el, done) => {
   el.offsetWidth; // trigger reflow
   el.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
   el.style.opacity = 1;
   el.style.transform = 'translateY(0)';
   done();
};

const leave = (el, done) => {
   el.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
   el.style.opacity = 0;
   el.style.transform = 'translateY(-20px)';
   done();
};

// 下滑滚动
</script>

<template>
   <div
      :class="{ 'bar-top': true, 'top-two': IsScroll, 'top-two2': !IsScroll }">
      <div class="box">
         <div class="top-box">
            <el-icon class="router">
               <Operation />
            </el-icon>

            <div
               class="title"
               @click="util.start()"
               style="
                  position: relative;
                  margin-left: 0.5em;
                  padding: 0 0.5em;
                  border-radius: 5px;
                  font-size: 1.5em;
               ">
               轻笑|tek
               <div class="tilte-line">
                  <el-icon>
                     <HomeFilled />
                  </el-icon>
               </div>
            </div>

            <div class="router-tab" v-if="Ispc">
               <div class="top-sc" @click="startpop">
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-sousuox"></use>
                  </svg>
                  <span>搜索</span>
               </div>
               <!--  -->
               <div class="top-sc" @click="util.home()">
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-a-homezhuyefangzijia"></use>
                  </svg>
                  <span>主页</span>
               </div>
               <!--  -->
               <div class="top-sc" @click="util.arg()">
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-biaoqian"></use>
                  </svg>
                  <span>标签</span>
               </div>
               <!--  -->
               <div class="top-sc" @click="util.tree()">
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-shu"></use>
                  </svg>
                  <span>留言板</span>
               </div>
               <!--  -->
               <div class="top-sc" @click="util.muisc()">
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-rengongzhinengjiqiren"></use>
                  </svg>
                  <span>GPT</span>
               </div>
               <!--  -->
               <div class="top-sc" @click="util.mine()">
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-guanyubeifen2"></use>
                  </svg>
                  <span>关于</span>
               </div>
               <!--日夜  -->
               <div class="top-sc" @click="setIsTab()">
                  <transition
                     name="fade-slide"
                     @before-enter="beforeEnter"
                     @enter="enter"
                     @leave="leave">
                     <svg v-if="IsTab" class="icon" aria-hidden="true">
                        <use xlink:href="#icon-qingbaitian"></use>
                     </svg>
                  </transition>

                  <transition
                     name="fades-slide"
                     @before-enter="beforeEnter"
                     @enter="enter"
                     @leave="leave">
                     <svg v-if="!IsTab" class="icon" aria-hidden="true">
                        <use xlink:href="#icon-tianqiicon_qingtian_heiye"></use>
                     </svg>
                  </transition>
               </div>
            </div>
            <div class="router-tab" v-else>
               <div class="top-sc">
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-sousuox"></use>
                  </svg>
               </div>

               <div class="top-sc" @click="setIsTab()">
                  <transition
                     name="fade-slide"
                     @before-enter="beforeEnter"
                     @enter="enter"
                     @leave="leave">
                     <svg v-if="IsTab" class="icon" aria-hidden="true">
                        <use xlink:href="#icon-qingbaitian"></use>
                     </svg>
                  </transition>

                  <transition
                     name="fades-slide"
                     @before-enter="beforeEnter"
                     @enter="enter"
                     @leave="leave">
                     <svg v-if="!IsTab" class="icon" aria-hidden="true">
                        <use xlink:href="#icon-tianqiicon_qingtian_heiye"></use>
                     </svg>
                  </transition>
               </div>
               <div class="top-sc" @click="handleClose()">
                  <svg class="icon" aria-hidden="true">
                     <use xlink:href="#icon-tushu"></use>
                  </svg>
               </div>
            </div>
         </div>
      </div>
   </div>

   <!-- 弹窗 -->

   <transition name="fade">
      <Pop v-if="Ispop"></Pop>
   </transition>
</template>
<style lang="scss" scoped>
//

/* 定义进入和离开动画 */
.fade-enter-active,
.fade-leave-active {
   transition: opacity 1s ease;
}

/* 定义进入开始状态和离开结束状态 */
.fade-enter-from,
.fade-leave-to {
   opacity: 0;
}

.fade-slide-enter-active,
.fade-slide-leave-active {
   transition:
      opacity 0.5s ease,
      transform 0.5s ease;
   opacity: 0;
   transform: translateY(20px);
}

.fade-slide-enter-from,
.fade-slide-leave-to {
   opacity: 0;
   transform: translateY(20px);
   transition:
      opacity 0.5s ease,
      transform 0.5s ease;
}

.fade-slides-enter-active,
.fades-slide-leave-active {
   transition:
      opacity 0.5s ease,
      transform 0.5s ease;
   opacity: 0;
   transform: translateY(20px);
}

.fades-slide-enter-from,
.fades-slide-leave-to {
   opacity: 0;
   transform: translateY(20px);
   transition:
      opacity 0.5s ease,
      transform 0.5s ease;
}

//

.bar-top {
   width: 100%;
   position: absolute;
   top: 0;
   font-family: 'MyCustomFonts', sans-serif;
   z-index: 99;

   .box {
      width: 100%;
      max-width: 1450px;
      margin: auto;

      height: 100%;

      .top-box {
         width: 95%;
         height: 100%;
         margin: auto;

         display: flex;
         align-items: center;

         .router {
            padding: 0.3em;
            border-radius: 5px;
            font-size: 1.5em;
         }

         .title {
            position: relative;

            .tilte-line {
               position: absolute;
               width: 0;
               height: 100%;
               color: #fff;
               top: 0;
               display: flex;
               justify-content: center;
               align-items: center;
               overflow: hidden;
            }
         }

         .title:hover .tilte-line {
            background: var(--color-title);
            border-radius: 5px;
            color: #fff;
            width: 100%;
         }

         .router:hover {
            background: var(--color-title);
            color: #fff;
         }

         & > div {
            line-height: 45px;
         }

         .router-tab {
            display: flex;
            justify-content: end;
            flex: 3;

            .top-sc {
               color: var(--bk-font-color);
               position: relative;
               display: flex;
               align-items: center;
               font-size: 1.4em;
               height: 40px;
               margin-left: 2em;

               & > span {
                  margin-left: 0.5em;
               }

               cursor: pointer;
            }

            .top-sc::after {
               content: '';
               position: absolute;
               bottom: 0;
               left: 0;
               height: 4px;
               width: 0;
               background: linear-gradient(90deg, #00f260, #0575e6);
               transition: width 0.5s ease-in-out;
            }

            /* 悬停时展示进度条 */
            .top-sc:hover::after {
               width: 100%;
            }
         }
      }
   }

   // @ts-ignore
   color: var(--bk-font-color);
}

.top-two {
   // @ts-ignore
   position: fixed;
   top: 0;

   height: 45px;

   background: var(--back-op-color);
}

.top-two2 {
   height: 50px;
}
</style>
