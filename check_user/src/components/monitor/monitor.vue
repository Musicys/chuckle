<template>
   <div class="box" style="width: 30px">
      <div @click="handleSidebarClick">
         <el-icon>
            <RefreshLeft />
         </el-icon>
      </div>
      <div @click="handleSidebarClick">
         <!-- 切换 -->
         <el-icon>
            <Switch />
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
      <div @click="handleSidebarClick">
         <el-icon>
            <Share />
         </el-icon>
      </div>
      <div @click="showAiChat = true" class="ai-btn">
         <el-icon> Ai </el-icon>
      </div>

      <div style="font-size: 0.7em">
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
            <div class="setting-item">
               <span class="setting-label">看板娘</span>
               <div
                  class="toggle-switch"
                  :class="{ active: isLive2dVisible }"
                  @click="toggleLive2d">
                  <div class="toggle-thumb"></div>
               </div>
            </div>
         </div>
      </div>
   </div>

   <AiChatModal :visible="showAiChat" @close="showAiChat = false" />
</template>

<script setup lang="ts">
import { scrollProgress } from '@/util/scrse';
import { watch, ref, Ref, onMounted } from 'vue';
import AiChatModal from '../AiChatModal.vue';

//是否显示Top箭头
const IsTop: Ref<boolean> = ref(false);

// 设置弹窗
const showSettings = ref(false);
const isLive2dVisible = ref(false);
const showAiChat = ref(false);
let live2dInstance: any = null;

const handleSidebarClick = () => {
   if (showAiChat.value) {
      showAiChat.value = false;
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
   isLive2dVisible.value = !isLive2dVisible.value;
   localStorage.setItem('live2d-visible', String(isLive2dVisible.value));

   if (isLive2dVisible.value) {
      loadLive2d();
   } else {
      unloadLive2d();
   }
};

onMounted(() => {
   const saved = localStorage.getItem('live2d-visible');
   if (saved === 'true') {
      isLive2dVisible.value = true;
      loadLive2d();
   }
});

// 使用 watch 监视 scrollProgress
watch(
   scrollProgress,
   (newValue, oldValue) => {
      if (newValue >= 99) {
         console.log('scrollProgress 达到了 99%');
         // 在这里执行你需要的操作
         if (!IsTop.value) {
            IsTop.value = true;
         }
      } else {
         if (IsTop.value) {
            IsTop.value = false;
         }
      }
   },
   { immediate: false }
); // immediate: false 表示不在初始化时立即调用回调
</script>

<style lang="scss" scoped>
.box {
   right: 20px;
   display: flex;
   flex-direction: column;

   top: 70%;
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
</style>
