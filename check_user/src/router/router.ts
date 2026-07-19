import { defineAsyncComponent } from 'vue';

export default [
   {
      path: '/',
      name: '',
      component: defineAsyncComponent(() => import('@/view/start/index.vue')),
      meta: { title: '博客入口' },
      children: []
   },

   {
      path: '/home',
      name: 'home',

      component: defineAsyncComponent(() => import('@/view/home/index.vue')),
      // redirect: '/login',
      meta: { title: '博客首页' },
      children: []
   },
   {
      path: '/arg',
      name: 'arg',

      component: defineAsyncComponent(() => import('@/view/arg/index.vue')),
      meta: { title: '标签' },
      children: []
   },
   {
      path: '/tree',
      name: 'tree',
      component: defineAsyncComponent(() => import('@/view/tree/index.vue')),
      meta: { title: '留言板' },
      children: []
   },
   {
      path: '/muisc',
      name: 'muisc',

      component: defineAsyncComponent(() => import('@/view/muisc/index.vue')),
      // redirect: '/login',

      meta: { title: '问问' },
      children: []
   },

   {
      path: '/mine',
      name: 'mine',

      component: defineAsyncComponent(() => import('@/view/mine/index.vue')),
      // redirect: '/login',
      meta: { title: '关于我们' },
      children: []
   },
   //
   {
      path: '/desc/:id?',
      name: '/desc',

      component: defineAsyncComponent(() => import('@/view/desc/index.vue')),
      // redirect: '/login',
      meta: { title: '博文' },
      children: []
   },
   {
      path: '/datail', // 子路由路径
      name: 'argDetail', // 子路由名称
      component: defineAsyncComponent(() => import('@/view/datail/index.vue')), // 子路由组件
      meta: { title: '详情' } // 子路由元信息
   }
];
