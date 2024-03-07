<template>
  <view>
    <header class="mui-bar mui-bar-nav">
      <a class="mui-action-back">
        <img src="@/static/image/shop/order/back.png" alt="" />
      </a>
      <h1 class="mui-title">订单信息录入</h1>
      <navigator url="/pages/shop/order/payRole">
        <a class="message link">
          收费规则
        </a>
      </navigator>
    </header>
    <div class="mui-content">
      <div class="main_one">
        <p class="one_list">
          <span class="ms">客人姓名</span>
          <input type="text" v-model="formObj.customerName" placeholder="请输入客人姓名" />
        </p>
        <p class="one_list">
          <span class="ms">客人电话</span>
          <input type="text" v-model="formObj.customerPhone" placeholder="请输入客人电话" />
        </p>
        <p class="one_list">
          <span class="ms">详细地址</span>
          <input type="text" v-model="formObj.customerAddress" placeholder="xxx市xxx街道xxx小区15楼302"
            class="mui-ellipsis" />
        </p>
        <!-- <p class="one_list">
          <span class="ms">订单来源</span>
          <input type="text" placeholder="请输入订单来源" />
        </p> -->
        <p class="one_list">
          <span class="ms">商品价值(元)</span>
          <input type="number" v-model="formObj.productMoney" placeholder="请输入商品价格" class="pri" />
        </p>
        <p class="one_list">
          <span class="ms">配送费(元)</span>
          <input type="number" v-model="formObj.deliveryFee" placeholder="请输入商品价格" class="pri" />
        </p>
        <p class="one_list">
          <span class="ms">备注</span>
          <input type="text" v-model="formObj.remark" placeholder="捎句话给配送员" class="mui-ellipsis" />
        </p>
      </div>
      <p class="btn">
        <span class="link" @click="subHandler()">完成录入</span>
        <span class="link" @click="subHandler(1)">继续录入</span>
      </p>
    </div>
  </view>
</template>

<script>
import { orderAdd } from "@/api/shop";
const formDef = {
  customerName: '',
  customerPhone: '',
  customerAddress: '',
  deliveryFee: 1,
  productMoney: null,
  remark: ''
}
export default {
  data() {
    return {
      formObj: JSON.parse(JSON.stringify(formDef))
    }
  },
  onLoad() {
  },
  methods: {
    async subHandler(e) {
      if (!Object.values(this.formObj).every(e =>
        typeof e === 'number' ? e >= 0 : e
      )) {
        uni.showModal({
          title: '提示',
          content: '请检查未填内容',
          showCancel: false
        })
        return
      }
      if (!/^1[0-9]{10}$/.test(this.formObj.customerPhone)) {
        uni.showModal({
          title: '提示',
          content: '请检查手机号正确',
          showCancel: false
        })
        return
      }
      try {
        const res = await orderAdd(this.formObj)
        if (res.code === 200) {
          uni.showToast({
            title: '操作成功！',
            duration: 2000,
          });
          if (e) {
            // 继续录入
            this.formObj = JSON.parse(JSON.stringify(formDef))
          } else {
            setTimeout(() => {
              uni.navigateBack()
            }, 1000);
          }
        }

      } catch (error) {
      }
    }
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

.mui-action-back,
.message {
  display: inline-block;
  height: 44px;
  position: relative;

}

.mui-action-back img {
  width: 9px;
  position: absolute;
  top: 14px;
}

.mui-action-back {
  float: left;
  width: 44px;
}

.message {
  float: right;
  color: #FFCC00;
  font-size: 14px;
}

.mui-title {
  color: #fff;
  font-size: 16px;
}

.mui-content {
  width: 100%;
  background: #f5f5f5;

}

.main_one {
  padding: 0 15px;
  margin-bottom: 10px;
  width: 100%;
  background: #fff;
  border-bottom: 1px solid #dbdbdb;
  border-top: 1px solid #dbdbdb;
  position: relative;

}

.one_list1 {
  height: auto !important;
}

.spxx {
  float: left;
  min-height: 42px;
  color: #666;
}

.spxx p {
  width: 100%;
  height: 30px;
  line-height: 30px;
}

.spxx p:first-child {
  margin-top: 6px;
}

.spxx p span {
  display: inline-block;
  float: left;
  color: #333;
  width: 20%;
  text-align: center;
}

.spxx p span:nth-child(1) {
  width: 60%;
}

.main_one .one_list {
  width: 100%;
  height: 42px;
  line-height: 42px;
  font-size: 14px;
  border-bottom: 1px dotted #dbdbdb;
  margin-bottom: 0;

}

.main_one p:last-child {
  border-bottom: none;
}

.main_one .one_list span {
  float: left;
  display: block;
}

/**/
.ms {
  display: inline-block;
  color: #666;
  width: 70px;
  font-size: 14px;
}

.txt {
  display: inline-block;
  width: 205px;
  height: 40px;
  line-height: 40px;
  color: #666;
  font-size: 14px;
  position: relative;
}

.txt>img {
  margin-right: 10px;
  margin-top: 10px;
}

.txt>span {
  display: inline-block !important;
  float: none !important;
  color: #333;
  position: absolute;
  top: 0px;
}

/**/
.main_one p.one_list .ms {
  height: 42px;
  line-height: 42px;
}

.main_one p.one_list input {
  padding: 0;
  margin: 0;
  padding-top: 2px;
  width: 205px;
  height: 40px;
  line-height: 40px;
  color: #333333;
  font-size: 14px;
  border: none;
}

.main_one p.one_list>img {
  float: right;
  margin-top: 14px;
  width: 7px;
}

.tianjia {
  padding-right: 15px;
  width: 42px;
  height: 42px;
  position: absolute;
  right: 0;
  top: 0;

}

.tianjia img {
  margin-top: 9px;
  width: 25px !important;

}

.spxx {
  width: 170px !important;
}

.btn {
  padding: 14px 15px;
  width: 100%;
  height: 61px;
}

.btn span {
  display: inline-block;
  float: right;
  margin-left: 10px;
  width: 80px;
  height: 29px;
  text-align: center;
  line-height: 29px;
  font-size: 14px;
  border-radius: 6px;
  background: #6A4A23;
  color: #fff;

}

.sum {
  margin-top: 3px;
  float: left;
  color: #333333;
  font-size: 14px;
}

.sum b {
  color: #CC3333;
  font-weight: 100;
}

/*//////订单来源选择、备注选择/////////*/
.pingTai_pop,
.beizhu_pop {
  display: none;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  position: absolute;
  top: 0;
  left: 0;
  z-index: 10;
}

.pingTai,
.beizhu {
  padding: 0 12px;
  width: 100%;
  background: #fff;
  position: absolute;
  left: 0;
  bottom: 0;
}

.pingTai p,
.beizhu p {
  width: 100%;
  height: 44px;
  text-align: center;
  line-height: 44px;
  color: #333;
  font-size: 14px;
  position: relative;
  border-bottom: 1px dotted #dbdbdb;
}

.pingTai p:last-child,
.beizhu p:last-child {
  border-bottom: none;
}

.pingTai p img {
  width: 20px;
  height: 20px;
  position: absolute;
  left: 38%;
  top: 12px;
}

.pingTai p span {
  position: absolute;
  left: 48%;
  top: 0px;
}
</style>
