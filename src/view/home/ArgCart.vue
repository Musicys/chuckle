<template>
    <div class="arg">
        <div :style="{'color':getRandomColor()}" v-for="i in home_data.home_arg" :key="i.id">
            {{ i.tiltle }}
            <div class="arg-cart-nuber">{{ i.number }}</div>
        </div>
    </div>

</template>

<script setup lang="ts">
import {home_data} from "@/util/home"
function getRandomColor(): string {
    const r = Math.floor(Math.random() * 256);
    const g = Math.floor(Math.random() * 256);
    const b = Math.floor(Math.random() * 256);
    return `rgb(${r}, ${g}, ${b})`;
}

</script>

<style lang="scss" scoped>
.arg{
        font-family: MyserverFonts,sans-serif;
    display: flex;
    flex-wrap: wrap;
    
    padding: 1em;
    &>div{
        cursor: pointer;
        margin-right: .5em;
        line-height: 1.2em;
        margin-bottom: .2em;
        font-size: 1.5em;
        position: relative;
        padding-right: .4em;
        padding-top: .4em;
        .arg-cart-nuber
        {
            position: absolute;
            top: -.4em;
            right: 0;
            font-size: .5em;

        }
        
    }
    &>div:hover{
       background: var(--bk-font-color);
    }

}

</style>