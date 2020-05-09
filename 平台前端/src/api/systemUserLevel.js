import request from '@/utils/request'

export function getUserLevel(data) {
  return request({
    url: 'api/systemUserLevel',
    method: 'get',
    data
  })
}

export function add(data) {
  return request({
    url: 'api/systemUserLevel',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/systemUserLevel/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/systemUserLevel',
    method: 'put',
    data
  })
}
