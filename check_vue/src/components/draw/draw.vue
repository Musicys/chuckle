<script lang="ts" setup>
import { drawer } from "@/util/slot";
import { music } from "@/util/user";
import util from "@/util/funtion"


</script>


<template>

  <el-drawer v-model="drawer" :size="300" class="father" :with-header="false">
    <div class="box" :style="{ backgroundImage: `url(${music.drawURL})` }">
      <img :src="music.userImg" alt="">
      <span>{{ music.username }}</span>
      <span style="font-size: 1.5em;">{{ music.drawtilte }}</span>
      <div style="width: 100%;  display: flex; margin-top: .5em;">
        <div class="flex-center" style="flex: 1;  text-align: center;">
          <span>文章</span>
          <span>15</span>
        </div>
        <div class="flex-center" style="flex: 1;  text-align: center;">
          <span>标签</span>
          <span>15</span>
        </div>
        <div class="flex-center" style="flex: 1;  text-align: center;">
          <span>分类</span>
          <span>15</span>
        </div>
      </div>


      <el-button style="width: 80%;   border-radius: 15px; margin-top: 1em;" type="primary">

        <el-icon>
          <Platform />
        </el-icon>

        Follow Me</el-button>


      <div class="grid">

        <div class="icons">
          <a :href="music.GitHubUrl" target="_blank">
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#icon-github"></use>
            </svg>

          </a>
        </div>
        <div class="icons">
          <a :href="music.CsdnUrl" target="_blank">

            <svg class="icon" aria-hidden="true">
              <use xlink:href="#icon-csdn"></use>
            </svg>
          </a>

        </div>
        <div class="icons">
          <a :href="music.QqUrl" target="_blank">
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#icon-QQ"></use>
            </svg>
          </a>

        </div>
        <div class="icons">
          <a :href="music.Bilbilurl" target="_blank">
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#icon-Bzhan"></use>
            </svg>
          </a>

        </div>




      </div>




    </div>
    <div class="box2">
      <!-- daph -->
      <el-menu active-text-color="none" background-color="var(--back-darw-liner)" class="el-menu-vertical-demo"
        default-active="1" text-color="var(  --bk-font-color)">


        <el-menu-item index="1" @click="util.home">
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-a-homezhuyefangzijia"></use>
          </svg>
          <span>主页</span>
        </el-menu-item>
        <el-menu-item index="2" @click="util.arg">
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-biaoqian"></use>
          </svg>
          <span>标签</span>
        </el-menu-item>
        <el-menu-item index="3" @click="util.tree">
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-shu"></use>
          </svg>
          <span>留言板</span>
        </el-menu-item>
        <el-menu-item index="4" @click="util.muisc">
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-rengongzhinengjiqiren"></use>
          </svg>
          <span>GPT</span>
        </el-menu-item>
        <el-menu-item index="5" @click="util.mine">
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-guanyubeifen2"></use>
          </svg>
          <span>关于</span>
        </el-menu-item>
      </el-menu>
    </div>
  </el-drawer>
</template>


<style lang="scss" scoped>
.icon {
  margin-right: 2em;
}

.flex-center {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 50px;
  justify-content: center;

  margin-top: 1em;

  &>span {
    font-family: 'MyCustomFonts', sans-serif;
    font-weight: bold;
  }

  &>span:nth-child(2) {
    margin-top: .5em;
  }

}

.flex-center:nth-child(1) {
  border-right: .5px solid var(--bk-font-color);
}

.flex-center:nth-child(3) {
  border-left: .5px solid var(--bk-font-color);

}

.father {
  display: flex;
  flex-direction: column;

  font-family: 'MyCustomFont', sans-serif;

  color: var(--back-op-color);

  .box {
    width: 100%;
    height: 50%;
    border-bottom: 5px dashed var(--bk-draw-back-color);
    display: flex;
    justify-content: start;
    align-items: center;
    flex-direction: column;
    min-height: 400px;

    img {
      width: 100px;
      border-radius: 25%;
      margin-top: .5em;
    }

    &>span {
      margin-top: .5em;
      font-size: 2em;

      font-family: 'MyCustomFont', sans-serif;
    }


  }

  .box2 {
    flex: 1;
    width: 100%;
    background: var(--back-darw-liner);
    height: 60%;
  }

  .grid {
    display: grid;
    grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
    gap: 15px;
    height: 40px;
    margin-top: 1em;
    width: 80%;
    max-width: 100%;
    overflow: hidden;

    &>.icons {
      margin-top: 1em;
      width: 100%;
      height: 30px;
    }
  }
}
</style>