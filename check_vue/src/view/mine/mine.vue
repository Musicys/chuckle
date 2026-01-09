<script setup lang="ts">
import MapContainer from '../../components/MapContainer.vue';
import { ref, Ref } from 'vue';
</script>

<template>
   <div class="mine-page">
      <div class="top">
         <!-- left -->
         <div class="top-futer-txt">
            <div style="--l: 20px; --s: 4s; margin-left: var(--l)">
               <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-yuanshen-wa"></use>
               </svg>

               <span>神里绫华的狗</span>
            </div>
            <div style="--l: 10px; --s: 4.4s">
               <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-CVV"></use>
               </svg>
               <span>专业复制粘贴用户</span>
            </div>
            <div style="--l: 10px; --s: 4.5s">
               <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-csdn"></use>
               </svg>
               <span> CSDN高级访问用户</span>
            </div>
            <div style="--l: 20px; --s: 5s; margin-left: var(--l)">
               <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-github"></use>
               </svg>
               <span> github高级访问用户</span>
            </div>
         </div>
         <!-- center -->
         <div class="box">
            <div class="img"></div>

            <img
               src="https://tse3-mm.cn.bing.net/th/id/OIP-C.U1UG7FN50qzrntU8he3s9wAAAA?rs=1&pid=ImgDetMain"
               alt="" />
         </div>
         <!-- right -->
         <div class="top-futer-txt02">
            <div style="--l: 20px; --s: 5s">
               <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-CVV"></use>
               </svg>
               <span>英魂之刃王者选手</span>
            </div>
            <div style="--l: 20px; --s: 4s; margin-left: var(--l)">
               <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-CVV"></use>
               </svg>
               <span> 毕业失业的人员之一</span>
            </div>
            <div style="--l: 20px; --s: 5s; margin-left: var(--l)">
               <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-daxuesheng"></use>
               </svg>
               <span> 自认天赋型游戏玩家</span>
            </div>
            <div style="--l: 20px; --s: 4s">
               <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-rengongzhinengjiqiren"></use>
               </svg>
               <span> GPT深度依赖者</span>
            </div>
         </div>
      </div>

      <!-- guanyu benz -->
      <h1 style="text-align: center; margin: 0.5em auto">关于本站</h1>

      <!-- cart -->

      <div class="grid">
         <div
            style="
               grid-column: 1 / 4;
               display: flex;
               flex-direction: column;
               justify-content: center;
               background: linear-gradient(to right, #3039cf, #1e7ece);
               color: wheat;
               opacity: 0.7;
               font-size: 1.1em;
            ">
            <span>这里~这里~</span>
            <span>网名 轻笑Chuckle</span>
            <span>有了备案就是 实名制上网啦QAQ</span>
            <span>是一只还在求学路上的 大学生、个人博主</span>
         </div>
         <div
            style="
               grid-column: 4/ 6;
               background: #1d1b26;
               background: var(--mine-back-zq-back);
               display: flex;
               flex-direction: column;
               justify-content: center;
            ">
            <span style="font-size: 0.8em">追求</span>
            <span style="font-size: 1.8em">源于</span>
            <span style="font-size: 1.8em">乐趣·兴趣</span>
            <div class="dom">
               <span>程序</span>
            </div>
         </div>

         <div style="grid-column: 1/3; grid-row: 2/4; color: white">
            <div style="margin-top: 1em; margin-left: 1em">数据</div>
            <div style="font-size: 2em; margin-left: 1em">访问统计</div>

            <div class="dom-grid">
               <div>
                  <span>今日访问</span>
                  <span>1321</span>
               </div>

               <div>
                  <span>访问人数</span>
                  <span>1321</span>
               </div>
               <div>
                  <span>本月访问</span>
                  <span>1321</span>
               </div>
               <div>
                  <span> 总访问量</span>
                  <span>1321</span>
               </div>

               <div></div>
            </div>
         </div>
         <div style="grid-column: 3/6; overflow: hidden">
            <MapContainer></MapContainer>
         </div>
         <div
            style="
               grid-column: 3/6;
               overflow: hidden;
               background: var(--mine-back-zq-back);
               display: flex;
               justify-content: space-around;
               align-items: center;
               height: 100%;
            "
            class="xinx">
            <div><span>生于</span><span style="color: #43a6c6">2003</span></div>
            <div>
               <span>专业</span
               ><span style="color: #c69043">计算机科学与技术</span>
            </div>
            <div>
               <span>现在职业</span><span style="color: #b04fe6">大三学生</span>
            </div>
         </div>
      </div>

      <div class="flex-cart">
         <div style="color: white">
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
         <div>
            <div class="cart-tilte">
               <span>爱好游戏</span>
               <span>原神</span>
            </div>

            <img
               src="https://upload-bbs.miyoushe.com/upload/2022/11/28/17949827/2266190b99bece98ebc3bfb05212ca01_5809411558282465606.jpg?x-oss-process=image//resize,s_600/quality,q_80/auto-orient,0/interlace,1/format,jpg"
               style="width: 100%; height: 100%"
               alt="" />
         </div>
         <div>
            <div class="cart-tilte">
               <span>爱好游戏</span>
               <span>永杰无间</span>
            </div>

            <img
               src="https://web-uns.oss-cn-beijing.aliyuncs.com/use.png"
               style="width: 100%; height: 100%"
               alt="" />
         </div>
         <div>
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
      height: 500px;
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
         & div {
            animation: tranformtop var(--s) linear infinite;
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

         & div {
            animation: tranformtop var(--s) linear infinite;
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
}
</style>
