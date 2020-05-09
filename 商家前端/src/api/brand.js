import request from '@/utils/request'

export function getPlatformBrands(params) {
  return request({
    url: 'api/brand',
    method: 'get',
    params
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

export default { getPlatformBrands, add, del, edit }
