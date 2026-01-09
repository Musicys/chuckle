// src/types/highlightjs-line-numbers.d.ts

import hljs from 'highlight.js';

declare module 'highlight.js' {
   interface HLJSApi {
      lineNumbersBlock: (block: HTMLElement) => void;
   }
}
