<template>
   <div class="sticky-board">
      <div class="input-area">
         <textarea
            v-model="content"
            placeholder="说点什么吧..."
            maxlength="200"
            @keypress="handleEnter" />
         <button @click.stop="openSubmitModal">发布</button>
      </div>

      <div ref="notesContainer" class="notes-container">
         <div
            v-for="note in notes"
            :key="note.id"
            class="note note-enter"
            :class="[note.color, { active: activeNoteId === note.id }]"
            :style="{
               left: note.x + 'px',
               top: note.y + 'px',
               zIndex: note.zIndex,
               animationDelay: note.animationDelay + 'ms'
            }"
            @mousedown="startDrag($event, note)"
            @touchstart="startDrag($event, note)"
            @dblclick="openNoteModal(note)">
            <div class="note-header">
               <div class="note-meta">
                  <img
                     :src="getAvatar(note)"
                     :alt="note.nickname"
                     class="note-avatar" />
                  <div class="note-info">
                     <span class="note-nickname">{{ note.nickname }}</span>
                     <span class="note-email">{{ note.email }}</span>
                  </div>
               </div>
               <span class="note-time">{{ note.time }}</span>
               <button class="note-close" @click.stop="deleteNote(note.id)">
                  ×
               </button>
            </div>
            <div class="note-content">{{ note.content }}</div>
            <button class="note-reply" @click.stop="openNoteModal(note)">
               <span>回复</span>
               <span v-if="note.children && note.children.length > 0">{{
                  note.children.length
               }}</span>
            </button>
         </div>
      </div>

      <!-- 分页加载更多 -->
      <div v-if="hasMore || loading" class="pagination-area">
         <button
            v-if="hasMore"
            @click.stop="loadMore"
            :disabled="loading"
            class="load-more-btn">
            <span v-if="loading" class="loading-spinner"></span>
            <span>{{ loading ? '加载中...' : '加载更多留言' }}</span>
         </button>
         <div v-else class="no-more">
            已加载全部留言（共 {{ totalCount }} 条）
         </div>
      </div>

      <div v-if="showModal" class="modal-overlay" @click.self="closeModal">
         <div class="modal-content">
            <div class="modal-header">
               <span>填写留言信息</span>
               <span class="close-btn" @click="closeModal">
                  <el-icon><Close /></el-icon>
               </span>
            </div>
            <div class="modal-body">
               <div class="form-row">
                  <div class="form-item avatar-item">
                     <el-icon><Camera /></el-icon>
                     <div class="avatar-selector">
                        <img :src="avatarUrl" alt="头像" class="modal-avatar" />
                        <button
                           class="change-avatar-btn"
                           @click="fetchRandomAvatar">
                           <el-icon><Refresh /></el-icon>
                           换一个
                        </button>
                     </div>
                  </div>
                  <div class="form-item flex-1">
                     <el-icon><User /></el-icon>
                     <input
                        v-model="nickname"
                        type="text"
                        placeholder="昵称"
                        class="modal-input" />
                  </div>
                  <div
                     class="form-item flex-1"
                     style="display: flex; justify-content: flex-end">
                     <el-icon><MessageIcon /></el-icon>
                     <input
                        v-model="email"
                        type="email"
                        placeholder="邮箱"
                        class="modal-input" />
                  </div>
               </div>
               <div class="form-item">
                  <textarea
                     v-model="content"
                     placeholder="输入留言内容..."
                     class="modal-textarea"></textarea>
               </div>
            </div>
            <div class="modal-footer">
               <button class="modal-btn cancel" @click="closeModal">
                  取消
               </button>
               <button class="modal-btn confirm" @click="confirmSubmit">
                  提交
               </button>
            </div>
         </div>
      </div>

      <div
         v-if="showNoteModal"
         class="note-modal-overlay"
         @click.self="closeNoteModal">
         <div class="note-modal-content">
            <div class="note-modal-header">
               <div class="note-modal-meta">
                  <img
                     :src="selectedNote?.avatar"
                     :alt="selectedNote?.nickname"
                     class="note-modal-avatar" />
                  <div class="note-modal-info">
                     <span class="note-modal-nickname">{{
                        selectedNote?.nickname
                     }}</span>
                     <span class="note-modal-time">{{
                        selectedNote?.time
                     }}</span>
                  </div>
               </div>
               <button class="note-modal-close" @click="closeNoteModal">
                  <el-icon><Close /></el-icon>
               </button>
            </div>
            <div class="note-modal-body">
               <div class="note-modal-content-text">
                  {{ selectedNote?.content }}
               </div>
               <div class="note-modal-replies">
                  <div class="replies-title">回复列表</div>
                  <div
                     v-if="
                        selectedNote &&
                        selectedNote.children &&
                        selectedNote.children.length > 0
                     "
                     class="replies-list">
                     <div
                        v-for="reply in selectedNote.children"
                        :key="reply.id"
                        class="reply-item">
                        <img
                           :src="getAvatar(reply)"
                           :alt="reply.nickname"
                           class="reply-avatar" />
                        <div class="reply-content">
                           <span class="reply-nickname">{{
                              reply.nickname
                           }}</span>
                           <span class="reply-text">{{ reply.content }}</span>
                           <span class="reply-time">{{ reply.createdAt }}</span>
                        </div>
                     </div>
                  </div>
                  <div v-else class="no-replies">暂无回复</div>
               </div>
            </div>
            <div class="note-modal-footer">
               <input
                  v-model="replyInput"
                  type="text"
                  placeholder="输入回复内容..."
                  maxlength="100"
                  @keypress="handleNoteReplyEnter" />
               <button @click="submitNoteReply">发送</button>
            </div>
         </div>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, nextTick } from 'vue';
import {
   Camera,
   Refresh,
   Close,
   User,
   Message as MessageIcon
} from '@element-plus/icons-vue';
import {
   getMessageList,
   submitMessage,
   type Message,
   type PageResponse
} from '@/api/tree';
import { ElMessage } from 'element-plus';

interface Note extends Message {
   x: number;
   y: number;
   zIndex: number;
   color: string;
   time: string;
   animationDelay: number;
}

const content = ref('');
const notes = ref<Note[]>([]);
const notesContainer = ref<HTMLElement | null>(null);
const activeNoteId = ref<number | null>(null);
const showModal = ref(false);
const nickname = ref('');
const email = ref('');
const avatarUrl = ref('');
const showNoteModal = ref(false);
const selectedNote = ref<Note | null>(null);
const replyInput = ref('');

// 分页状态
const currentPage = ref(1);
const pageSize = ref(20);
const totalCount = ref(0);
const hasMore = ref(true);
const loading = ref(false);
// 存储API返回的完整数据，前端做分页展示
const allNotes = ref<Note[]>([]);

let zIndex = 1000;
let isDragging = false;
let currentNote: Note | null = null;
let startX = 0;
let startY = 0;
let startLeft = 0;
let startTop = 0;

const colors = ['note-yellow', 'note-pink', 'note-blue', 'note-green'];

const getAvatar = (note: Note) => {
   if (note.avatar && note.avatar.trim()) {
      return note.avatar;
   }
   return `https://api.dicebear.com/9.x/avataaars/svg?seed=${encodeURIComponent(note.nickname || 'guest')}`;
};

const loadNotes = async () => {
   try {
      loading.value = true;
      // API 不支持后端分页，一次拉取全部数据，前端做分页展示
      const response = await getMessageList();
      console.log('=== loadNotes ===');
      console.log('response:', response);

      let messageList: Message[] = [];

      if (response && response.data) {
         if (Array.isArray(response.data)) {
            messageList = response.data;
         } else if (response.data.records) {
            messageList = response.data.records;
         } else if (Array.isArray(response.data.data)) {
            messageList = response.data.data;
         }
      }

      totalCount.value = messageList.length;
      console.log('messageList.length:', messageList.length);

      const savedPositions = localStorage.getItem('sticky-notes-positions');
      const positions: Record<
         number,
         { x: number; y: number; zIndex: number; color: string }
      > = savedPositions ? JSON.parse(savedPositions) : {};

      const container = notesContainer.value;
      const rect = container?.getBoundingClientRect();
      const containerWidth = rect?.width || 800;
      const containerHeight = rect?.height || 600;

      allNotes.value = messageList.map((msg: Message, index: number) => {
         const saved = positions[msg.id];
         return {
            ...msg,
            x: saved?.x ?? 20 + Math.random() * (containerWidth - 220),
            y: saved?.y ?? 20 + Math.random() * (containerHeight - 140),
            zIndex: saved?.zIndex ?? zIndex++,
            color:
               saved?.color ??
               colors[Math.floor(Math.random() * colors.length)],
            time: msg.createdAt,
            animationDelay: index * 100
         };
      });

      // 显示第一页
      currentPage.value = 1;
      notes.value = allNotes.value.slice(0, pageSize.value);
      hasMore.value = allNotes.value.length > pageSize.value;
   } catch (error) {
      console.error('加载留言失败:', error);
   } finally {
      loading.value = false;
   }
};

// 加载更多（客户端分页）
const loadMore = () => {
   if (loading.value || !hasMore.value) return;
   currentPage.value++;
   const end = currentPage.value * pageSize.value;
   notes.value = allNotes.value.slice(0, end);
   hasMore.value = end < allNotes.value.length;
};

const savePositions = () => {
   const positions: Record<
      number,
      { x: number; y: number; zIndex: number; color: string }
   > = {};
   allNotes.value.forEach(note => {
      positions[note.id] = {
         x: note.x,
         y: note.y,
         zIndex: note.zIndex,
         color: note.color
      };
   });
   localStorage.setItem('sticky-notes-positions', JSON.stringify(positions));
};

const openSubmitModal = async () => {
   const savedNickname = localStorage.getItem('comment_nickname');
   const savedEmail = localStorage.getItem('comment_email');
   if (savedNickname) {
      nickname.value = savedNickname;
   }
   if (savedEmail) {
      email.value = savedEmail;
   }
   await fetchRandomAvatar();
   showModal.value = true;
};

const closeModal = () => {
   showModal.value = false;
};

const fetchRandomAvatar = async () => {
   try {
      const response = await fetch(
         'https://v2.xxapi.cn/api/head?' + Date.now()
      );
      const data = await response.json();
      if (data.code === 200 && data.data) {
         avatarUrl.value = data.data;
      }
   } catch (error) {
      console.error('获取随机头像失败:', error);
      avatarUrl.value = `https://api.dicebear.com/9.x/avataaars/svg?seed=${encodeURIComponent(nickname.value || Date.now().toString())}`;
   }
};

const confirmSubmit = async () => {
   if (!nickname.value.trim()) {
      ElMessage.warning('请输入昵称');
      return;
   }
   if (!content.value.trim()) {
      ElMessage.warning('请输入留言内容');
      return;
   }

   try {
      const { data } = await submitMessage({
         nickname: nickname.value.trim(),
         email: email.value.trim(),
         avatar: avatarUrl.value,
         content: content.value.trim()
      });

      localStorage.setItem('comment_nickname', nickname.value.trim());
      if (email.value) {
         localStorage.setItem('comment_email', email.value.trim());
      }

      const container = notesContainer.value;
      const rect = container?.getBoundingClientRect();
      const containerWidth = rect?.width || 800;
      const containerHeight = rect?.height || 600;

      const noteData: Note = {
         id: data.data,
         content: content.value.trim(),
         nickname: nickname.value.trim(),
         email: email.value.trim(),
         avatar: avatarUrl.value,
         replyToCommentId: null,
         createdAt: new Date().toLocaleString(),
         children: [],
         x: 20 + Math.random() * (containerWidth - 220),
         y: 20 + Math.random() * (containerHeight - 140),
         zIndex: zIndex++,
         color: colors[Math.floor(Math.random() * colors.length)],
         time: new Date().toLocaleString()
      };

      notes.value.push(noteData);
      allNotes.value.push(noteData);
      totalCount.value = allNotes.value.length;
      hasMore.value = notes.value.length < allNotes.value.length;
      savePositions();
      content.value = '';
      closeModal();
      ElMessage.success('留言提交成功');
   } catch (error) {
      console.error('提交留言失败:', error);
      ElMessage.error('提交失败，请稍后重试');
   }
};

const handleEnter = (e: KeyboardEvent) => {
   if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      openSubmitModal();
   }
};

const startDrag = (e: MouseEvent | TouchEvent, note: Note) => {
   isDragging = true;
   currentNote = note;
   activeNoteId.value = note.id;
   bringToFront(note.id);

   const clientX = 'touches' in e ? e.touches[0].clientX : e.clientX;
   const clientY = 'touches' in e ? e.touches[0].clientY : e.clientY;
   startX = clientX;
   startY = clientY;
   startLeft = note.x;
   startTop = note.y;

   e.preventDefault();
};

const handleMove = (e: MouseEvent | TouchEvent) => {
   if (!isDragging || !currentNote || !notesContainer.value) return;

   const clientX = 'touches' in e ? e.touches[0].clientX : e.clientX;
   const clientY = 'touches' in e ? e.touches[0].clientY : e.clientY;
   const dx = clientX - startX;
   const dy = clientY - startY;

   const rect = notesContainer.value.getBoundingClientRect();
   let newLeft = startLeft + dx;
   let newTop = startTop + dy;

   newLeft = Math.max(0, Math.min(newLeft, rect.width - 200));
   newTop = Math.max(0, Math.min(newTop, rect.height - 120));

   currentNote.x = newLeft;
   currentNote.y = newTop;
};

const handleEnd = () => {
   if (isDragging && currentNote) {
      isDragging = false;
      activeNoteId.value = null;
      currentNote = null;
      savePositions();
   }
};

const bringToFront = (id: number) => {
   const note = notes.value.find(n => n.id === id);
   if (note) {
      note.zIndex = zIndex++;
      savePositions();
   }
};

const deleteNote = (id: number) => {
   allNotes.value = allNotes.value.filter(n => n.id !== id);
   notes.value = notes.value.filter(n => n.id !== id);
   totalCount.value = allNotes.value.length;
   hasMore.value = notes.value.length < allNotes.value.length;
   savePositions();
};

const openNoteModal = (note: Note) => {
   selectedNote.value = note;
   replyInput.value = '';
   showNoteModal.value = true;
};

const closeNoteModal = () => {
   showNoteModal.value = false;
   selectedNote.value = null;
   replyInput.value = '';
};

const submitNoteReply = async () => {
   const replyContent = replyInput.value.trim();
   if (!replyContent) {
      ElMessage.warning('请输入回复内容');
      return;
   }

   if (!selectedNote.value) return;

   const savedNickname = localStorage.getItem('comment_nickname') || '';
   const savedEmail = localStorage.getItem('comment_email') || '';
   const savedAvatar = localStorage.getItem('comment_avatar') || '';

   if (!savedNickname) {
      ElMessage.warning('请先发布一条留言以设置昵称');
      return;
   }

   try {
      const { data } = await submitMessage({
         nickname: savedNickname,
         email: savedEmail,
         avatar: savedAvatar || avatarUrl.value,
         content: replyContent,
         replyToCommentId: selectedNote.value.id
      });

      const reply: Note = {
         id: data.data,
         content: replyContent,
         nickname: savedNickname,
         email: savedEmail,
         avatar: savedAvatar || avatarUrl.value,
         replyToCommentId: selectedNote.value.id,
         createdAt: new Date().toLocaleString(),
         children: [],
         x: 0,
         y: 0,
         zIndex: 0,
         color: '',
         time: new Date().toLocaleString()
      };

      selectedNote.value.children.push(reply);
      replyInput.value = '';
      ElMessage.success('回复成功');
   } catch (error) {
      console.error('回复失败:', error);
      ElMessage.error('回复失败，请稍后重试');
   }
};

const handleNoteReplyEnter = (e: KeyboardEvent) => {
   if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      submitNoteReply();
   }
};

onMounted(async () => {
   await nextTick();
   loadNotes();
   document.addEventListener('mousemove', handleMove);
   document.addEventListener('mouseup', handleEnd);
   document.addEventListener('touchmove', handleMove);
   document.addEventListener('touchend', handleEnd);
});

onBeforeUnmount(() => {
   document.removeEventListener('mousemove', handleMove);
   document.removeEventListener('mouseup', handleEnd);
   document.removeEventListener('touchmove', handleMove);
   document.removeEventListener('touchend', handleEnd);
});

defineExpose({ loadMore, hasMore, loading, totalCount, currentPage, pageSize });
</script>

<style scoped>
.sticky-board {
   width: 100%;
   min-height: 600px;
   position: relative;
   margin: auto;
}

.input-area {
   display: flex;
   gap: 15px;
   margin-bottom: 25px;
   padding: 20px;
   background: var(--cart-back-color);
   border: 1px solid var(--cart-border-color);
   border-radius: 12px;
   box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.input-area textarea {
   flex: 1;
   padding: 15px 20px;
   border: 1px solid var(--cart-border-color);
   border-radius: 8px;
   font-size: 16px;
   outline: none;
   resize: none;
   min-height: 80px;
   background: var(--back-op-color);
   color: var(--bk-font-color);
}

.input-area textarea:focus {
   border-color: var(--bk-draw-back-color);
}

.input-area button {
   padding: 15px 30px;
   background: var(--bk-draw-back-color);
   color: var(--bk-back-color);
   border: none;
   border-radius: 8px;
   cursor: pointer;
   font-size: 16px;
   white-space: nowrap;
}

.input-area button:hover {
   opacity: 0.9;
}

.notes-container {
   position: relative;
   width: 100%;
   min-height: 600px;
   background: var(--back-op-color);
   border-radius: 12px;
}

.note {
   position: absolute;
   width: 200px;
   min-height: 120px;
   padding: 10px;
   border-radius: 8px;
   box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
   cursor: move;
   user-select: none;
   transition: box-shadow 0.2s;
   display: flex;
   flex-direction: column;
   opacity: 0;
   transform: translateY(30px) scale(0.9);
}

.note-enter {
   animation: noteFadeIn 0.5s ease-out forwards;
}

@keyframes noteFadeIn {
   from {
      opacity: 0;
      transform: translateY(30px) scale(0.9);
   }
   to {
      opacity: 1;
      transform: translateY(0) scale(1);
   }
}

.note:hover {
   box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
}

.note.active {
   box-shadow: 0 12px 24px rgba(0, 0, 0, 0.25);
}

.note-header {
   display: flex;
   align-items: center;
   margin-bottom: 8px;
   gap: 6px;
}

.note-meta {
   display: flex;
   align-items: center;
   gap: 6px;
   flex: 1;
}

.note-avatar {
   width: 28px;
   height: 28px;
   border-radius: 50%;
   object-fit: cover;
   border: 2px solid rgba(0, 0, 0, 0.1);
}

.note-info {
   display: flex;
   flex-direction: column;
}

.note-nickname {
   font-size: 12px;
   font-weight: 600;
   color: #333;
}

.note-email {
   font-size: 10px;
   color: #999;
}

.note-time {
   font-size: 10px;
   color: #999;
   flex-shrink: 0;
}

.note-close {
   width: 20px;
   height: 20px;
   border: none;
   background: transparent;
   cursor: pointer;
   font-size: 14px;
   line-height: 1;
   opacity: 0.6;
   flex-shrink: 0;
}

.note-close:hover {
   opacity: 1;
}

.note-reply {
   display: flex;
   align-items: center;
   justify-content: center;
   gap: 3px;
   padding: 4px 8px;
   border: none;
   background: rgba(0, 0, 0, 0.1);
   cursor: pointer;
   font-size: 12px;
   opacity: 0.7;
   border-radius: 4px;
   transition: all 0.2s;
   margin-top: auto;
   align-self: flex-end;
}

.note-reply:hover {
   opacity: 1;
   background: rgba(0, 0, 0, 0.2);
}

.note-content {
   font-size: 13px;
   line-height: 1.5;
   word-break: break-all;
}

.note-yellow {
   background: #fff9db;
}

.note-pink {
   background: #ffd6e7;
}

.note-blue {
   background: #d6eaff;
}

.note-green {
   background: #d6ffdb;
}

/* 夜间模式便签颜色 */
[data-theme='dark'] {
   :deep(.note-yellow) {
      background: #2a2518;
   }

   :deep(.note-pink) {
      background: #2a1820;
   }

   :deep(.note-blue) {
      background: #18202a;
   }

   :deep(.note-green) {
      background: #182a1c;
   }

   :deep(.note-nickname) {
      color: var(--bk-font-color);
   }

   :deep(.note-email) {
      color: rgba(255, 255, 255, 0.6);
   }

   :deep(.note-time) {
      color: rgba(255, 255, 255, 0.6);
   }

   :deep(.note-content) {
      color: var(--bk-font-color);
   }

   :deep(.note-close) {
      color: rgba(255, 255, 255, 0.6);
   }

   :deep(.note-reply) {
      background: rgba(255, 255, 255, 0.1);
      color: rgba(255, 255, 255, 0.8);

      &:hover {
         background: rgba(255, 255, 255, 0.2);
      }
   }
}

.modal-overlay {
   position: fixed;
   top: 0;
   left: 0;
   right: 0;
   bottom: 0;
   background: rgba(0, 0, 0, 0.8);
   display: flex;
   justify-content: center;
   align-items: center;
   z-index: 9999;
   animation: fadeIn 0.3s ease-out;

   @keyframes fadeIn {
      from {
         opacity: 0;
      }
      to {
         opacity: 1;
      }
   }

   .modal-content {
      width: 70vw;
      height: 50vh;
      background: var(--mart-top-pr-back);
      border-radius: 15px;
      border: 3px solid var(--cart-border-color);
      overflow: hidden;
      animation: slideInFromReply 0.4s cubic-bezier(0.34, 1.56, 0.64, 1);

      @keyframes slideInFromReply {
         from {
            opacity: 0;
            transform: scale(0.8) translateY(20px);
         }
         to {
            opacity: 1;
            transform: scale(1) translateY(0);
         }
      }

      .modal-header {
         display: flex;
         justify-content: space-between;
         align-items: center;
         padding: 1em;
         border-bottom: 1px solid var(--cart-border-color);
         font-size: 1.2em;
         font-weight: bold;
         color: var(--bk-font-color);

         .close-btn {
            cursor: pointer;
            padding: 0.3em;
            border-radius: 50%;
            transition: background 0.3s;

            &:hover {
               background: var(--mart-commment-bot-back);
            }
         }
      }

      .modal-body {
         padding: 1.5em;

         .form-row {
            display: flex;
            align-items: center;
            gap: 1em;
            margin-bottom: 1em;
         }

         .flex-1 {
            flex: 1;
         }

         .avatar-item {
            flex-shrink: 0;
         }

         .form-item {
            display: flex;
            align-items: center;
            gap: 0.5em;
            margin-bottom: 1em;

            .modal-input {
               flex: 1;
               max-width: 180px;
               padding: 0.8em;
               border-radius: 8px;
               border: 2px solid var(--cart-border-color);
               background: var(--mart-commment-bot-back);
               color: var(--bk-font-color);
               outline: none;

               &::placeholder {
                  color: var(--cart-home-time-color);
               }

               &:focus {
                  border-color: var(--bk-draw-back-color);
               }
            }

            .modal-textarea {
               flex: 1;
               min-height: 120px;
               padding: 0.8em;
               border-radius: 8px;
               border: 2px solid var(--cart-border-color);
               background: var(--mart-commment-bot-back);
               color: var(--bk-font-color);
               outline: none;
               resize: vertical;

               &::placeholder {
                  color: var(--cart-home-time-color);
               }

               &:focus {
                  border-color: var(--bk-draw-back-color);
               }
            }

            .avatar-selector {
               flex: 1;
               display: flex;
               align-items: center;
               gap: 1em;

               .modal-avatar {
                  width: 60px;
                  height: 60px;
                  border-radius: 50%;
                  border: 2px solid var(--cart-border-color);
               }

               .change-avatar-btn {
                  display: flex;
                  align-items: center;
                  gap: 0.3em;
                  padding: 0.5em 1em;
                  border-radius: 8px;
                  border: 2px solid var(--cart-border-color);
                  background: var(--mart-commment-bot-back);
                  color: var(--bk-font-color);
                  cursor: pointer;
                  transition: all 0.3s;

                  &:hover {
                     border-color: var(--bk-draw-back-color);
                     background: var(--bk-draw-back-color);
                     color: white;
                  }
               }
            }
         }
      }

      .modal-footer {
         display: flex;
         justify-content: flex-end;
         gap: 1em;
         padding: 1em;
         border-top: 1px solid var(--cart-border-color);

         .modal-btn {
            padding: 0.6em 1.5em;
            border-radius: 8px;
            border: none;
            cursor: pointer;
            font-size: 1em;
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
               background: #61a9de;
               color: var(--bk-back-color);

               &:hover {
                  background: #f5cf51;
                  color: #000;
               }
            }
         }
      }
   }
}

.note-replies {
   margin-top: 12px;
   padding-top: 12px;
   border-top: 1px dashed rgba(0, 0, 0, 0.2);
}

.reply-item {
   display: flex;
   gap: 8px;
   padding: 8px 0;
   font-size: 13px;
}

.reply-avatar {
   width: 24px;
   height: 24px;
   border-radius: 50%;
   object-fit: cover;
   flex-shrink: 0;
}

.reply-content {
   flex: 1;
   display: flex;
   flex-direction: column;
   gap: 2px;
}

.reply-nickname {
   font-weight: 600;
   color: #555;
}

.reply-text {
   color: #444;
   word-break: break-all;
}

.reply-time {
   font-size: 10px;
   color: #999;
}

.note-modal-overlay {
   position: fixed;
   top: 0;
   left: 0;
   right: 0;
   bottom: 0;
   background: rgba(0, 0, 0, 0.8);
   display: flex;
   justify-content: center;
   align-items: center;
   z-index: 9999;
   animation: fadeIn 0.3s ease-out;

   @keyframes fadeIn {
      from {
         opacity: 0;
      }
      to {
         opacity: 1;
      }
   }

   .note-modal-content {
      width: 600px;
      max-height: 70vh;
      background: var(--cart-back-color);
      border-radius: 15px;
      border: 2px solid var(--cart-border-color);
      overflow: hidden;
      animation: slideUp 0.3s cubic-bezier(0.34, 1.56, 0.64, 1);
      display: flex;
      flex-direction: column;

      @keyframes slideUp {
         from {
            opacity: 0;
            transform: translateY(30px) scale(0.95);
         }
         to {
            opacity: 1;
            transform: translateY(0) scale(1);
         }
      }

      .note-modal-header {
         display: flex;
         justify-content: space-between;
         align-items: center;
         padding: 15px 20px;
         border-bottom: 1px solid var(--cart-border-color);
         background: var(--back-op-color);

         .note-modal-meta {
            display: flex;
            align-items: center;
            gap: 12px;
         }

         .note-modal-avatar {
            width: 48px;
            height: 48px;
            border-radius: 50%;
            object-fit: cover;
            border: 2px solid var(--cart-border-color);
         }

         .note-modal-info {
            display: flex;
            flex-direction: column;
            gap: 4px;
         }

         .note-modal-nickname {
            font-size: 16px;
            font-weight: 600;
            color: var(--bk-font-color);
         }

         .note-modal-time {
            font-size: 12px;
            color: #999;
         }

         .note-modal-close {
            display: flex;
            align-items: center;
            justify-content: center;
            width: 32px;
            height: 32px;
            border: none;
            background: transparent;
            cursor: pointer;
            border-radius: 50%;
            transition: background 0.2s;

            &:hover {
               background: rgba(0, 0, 0, 0.1);
            }
         }
      }

      .note-modal-body {
         flex: 1;
         padding: 20px;
         overflow-y: auto;

         .note-modal-content-text {
            font-size: 16px;
            line-height: 1.8;
            color: var(--bk-font-color);
            margin-bottom: 20px;
            padding-bottom: 15px;
            border-bottom: 1px solid var(--cart-border-color);
         }

         .note-modal-replies {
            .replies-title {
               font-size: 14px;
               font-weight: 600;
               color: var(--bk-font-color);
               margin-bottom: 12px;
               display: flex;
               align-items: center;
               gap: 8px;
            }

            .replies-list {
               display: flex;
               flex-direction: column;
               gap: 12px;
            }

            .no-replies {
               text-align: center;
               color: #999;
               padding: 20px;
               font-size: 14px;
            }
         }
      }

      .note-modal-footer {
         display: flex;
         gap: 10px;
         padding: 15px 20px;
         border-top: 1px solid var(--cart-border-color);
         background: var(--back-op-color);

         input {
            flex: 1;
            padding: 10px 15px;
            border: 2px solid var(--cart-border-color);
            border-radius: 8px;
            font-size: 14px;
            outline: none;
            background: var(--mart-commment-bot-back);
            color: var(--bk-font-color);

            &::placeholder {
               color: #999;
            }

            &:focus {
               border-color: var(--bk-draw-back-color);
            }
         }

         button {
            padding: 10px 24px;
            background: var(--bk-draw-back-color);
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 14px;
            white-space: nowrap;
            transition: opacity 0.2s;

            &:hover {
               opacity: 0.9;
            }
         }
      }
   }
}

.pagination-area {
   display: flex;
   justify-content: center;
   align-items: center;
   margin-top: 20px;
   padding: 15px;

   .load-more-btn {
      display: flex;
      align-items: center;
      gap: 8px;
      padding: 12px 24px;
      background: var(--bk-draw-back-color);
      color: white;
      border: none;
      border-radius: 8px;
      cursor: pointer;
      font-size: 14px;
      transition: all 0.3s;

      &:hover:not(:disabled) {
         opacity: 0.9;
         transform: translateY(-2px);
      }

      &:disabled {
         opacity: 0.6;
         cursor: not-allowed;
      }

      .loading-spinner {
         width: 16px;
         height: 16px;
         border: 2px solid rgba(255, 255, 255, 0.3);
         border-top-color: white;
         border-radius: 50%;
         animation: spin 0.8s linear infinite;
      }
   }

   .no-more {
      padding: 12px 24px;
      font-size: 14px;
      color: var(--cart-home-time-color);
      background: var(--mart-commment-bot-back);
      border-radius: 8px;
   }
}

@keyframes spin {
   to {
      transform: rotate(360deg);
   }
}
</style>
