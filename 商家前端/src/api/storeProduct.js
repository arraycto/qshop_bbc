import request from '@/utils/request'

export function getProduct(data) {
  return request({
    url: 'api/storeProduct',
    method: 'get',
    params: data
  })
}

export function getProductDetail(id) {
  return request({
    url: 'api/storeProduct/' + id,
    method: 'get'
  })
}

export function add(data) {
  return request({
    url: 'api/storeProduct',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/storeProduct/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/storeProduct',
    method: 'put',
    data
  })
}

export function onsale(id, data) {
  return request({
    url: 'api/storeProduct/onsale/' + id,
    method: 'post',
    data
  })
}

export function recovery(id) {
  return request({
    url: 'api/storeProduct/recovery/' + id,
    method: 'delete'
  })
}

export function isFormatAttr(id, data) {
  return request({
    url: 'api/storeProduct/isFormatAttr/' + id,
    method: 'post',
    data
  })
}

export function setAttr(id, data) {
  return request({
    url: 'api/storeProduct/setAttr/' + id,
    method: 'post',
    data
  })
}

export function clearAttr(id) {
  return request({
    url: 'api/storeProduct/clearAttr/' + id,
    method: 'post'
  })
}

export function getAttr(id) {
  return request({
    url: 'api/storeProduct/attr/' + id,
    method: 'get'
  })
}

export function updateStatus(data) {
  return request({
    url: 'api/storeProduct/updateStatus',
    method: 'post',
    data
  })
}
