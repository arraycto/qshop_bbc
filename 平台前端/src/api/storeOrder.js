import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/storeOrder',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/storeOrder/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/storeOrder',
    method: 'put',
    data
  })
}

export function editT(data) {
  return request({
    url: 'api/storeOrder/check',
    method: 'put',
    data
  })
}

export function refund(data) {
  return request({
    url: 'api/storeOrder/refund',
    method: 'post',
    data
  })
}

export function editOrder(data) {
  return request({
    url: 'api/storeOrder/edit',
    method: 'post',
    data
  })
}

export function remark(data) {
  return request({
    url: 'api/storeOrder/remark',
    method: 'post',
    data
  })
}

export function get() {
  return request({
    url: 'api/express',
    method: 'get'
  })
}

export function express(data) {
  return request({
    url: 'api/storeOrder/express',
    method: 'post',
    data
  })
}
