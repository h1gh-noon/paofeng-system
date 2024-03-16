<template>
  <view>
    <header class="mui-bar mui-bar-nav">
      <navigator open-type="navigateBack">
        <img src="@/static/image/rider/enter/icon_back@2x.png" alt="" />
      </navigator>
      <h1 class="mui-title" v-if="chatUser">{{ chatUser.role === '1' ? '店铺: ' + chatUser.shopName : '骑手: ' +
        chatUser.riderName }}</h1>
    </header>
    <div class="mui-content" ref="msgBox">
      <div v-if="userId && chatUser">
        <template v-for="item in chatUser.data">
          <div class="chat-item-line" :key="item.id" :class="{ 'chat-item-me': item.senderId === getId }">
            <img class="chat-avatar" :src="item.senderId === getId ? getAvatar : chatUser.avatar" alt="">
            <div class="chat-border">
              <div class="chat-border-triangle"></div>
              {{ item.content }}
            </div>
          </div>
        </template>
      </div>
    </div>
    <div class="message-input-box">
      <input type="text" ref="msgInput" id="msg-input" v-model="msg" @confirm="sendHandler">
      <div class="send-btn" @click="sendHandler">发送</div>
    </div>
  </view>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  data() {
    return {
      userId: null,
      msg: ''
    }
  },
  computed: {
    ...mapGetters(['getMessages', 'getId', 'getAvatar']),
    chatUser() {
      if (this.userId && this.getMessages.length) {
        const item = this.getMessages.find(e => e.userId === this.userId)
        if (item) {
          return item
        }
      }
      return null
    },
    chatArr() {
      if (this.chatUser) {
        return this.chatUser.data
      }
      return []
    }
  },
  watch: {
    chatArr() {
      this.$nextTick(() => {
        this.$refs.msgBox.scrollIntoView(false)
      })
    }
  },
  onLoad(val) {
    this.userId = parseInt(val.userId, 10)
    this.$store.commit('SET_UNREAD_NUM', this.userId)
    this.$refs.msgBox.scrollIntoView(false)
    this.setInputFocus()
  },
  methods: {
    sendHandler() {
      if (this.msg.length) {
        const msgData = {
          id: new Date().getTime(),
          targetId: this.userId,
          content: this.msg
        }
        const data = JSON.stringify(msgData)
        msgData.senderId = this.getId
        this.msg = ''
        this.$store.dispatch('pushMessage', msgData).then(() => {
          // 定位到页面底部
          this.setInputFocus()
        })
        uni.sendSocketMessage({
          data
        });
      } else {
        uni.showToast({
          title: '请输入发送内容!',
          icon: 'none',
          duration: 2000
        });
      }
    },
    setInputFocus() {
      // 设置input为焦点
      this.$nextTick(() => {
        document.querySelector('#msg-input input').focus()
      })
    }
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
  border-radius: 50%;
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
  background-color: #fff;
}

#msg-input {
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
