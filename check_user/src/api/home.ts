import request from './request';

export interface BloggerInfo {
   avatar: string;
   nickname: string;
   blogTitle: string;
   qq: string;
   github: string;
   gitee: string;
   bilibili: string;
   birthDate: string;
   gender: number;
   occupation: string;
   tags: string;
   hobbies: string;
   games: string;
   latitude: number;
   longitude: number;
   address: string;
   announcement: string;
}

export interface ApiResponse<T> {
   code: number;
   data: T;
   message: string;
}

/** 获取博主公开信息 */
export const getBloggerInfo = () => {
   return request.get<any, ApiResponse<BloggerInfo>>('/blogger/info');
};
