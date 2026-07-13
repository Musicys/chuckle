import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useAppStore = defineStore('app', () => {
   const Isindex = ref(true);

   // 抽屉状态 (from util/slot.ts)
   const drawer = ref(false);
   function handleClose() {
      drawer.value = !drawer.value;
   }

   // 弹窗状态 (from util/com/pop.ts)
   const Ispop = ref(false);
   function startpop() {
      Ispop.value = true;
   }
   function endpop() {
      Ispop.value = false;
   }

   return {
      Isindex,
      drawer,
      handleClose,
      Ispop,
      startpop,
      endpop
   };
});
