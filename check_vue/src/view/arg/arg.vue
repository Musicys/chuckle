<template>
    <div class="page">
        <h1>标签</h1>
        <div class="arg">
            <div :style="{ 'color': getRandomColor() }" @click="util.datail(i.id)" v-for="i in home_data.home_arg"
                :key="i.id">
                {{ i.tiltle }}
                <div class="arg-cart-nuber">{{ i.number }}</div>
            </div>
            <div>

            </div>

        </div>
        <div style="width: 100%;height: 400px; 
        ">
            <Echarts v-if="IsCharts" :data="option"></Echarts>
        </div>
    </div>

</template>

<script setup lang="ts">
import { option } from "@/util/echarts"
import { home_data } from '@/util/home';
import util from "@/util/funtion"
import Echarts from '@/components/Echarts.vue';

import { onMounted, ref } from "vue"
const IsCharts = ref(false)

function getRandomColor(): string {
    const r = Math.floor(Math.random() * 256);
    const g = Math.floor(Math.random() * 256);
    const b = Math.floor(Math.random() * 256);
    return `rgb(${r}, ${g}, ${b})`;
}

onMounted(() => {
    setTimeout(() => {
        IsCharts.value = true


    }, 1000);

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

.page {
    margin-bottom: 2em;

    &>h1 {
        text-align: center;
        margin: .5em auto;

    }

    @extend .bordr;

    .arg {
        width: 100%;
        display: flex;
        flex-wrap: wrap;
        font-size: 2em;
        margin-left: 1em;


        &>div {
            cursor: pointer;
            margin-right: .5em;
            line-height: 1.2em;
            margin-bottom: .2em;
            font-size: 1.5em;
            position: relative;
            padding-right: .4em;
            padding-top: .4em;

            .arg-cart-nuber {
                position: absolute;
                top: -.4em;
                right: 0;
                font-size: .5em;

            }

        }

        &>div:hover {
            background: var(--bk-font-color);
        }
    }

}
</style>