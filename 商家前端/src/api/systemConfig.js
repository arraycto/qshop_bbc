import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/storeSystemConfig',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/storeSystemConfig/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/storeSystemConfig',
    method: 'put',
    data
  })
}

export function get() {
  return request({
    url: 'api/storeSystemConfig?size=50',
    method: 'get'
  })
}

