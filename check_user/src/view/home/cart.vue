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
         <div class="but-bt">{{ truncateText(data.title, 8) }}</div>
         <div class="meta-row">
            <div>
               <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-guanyubeifen2"></use>
               </svg>
               {{ formatDate(data.createtime) }}
            </div>
            <div style="margin: 0 5px">|</div>
            <div>
               <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-shalou"></use>
               </svg>
               {{ formatDate(data.updatetime) }}
            </div>
         </div>

         <div class="meta-row">
            <div>
               <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-fenlei"></use>
               </svg>
               {{ data.arg }}
            </div>
            <div style="margin: 0 5px">|</div>
            <div>
               <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-bi1"></use>
               </svg>
               {{ data.fontnber }}字
            </div>

            <div style="margin: 0 5px">|</div>
            <div>
               <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-pinglun"></use>
               </svg>
               {{ data.commentCount || 0 }}
            </div>
         </div>

         <div v-if="data.tags && data.tags.length > 0" class="tags-row">
            <span
               v-for="tag in data.tags"
               :key="tag.id"
               class="tag-item"
               :style="{ backgroundColor: tag.color || '#25c2fe' }">
               {{ tag.name }}
            </span>
         </div>

         <div class="yc">{{ truncateText(data.jjdesc, 50) }}</div>
      </div>

      <div :class="!Iswart ? 'desc-po-top' : 'desc-bo-top'">
         {{ data.isNew ? '最新文章' : '文章' }}
      </div>
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

const formatDate = (dateStr: string) => {
   if (!dateStr) return '';
   const date = new Date(dateStr);
   const year = date.getFullYear();
   const month = String(date.getMonth() + 1).padStart(2, '0');
   const day = String(date.getDate()).padStart(2, '0');
   return `${year}-${month}-${day}`;
};

const truncateText = (text: string, maxLength: number) => {
   if (!text) return '';
   return text.length > maxLength ? text.slice(0, maxLength) + '...' : text;
};

onMounted(() => {
   Iswart.value = data.value.id % 2 === 0 ? false : true;

   console.log(props.data);
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
      border-radius: 5px;
      height: 40%;
      background-color: #38d0c5;
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
      border-radius: 5px;
      height: 40%;
      background-color: #38d0c5;
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
      padding-left: 10px;

      & > div {
         flex-wrap: nowrap;
         color: #858585;
         margin-bottom: 0.5em;
         display: flex;
         align-items: center;

         & > div {
            display: flex;
            align-items: center;
            flex-wrap: nowrap;
         }
      }

      .but-bt {
         font-size: 1.5em;
         color: var(--bk-font-color);
         margin-bottom: 0.8em;
         cursor: pointer;

         &:hover {
            color: #25c2fe;
         }
      }

      .meta-row {
         font-size: 0.9em;
      }

      .tags-row {
         display: flex;
         flex-wrap: wrap;
         gap: 6px;
         margin-top: 5px;
      }

      .tag-item {
         padding: 2px 8px;
         border-radius: 4px;
         font-size: 0.8em;
         color: white;
      }

      .yc {
         color: var(--bk-font-color);
         height: 0px;
         opacity: 0;
         transition:
            opacity 0.5s ease,
            height 0.5s ease;
         font-size: 0.9em;
         line-height: 1.5;
         margin-top: 5px;
      }
   }

   &:hover .yc {
      opacity: 1;
      height: 40px;
      overflow: hidden;
   }
}
</style>
