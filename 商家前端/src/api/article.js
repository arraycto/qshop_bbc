import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/article',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/article/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/article',
    method: 'put',
    data
  })
}

export function publish(id) {
  return request({
    url: 'api/article/publish/' + id,
    method: 'get'
  })
}
