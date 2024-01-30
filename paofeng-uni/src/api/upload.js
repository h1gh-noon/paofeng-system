import baseConfig from '../baseConfig.js'

export const uniUploadImage = imageSrc =>
  new Promise((resolve, reject) => {
    console.log(imageSrc)
    uni.uploadFile({
      url: '/api/file/upload',
      header: {
        Authorization: 'Bearer ' + uni.getStorageSync('auth_token')
      },
      filePath: imageSrc,
      fileType: 'image',
      name: 'file',
      success: res => resolve(JSON.parse(res.data)),
      fail: err => reject(err)
    })
  })
