import $store from '@/store'

const whiteList = ['/pages/login/login', '/pages/home/home']

const list = ['navigateTo', 'redirectTo', 'reLaunch', 'switchTab']

list.forEach(item => {
  uni.addInterceptor(item, {
    // 页面跳转前进行拦截, invoke根据返回值进行判断是否继续执行跳转
    invoke(e) {
      const token = uni.getStorageSync('auth_token')
      if (token && $store.state.id === '') {
        $store.dispatch('getUserInfo')
      }
      if (whiteList.includes(e.url)) {
        return true
      }
      if (!token) {
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
