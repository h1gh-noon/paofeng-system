import baseConfig from '../baseConfig.js'

const httpRequest = {
  request: function (method, url, data) {
    const header = {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + uni.getStorageSync('auth_token')
    }

    return new Promise((resolve, reject) => {
      uni.showLoading({
        title: '加载中',
        mask: true
      })

      uni.request({
        url: url,
        header,
        data,
        method,
        dataType: 'json',
        success: function (res) {
          uni.hideLoading()
          console.log('接口获取原始数据：-------------------', res)
          if (res.statusCode === 401) {
            uni.showToast({
              icon: 'none',
              duration: 1000,
              title: res.data.error.message
            })
            // setTimeout(function () {
            //   uni.reLaunch({
            //     url: '../login/login'
            //   })
            // }, 1500)
          } else {
            if (res.statusCode === 200) {
              resolve(res.data)
            } else {
              uni.showToast({
                icon: 'none',
                duration: 1000,
                title: '系统异常!'
              })
              reject(err)
              return
            }
          }
        },
        fail: function (err) {
          uni.hideLoading()
          reject(err)
        }
      })
    })
  },
  get: function (url, params) {
    const allurl = this.geturl(url) + this.getParams(params)
    return this.request('GET', allurl)
  },
  post: function (url, data, params) {
    const allurl = this.geturl(url) + this.getParams(params)
    return this.request('POST', allurl, data)
  },
  geturl: function (url) {
    return baseConfig.server + url
  },
  getParams: params => {
    if (params) {
      let str = Object.keys(params).reduce(
        (a, c) => (a += `${c}=${params[c]}&`),
        '?'
      )
      return str.substring(0, str.length - 1)
    }
    return ''
  }
}

export default httpRequest
