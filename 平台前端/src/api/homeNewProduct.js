import request from '@/utils/request'

export function getHomeNewPrdductList(data) {
  return request({
    url: 'api/homeNewProduct',
    method: 'get',
    params: data
  })
}

export function add(data) {
  return request({
    url: 'api/homeNewProduct',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/homeNewProduct/',
    method: 'delete',
    data: [ids]
  })
}

export function edit(data) {
  return request({
    url: 'api/homeNewProduct',
    method: 'put',
    data
  })
}

export default { add, edit, del }
