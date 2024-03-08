<template>
  <view>
    <header class="mui-bar mui-bar-nav">
      <a class="mui-action-back">
        <navigator open-type="navigateBack">
          <img src="@/static/image/rider/enter/icon_back@2x.png" alt="" />
        </navigator>
      </a>
      <h1 class="mui-title">个人中心</h1>
    </header>
    <div class="mui-content">
      <div class="main_one">
        <p>
          <img src="@/static/image/rider/enter/logo.png" />
          <img src="@/static/image/rider/user/Write@2x.png" />
          <input type="file" id="file1" name="file1" />
        </p>
        <p>
          <img src="@/static/image/rider/user/qs.png" alt="" />
          <span>张先生</span>
        </p>
        <p>
          18000000000
        </p>
      </div>
      <div class="main_two">
        <p class="link" data-link="zhangHu_yuE.html">
          <span>￥6000.00</span>
          <span>账户余额</span>
        </p>
        <p class="dairenzheng">
          <!--进行显示登陆的用户是否已经认证过还是没有认证过.默认待认证显示-->
          <span class="dai link" data-link="@/static/image/rider/enter/wanShan_ziLiao.html">待认证</span>
          <!--已认证不显示-->
          <span class="yi link" data-link="renZheng_ziLiao.html" style="display: none;">已认证</span> <!---->
          <span>签约状态</span>
        </p>

      </div>
      <div class="main_three">
        <p class="link" data-link="youJiang_tuiJian.html">
          <img src="@/static/image/rider/user/Share@2x.png" style="width: 20px;" />
          <span>有奖推荐</span>
        </p>
        <p class="link" data-link="qiShou_paiHang.html">
          <img src="@/static/image/rider/user/Menu@2x.png" style="width: 15px;" />
          <span>骑手排行</span>
        </p>
        <p class="link" data-link="message.html">
          <img src="@/static/image/rider/user/Comment@2x.png" style="width: 21px;" />
          <span>消息通知</span>
        </p>
        <p class="link" data-link="zaiXian_keFu.html">
          <img src="@/static/image/rider/user/Comment-(alt)@2x.png" style="width: 21px;" />
          <span>在线客服</span>
        </p>
        <p class="link" data-link="wanCheng_order.html">
          <img src="@/static/image/rider/user/Newspaper@2x.png" style="width: 20px;" />
          <span>完成订单</span>
        </p>
        <p class="link" data-link="set.html">
          <img src="@/static/image/rider/user/Setting@2x.png" style="width: 20px;" />
          <span>设置中心</span>
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
  padding: 86px 12px 12px 12px !important;
  width: 100%;
  margin-bottom: 20px;
  background: #f5f5f5;
}

/**/
.main_one {
  padding-bottom: 6px;
  width: 100%;
  background: #fff;
  border-radius: 10px;
  position: relative;
}

.main_one p:nth-child(1) {
  width: 100%;
  height: 74.5px;
  position: relative;
}

.main_one p:nth-child(1) img:nth-child(1) {
  margin-left: -52px;
  width: 105px;
  height: 105px;
  position: absolute;
  top: -31px;
  left: 50%;
}

.main_one p:nth-child(1) img:nth-child(2) {
  width: 12px;
  position: absolute;
  top: 57px;
  left: 66.5%;
}

.main_one p:nth-child(2) {
  margin-top: 10px;
  width: 100%;
  height: 17px;
  text-align: center;
  line-height: 17px;
  color: #333333;
  font-size: 15px;
  font-weight: bold;
}

.main_one p:nth-child(2) img {
  margin-right: 3px;
  width: 28px;
}

.main_one p:nth-child(3) {
  width: 100%;
  height: 20px;
  text-align: center;
  line-height: 20px;
  color: #999;
  font-size: 12px;
}

/**/
.main_two {
  margin-top: 10px;
  padding: 11px 0;
  width: 100%;
  height: 58px;
  background: #fff;
  border-radius: 10px;
}

.main_two p {
  float: left;
  width: 50%;
  height: 36px;
  position: relative;
}

.main_two p:nth-child(1) {
  border-right: 1px solid #ccc;
}

.main_two p span {
  display: inline-block;
  width: 100%;
  text-align: center;
}

.main_two p:nth-child(1) span:nth-child(1) {
  height: 15px;
  line-height: 15px;
  color: #333;
  font-size: 15px;
  font-weight: bold;
  position: absolute;
  top: 0;
}

.main_two p span:nth-child(2) {
  height: 15px;
  line-height: 15px;
  color: #333;
  font-size: 12px;
  position: absolute;
  top: 24px;
}

.main_two p:nth-child(2) span:nth-child(1),
.main_two p:nth-child(2) span:nth-child(2) {
  height: 15px;
  line-height: 15px;
  color: #333;
  font-size: 14px;
  font-weight: bold;
  position: absolute;
  top: 0;
}

.main_two p span:nth-child(3) {
  height: 15px;
  line-height: 15px;
  color: #333;
  font-size: 12px;
  position: absolute;
  top: 24px;
}

.main_three {
  width: 100%;
}

.main_three p {
  margin-top: 10px;
  padding-top: 22px;
  width: 48.65%;
  height: 65px;
  text-align: center;
  color: #333;
  font-size: 14px;
  font-weight: bold;
  background: #fff;
  border-radius: 10px;
  position: relative;
}

.main_three p:nth-child(odd) {
  float: left;
}

.main_three p:nth-child(even) {
  float: right;
}

.main_three p img {
  margin-right: 3px;
  position: absolute;
  left: 23%;
}

.main_three p span {
  margin-right: 3px;
  position: absolute;
  left: 43%;
}

/**/
.dai {
  display: none;
}

#file1 {
  margin-left: -75px;
  width: 150px;
  height: 100px;
  opacity: 0;
  position: absolute;
  top: -30px;
  left: 50%;
}
</style>
