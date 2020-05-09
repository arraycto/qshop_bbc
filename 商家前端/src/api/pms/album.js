import request from '@/utils/request'

export function fetchList(params) {
  return request({
    url: '/pms/PmsAlbum/list',
    method: 'get',
    params: params
  })
}
export function categoryLists(params) {
  return request({
    url: '/pms/PmsAlbum/list',
    method: 'get',
    params: params
  })
}
export function addCate(data) {
  return request({
    url: '/pms/PmsAlbum/create',
    method: 'post',
    data: data
  })
}

export function deleteCategory(id) {
  return request({
    url: '/pms/PmsAlbum/delete/' + id,
    method: 'get'
  })
}

export function getAlbum(id) {
  return request({
    url: '/pms/PmsAlbum/' + id,
    method: 'get'
  })
}

export function updateAlbum(id, data) {
  return request({
    url: '/pms/PmsAlbum/update/' + id,
    method: 'post',
    data: data
  })
}

export function categoods(params) {
  return request({
    url: '/pms/PmsAlbumPic/list',
    method: 'get',
    params: params
  })
}

export function deleteImg(id) {
  return request({
    url: '/pms/PmsAlbumPic/delete/' + id,
    method: 'get'
  })
}

