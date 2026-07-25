import request from './request';

export interface VisitsStats {
   todayPv: number;
   todayUv: number;
   monthPv: number;
   totalPv: number;
}

export const getVisitsStats = () => {
   return request.get<VisitsStats>('/visits/stats');
};

export const recordVisit = (path?: string) => {
   return request.post('/visits/record', path || window.location.pathname);
};
