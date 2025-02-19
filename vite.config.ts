import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import { resolve } from 'path';

// https://vitejs.dev/config/
export default defineConfig({

  optimizeDeps: {
    include: ['highlightjs-line-numbers.js']
  },

  build: {
    outDir: 'dist',
    assetsDir: 'assets',
    sourcemap: false,
    terserOptions: {
      compress: {
        drop_console: true,
        drop_debugger: true,
      },
    },
  },

  plugins: [
    vue(),
    // ...
    ,
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],

  css: {
    preprocessorOptions: {
      scss: {
        // additionalData: `@import "./src/style.scss";`
      }
    }
  },
  server: {
    proxy: {
      '/api': {
        target: "http://localhost:8087/", //跨域地址
        changeOrigin: true, //支持跨域
        rewrite: (path) => path.replace(/^\/api/, "")//重写路径,替换/api
      }
    }
  },
  resolve: {
    // 设置文件./src路径为 @
    alias: [
      {
        find: '@',
        replacement: resolve(__dirname, './src')
      }
    ],

  },



})
