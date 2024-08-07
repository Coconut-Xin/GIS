import service from '@/utils/request'

let URL= 'http:127.0.0.1:8080/GIS/node';
    
function ApiSet(){
  this.urldata="/api"
}

// 封装 GET 请求  
export function getData(query=null) {  
    return service({
      baseURL : `/list2`,
      method: 'get',
      params: query
    })
}  
  
// 封装 POST 请求  
export function postData(endpoint, data = {}) {  
  return apiClient.post(endpoint, data);  
}  

// 如果你需要拼接额外的 URL 片段，可以在函数内部进行  
export function getSpecificData(resourceId, params = {}) {  
    // 假设你有一个固定的 'resources' 路径，但 resourceId 是可变的  
    const endpoint = `/resources/${resourceId}`;  
    return apiClient.get(endpoint, { params });  
}  
  
// 封装其他类型的请求...  
  
// 你可以在这里继续添加更多的请求函数