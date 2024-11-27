<template>
<div class="desc">
    <div class="left" v-if="Ispc">
     
</div>
<div class="right">
 
    
  
 
  
</div>
    </div>
</template>

<script setup lang="ts">
import {Ispc} from "@/util/windows"
import {ref} from "vue"
import wz from "@/static/java.md?raw";


</script>

<style lang="scss" scoped>
/* 自定义代码块背景颜色 */


.bordr{
  color:var(--bk-font-color);
    border-radius: 10px;
    background: var(--cart-back-color);
    border: 3px solid var(--cart-border-color);
    padding: 5px;
}

.desc{
    display: flex;
    .left{
        flex: 1;

    }
    .right
    {
        margin-left: 1em;
   
        flex: 3;

    }
    &>div{
        @extend .bordr;
    }
}
</style>