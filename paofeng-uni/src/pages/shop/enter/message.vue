<template>
  <view>
    <header class="mui-bar mui-bar-nav">
      <a class="mui-action-back" @click="uni.navigateBack()">
        <img src="@/static/image/shop/order/back.png" alt="" />
      </a>
      <h1 class="mui-title">消息</h1>
    </header>
    <div class="mui-content">
      <div class="list">
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
      <div class="list">
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
      <div class="list">
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
      </div>
    </div>
  </view>
</template>

<script>
export default {
  data() {
    return {
    }
  },
  onLoad() {
    this.initWebsocket()
  },
  methods: {
    initWebsocket() {
      uni.connectSocket({
        url: 'ws://localhost:18080/chat/websocket',
        protocols: [uni.getStorageSync('auth_token')]
      });
      uni.onSocketMessage(function (res) {
        console.log('收到服务器内容：' + res.data);
      });
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

.list {
  padding: 15px 0;
  width: 100%;
  height: 63px;
  border-bottom: 1px dotted #dbdbdb;
  background: #fff;
}

.mui-content .list:last-child {
  border-bottom: none;
}

.list p:nth-child(1) {
  float: left;
}

.list p:nth-child(1) img {
  margin-top: 2px;
  margin-right: 8px;
  width: 30px;
  height: 30px;
}

.list p:nth-child(2) {
  float: left;
  width: 60%;
  position: relative;
}

.list p:nth-child(2) span {
  display: block;
  height: 15px;
  line-height: 15px;
  position: absolute;
}

.list p:nth-child(2) span:nth-child(1) {
  display: inline-block;
  width: 100%;
  color: #333333;
  font-size: 14px;
  top: 0px;
  left: 0;
}

.list p:nth-child(2) span:nth-child(2) {
  display: inline-block;
  width: 100%;
  color: #666;
  font-size: 13px;
  top: 22px;
  left: 0;
}

.list p:nth-child(3) {
  float: right;
  height: 13px;
  line-height: 13px;
  color: #999;
  font-size: 13px;
}
</style>
