import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/storeCoupon',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/storeCoupon/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/storeCoupon',
    method: 'put',
    data
  })
}
