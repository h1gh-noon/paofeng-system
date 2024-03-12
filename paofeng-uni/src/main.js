import App from './App'
import store from './store'

import '@/static/mui/css/mui.min.css'
import '@/utils/permission.js'
import PopupMessage from '@/pages/component/popup-message/index.vue'
// import '@/static/js/jquery-1.7.2.js'

import Vue from 'vue'
// Vue.component('PopupMessage', PopupMessage);

// vue 新增组件
const addH5component = function (url, name) {
  return new Promise(async (r, _) => {
    const idName =
      'PopupMessage_new_custom_' +
      new Date().getTime() +
      '_' +
      parseInt(Math.random() * 1000)
    const customComponent = url.default
    const customComponentCom = Vue.component('PopupMessage', customComponent) // 创建组件
    const customComponentComNew = new customComponentCom() // 创建组件实例（可以传递参数 { propsData: props }）
    const DOM = document.createElement('div')
    DOM.setAttribute('class', `confirmDialog_new_custom ${name}_component`)
    DOM.setAttribute('id', idName)
    document.body.appendChild(DOM)
    const comp = customComponentComNew.$mount(
      DOM.appendChild(document.createElement('template'))
    ) // 将虚拟dom节点改变成真实dom,获取组件的dom节点,并实现挂载
    comp.componentId = idName
    r(comp)
  })
}
// PopupMessage提示
Vue.prototype.$PopupMessage = async function (obj) {
  // #ifdef H5
  if (!this.$refs.PopupMessage) {
    this.$refs['PopupMessage'] = await addH5component(
      require('@/pages/component/popup-message/index.vue'),
      'PopupMessage'
    )
    console.log(this)
    this.$refs.PopupMessage.initWebsocket()
  }
  if (obj) {
    this.$refs.PopupMessage.pushMessage(obj)
  }
}
Vue.prototype.$hidePopupMessage = function () {
  if (this.$refs.PopupMessage) {
    this.$refs.PopupMessage.close()
    // #ifdef H5
    // 删除组件
    setTimeout(() => {
      document.querySelectorAll('.PopupMessage_component').forEach(item => {
        document.body.removeChild(item)
      })
      this.$refs.PopupMessage.$destroy()
    }, 0)
    // #endif
  }
}

Vue.config.productionTip = false
Vue.prototype.$store = store
Vue.prototype.$adpid = '1111111111'
Vue.prototype.$backgroundAudioData = {
  playing: false,
  playTime: 0,
  formatedPlayTime: '00:00:00'
}
App.mpType = 'app'
const app = new Vue({
  store,
  ...App
})
app.$mount()
