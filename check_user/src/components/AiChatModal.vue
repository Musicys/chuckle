<template>
   <div class="ai-chat-modal" :class="{ active: visible }">
      <div class="ai-chat-mask" @click="close"></div>
      <div class="ai-chat-content">
         <div class="ai-chat-header">
            <div class="ai-chat-title">
               <div class="ai-avatar">
                  <svg
                     class="ai-icon"
                     viewBox="0 0 24 24"
                     fill="none"
                     stroke="currentColor"
                     stroke-width="2">
                     <rect x="3" y="3" width="7" height="7" rx="1" />
                     <rect x="14" y="3" width="7" height="7" rx="1" />
                     <rect x="3" y="14" width="7" height="7" rx="1" />
                     <rect x="14" y="14" width="7" height="7" rx="1" />
                  </svg>
               </div>
               <span>AI 助手</span>
            </div>
            <div class="ai-chat-header-actions">
               <button class="ai-chat-clear" @click="deleteLIST">
                  <svg
                     viewBox="0 0 24 24"
                     fill="none"
                     stroke="currentColor"
                     stroke-width="2">
                     <path d="M3 6h18" />
                     <path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6" />
                     <path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2" />
                  </svg>
               </button>
               <button class="ai-chat-close" @click="close">
                  <svg
                     viewBox="0 0 24 24"
                     fill="none"
                     stroke="currentColor"
                     stroke-width="2">
                     <line x1="18" y1="6" x2="6" y2="18" />
                     <line x1="6" y1="6" x2="18" y2="18" />
                  </svg>
               </button>
            </div>
         </div>
         <div class="ai-chat-body" ref="scrollContainer">
            <div
               v-for="(item, index) in tileList"
               :key="index"
               :class="{
                  'mine-tile': item.preoes == 1,
                  'ai-title': item.preoes == 2
               }">
               <div v-if="item.preoes == 2" class="ai-avatar-small">
                  <svg
                     viewBox="0 0 24 24"
                     fill="none"
                     stroke="currentColor"
                     stroke-width="2">
                     <rect x="3" y="3" width="7" height="7" rx="1" />
                     <rect x="14" y="3" width="7" height="7" rx="1" />
                     <rect x="3" y="14" width="7" height="7" rx="1" />
                     <rect x="14" y="14" width="7" height="7" rx="1" />
                  </svg>
               </div>
               <div class="ai-tile-nr" v-if="item.preoes == 2">
                  <MarkdownViewer :markdownContent="item.title" />
                  <div
                     v-if="item.isStreaming && !item.title"
                     class="typing-indicator">
                     <span></span>
                     <span></span>
                     <span></span>
                  </div>
               </div>
               <div class="mine-nr" v-if="item.preoes == 1">
                  <div v-if="item.preoes == 1" class="box-time">
                     {{ item.time }}
                  </div>
                  <p>{{ item.title }}</p>
               </div>
            </div>
            <div class="tile-ai" v-if="tileList.length == 0">
               <div class="log">
                  <div class="ai-avatar-large">
                     <svg
                        viewBox="0 0 24 24"
                        fill="none"
                        stroke="currentColor"
                        stroke-width="2">
                        <rect x="3" y="3" width="7" height="7" rx="1" />
                        <rect x="14" y="3" width="7" height="7" rx="1" />
                        <rect x="3" y="14" width="7" height="7" rx="1" />
                        <rect x="14" y="14" width="7" height="7" rx="1" />
                     </svg>
                  </div>
                  <div>
                     <div class="welcome-title">AI 助手</div>
                     <div class="welcome-desc">我可以帮你解答各种问题</div>
                  </div>
               </div>
            </div>
         </div>
         <div class="ai-chat-footer">
            <div class="input-wrapper">
               <svg
                  class="input-icon"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  stroke-width="2">
                  <circle cx="11" cy="11" r="8" />
                  <line x1="21" y1="21" x2="16.65" y2="16.65" />
               </svg>
               <input
                  @keyup.enter="Enters"
                  placeholder="问一问"
                  v-model="musicnr"
                  type="text" />
               <button class="send-btn" @click="Enters">
                  <svg
                     viewBox="0 0 24 24"
                     fill="none"
                     stroke="currentColor"
                     stroke-width="2">
                     <line x1="22" y1="2" x2="11" y2="13" />
                     <polygon points="22 2 15 22 11 13 2 9 22 2" />
                  </svg>
               </button>
            </div>
         </div>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch, nextTick } from 'vue';
import MarkdownViewer from '@/view/desc/Markdownview.vue';
import { tileSaiStream } from '@/htpps/ai';
import { open } from '@/util/el/tilte';
import {
   aireadlost,
   aideletelost,
   aiupdatelost
} from '@/util/localStorage/localStorage';

const props = defineProps<{
   visible: boolean;
}>();

const emit = defineEmits<{
   (e: 'close'): void;
}>();

const scrollContainer = ref<HTMLElement | null>(null);

const scrollToBottom = (behavior: 'smooth' | 'auto' = 'smooth') => {
   nextTick(() => {
      if (scrollContainer.value) {
         scrollContainer.value.scrollTo({
            top: scrollContainer.value.scrollHeight,
            behavior
         });
      }
   });
};

const ISai = ref(false);
const musicnr = ref('');
const tileList = ref<any[]>([]);
const abortController = ref<AbortController | null>(null);

const deleteLIST = () => {
   tileList.value = [];
   aideletelost();
};

const Enters = async () => {
   let str = musicnr.value;
   musicnr.value = '';

   if (!str.length || ISai.value) {
      return;
   }

   const d = new Date();
   const year = d.getFullYear();
   const month = d.getMonth() + 1;
   const date = d.getDate();
   const hours = d.getHours();
   const minutes = d.getMinutes().toString().padStart(2, '0');

   tileList.value.push({
      title: str,
      preoes: 1,
      time: `${year}-${month}-${date} ${hours}.${minutes}`
   });

   ISai.value = true;

   // 创建新的 AbortController
   abortController.value = new AbortController();

   // 添加占位的AI消息，用于流式输出
   const aiIndex =
      tileList.value.push({
         title: '',
         preoes: 2,
         isStreaming: true
      }) - 1;

   setTimeout(() => scrollToBottom(), 100);

   try {
      await tileSaiStream(str, {
         signal: abortController.value.signal,
         onChunk: chunk => {
            tileList.value[aiIndex].title += chunk;
            // 流式输出时使用 auto 行为，避免频繁滚动动画导致卡顿
            scrollToBottom('auto');
         },
         onEnd: () => {
            tileList.value[aiIndex].isStreaming = false;
            ISai.value = false;
            scrollToBottom('smooth');
            aiupdatelost({ ...tileList.value });
         },
         onError: error => {
            // 忽略 AbortError（用户主动关闭）
            if (error.name !== 'AbortError') {
               tileList.value[aiIndex].isStreaming = false;
               ISai.value = false;
               console.error('AI 请求失败:', error);
               open('请求失败，请稍后重试');
            }
         }
      });
   } catch (error) {
      // 忽略 AbortError（用户主动关闭）
      if ((error as Error).name !== 'AbortError') {
         tileList.value[aiIndex].isStreaming = false;
         ISai.value = false;
         console.error('AI 请求失败:', error);
         open('请求失败，请稍后重试');
      }
   }
};

const close = () => {
   // 中止正在进行的请求
   if (abortController.value) {
      abortController.value.abort();
      abortController.value = null;
   }
   emit('close');
};

onMounted(() => {
   tileList.value = aireadlost() || [];
});

watch(
   () => props.visible,
   val => {
      if (val) {
         setTimeout(() => scrollToBottom(), 100);
      }
   }
);
</script>

<style lang="scss" scoped>
.ai-chat-modal {
   position: fixed;
   top: 0;
   left: 0;
   right: 0;
   bottom: 0;
   z-index: 9999;
   opacity: 0;
   pointer-events: none;
   transition: opacity 0.4s cubic-bezier(0.16, 1, 0.3, 1);

   &.active {
      opacity: 1;
      pointer-events: auto;
   }

   .ai-chat-mask {
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background: rgba(0, 0, 0, 0);
      transition: background 0.4s cubic-bezier(0.16, 1, 0.3, 1);

      .active & {
         background: rgba(0, 0, 0, 0.3);
      }
   }

   .ai-chat-content {
      position: absolute;
      top: 0;
      right: -420px;
      width: 420px;
      height: 100%;
      background: #f7f6f3;
      display: flex;
      flex-direction: column;
      overflow: hidden;
      border-left: 1px solid #eaeaea;
      box-shadow: -4px 0 24px rgba(0, 0, 0, 0.08);
      transition: right 0.4s cubic-bezier(0.16, 1, 0.3, 1);

      .active & {
         right: 0;
      }
   }

   .ai-chat-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 16px 20px;
      background: #ffffff;
      border-bottom: 1px solid #eaeaea;

      .ai-chat-title {
         display: flex;
         align-items: center;
         gap: 10px;
         font-size: 15px;
         font-weight: 500;
         color: #111111;
         font-family:
            'SF Pro Display', 'Geist Sans', 'Helvetica Neue', sans-serif;

         .ai-avatar {
            width: 28px;
            height: 28px;
            background: #f7f6f3;
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
            border: 1px solid #eaeaea;

            .ai-icon {
               width: 14px;
               height: 14px;
               color: #787774;
            }
         }
      }

      .ai-chat-header-actions {
         display: flex;
         align-items: center;
         gap: 8px;

         .ai-chat-clear {
            display: flex;
            align-items: center;
            justify-content: center;
            width: 30px;
            height: 30px;
            border: none;
            background: transparent;
            cursor: pointer;
            border-radius: 6px;
            transition: all 0.2s ease;

            svg {
               color: #787774;
               width: 14px;
               height: 14px;
            }

            &:hover {
               background: #f0efed;
               svg {
                  color: #111111;
               }
            }
         }

         .ai-chat-close {
            display: flex;
            align-items: center;
            justify-content: center;
            width: 30px;
            height: 30px;
            border: none;
            background: transparent;
            cursor: pointer;
            border-radius: 6px;
            transition: all 0.2s ease;

            svg {
               color: #787774;
               width: 14px;
               height: 14px;
            }

            &:hover {
               background: #f0efed;
               svg {
                  color: #111111;
               }
            }
         }
      }
   }

   .ai-chat-body {
      flex: 1;
      overflow-y: auto;
      padding: 20px;
      background: #fbfbfa;

      &::-webkit-scrollbar {
         width: 4px;
      }

      &::-webkit-scrollbar-thumb {
         background: #d6d6d4;
         border-radius: 2px;
      }

      &::-webkit-scrollbar-track-piece {
         background: transparent;
      }
   }

   .ai-chat-footer {
      padding: 14px 20px;
      background: #ffffff;
      border-top: 1px solid #eaeaea;
   }
}

.box-time {
   font-size: 12px;
   color: #787774;
   margin-bottom: 6px;
   font-family: 'Geist Mono', 'SF Mono', monospace;
}

@keyframes fadeInUp {
   from {
      opacity: 0;
      transform: translateY(8px);
   }
   to {
      opacity: 1;
      transform: translateY(0);
   }
}

.tile-ai {
   width: 100%;
   height: 100%;
   display: flex;
   justify-content: center;
   align-items: center;
   flex-direction: column;
   padding-top: 40px;

   .log {
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 16px;
      text-align: center;

      .ai-avatar-large {
         width: 48px;
         height: 48px;
         background: #ffffff;
         border-radius: 12px;
         display: flex;
         align-items: center;
         justify-content: center;
         border: 1px solid #eaeaea;

         svg {
            width: 20px;
            height: 20px;
            color: #787774;
         }
      }

      .welcome-title {
         font-size: 16px;
         font-weight: 500;
         color: #111111;
         letter-spacing: -0.01em;
      }

      .welcome-desc {
         font-size: 13px;
         color: #787774;
         margin-top: 4px;
         line-height: 1.5;
      }
   }
}

.ai-title {
   display: flex;
   margin-top: 20px;
   animation: fadeInUp 0.4s ease-out forwards;

   .ai-avatar-small {
      width: 36px;
      height: 36px;
      background: #ffffff;
      border-radius: 8px;
      display: flex;
      align-items: center;
      justify-content: center;
      flex-shrink: 0;
      border: 1px solid #eaeaea;

      svg {
         width: 16px;
         height: 16px;
         color: #787774;
      }
   }

   .ai-tile-nr {
      background: #ffffff;
      padding: 14px 18px;
      color: #111111;
      border-radius: 0 8px 8px 8px;
      margin-left: 10px;
      display: flex;
      flex-direction: column;
      align-items: flex-start;
      border: 1px solid #eaeaea;
      max-width: calc(100% - 56px);
      line-height: 1.6;
      font-size: 15px;
   }
}

.mine-tile {
   animation: fadeInUp 0.4s ease-out forwards;
   display: flex;
   justify-content: flex-end;
   margin-top: 20px;

   & > .mine-nr {
      background: #111111;
      padding: 14px 18px;
      color: #ffffff;
      border-radius: 8px 0 8px 8px;
      border: none;
      max-width: 70%;
      line-height: 1.6;
      font-size: 15px;

      p {
         margin: 0;
      }
   }
}

.typing-indicator {
   display: flex;
   align-items: center;
   gap: 4px;
   margin-top: 8px;
   padding: 4px 0;

   span {
      width: 5px;
      height: 5px;
      background: #d6d6d4;
      border-radius: 50%;
      animation: typingBounce 1.4s infinite ease-in-out both;

      &:nth-child(1) {
         animation-delay: -0.32s;
      }
      &:nth-child(2) {
         animation-delay: -0.16s;
      }
      &:nth-child(3) {
         animation-delay: 0s;
      }
   }
}

@keyframes typingBounce {
   0%,
   80%,
   100% {
      transform: scale(0);
   }
   40% {
      transform: scale(1);
   }
}

.input-wrapper {
   display: flex;
   align-items: center;
   background: #f7f6f3;
   border-radius: 8px;
   padding: 10px 14px;
   transition: all 0.2s ease;
   border: 1px solid #eaeaea;

   &:focus-within {
      background: #ffffff;
      border-color: #111111;
   }

   .input-icon {
      color: #787774;
      margin-left: 2px;
      width: 16px;
      height: 16px;
   }

   & > input {
      flex: 1;
      padding: 8px 12px;
      border: none;
      background: transparent;
      font-size: 15px;
      outline: none;
      color: #111111;
      font-family: 'SF Pro Display', 'Geist Sans', 'Helvetica Neue', sans-serif;

      &::placeholder {
         color: #b8b7b4;
      }
   }

   .send-btn {
      width: 36px;
      height: 36px;
      background: #111111;
      border: none;
      border-radius: 6px;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      transition: all 0.2s ease;

      svg {
         color: #ffffff;
         width: 14px;
         height: 14px;
      }

      &:hover {
         background: #333333;
      }

      &:active {
         transform: scale(0.96);
      }
   }
}

@media (max-width: 768px) {
   .ai-chat-content {
      width: 100%;
      right: -100%;
   }

   .mine-tile > .mine-nr {
      max-width: 85%;
   }

   .ai-chat-body {
      padding: 16px;
   }

   .ai-chat-footer {
      padding: 12px 16px;
   }
}
</style>
