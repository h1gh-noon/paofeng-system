<template>
  <view>
    <header class="mui-bar mui-bar-nav">
      <navigator open-type="navigateBack">
        <img src="@/static/image/rider/enter/icon_back@2x.png" alt="" />
      </navigator>
      <h1 class="mui-title">消息</h1>
    </header>
    <div class="mui-content">
      <div class="chat-item-line">
        <img class="chat-avatar" src="@/static/image/rider/user/tx.png" alt="">
        <div class="chat-border">
          <div class="chat-border-triangle"></div>
          广泛大使馆犯得上方法烦烦烦烦烦烦烦烦烦是顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶烦烦烦烦烦烦是顶顶顶顶顶顶顶顶顶顶顶顶
          广泛大使馆犯得上方法烦烦烦烦烦烦烦烦烦是顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶烦烦烦烦烦烦是顶顶顶顶顶顶顶顶顶顶顶顶
        </div>
      </div>
      <div class="chat-item-line chat-item-me">
        <img class="chat-avatar" src="@/static/image/rider/user/tx.png" alt="">
        <div class="chat-border">
          <div class="chat-border-triangle"></div>
          广泛大使馆犯得上方法烦烦烦烦烦烦烦烦烦是顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶烦烦烦烦烦烦是顶顶顶顶顶顶顶顶顶顶顶顶
          广泛大使馆犯得上方法烦烦烦烦烦烦烦烦烦是顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶烦烦烦烦烦烦是顶顶顶顶顶顶顶顶顶顶顶顶
        </div>
      </div>
    </div>
    <div class="message-input-box">
      <input type="text" class="msg-input">
      <div class="send-btn">发送</div>
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
  border-bottom: 1px solid #ccc;
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
  padding-top: 54px !important;
  width: 100%;
  background: #f5f5f5;
  padding-bottom: 50px;
}

.chat-item-line {
  display: flex;
}

.chat-item-me {
  flex-direction: row-reverse;
}

.chat-avatar {
  width: 50px;
  height: 50px;
}

.chat-border {
  background-color: #fff;
  margin: 15px;
  padding: 10px;
  position: relative;
  border-radius: 5px;
}

.chat-border-triangle {
  position: absolute;
  left: -10px;
  top: 10px;
  width: 0;
  height: 0;
  border-top: 6px solid transparent;
  border-right: 12px solid #fff;
  border-bottom: 6px solid transparent;
}

.chat-item-me .chat-border-triangle {
  right: -10px;
  left: auto;
  border-left: 12px solid #fff;
  border-right: none;
}

.message-input-box {
  position: fixed;
  bottom: 0;
  border-top: 1px #ccc solid;
  display: flex;
  width: 100%;
  height: 36px;
  align-items: center;
}
.msg-input {
  flex: 1;
  margin: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 0 5px;
}
.send-btn {
  border-radius: 6px;
  width: 66px;
  text-align: center;
  background-color: #77bff0;
  border: 1px #f5f5f5 solid;

}
</style>
