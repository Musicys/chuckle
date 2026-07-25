import request from './request';

export interface ArticleTag {
   id: number;
   name: string;
   color: string;
}

export interface ArticleCategory {
   id: number;
   name: string;
}

export interface ArticleDetail {
   id: number;
   title: string;
   summary: string;
   cover: string;
   content: string;
   category: ArticleCategory;
   tags: ArticleTag[];
   wordCount: number;
   readCount: number;
   commentCount: number;
   isNew: boolean;
   createdAt: string;
   updatedAt: string;
}

export interface CommentRequest {
   articleId: number;
   nickname: string;
   email?: string;
   avatar?: string;
   content: string;
   replyToCommentId?: number;
}

export interface Comment {
   id: number;
   nickname: string;
   email: string;
   avatar: string;
   content: string;
   replyToCommentId: number | null;
   createdAt: string;
   children: Comment[];
}

export interface ApiResponse<T> {
   code: number;
   data: T;
   message: string;
}

export const fetchArticleById = (id: number) => {
   return request.get<any, ApiResponse<ArticleDetail>>(`/articles/${id}`);
};

export const submitComment = (data: CommentRequest) => {
   return request.post<any, ApiResponse<number>>('/articles/comment', data);
};

export const fetchComments = (articleId: number) => {
   return request.get<any, ApiResponse<Comment[]>>(
      `/articles/comments/${articleId}`
   );
};
