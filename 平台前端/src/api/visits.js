import request from '@/utils/request'

export function count() {
  return request({
    url: 'api/visits',
    method: 'post'
  })
}

export function get() {
  return request({
    url: 'api/visits',
    method: 'get'
  })
}

export function getChartData() {
  return request({
    url: 'api/visits/chartData',
    method: 'get'
  })
}

export function gett() {
  return request({
    url: 'api/data/count',
    method: 'get'
  })
}

export function chart(data) {
  const requestParam = {
    url: 'api/data/chart',
    method: 'get'
  }
  if (data) {
    requestParam.params = data
  }
  return request(requestParam)
}

export function getGoodsSalesList(type) {
  return request({
    url: 'api/product/salesList?type=' + type,
    method: 'get'
  })
}
