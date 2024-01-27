<template>
  <div class="mui-content">
    <div class="jinDu">
      <img src="@/static/image/shop/enter/jingdu_2.png" />
      <p>
        <span>完善信息</span>
        <span>审核中</span>
      </p>
    </div>
    <div class="tip">
      <img src="@/static/image/shop/enter/renwu_1.png" alt="" />
      <p>提交成功，请耐心等待...</p>
    </div>
    <!-- <div class="tip">
      <img src="@/static/image/shop/enter/renwu_2.png" alt="" />
      <p>恭喜您，审核通过！</p>
    </div> -->
    <!--<p class="btn link" data-link="../../html/order/order.html">去主页看看</p>-->
  </div>
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
  width: 100%;
  margin-bottom: 20px;
  background: #f5f5f5;
}

/**/
.jinDu {
  margin-bottom: 10px;
  padding-top: 10px;
  width: 100%;
}

.jinDu img {
  margin-left: -30.6%;
  width: 61.565%;
  max-height: 25px;
  position: absolute;
  left: 50%;

}

.jinDu p {
  margin-top: 6.565%;
  width: 100%;
  height: 20px;
  line-height: 20px;
  color: #666666;
  font-size: 14px;
}

.jinDu p span {
  float: left;
  display: inline-block;
  width: 50%;
  height: 20px;
  /*text-align: center;*/
  line-height: 20px;
}

.jinDu p span:nth-child(1) {
  color: #6A4A23;
  padding-left: 14%;
}

.jinDu p span:nth-child(2) {
  padding-left: 23%;
}

.tip {
  margin-top: 65px;
  width: 100%;
  text-align: center;
}

.tip img {
  margin: auto;
  width: 81px;
  height: 91px;
}

.tip p {
  width: 100%;
  height: 33px;
  text-align: center;
  line-height: 33px;
  color: #6A4A23;
  font-size: 14px;
}

/**/
.btn {
  margin-left: -39.219%;
  width: 78.438%;
  height: 35px;
  text-align: center;
  line-height: 35px;
  color: #fff;
  font-size: 15px;
  border-radius: 30px;
  background: #6A4A23;
  position: absolute;
  left: 50%;
  bottom: 65px;
}
</style>
