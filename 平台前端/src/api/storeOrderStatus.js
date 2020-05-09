import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/storeOrderrStatus',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/storeOrderrStatus/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/storeOrderrStatus',
    method: 'put',
    data
  })
}
