<template>
  <view class="mine-container">

    <!--顶部个人信息栏-->
    <view class="header-section">

    </view>
  </view>
</template>

<script setup name="mine">
import { onLoad } from '@dcloudio/uni-app'
import { reactive, toRefs } from 'vue'
import { useAuthStore } from "@/stores/auth";

const userInfo = useAuthStore().userInfoComputed

const initHandler = async () => {
  try {
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
.mine-container {
  .header-section {
    padding: 15px 15px 45px 15px;
    background-color: #389bff;
    color: white;

  }

}
</style>
