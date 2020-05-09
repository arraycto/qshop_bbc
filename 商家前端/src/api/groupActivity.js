import request from '@/utils/request'

export function getGroupActivity(data) {
  return request({
    url: 'api/smsGroupActivity',
    method: 'get',
    params: data
  })
}

export function getGroupActivityDetail(id) {
  return request({
    url: 'api/smsGroupActivity/' + id,
    method: 'get'
  })
}

export function add(data) {
  return request({
    url: 'api/smsGroupActivity',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/smsGroupActivity/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/smsGroupActivity',
    method: 'put',
    data
  })
}

export default { getGroupActivity, getGroupActivityDetail, add, edit, del }
