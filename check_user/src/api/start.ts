import request from './request';

export interface PortfolioItem {
   id: number;
   title: string;
   description: string;
   category: string;
   cover: string;
   projectUrl: string;
   sortOrder: number;
   status: number;
}

export interface FriendLinkItem {
   id: number;
   name: string;
   url: string;
   avatar: string;
   description: string;
   email: string;
   sortOrder: number;
   status: number;
}

export function getPortfolioList() {
   return request.get('/portfolio/list');
}

export function getFriendLinks() {
   return request.get('/friendLinks/list');
}

export function applyFriendLink(data: {
   name: string;
   url: string;
   avatar?: string;
   description?: string;
   email?: string;
}) {
   return request.post('/friendLinks/apply', data);
}
