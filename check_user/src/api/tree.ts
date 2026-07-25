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

export const getMessageList = () => {
   return request.get<any, ApiResponse<Message[]>>('/messages/list');
};

export const submitMessage = (data: MessageRequest) => {
   return request.post<any, ApiResponse<number>>('/messages/comment', data);
};
