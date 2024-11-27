<template>
    <div class="page">
            <div :class="{'page-left':true,'right':Isjc,'left':!Isjc}">
             <div class="cart-xx">
              <UserCart/>



             </div>
             <div class="cart-ts">
              
               <div class="flex">
                  <div class="flex-tile"> <el-icon><Platform /></el-icon>
                    公告</div>
                  <div class="cart-arg-title-left">
                    <div></div>
                    <div></div>
                    <div></div>
                  </div>
                </div>

                <div class="cart-ts-nr">
                {{ home_data.home_tilte }}
                </div>
             </div>
             <div class="cart-gx">
              <div class="flex">
                  <div class="flex-tile"> 
                    <el-icon><PieChart /></el-icon>
                    最近跟新</div>
                  <div class="cart-arg-title-left">
                    <div></div>
                    <div></div>
                    <div></div>
                  </div>
                </div>

                <div class="cart-gx-test">
                    <div class="cart-gx-test-kp" v-for="i in home_data.home_1lates" :key="i.id">
                      <div class=""><img v-lazy="i.url" alt=""></div>
                      <div>
                      <div>{{ i.title }}</div>
                      <div>{{ i.time }}</div>
                    </div>
                    </div>
                </div>

            
             </div>
             <div class="cart-txl" >
              <div class="flex">
                  <div class="flex-tile">
                    
                    <el-icon><Histogram /></el-icon>
                    标签</div>
                  <div class="cart-arg-title-left">
                    <div></div>
                    <div></div>
                    <div></div>
                  </div>

                </div>
                <div>
                 <ArgCart></ArgCart>
                </div>
             </div>

             <div class="cart-gd" >
              <div class="flex">
                  <div class="flex-tile"> 
                    <el-icon><Edit /></el-icon>
                    归档</div>
                  <div class="cart-arg-title-left">
                    <div></div>
                    <div></div>
                    <div></div>
                  </div>
                </div>
                <div>
                  <PackCart/>
                </div>
             </div>

             <div class="cart-zx" >
              <div class="flex">
                  <div class="flex-tile"> 
                    
                    <el-icon><Promotion /></el-icon>
                    网站咨询</div>
                  <div class="cart-arg-title-left">
                    <div></div>
                    <div></div>
                    <div></div>
                  </div>
                </div>
                <div>
                  <ConsultCart></ConsultCart>
                </div>
             </div>




            </div>
            <div :class="{'page-right':true,'right':!Isjc,'left':Isjc}"  >
             <div class="cart-input">
              <div>
                <el-icon class="icons"><ChatSquare /></el-icon>
                公告
              </div>
             </div>



              <div    class="cart-jt-box"    style="border: none; background: none;">


                <div  class="jt-right"   >
                  
                  <svg class="icons" aria-hidden="true"  @click="scrollToRight">
               <use  xlink:href="#icon-youjiantou"></use>
                  </svg>
                  <svg class="icons" aria-hidden="true" @click="scrollToRight">
               <use xlink:href="#icon-youjiantou"></use>
                  </svg>

                



                </div>
                
                <div class="jt-left" >
               
             
                  <svg class="icons" aria-hidden="true" @click="scrollToLeft">
               <use xlink:href="#icon-zuojiantou"></use>
                  </svg>

                  <svg class="icons" aria-hidden="true" @click="scrollToLeft">
                   <use xlink:href="#icon-zuojiantou"></use>
                  </svg>
              



                </div>
                <div class="cart-lbt"   @wheel="handleScroll" ref="scrollableDiv"  >
             

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

             <div >
              <el-icon class="icons"><Menu /></el-icon>
             </div>

              <div class="input-arg">
                  <div style="background: #25c2fe; padding-right:5px;">首页</div>
                <div v-for="i in ArgData" :key="i.title">
                  {{ i.title }}

                  <span>{{ i.nuber }}</span>
                </div>
          
              </div>
              <div  >
                  更多  
             </div>

             </div>
             
             <Cart  @click="util.desc()" v-for=" i in Data" :data="i" :key="i.id"></Cart> 
<!-- 分页 -->
              <div style=" background:none; border: none;" class="page-but">
                  <div class="select-page">1</div>
                  <div>2</div>
                  <div>......</div>
                  <div>99</div>

                  <input type="text">
                  <div>跳转</div>
              </div>
              </div>

    </div>

</template>

<script setup lang="ts">
import { defineComponent, defineAsyncComponent,Ref ,ref,watch } from 'vue'
import { home_cart ,home_arg} from '@/util/type';
import {Isjc} from "@/util/windows";
import UserCart from "./usercart.vue";
import ArgCart from "./ArgCart.vue"
import { home_data } from "@/util/home";
import PackCart from "./PackCart.vue"
import ConsultCart from "./ConsultCart.vue"
import util from "@/util/funtion.ts"
import Cart from "./cart.vue"
  //鼠标横向滚动事件
const scrollableDiv = ref<HTMLDivElement | null>(null);




const handleScroll = (event: WheelEvent) => {
  event.preventDefault(); // 阻止默认的页面滚动行为
  event.stopPropagation(); // 阻止事件冒泡到父元素

  
  if (scrollableDiv.value) {
    scrollableDiv.value.scrollBy({
      left: event.deltaY < 0 ? -50 : 50, // 根据滚轮方向滚动
      behavior: 'smooth' // 平滑滚动
    });
  }
};


  
// })
const scrollToRight = () => {
      if (scrollableDiv.value) {
        scrollableDiv.value.scrollTo({
          left: scrollableDiv.value.scrollWidth,
          behavior: 'smooth'
        });
      }
    };

 const scrollToLeft = () => {
      if (scrollableDiv.value) {
        scrollableDiv.value.scrollTo({
          left: 0,
          behavior: 'smooth'
        });
      }
    };






const Data:Ref<home_cart[]>=ref([
  {
    id:1,
    title:"123",
    url:"https://cdn.seovx.com/d/?mom=302",
    arg:["科学","编程"],
    createdtime:new Date(),
    updatetime:new Date()
    
  },
  {
    id:2,
    title:"123",
    url:"https://cdn.seovx.com/d/?mom=302",
    arg:["科学","编程"],
    createdtime:new Date(),
    updatetime:new Date()
    
  },
  {
    id:3,
    title:"123",
    url:"https://cdn.seovx.com/d/?mom=302",
    arg:["科学","编程"],
    createdtime:new Date(),
    updatetime:new Date()
    
  },
  {
    id:4,
    title:"123",
    url:"https://cdn.seovx.com/d/?mom=302",
    arg:["科学","编程"],
    createdtime:new Date(),
    updatetime:new Date()
    
  },
  {
    id:5,
    title:"123",
    url:"https://cdn.seovx.com/d/?mom=302",
    arg:["科学","编程"],
    createdtime:new Date(),
    updatetime:new Date()
    
  },
  {
    id:6,
    title:"123",
    url:"https://cdn.seovx.com/d/?mom=302",
    arg:["科学","编程"],
    createdtime:new Date(),
    updatetime:new Date()
    
  },
  {
    id:7,
    title:"123",
    url:"https://cdn.seovx.com/d/?mom=302",
    arg:["科学","编程"],
    createdtime:new Date(),
    updatetime:new Date()
    
  },
  {
    id:8,
    title:"123",
    url:"https://cdn.seovx.com/d/?mom=302",
    arg:["科学","编程"],
    createdtime:new Date(),
    updatetime:new Date()
    
  },
  

])





//Arg数据
const ArgData:Ref<home_arg[]>=ref([

{title:"教程",nuber:4},
{title:'其它4',nuber:7},
{title:'学习笔记57',nuber:7},
{title:'Hexo15',nuber:7},
{title:'项目6',nuber:7}
])








</script>

<style lang="scss" scoped>
.flex{
  display: flex;
  justify-content: space-between;
  align-items: center;
  .flex-tile{
    display: flex;
    justify-content: center;
    align-items: center;
  }
}
.cart-arg-title-left
        { 
          display: flex;
          &>div{
            width: 10px;
            height: 10px;
            border-radius: 50%;
            margin-right: .8em;
       
          }
          &>div:nth-child(1)
        {
          background: red;
        }
          &>div:nth-child(2)
        {
          background:   #f5cf51;
        }
        &>div:nth-child(3)
        {
          background:     #5ed164;
        }

      

        }

.select-page{
  background: var(--div-hover-color);

}
.page-but{
  display: flex;
  justify-content: center;
  align-items: center;
  &>div{
    cursor: pointer;
    margin-left: 1em;
    padding: 4px 8px;
    border: 3px solid var(--cart-border-color);
    border-radius: 5px;
    background: var(--cart-back-color);
    white-space: nowrap; 
    
    
    
    &:hover{
    
        background: #06c0b4;
    }
    
  }
  &>input
    {
      
      width: 50px;
      margin-left: 1em;
    padding: 4px 8px;
    border: 3px solid var(--cart-border-color);
    border-radius: 5px;
    background: var(--cart-back-color);
    white-space: nowrap; 
    }

}
.icons{
  font-size: 1.2em;

}

.icon{
  font-size: 1em;

}
.right{
  order: 1;
}

.left{
  order: 2;
}

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
 
      overflow: hidden;
    }
    .cart-ts{
      padding:  1em;
      .cart-arg-title{
        display: flex;
        justify-content:space-between;
        align-items: center;
        color: var(--bk-font-color);
       
       
      }
      .cart-ts-nr{
        margin-top: 1em;
        line-height: 2em;
        text-align: justify;
        text-justify: inter-word;
        font-size: .9em;
      }
    }
    .cart-gx{
     
      .cart-gx-test{
        display: flex;
        flex-direction: column;
        margin: 1em 0;
        .cart-gx-test-kp{
          display: flex;
          margin-bottom: .5em;
          &>div{
              cursor: pointer;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            &>img{
              width: 80px;
              height: 70px;
              border-radius: 10px;
              margin-right:  1em;
              margin-bottom: .5em;

            }
           
            &>div{
              margin-bottom: 1em;
              font-size: .8em;
        
            }
            &>:nth-child(1)
            {
              color:var(--bk-font-color);
            }
            &>:nth-child(1):hover{
              color: #06c0b4;
            }
            &>:nth-child(2)
            {
              color:  var(--cart-home-time-color);
            }
          }
        }
      }
    }
    .cart-txl{
      
      
      
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
        width: 35px;
    
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
          width: 35px;
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
        &::-webkit-scrollbar {
          display: none;
        }
    }

  }
    .cart-input{

      height: 25px;
      display: flex;
      justify-content: space-between;
      &>div:nth-child(1)
      {
        display: flex;
        justify-content: center;
        align-items: center;
        
      }
      &>div:nth-child(2)
      {
       margin: auto 10px;
     
        height: 100%;
        &>div{
          margin-left: .5em;
        }
        
      
      }



      
      &>div:nth-child(3)
    {
      cursor: pointer;
      width: 50px;
      display: flex;
        justify-content: center;
        align-items: center;
        white-space: nowrap;

    }
    &>div:nth-child(3):hover{
        color: #25c2fe;
    }
      .input-arg{
        
        display: flex;
        justify-content: space-between;
        align-items: center;
        overflow-y: hidden;
        overflow-x: auto;
      
        &::-webkit-scrollbar {
          display: none;
        }
    


        &>div{
          cursor: pointer;
          border-radius: 5px;
          padding: 4px;
         
          transition: color .3s ;
          position: relative;
          padding-right: 15px;
          white-space: nowrap;


          &>span{
              width: 15px;
              position: absolute;
              font-size: .5em;
            
          }
          
        }
        &>div:hover {
          background: #25c2fe;
        }

      }
    }
  
    
  }


}

</style>