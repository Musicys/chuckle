<template>
    <div class="box" style="width: 30px;">
      
        <div>
            <el-icon><RefreshLeft /></el-icon>
        </div>
     
        <div>
            <!-- 切换 -->
            <el-icon><Switch /></el-icon>
        </div>
        <div >
            <el-icon  class="elrote"><Setting /></el-icon>
        </div>
        <div>

            <el-icon ><Share /></el-icon>
        </div>

        <div style="font-size: .7em;">
            <div class="top" :style="{opacity:IsTop?1:0}" >
                <el-icon><Top /></el-icon>
            </div>    
            
            {{ scrollProgress+'%' }}
        
        </div>
    </div>
</template>

<script setup lang="ts">
import {scrollProgress } from "@/util/scrse"


import { watch,ref ,Ref } from 'vue';

//是否显示Top箭头
const IsTop:Ref<boolean>=ref(false)


// 使用 watch 监视 scrollProgress
watch(scrollProgress, (newValue, oldValue) => {
  if (newValue === 99) {
    console.log('scrollProgress 达到了 99%');
    // 在这里执行你需要的操作
    if(!IsTop.value)
  { 
    IsTop.value=true
  }
  }
  else
  {
    if(IsTop.value)
  {
        IsTop.value=false
  }

  }
}, { immediate: false }); // immediate: false 表示不在初始化时立即调用回调





</script>

<style lang="scss" scoped>
.box{
    right: 20px;
    display: flex;
    flex-direction: column;

    top: 70%;
    transform: translateY(-50%);
    justify-content: start;

    
    z-index: 999;
    position: fixed;


    &>div{
        cursor: pointer;
        width:25px;
        height: 25px;
        background: var( --cart-back-color);
        margin-bottom: 1em;
        padding: 1px;
        border-radius: 5px;
        font-size: 1em;
        display: flex;
        justify-content: center;
        align-items: center;
        border: 3px solid var(--cart-border-color);
        color: var( --bk-font-color);
        position: relative;
        .top{
        
            position: absolute;
            width: 100%;
            height: 100%;
            color: var( --bk-font-color);
            display: flex;
            justify-content: center;
            align-items: center;
            background: var(--div-hover-color);
            font-size: 1.5em;
            opacity: 0;
            transition: opacity .5s;
        }
     
        
    }

    &>div:hover{
        background: var(--div-hover-color);
    }

  
}

.elrote{
    animation: rotate 2s linear infinite; /* 应用动画，无限循环 */
      transform-origin: center; /* 设置旋转中心点为元素的中心 */
}
@keyframes rotate {
      0% {
        transform: rotate(0deg); /* 起始角度 */
      }
      100% {
        transform: rotate(360deg); /* 结束角度 */
      }
    }

</style>