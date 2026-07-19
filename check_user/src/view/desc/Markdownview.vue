<template>
   <div class="markdown-preview-wrapper">
      <MdPreview
         :model-value="markdownContent"
         :style="customTheme"
         :codeFoldable="false" />
   </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { MdPreview } from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';

const props = defineProps<{
   markdownContent: string;
}>();

const customTheme = computed(() => ({
   '--md-bk-color': 'var(--datail-back-color)',
   '--md-color': 'var(--bk-font-color)',
   '--md-border-color': 'var(--cart-border-color)',
   '--md-color-primary': 'var(--bk-draw-back-color)',
   '--md-code-bk-color': 'var(--cart-back-color)',
   '--md-code-color': 'var(--bk-font-color)',
   '--md-blockquote-bk-color': 'var(--cart-back-color)',
   '--md-blockquote-border-color': 'var(--bk-draw-back-color)',
   '--md-border-radius': '8px',
   '--md-font-size': '16px',
   '--md-line-height': '2.2'
}));
</script>

<style scoped>
.markdown-preview-wrapper {
   width: 100%;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview p) {
   margin-bottom: 24px !important;
   line-height: 2.2 !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview table) {
   width: 100% !important;
   border-collapse: collapse !important;
   margin-bottom: 16px !important;
   background-color: var(--datail-back-color) !important;
   border: 1px solid var(--cart-border-color) !important;
   border-radius: 8px !important;
   overflow: hidden !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview th),
.markdown-preview-wrapper ::v-deep(.md-editor-preview td) {
   border: 1px solid var(--cart-border-color) !important;
   padding: 12px 16px !important;
   text-align: left !important;
   color: var(--bk-font-color) !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview th) {
   background-color: var(--cart-back-color) !important;
   font-weight: 600 !important;
   color: var(--bk-font-color) !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview tr:nth-child(even)) {
   background-color: var(--cart-back-color) !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview tr:hover) {
   background-color: var(--div-hover-color) !important;
}
</style>
