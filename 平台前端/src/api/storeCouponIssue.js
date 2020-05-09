import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/storeCouponIssue',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/storeCouponIssue/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/storeCouponIssue',
    method: 'put',
    data
  })
}
