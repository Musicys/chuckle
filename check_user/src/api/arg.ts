import request from './request';

export interface Tag {
   id: number;
   name: string;
   color: string;
   articleCount: number;
}

export interface ApiResponse<T> {
   code: number;
   data: T;
   message: string;
}

export const getTagList = () => {
   return request.get<any, ApiResponse<Tag[]>>('/tags/list');
};