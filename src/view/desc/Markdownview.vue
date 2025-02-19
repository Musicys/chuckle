<template>
  <div class="mkdir" v-html="renderedMarkdown" />
</template>

<script>
import { ref, onMounted } from 'vue';
import { md, initClipboard } from '@/util/markdownSetup';

export default {
  props: {
    markdownContent: String,
  },
  setup(props) {
    const renderedMarkdown = ref('');

    onMounted(() => {
      renderedMarkdown.value = md.render(props.markdownContent);
      setTimeout(() => {
        initClipboard();
      }) // 初始化Clipboard.js
    });

    return {
      renderedMarkdown,
    };



  },
};
</script>

<style>
.mkdir {
  width: 100%;

}
</style>