import request from '@/utils/request'

export function getCates(params) {
  return request({
    url: 'api/storeCategory',
    method: 'get',
    params
  })
}

export function add(data) {
  return request({
    url: 'api/storeCategory',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/storeCategory/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/storeCategory',
    method: 'put',
    data
  })
}

export default { getCates, add, edit, del }
