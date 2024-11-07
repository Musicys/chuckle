
<template>

<div class="cart-desc">
    <div class="desc-top" >
    <img v-lazy="data.url" alt="">
    </div>
    <div class="desc-but">
    文字信息
    </div>
</div>
</template>

<script setup lang="ts">
import { defineProps, toRefs } from 'vue';
import {  home_cart} from "@/util/type";
const props = defineProps<{
  data:home_cart
}>();
const { data} = toRefs(props);




</script>

<style scoped>

.cart-desc{
      display: flex;
      flex-wrap: wrap;
      height: 180px;
      position: relative;
      padding: 10px;
      .desc-top{
        flex: 1;
     
        height: 100%;
        &>img{
       
          width: 100%;
          height: 100%;
          border-radius:10px ;
          margin-right: 50px;
        }
      }
      .desc-but{
        flex: 1;
      }

    }
</style>
