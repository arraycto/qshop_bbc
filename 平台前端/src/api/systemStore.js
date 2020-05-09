import request from '@/utils/request'

export function get() {
  return request({
    url: 'api/systemStore',
    method: 'get'
  })
}

export function getL(data) {
  return request({
    url: 'api/systemStore/getL',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/systemStore/',
    method: 'delete',
    data: ids
  })
}

export function update(data) {
  return request({
    url: 'api/systemStore',
    method: 'put',
    data
  })
}

export default { get, getL, update, del }
