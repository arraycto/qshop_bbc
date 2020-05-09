import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/userExtract',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/userExtract/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/userExtract',
    method: 'put',
    data
  })
}
