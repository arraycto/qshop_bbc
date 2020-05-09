import request from '@/utils/request'

export function getSeckills(data) {
  return request({
    url: 'api/storeSeckill',
    method: 'get',
    params: data
  })
}

export function add(data) {
  return request({
    url: 'api/storeSeckill',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/storeSeckill/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/storeSeckill',
    method: 'put',
    data
  })
}
