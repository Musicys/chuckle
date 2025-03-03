<template>
  <div class="pr">
    <div style="margin-bottom: 1em;">
      <span> 名称:music </span>
      <span> 邮箱 :2825424566@qq.com</span>
    </div>
    <textarea @input="updateCharacterCount" placeholder="来评论交流吧,博主可能会邮箱回复哦！" name="" class="text"
      id="commentTextarea"></textarea>
    <div class="pr_but" style="justify-content: end;">
      <span>{{ characterCount }} / 400 字</span>
      <button class="but">提交</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

// 定义一个响应式变量来存储字数
const characterCount = ref(0);

// 定义一个函数来更新字数
const updateCharacterCount = (event: Event) => {
  const textarea = event.target as HTMLTextAreaElement;
  characterCount.value = textarea.value.length;
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

    &>span {
      margin-right: 1em;
    }
  }

  &>div {
    width: 100%;
    display: flex;
    justify-content: space-between;

    &>span {
      display: inline-block;


    }

  }

  .but {
    color: var(--bk-back-color);
    background: #61A9DE;
    padding: .5em 1em;
    border-radius: 10px;

    border: none;

    &:hover {
      cursor: pointer;
      background: #F5CF51;
    }

  }

  .text {
    width: 95%;

    resize: vertical;
    /* 用户只能调整高度 */
    min-height: 150px;
    border-radius: 10px;
    color: var(--bk-font-color);
    border: none;
    border: none;

    /* 清除内边距 */
    padding: 0;

    /* 清除外边距 */
    margin: 0;

    /* 清除背景颜色 */
    background-color: transparent;

    /* 清除字体样式 */
    font-family: inherit;
    font-size: inherit;
    line-height: inherit;

    /* 清除轮廓线 */
    outline: none;

    /* 清除文本区域的滚动条 */
    overflow: auto;
    margin-bottom: .5em;
    padding: 1em;

    &:focus {
      background: var(--mart-commment-bot-back);
    }

  }
}
</style>