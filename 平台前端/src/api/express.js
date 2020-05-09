import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/express',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/express/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/express',
    method: 'put',
    data
  })
}

