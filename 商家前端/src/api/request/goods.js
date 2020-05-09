
import request from '@/utils/request'

export function goodsClassAll(params) {
  return request({
    url: '/pms/PmsProductCategory/list',
    method: 'get',
    params: params
  })
}

export function goodsOneClassEnable(params) {
  return request({
    url: '/pms/PmsProductCategory/list',
    method: 'get',
    params: params
  })
}

export function goodsList(params) {
  return request({
    url: '/pms/PmsProduct/list',
    method: 'get',
    params: params
  })
}

export function getGoodsUpper(params) {
  return request({
    url: '/pms/PmsProduct/list',
    method: 'get',
    params: params
  })
}

export function shopCustomData(params) {
  return request({
    url: '/sms/smsDiyPage/selCustom',
    method: 'get',
    params: params
  })
}

