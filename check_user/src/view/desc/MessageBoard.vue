<template>
   <div class="message-board">
      <hr />
      <div class="title">
         <span>评论 </span>
         <el-icon><Comment /></el-icon>
      </div>

      <div class="comment-input">
         <CommentBox @submit="handleSubmit" />
      </div>

      <div class="comments-section">
         <div class="title">
            <span>{{ comments.length }} 评论</span>
         </div>

         <div v-if="comments.length === 0" class="empty-comments">
            <el-icon><Message /></el-icon>
            <span>暂无评论，快来发表第一条评论吧！</span>
         </div>

         <div class="comment-list">
            <div
               v-for="comment in comments"
               :key="comment.id"
               class="comment-item">
               <div class="comment-avatar">
                  <img :src="getAvatar(comment)" alt="头像" />
               </div>
               <div class="comment-content">
                  <div class="comment-header">
                     <span class="comment-nickname">{{
                        comment.nickname
                     }}</span>
                     <span class="comment-time">{{ comment.createdAt }}</span>
                  </div>
                  <div class="comment-body">{{ comment.content }}</div>
                  <div class="comment-actions">
                     <span class="reply-btn" @click="openReplyModal(comment)">
                        <el-icon><ChatDotRound /></el-icon>
                        回复
                     </span>
                  </div>

                  <div
                     v-if="comment.children && comment.children.length > 0"
                     class="children-comments">
                     <div
                        v-for="child in comment.children"
                        :key="child.id"
                        class="child-comment">
                        <div class="child-avatar">
                           <img :src="getAvatar(child)" alt="头像" />
                        </div>
                        <div class="child-content">
                           <div class="child-header">
                              <span class="child-nickname">{{
                                 child.nickname
                              }}</span>
                              <span class="child-time">{{
                                 child.createdAt
                              }}</span>
                           </div>
                           <div class="child-body">
                              <span class="reply-to"
                                 >@{{ comment.nickname }}：</span
                              >
                              {{ child.content }}
                           </div>
                           <div class="child-actions">
                              <span
                                 class="reply-btn"
                                 @click="openReplyModal(child)">
                                 <el-icon><ChatDotRound /></el-icon>
                                 回复
                              </span>
                           </div>
                        </div>
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </div>

      <div v-if="showModal" class="modal-overlay" @click.self="closeModal">
         <div class="modal-content">
            <div class="modal-header">
               <span>{{ isReply ? '回复评论' : '填写评论信息' }}</span>
               <span class="close-btn" @click="closeModal">
                  <el-icon><Close /></el-icon>
               </span>
            </div>
            <div class="modal-body">
               <div v-if="isReply && replyTarget" class="reply-target">
                  <el-icon><Message /></el-icon>
                  <span>回复 @{{ replyTarget.nickname }}</span>
               </div>
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
                     <el-icon><Message /></el-icon>
                     <input
                        v-model="email"
                        type="email"
                        placeholder="邮箱"
                        class="modal-input" />
                  </div>
               </div>
               <div class="form-item">
                  <textarea
                     v-model="pendingContent"
                     placeholder="输入评论内容..."
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
   </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch, computed } from 'vue';
import {
   Comment,
   Message,
   ChatDotRound,
   Close,
   User,
   Camera,
   Refresh
} from '@element-plus/icons-vue';
import CommentBox from './CommentBox.vue';
import { fetchComments, Comment as CommentType } from '@/api/desc';
import { ElMessage } from 'element-plus';

const props = defineProps<{
   articleId: number;
}>();

const emit = defineEmits<{
   (
      e: 'submit',
      data: {
         nickname: string;
         email?: string;
         content: string;
         avatar?: string;
      }
   ): void;
   (
      e: 'reply',
      data: {
         nickname: string;
         email?: string;
         content: string;
         avatar?: string;
         replyToCommentId: number;
      }
   ): void;
}>();

const comments = ref<CommentType[]>([]);
const showModal = ref(false);
const pendingContent = ref('');
const pendingReplyId = ref<number | null>(null);
const replyTarget = ref<CommentType | null>(null);
const nickname = ref('');
const email = ref('');
const avatarUrl = ref('');

const isReply = computed(() => pendingReplyId.value !== null);

const getAvatar = (comment: CommentType) => {
   if (comment.avatar && comment.avatar.trim()) {
      return comment.avatar;
   }
   return `https://api.dicebear.com/9.x/avataaars/svg?seed=${encodeURIComponent(comment.nickname || 'guest')}`;
};

const loadComments = async () => {
   try {
      const result = await fetchComments(props.articleId);
      if (result && result.code === 0 && result.data) {
         comments.value = result.data;
      }
   } catch (error) {
      console.error('加载评论失败:', error);
   }
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

const openModal = async (content?: string, replyId?: number) => {
   pendingContent.value = content || '';
   pendingReplyId.value = replyId || null;
   replyTarget.value = null;
   showModal.value = true;

   const savedNickname = localStorage.getItem('comment_nickname');
   const savedEmail = localStorage.getItem('comment_email');
   if (savedNickname) {
      nickname.value = savedNickname;
   }
   if (savedEmail) {
      email.value = savedEmail;
   }

   await fetchRandomAvatar();
};

const openReplyModal = async (comment: CommentType) => {
   pendingContent.value = '';
   pendingReplyId.value = comment.id;
   replyTarget.value = comment;
   showModal.value = true;

   const savedNickname = localStorage.getItem('comment_nickname');
   const savedEmail = localStorage.getItem('comment_email');
   if (savedNickname) {
      nickname.value = savedNickname;
   }
   if (savedEmail) {
      email.value = savedEmail;
   }

   await fetchRandomAvatar();
};

const closeModal = () => {
   showModal.value = false;
   pendingContent.value = '';
   pendingReplyId.value = null;
   replyTarget.value = null;
};

const confirmSubmit = () => {
   if (!nickname.value.trim()) {
      ElMessage.warning('请输入昵称');
      return;
   }
   if (!pendingContent.value.trim()) {
      ElMessage.warning('请输入评论内容');
      return;
   }

   localStorage.setItem('comment_nickname', nickname.value);
   if (email.value) {
      localStorage.setItem('comment_email', email.value);
   }

   if (pendingReplyId.value !== null) {
      emit('reply', {
         nickname: nickname.value,
         email: email.value || undefined,
         content: pendingContent.value,
         avatar: avatarUrl.value,
         replyToCommentId: pendingReplyId.value
      });
   } else {
      emit('submit', {
         nickname: nickname.value,
         email: email.value || undefined,
         content: pendingContent.value,
         avatar: avatarUrl.value
      });
   }

   closeModal();
};

const handleSubmit = (content: string) => {
   openModal(content);
};

watch(
   () => props.articleId,
   () => {
      loadComments();
   }
);

onMounted(() => {
   loadComments();
});

defineExpose({
   loadComments
});
</script>

<style lang="scss" scoped>
.message-board {
   padding: 1em 0;
   position: relative;

   .title {
      margin: 1em auto;
      display: flex;
      justify-content: start;
      align-items: center;
      font-size: 1.2em;

      & > span {
         margin: auto 0.5em;
      }
   }

   .comment-input {
      margin-bottom: 1em;
   }

   .empty-comments {
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: 2em;
      color: var(--cart-home-time-color);

      span {
         margin-top: 0.5em;
      }
   }

   .comment-list {
      .comment-item {
         display: flex;
         margin-bottom: 1.5em;
         padding-bottom: 1.5em;
         border-bottom: 1px solid var(--cart-border-color);

         &:last-child {
            border-bottom: none;
         }

         .comment-avatar {
            flex-shrink: 0;
            width: 50px;
            height: 50px;
            margin-right: 1em;

            img {
               width: 100%;
               height: 100%;
               border-radius: 50%;
            }
         }

         .comment-content {
            flex: 1;

            .comment-header {
               display: flex;
               justify-content: space-between;
               align-items: center;
               margin-bottom: 0.5em;

               .comment-nickname {
                  font-size: 1.1em;
                  font-weight: bold;
                  color: var(--bk-draw-back-color);
               }

               .comment-time {
                  font-size: 0.8em;
                  color: var(--cart-home-time-color);
               }
            }

            .comment-body {
               line-height: 1.8;
               color: var(--bk-font-color);
               margin-bottom: 0.8em;
            }

            .comment-actions {
               .reply-btn {
                  display: flex;
                  align-items: center;
                  font-size: 0.9em;
                  color: var(--bk-draw-back-color);
                  cursor: pointer;
                  transition: color 0.3s;

                  &:hover {
                     color: var(--div-hover-color);
                  }
               }
            }
         }
      }

      .children-comments {
         margin-left: 60px;
         margin-top: 1em;
         padding-left: 1em;
         border-left: 2px solid var(--cart-border-color);

         .child-comment {
            display: flex;
            margin-bottom: 1em;

            .child-avatar {
               flex-shrink: 0;
               width: 40px;
               height: 40px;
               margin-right: 0.8em;

               img {
                  width: 100%;
                  height: 100%;
                  border-radius: 50%;
               }
            }

            .child-content {
               flex: 1;

               .child-header {
                  display: flex;
                  justify-content: space-between;
                  align-items: center;
                  margin-bottom: 0.3em;

                  .child-nickname {
                     font-size: 1em;
                     font-weight: bold;
                     color: var(--bk-draw-back-color);
                  }

                  .child-time {
                     font-size: 0.75em;
                     color: var(--cart-home-time-color);
                  }
               }

               .child-body {
                  line-height: 1.6;
                  color: var(--bk-font-color);
                  font-size: 0.9em;
                  margin-bottom: 0.5em;

                  .reply-to {
                     color: var(--bk-draw-back-color);
                     font-weight: bold;
                  }
               }

               .child-actions {
                  .reply-btn {
                     display: flex;
                     align-items: center;
                     font-size: 0.85em;
                     color: var(--bk-draw-back-color);
                     cursor: pointer;
                     transition: color 0.3s;

                     &:hover {
                        color: var(--div-hover-color);
                     }
                  }
               }
            }
         }
      }
   }

   .modal-overlay {
      position: fixed;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background: rgba(0, 0, 0, 0.5);
      display: flex;
      justify-content: center;
      align-items: center;
      z-index: 1000;
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

            .reply-target {
               display: flex;
               align-items: center;
               padding: 0.8em;
               margin-bottom: 1em;
               border-radius: 8px;
               background: var(--bk-draw-back-color);
               color: white;
               font-size: 0.9em;

               span {
                  margin-left: 0.5em;
               }
            }

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
}
</style>
