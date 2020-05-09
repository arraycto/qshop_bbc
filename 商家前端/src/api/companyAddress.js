import request from '@/utils/request'

export function getCompanyList(data) {
  return request({
    url: 'api/companyAddress',
    method: 'get',
    params: data
  })
}

export function add(data) {
  return request({
    url: 'api/companyAddress',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/companyAddress/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/companyAddress',
    method: 'put',
    data
  })
}

export default { getCompanyList, add, edit, del }
