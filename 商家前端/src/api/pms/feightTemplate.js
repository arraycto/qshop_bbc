import request from '@/utils/request'
export function fetchList(params) {
  return request({
    url: '/pms/PmsFeightTemplate/list',
    method: 'get',
    params: params
  })
}
export function createFeightTemplate(data) {
  return request({
    url: '/pms/PmsFeightTemplate/create',
    method: 'post',
    data: data
  })
}

export function deleteFeightTemplate(id) {
  return request({
    url: '/pms/PmsFeightTemplate/delete/' + id,
    method: 'get'
  })
}

export function getFeightTemplate(id) {
  return request({
    url: '/pms/PmsFeightTemplate/' + id,
    method: 'get'
  })
}

export function updateFeightTemplate(id, data) {
  return request({
    url: '/pms/PmsFeightTemplate/update/' + id,
    method: 'post',
    data: data
  })
}

