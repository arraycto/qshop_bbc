import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/shop',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/shop',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/shop',
    method: 'put',
    data
  })
}

export function get() {
  return request({
    url: 'api/shop',
    method: 'get'
  })
}

export function getAllShopList() {
  return request({
    url: 'api/shop/list',
    method: 'get'
  })
}

export default { add, edit, del, get, getAllShopList }
