import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/wechatTemplate',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/wechatTemplate/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/wechatTemplate',
    method: 'put',
    data
  })
}

export function get() {
  return request({
    url: 'api/wechatTemplate',
    method: 'get'
  })
}

