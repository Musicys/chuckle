<template>
    <div class="cunsult">
        <div><span>文章数目 :</span><span>{{ home_data.home_information.articles }}</span></div>
        <div><span>已运行时间 :</span><span>{{ home_data.home_information.days }}</span></div>
        <div><span>本站总字数 :</span><span>{{ home_data.home_information.words }}</span></div>
        <div><span>最后更新时间  :</span><span>{{ home_data.home_information.updated }}</span></div>
    </div>
</template>

<script setup lang="ts">
import { home_data  } from '@/util/home';

</script>

<style scoped>
.cunsult{
    display: flex;
    flex-direction: column;
    margin-top: 1em;
    padding: .5em;
    &>div{
        flex: 1;
        display: flex;
        justify-content: space-between;

        margin-bottom: 1em;
    }
}

</style>