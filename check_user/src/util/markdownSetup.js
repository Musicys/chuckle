import MarkdownIt from 'markdown-it';
import hljs from 'highlight.js';
import ClipboardJS from 'clipboard';
import markdownItAnchor from 'markdown-it-anchor';
import markdownItToc from 'markdown-it-table-of-contents';

const md = new MarkdownIt({
   html: true, // 允许解析 HTML
   linkify: true, // 自动链接
   typographer: true // 排版
});

md.set({
   highlight: function (str, lang) {
      let highlighted = str; // 默认为原始字符串
      if (lang && hljs.getLanguage(lang)) {
         try {
            highlighted = hljs.highlight(str, {
               language: lang,
               ignoreIllegals: true
            }).value;
         } catch (__) {
            highlighted = md.utils.escapeHtml(str); // 确保对于高亮失败的情况进行处理
         }
      } else {
         highlighted = md.utils.escapeHtml(str);
      }

      // 为代码块添加行号
      const lines = highlighted
         .split('\n')
         .map((line, index) => {
            return `<span class="line-number">${index + 1}</span>${line}`;
         })
         .join('\n');

      // 为代码块添加复制按钮
      return `<div class="code-block"><pre class="hljs"><code>${lines}</code></pre><button class="copy-btn" data-clipboard-action="copy" data-clipboard-text="${str}">复制</button></div>`;
   }
});

// 使用 markdown-it-anchor 插件生成锚点
md.use(markdownItAnchor, {
   level: 1, // 从 H1 开始生成锚点
   permalink: true, // 为每个标题生成一个永久链接
   permalinkClass: 'header-anchor', // 锚点的 CSS 类
   permalinkSymbol: '' // 锚点的符号
});

// 使用 markdown-it-table-of-contents 插件生成目录
md.use(markdownItToc, {
   includeLevel: [1, 2, 3], // 只包含 <h1> 到 <h3> 级别的标题
   listType: 'ul' // 使用无序列表
});

function initClipboard() {
   // 选择所有的复制按钮
   document.querySelectorAll('.copy-btn').forEach(button => {
      let timeoutId; // 用于存储定时器ID

      // 为每个按钮创建一个ClipboardJS实例
      const clipboard = new ClipboardJS(button);

      clipboard
         .on('success', function (e) {
            // 清除之前的定时器（如果有）
            clearTimeout(timeoutId);
            console.log(e);

            // 设置复制成功的反馈
            const originalText = '复制'; // 假设原始按钮文本是"复制"
            e.trigger.textContent = '已复制'; // 更改按钮文本为“已复制”
            e.trigger.classList.add('copied'); // 添加成功样式

            // 设置定时器，3秒后恢复原始文本
            timeoutId = setTimeout(() => {
               e.trigger.textContent = originalText; // 3秒后恢复原始文本
               e.trigger.classList.remove('copied'); // 移除成功样式
            }, 3000);

            e.clearSelection(); // 清除选中状态
         })
         .on('error', function (e) {
            console.error('复制失败！');
         });
   });
}

export { md, initClipboard };
