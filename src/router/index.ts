// src/router/index.js

import {createRouter, createWebHistory} from 'vue-router'
import { defineAsyncComponent } from 'vue'
import { ref,Ref } from "vue"; 
export const router = createRouter({ 
  // history: createWebHashHistory(),  // hash 模式
  history: createWebHistory(),  // history 模式
  routes: [
    
    {
      path: '/',
      name: '',
      
      component: defineAsyncComponent(() => import("@/view/start/start.vue")),
      // redirect: '/login',
      meta: { title: '博客入口' },
      children:[

      ]
      
   },
 
    {
        path: '/home',
        name: 'home',
        
        component: defineAsyncComponent(() => import("@/view/home/home.vue")),
        // redirect: '/login',
        meta: { title: '博客首页' },
        children:[

        ]
        
     },
     {
      path: '/arg',
      name: 'arg',
      
      component: defineAsyncComponent(() => import("@/view/arg/arg.vue")),
      meta: { title: '标签' },
      children:[

      ]
   },
   {
    path: '/tree',
    name: 'tree',
    
    component: defineAsyncComponent(() => import("@/view/tree/tree.vue")),
    // redirect: '/login',
    
    meta: { title: '树洞' },
    children:[

    ]
 },
 {
  path: '/muisc',
  name: 'muisc',
  
  component: defineAsyncComponent(() => import("@/view/muisc/muisc.vue")),
  // redirect: '/login',
  
  meta: { title: '音乐' },
  children:[

  ]
},

 {
  path: '/mine',
  name: 'mine',
  
  component: defineAsyncComponent(() => import("@/view/mine/mine.vue")),
  // redirect: '/login',
  meta: { title: '关于我们' },
  children:[

  ]
},
//
{
  path: '/desc',
  name: '/desc',
  
  component: defineAsyncComponent(() => import("@/view/desc/desc.vue")),
  // redirect: '/login',
  meta: { title: '博文' },
  children:[

  ]
}

  ]
})



// 全局路由守卫
router.beforeEach((to, from, next)=>{

    
  if (to.meta.title) {

    Isindex.value=!Isindex.value

    setTimeout(()=>{
      Isindex.value=!Isindex.value
    },600)

    document.title = `${to.meta.title}`;


  }


  next()
})

export  const Isindex=ref(true)

router.afterEach((to, from)=>{
  // console.log(to, from)

  console.log('afterEach')
})



