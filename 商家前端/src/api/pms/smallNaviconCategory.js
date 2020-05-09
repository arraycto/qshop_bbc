import request from '@/utils/request'
export function fetchList(params) {
  return request({
    url: '/pms/smallNaviconCategory/list',
    method: 'get',
    params: params
  })
}
export function createSmallNaviconCategory(data) {
  return request({
    url: '/pms/smallNaviconCategory/create',
    method: 'post',
    data: data
  })
}

export function deleteSmallNaviconCategory(id) {
  return request({
    url: '/pms/smallNaviconCategory/delete/' + id,
    method: 'get'
  })
}

export function getSmallNaviconCategory(id) {
  return request({
    url: '/pms/smallNaviconCategory/' + id,
    method: 'get'
  })
}

export function updateSmallNaviconCategory(id, data) {
  return request({
    url: '/pms/smallNaviconCategory/update/' + id,
    method: 'post',
    data: data
  })
}

