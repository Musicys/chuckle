// api.js
import axios from 'axios';
import config from '@/config';
// 创建一个 Axios 实例
const apiClient = axios.create({
  baseURL: config.url, // 设置基础 URL
  timeout: 20000, // 设置请求超时时间

});




// 添加请求拦截器
apiClient.interceptors.request.use(
  config => {
    // 在请求发送之前做些什么
    // 例如添加认证 Token
    const token = localStorage.getItem('authToken');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  error => {
    // 对请求错误做些什么
    return Promise.reject(error);
  }
);

// 添加响应拦截器
apiClient.interceptors.response.use(
  response => {
    // 对响应数据做些什么


    //对时间进行格式



    return response.data;
  },
  error => {
    // 对响应错误做些什么
    return Promise.reject(error);
  }
);

export default apiClient;