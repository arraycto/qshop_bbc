import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/user',
    method: 'post',
    data
  })
}

export function del(uid) {
  return request({
    url: 'api/user/' + uid,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/user',
    method: 'put',
    data
  })
}

export function onStatus(id, data) {
  return request({
    url: 'api/user/onStatus/' + id,
    method: 'post',
    data
  })
}

export function editp(data) {
  return request({
    url: 'api/user/money',
    method: 'post',
    data
  })
}
