import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/storeBargainUserHelp',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/storeBargainUserHelp/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/storeBargainUserHelp',
    method: 'put',
    data
  })
}
