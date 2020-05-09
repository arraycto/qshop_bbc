import request from '@/utils/request'

export function getStoreSettle(data) {
  return request({
    url: 'api/storeSettle',
    method: 'get',
    params: data
  })
}

export function getStoreVertifyRecord(data) {
  return request({
    url: 'api/storeVertifyRecord',
    method: 'get',
    params: data
  })
}

export function add(data) {
  return request({
    url: 'api/storeSettle',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/storeSettle/',
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

export default { add, edit, del, getStoreSettle, getStoreVertifyRecord }
