<template>
  <div class="mui-content">
    <div class="logo">
      <img src="@/static/image/shop/enter/logo.png" alt="" />
    </div>
    <div class="shuRu">
      <div class="input-border">
        <img src="@/static/image/shop/enter/denglu_1.png" alt="" />
        <input type="number" v-model="requestForm.phone" placeholder="请输入手机号" />
      </div>
      <div class="input-border">
        <img src="@/static/image/shop/enter/denglu_2.png" />
        <input type="password" v-model="requestForm.password" placeholder="请输入登录密码" />
      </div>
    </div>
    <div class="forget link" data-link="forget_pwd.html">
      忘记密码？
    </div>
    <div class="enter_register">
      <p class="enter" @click="loginHandler">登录</p>
      <navigator url="/pages/login/register" open-type="redirect" hover-class="other-navigator-hover">
        <p class="register link" data-link="register.html">注册</p>
      </navigator>
    </div>
  </div>
</template>

<script>
import { loginByPhone } from "@/api/login";

export default {
  data() {
    return {
      requestForm: {
        phone: '',
        password: ''
      }
    }
  },
  onLoad() {
  },
  methods: {
    loginHandler() {
      if (!/^1[0-9]{10}/.test(this.requestForm.phone)) {
        uni.showModal({
          title: '提示',
          content: '请检查手机号是否正确',
          showCancel: false
        })
        return
      }

      if (!this.requestForm.password.length) {
        uni.showModal({
          title: '提示',
          content: '密码长度不能小于6位',
          showCancel: false
        })
        return
      }


      loginByPhone(this.requestForm).then(res => {
        if (res.code === 200) {
          uni.showToast({
            title: '登录成功！',
            duration: 2000,
          });
          setTimeout(() => {
            uni.redirectTo({ url: '/pages/shop/order/order/index' })
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
  padding-top: 78px;
  width: 100%;
  height: 100vh;
  background: #fff url(../../static/image/shop/enter/bei_jing.png) no-repeat;
  background-size: 100% 21%;
  background-position: bottom 47px right 0px;
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
}

.input-border {
  padding: 10px 4.689%;
  margin: auto;
  margin-top: 20px;
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

.forget {
  margin: auto;
  width: 78.125%;
  height: 32px;
  line-height: 32px;
  color: #666;
  font-size: 12px;
}

.enter_register {
  margin: auto;
  margin-top: 10px;
  width: 78.125%;

}

.enter_register p:nth-child(1) {
  width: 100%;
  height: 50px;
  text-align: center;
  line-height: 50px;
  color: #fff;
  font-size: 15px;
  background: #6a4a23;
  border-radius: 3px;
}

.enter_register p:nth-child(2) {
  width: 100%;
  height: 50px;
  text-align: center;
  line-height: 50px;
  color: #ffcc00;
  font-size: 15px;
}
</style>