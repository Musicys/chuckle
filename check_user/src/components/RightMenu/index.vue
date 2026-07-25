<template>
   <Teleport to="body">
      <!-- 遮罩层 -->
      <div
         v-if="visible"
         class="right-menu-mask"
         @click="hideMenu"
         @contextmenu.prevent></div>

      <!-- 右键菜单 -->
      <Transition name="fade">
         <div
            v-if="visible"
            class="right-menu-wrapper"
            :style="{ top: `${y}px`, left: `${x}px` }"
            @contextmenu.prevent>
            <el-menu
               default-active="1"
               mode="vertical"
               class="right-menu"
               background-color="transparent"
               text-color="inherit"
               active-text-color="#49b1f5">
               <!-- 导航操作 -->
               <el-menu-item index="back" @click="handleMenuItemClick('back')">
                  <el-icon><ArrowLeft /></el-icon>
                  <span>返回上一页</span>
               </el-menu-item>
               <el-menu-item
                  index="forward"
                  @click="handleMenuItemClick('forward')">
                  <el-icon><ArrowRight /></el-icon>
                  <span>前进下一页</span>
               </el-menu-item>
               <el-menu-item
                  index="refresh"
                  @click="handleMenuItemClick('refresh')">
                  <el-icon><Refresh /></el-icon>
                  <span>刷新页面</span>
               </el-menu-item>
               <el-menu-item index="home" @click="handleMenuItemClick('home')">
                  <el-icon><Back /></el-icon>
                  <span>回到首页</span>
               </el-menu-item>
               <el-menu-item index="top" @click="handleMenuItemClick('top')">
                  <el-icon><ArrowUp /></el-icon>
                  <span>回到顶部</span>
               </el-menu-item>

               <!-- 分隔线 -->
               <el-divider class="menu-divider"></el-divider>

               <!-- 站点导航 -->
               <el-menu-item
                  index="archive"
                  @click="handleMenuItemClick('archive')">
                  <el-icon><Folder /></el-icon>
                  <span>文章归档</span>
               </el-menu-item>
               <el-menu-item
                  index="category"
                  @click="handleMenuItemClick('category')">
                  <el-icon><Folder /></el-icon>
                  <span>文章分类</span>
               </el-menu-item>
               <el-menu-item index="tags" @click="handleMenuItemClick('tags')">
                  <el-icon><Collection /></el-icon>
                  <span>文章标签</span>
               </el-menu-item>
               <el-menu-item
                  index="random"
                  @click="handleMenuItemClick('random')">
                  <el-icon><Compass /></el-icon>
                  <span>随便逛逛</span>
               </el-menu-item>

               <!-- 分隔线 -->
               <el-divider class="menu-divider"></el-divider>

               <!-- 文本操作 -->
               <el-menu-item
                  v-if="hasText"
                  index="copy"
                  @click="handleMenuItemClick('copy')">
                  <el-icon><DocumentCopy /></el-icon>
                  <span>复制文本</span>
               </el-menu-item>
               <el-menu-item
                  v-if="!hasText"
                  index="selectAll"
                  @click="handleMenuItemClick('selectAll')">
                  <el-icon><Check /></el-icon>
                  <span>全选文本</span>
               </el-menu-item>

               <!-- 分隔线 -->
               <el-divider v-if="hasLink" class="menu-divider"></el-divider>

               <!-- 链接操作 -->
               <el-menu-item
                  v-if="hasLink"
                  index="openLink"
                  @click="handleMenuItemClick('openLink')">
                  <el-icon><Share /></el-icon>
                  <span>新标签页打开</span>
               </el-menu-item>
               <el-menu-item
                  v-if="hasLink"
                  index="copyLink"
                  @click="handleMenuItemClick('copyLink')">
                  <el-icon><LinkIcon /></el-icon>
                  <span>复制链接</span>
               </el-menu-item>

               <!-- 分隔线 -->
               <el-divider class="menu-divider"></el-divider>

               <!-- 页面操作 -->
               <el-menu-item
                  index="search"
                  @click="handleMenuItemClick('search')">
                  <el-icon><Search /></el-icon>
                  <span>搜索</span>
               </el-menu-item>
               <el-menu-item
                  index="darkMode"
                  @click="handleMenuItemClick('darkMode')">
                  <el-icon><Moon /></el-icon>
                  <span>切换模式</span>
               </el-menu-item>
               <el-menu-item
                  index="print"
                  @click="handleMenuItemClick('print')">
                  <el-icon><Printer /></el-icon>
                  <span>打印页面</span>
               </el-menu-item>
            </el-menu>
         </div>
      </Transition>
   </Teleport>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useAppStore } from '@/store';
import {
   ArrowLeft,
   ArrowRight,
   Refresh,
   Back,
   ArrowUp,
   Folder,
   Collection,
   Compass,
   DocumentCopy,
   Check,
   Search,
   Moon,
   Printer,
   Share,
   Link as LinkIcon
} from '@element-plus/icons-vue';

const router = useRouter();
const appStore = useAppStore();

// 菜单状态
const visible = ref(false);
const x = ref(0);
const y = ref(0);

// 当前点击的链接
const currentLink = ref<HTMLAnchorElement | null>(null);

// 获取选中的文本
const getSelectedText = () => {
   return window.getSelection()?.toString() || '';
};

// 是否有选中文本
const hasText = computed(() => getSelectedText().length > 0);

// 是否有链接
const hasLink = computed(() => currentLink.value !== null);

// 获取右键点击的链接元素
const getTargetLink = (e: MouseEvent) => {
   let target = e.target as HTMLElement;
   while (target) {
      if (target.tagName === 'A' && target.href) {
         return target;
      }
      target = target.parentElement as HTMLElement;
   }
   return null;
};

// 菜单操作映射
const menuActions: Record<string, () => void> = {
   back: () => history.back(),
   forward: () => history.forward(),
   refresh: () => location.reload(),
   home: () => router.push('/home'),
   top: () => window.scrollTo({ top: 0, behavior: 'smooth' }),
   archive: () => router.push('/arg'),
   category: () => router.push('/tree'),
   tags: () => router.push('/arg'),
   random: () => router.push('/random'),
   copy: async () => {
      const text = getSelectedText();
      if (text) {
         try {
            await navigator.clipboard.writeText(text);
         } catch {
            const textArea = document.createElement('textarea');
            textArea.value = text;
            document.body.appendChild(textArea);
            textArea.select();
            document.execCommand('copy');
            document.body.removeChild(textArea);
         }
      }
   },
   selectAll: () => document.execCommand('selectAll'),
   openLink: () => {
      if (currentLink.value) {
         window.open(currentLink.value.href, '_blank');
      }
   },
   copyLink: async () => {
      if (currentLink.value) {
         try {
            await navigator.clipboard.writeText(currentLink.value.href);
         } catch {
            const textArea = document.createElement('textarea');
            textArea.value = currentLink.value.href;
            document.body.appendChild(textArea);
            textArea.select();
            document.execCommand('copy');
            document.body.removeChild(textArea);
         }
      }
   },
   search: () => appStore.startpop(),
   darkMode: () => {
      const currentTheme = document.documentElement.getAttribute('data-theme');
      const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
      document.documentElement.setAttribute('data-theme', newTheme);
   },
   print: () => window.print()
};

// 处理菜单项点击
const handleMenuItemClick = (index: string) => {
   hideMenu();
   const action = menuActions[index];
   if (action) {
      action();
   }
};

// 显示菜单
const showMenu = (e: MouseEvent) => {
   e.preventDefault();
   currentLink.value = getTargetLink(e);

   let menuX = e.clientX;
   let menuY = e.clientY;

   const menuWidth = 180;
   const menuHeight = 500;

   if (menuX + menuWidth > window.innerWidth) {
      menuX = window.innerWidth - menuWidth;
   }
   if (menuY + menuHeight > window.innerHeight) {
      menuY = window.innerHeight - menuHeight;
   }

   x.value = menuX;
   y.value = menuY;
   visible.value = true;
};

// 隐藏菜单
const hideMenu = () => {
   visible.value = false;
   currentLink.value = null;
};

onMounted(() => {
   document.addEventListener('contextmenu', showMenu);
   document.addEventListener('click', hideMenu);
});

onUnmounted(() => {
   document.removeEventListener('contextmenu', showMenu);
   document.removeEventListener('click', hideMenu);
});
</script>

<style lang="scss" scoped>
.right-menu-mask {
   position: fixed;
   top: 0;
   left: 0;
   width: 100%;
   height: 100%;
   z-index: 9998;
   background: transparent;
   cursor: url('/src/static/cur/原神.cur'), auto !important;
}

.right-menu-wrapper {
   position: fixed;
   min-width: 180px;
   z-index: 9999;
   cursor: url('/src/static/cur/原神.cur'), auto !important;
}

.right-menu {
   border-radius: 12px !important;
   box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
   padding: 4px 0 !important;
   overflow: hidden;
}

/* 白天模式 */
.right-menu {
   background-color: rgba(255, 255, 255, 0.95) !important;
   border: 1px solid rgba(0, 0, 0, 0.1) !important;
}

.right-menu :deep(.el-menu-item) {
   color: #333 !important;
   height: 36px !important;
   line-height: 36px !important;
   padding: 0 16px !important;
   margin: 0 !important;
   cursor: url('/src/static/cur/原神.cur'), auto !important;
}

.right-menu :deep(.el-menu-item:hover) {
   background-color: rgba(73, 177, 245, 0.1) !important;
   cursor: url('/src/static/cur/原神-后台.cur'), pointer !important;
}

.right-menu :deep(.el-menu-item.is-active) {
   background-color: rgba(73, 177, 245, 0.15) !important;
   color: #49b1f5 !important;
   cursor: url('/src/static/cur/原神-帮助.cur'), pointer !important;
}

.right-menu :deep(.el-divider) {
   margin: 4px 0 !important;
   background-color: rgba(0, 0, 0, 0.06) !important;
}

/* 深色模式 */
[data-theme='dark'] {
   .right-menu {
      background-color: rgba(20, 20, 25, 0.95) !important;
      border-color: rgba(255, 255, 255, 0.1) !important;
      box-shadow: 0 4px 20px rgba(0, 0, 0, 0.4) !important;
   }

   .right-menu :deep(.el-menu-item) {
      color: #fff !important;
   }

   .right-menu :deep(.el-menu-item:hover) {
      background-color: rgba(73, 177, 245, 0.1) !important;
   }

   .right-menu :deep(.el-menu-item.is-active) {
      background-color: rgba(73, 177, 245, 0.2) !important;
      color: #49b1f5 !important;
   }

   .right-menu :deep(.el-divider) {
      background-color: rgba(255, 255, 255, 0.06) !important;
   }
}

.fade-enter-active,
.fade-leave-active {
   transition:
      opacity 0.15s ease,
      transform 0.15s ease;
}

.fade-enter-from,
.fade-leave-to {
   opacity: 0;
   transform: scale(0.95) translateY(5px);
}
</style>
