import request from '@/utils/request'

let URL= '/GIS/task';


export function getTaskData(query=null) {  
  return request({
    url : `${URL}/taskList`,
    method: 'get',
    params: query
  });
}