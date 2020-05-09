import request from '@/utils/request'

export function getStoreComplaint(data) {
  return request({
    url: 'api/storeComplaint',
    method: 'get',
    params: data
  })
}

export function add(data) {
  return request({
    url: 'api/storeComplaint',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/storeComplaint/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/storeComplaint',
    method: 'put',
    data
  })
}

export default { add, edit, del, getStoreComplaint }
