import request from '@/utils/request'

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

export function onVerify(data) {
  return request({
    url: 'api/bargainVertifyRecord',
    method: 'post',
    data
  })
}
