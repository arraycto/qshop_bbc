import request from '@/utils/request'

export function getBrandList(data) {
  return request({
    url: 'api/brand',
    method: 'get',
    params: data
  })
}

export function getBrandInfo(id) {
  return request({
    url: 'api/brand/detail/' + id,
    method: 'get'
  })
}

export function add(data) {
  return request({
    url: 'api/brand',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/brand',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/brand',
    method: 'put',
    data
  })
}

export default { add, edit, del, getBrandList, getBrandInfo }
