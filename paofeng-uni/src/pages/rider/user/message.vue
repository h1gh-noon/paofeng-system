<template>
  <view>
    <header class="mui-bar mui-bar-nav">
      <navigator open-type="navigateBack">
        <img src="@/static/image/rider/enter/icon_back@2x.png" alt="" />
      </navigator>
      <h1 class="mui-title">消息</h1>
    </header>
    <div class="mui-content">
      <div class="list link" data-link="message_1.html">
        <p>
          <img src="@/static/image/rider/user/tip1.png" alt="" />
          <span>订单状态</span>
          <i>2017-06-19</i>
        </p>
        <p class="mui-ellipsis">您有新的订单状态，点击查看</p>
      </div>
      <div class="list link" data-link="message_1.html">
        <p>
          <img src="@/static/image/rider/user/tip2.png" alt="" />
          <span>天气预警</span>
          <i>2017-06-19</i>
        </p>
        <p class="mui-ellipsis">暴雨蓝色预警！郑州市明天将有局部暴雨，请广大市民做好转杯等等等等</p>
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
      // this.$PopupMessage('aa')
  },
  methods: {
    
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
.list {
  padding: 0 12px;
  width: 100%;
  height: 65px;
  background: #fff;
  border-radius: 10px;
  margin-bottom: 10px;
}

.list p:nth-child(1) {

  width: 100%;
  height: 36px;
}

.list p:nth-child(1) img {
  float: left;
  margin-right: 10px;
  margin-top: 11px;
  width: 13px;
}

.list p:nth-child(1) span {
  float: left;
  display: inline-block;
  height: 36px;
  line-height: 36px;
  color: #333333;
  font-size: 15px;
}

.list p:nth-child(1) i {
  float: right;
  display: inline-block;
  height: 36px;
  line-height: 36px;
  color: #999;
  font-size: 12px;
  font-style: normal;
}

.list p:nth-child(2) {
  width: 100%;
  height: 21px;
  line-height: 21px;
  color: #333333;
  font-size: 12px;

}
</style>
