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
   '--md-table-bk-color': 'var(--datail-back-color)',
   '--md-table-border-color': 'var(--cart-border-color)',
   '--md-table-th-bk-color': 'var(--cart-back-color)',
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

/* 标题样式 */
.markdown-preview-wrapper ::v-deep(.md-editor-preview h1) {
   font-size: 28px !important;
   font-weight: 700 !important;
   color: var(--bk-draw-back-color) !important;
   margin: 32px 0 16px 0 !important;
   padding-bottom: 12px !important;
   border-bottom: 2px solid var(--cart-border-color) !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview h2) {
   font-size: 24px !important;
   font-weight: 600 !important;
   color: var(--bk-draw-back-color) !important;
   margin: 28px 0 14px 0 !important;
   padding-bottom: 8px !important;
   border-bottom: 1px solid var(--cart-border-color) !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview h3) {
   font-size: 20px !important;
   font-weight: 600 !important;
   color: var(--bk-draw-back-color) !important;
   margin: 24px 0 12px 0 !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview h4) {
   font-size: 18px !important;
   font-weight: 600 !important;
   color: var(--bk-font-color) !important;
   margin: 20px 0 10px 0 !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview h5) {
   font-size: 16px !important;
   font-weight: 500 !important;
   color: var(--bk-font-color) !important;
   margin: 16px 0 8px 0 !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview h6) {
   font-size: 14px !important;
   font-weight: 500 !important;
   color: var(--bk-font-color) !important;
   margin: 12px 0 6px 0 !important;
}

/* 代码块样式 */
.markdown-preview-wrapper ::v-deep(.md-editor-preview code) {
   background-color: rgba(72, 243, 247, 0.15) !important;
   color: var(--bk-draw-back-color) !important;
   padding: 2px 6px !important;
   border-radius: 4px !important;
   font-size: 14px !important;
   font-family: 'MyserverFonts', 'Courier New', monospace !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview pre) {
   background-color: var(--cart-back-color) !important;
   border: 1px solid var(--cart-border-color) !important;
   border-radius: 12px !important;
   overflow-x: auto !important;
   box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15) !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview pre code) {
   font-family: 'MyserverFonts', 'Courier New', monospace !important;
   font-size: 14px !important;
   line-height: 1.8 !important;
   color: var(--bk-font-color) !important;
   white-space: pre !important;
   background-color: transparent !important;
   padding: 0 !important;
   border-radius: 0 !important;
}

/* 代码块复制按钮 */
.markdown-preview-wrapper ::v-deep(.md-editor-preview .copy-btn) {
   position: absolute !important;
   top: 8px !important;
   right: 8px !important;
   background-color: var(--bk-draw-back-color) !important;
   color: var(--bk-font-color) !important;
   border: none !important;
   border-radius: 6px !important;
   padding: 6px 12px !important;
   font-size: 12px !important;
   cursor: pointer !important;
   transition: all 0.2s ease !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview .copy-btn:hover) {
   background-color: var(--div-hover-color) !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview .copy-btn.copied) {
   background-color: #28a745 !important;
}

/* 引用样式 */
.markdown-preview-wrapper ::v-deep(.md-editor-preview blockquote) {
   background-color: var(--cart-back-color) !important;
   border-left: 4px solid var(--bk-draw-back-color) !important;
   padding: 16px 20px !important;
   margin: 16px 0 !important;
   border-radius: 0 8px 8px 0 !important;
   color: var(--bk-font-color) !important;
   font-style: italic !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview blockquote p) {
   margin-bottom: 8px !important;
   line-height: 1.8 !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview blockquote p:last-child) {
   margin-bottom: 0 !important;
}

/* 列表样式 */
.markdown-preview-wrapper ::v-deep(.md-editor-preview ul),
.markdown-preview-wrapper ::v-deep(.md-editor-preview ol) {
   margin: 16px 0 !important;
   padding-left: 28px !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview li) {
   margin-bottom: 8px !important;
   line-height: 1.8 !important;
   color: var(--bk-font-color) !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview ul li) {
   list-style-type: disc !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview ol li) {
   list-style-type: decimal !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview ul ul),
.markdown-preview-wrapper ::v-deep(.md-editor-preview ol ol),
.markdown-preview-wrapper ::v-deep(.md-editor-preview ul ol),
.markdown-preview-wrapper ::v-deep(.md-editor-preview ol ul) {
   margin: 8px 0 !important;
   padding-left: 20px !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview ul ul li) {
   list-style-type: circle !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview ul ul ul li) {
   list-style-type: square !important;
}

/* 图片样式 */
.markdown-preview-wrapper ::v-deep(.md-editor-preview img) {
   max-width: 100% !important;
   height: auto !important;
   border-radius: 8px !important;
   display: block !important;
   margin: 16px auto !important;
   box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2) !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview .image-wrapper) {
   text-align: center !important;
}

.markdown-preview-wrapper
   ::v-deep(.md-editor-preview .image-wrapper figcaption) {
   font-size: 14px !important;
   color: var(--bk-draw-back-color) !important;
   margin-top: 8px !important;
}

/* 链接样式 */
.markdown-preview-wrapper ::v-deep(.md-editor-preview a) {
   color: var(--bk-draw-back-color) !important;
   text-decoration: none !important;
   border-bottom: 1px solid transparent !important;
   transition: all 0.2s ease !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview a:hover) {
   color: var(--div-hover-color) !important;
   border-bottom-color: var(--bk-draw-back-color) !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview a:active) {
   color: var(--cart-border-color) !important;
}

/* 分隔线样式 */
.markdown-preview-wrapper ::v-deep(.md-editor-preview hr) {
   border: none !important;
   height: 1px !important;
   background: linear-gradient(
      to right,
      transparent,
      var(--cart-border-color),
      transparent
   ) !important;
   margin: 32px 0 !important;
}

/* 行内样式 */
.markdown-preview-wrapper ::v-deep(.md-editor-preview strong) {
   font-weight: 600 !important;
   color: var(--bk-font-color) !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview em) {
   font-style: italic !important;
   color: var(--bk-font-color) !important;
}

.markdown-preview-wrapper ::v-deep(.md-editor-preview del) {
   text-decoration: line-through !important;
   color: var(--bk-draw-back-color) !important;
}

/* 任务列表样式 */
.markdown-preview-wrapper ::v-deep(.md-editor-preview input[type='checkbox']) {
   margin-right: 8px !important;
   cursor: pointer !important;
}

.markdown-preview-wrapper
   ::v-deep(.md-editor-preview input[type='checkbox']:checked) {
   accent-color: var(--bk-draw-back-color) !important;
}

.markdown-preview-wrapper
   ::v-deep(.md-editor-preview input[type='checkbox']):checked
   + span {
   text-decoration: line-through !important;
   color: var(--bk-draw-back-color) !important;
}
</style>
