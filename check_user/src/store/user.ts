import { defineStore } from 'pinia';
import { reactive } from 'vue';
import { getBloggerInfo } from '@/api/home';
import type { BloggerInfo, Config } from '@/api/home';

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
   // 新增字段
   occupation: string;
   address: string;
   tags: string[];
   hobbies: string[];
}

export const useUserStore = defineStore('user', () => {
   const user: User = reactive({
      username: '轻笑Chuckle',
      userImg: '',
      LodingImgUrl: '',
      IndexBack: '',
      drawURL: '',
      IndexBackImg: '',
      GitHubUrl: '',
      gitURL: '',
      drawtilte: '轻笑Chuckle的个人博客',
      QqUrl: '',
      Bilbilurl: '',
      CsdnUrl: '',
      StartTilte: '',
      announcement: '',
      occupation: '',
      address: '',
      tags: [],
      hobbies: []
   });

   const config: Config = reactive({
      theme: 'default',
      layout: 'blog',
      musicEnabled: false,
      commentEnabled: true,
      url: '',
      aiurl: '',
      miyao: '',
      age: '',
      major: '',
      currentOccupation: '',
      pursuits: [],
      games: []
   });

   /** 从后端获取博主信息并映射到 store */
   const fetchBloggerInfo = async () => {
      try {
         const res = await getBloggerInfo();
         if (res.code === 0) {
            const data: BloggerInfo = res.data;
            user.username = data.nickname || '轻笑Chuckle';
            user.userImg = data.avatar || '';
            user.drawURL = data.avatar || '';
            user.GitHubUrl = data.github || '';
            user.gitURL = data.gitee || '';
            user.QqUrl = data.qq || '';
            user.Bilbilurl = data.bilibili || '';
            user.drawtilte = data.blogTitle || '轻笑Chuckle的个人博客';
            user.announcement = data.announcement || '';
            user.occupation = data.occupation || '';
            user.address = data.address || '';

            // 解析 tags 和 hobbies
            try {
               user.tags = data.tags ? JSON.parse(data.tags) : [];
            } catch {
               user.tags = [];
            }
            try {
               user.hobbies = data.hobbies ? JSON.parse(data.hobbies) : [];
            } catch {
               user.hobbies = [];
            }

            // 解析 config
            if (data.config) {
               try {
                  const configObj = JSON.parse(data.config);
                  config.theme = configObj.theme || 'default';
                  config.layout = configObj.layout || 'blog';
                  config.musicEnabled = configObj.musicEnabled || false;
                  config.commentEnabled = configObj.commentEnabled ?? true;
                  config.url = configObj.url || '';
                  config.aiurl = configObj.aiurl || '';
                  config.miyao = configObj.miyao || '';
                  config.age = configObj.age || '';
                  config.major = configObj.major || '';
                  config.currentOccupation = configObj.currentOccupation || '';
                  config.pursuits = Array.isArray(configObj.pursuits)
                     ? configObj.pursuits
                     : [];
                  config.games = Array.isArray(configObj.games)
                     ? configObj.games
                     : [];
               } catch (e) {
                  console.error('解析配置失败', e);
               }
            }
            return data;
         }
      } catch (e) {
         console.error('获取博主信息失败', e);
      }
   };

   return { user, config, fetchBloggerInfo };
});
