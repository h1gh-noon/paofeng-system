<template>
  <view>
    <uni-popup ref="popup" type="message" class="global-popup">
      <!-- sss -->
      <uni-popup-message type="info" :duration="200000">{{ message.content }}</uni-popup-message>
    </uni-popup>
  </view>
</template>

<script>
import { mapGetters } from 'vuex'
import { OPTION_GET_FRIEND } from './option';
export default {
  data() {
    return {
      message: {},
      tryNum: 0
    }
  },
  computed: {
    ...mapGetters([
      'getMessages'
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
          option: OPTION_GET_FRIEND
        }
        this.sendMessage(d)
      });
      uni.onSocketMessage((res) => {
        const msgData = JSON.parse(res.data)
        console.log(msgData)
        if (msgData.code === 200) {
          this.pushMessage(msgData.data)
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
      this.message = val
      this.$refs.popup.open()
      this.$store.commit('PUSH_MESSAGE', val)
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
</style>
