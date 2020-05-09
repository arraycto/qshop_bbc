import request from '@/utils/request'

export function getCmsSubjectCategory(data) {
  return request({
    url: 'api/cmsSubjectCategory',
    method: 'get',
    params: data
  })
}

export function add(data) {
  return request({
    url: 'api/cmsSubjectCategory',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/cmsSubjectCategory/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/cmsSubjectCategory',
    method: 'put',
    data
  })
}

export default { add, edit, del }
