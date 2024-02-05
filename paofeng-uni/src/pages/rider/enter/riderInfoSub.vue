<template>
  <view>
    <header class="mui-bar mui-bar-nav">
      <a class="mui-action-back">
        <img src="@/static/image/rider/enter/icon_back@2x.png" alt="" />
      </a>
      <h1 class="mui-title">完善资料</h1>
    </header>
    <div class="mui-content">
      <div class="jinDu">
        <img src="@/static/image/rider/enter/wsxx.png" />
      </div>
      <div class="tip">
        <p>1.完善个人资料，以便于信息审核；</p>
        <p>2.认证资料不会公开给任何组织和个人；</p>
        <p>3.审核通过后，信息无法修改，如需帮助请致电客服：<a href="tel:0000000000">000-000-0000</a></p>
      </div>
      <div class="wzz_list">
        <p>
          <span>姓名：</span>
          <input type="text" value="" id="name" />
        </p>
        <p class="xingbie">
          <span class="bot1">性别：</span>
          <span class="bot2"></span>
        </p>
        <p>
          <span>身份证号：</span>
          <span class="lb"><input type="text" value="" id="sfzh" /></span>
          <span></span>
        </p>
        <!-- <p class="link" data-link="keRen_area.html">
          <span>居住地址：</span>
          <span></span>
          <span><img src="@/static/image/rider/enter/right.png" /></span>
        </p> -->
        <p>
          <span>第二联系人：</span>
          <span class="lb"><input type="text" value="" id="lxr" /></span>
          <span></span>
        </p>
        <p>
          <span>第二联系人电话：</span>
          <span class="lb"><input type="num " value="" id="phone" style="width: 60%;" /></span>
          <span></span>
        </p>
      </div>
      <div class="main_top">
        <p class="tit">手持身份证正面照片</p>
        <uni-file-picker class="file-input" limit="1">
        </uni-file-picker>
      </div>
      <div class="main_top">
        <p class="tit">身份证正面：</p>
        <div class="form-item-file">
          <img class="demo-img" src="@/static/image/rider/enter/sfz1.png" alt="" />
          <div class="file-input">
            <uni-file-picker limit="1">
            </uni-file-picker>
          </div>
        </div>
      </div>
      <div class="main_top">
        <p class="tit">身份证背面</p>
        <div class="form-item-file">
          <img class="demo-img" src="@/static/image/rider/enter/sfz2.png" alt="" />
          <div class="file-input">
            <uni-file-picker limit="1">
            </uni-file-picker>
          </div>
        </div>
      </div>
      <div class="btn link" data-link="shenHe_ing.html">确认提交</div>
    </div>
    <div class="xingbie_pop">
      <div>
        <p class="nan">
          <span class="xb_txt">男</span>
          <span class="radio">
            <img src="@/static/image/rider/enter/checked.png" class="ok" />
          </span>
        </p>
        <p class="nan">
          <span class="xb_txt">女</span>
          <span class="radio"></span>
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
  padding: 0 11px;
  width: 100%;
  margin-bottom: 20px;
  background: #f5f5f5;
}

.file-input {
  height: 80px;
  width: 80px;
  margin-left: 20px;
}

.file-input /deep/ .uni-file-picker,
.file-input /deep/ .uni-file-picker__container,
.file-input /deep/ .file-picker__box {
  height: 100% !important;
  width: 100% !important;
}

/**/
.jinDu {
  margin-bottom: 16px;
  padding-top: 16px;
  width: 100%;
  text-align: center;
}

.jinDu img {
  margin: auto;
  width: 58.438%;

}

.jinDu p {
  margin-top: 6.565%;
  width: 100%;
  height: 20px;
  line-height: 20px;
  color: #666666;
  font-size: 12px;
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

/**/
.tip {
  padding: 17px 11px;
  margin-bottom: 10px;
  width: 100%;
  background: #fff;
  border-radius: 10px;
}

.tip p {
  width: 100%;
  line-height: 18px;
  color: #333333;
  font-size: 12px;
}

.tip p a {
  color: #ff6666;
  font-size: 12px;
}

/**/
.wzz_list {
  padding: 0 11px;
  width: 100%;
  background: #fff;
  border-bottom: 1px solid #dbdbdb;
  border-radius: 10px;
}

.wzz_list p {
  width: 100%;
  height: 45px;
  line-height: 45px;
  font-size: 12px;
  border-bottom: 1px solid #dbdbdb;
  position: relative;
  display: flex;
  align-items: center;
}

.wzz_list p:last-child {
  border-bottom: none;
}

.wzz_list p span:nth-child(1) {
  color: #333;
}

.wzz_list p span:nth-child(2) {
  color: #333;
}

.wzz_list p span:nth-child(2) input {
  padding: 0;
  margin: 0;
  width: 70%;
  height: 28px;
  line-height: 30px;
  color: #333;
  font-size: 13px;
  border: none;
}

.wzz_list p span:nth-child(3) {
  display: inline-block;
  width: 6px;
  height: 10px;
  position: absolute;
  top: 0;
  right: 0;
}

.wzz_list p span:nth-child(3) img {
  width: 6px;
  height: 10px;
}

/**/
.main_top {
  margin-top: 10px;
  padding: 0 11px;
  width: 100%;
  background: #fff;
  border-radius: 10px;
}

.form-item-file {
  display: flex;
  align-items: flex-start;
}

.main_top>p {
  width: 100%;
  height: 35px;
  line-height: 35px;
  color: #333;
  font-size: 12px;
}

.tit span {
  color: #cc3333;
}

.demo-img {
  width: 70px;
  object-fit: contain;
}

.main_top>div p span {
  margin-top: -4px;
  display: inline-block;
  width: 100%;
  height: 30px;
  text-align: center;
  line-height: 30px;
  color: #666;
  font-size: 13px;
}

.btn {
  margin: auto;
  margin-top: 11px;
  width: 67.115%;
  height: 40px;
  text-align: center;
  line-height: 40px;
  color: #fff;
  font-size: 15px;
  border-radius: 30px;
  background: #6A4A23;
}

/**/
.mui-poppicker {
  background: #fff;
}

.mui-picker {
  background: #fff;
}

.mui-pciker-list li {
  color: #333333;
  font-size: 14px;
}

.mui-poppicker-body {
  height: 150px;
}

/**/
.z_photo {
  position: relative;
  width: 44px;
  height: 44px;
}

/**/
.xingbie_pop {
  display: none;
  /*padding: 0 12.5%;*/
  width: 100%;
  background: rgba(0, 0, 0, 0.5);
  position: absolute;
  top: 0;
  left: 0;
}

.xingbie_pop div {
  padding: 30px;
  margin-top: -75px;
  margin-left: -37.5%;
  width: 75%;
  height: 150px;
  position: fixed;
  top: 50%;
  left: 50%;
  background: #fff;
  border-radius: 6px;
}

.xingbie_pop div p {
  padding: 15px 0;
  width: 100%;
  height: 45px;
  line-height: 45px;
  border-bottom: 1px solid #ccc;
  position: relative;
}

.xingbie_pop div p:last-child {
  border: none;
}

.xingbie_pop div p span:nth-child(1) {
  color: #333333;
  font-size: 15px;
  position: absolute;
  top: 0;
  left: 0;
}

.xingbie_pop div p span.radio {
  margin-top: 15px;
  width: 15px;
  height: 15px;
  position: absolute;
  top: 0;
  right: 0 !important;
  border: 1px solid #ccc;
  border-radius: 50%;
}

.ok {
  width: 15px;
  position: absolute;
  top: 0;
  left: 0;
}
</style>
