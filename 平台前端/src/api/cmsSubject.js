import request from '@/utils/request'

export function getCmsSubject(data) {
  return request({
    url: 'api/cmsSubject',
    method: 'get',
    params: data
  })
}

export function add(data) {
  return request({
    url: 'api/cmsSubject',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/cmsSubject/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/cmsSubject',
    method: 'put',
    data
  })
}

export default { add, edit, del, getCmsSubject }
