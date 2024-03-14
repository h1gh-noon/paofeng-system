<template>
  <view>
    <div @click="pushRoute">
      <uni-popup ref="popup" type="message" class="global-popup">
        <!-- sss -->
        <uni-popup-message type="info" :duration="3000">
          <div class="item-left">
            <img class="img-avatar" :src="message.avatar" />
            <div class="item-content">
              <div>{{
      message.role === '1'
        ? '店铺: ' + message.shopName
        : '骑手: ' + message.riderName }}</div>
              <div class="mui-ellipsis">{{ message.content }}</div>
            </div>
          </div>
        </uni-popup-message>
      </uni-popup>
    </div>
  </view>
</template>

<script>
import { mapGetters } from 'vuex'
import { OPTION_GET_FRIEND, TYPE_SYNC_CHAT } from './type';
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
        this.$store.dispatch('setMsgTimer')
        // 同步聊天数据
        d.type = TYPE_SYNC_CHAT
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
        const routes = getCurrentPages(); // 获取当前打开过的页面路由数组
        const curRoute = routes[routes.length - 1].route //获取当前页面路由
        const curParam = routes[routes.length - 1].options; //获取路由参数
        if (curRoute === 'pages/component/chat/chat' && parseInt(curParam.userId, 10) === val.userId) {
          // 判断当前页面
          // 在聊天页面 不需要弹窗
          return
        }
        this.message = val
        this.$refs.popup.open()
      }
    },
    sendMessage(msg) {
      uni.sendSocketMessage({
        data: JSON.stringify(msg)
      });
    },
    pushRoute() {
      this.$refs.popup.close()
      uni.navigateTo({
        url: `/pages/component/chat/chat?userId=${this.message.userId}`
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
