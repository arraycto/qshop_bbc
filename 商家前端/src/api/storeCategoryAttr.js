import request from '@/utils/request'

export function getStoreCategoryAttr(data) {
  return request({
    url: 'api/storeCategoryAttr',
    method: 'get',
    param: data
  })
}

export function add(data) {
  return request({
    url: 'api/storeCategoryAttr',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/storeCategoryAttr/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/storeCategoryAttr',
    method: 'put',
    data
  })
}

export default { add, edit, del }
