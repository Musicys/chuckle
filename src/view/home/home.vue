<template>
    <div class="page">
            <div class="page-left">
             <div class="cart-xx">
              个人信息
             </div>
             <div class="cart-ts">
              公告
             </div>
             <div class="cart-gx">
              最近跟新
             </div>
             <div class="cart-txl" v-for=" i in 4">
              标签
             </div>









            </div>
            <div class="page-right">
             <div class="cart-input">
              公告
             </div>



              <div class="cart-jt-box" style="border: none; background: none;">


                <div class="jt-right">
                    >>
                </div>
                <div class="jt-left">
                  <<
                </div>
                <div class="cart-lbt"  @scroll="" >
             

             <div class="lbt-left">
               <div class="lbt-left-but">
                 <span>  归档</span>
               </div>
               <div class="lbt-left-but">
                   <span>  年份</span>


               </div>
             </div>
            

               <div class="cart-lbt-cartx" v-for="i in 4">
                 <div class="right-top-box">
                       <img src="../../static/t.jpg" alt="">
                       <div class="zz">
                           为啥发表文章的说明
                       </div>
                   </div>      
                   <div class="right-buttom-box">
                     文章标题
                   </div>  
               </div>
         </div>  


              
              </div>
             


             <div class="cart-input">
              公告
             </div>
             
             <Cart v-for=" i in Data" :data="i" :key="i.id.toString()"></Cart> 


              </div>
    </div>

</template>

<script setup lang="ts">
import { defineComponent, defineAsyncComponent,Ref ,ref,watch } from 'vue'
import { home_cart } from '@/util/type';
import {Isjc} from "@/util/windows";

//监视Ispc 交换 left right顺序

watch(Isjc,(newVal, oldVal) => {
  console.log(`count changed from ${oldVal} to ${newVal}`);
  // 在这里执行你需要的逻辑\
  console.log(newVal);
  


  
})





const Cart = defineAsyncComponent(() => import('./cart.vue'))
 
defineComponent({
  components: { Cart }
})

const Data:Ref<home_cart[]>=ref([
  {
    id:1,
    title:"123",
    url:"https://cdn.seovx.com/d/?mom=302",
    arg:["科学","编程"],
    createdtime:new Date(),
    updatetime:new Date()
    
  }

])









</script>

<style lang="scss" scoped>
.bordr{
  color:var(--bk-font-color);
    border-radius: 10px;
    background: var(--cart-back-color);
    border: 3px solid var(--cart-border-color);
    padding: 5px;
}

.page{
  transition: all .3s;
    width: 100%; 
    display: flex;
    margin: auto;
    flex-wrap: wrap;
    
  &>div{
    transition: all 1s;
  }
  .page-left{
    transition: all .3s;
    min-width: 250px;
    flex: 1;
    margin-bottom: 50px;
    margin-right: 10px;
    &>div{
      @extend .bordr;
    }
    &>div:not(:first-child){
      margin-top: 10px;
    }
    .cart-xx{
      height: 340px;
    }
    .cart-ts{
      height: 200px;
    }
    .cart-gx{
      height: 250px;
    }
    .cart-txl{
      height: 300px;
      
      
    }
  }
  .page-right{
    margin-bottom: 50px;
    flex: 4;
    min-width:250px;
    height: 100%;
    margin-right: 10px;


    &>div:not(:first-child){
      margin-top: 10px;
    }
    &>div{
      @extend .bordr;
    }
    .cart-jt-box{
      position: relative;
      .jt-right{
        position: absolute;
        right: 0;
        color: white;
        z-index: 3;
        width: 30px;
        font-size: 1.2em;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
      }
      .jt-left{

          position: absolute;
          left: 0;
          color: white;
          z-index: 3;
        width: 30px;
        font-size: 1.2em;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
      }


    .cart-lbt{
        height: 200px;
        display: flex;
      overflow-y: hidden;
      position: relative;
 


   
        .lbt-left{
          height: 100%;
          min-width: 210px;
         
          display: flex;
          flex-direction: column;

          &>div{
            flex: 1;
           display: flex;
           justify-content: center;
           align-items: center;
           
          border-radius: 10px;
          font-size: 1.2em;
            color: white;
          }

          .lbt-left-but:nth-child(1){
              
             
              background-image: linear-gradient(to bottom, #1aa0a8, rgba(255, 255, 255, 0.2)), url('../../static/t.jpg');
           }
          
           .lbt-left-but:nth-child(2){
            background-image: linear-gradient(to bottom, rgba(29, 143, 205, 0.6), rgba(255, 255, 255, 0.2)), url('../../static/t.jpg');
              margin-top: 10px;
           }
          
          }
      



          .cart-lbt-cartx{
            transition: all .5s;
         
            flex: 1;
            max-width:250px;
            min-width: 250px;
            
            margin-left: 10px;
            @extend .bordr;
            display: flex;
            flex-direction: column;
            padding: 0;
            overflow: hidden;
            .right-top-box{
                height: 60%;
                position: relative;
           
                overflow: hidden;
                &>img{
                  position: absolute;
                  top: 0;
                  left: 0;
                  width: 100%;
                  height: 100%;
                  z-index: 1;
                }
                .zz{
                  position: absolute;
                  color: white;
                  position: absolute;
                  padding: 5px;
                  width: 100%;
                  font-size: 1.2em;
                
                  height: 100%;
                  background: rgba(0, 0, 0, 0.4);
               
                 

                }
                
            }
            .right-buttom-box{
              font-size: 1.1em;
              flex: 1;
              padding: 5px;
            }
            &:hover .right-buttom-box{
              background: #1792d7;
              color: white;
            }
            &:hover .zz{
              z-index: 2;
            }

          }

      

/* 隐藏滚动条 */
        // &::-webkit-scrollbar {
        //   display: none;
        // }
    }

  }
    .cart-input{

      height: 20px;
   
      
    }
  
    
  }


}

</style>