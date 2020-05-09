import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/storeSettle',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/storeSettle',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/storeSettle',
    method: 'put',
    data
  })
}

export function get() {
  return request({
    url: 'api/storeSettle',
    method: 'get'
  })
}

export default { add, edit, del, get }
