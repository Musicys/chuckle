import request from './request';

export interface Message {
   id: number;
   nickname: string;
   email: string;
   avatar: string;
   content: string;
   replyToCommentId: number | null;
   createdAt: string;
   children: Message[];
}

export interface MessageRequest {
   nickname: string;
   email?: string;
   avatar?: string;
   content: string;
   replyToCommentId?: number;
}

export interface ApiResponse<T> {
   code: number;
   data: T;
   message: string;
}

export interface PageResponse<T> {
   code: number;
   data: {
      records: T[];
      total: number;
      page: number;
      size: number;
      pages: number;
   };
}

export const getMessageList = (page: number = 1, size: number = 20) => {
   return request.get<any, PageResponse<Message>>('/messages/list', {
      params: { page, size }
   });
};

export const submitMessage = (data: MessageRequest) => {
   return request.post<any, ApiResponse<number>>('/messages/comment', data);
};
