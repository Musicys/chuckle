<template>
   <div class="cart-desc" :class="!Ispc ? '' : Iswart ? 'hs-left' : 'hs-right'">
      <div
         :class="{
            'desc-top': true,
            right: Iswart && Ispc,
            left: !Iswart && Ispc
         }">
         <img v-lazy="data.url" alt="" />
      </div>
      <div
         :class="{
            'desc-but': true,
            right: !Iswart && Ispc,
            left: Iswart && Ispc
         }">
         <div class="but-bt">标题{{ data.title }}</div>
         <div>
            <div>
               <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-guanyubeifen2"></use>
               </svg>

               发表于{{ data.createtime }}
            </div>
            <div style="margin: 0 5px">|</div>
            <div>
               <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-shalou"></use>
               </svg>

               更新于{{ data.updatetime }}
            </div>
         </div>

         <div>
            <div>
               <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-bijiben"></use>
               </svg>
               笔记
            </div>
            <div style="margin: 0 5px">|</div>
            <div>
               <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-fenlei"></use>
               </svg>
               分类|{{ data.arg }}
            </div>
            <div style="margin: 0 5px">|</div>
            <div>
               <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-bi1"></use>
               </svg>
               字数
            </div>
         </div>

         <div class="yc">{{ data.jjdesc }}</div>
      </div>

      <div :class="!Iswart ? 'desc-po-top' : 'desc-bo-top'">最新文章</div>
   </div>
</template>

<script setup lang="ts">
import { defineProps, toRefs, Ref, ref, onMounted } from 'vue';

import { Ispc } from '@/util/windows';
const Iswart: Ref<boolean> = ref(true);

const props = defineProps<{
   data: any;
}>();
const { data } = toRefs(props);
const cart_test = ref({
   //是否置顶
   IsTop: false,
   //是否最新创建
   IscateTime: false
});

const IsNotYC: Ref<boolean> = ref(true);
console.log('==>', { ...data.value });

onMounted(() => {
   Iswart.value = data.value.id % 2 === 0 ? false : true;
});
</script>

<style lang="scss" scoped>
$r: 8px;

.icon {
   font-size: 0.8em;
}

.hs-left {
   &::after {
      content: '';
      position: absolute;
      top: 50%;
      bottom: 0;
      right: $r;
      width: 5px;
      /* 竖线的宽度 */
      border-radius: 5px;
      height: 40%;
      background-color: #38d0c5;
      /* 竖线的颜色 */
      transform: translateY(-50%);
   }
}

.hs-right {
   &::after {
      content: '';
      position: absolute;
      top: 50%;
      bottom: 0;
      left: $r;
      width: 5px;
      /* 竖线的宽度 */
      border-radius: 5px;
      height: 40%;
      background-color: #38d0c5;
      /* 竖线的颜色 */
      transform: translateY(-50%);
   }
}

.right {
   order: 1;
}

.left {
   order: 2;
}

.cart-desc {
   display: flex;
   flex-wrap: wrap;

   position: relative;
   padding: 10px;

   .desc-po-top {
      position: absolute;
      top: 0;
      left: 10px;
      font-size: 1.2em;

      color: white;
      background: #49b1f5;
      padding: 5px 10px;
      border-radius: 0 0px 10px 10px;
   }

   .desc-bo-top {
      position: absolute;
      top: 0;
      right: 10px;
      font-size: 1.2em;

      color: white;
      background: #49b1f5;
      padding: 5px 10px;
      border-radius: 0 0px 10px 10px;
   }

   .desc-top {
      flex: 1;

      height: 100%;
      min-width: 200px;

      & > img {
         width: 100%;
         height: 180px;
         border-radius: 10px;
         margin-right: 50px;
      }
   }

   .desc-but {
      flex: 1;

      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      min-width: 200px;

      & > div {
         height: 20px;
         flex-wrap: nowrap;
         color: #858585;
         margin-bottom: 0.5em;
         display: flex;
         justify-content: center;
         align-items: center;

         & > div {
            display: flex;
            justify-content: center;
            align-items: center;
            flex-wrap: nowrap;
         }
      }

      .but-bt {
         font-size: 1.5em;

         color: var(--bk-font-color);
      }

      .yc {
         color: var(--bk-font-color);
         height: 0px;
         opacity: 0;
         transition:
            opacity 0.5s ease,
            height 0.5s ease;
      }
   }

   &:hover .yc {
      opacity: 1;
      height: 20px;
   }

   &:active .yc {
      opacity: 1;
      height: 20px;
   }
}
</style>
