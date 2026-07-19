import { defineStore } from 'pinia';
import { reactive } from 'vue';
import { getBloggerInfo } from '@/api/home';
import type { BloggerInfo } from '@/api/home';

interface User {
   username: string;
   userImg: string;
   LodingImgUrl: string;
   IndexBack: string;
   IndexBackImg: string;
   GitHubUrl: string;
   gitURL: string;
   drawURL: string;
   drawtilte: string;
   QqUrl: string;
   Bilbilurl: string;
   CsdnUrl: string;
   StartTilte: string;
   announcement: string;
}

export const useUserStore = defineStore('user', () => {
   const user: User = reactive({
      username: '轻笑Chuckle',
      userImg:
         'https://www.keaitupian.cn/cjpic/frombd/2/253/1676065055/2828606542.jpg',
      LodingImgUrl: '',
      IndexBack: '',
      drawURL:
         'https://tse2-mm.cn.bing.net/th/id/OIP-C.OX22ZodoOmhwmPQ3-31sSgAAAA?rs=1&pid=ImgDetMain',
      IndexBackImg: '',
      GitHubUrl: '',
      gitURL: 'string,',
      drawtilte: '漫天倾尘 风中轻笑',
      QqUrl: 'https://space.bilibili.com/283189629?spm_id_from=333.788.0.0',
      Bilbilurl: 'https://space.bilibili.com/283189629?spm_id_from=333.788.0.0',
      CsdnUrl: 'https://blog.csdn.net/Idmusi?spm=1000.2115.3001.5343',
      StartTilte: '我走不开制动懂你发个额额',
      announcement: ''
   });

   /** 从后端获取博主信息并映射到 store */
   const fetchBloggerInfo = async () => {
      try {
         const res = await getBloggerInfo();
         if (res.code === 0) {
            const data: BloggerInfo = res.data;
            user.username = data.nickname;
            user.userImg = data.avatar;
            user.drawURL = data.avatar;
            user.GitHubUrl = data.github;
            user.gitURL = data.gitee;
            user.QqUrl = data.qq;
            user.Bilbilurl = data.bilibili;
            user.drawtilte = data.blogTitle;
            user.announcement = data.announcement;
            return data;
         }
      } catch (e) {
         console.error('获取博主信息失败', e);
      }
   };

   return { user, fetchBloggerInfo };
});
