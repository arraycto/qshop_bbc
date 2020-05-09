import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/wechatUser',
    method: 'post',
    data
  })
}

export function del(uid) {
  return request({
    url: 'api/wechatUser/' + uid,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/wechatUser',
    method: 'put',
    data
  })
}
