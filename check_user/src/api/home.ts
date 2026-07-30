import request from './request';

export interface GameItem {
   icon: string;
   title: string;
   value: string;
}

export interface Config {
   theme: string;
   layout: string;
   musicEnabled: boolean;
   commentEnabled: boolean;
   url: string;
   aiurl: string;
   miyao: string;
   age: string;
   major: string;
   currentOccupation: string;
   pursuits: string[];
   games: GameItem[];
}

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
   config: string;
   profileStats: ProfileStat[];
}

export interface ProfileStat {
   label: string;
   count: number;
}

export interface RecentArticle {
   id: number;
   title: string;
   time: string;
}

export interface HotArticle {
   id: number;
   title: string;
   cover: string;
}

export interface SiteInfo {
   siteUptime: string;
   totalArticles: number;
   lastUpdated: string;
   totalWords: number;
}

export interface HomeInfo {
   recentArticles: RecentArticle[];
   tags: Record<string, number>;
   articlesByYear: Record<string, number>;
   siteInfo: SiteInfo;
   hotArticles: HotArticle[];
}

export interface ArticleTag {
   id: number;
   name: string;
   color: string;
}

export interface ArticleCategory {
   id: number;
   name: string;
}

export interface ArticleItem {
   id: number;
   title: string;
   titleHighlight: string;
   summary: string;
   summaryHighlight: string;
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

export interface ArticleListRequest {
   keyword?: string;
   categoryId?: number;
   tagId?: number;
   current?: number;
   pageSize?: number;
   sortField?: string;
   sortOrder?: string;
}

export interface ArticleListResponse {
   records: ArticleItem[];
   total: number;
   current: number;
   size: number;
   pages: number;
}

export interface ApiResponse<T> {
   code: number;
   data: T;
   message: string;
}

export const getBloggerInfo = () => {
   return request.get<any, ApiResponse<BloggerInfo>>('/blogger/info');
};

export const fetchHomeInfo = () => {
   return request.get<any, ApiResponse<HomeInfo>>('/blogger/home');
};

export const fetchArticles = (params: ArticleListRequest) => {
   return request.post<any, ApiResponse<ArticleListResponse>>(
      '/articles/list',
      params
   );
};

export const fetchArticleById = (id: number) => {
   return request.get<any, ApiResponse<ArticleItem>>(`/articles/${id}`);
};

export const submitComment = (data: CommentRequest) => {
   return request.post<any, ApiResponse<number>>('/articles/comment', data);
};

export const searchArticles = (params: ArticleListRequest) => {
   return request.post<any, ApiResponse<ArticleListResponse>>(
      '/articles/search',
      params
   );
};

export interface ArchiveArticle {
   id: number;
   title: string;
   summary: string;
   cover: string;
   readCount: number;
   commentCount: number;
   createdAt: string;
}

export interface ArchiveYearGroup {
   year: number;
   count: number;
   articles: ArchiveArticle[];
}

export const fetchArchives = () => {
   return request.get<any, ApiResponse<ArchiveYearGroup[]>>(
      '/articles/archives'
   );
};
