<template>
  <template v-if="openId">
    <div class="mine-page">
      登录成功
    </div>
  </template>
  <div v-else class="login-info">
    <button class="login-btn" type="primary" @click="getUserInfo" open-type="getUserInfo">
      微信一键登录
    </button>
  </div>
</template>

<script setup name="mine">
import { onLoad } from '@dcloudio/uni-app'
import { reactive, toRefs } from 'vue'
import { getRandomString } from '@/utils'

import { login } from '@/common/login'

const data = reactive({
  userInfo: {
    avatarUrl: '',
    nickName: `wx_${getRandomString(16)}`
  },
  openId: null
})
const { userInfo, openId } = toRefs(data)

const getUserInfo = async () => {
  try {
    data.openId = await login()
  } catch (error) { }
}

const initHandler = async () => {
  try {
    const oId = uni.getStorageSync('openId')
    if (oId) {
      data.openId = oId
    } else {
      data.openId = await login()
    }
  } catch (e) { }
}
initHandler()

onLoad(() => {
  // if (!data.userInfo) {
  //   uni.navigateTo({ url: '/pages/login/index' })
  // }
})
</script>

<style scoped lang="scss">
.mine-page {
  background-color: #389bff;
  padding: 20rpx 60rpx;
}

</style>
