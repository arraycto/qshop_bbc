import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/wechatReply',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/wechatReply/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/wechatReply',
    method: 'put',
    data
  })
}

export function get() {
  return request({
    url: 'api/wechatReply',
    method: 'get'
  })
}

