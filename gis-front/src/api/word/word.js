import request from '@/utils/request'

let URL= '/GIS/download';


export function exportDataWord() {  
  return request({
    url : `${URL}/exportDataWord`,
    method: 'get',
    responseType: 'blob', // 限制返回的数据结构为blob格式
  });
}