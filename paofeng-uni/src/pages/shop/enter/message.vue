<template>
  <view>
    <header class="mui-bar mui-bar-nav">
      <a class="mui-action-back">
        <img src="@/static/image/shop/order/back.png" alt="" />
      </a>
      <h1 class="mui-title">消息</h1>
    </header>
    <div class="mui-content">
      <div class="list-item" v-for="item in getMessages" :key="item.userId || item.id"
        @click="pushMessage(item.userId)">
        <div class="item-left">
          <img :src="item.avatar" />
          <div class="item-content">
            <span>骑手: {{ item.riderName }}</span>
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
          <img src="@/static/image/shop/enter/xiaoxi_1.png" />
        </p>
        <p>
          <span>订单状态</span>
          <span class="mui-ellipsis">您有新的订单状态，点击查水电费v看</span>
        </p>
        <p>
          <span>05-22</span>
          <span>09:56</span>
        </p>
      </div>
      <div class="list-item">
        <p>
          <img src="@/static/image/shop/enter/xiaoxi_2.png" />
        </p>
        <p>
          <span>通知消息</span>
          <span class="mui-ellipsis">您有一个新的订单，点击查看</span>
        </p>
        <p>
          <span>05-22</span>
          <span>09:56</span>
        </p>
      </div>
      <div class="list-item">
        <p>
          <img src="@/static/image/shop/enter/xiaoxi_3.png" />
        </p>
        <p>
          <span>异常订单</span>
          <span>您有一个异常订单，点击查看</span>
        </p>
        <p>
          <span>05-22</span>
          <span>09:56</span>
        </p>
      </div> -->
    </div>
  </view>
</template>

<script>
import { mapGetters } from 'vuex';
import { dateUtils } from '@/common/util';
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
  background: #6A4A23;
  box-shadow: none;
  -webkit-box-shadow: none;
}

.mui-action-back {
  display: inline-block;
  width: 44px;
  height: 44px;
}

header img {
  width: 10px;
  height: 17px;
  position: absolute;
  top: 13px;
}

.mui-title {
  color: #fff;
  font-size: 16px;
}

.mui-content {
  padding: 0 15px;
  padding-top: 44px;
  width: 100%;
  background: #fff;
  border-bottom: 1px solid #dbdbdb;
}

/**/

.list-item {
  width: 100%;
  height: 63px;
  border-bottom: 1px dotted #dbdbdb;
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: relative;
}

.mui-content .list-item:last-child {
  border-bottom: none;
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
  right: 20px;
}
</style>
