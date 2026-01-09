<template>
   <div class="pop-zz">
      <div class="sach">
         <div class="top">
            <div>在线搜索</div>
            <div style="cursor: pointer" @click="endpop">
               <el-icon>
                  <Close />
               </el-icon>
            </div>
         </div>
         <input placeholder="搜索关键词" type="text" />

         <div
            style="
               margin: 0.5em auto;
               width: 100%;
               border-bottom: 3px dashed #49b1f5;
            "></div>

         <div class="but-nr">
            <div class="cart" v-for="i in 20">
               <div class="tile">
                  %80%9a%e7%94%a8-apiresponse)，但我试了后，响应还是缺少了除data字段外的其它字段。不过思路确实是如此，封装一个统一的装饰器，简化标注操作。
                  先定义响应的dto类型，注意文件名后缀需要是 **.dto.ts**，否则
                  swagge...%80%9a%e7%94%a8-apiresponse)，但我试了后，响应还是缺少了除data字段外的其它字段。不过思路确实是如此，封装一个统一的装饰器，简化标注操作。
                  先定义响应的dto类型，注意文件名后缀需要是 **.dto.ts**，否则
                  swagge...
               </div>

               <div class="nr">
                  %80%9a%e7%94%a8-apiresponse)，但我试了后，响应还是缺少了除data字段外的其它字段。不过思路确实是如此，封装一个统一的装饰器，简化标注操作。
                  先定义响应的dto类型，注意文件名后缀需要是 **.dto.ts**，否则
                  swagge...
               </div>
            </div>
         </div>
      </div>
   </div>
</template>

<script setup lang="ts">
import { endpop } from '@/util/com/pop';
</script>

<style lang="scss" scoped>
.bordr {
   color: var(--bk-font-color);
   border-radius: 10px;
   background: var(--cart-back-color);
   border: 3px solid var(--cart-border-color);
   padding: 5px;
}

.pop-zz {
   font-family: 'MyCustomFonts', sans-serif;
   position: absolute;
   width: 100vw;
   height: 100vh;
   background: rgba(0, 0.4, 0, 0.4);
   top: 0;
   z-index: 99999;

   .sach {
      @extend .bordr;
      width: 35%;
      min-height: 250px;
      margin: 50px auto;

      .top {
         font-size: 1.2em;
         display: flex;
         justify-content: space-between;
         align-items: center;

         color: #49b1f5;
      }

      input {
         color: black;
         width: calc(100% - 40px);
         margin: 0.5em auto;
         padding: 10px 10px;
         @extend .bordr;
         border-radius: 15px;
         transition: border 0.5s;
         border: 3px solid #49b1f5;
      }

      .but-nr {
         max-height: 350px;
         overflow-y: auto;
         overflow-x: hidden;

         &::-webkit-scrollbar-track-piece {
            background: var(--cart-back-color);
            /* 水平滚动条轨道的背景颜色 */
         }

         .cart {
            max-height: 60px;
            --l: 20px;
            margin-top: 0.5em;

            .tile {
               height: 20px;
               position: relative;

               padding-left: var(--l);
               white-space: nowrap;
               /* 防止文本换行 */
               overflow: hidden;
               /* 超出部分隐藏 */
               text-overflow: ellipsis;
               font-size: 1.2em;
               transition: color 0.3s;
               cursor: pointer;

               &:hover {
                  color: #49b1f5;
               }

               /* 超出部分用省略号表示 */
               &::after {
                  content: ' ';
                  position: absolute;
                  width: 5px;
                  height: 5px;
                  border: 3px solid #49b1f5;

                  left: 0;
                  top: 50%;
                  transform: translateY(-50%);
                  border-radius: 50%;
               }
            }

            .nr {
               padding-left: var(--l);
               max-height: 40px;
               overflow: hidden;
               margin-top: 0.3em;
            }
         }
      }
   }
}
</style>
