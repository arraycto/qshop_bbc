import request from '@/utils/request'

export function getPlatformCates(params) {
  return request({
    url: 'api/category',
    method: 'get',
    params
  })
}

export default { getPlatformCates }
