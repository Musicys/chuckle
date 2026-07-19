import type { App } from "vue";
import {
  type RouterHistory,
  type RouteRecordRaw,
  createMemoryHistory,
  createRouter,
  createWebHashHistory,
  createWebHistory,
} from "vue-router";
import { createBuiltinVueRoutes } from "./routes/builtin";
import { createRouterGuard } from "./guard";
import BaseLayout from "@/layouts/base-layout/index.vue";

const { VITE_ROUTER_HISTORY_MODE = "history", VITE_BASE_URL } = import.meta.env;

const historyCreatorMap: Record<
  Env.RouterHistoryMode,
  (base?: string) => RouterHistory
> = {
  hash: createWebHashHistory,
  history: createWebHistory,
  memory: createMemoryHistory,
};

export const customRoutes: RouteRecordRaw[] = [
  {
    name: "article_edit",
    path: "/article/edit/:id?",
    component: BaseLayout,
    meta: {
      title: "article-edit",
      i18nKey: "route.article",
      hideInMenu: true,
      activeMenu: "article",
    },
    children: [
      {
        path: "",
        component: () => import("@/views/article/edit.vue"),
      },
    ],
  },
  {
    name: "blogger_edit",
    path: "/blogger/edit/:id?",
    component: BaseLayout,
    meta: {
      title: "blogger-edit",
      i18nKey: "route.blogger",
      hideInMenu: true,
      activeMenu: "blogger",
    },
    children: [
      {
        path: "",
        component: () => import("@/views/blogger/edit.vue"),
      },
    ],
  },
];

export const router = createRouter({
  history: historyCreatorMap[VITE_ROUTER_HISTORY_MODE](VITE_BASE_URL),
  routes: [...createBuiltinVueRoutes(), ...customRoutes],
});

/** Setup Vue Router */
export async function setupRouter(app: App) {
  app.use(router);
  createRouterGuard(router);
  await router.isReady();
}
