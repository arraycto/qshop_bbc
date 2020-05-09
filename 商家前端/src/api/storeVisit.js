import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/storeVisit',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/storeVisit/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/storeVisit',
    method: 'put',
    data
  })
}
