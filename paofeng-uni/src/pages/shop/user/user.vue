<template>
  <view>
    <header class="mui-bar mui-bar-nav">
      <navigator url="/pages/shop/order/order/index" hover-class="other-navigator-hover">
        <a class="shouye link">
          <img src="@/static/image/shop/user/zhuye.png" alt="" />
        </a>
      </navigator>
      <h1 class="mui-title">个人中心</h1>
      <navigator url="/pages/shop/enter/message" hover-class="other-navigator-hover">
        <a class="message link">
          <img src="@/static/image/shop/order/xiaoxi.png" alt="" />
        </a>
      </navigator>
    </header>
    <div class="mui-content">
      <div class="main-top">
        <p>
          <img src="@/static/image/shop/user/touxiang.png" class="tx" />
          <img src="@/static/image/shop/user/bianji.png" alt="" />
          <input type="file" id="file1" name="file1" />
        </p>
        <p>
          <img src="@/static/image/shop/user/jinbi.png" alt="" />
          <span>余额</span>
          <b>566</b>
        </p>
        <p class="link" data-link="../../html/user/renZheng_ziLiao.html">
          <span>商家信息已验证</span><span>，点击查看</span>
        </p>
      </div>
      <div class="main_bot">
        <table>
          <tr>
            <td class="link" data-link="bangDing_pingTai.html">
              <img src="@/static/image/shop/user/geren_1.png" alt="" />
              <p>绑定外卖平台</p>
            </td>
            <td class="link" data-link="lianXi_fangShi.html">
              <img src="@/static/image/shop/user/geren_2.png" alt="" />
              <p>联系方式</p>
            </td>
            <td class="link" data-link="order_select.html">
              <img src="@/static/image/shop/user/geren_3.png" alt="" />
              <p>已完成订单查询</p>
            </td>
          </tr>
          <tr>
            <td class="link" data-link="yuE_chongZhi.html">
              <img src="@/static/image/shop/user/geren_4.png" alt="" />
              <p>余额充值</p>
            </td>
            <td class="link" data-link="pingTai_set.html">
              <img src="@/static/image/shop/user/geren_5.png" alt="" />
              <p>平台设置</p>
            </td>
            <td class="link" data-link="shangJia_apply.html">
              <img src="@/static/image/shop/user/geren_6.png" alt="" />
              <p>优质商家申请</p>
            </td>
          </tr>
        </table>
      </div>
    </div>
    <div class="tx_pop">
      <div class="tx_main">
        <p>拍照</p>
        <p>
          <input type="file" name="file" id="file" />
          <span>从手机相册中选择</span>
        </p>
        <p class="cancel">取消</p>
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
  padding: 0 15px !important;
  height: 44px;
  line-height: 44px;
  background: #6A4A23;
  box-shadow: none;
  -webkit-box-shadow: none;
}

.shouye,
.message {
  display: inline-block;
  width: 44px;
  height: 44px;
  position: relative;

}

.shouye img,
.message img {
  width: 16px;
  position: absolute;
  top: 13px;
}

.shouye {
  float: left;
}

.message {
  float: right;
}

.message img {
  right: 0;
}

.mui-title {
  color: #fff;
  font-size: 16px;
}

.mui-content {
  width: 100%;
  background: #fff;

}

/**/
.main-top {
  padding-top: 16px;
  width: 100%;
  height: 171px;
  background: url(../../../static/image/shop/user/background.png) no-repeat;
  background-size: 100% 171px;
}

.main-top p:nth-child(1) {
  padding: 3px;
  margin: auto;
  width: 67px;
  height: 67px;
  border-radius: 50%;
  background: #aa9579;
  position: relative;
}

.main-top p:nth-child(1) img:nth-child(1) {
  width: 61px;
  height: 61px;
  border-radius: 50%;
}

.main-top p:nth-child(1) img:nth-child(2) {
  width: 13px;
  height: 13px;
  position: absolute;
  right: 0;
  bottom: 6px;
  z-index: 2;
}

#file1 {
  margin-left: -40px;
  width: 80px;
  height: 80px;
  opacity: 0;
  position: absolute;
  top: 0;
  left: 50%;
}

.main-top p:nth-child(2) {
  width: 100%;
  height: 23px;
  line-height: 23px;
  text-align: center;
  color: #ffcc00;
  font-size: 13px;
}

.main-top p:nth-child(2) img {
  margin-right: 3px;
  width: 13px;
}

.main-top p:nth-child(2) b {
  margin-left: 6px;
  font-size: 14px;
}

.main-top p:nth-child(3) {
  width: 100%;
  height: 22px;
  text-align: center;
  line-height: 22px;
  font-size: 13px;
  color: #ccc;
}

.main-top p:nth-child(3) span:nth-child(2) {
  color: #ffcc00;
  font-size: 14px;
}

.main_bot {
  margin-top: 22px;
  width: 100%;
}

.main_bot table {
  width: 100%;
}

.main_bot table tr td {
  padding-top: 15px;
  width: 33.3%;
  height: 115px;
  text-align: center;
  border: 1px solid #dbdbdb;
}

.main_bot table tr td:nth-child(1),
.main_bot table tr td:nth-child(4) {
  border-left: none;
}

.main_bot table tr td:nth-child(3),
.main_bot table tr td:nth-child(6) {
  border-right: none;
}

.main_bot table tr td img {
  margin: auto;
  width: 40px;
  height: 40px;
}

.main_bot table tr td p {
  width: 100%;
  height: 32px;
  text-align: center;
  line-height: 32px;
  color: #333;
  font-size: 14px;
}

.tx_pop {
  display: none;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  position: absolute;
  top: 0;
  left: 0;
  z-index: 10;
}

.tx_main {
  width: 100%;
  background: #fff;
  position: absolute;
  left: 0;
  bottom: 0;
}

.tx_main p {
  width: 100%;
  height: 41px;
  text-align: center;
  line-height: 41px;
  color: #333333;
  font-size: 14px;
  border-bottom: 1px solid #dbdbdb;
  position: relative;
}

.tx_main p input {
  width: 100%;
  height: 38px;
  opacity: 0;
  position: absolute;
}

.tx_main p:last-child {
  border-bottom: none;
}
</style>
