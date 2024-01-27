<template>
  <div class="mui-content">
    <div class="jinDu">
      <img src="@/static/image/shop/enter/jingdu_1.png" />
      <p>
        <span>完善信息</span>
        <span>审核中</span>
      </p>
    </div>
    <div class="wzz_list">
      <p>
        <span>联系人：</span>
        <span><input type="text" value="" id="name" /></span>
        <span></span>
      </p>
      <p>
        <span>店铺名称：</span>
        <span><input type="text" value="" id="name" /></span>
        <span></span>
      </p>
      <p class="link" data-link="../../html/order/keRen_area.html">
        <span>店铺地址：</span>
        <!--下面是提示字体颜色为666，当选中之后返回该页面字体颜色变为333-->
        <span class="lb">请选择地址</span>
        <!---->
        <span><img src="@/static/image/shop/user/xuanfen.png" /></span>
      </p>
      <p>
        <span>详细地址：</span>
        <span><input type="text" value="" placeholder="请输入详细地址" /></span>
        <span></span>
      </p>
    </div>
    <div class="main_top">
      <p class="tit">请上传店铺门头与分组人合照：</p>
      <div>
        <div class="con">
          <!--    照片添加    -->
          <div class="z_photo">
            <div class="z_file">
              <input type="file" name="file" id="file" value="" accept="image/*" multiple />
            </div>
          </div>
          <span>真实照片</span>
        </div>
      </div>
    </div>
    <div class="main_top">
      <p class="tit">请上传身份证正面照片：</p>
      <div>
        <p>
          <img src="@/static/image/shop/enter/sfz_07.png" alt="" />
          <span>示例</span>
        </p>
        <div class="con">
          <!--    照片添加    -->
          <div class="z_photo">
            <div class="z_file">
              <input type="file" name="file1" id="file1" value="" accept="image/*" multiple />
            </div>
          </div>
          <span>正面</span>
        </div>
      </div>
    </div>
    <div class="main_top">
      <p class="tit">请上传身份证背面照片：</p>
      <div>
        <p>
          <img src="@/static/image/shop/enter/sfz_10.png" alt="" />
          <span>示例</span>
        </p>
        <div class="con">
          <div class="z_photo">
            <div class="z_file">
              <input type="file" name="file2" id="file2" value="" accept="image/*" multiple />
            </div>
          </div>
          <span>背面</span>
        </div>
      </div>
    </div>
    <div class="main_top">
      <p class="tit mui-ellipsis">请上传营业执照照片<span>(非必须,但上传可提高审核通过率)</span></p>
      <div>
        <div class="con">
          <div class="z_photo">
            <div class="z_file">
              <input type="file" name="file3" id="file3" value="" accept="image/*" multiple />
            </div>
          </div>
          <span>营业执照</span>
        </div>
      </div>
    </div>
    <div class="main_top">
      <p class="tit mui-ellipsis">请上传餐饮许可证照片<span>(非必须,但上传可提高审核通过率)</span></p>
      <div>
        <div class="con">
          <div class="z_photo">
            <div class="z_file">
              <input type="file" name="file4" id="file4" value="" accept="image/*" multiple />
            </div>
          </div>
          <span>餐饮许可证</span>
        </div>
      </div>
    </div>
    <div class="xieyi">
      <span class="dui"></span>
      <span>我已阅读并同意</span>
      <span>《捷速外卖平台商家协议》</span>
    </div>
    <p class="btn link" data-link="../../html/enter/shenHe_ing.html">提交审核</p>
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

/**/
.wzz_list {
  padding: 0 15px;
  width: 100%;
  background: #fff;
  border-bottom: 1px solid #dbdbdb;
}

.wzz_list p {
  width: 100%;
  height: 41px;
  line-height: 41px;
  font-size: 14px;
  border-bottom: 1px dotted #dbdbdb;
  position: relative;
}

.wzz_list p:last-child {
  border-bottom: none;
}

.wzz_list p span:nth-child(1) {
  color: #333;
}

.wzz_list p span:nth-child(2) {
  color: #666;
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
  padding: 0 15px;
  width: 100%;
  background: #fff;
  border-top: 1px solid #dbdbdb;
  border-bottom: 1px solid #dbdbdb;
}

.main_top>p {
  width: 100%;
  height: 30px;
  line-height: 30px;
  color: #333;
  font-size: 14px;
}

.tit span {
  color: #cc3333;
}

.main_top>div {
  margin-bottom: 3px;
  width: 100%;
  height: 90px;
}

.main_top>div p {
  float: left;
  width: 96px;
}

.main_top>div p img {
  width: 96px;
  height: 60px;
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

.main_top>div div.con {
  float: left;
  width: 96px;
}

.main_top>div div.con>span {
  display: inline-block;
  width: 100%;
  height: 30px;
  text-align: center;
  line-height: 30px;
  color: #666;
  font-size: 14px;
}

.btn {
  margin: auto;
  width: 78.438%;
  height: 35px;
  text-align: center;
  line-height: 35px;
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
  font-size: 16px;
}

.mui-poppicker-body {
  height: 150px;
}

/**/
.xieyi {
  margin-bottom: 5px;
  padding: 0 15px;
  padding-left: 38px;
  width: 100%;
  height: 35px;
  line-height: 35px;
  color: #999999;
  font-size: 12px;
  position: relative;
}

.xieyi span:nth-child(1) {
  display: inline-block;
  width: 15px;
  height: 15px;
  position: absolute;
  top: 10px;
  left: 15px;
  background: url(../../../static/image/shop/order/xuankuang.png) no-repeat;
  background-size: 15px 15px;
}

.xieyi span:nth-child(1) img {
  width: 21px;
  position: absolute;
  top: -6px;
  left: -3px;
}

.xieyi span:nth-child(3) {
  color: #6A4A23;
}

/**/
.z_photo {
  position: relative;
  width: 96px;
  height: 60px;
}

.z_file {
  width: 96px;
  height: 60px;
  background: url(../../../static/image/shop/enter/sfz_04.png) no-repeat 3.125% top;
  background-size: 100% 100%;
  position: absolute;
  bottom: 0;
}

#file,
#file1,
#file2,
#file3,
#file4 {
  opacity: 0;
  width: 100%;
  height: 100%;
}
</style>
