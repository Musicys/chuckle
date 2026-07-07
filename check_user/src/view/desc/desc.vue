<template>
   <div class="desc">
      <div class="left" v-if="Ispc">
         <div
            class="box"
            style="margin-bottom: 1em; padding: 0; overflow: hidden">
            <Usercart></Usercart>
         </div>
         <div class="box" style="margin-bottom: 1em">
            <Announcement></Announcement>
         </div>
         <div class="box ml-box" ref="desc_mr">
            <div class="left-top">
               <el-icon> <Memo /> </el-icon>目录
            </div>

            <div class="ml">
               <ol>
                  <!-- 跳转加到下面这些目录里 -->
                  <li v-for="heading in toc" :key="heading.id">
                     <a
                        :class="heading.level == 1 ? 'one' : 'srte'"
                        :href="'#' + heading.text.toLowerCase()"
                        >{{ heading.text }}</a
                     >
                  </li>
               </ol>
            </div>
         </div>
      </div>
      <div class="right">
         <MarkTop></MarkTop>
         <MarkdownViewer :markdownContent="markdownContent" />
         <MessageBoard />
      </div>
   </div>
</template>

<script setup lang="ts">
import { Ispc } from '@/util/windows';
import { ref, onMounted, watch } from 'vue';
import Usercart from '../home/usercart.vue';
import Announcement from './Announcement.vue';
import wz from '../../static/java.md?raw';
import MarkdownViewer from './Markdownview.vue';
import MarkTop from './MarkTop.vue';
import { descIsFlex, desc_mr } from '@/util/scrse';
import MessageBoard from './MessageBoard.vue';
import util from '@/util/funtion';
import { useRoute } from 'vue-router';
// 获取当前路由对象
const route = useRoute();

// 创建一个响应式的 id 变量
const id = ref(route.query.id);

// 监听路由参数的变化
watch(
   () => route.query.id,
   newId => {
      id.value = newId;
   }
);

const markdownContent = ref(`

# 你好

This is a **markdown** example.

## Headings1
## Headings2
## Headings3

## Headings4
## Headings5

# Hello, world!

This is a **markdown** example.

## Headings5
## Headings
## Headings6

## Headings7
## Headings


# Hello, world!

This is a **markdown** example.




**Bold** text.

***Bold and Italic*** text.

~~Strikethrough~~ text.

## Lists

### Unordered List

- Item 1
- Item 2
  - Subitem 2.1
  - Subitem 2.2
- Item 3

### Ordered List

1. First item
2. Second item
   1. Subitem 2.1
   2. Subitem 2.2
3. Third item

## Links

[Google](https://www.google.com)

## Images

![Markdown Logo](https://file.moyublog.com/free_wallpapers_files/kdxevforc0m.jpg)

## Code

![Markdown Logo](https://wallpaperm.cmcm.com/4700eaf249b71d56d95aff8ca94313fa.jpg)



This is an example of \`inline code\`.



\`\`\`javascript
function greet(name) {
  console.log(\`Hello, \${name}!\`);
}

greet('Alice');
\`\`\`



\`\`\`javascript
function greet(name) {
  console.log(\`Hello, \${name}!\`);
}


ddddd

ddddd
ddddd


greet('Alice');
\`\`\`

## Blockquotes

> This is a blockquote.
>
> It can span multiple lines.

## Horizontal Rule

---

## Tables

| Header 1 | Header 2 |
| -------- | -------- |
| Row 1    | Data 1   |
| Row 2    | Data 2   |
`);

const toc = ref<{ id: string; level: number; text: string }[]>([]);

function generateToc() {
   const headingRegex = /^(#+)\s+(.*)$/gm;
   let match: RegExpExecArray | null;

   toc.value = [];

   while ((match = headingRegex.exec(markdownContent.value)) !== null) {
      const level: number = match[1].length;
      const text: string = match[2];
      const id: string = text
         .toLowerCase()
         .replace(/\s+/g, '-')
         .replace(/[^\w-]+/g, '');
      toc.value.push({ id, level, text });
   }
}
onMounted(() => {
   generateToc();

   console.log(id.value);
});
</script>

<style lang="scss" scoped>
/* 自定义代码块背景颜色 */

.one {
   display: inline-block;
   margin-bottom: 0.5em;
   transition: all 0.5s ease;
   width: 100%;
   cursor: pointer;

   &:hover {
      background: rgba(129, 209, 239, 0.5);
   }
}

.srte {
   width: 100%;
   display: inline-block;
   margin-left: 0.8em;
   padding-left: 0.2em;

   border-left: 1px dashed var(--bk-font-color);
   transition: all 0.5s ease;
   cursor: pointer;

   &:hover {
      background: rgba(129, 209, 239, 0.9);
   }
}

.ml-box {
   height: 85vh;
   position: sticky;
   /* 关键属性 */
   top: 0;
   /* 固定在顶部 */
}

.bordr {
   color: var(--bk-font-color);
   border-radius: 10px;
   background: var(--cart-back-color);
   border: 3px solid var(--cart-border-color);
   padding: 5px;
}

.desc {
   display: flex;

   .left {
      position: relative;
      flex: 1;

      .box {
         @extend .bordr;

         .left-top {
            display: flex;
            justify-content: start;
            align-items: center;
            margin-bottom: 0.5em;
         }

         .ml {
            height: 80vh;
            overflow-y: auto;
            overflow-x: hidden;

            &::-webkit-scrollbar-track {
               background: var(--cart-back-color);
               /* 轨道背景色 */
            }

            &::-webkit-scrollbar {
               width: 5px;
               /* 滚动条的宽度 */
            }

            & > ol {
               list-style: none;

               & > li > a {
                  line-height: 1.8em;

                  text-decoration: none;
                  /* 去掉下划线 */
                  color: inherit;
                  /* 继承父元素的颜色 */
                  cursor: pointer;
                  /* 确保光标样式为指针 */
                  outline: none;
                  /* 去掉点击时的外边框 */
               }
            }
         }
      }
   }

   .right {
      margin-left: 1em;
      margin-bottom: 2em;
      flex: 4;
      @extend .bordr;
   }
}
</style>
