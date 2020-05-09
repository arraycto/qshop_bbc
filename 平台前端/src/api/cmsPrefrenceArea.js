import request from '@/utils/request'

export function getPrefrenceArea(data) {
  return request({
    url: 'api/cmsPrefrenceArea',
    method: 'get',
    params: data
  })
}

export function add(data) {
  return request({
    url: 'api/cmsPrefrenceArea',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/cmsPrefrenceArea/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/cmsPrefrenceArea',
    method: 'put',
    data
  })
}

export default { add, edit, del, getPrefrenceArea }
