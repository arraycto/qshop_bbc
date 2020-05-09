import request from '@/utils/request'

export function getCombinations(data) {
  return request({
    url: 'api/storeCombination',
    method: 'get',
    params: data
  })
}

export function add(data) {
  return request({
    url: 'api/storeCombination',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/storeCombination/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/storeCombination',
    method: 'put',
    data
  })
}

export function onsale(id, data) {
  return request({
    url: 'api/storeCombination/onsale/' + id,
    method: 'post',
    data
  })
}
