import { ref, onMounted, onBeforeUnmount } from 'vue';

export function useLazyVisible(rootMargin = '100px') {
   const isVisible = ref(false);
   const containerRef = ref<HTMLElement | null>(null);
   let observer: IntersectionObserver | null = null;

   onMounted(() => {
      if (containerRef.value) {
         observer = new IntersectionObserver(
            entries => {
               entries.forEach(entry => {
                  if (entry.isIntersecting) {
                     isVisible.value = true;
                     observer?.disconnect();
                  }
               });
            },
            { rootMargin }
         );
         observer.observe(containerRef.value);
      }
   });

   onBeforeUnmount(() => {
      observer?.disconnect();
   });

   return { isVisible, containerRef };
}
