<script setup lang="ts">
import { ref, Ref, onMounted } from 'vue';
import MarkdownViewer from '@/view/desc/Markdownview.vue';
import { tileSai } from '@/htpps/ai';
import { open } from '@/util/el/tilte';
import {
   aireadlost,
   aideletelost,
   aiupdatelost
} from '@/util/localStorage/localStorage';
//滚动到最后 默认

const scrollContainer = ref(null);

// 定义一个函数来滚动到容器底部
const scrollToBottom = () => {
   if (scrollContainer.value) {
      // 使用 scrollIntoView 或者直接设置 scrollTop
      // 方法1: 使用 scrollTo
      scrollContainer.value.scrollTo({
         top: scrollContainer.value.scrollHeight,
         behavior: 'smooth' // 平滑滚动
      });
      // 方法2: 直接设置 scrollTop
      // scrollContainer.value.scrollTop = scrollContainer.value.scrollHeight;
   }
};

//AI是否在回答
const ISai: Ref<Boolean> = ref(false);

const musicnr: Ref<string> = ref('');

const tileList: Ref<any> = ref([]);

//删除lost和本地记录
const deleteLIST: () => void = () => {
   tileList.value = [];
   aideletelost();
};
const markdownContent: Ref<string> = ref('');

const Enters: () => void = async () => {
   let str = musicnr.value;
   musicnr.value = '';
   let preoes = 1;

   if (!str.length || ISai.value) {
      return;
   } else {
      const d = new Date();
      const year = d.getFullYear();
      const month = d.getMonth() + 1; // 注意：getMonth() 返回的月份是从0开始的
      const date = d.getDate();
      const hours = d.getHours();
      const minutes = d.getMinutes().toString().padStart(2, '0');
      tileList.value.push({
         title: str,
         preoes: preoes,
         time: `${year}-${month}-${date} ${hours}.${minutes}`
      });

      ISai.value = true;
      setTimeout(() => {
         scrollToBottom();
      }, 100);
      let data = await tileSai(str);

      if (data.status != 200) {
         ISai.value = false;
         return open('停止服务，请联系后台');
      }

      let str2 = data.data.choices[0].message.content;
      if (str2.length != 0) {
         console.log(str2);

         tileList.value.push({ title: str2, preoes: 2 });
         ISai.value = false;
         setTimeout(() => {
            scrollToBottom();
         }, 500);
      } else {
         ISai.value = false;
         return open('异常，请联系后台');
      }

      aiupdatelost({ ...tileList.value });
   }
};

onMounted(() => {
   //读
   tileList.value = aireadlost() || [];
});
</script>

<template>
   <div class="page-music" style="position: relative; --mine--back2: #5ad2c9">
      <div @click="deleteLIST" class="music-but">清空缓存</div>
      <div class="box">
         <div class="box-top" ref="scrollContainer">
            <!-- 内容 -->

            <div
               v-for="(i, index) in tileList"
               :index="index"
               :class="{
                  'mine-tile': i.preoes == 1,
                  'ai-title': i.preoes == 2
               }">
               <img
                  v-if="i.preoes == 2"
                  class="img"
                  src="https://img.alicdn.com/imgextra/i1/O1CN01khO8o01EAwXZnPWhX_!!6000000000312-2-tps-144-144.png" />
               <div class="ai-tile-nr" v-if="i.preoes == 2">
                  <MarkdownViewer :markdownContent="i.title" />
               </div>

               <div class="mine-nr" v-if="i.preoes == 1">
                  <div v-if="i.preoes == 1" class="box-time">{{ i.time }}</div>
                  <p>{{ i.title }}</p>
               </div>
            </div>
            <div class="tile-ai" v-if="tileList.length == 0">
               <div class="log" style="">
                  <img
                     src="https://img.alicdn.com/imgextra/i1/O1CN01khO8o01EAwXZnPWhX_!!6000000000312-2-tps-144-144.png"
                     alt="" />
                  <div>
                     <div style="font-size: 1.5em">
                        通情、达义，你的全能AI助手
                     </div>
                     <div style="text-decoration: dashed; margin-top: 0.5em">
                        我可以帮你做这些事情，换一换
                     </div>
                  </div>
               </div>
            </div>
         </div>
         <div class="input" style="position: relative; --x: 18px">
            <el-icon
               style="position: absolute; left: var(--x); font-size: 1.4em">
               <Search />
            </el-icon>
            <input
               @keyup.enter="Enters"
               placeholder="问一问"
               v-model="musicnr"
               type="text" />
            <el-icon
               @click="Enters"
               style="
                  position: absolute;
                  right: var(--x);
                  font-size: 1.4em;
                  cursor: pointer;
               ">
               <Position />
            </el-icon>
         </div>
      </div>
   </div>
</template>

<style lang="scss" scoped>
.bordr {
   color: var(--bk-font-color);
   border-radius: 10px;
   background: var(--cart-back-color);
   border: 3px solid var(--cart-border-color);
   padding: 5px;
}

.box-time {
   font-size: 0.8em;
}

.img {
   animation: totitle var(--time) ease-out forwards;
}

@keyframes totitle {
   from {
      opacity: 0;
      transform: translateY(100%);
   }

   to {
      opacity: 1;
      transform: translateY(0%);
   }
}

.page-music {
   --time: 0.5s;
   @extend .bordr;
   width: 100%;
   height: 700px;
   display: flex;
   justify-content: center;
   align-items: center;
   overflow: hidden;

   .music-but {
      position: absolute;
      right: 0px;
      top: 0px;
      background: var(--cart-border-color);
      padding: 5px 10px;
      cursor: pointer;
      border-radius: 0 0 0 15px;
   }

   & p {
      text-align: justify;
      line-height: 30px;
   }

   .box {
      display: flex;
      flex-direction: column;
      width: 90%;
      height: 90%;
      justify-content: space-between;
      align-items: center;

      border-radius: 15px;

      .box-top {
         width: 100%;
         height: 95%;
         overflow-y: auto;

         &::-webkit-scrollbar-thumb {
            background: var(--mine--back2);
            /* 滚动条的颜色 */
         }

         &::-webkit-scrollbar-track-piece {
            background: var(--cart-back-color);
            /* 水平滚动条轨道的背景颜色 */
         }

         .tile-ai {
            width: 100%;
            height: 100%;
            display: flex;

            justify-content: start;

            .log {
               display: flex;
               margin-top: 30px;
               margin-left: 50px;

               & > img {
                  width: 50px;
                  height: 50px;
                  margin-right: 1em;
               }
            }
         }

         .ai-title {
            display: flex;
            --h: 45px;
            margin-top: 1.5em;
            margin-right: 1em;

            & img {
               width: var(--h);
               height: var(--h);
               border-radius: 50%;
            }

            .ai-tile-nr {
               background: var(--mine--back2);
               padding: 5px 20px;
               color: black;
               border-radius: 15px;
               margin-left: 1em;
               display: flex;
               justify-content: center;
               align-items: center;
               animation: totitle var(--time) ease-out forwards;
            }
         }

         .mine-tile {
            margin-right: 50px;
            animation: totitle var(--time) ease-out forwards;
            display: flex;
            justify-content: end;

            & > .mine-nr {
               background: var(--mine--back2);
               padding: 5px 20px;
               color: black;
               border-radius: 15px;
               margin-top: 1em;
            }
         }
      }

      .input {
         margin-top: 1em;
         width: 100%;
         max-height: 5%;
         display: flex;
         justify-content: center;
         align-items: center;

         & > input {
            color: black;
            width: calc(100% - 82px);
            padding: 10px 30px;
            border: 1px solid rgba($color: #000000, $alpha: 0.5);
            border-radius: 15px;
            transition: border 0.5s;
            background: var(--mine--back2);
         }

         & > input:hover {
            border: 1px solid rgba($color: #ff74ee, $alpha: 0.5);
         }
      }
   }
}
</style>
