const whiteList = ['/pages/login/login', '/pages/home/home']

const list = ['navigateTo', 'redirectTo', 'reLaunch', 'switchTab']

function hasPermission(url) {
  // 在白名单中或有token，直接跳转
  return whiteList.includes(url) || uni.getStorageSync('auth_token')
}

list.forEach(item => {
  uni.addInterceptor(item, {
    // 页面跳转前进行拦截, invoke根据返回值进行判断是否继续执行跳转
    invoke(e) {
      console.log(e, 111)
      if (!hasPermission(e.url)) {
        // 将用户的目标路径保存下来
        // 这样可以实现 用户登录之后，直接跳转到目标页面
        uni.setStorageSync('URL', e.url)

        uni.navigateTo({
          url: '/pages/login/login'
        })

        return false
      }
      return true
    }
  })
})
