import { ref, Ref } from 'vue';
export const drawer: Ref<boolean> = ref(false);
export const handleClose: Function = (): void => {
   console.log('执行');

   drawer.value = !drawer.value;
};
