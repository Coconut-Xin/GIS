import request from '@/utils/request'

let URL= '/GIS/cluster';


export function getClusterData(query=null) {  
  return request({
    url : `${URL}/clusterList`,
    method: 'get',
    params: query
  });
}