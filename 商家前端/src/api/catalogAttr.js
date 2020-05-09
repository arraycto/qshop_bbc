import request from '@/utils/request'

export function getCatalogAttr(data) {
  return request({
    url: 'api/catalogAttr',
    method: 'get',
    params: data
  })
}

export function add(data) {
  return request({
    url: 'api/catalogAttr',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/catalogAttr/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/catalogAttr',
    method: 'put',
    data
  })
}

export default { add, edit, del }
