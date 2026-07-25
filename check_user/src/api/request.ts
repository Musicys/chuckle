import axios from 'axios';

const request = axios.create({
   baseURL: '/api',
   timeout: 20000
});

const timeRegex = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}/;

const formatTimeFields = (data: any): any => {
   if (data === null || data === undefined || typeof data !== 'object') {
      return data;
   }

   if (Array.isArray(data)) {
      return data.map(item => formatTimeFields(item));
   }

   const formatted: any = {};
   for (const key in data) {
      if (Object.prototype.hasOwnProperty.call(data, key)) {
         const value = data[key];
         if (typeof value === 'string' && timeRegex.test(value)) {
            const date = new Date(value);
            if (!isNaN(date.getTime())) {
               const year = date.getFullYear();
               const month = String(date.getMonth() + 1).padStart(2, '0');
               const day = String(date.getDate()).padStart(2, '0');
               const hours = String(date.getHours()).padStart(2, '0');
               const minutes = String(date.getMinutes()).padStart(2, '0');
               const seconds = String(date.getSeconds()).padStart(2, '0');
               formatted[key] =
                  `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
            } else {
               formatted[key] = value;
            }
         } else {
            formatted[key] = formatTimeFields(value);
         }
      }
   }
   return formatted;
};

request.interceptors.response.use(
   response => {
      const data = response.data;
      return formatTimeFields(data);
   },
   error => Promise.reject(error)
);

export default request;
