<template>
  <view>
    <header class="mui-bar mui-bar-nav">
      <a class="mui-action-back">
        <img src="@/static/image/shop/order/back.png" alt="" />
      </a>
      <h1 class="mui-title">完善信息</h1>
    </header>
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
          <span>店铺名称：</span>
          <span><input type="text" v-model="formObj.shopName" /></span>
          <span></span>
        </p>
        <!-- <p class="link" data-link="../../html/order/keRen_area.html" style="justify-content: space-between;">
          <span class="list-item">
            <span>店铺地址：</span>
            <span class="lb">请选择地址</span>
          </span>
          <img class="icon-right" src="@/static/image/shop/user/xuanfen.png" />
        </p> -->
        <p>
          <span>店铺地址：</span>
          <span><input type="text" v-model="formObj.address" placeholder="请输入详细地址" /></span>
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
                <uni-file-picker class="file-input" limit="9" v-model="formObj.shopPhoto"
                  @select="e => selectFile(e, 'shopPhoto')">
                  <view></view>
                </uni-file-picker>
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
                <uni-file-picker class="file-input" limit="1" v-model="formObj.cardPhotoZ"
                  @select="e => selectFile(e, 'cardPhotoZ')">
                  <view></view>
                </uni-file-picker>
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
                <uni-file-picker class="file-input" limit="1" v-model="formObj.cardPhotoB"
                  @select="e => selectFile(e, 'cardPhotoB')">
                  <view></view>
                </uni-file-picker>
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
                <uni-file-picker class="file-input" limit="9" v-model="formObj.businessLicense"
                  @select="e => selectFile(e, 'businessLicense')">
                  <view></view>
                </uni-file-picker>
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
                <uni-file-picker class="file-input" limit="9" v-model="formObj.cateringLicense"
                  @select="e => selectFile(e, 'cateringLicense')">
                  <view></view>
                </uni-file-picker>
              </div>
            </div>
            <span>餐饮许可证</span>
          </div>
        </div>
      </div>
      <div class="xieyi">
        <uni-data-checkbox v-model="pact" :localdata="pactArr" multiple />
        <span></span>
      </div>
      <p class="btn link" style="margin-bottom: 10px;" @click="subHandler" data-link="../../html/enter/shenHe_ing.html">
        提交审核</p>
    </div>
  </view>
</template>

<script>
import { uniUploadImage } from "@/api/upload";
import { shopAdd } from "@/api/shop";

export default {
  data() {
    return {
      formObj: {
        shopName: '',
        address: '',
        shopPhoto: [],
        cardPhotoZ: [],
        cardPhotoB: [],
        businessLicense: [],
        cateringLicense: [],
      },
      pact: '',
      pactArr: [{
        text: '我已阅读并同意《捷速外卖平台商家协议》',
        value: 1
      }]
    }
  },
  onLoad() {
  },
  methods: {
    selectFile(e, field) {
      console.log(e, field)
      const uploadArr = e.tempFilePaths.map(m => uniUploadImage(m))
      Promise.all(uploadArr).then(res => {
        this.formObj[field] = res.reduce((a, c) => {
          if (c.code === 200) {
            const arr = c.data.name.split('.')
            const extname = arr[arr.length - 1]
            a.push({
              ...c.data,
              extname
            })
          }
          return a
        }, [])
      })
    },
    subHandler() {
      if (!Object.values(this.formObj).every(e => e.length)) {
        uni.showModal({
          title: '提示',
          content: '请检查未填内容',
          showCancel: false
        })
        return
      }

      if (!this.pact) {
        uni.showModal({
          title: '提示',
          content: '请阅读协议',
          showCancel: false
        })
        return
      }

      const data = {
        shopName: this.formObj.shopName,
        address: this.formObj.address,
        shopPhoto: JSON.stringify(this.formObj.shopPhoto),
        cardPhotoZ: JSON.stringify(this.formObj.cardPhotoZ),
        cardPhotoB: JSON.stringify(this.formObj.cardPhotoB),
        businessLicense: JSON.stringify(this.formObj.businessLicense),
        cateringLicense: JSON.stringify(this.formObj.cateringLicense)
      }

      shopAdd(data).then(res => {
        if (res.code === 200) {
          uni.showToast({
            title: '操作成功！',
            duration: 2000,
          });
          setTimeout(() => {
            uni.redirectTo({ url: '/pages/shop/enter/shopInfoProess' })
          }, 1000);
        }
      })

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
  width: 100%;
  padding-bottom: 20px;
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
  display: flex;
  align-items: center;
  width: 100%;
  height: 41px;
  line-height: 41px;
  font-size: 14px;
  border-bottom: 1px dotted #dbdbdb;
  position: relative;
  margin-bottom: 0;
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

.wzz_list .list-item {
  display: flex;
  align-items: center;
}

.wzz_list .icon-right {
  width: 6px;
  height: 10px;
  justify-items: flex-end;
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
  display: flex;
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

.z_file .file-input {
  height: 100%;
  height: 100%;
}

.file-input /deep/ .uni-file-picker,
.file-input /deep/ .uni-file-picker__container,
.file-input /deep/ .file-picker__box {
  height: 100% !important;
  width: 100% !important;
}
</style>
