import request from '@/utils/request'
export function fetchList(params) {
  return request({
    url: '/sms/smsPaimai/list',
    method: 'get',
    params: params
  })
}

export function createPaimai(data) {
  return request({
    url: '/sms/smsPaimai/create',
    method: 'post',
    data: data
  })
}

export function deletesmsPaiMai(id) {
  return request({
    url: '/sms/smsPaimai/delete/' + id,
    method: 'get'
  })
}

export function getPaimai(id) {
  return request({
    url: '/sms/smsPaimai/' + id,
    method: 'get'
  })
}

export function updatePaimai(id, data) {
  return request({
    url: '/sms/smsPaimai/update/' + id,
    method: 'post',
    data: data
  })
}
export function fetchPaiMaiLog(params) {
  return request({
    url: '/sms/smsPaimai/fetchPaiMaiLog',
    method: 'get',
    params: params
  })
}
export function updateShowStatus(params) {
  return request({
    url: '/sms/smsPaimai/update/updateShowStatus',
    method: 'post',
    params: params
  })
}

