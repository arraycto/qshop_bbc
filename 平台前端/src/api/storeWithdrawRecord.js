import request from '@/utils/request'

export function getStoreWithdrawRecord(data) {
  return request({
    url: 'api/storeWithdrawRecord',
    method: 'get',
    params: data
  })
}

export function add(data) {
  return request({
    url: 'api/storeWithdrawRecord',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/storeWithdrawRecord/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/storeWithdrawRecord',
    method: 'put',
    data
  })
}

export default { add, edit, del, getStoreWithdrawRecord }
