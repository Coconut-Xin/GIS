// apiClient.js  
import axios from 'axios';  

axios.defaults.baseURL = 'http:127.0.0.1:8080' ;
// 创建一个 axios 实例，并配置基础 URL、超时时间等（如果需要）  
const service = axios.create({  
//   baseURL: 'http://your-springboot-server/api', // 设置基础 URL 
  
  baseURL : 'http://localhost:8080',
  timeout: 20000, // 设置请求超时时间（毫秒）  
  headers: {
    'Content-Type': 'application/json;charset=utf-8'
  },
  // ... 其他配置  

});  


export default service