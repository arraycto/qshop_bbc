import request from '@/utils/request'
export function fetchList(params) {
  return request({
    url: '/sms/smsDraw/list',
    method: 'get',
    params: params
  })
}

export function listDrawMember(params) {
  return request({
    url: '/sms/smsDraw/listDrawMember',
    method: 'get',
    params: params
  })
}
export function createDraw(data) {
  return request({
    url: '/sms/smsDraw/create',
    method: 'post',
    data: data
  })
}

export function deletesmsDraw(id) {
  return request({
    url: '/sms/smsDraw/delete/' + id,
    method: 'get'
  })
}

export function getDraw(id) {
  return request({
    url: '/sms/smsDraw/' + id,
    method: 'get'
  })
}

export function updateDraw(id, data) {
  return request({
    url: '/sms/smsDraw/update/' + id,
    method: 'post',
    data: data
  })
}
export function fetchUserDraw(params) {
  return request({
    url: '/sms/smsDrawUser/list',
    method: 'get',
    params: params
  })
}

