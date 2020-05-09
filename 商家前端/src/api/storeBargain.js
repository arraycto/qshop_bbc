import request from '@/utils/request'

export function getBargains(data) {
  return request({
    url: 'api/storeBargain',
    method: 'get',
    params: data
  })
}

export function add(data) {
  return request({
    url: 'api/storeBargain',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/storeBargain/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/storeBargain',
    method: 'put',
    data
  })
}
