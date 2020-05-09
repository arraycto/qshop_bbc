import request from '@/utils/request'
export function fetchList(params) {
  return request({
    url: '/pms/PmsProductConsult/list',
    method: 'get',
    params: params
  })
}
export function createGifts(data) {
  return request({
    url: '/pms/PmsProductConsult/create',
    method: 'post',
    data: data
  })
}

export function deleteGifts(id) {
  return request({
    url: '/pms/PmsProductConsult/delete/' + id,
    method: 'get'
  })
}

export function getGifts(id) {
  return request({
    url: '/pms/PmsProductConsult/' + id,
    method: 'get'
  })
}

export function updateGifts(id, data) {
  return request({
    url: '/pms/PmsProductConsult/update/' + id,
    method: 'post',
    data: data
  })
}

export function updateShowStatus(params) {
  return request({
    url: '/pms/PmsProductConsult/update/updateShowStatus',
    method: 'post',
    params: params
  })
}
