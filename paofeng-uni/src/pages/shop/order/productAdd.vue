<template>
  <div class="mui-content">
    <div class="message_list">
      <!--<p>
					<span class="ms">商品信息</span>
					<span class="mui-ellipsis mc">西红柿鸡蛋盖浇饭</span>
					<span>x1</span>
					<span>￥10</span>
				</p>-->
    </div>
    <div class="main_one">
      <p class="one_list">
        <span class="ms">商品名称</span>
        <input type="text" placeholder="请输入商品名称" class="spmc" />
      </p>
      <p class="one_list">
        <span class="ms">商品数量</span>
        <input type="number" placeholder="请输入商品数量" class="spsl" />
      </p>
      <p class="one_list">
        <span class="ms">商品单价</span>
        <input type="text" placeholder="请输入商品单价（单位元）" class="spdj" />
      </p>
    </div>
    <div class="contain">
      <img src="@/static/image/shop/order/tianjia_2.png" />
      <span>继续添加商品</span>
    </div>
    <div class="btn link" data-link="order_msg_import.html">
      <p>保存</p>
    </div>
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
.message_list {
  margin-bottom: 0px;
  width: 100%;
  background: #fff;
  border-bottom: 1px solid #dbdbdb;
}

.message_list p {
  padding: 0 15px;
  width: 100%;
  height: 42px;
  border-bottom: 1px dotted #dbdbdb;
  position: relative;
}

.message_list p:last-child {
  border: none;
}

.message_list p span {
  float: left;
  display: block;
  width: 13%;
  height: 42px;
  line-height: 42px;
  font-size: 14px;
  color: #333;
}

.message_list p span:nth-child(1) {
  color: #666;
  width: 70px;
}

.message_list p span:nth-child(2) {
  width: 49%;
}

.contain {
  float: right;
  margin-top: -10px;
  width: 40%;
  height: 40px;
  line-height: 30px;
  color: #6a4a23;
  font-size: 14px;
  position: relative;
}

.contain img {
  width: 20px;
  position: absolute;
  right: 105px;
  top: 9px;
}

.contain span {
  position: absolute;
  right: 15px;
  top: 5px;
}

.delete {
  text-align: center;
  border-bottom: 1px solid #fff;
  position: absolute;
  right: 0;
  top: 0;
  background: #CC3333;
  color: #fff !important;
}

.btn {
  width: 100%;
  height: 40px;
  position: absolute;
  bottom: 30px;
}

.btn p {
  margin: auto;
  width: 78%;
  height: 40px;
  text-align: center;
  line-height: 40px;
  color: #fff;
  font-size: 15px;
  background: #6A4A23;
  border-radius: 30px;

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
}</style>
