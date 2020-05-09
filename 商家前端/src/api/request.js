import axios from 'axios'
import store from '@/store'
import { getToken } from '@/utils/auth'

const Qs = require('qs')

// uat
// const baseURL = 'http://localhost:8089/'
const baseURL = process.env.BASE_API
// const env_data = 'DEV'
const env_data = 'PRO'
const api = {
  env() {
    return env_data
  },
  // 上传图片
  async uploadImg(type, params) {
    const url = '/aliyun/oss/upload'

    const ajax = axios.create({
      baseURL: baseURL,
      url: url,
      headers: {
        'Content-Type': 'multipart/form-data'
      },
      withCredentials: true,
      method: type || 'POST',
      data: params
    })

    const response = await ajax()
    if (response.status === 200) {
      return response.data
    } else {
      return 'error'
    }
  },

  // 上传视频 音频 文件
  async uploadFile(type, params) {
    const url = '/aliyun/oss/uploadFile'

    const ajax = axios.create({
      baseURL: baseURL,
      url: url,
      headers: {
        'Content-Type': 'multipart/form-data'
      },
      withCredentials: true,
      method: type || 'POST',
      data: params
    })

    const response = await ajax()
    if (response.status === 200) {
      return response.data
    } else {
      return 'error'
    }
  },

  /**
     *
     * @param {请求的后置url} url
     * @param {请求的类型：根据java那边的来} type
     * @param {请求的参数} parmas
     * 使用方式：
     * api.request(url,{params},type).then((res) => {
          // do something
        }).catch((error) => {
          // error solution
        })
     */
  async request(url, type, parmas1, isJson) {
    isJson = isJson || false
    //  console.log('请求', parmas)
    // console.log('请求url', url)
    var parmas = []
    for (const key in parmas1) {
      if (parmas1.hasOwnProperty(key)) {
        const typeParam = typeof (parmas1[key])
        if (typeParam === 'object' && parmas1[key]) {
          parmas1[key] = JSON.stringify(parmas1[key])
          if (parmas1[key].substr(0, 2) === '[{' && parmas1[key].substr(-2) === '}]') {
            parmas[key] = parmas1[key]
          } else if (parmas1[key].substr(0, 1) === '[' && parmas1[key].substr(-1) === ']') {
            parmas[key] = parmas1[key].substring(1, parmas1[key].length - 1)
            parmas[key] = parmas1[key].replace(/\"/g, '')
          }
        } else {
          if (parmas1[key]) {
            parmas[key] = parmas1[key]
          }
        }
      }
    }
    let ajax
    if (isJson) {
      ajax = axios.create({
        baseURL: baseURL,
        url: url,
        method: type || 'POST',
        params: {
          ...parmas,
          token: store.getters.token,
          Authorization: getToken(),
          tenantId: store.getters.tenantId
        }
      })
    } else {
      ajax = axios.create({
        baseURL: baseURL,
        url: url,
        method: type || 'POST',
        data: Qs.stringify({
          ...parmas,
          token: store.getters.token,
          tenantId: store.getters.tenantId
        })
      })
    }

    const response = await ajax()

    if (response.status === 200) {
      return response.data
    } else {
      console.log(response)
      return 'error'
    }
  }

}

export default api
