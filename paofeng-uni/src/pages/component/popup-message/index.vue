<template>
  <view>
    <uni-popup ref="popup" type="message" class="global-popup">
      <!-- sss -->
      <uni-popup-message type="info" :duration="3000">
        <div class="item-left">
          <img class="img-avatar" :src="message.avatar" />
          <div class="item-content">
            <div>{{ message.role === '1' ? '店铺: ' + message.shopName : '骑手: ' +
        message.riderName }}</div>
            <div class="mui-ellipsis">{{ message.content }}</div>
          </div>
        </div>
      </uni-popup-message>
    </uni-popup>
  </view>
</template>

<script>
import { mapGetters } from 'vuex'
import { OPTION_GET_FRIEND } from './type';
export default {
  data() {
    return {
      message: {},
      tryNum: 0
    }
  },
  computed: {
    ...mapGetters([
      'getMessages',
      'getId'
    ])
  },
  methods: {
    initWebsocket() {
      console.log('initWebsocket')
      const token = this.$store.getters.getToken
      if (!token) {
        return
      }
      this.tryNum++
      if (this.tryNum > 5) {
        return
      }

      uni.connectSocket({
        url: 'ws://localhost:18080/chat/websocket',
        protocols: [token]
      });
      uni.onSocketOpen(() => {
        const d = {
          type: OPTION_GET_FRIEND
        }
        this.sendMessage(d)
      });
      uni.onSocketMessage((res) => {
        const msgData = JSON.parse(res.data)
        console.log(msgData)
        if (msgData.code === 200) {
          this.$store.dispatch('pushMessage', msgData.data).then(this.pushMessage)
        } else {
          console.log('err')
        }
      });
      uni.onSocketError(() => {
        console.log('WebSocket连接失败！');
        setTimeout(() => {
          this.initWebsocket()
        }, 3000);
      });
    },
    pushMessage(val) {
      if (val) {
        // 判断当前页面
        this.message = val
        this.$refs.popup.open()
      }
    },
    sendMessage(msg) {
      uni.sendSocketMessage({
        data: JSON.stringify(msg)
      });
    }
  }
}
</script>

<style scoped>
.global-popup .uni-popup-message {
  border-radius: 50px;
  width: calc(100% - 20px);
  overflow: hidden;
  margin: auto;
}

.global-popup ::v-deep .uni-popup-message__box {
  padding: 5px 10px;
}

.item-content {
  margin-left: 10px;
  width: 100%;
}

.item-left {
  display: flex;
}

.img-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}
.mui-ellipsis {
  width: calc(100vw - 100px);
}
</style>
