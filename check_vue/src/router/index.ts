// src/router/index.js

import { createRouter, createWebHistory } from 'vue-router';

import { ref, Ref } from 'vue';

import routes from './router';
export const router = createRouter({
   // history: createWebHashHistory(),  // hash 模式
   history: createWebHistory(), // history 模式
   routes: routes as any
});

// 全局路由守卫
router.beforeEach((to, from, next) => {
   // @ts-ignore
   if (to.path == from.path) {
      next();
   } else {
      if (to.meta.title) {
         Isindex.value = !Isindex.value;

         setTimeout(() => {
            Isindex.value = !Isindex.value;
         }, 600);

         document.title = `${to.meta.title}`;
      }
   }

   next();
});

export const Isindex = ref(true);

router.afterEach((to, from) => {
   // console.log(to, from)

   console.log('afterEach');
});
