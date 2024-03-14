<template>
  <view>
    <header class="mui-bar mui-bar-nav">
      <navigator open-type="navigateBack">
        <img src="@/static/image/rider/enter/icon_back@2x.png" alt="" />
      </navigator>
      <h1 class="mui-title">消息</h1>
    </header>
    <div class="mui-content">
      <div class="list-item" v-for="item in getMessages" :key="item.userId || item.id"
        @click="pushMessage(item.userId)">
        <div class="item-left">
          <img :src="item.avatar" />
          <div class="item-content">
            <span>店铺: {{ item.shopName }}</span>
            <span class="mui-ellipsis">{{ getLastMsgHandler(item) }}</span>
          </div>
        </div>
        <div class="item-right">
          <span class="time">{{ item.lastChatTimeStr }}</span>
          <span class="unread-num" v-show="item.unReadNum > 0">{{ item.unReadNum > 99 ? '99+' : item.unReadNum }}</span>
        </div>
      </div>
      <!-- <div class="list-item">
        <p>
          <img src="@/static/image/rider/user/tip1.png" alt="" />
          <span>订单状态</span>
          <i>2017-06-19</i>
        </p>
        <p class="mui-ellipsis">您有新的订单状态，点击查看</p>
      </div>
      <div class="list-item">
        <p>
          <img src="@/static/image/rider/user/tip2.png" alt="" />
          <span>天气预警</span>
          <i>2017-06-19</i>
        </p>
        <p class="mui-ellipsis">暴雨蓝色预警！郑州市明天将有局部暴雨，请广大市民做好转杯等等等等</p>
      </div> -->
    </div>
  </view>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  data() {
    return {
      timer: null
    }
  },
  computed: {
    ...mapGetters(['getMessages'])
  },
  methods: {
    getLastMsgHandler(item) {
      if (item.data && item.data.length) {
        return item.data[item.data.length - 1].content
      }
      return ''
    },
    pushMessage(userId) {
      uni.navigateTo({ url: `/pages/component/chat/chat?userId=${userId}` })
    }
  }
}
</script>

<style scoped>
.mui-bar {
  height: 44px;
  line-height: 44px;
  background: #fff;
  box-shadow: none;
  -webkit-box-shadow: none;
  border-bottom: 1px solid #ccc;
}

.mui-action-back {
  display: inline-block;
  width: 44px;
  height: 44px;
}

header img {
  width: 9px;
  position: absolute;
  top: 14px;
}

.mui-title {
  color: #333;
  font-size: 15px;
}

.mui-content {
  padding: 0 11px;
  padding-top: 54px !important;
  width: 100%;
  background: #f5f5f5;
}

/**/
.list-item {
  padding: 5px 12px;
  width: 100%;
  background: #fff;
  border-radius: 10px;
  margin-bottom: 10px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: relative;
}

.list-item img {
  margin-top: 2px;
  margin-right: 8px;
  width: 48px;
  height: 48px;
}

.item-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-top: 5px;
}

.item-left {
  display: flex;

}

.unread-num {
  border-radius: 50%;
  background-color: red;
  color: #fff;
  display: inline-block;
  height: 23px;
  width: 23px;
  line-height: 23px;
  text-align: center;
  font-size: 12px;
}

.list-item .time {
  height: 13px;
  line-height: 13px;
  color: #999;
  font-size: 13px;
  position: absolute;
  top: 10px;
  right: 40px;
}
</style>
