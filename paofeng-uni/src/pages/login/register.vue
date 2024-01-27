<template>
  <div class="mui-content">
    <div class="logo">
      <img src="@/static/image/shop/enter/logo.png" alt="" />
    </div>
    <div class="shuRu">
      <div class="input-border">
        <img src="@/static/image/shop/enter/denglu_1.png" alt="" />
        <input type="number" v-model="registerForm.phone" placeholder="请输入手机号" />
      </div>
      <div class="input-border">
        <img src="@/static/image/shop/enter/denglu_3.png" />
        <input type="text" id="Vcode" placeholder="请输入验证码" style="width: 110px;" />
        <span class="send">获取验证码</span>
      </div>
      <div class="input-border">
        <img src="@/static/image/shop/enter/denglu_2.png" />
        <input type="password" v-model="registerForm.password" placeholder="请输入登录密码" />
      </div>
      <div class="input-border">
        <img src="@/static/image/shop/enter/denglu_2.png" />
        <input type="password" v-model="registerForm.passwordCom" placeholder="请再次设置登录密码" />
      </div>
    </div>
    <div class="enter_register" @click="sendRegister">
      <p class="register">注册</p>
    </div>
    <navigator url="/pages/login/login" open-type="redirect" hover-class="other-navigator-hover">
      <div class="forget link">
        返回登录
      </div>
    </navigator>
    <img src="@/static/image/shop/enter/bei_jing.png" alt="" class="bg" />
    <p class="xieyi">注册表示您已同意平台的相关协议</p>
  </div>
</template>

<script>
import { register } from "@/api/login";


export default {
  data() {
    return {
      registerForm: {
        phone: '',
        password: '',
        passwordCom: ''
      }
    }
  },
  onLoad() {
  },
  methods: {
    sendRegister() {
      if (!this.registerForm.phone || this.registerForm.phone.length !== 11) {
        uni.showModal({
          title: '提示',
          content: '请检查手机号是否正确',
          showCancel: false
        })
        return
      }

      if (this.registerForm.password.length < 5) {
        uni.showModal({
          title: '提示',
          content: '密码长度不能小于6位',
          showCancel: false
        })
        return
      }

      if (this.registerForm.password !== this.registerForm.passwordCom) {
        uni.showModal({
          title: '提示',
          content: '两次密码输入不一致',
          showCancel: false
        })
        return
      }
      register({ phone: this.registerForm.phone, password: this.registerForm.password }).then(res => {
        if (res.code === 200) {
          uni.showToast({
            title: '操作成功！',
            duration: 2000,
          });
          setTimeout(() => {
            uni.redirectTo({ url: '/pages/login/login' })
          }, 1000);

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
}
</script>

<style scoped>
.mui-content {
  padding-top: 50px;
  width: 100%;
  height: 100%;
  background: #fff;
  /*background-size: 100%;
	background-position: 0 bottom;*/
  position: absolute;
  top: 0;
  left: 0;
}

.logo {
  margin: 0 0 25px 0;
  width: 100%;
  height: 100px;
  text-align: center;
}

.logo img {
  margin: auto;
  width: 100px;
  height: 100px;
}

.shuRu {
  width: 100%;
  position: relative;
  z-index: 2;
}

.input-border {
  padding: 10px 4.689%;
  margin: auto;
  margin-top: 15px;
  width: 78.125%;
  height: 50px;
  border: 1px solid #dbdbdb;
  border-radius: 3px;
}

.shuRu div img {
  float: left;
  margin-top: 8px;
  margin-right: 20px;
  width: 12px;
}

.shuRu div input {
  padding: 0;
  margin: 0;
  float: left;
  width: 185px;
  height: 30px;
  line-height: 30px;
  color: #333333;
  font-size: 14px;
  border: none;

}

::-webkit-input-placeholder {
  color: #999;
  font-size: 14px;
}

::-moz-placeholder {
  color: #999;
  font-size: 14px;
}

/* firefox 19+ */
:-ms-input-placeholder {
  color: #999;
  font-size: 14px;
}

/* ie */
input:-moz-placeholder {
  color: #999;
  font-size: 14px;
}

.shuRu div span {
  float: right;
  margin: 2px 0;
  display: inline-block;
  width: 75px;
  height: 25px;
  text-align: center;
  line-height: 25px;
  color: #fff;
  font-size: 12px;
  background: #6A4A23;
  border-radius: 3px;
}

.forget {
  margin: auto;
  width: 78.125%;
  height: 32px;
  line-height: 32px;
  color: #ffcc00;
  font-size: 14px;
  font-weight: bold;
  position: relative;
  z-index: 2;
}

.enter_register {
  margin: auto;
  margin-top: 15px;
  width: 78.125%;
  position: relative;
  z-index: 2;

}

.enter_register p {
  width: 100%;
  height: 50px;
  text-align: center;
  line-height: 50px;
  color: #fff;
  font-size: 15px;
  background: #6a4a23;
  border-radius: 3px;
}

.bg {
  width: 100%;
  height: 140px;
  position: absolute;
  left: 0;
  /*bottom: 0;*/
  top: 480px;
}

.xieyi {
  margin-top: 40px;
  width: 100%;
  height: 50px;
  text-align: center;
  line-height: 50px;
  color: #FFCC00;
  font-size: 12px;
  /*position: fixed;
	left: 0;
	bottom: 0;
	z-index: 2;*/
  position: relative;
  z-index: 2;
}
</style>
