import request from '@/utils/request'

export function getReturnApplyList(data) {
  return request({
    url: 'api/orderReturnApply',
    method: 'get',
    params: data
  })
}

export function add(data) {
  return request({
    url: 'api/orderReturnApply',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/orderReturnApply',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/orderReturnApply',
    method: 'put',
    data
  })
}

export function getReturnApplyInfo(id) {
  return request({
    url: 'api/orderReturnApply/detail/' + id,
    method: 'get'
  })
}

export default { getReturnApplyList, add, edit, del, getReturnApplyInfo }
