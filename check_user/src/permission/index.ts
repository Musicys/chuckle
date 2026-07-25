import { router } from '@/router';
import { useAppStore } from '@/store';
import { recordVisit } from '@/api/mine';

let animationTimer: ReturnType<typeof setTimeout> | null = null;

router.beforeEach((to, from, next) => {
   if (to.path === from.path) {
      next();
      return;
   }

   // 清除上一次未完成的动画定时器，防止状态混乱
   if (animationTimer) {
      clearTimeout(animationTimer);
      animationTimer = null;
   }

   if (to.meta.title) {
      const store = useAppStore();
      store.Isindex = false;

      animationTimer = setTimeout(() => {
         store.Isindex = true;
         animationTimer = null;
      }, 500);

      document.title = `${to.meta.title}`;
   }

   next();
});

router.afterEach((to) => {
   // 记录访问
   recordVisit(to.path).catch((error) => {
      console.error('记录访问失败:', error);
   });
});
