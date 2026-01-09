import { Ref, ref } from 'vue';

export const Ispop: Ref<boolean> = ref(false);

export const startpop: () => void = () => {
   Ispop.value = true;
};

export const endpop: () => void = () => {
   Ispop.value = false;
};
