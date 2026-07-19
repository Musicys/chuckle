<template>
   <div class="screen-content">
      <div class="section-title">友情链接</div>
      <div class="links-container">
         <div
            v-for="(link, index) in friendLinks"
            :key="index"
            class="link-item"
            :style="{ animationDelay: `${index * 0.12}s` }"
            :class="{ show: isVisible }">
            <div class="link-icon">
               <img
                  :src="
                     loadedImages[index] ||
                     'https://img.zcool.cn/community/0115bb5715c7356ac725134349d24b.gif'
                  "
                  :alt="link.name"
                  @load="onImageLoad(index)" />
            </div>
            <div class="link-info">
               <div class="link-name">{{ link.name }}</div>
               <div class="link-desc">{{ link.description }}</div>
            </div>
            <a :href="link.url" target="_blank" class="link-btn">访问</a>
         </div>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';

const isVisible = ref(false);
const loadedImages = ref<string[]>([]);

const friendLinks = computed(() => {
   return [
      {
         name: '轻笑Chuckle',
         description: '个人技术博客',
         url: 'https://www.cnblogs.com/chuckle/',
         icon: 'https://www.keaitupian.cn/cjpic/frombd/2/253/1676065055/2828606542.jpg'
      },
      {
         name: 'GitHub',
         description: '开源代码托管平台',
         url: 'https://github.com',
         icon: 'https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png'
      },
      {
         name: 'CSDN',
         description: '专业技术社区',
         url: 'https://blog.csdn.net/Idmusi',
         icon: 'https://g.csdnimg.cn/static/logo/favicon32.ico'
      },
      {
         name: '掘金',
         description: '开发者成长社区',
         url: 'https://juejin.cn',
         icon: 'https://lf3-static.bytednsdoc.com/obj/eden-cn/ptlz_zlp/ljhwZthlaukjlkulzlp/juejin.png'
      },
      {
         name: 'Bilibili',
         description: '哔哩哔哩',
         url: 'https://space.bilibili.com/283189629',
         icon: 'https://i0.hdslb.com/bfs/static/jinkela/main/assets/img/logo.svg'
      },
      {
         name: 'SegmentFault',
         description: '思否技术社区',
         url: 'https://segmentfault.com',
         icon: 'https://segmentfault.com/favicon.ico'
      }
   ];
});

const onImageLoad = (index: number) => {
   const nextIndex = index + 1;
   if (nextIndex < friendLinks.value.length && !loadedImages.value[nextIndex]) {
      setTimeout(() => {
         loadedImages.value[nextIndex] = friendLinks.value[nextIndex].icon;
      }, 100);
   }
};

onMounted(() => {
   isVisible.value = true;
   if (friendLinks.value.length > 0) {
      loadedImages.value[0] = friendLinks.value[0].icon;
   }
});
</script>

<style scoped>
.screen-content {
   width: 100%;
   padding: 50px 40px 100px 40px;
   text-align: center;
   display: flex;
   align-items: center;
   justify-content: center;
   flex-direction: column;
   height: 100%;
   box-sizing: border-box;
}

.section-title {
   font-size: 2.5rem;
   font-weight: 700;
   margin-bottom: 40px;
}

.links-container {
   display: grid;
   grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
   gap: 20px;
   max-width: 900px;
   margin: 0 auto;
}

.link-item {
   background: var(--cart-back-color);
   border: 1px solid var(--cart-border-color);
   border-radius: 16px;
   padding: 20px;
   display: flex;
   align-items: center;
   gap: 15px;
   opacity: 0;
   transform: translateY(20px);
   transition:
      opacity 0.4s ease-out,
      transform 0.4s ease-out;
}

.link-item.show {
   opacity: 1;
   transform: translateY(0);
}

.link-item:hover {
   transform: translateY(-5px);
   box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
}

.link-icon {
   width: 50px;
   height: 50px;
   border-radius: 12px;
   overflow: hidden;
   flex-shrink: 0;
   background: rgba(255, 255, 255, 0.1);
}

.link-icon img {
   width: 100%;
   height: 100%;
   object-fit: contain;
   padding: 5px;
}

.link-info {
   flex: 1;
   text-align: left;
}

.link-name {
   font-size: 1.1rem;
   font-weight: 600;
   margin-bottom: 4px;
}

.link-desc {
   font-size: 0.85rem;
   opacity: 0.7;
}

.link-btn {
   padding: 8px 16px;
   background: var(--el-but-back);
   color: #fff;
   border-radius: 8px;
   text-decoration: none;
   font-size: 0.85rem;
   font-weight: 500;
   transition: background 0.3s ease;
}

.link-btn:hover {
   opacity: 0.8;
}

@media (max-width: 768px) {
   .screen-content {
      padding: 20px;
   }
   .section-title {
      font-size: 2rem;
   }
   .links-container {
      grid-template-columns: 1fr;
   }
   .link-item {
      padding: 15px;
      gap: 12px;
   }
   .link-icon {
      width: 40px;
      height: 40px;
   }
}
</style>
