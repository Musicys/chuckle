<template>

  <div class="cart-ts">

    <div class="flex">
      <div class="flex-tile"> <el-icon>
          <Platform />
        </el-icon>
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
</template>

<script setup lang="ts">
import { home_data } from "@/util/home";

</script>

<style lang="scss" scoped>
.cart-ts {
  padding: 1em;

  .cart-arg-title {
    display: flex;
    justify-content: space-between;
    align-items: center;
    color: var(--bk-font-color);


  }

  .flex {
    display: flex;
    justify-content: space-between;
    align-items: center;

    .flex-tile {
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }

  .cart-ts-nr {
    margin-top: 1em;
    line-height: 2em;
    text-align: justify;
    text-justify: inter-word;
    font-size: .9em;
  }
}
</style>