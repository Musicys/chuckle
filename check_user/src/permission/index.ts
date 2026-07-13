import { router } from '@/router';
import { useAppStore } from '@/store';

router.beforeEach((to, from, next) => {
   if (to.path === from.path) {
      next();
      return;
   }

   if (to.meta.title) {
      const store = useAppStore();
      store.Isindex = !store.Isindex;

      setTimeout(() => {
         store.Isindex = !store.Isindex;
      }, 600);

      document.title = `${to.meta.title}`;
   }

   next();
});

router.afterEach(() => {
   console.log('afterEach');
});
