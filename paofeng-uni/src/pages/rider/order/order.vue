<template>
  <view>
    <header class="mui-bar mui-bar-nav">
      <a class="left link">
        <navigator url="/pages/rider/user/user">
          <img src="@/static/image/rider/order/wode.png" />
        </navigator>
      </a>
      <h1 class="mui-title">
        <a class="mui-control-item b" :class="[{ 'item-actived': statusFlag === '1' }]" @click="initHandler('1')">
          新任务
        </a>
        <a class="mui-control-item b" :class="{ 'item-actived': statusFlag === '3' }" @click="initHandler('3')">
          待取货
        </a>
        <a class="mui-control-item b" :class="{ 'item-actived': statusFlag === '4' }" @click="initHandler('4')">
          配送中
        </a>
      </h1>
    </header>
    <div class="wkg" v-if="!checkPermission('order:order:take')">
      <p>暂时无法接单</p>
    </div>
    <!-- <div class="wkg" v-else-if="tableList.length === 0">
      <p>暂无订单</p>
    </div> -->
    <div class="mui-content" v-else>
      <div class="mui-slider" v-if="tableList.length">
        <div class="mui-slider-group">
          <div class="list-item" v-for="item in tableList" :key="item.orderId">
            <div class="item1_main_one" v-if="statusFlag !== '1'">
              <div class="item1_one_top">
                <p><img src="@/static/image/rider/order/logoxiao.png" /></p>
                <p><span>订单号</span><span>{{ item.orderId }}</span></p>
              </div>
            </div>
            <div class="item1_main_one">
              <div class="item1_two_top">
                <p>取</p>
                <p><span>{{ item.shopName }}</span><span class="mui-ellipsis">{{ item.pickupAddress }}</span></p>
                <p></p>
              </div>
              <div class="item1_two_bot">
                <p>送</p>
                <p>{{ item.customerAddress }}</p>
                <p></p>
              </div>
            </div>
            <div class="item1_main_three">
              <p>配送费用： <strong>{{ item.deliveryFee }}元</strong></p>
            </div>
            <div class="item1_main_three" v-if="item.status === '3'">
              <p>接单时间： <strong>{{ item.takeTime }}</strong></p>
            </div>
            <div class="item1_main_three" v-if="item.status === '4'">
              <p>取货时间： <strong>{{ item.pickupTime }}</strong></p>
            </div>
            <div class="item1_main_fore">
              <span v-if="statusFlag === '1'"></span>
              <span v-else class="btn" @click="btnCancelOrder(item)">取消订单</span>
              <span class="btn btn-succ" @click="takeOrderHandler(item)">{{ getStr() }}</span>
            </div>
          </div>
        </div>
      </div>
      <div class="wkg" v-else>
        <p>暂无订单</p>
      </div>
    </div>

    <!--取消出现的弹框-->
    <div class="quxiao_pop">
      <div class="quxiao">
        <p>订单被取消</p>
        <p>商家已取消该订单，请及时沟通</p>
        <p class="enter">确定</p>
      </div>
    </div>
    <!--若没有认证显示，否则不显示-->
    <navigator url="/pages/rider/enter/riderInfoSub">
      <nav class="mui-bar mui-bar-tab link" v-if="!checkPermission('order:order:take')">
        <p>您还未通过资料审核,不能进行接单</p>
        <p class="link">马上认证</p>
      </nav>
    </navigator>
  </view>
</template>

<script>
import { checkPermission } from "@/common/util";
import { riderTakeOrderList, takeOrder, riderList, takeOrderGoods, successOrder, cancelOrder } from "@/api/rider";

export default {
  data() {
    return {
      statusFlag: '1',
      keyObj: {
        '1': '抢单',
        '3': '确认取货',
        '4': '确认送达'
      },
      totalFlag: false,
      total: 0,
      tableList: [],
      pages: { pageNum: 1, pageSize: 5 }
    }
  },
  onLoad() {
    if (checkPermission('order:order:take')) {
      this.initHandler()
    }
  },
  onReachBottom() {
    const allTotal = this.pages.pageNum * this.pages.pageSize
    if (allTotal < this.total) {
      this.totalFlag = false;
      this.pages.pageNum++;
      //加载次数递加
      this.initHandler(); //请求更多数据列表
    } else {
      this.totalFlag = true;
      console.log('已加载全部数据')
    }
  },
  methods: {
    checkPermission,
    async initHandler(val) {
      if (val) {
        this.statusFlag = val
        this.pages.pageNum = 1
        this.totalFlag = false
        this.tableList = []
      }
      if (this.totalFlag) {
        return
      }
      let res
      if (this.statusFlag === '1') {
        res = await riderTakeOrderList(null, this.pages)
      } else {
        res = await riderList({ status: this.statusFlag }, this.pages)
      }
      if (res.code === 200) {
        this.tableList.push(...res.rows)
        this.total = res.total
      }
    },
    getStr() {
      return this.keyObj[this.statusFlag]
    },
    takeOrderHandler(item) {
      uni.showModal({
        title: '确认',
        content: `确认${this.getStr()}?`,
        success: async (res) => {
          if (res.confirm) {
            const data = { orderId: item.orderId }
            let res
            if (this.statusFlag === '1') {
              res = await takeOrder(data)
            } else if (this.statusFlag === '3') {
              res = await takeOrderGoods(data)
            } else {
              res = await successOrder(data)
            }
            if (res.code === 200) {
              uni.showToast({
                title: '操作成功！',
                duration: 2000,
              });
              this.tableList = this.tableList.filter(e => e.orderId !== data.orderId)
            } else {
              uni.showModal({
                title: '提示',
                content: res.msg || '系统异常!',
                showCancel: false
              })
            }
          }
        }
      });
    },
    btnCancelOrder(item) {
      uni.showModal({
        title: '确认',
        content: `取消订单?${['4'].includes(item.status) ? '会扣除部分费用!' : ''}`,
        success: (res) => {
          if (res.confirm) {
            cancelOrder({ orderId: item.orderId }).then(res => {
              if (res.code === 200) {
                uni.showToast({
                  title: '操作成功！',
                  duration: 2000,
                });
                this.tableList = this.tableList.filter(e => e.orderId !== item.orderId)
              } else {
                uni.showModal({
                  title: '提示',
                  content: res.msg,
                  showCancel: false
                })
              }
            })
          }
        }
      });
    }
  }
}
</script>

<style scoped>
.item-actived {
  border-bottom: 3px solid #6A4A23;
}

.mui-bar {
  padding: 0 3.125%;
  height: 45px;
  background: #fff;
  box-shadow: none;
  -webkit-box-shadow: none;
}

.mui-bar>a.left {
  display: inline-block;
  width: 44px;
  height: 44px;
}

.mui-bar>a.left img {
  margin-top: 15px;
  width: 15px;
}

.mui-bar .mui-title {
  right: 31px;
}

.mui-title {
  line-height: 41px;
}

.mui-title>a {
  display: inline-block;
  width: 25%;
  color: #333;
  font-size: 15px;
}

.mui-content {
  width: 100%;
  height: 100%;
  padding: 50px 0;
  background: #f5f5f5;
  /*margin-bottom: 50px;*/

}

/*/轮播///*/
.mui-slider {

  width: 100%;
  height: 100%;
}

.mui-slider-indicator .mui-indicator {
  margin: 1px 3px !important;
  width: 6px !important;
  height: 6px !important;
  box-shadow: none !important;
  -webkit-box-shadow: none !important;
}

.mui-slider .mui-slider-group {
  margin-top: 5px;
}

.mui-slider-indicator .mui-active.mui-indicator {
  background: #CE8546;
}

.mui-slider-indicator .mui-indicator {
  background: #fff;
}

.mui-slider-indicator {
  bottom: 6px;
}

.tiaojian {
  width: 100%;
  position: fixed;
  top: 0px;
  left: 0;
  z-index: 999;
}

.tiaojian_tit,
.tj {
  width: 100%;
  height: 40px;
  text-align: center;
  line-height: 40px;
  color: #333;
  font-size: 12px;
  background: #fff;
  border-top: 1px solid #ccc;
  border-bottom: 1px solid #ccc;
}

.tiaojian_tit img,
.tj img {
  float: none;
  margin: 0 5px;
  display: inline-block;
  width: 9px !important;

}

.tiaojian ul {
  display: none;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);

}

.tiaojian ul li {
  width: 100%;
  height: 200px;
  overflow: scroll;
  z-index: 999;
}

::-webkit-scrollbar {
  width: 0px;
  height: 0px;
  background-color: transparent;
}

.tiaojian ul li span {
  display: inline-block;
  width: 100%;
  height: 40px;
  text-align: center;
  line-height: 40px;
  color: #333;
  font-size: 12px;
  border-bottom: 1px solid #ccc;
  background: #fff;
}

.tiaojian ul li span:last-child {
  border: none;
}

.mui-scroll-wrapper {
  background: #f5f5f5;
}

.mui-scroll {
  /*margin-top: 10px;*/
}

.mui-scroll {
  padding-bottom: 60px;
  background: #f5f5f5;
}

.list-item {
  padding: 6px 12px;
  margin: 0 12px;
  margin-bottom: 10px;
  background: #fff;
  border-radius: 10px;
}

.item1_main {
  margin-bottom: 10px;
  padding: 12px;
  width: 100%;
  background: #fff;
  border-radius: 10px;
}

.item1_main_one {
  padding-top: 8px;
  border-bottom: 1px solid #ccc;
}

.item1_one_top {
  width: 100%;
  height: 50px;
}

.item1_one_top p {
  float: left;
}

.item1_one_top p:nth-child(1) {
  margin-right: 8px;
  width: 32px;
  height: 32px;
  border-radius: 50%;
  overflow: hidden;
}

.item1_one_top p:nth-child(1) img {
  width: 100%;
}

.item1_one_top p:nth-child(2) {
  width: 160px;
  height: 32px;
  position: relative;
}

.item1_one_top p:nth-child(2) span {
  display: inline-block;
  width: 100%;
  color: #666;
  font-size: 12px;
}

.item1_one_top p:nth-child(2) span:nth-child(1) {
  height: 15px;
  line-height: 15px;
  position: absolute;
  top: 1px;
}

.item1_one_top p:nth-child(2) span:nth-child(2) {
  height: 14px;
  line-height: 14px;
  position: absolute;
  top: 19px;
}

.item1_one_top p:nth-child(3) {
  float: right;
  width: 40px;
  height: 24px;
  text-align: center;
  line-height: 21px;
  color: #FF6666;
  font-size: 12px;
  border-radius: 30px;
  border: 1px dotted #FF6666;

}

/**/
.item1_one_mid {
  margin-top: 19px;
  width: 100%;
  height: 38px;
}

.item1_one_mid p {
  float: left;
  width: 50%;
  height: 38px;
  text-align: center;
}

.item1_one_mid p:nth-child(1) {
  border-right: 1px solid #ccc;
}

.item1_one_mid p span {
  display: inline-block;
  width: 100%;
  text-align: center;
}

.item1_one_mid p span:nth-child(1) {
  height: 18px;
  line-height: 18px;
  color: #333333;
  font-size: 12px;
  font-weight: bold;
}

.item1_one_mid p span:nth-child(1) b {
  font-size: 15px;
}

.item1_one_mid p span:nth-child(2) {
  height: 14px;
  line-height: 14px;
  color: #333333;
  font-size: 12px;
}

.item1_one_bot {
  width: 100%;
  height: 50px;
  text-align: center;
  line-height: 50px;
  color: #333333;
  font-size: 12px;
}

/**/
.item1_main_two {
  padding-top: 8px;
  width: 100%;
  border-bottom: 1px solid #ccc;
}

.item1_two_top {
  width: 100%;
  height: 45px;
}

.item1_two_top p {
  float: left;

}

.item1_two_top p:nth-child(1) {
  width: 18px;
  height: 24px;
  line-height: 24px;
  color: #6A4A23;
  font-size: 12px;
}

.item1_two_top p:nth-child(2),
.item1_two_bot p:nth-child(2) {
  width: 188px;
  position: relative;
}

.item1_two_top p:nth-child(2) span {
  display: inline-block;
  width: 100%;
}

.item1_two_top p:nth-child(2) span:nth-child(1) {
  height: 24px;
  line-height: 24px;
  color: #333333;
  font-size: 15px;
  font-weight: bold;
}

.item1_two_top p:nth-child(2) span:nth-child(2) {
  height: 16px;
  line-height: 16px;
  color: #999;
  font-size: 12px;
  position: absolute;
  top: 24px;
  left: 0;
}

.item1_two_top p:nth-child(3) {
  float: right;
  line-height: 24px;
  color: #666;
  font-size: 12px;
}

/**/
.item1_two_bot {
  width: 100%;
  height: 37px;
}

.item1_two_bot p {
  float: left;
}

.item1_two_bot p:nth-child(1) {
  width: 18px;
  line-height: 37px;
  color: #6A4A23;
  font-size: 12px;
}

.item1_two_bot p:nth-child(2) {
  line-height: 37px;
  color: #333333;
  font-size: 12px;
}

.item1_two_bot p:nth-child(3) {
  float: right;
  line-height: 37px;
  color: #666;
  font-size: 12px;
}

/**/
.item1_main_three {
  padding: 12px 0;
  width: 100%;
  height: 50px;
  border-bottom: 1px solid #ccc;
}

.item1_main_three p {
  color: #000;
}


.item1_main_fore {
  padding-top: 8px;
  width: 100%;
  display: flex;
  justify-content: space-between;
}

.item1_main_fore .btn {
  width: 120px;
  height: 33px;
  display: inline-block;
  text-align: center;
  line-height: 33px;
  color: #fff;
  font-size: 15px;
  background: #cfc4c4;
  border-radius: 30px;
}

.item1_main_fore .btn-succ {
  background: #6A4A23;
}

/*下拉提示*/
.mui-pull-bottom-wrapper {
  text-align: center;
}

/*上拉提示*/
.mui-bar~.mui-content .mui-fullscreen {
  top: 44px;
  height: auto;
}

.mui-pull-top-tips {
  position: absolute;
  top: -20px;
  left: 50%;
  margin-left: -25px;
  width: 40px;
  height: 40px;
  border-radius: 100%;
  z-index: 1;
}

.mui-bar~.mui-pull-top-tips {
  top: 24px;
}

.mui-pull-top-wrapper {
  width: 42px;
  height: 42px;
  display: block;
  text-align: center;
  background-color: #efeff4;
  border: 1px solid #ddd;
  border-radius: 25px;
  background-clip: padding-box;
  box-shadow: 0 4px 10px #bbb;
  overflow: hidden;
}

.mui-pull-top-tips.mui-transitioning {
  -webkit-transition-duration: 200ms;
  transition-duration: 200ms;
}

.mui-pull-top-tips .mui-pull-loading {
  margin: 0;
}

.mui-pull-top-wrapper .mui-icon,
.mui-pull-top-wrapper .mui-spinner {
  margin-top: 7px;
}

.mui-pull-bottom-tips {
  text-align: center;
  background-color: #f5f5f5;
  font-size: 15px;
  line-height: 40px;
  color: #777;
}

.mui-pull-top-canvas {
  overflow: hidden;
  background-color: #fafafa;
  border-radius: 40px;
  box-shadow: 0 4px 10px #bbb;
  width: 40px;
  height: 40px;
  margin: 0 auto;
}

.mui-pull-top-canvas canvas {
  width: 40px;
}

.mui-slider-indicator.mui-segmented-control {
  background-color: #efeff4;
}

/**/
.zhuangtai1 {
  display: none;
  padding-top: 15.625%;
  width: 15.625%;
  text-align: center;
  /*line-height: 50px;*/
  border-radius: 50%;
  background: #6A4A23;
  color: #fff;
  font-size: 15px;
  position: fixed;
  bottom: 25px;
  right: 12px;
  z-index: 999;
}

.zhuangtai {
  display: inline-block;
  width: 100%;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  -webkit-transform: translate(-50%, -50%);
}

/**/
.wkg {
  width: 100%;
  height: 238px;
  background: #f5f5f5;
  position: relative;
  top: 110px;
}

.wkg img {
  margin-left: -50px;
  width: 100px !important;
  position: absolute;
  left: 50%;

}

.wkg p {
  width: 100%;
  height: 42px;
  text-align: center;
  line-height: 42px;
  color: #666666;
  font-size: 12px;
  position: absolute;
  left: 0;
  bottom: 0;
}

/**/
nav {
  padding: 10px 13px !important;
  width: 100%;
  height: 50px !important;
  background: #494949 !important;
}

nav p:nth-child(1) {
  float: left;
  color: #fff;
  line-height: 28px;
  font-size: 12px;
}

nav p:nth-child(2) {
  float: right;
  color: #fff;
  font-size: 12px;
  width: 66px;
  height: 28px;
  text-align: center;
  line-height: 28px;
  border-radius: 30px;
  background: #6A4A23;
}

/**/
.qiangdan_pop,
.quxiao_pop,
.qiangdan_pop1,
.ysd_pop {
  display: none;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  position: absolute;
  top: 0;
  left: 0;
  z-index: 99;
}

.qiangdan,
.quxiao,
.qiangdan1,
.ysd {
  margin-left: -39.065%;
  margin-top: -75px;
  width: 78.125%;
  height: 150px;
  background: #fff;
  border-radius: 10px;
  position: fixed;
  top: 50%;
  left: 50%;
  /*transform: translate(-50%,-50%);
	-webkit-transform: translate(-50%,-50%);*/
}

.qiangdan p:nth-child(1),
.quxiao p:nth-child(1),
.ysd p:nth-child(1) {
  width: 100%;
  height: 60px;
  text-align: center;
  line-height: 60px;
  color: #333;
  font-size: 15px;
  font-weight: bold;

}

.qiangdan1 p:nth-child(1) {
  width: 100%;
  height: 100px;
  text-align: center;
  line-height: 100px;
  color: #333;
  font-size: 15px;
  font-weight: bold;
}

.qiangdan p:nth-child(2),
.quxiao p:nth-child(2) {
  width: 100%;
  height: 15px;
  text-align: center;
  line-height: 15px;
  color: #999;
  font-size: 12px;
}

.ysd p:nth-child(2) {
  width: 100%;
  height: 25px;
  text-align: center;
  line-height: 25px;
  color: #999;
  font-size: 12px;
}

.qiangdan p:nth-child(3),
.quxiao p:nth-child(3) {
  margin: auto;
  margin-top: 25px;
  width: 65px;
  height: 33px;
  text-align: center;
  line-height: 33px;
  color: #fff;
  font-size: 12px;
  background: #6A4A23;
  border-radius: 30px;
}

.qiangdan1 p:nth-child(2) {
  width: 100%;
  height: 50px;
}

.ysd p:nth-child(3) {
  width: 100%;
  height: 65px;
  line-height: 65px;
}

.qiangdan1 p:nth-child(2) a {
  float: left;
  display: inline-block;
  width: 50%;
  height: 49px;
  color: #333;
  font-size: 12px;
}

.ysd p:nth-child(3) a {
  float: left;
  display: inline-block;
  width: 50%;
  height: 49px;
  color: #333;
  font-size: 12px;
}

.qiangdan1 p:nth-child(2) a i {
  display: inline-block;
  font-style: normal;
  width: 65px;
  height: 33px;
  text-align: center;
  line-height: 33px;
  color: #fff;
  font-size: 12px;
  background: #6A4A23;
  border-radius: 30px;
}

.ysd p:nth-child(3) a i {
  display: inline-block;
  font-style: normal;
  width: 65px;
  height: 33px;
  text-align: center;
  line-height: 33px;
  color: #fff;
  font-size: 12px;
  background: #6A4A23;
  border-radius: 30px;
}

.qiangdan1 p:nth-child(2) a:nth-child(1) i {
  margin-left: 33%;
}

.ysd p:nth-child(3) a:nth-child(1) i {
  margin-left: 33%;
}

.qiangdan1 p:nth-child(2) a:nth-child(2) i {
  margin-left: 15%;
}

.ysd p:nth-child(3) a:nth-child(2) i {
  margin-left: 15%;
}
</style>
