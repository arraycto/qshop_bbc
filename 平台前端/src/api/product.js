import request from '@/utils/request'

export function getProduct(data) {
  return request({
    url: 'api/product',
    method: 'get',
    params: data
  })
}

export function add(data) {
  return request({
    url: 'api/product',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/product/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/product',
    method: 'put',
    data
  })
}

export function onsale(id, data) {
  return request({
    url: 'api/product/onsale/' + id,
    method: 'post',
    data
  })
}

export function recovery(id) {
  return request({
    url: 'api/product/recovery/' + id,
    method: 'delete'
  })
}

export function isFormatAttr(id, data) {
  return request({
    url: 'api/product/isFormatAttr/' + id,
    method: 'post',
    data
  })
}

export function setAttr(id, data) {
  return request({
    url: 'api/product/setAttr/' + id,
    method: 'post',
    data
  })
}

export function clearAttr(id) {
  return request({
    url: 'api/product/clearAttr/' + id,
    method: 'post'
  })
}

export function getAttr(id) {
  return request({
    url: 'api/product/attr/' + id,
    method: 'get'
  })
}

