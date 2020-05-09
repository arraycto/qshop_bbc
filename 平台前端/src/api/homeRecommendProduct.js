import request from '@/utils/request'

export function getHomeReProductList(data) {
  return request({
    url: 'api/homeRecommendProduct',
    method: 'get',
    params: data
  })
}

export function add(data) {
  return request({
    url: 'api/homeRecommendProduct',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/homeRecommendProduct/',
    method: 'delete',
    data: [ids]
  })
}

export function edit(data) {
  return request({
    url: 'api/homeRecommendProduct',
    method: 'put',
    data
  })
}

export default { getHomeReProductList, add, edit, del }
