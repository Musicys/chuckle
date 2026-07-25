<template>
   <div class="pr">
      <textarea
         v-model="content"
         @input="updateCharacterCount"
         placeholder="来评论交流吧,博主可能会邮箱回复哦！"
         name=""
         class="text"
         id="commentTextarea"></textarea>
      <div class="pr_but" style="justify-content: end">
         <span>{{ characterCount }} / 400 字</span>
         <button class="but" @click="handleSubmit">提交</button>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { ElMessage } from 'element-plus';

defineProps<{
   isReply?: boolean;
}>();

const emit = defineEmits<{
   (e: 'submit', content: string): void;
}>();

const content = ref('');
const characterCount = ref(0);

const updateCharacterCount = (event: Event) => {
   const textarea = event.target as HTMLTextAreaElement;
   characterCount.value = textarea.value.length;
};

const handleSubmit = () => {
   if (!content.value.trim()) {
      ElMessage.warning('请输入评论内容');
      return;
   }
   emit('submit', content.value);
   content.value = '';
   characterCount.value = 0;
};
</script>

<style lang="scss" scoped>
.pr {
   width: 90%;
   margin: 1em auto;
   border-radius: 15px;
   background: var(--mart-top-pr-back);
   display: flex;
   flex-direction: column;
   align-items: center;
   justify-content: center;
   padding: 1em;

   .pr_but {
      align-items: center;
      width: 100%;
      display: flex;
      justify-content: end;

      & > span {
         margin-right: 1em;
         font-size: 0.9em;
         color: var(--cart-home-time-color);
      }
   }

   .but {
      color: var(--bk-back-color);
      background: #61a9de;
      padding: 0.5em 1em;
      border-radius: 10px;
      border: none;
      cursor: pointer;

      &:hover {
         background: #f5cf51;
      }
   }

   .text {
      width: 95%;
      resize: vertical;
      min-height: 150px;
      border-radius: 10px;
      color: var(--bk-font-color);
      border: none;
      background-color: transparent;
      font-family: inherit;
      font-size: inherit;
      line-height: inherit;
      outline: none;
      overflow: auto;
      margin-bottom: 0.5em;
      padding: 1em;

      &:focus {
         background: var(--mart-commment-bot-back);
      }

      &::placeholder {
         color: var(--cart-home-time-color);
      }
   }
}
</style>
