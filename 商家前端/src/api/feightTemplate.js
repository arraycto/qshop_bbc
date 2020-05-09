import request from '@/utils/request'

export function getFeightTemplate(data) {
  return request({
    url: 'api/feightTemplate',
    method: 'get',
    params: data
  })
}

export function add(data) {
  return request({
    url: 'api/feightTemplate',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/feightTemplate/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/feightTemplate',
    method: 'put',
    data
  })
}

export default { add, edit, del }
