import request from '@/utils/request'

export function getProductRecord(id) {
  return request({
    url: 'api/productVertifyRecord/',
    method: 'get',
    data: id
  })
}

export function add(data) {
  return request({
    url: 'api/productVertifyRecord',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/productVertifyRecord/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/productVertifyRecord',
    method: 'put',
    data
  })
}

export default { add, edit, del, getProductRecord }
