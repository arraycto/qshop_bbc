import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/storeCouponUser',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/storeCouponUser/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/storeCouponUser',
    method: 'put',
    data
  })
}
