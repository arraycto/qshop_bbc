import request from '@/utils/request'
export function fetchList(params) {
  return request({
    url: '/sms/smsDiyPage/list',
    method: 'get',
    params: params
  })
}

export function saveTemplate(data) {
  return request({
    url: '/sms/smsDiyPage/create',
    method: 'post',
    data: data
  })
}

export function deleteSmsBasicMarking(id) {
  return request({
    url: '/sms/smsDiyPage/delete/' + id,
    method: 'get'
  })
}

export function detailTemplate(id) {
  return request({
    url: '/sms/smsDiyPage/' + id,
    method: 'get'
  })
}

export function editTemplate(data) {
  return request({
    url: '/sms/smsDiyPage/update',
    method: 'post',
    data: data
  })
}
export function updateStatus(params) {
  return request({
    url: '/sms/smsDiyPage/publishStatus',
    method: 'post',
    params: params
  })
}

