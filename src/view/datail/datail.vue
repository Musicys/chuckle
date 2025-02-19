<template>

      <div class="bordr page">
            <div class="cart-input">

                  <div>
                        <el-icon class="icons">
                              <Menu />
                        </el-icon>
                  </div>

                  <div class="input-arg">
                        <div style="background: #25c2fe; padding-right:5px;">首页</div>
                        <div v-for="i in ArgData" :key="i.title">
                              {{ i.title }}

                              <span>{{ i.nuber }}</span>
                        </div>

                  </div>
                  <div>
                        更多
                  </div>

            </div>
            <div class="tilte">标签 - NodeJS</div>


            <div>
                  <div class="dacat-time">2024</div>
                  <div class="dacat">


                        <div class="dacat-cart" v-for="i in 3">

                              <div class="dacat-cart-img"><img
                                          src="https://tse4-mm.cn.bing.net/th/id/OIP-C.i0PxkbYvoZKnJbU4U0vJHQHaEK?rs=1&pid=ImgDetMain"
                                          alt=""></div>
                              <div class="cart-right">
                                    <div class="taion-nr">
                                          Node-回眸[三]

                                    </div>
                                    <div>
                                          <div class="taion-nr">
                                                <svg class="icon" aria-hidden="true">
                                                      <use xlink:href="#icon-a-addtag"></use>
                                                </svg>
                                                标签
                                          </div>

                                    </div>
                              </div>
                              <div class="dacat-cart-num">2</div>
                              <div class="dacat-cart-time">2024-12-09</div>
                        </div>
                  </div>
            </div>
      </div>
</template>``

<script setup lang="ts">
import { onMounted, ref, Ref, watch } from "vue";

import { home_cart, home_arg } from '@/util/type';
import { useRoute } from 'vue-router';
// 获取当前路由对象
const route = useRoute();

// 创建一个响应式的 id 变量
const id = ref(route.query.id);

// 监听路由参数的变化
watch(() => route.query.id, (newId) => {
      id.value = newId;
});




const ArgData: Ref<home_arg[]> = ref([

      { title: "教程", nuber: 4 },
      { title: '其它4', nuber: 7 },
      { title: '学习笔记57', nuber: 7 },
      { title: 'Hexo15', nuber: 7 },
      { title: '项目6', nuber: 7 },
      { title: '学习笔记57', nuber: 7 },
      { title: 'Hexo15', nuber: 7 },
      { title: '项目6', nuber: 7 },
      { title: '学习笔记57', nuber: 7 },
      { title: 'Hexo15', nuber: 7 },
      { title: '项目6', nuber: 7 },
      { title: '学习笔记57', nuber: 7 },
      { title: 'Hexo15', nuber: 7 },
      { title: '项目6', nuber: 7 }

])


onMounted(() => {
      console.log(id.value);


})
</script>

<style lang="scss" scoped>
.bordr {
      color: var(--bk-font-color);
      border-radius: 10px;
      background: var(--cart-back-color);
      border: 3px solid var(--cart-border-color);
      padding: 5px;
}

.taion-nr:hover {
      transform: translateX(1em);
}

.taion-nr {
      transition: transform .5s;
}

.page {
      padding: 10px;

      .cart-input {

            height: 25px;
            display: flex;
            justify-content: space-between;

            &>div:nth-child(1) {
                  display: flex;
                  justify-content: center;
                  align-items: center;

            }

            &>div:nth-child(2) {
                  margin: auto 10px;

                  height: 100%;

                  &>div {
                        margin-left: .5em;
                  }


            }




            &>div:nth-child(3) {
                  cursor: pointer;
                  width: 50px;
                  display: flex;
                  justify-content: center;
                  align-items: center;
                  white-space: nowrap;

            }

            &>div:nth-child(3):hover {
                  color: #25c2fe;
            }

            .input-arg {

                  display: flex;
                  justify-content: space-between;
                  align-items: center;
                  overflow-y: hidden;
                  overflow-x: auto;

                  &::-webkit-scrollbar {
                        display: none;
                  }



                  &>div {
                        cursor: pointer;
                        border-radius: 5px;
                        padding: 4px;

                        transition: color .3s;
                        position: relative;
                        padding-right: 15px;
                        white-space: nowrap;


                        &>span {
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

      .tilte {
            font-size: 1.5em;
            margin: .5em auto;
      }

      /* 屏幕宽度大于500px且小于600px时，每行显示两个子元素 */
      @media (min-width: 800px) {
            .dacat {
                  transition: all .3s;
                  width: 100%;
                  margin: 20px auto;
                  display: grid;
                  grid-template-columns: repeat(auto-fill, minmax(500px, 1fr));
                  gap: 10px;
                  font-size: 1.5em;
            }

            .dacat-cart-num {

                  position: absolute;
                  right: 3px;
                  top: 5px;
                  font-size: 2em;
            }

            .dacat-cart-time {
                  position: absolute;
                  right: 3px;
                  bottom: 5px;
                  font-size: 1.2em;

            }
      }

      /* 屏幕宽度小于500px时，每行显示一个子元素 */
      @media (max-width: 800px) {
            .dacat {
                  transition: all .3s;
                  width: 100%;
                  margin: 20px auto;
                  display: grid;
                  grid-template-columns: repeat(auto-fill, minmax(60%, 1fr));
                  gap: 10px;
                  font-size: 1.2em;
            }

            .dacat-cart-num {

                  position: absolute;
                  right: 3px;
                  top: 5px;
                  font-size: 1em;
            }

            .dacat-cart-time {
                  position: absolute;
                  right: 3px;
                  bottom: 5px;
                  font-size: 1em;

            }
      }





      .dacat-time {
            font-size: 2em;
            margin-top: 1em;



      }


      .dacat {




            .dacat-cart {
                  display: flex;
                  height: 120px;
                  position: relative;
                  flex: 1 1 calc(50% - 20px);
                  /* 50% 减去间距 */

                  border-radius: 15px;
                  background: var(--datail-back-color);
                  padding: 5px;

                  .dacat-cart-img {

                        position: relative;
                        height: 100%;
                        border-radius: 15px;
                        margin-right: 10px;
                        overflow: hidden;


                  }

                  & img {
                        transition: .5 all;
                        position: relative;
                        height: 100%;
                        width: 100%;
                        border-radius: 15px;
                        margin-right: 10px;

                  }

                  .cart-right {
                        display: flex;
                        flex-direction: column;
                        justify-content: start;


                  }

            }

      }
}
</style>