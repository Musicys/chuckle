import axios from 'axios';

const request = axios.create({
   baseURL: '/api',
   timeout: 20000
});

request.interceptors.response.use(
   response => response.data,
   error => Promise.reject(error)
);

export default request;
