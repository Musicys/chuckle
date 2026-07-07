<template>
   <div class="pack">
      <div v-for="i in home_data.home_archiving" :key="i.id">
         <div class="box">
            <div>
               {{ i.year }}
            </div>
            <div>{{ i.number }} <span style="font-size: 0.8em">篇</span></div>
         </div>
      </div>
   </div>
</template>

<script setup lang="ts">
import { home_data } from '@/util/home';
</script>

<style scoped>
.pack {
   display: flex;
   flex-wrap: wrap;
   margin-top: 1em;

   & > div {
      border: 1px solid white;
      border-radius: 10px;
      margin-right: 1.2em;
      padding: 0.4em 3em 0em 0.2em;
      margin-bottom: 1em;
      width: 100%;

      .box {
         transition: all 0.3s;

         & > div:nth-child(1) {
            font-size: 1em;
            margin-bottom: 0.5em;
         }

         & > div:nth-child(2) {
            font-size: 1.4em;
         }

         &:hover {
            transform: translateX(10px);
         }
      }
   }
}
</style>
