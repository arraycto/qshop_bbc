import request from '@/utils/request'

export function getPlatformCatalogs(data) {
  return request({
    url: 'api/catalog',
    method: 'get',
    params: data
  })
}

export function getPlatformCatalogCList(data) {
  return request({
    url: 'api/catalog/list',
    method: 'get',
    params: data
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

export default { getPlatformCatalogCList, getPlatformCatalogs, add, edit, del }
