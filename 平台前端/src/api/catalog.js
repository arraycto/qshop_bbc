import request from '@/utils/request'

export function getCatalogs(params) {
  return request({
    url: 'api/catalog/list',
    method: 'get',
    params
  })
}

export function getCatalogList(params) {
  return request({
    url: 'api/catalog',
    method: 'get',
    params: params
  })
}

export function getCatalogInfo(id) {
  return request({
    url: 'api/catalog/detail/' + id,
    method: 'get'
  })
}

export function add(data) {
  return request({
    url: 'api/catalog',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/catalog/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/catalog',
    method: 'put',
    data
  })
}

export default { add, edit, del, getCatalogs, getCatalogList, getCatalogInfo }
