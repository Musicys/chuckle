<template>
   <div class="box" style="width: 30px">
      <div @click="refreshPage" title="刷新页面">
         <el-icon>
            <RefreshLeft />
         </el-icon>
      </div>

      <div
         @click="
            handleSidebarClick();
            showSettings = true;
         ">
         <el-icon class="elrote">
            <Setting />
         </el-icon>
      </div>
      <div @click="copyLink" title="复制链接">
         <el-icon>
            <Share />
         </el-icon>
      </div>
      <div @click="openFriendLinkModal" title="友链申请">
         <el-icon>
            <Link />
         </el-icon>
      </div>
      <div @click.stop="openAiChat" class="ai-btn" title="AI 助手">
         <el-icon> Ai </el-icon>
      </div>

      <div class="scroll-progress" @click="scrollToTop">
         <div class="top" :style="{ opacity: IsTop ? 1 : 0 }">
            <el-icon>
               <Top />
            </el-icon>
         </div>

         {{ scrollProgress + '%' }}
      </div>
   </div>

   <div
      class="settings-modal"
      :class="{ active: showSettings }"
      @click="showSettings = false">
      <div class="modal-content" @click.stop>
         <div class="modal-header">
            <span>设置</span>
            <el-icon @click="showSettings = false"><Close /></el-icon>
         </div>
         <div class="modal-body">
            <div class="setting-item" v-if="!isMobile">
               <span class="setting-label">看板娘</span>
               <div
                  class="toggle-switch"
                  :class="{ active: isLive2dVisible }"
                  @click="toggleLive2d">
                  <div class="toggle-thumb"></div>
               </div>
            </div>
            <div class="setting-item" v-else>
               <span class="setting-label">看板娘</span>
               <span class="setting-tip">移动端不可用</span>
            </div>
         </div>
      </div>
   </div>

   <!-- 友链申请弹窗 -->
   <Teleport to="body">
      <div
         v-if="showFriendLinkModal"
         class="friend-link-overlay"
         @click.self="closeFriendLinkModal">
         <div class="friend-link-modal">
            <div class="friend-link-header">
               <span>友链申请</span>
               <el-icon @click="closeFriendLinkModal"><Close /></el-icon>
            </div>
            <div class="friend-link-body">
               <div class="form-item">
                  <label>网站名称 <span class="required">*</span></label>
                  <input
                     v-model="friendLinkForm.name"
                     placeholder="例如：张三的博客"
                     maxlength="50" />
               </div>
               <div class="form-item">
                  <label>网站地址 <span class="required">*</span></label>
                  <input
                     v-model="friendLinkForm.url"
                     placeholder="例如：https://example.com"
                     maxlength="200" />
               </div>
               <div class="form-item">
                  <label>网站图标</label>
                  <input
                     v-model="friendLinkForm.avatar"
                     placeholder="图片URL（可选）"
                     maxlength="500" />
               </div>
               <div class="form-item">
                  <label>站点描述</label>
                  <textarea
                     v-model="friendLinkForm.description"
                     placeholder="简单介绍你的网站（可选）"
                     maxlength="200"
                     rows="3"></textarea>
               </div>
               <div class="form-item">
                  <label>联系邮箱</label>
                  <input
                     v-model="friendLinkForm.email"
                     placeholder="用于审核通知（可选）"
                     maxlength="100" />
               </div>
            </div>
            <div class="friend-link-footer">
               <button class="btn cancel" @click="closeFriendLinkModal">
                  取消
               </button>
               <button
                  class="btn confirm"
                  :disabled="friendLinkSubmitting"
                  @click="submitFriendLink">
                  {{ friendLinkSubmitting ? '提交中...' : '提交申请' }}
               </button>
            </div>
         </div>
      </div>
   </Teleport>
</template>

<script setup lang="ts">
import { scrollProgress } from '@/util/scrse';
import { watch, ref, Ref, onMounted, onUnmounted, reactive } from 'vue';
import { ElMessage } from 'element-plus';
import { applyFriendLink } from '@/api/start';

// 是否显示Top箭头
const IsTop: Ref<boolean> = ref(false);

// 检测是否为移动端
const isMobile = ref(
   window.innerWidth < 768 ||
      /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(
         navigator.userAgent
      )
);

// 设置弹窗
const showSettings = ref(false);
const isLive2dVisible = ref(false);
let live2dInstance: any = null;

// 监听窗口大小变化
const handleResize = () => {
   isMobile.value =
      window.innerWidth < 768 ||
      /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(
         navigator.userAgent
      );
   // 如果切换到移动端，自动关闭看板娘
   if (isMobile.value && isLive2dVisible.value) {
      isLive2dVisible.value = false;
      localStorage.setItem('live2d-visible', 'false');
      unloadLive2d();
   }
};

window.addEventListener('resize', handleResize);

// 友链申请弹窗
const showFriendLinkModal = ref(false);
const friendLinkForm = reactive({
   name: '',
   url: '',
   avatar: '',
   description: '',
   email: ''
});
const friendLinkSubmitting = ref(false);

const handleSidebarClick = () => {};

const refreshPage = () => {
   location.reload();
};

const copyLink = async () => {
   try {
      await navigator.clipboard.writeText(window.location.href);
      ElMessage.success('链接已复制到剪贴板');
   } catch {
      // 降级方案
      const textarea = document.createElement('textarea');
      textarea.value = window.location.href;
      document.body.appendChild(textarea);
      textarea.select();
      document.execCommand('copy');
      document.body.removeChild(textarea);
      ElMessage.success('链接已复制到剪贴板');
   }
};

const openFriendLinkModal = () => {
   showFriendLinkModal.value = true;
};

const closeFriendLinkModal = () => {
   showFriendLinkModal.value = false;
   friendLinkForm.name = '';
   friendLinkForm.url = '';
   friendLinkForm.avatar = '';
   friendLinkForm.description = '';
   friendLinkForm.email = '';
};

const submitFriendLink = async () => {
   if (!friendLinkForm.name.trim()) {
      ElMessage.warning('请输入网站名称');
      return;
   }
   if (!friendLinkForm.url.trim()) {
      ElMessage.warning('请输入网站地址');
      return;
   }
   friendLinkSubmitting.value = true;
   try {
      await applyFriendLink({
         name: friendLinkForm.name.trim(),
         url: friendLinkForm.url.trim(),
         avatar: friendLinkForm.avatar.trim() || undefined,
         description: friendLinkForm.description.trim() || undefined,
         email: friendLinkForm.email.trim() || undefined
      });
      ElMessage.success('友链申请已提交，等待审核');
      closeFriendLinkModal();
   } catch (error) {
      console.error('提交友链申请失败:', error);
      ElMessage.error('提交失败，请稍后重试');
   } finally {
      friendLinkSubmitting.value = false;
   }
};

const scrollToTop = () => {
   // 找到滚动容器并滚动到顶部
   const pages = document.querySelectorAll('.page');
   pages.forEach(p => {
      const el = p as HTMLElement;
      if (el.scrollHeight - el.clientHeight > 100) {
         el.scrollTo({ top: 0, behavior: 'smooth' });
      }
   });
};

const openAiChat = () => {
   // 调用全局方法打开AI弹窗
   if ((window as any).openAiChat) {
      (window as any).openAiChat();
   }
};

const loadLive2d = async () => {
   if (typeof OML2D !== 'undefined') {
      const result = await OML2D.loadOml2d({
         models: [
            {
               path: 'https://model.hacxy.cn/HK416-1-normal/model.json',
               position: [0, 60],
               scale: 0.08,
               stageStyle: {
                  height: 450
               }
            }
         ]
      });
      live2dInstance = result;
   }
};

const unloadLive2d = () => {
   console.log('=== unloadLive2d ===');

   if (live2dInstance && typeof live2dInstance.destroy === 'function') {
      live2dInstance.destroy();
      live2dInstance = null;
      console.log('destroyed live2dInstance');
   }

   const stages = document.querySelectorAll('.oml2d-stage');
   console.log('stages found:', stages.length);
   stages.forEach(stage => stage.remove());

   const live2dContainers = document.querySelectorAll(
      '[class*="live2d"], [class*="oml2d"], [id*="live2d"], [id*="oml2d"]'
   );
   console.log('live2d containers found:', live2dContainers.length);
   live2dContainers.forEach(container => container.remove());

   const canvasElements = document.querySelectorAll('canvas');
   console.log('canvas elements found:', canvasElements.length);
   canvasElements.forEach(canvas => {
      if (
         canvas.className.includes('live2d') ||
         canvas.className.includes('oml2d') ||
         canvas.id.includes('live2d') ||
         canvas.id.includes('oml2d')
      ) {
         canvas.remove();
         console.log('removed canvas');
      }
   });

   const iframeElements = document.querySelectorAll('iframe');
   console.log('iframe elements found:', iframeElements.length);
   iframeElements.forEach(iframe => {
      if (
         iframe.src.includes('live2d') ||
         iframe.src.includes('model') ||
         iframe.className.includes('live2d') ||
         iframe.className.includes('oml2d')
      ) {
         iframe.remove();
         console.log('removed iframe');
      }
   });

   const bodyChildren = document.body.children;
   const appElement = document.getElementById('app');
   for (let i = bodyChildren.length - 1; i >= 0; i--) {
      const child = bodyChildren[i];
      if (child !== appElement && child.tagName !== 'SCRIPT') {
         if (
            child.className.includes('live2d') ||
            child.className.includes('oml2d') ||
            child.className.includes('canvas') ||
            child.tagName === 'CANVAS'
         ) {
            child.remove();
            console.log('removed body child:', child.tagName, child.className);
         }
      }
   }

   const allCanvas = document.querySelectorAll('canvas');
   if (allCanvas.length > 0) {
      console.log('remaining canvas:', allCanvas.length);
      allCanvas.forEach(c =>
         console.log('canvas class:', c.className, 'id:', c.id)
      );
   }
};

const toggleLive2d = () => {
   // 移动端不允许开启
   if (isMobile.value && !isLive2dVisible.value) {
      ElMessage.warning('移动端暂不支持看板娘');
      return;
   }
   isLive2dVisible.value = !isLive2dVisible.value;
   localStorage.setItem('live2d-visible', String(isLive2dVisible.value));

   if (isLive2dVisible.value) {
      loadLive2d();
   } else {
      unloadLive2d();
   }
};

onMounted(() => {
   // 移动端自动禁用看板娘
   if (isMobile.value) {
      localStorage.setItem('live2d-visible', 'false');
      isLive2dVisible.value = false;
      return;
   }

   const saved = localStorage.getItem('live2d-visible');
   if (saved === 'true') {
      isLive2dVisible.value = true;
      loadLive2d();
   }
});

// 使用 watch 监视 scrollProgress，立即执行
watch(
   scrollProgress,
   newValue => {
      console.log('[Monitor] scrollProgress changed:', newValue);
      if (newValue >= 99) {
         if (!IsTop.value) {
            IsTop.value = true;
         }
      } else {
         if (IsTop.value) {
            IsTop.value = false;
         }
      }
   },
   { immediate: true }
);

// 强制更新一次滚动进度
onMounted(() => {
   if (scrollProgress.value >= 99) {
      IsTop.value = true;
   }
});

// 清理事件监听
onUnmounted(() => {
   window.removeEventListener('resize', handleResize);
});
</script>

<style lang="scss" scoped>
.box {
   right: 20px;
   display: flex;
   flex-direction: column;

   top: 50%;
   transform: translateY(-50%);
   justify-content: start;

   z-index: 999;
   position: fixed;

   & > div {
      cursor: pointer;
      width: 25px;
      height: 25px;
      background: var(--cart-back-color);
      margin-bottom: 1em;
      padding: 1px;
      border-radius: 5px;
      font-size: 1em;
      display: flex;
      justify-content: center;
      align-items: center;
      border: 3px solid var(--cart-border-color);
      color: var(--bk-font-color);
      position: relative;

      .top {
         position: absolute;
         width: 100%;
         height: 100%;
         color: var(--bk-font-color);
         display: flex;
         justify-content: center;
         align-items: center;
         background: var(--div-hover-color);
         font-size: 1.5em;
         opacity: 0;
         transition: opacity 0.5s;
      }
   }

   & > div:hover {
      background: var(--div-hover-color);
   }

   .ai-btn {
      /* 与其他按钮保持一致的样式 */
   }
}

.elrote {
   animation: rotate 2s linear infinite;
   /* 应用动画，无限循环 */
   transform-origin: center;
   /* 设置旋转中心点为元素的中心 */
}

@keyframes rotate {
   0% {
      transform: rotate(0deg);
      /* 起始角度 */
   }

   100% {
      transform: rotate(360deg);
      /* 结束角度 */
   }
}

@keyframes aiPulse {
   0%,
   100% {
      box-shadow: 0 0 0 0 rgba(102, 126, 234, 0.4);
   }
   50% {
      box-shadow: 0 0 0 10px rgba(102, 126, 234, 0);
   }
}

.settings-modal {
   position: fixed;
   top: 0;
   left: 0;
   width: 100%;
   height: 100%;
   background: rgba(0, 0, 0, 0.5);
   display: flex;
   justify-content: center;
   align-items: center;
   opacity: 0;
   pointer-events: none;
   transition: opacity 0.3s;
   z-index: 2000;
}

.settings-modal.active {
   opacity: 1;
   pointer-events: auto;
}

.modal-content {
   background: white;
   border-radius: 12px;
   width: 320px;
   overflow: hidden;
   transform: translateY(20px);
   transition: transform 0.3s;
}

.settings-modal.active .modal-content {
   transform: translateY(0);
}

.modal-header {
   display: flex;
   justify-content: space-between;
   align-items: center;
   padding: 16px 20px;
   border-bottom: 1px solid #eee;
   font-size: 18px;
   font-weight: 600;
   color: #333;
}

.modal-header el-icon {
   cursor: pointer;
   color: #999;
   transition: color 0.3s;
}

.modal-header el-icon:hover {
   color: #333;
}

.modal-body {
   padding: 20px;
}

.setting-item {
   display: flex;
   justify-content: space-between;
   align-items: center;
}

.setting-label {
   font-size: 16px;
   color: #333;
}

.toggle-switch {
   width: 44px;
   height: 24px;
   background: #ddd;
   border-radius: 12px;
   position: relative;
   cursor: pointer;
   transition: background 0.3s;
}

.toggle-switch.active {
   background: var(--bk-draw-back-color);
}

.toggle-thumb {
   width: 20px;
   height: 20px;
   background: white;
   border-radius: 50%;
   position: absolute;
   top: 2px;
   left: 2px;
   transition: transform 0.3s;
   box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.toggle-switch.active .toggle-thumb {
   transform: translateX(20px);
}

.setting-tip {
   font-size: 14px;
   color: #999;
   font-style: italic;
}

/* 友链申请弹窗 */
.friend-link-overlay {
   position: fixed;
   top: 0;
   left: 0;
   width: 100%;
   height: 100%;
   background: rgba(0, 0, 0, 0.5);
   display: flex;
   justify-content: center;
   align-items: center;
   z-index: 3000;
}

.friend-link-modal {
   background: var(--mart-top-pr-back);
   border-radius: 12px;
   width: 420px;
   max-width: 90vw;
   max-height: 80vh;
   overflow-y: auto;
   border: 1px solid var(--cart-border-color);
   box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
}

.friend-link-header {
   display: flex;
   justify-content: space-between;
   align-items: center;
   padding: 16px 20px;
   border-bottom: 1px solid var(--cart-border-color);
   font-size: 18px;
   font-weight: 600;
   color: var(--bk-font-color);

   .el-icon {
      cursor: pointer;
      color: var(--cart-home-time-color);
      transition: color 0.3s;

      &:hover {
         color: var(--bk-font-color);
      }
   }
}

.friend-link-body {
   padding: 20px;

   .form-item {
      margin-bottom: 16px;

      label {
         display: block;
         margin-bottom: 6px;
         font-size: 14px;
         color: var(--bk-font-color);

         .required {
            color: #e74c3c;
         }
      }

      input,
      textarea {
         width: 100%;
         padding: 10px 12px;
         border: 2px solid var(--cart-border-color);
         border-radius: 8px;
         font-size: 14px;
         outline: none;
         background: var(--mart-commment-bot-back);
         color: var(--bk-font-color);
         box-sizing: border-box;

         &::placeholder {
            color: var(--cart-home-time-color);
         }

         &:focus {
            border-color: var(--bk-draw-back-color);
         }
      }

      textarea {
         resize: vertical;
         min-height: 60px;
      }
   }
}

.friend-link-footer {
   display: flex;
   justify-content: flex-end;
   gap: 12px;
   padding: 16px 20px;
   border-top: 1px solid var(--cart-border-color);

   .btn {
      padding: 10px 24px;
      border-radius: 8px;
      border: none;
      font-size: 14px;
      cursor: pointer;
      transition: all 0.3s;

      &.cancel {
         background: var(--mart-commment-bot-back);
         color: var(--bk-font-color);
         border: 2px solid var(--cart-border-color);

         &:hover {
            background: var(--cart-border-color);
         }
      }

      &.confirm {
         background: var(--bk-draw-back-color);
         color: white;

         &:hover:not(:disabled) {
            opacity: 0.9;
         }

         &:disabled {
            opacity: 0.6;
            cursor: not-allowed;
         }
      }
   }
}
</style>
