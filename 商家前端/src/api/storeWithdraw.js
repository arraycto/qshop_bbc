import request from '@/utils/request'

export function getStoreWithdraw(data) {
  return request({
    url: 'api/storeWithdraw',
    method: 'get',
    params: data
  })
}

export function add(data) {
  return request({
    url: 'api/storeWithdraw',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/storeWithdraw/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/storeWithdraw',
    method: 'put',
    data
  })
}

export default { add, edit, del }
