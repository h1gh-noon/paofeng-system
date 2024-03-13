import { getInfo } from "@/api/login";
import { OPTION_GET_FRIEND, TYPE_REPLY } from "@/pages/component/popup-message/type";

// #ifndef VUE3
import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)
const store = new Vuex.Store({
// #endif

// #ifdef VUE3
import { createStore } from 'vuex'
const store = createStore({
// #endif
	state: {
		hasLogin: false,
		isUniverifyLogin: false,
		loginProvider: "",
		openid: null,
		testvuex: false,
		colorIndex: 0,
		colorList: ['#FF0000', '#00FF00', '#0000FF'],
		noMatchLeftWindow: true,
		active: 'componentPage',
		leftWinActive: '/pages/component/view/view',
		activeOpen: '',
		menu: [],
		univerifyErrorMsg: '',
		token: uni.getStorageSync('auth_token'),
    id: '',
    name: '',
    avatar: '',
    roles: [],
    permissions: [],
		message: []
	},
	mutations: {
		login(state, provider) {
			state.hasLogin = true;
			state.loginProvider = provider;
		},
		logout(state) {
			state.hasLogin = false
			state.openid = null
		},
		setOpenid(state, openid) {
			state.openid = openid
		},
		setTestTrue(state) {
			state.testvuex = true
		},
		setTestFalse(state) {
			state.testvuex = false
		},
		setColorIndex(state, index) {
			state.colorIndex = index
		},
		setMatchLeftWindow(state, matchLeftWindow) {
			state.noMatchLeftWindow = !matchLeftWindow
		},
		setActive(state, tabPage) {
			state.active = tabPage
		},
		setLeftWinActive(state, leftWinActive) {
			state.leftWinActive = leftWinActive
		},
		setActiveOpen(state, activeOpen) {
			state.activeOpen = activeOpen
		},
		setMenu(state, menu) {
			state.menu = menu
		},
		setUniverifyLogin(state, payload) {
			typeof payload !== 'boolean' ? payload = !!payload : '';
			state.isUniverifyLogin = payload;
		},
		setUniverifyErrorMsg(state,payload = ''){
			state.univerifyErrorMsg = payload
		},
		SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_EXPIRES_IN: (state, time) => {
      state.expires_in = time
    },
    SET_ID: (state, id) => {
      state.id = id
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_PERMISSIONS: (state, permissions) => {
      state.permissions = permissions
    },
    SET_MESSAGE: (state, message) => {
      state.message = message
    },
		PUSH_MESSAGE: (state, message) => {
			state.message.push(message)
		}
	},
	getters: {
		currentColor(state) {
			return state.colorList[state.colorIndex]
		},
		getToken(state) {
			return state.token
		},
		getId(state) {
			return state.id
		},
		getName(state) {
			return state.name
		},
		getAvatar(state) {
			return state.avatar
		},
		getRoles(state) {
			return state.roles
		},
		getRoles(state) {
			return state.roles
		},
		getPermissions(state) {
			return state.permissions
		},
		getMessages(state) {
			return state.message
		}
	},
	actions: {
		// lazy loading openid
		getUserOpenId: async function({
			commit,
			state
		}) {
			return await new Promise((resolve, reject) => {
				if (state.openid) {
					resolve(state.openid)
				} else {
					uni.login({
						success: (data) => {
							commit('login')
							setTimeout(function() { //模拟异步请求服务器获取 openid
								const openid = '123456789'
								console.log('uni.request mock openid[' + openid + ']');
								commit('setOpenid', openid)
								resolve(openid)
							}, 1000)
						},
						fail: (err) => {
							console.log('uni.login 接口调用失败，将无法正常使用开放接口等服务', err)
							reject(err)
						}
					})
				}
			})
		},
		getPhoneNumber: function({
			commit
		}, univerifyInfo) {
			return new Promise((resolve, reject) => {
				uni.request({
					url: 'https://97fca9f2-41f6-449f-a35e-3f135d4c3875.bspapp.com/http/univerify-login',
					method: 'POST',
					data: univerifyInfo,
					success: (res) => {
						const data = res.data
						if (data.success) {
							resolve(data.phoneNumber)
						} else {
							reject(res)
						}

					},
					fail: (err) => {
						reject(res)
					}
				})
			})
		},
		getUserInfo: function({
			commit
		}) {
			return new Promise((resolve, reject) => {
				getInfo().then(res => {
					if (res.code === 200) {
						const user = res.user
						const avatar = (user.avatar == "" || user.avatar == null) ? '' : user.avatar;
						if (res.roles && res.roles.length > 0) { // 验证返回的roles是否是一个非空数组
							commit('SET_ROLES', res.roles)
							commit('SET_PERMISSIONS', res.permissions)
						} else {
							commit('SET_ROLES', ['ROLE_DEFAULT'])
						}
						commit('SET_ID', user.userId)
						commit('SET_NAME', user.userName)
						commit('SET_AVATAR', avatar)
						resolve(res)
					}
					reject(res)
        }).catch(error => {
          reject(error)
        })
			})
		},
		pushMessage: function({
			commit,
			state
		}, message) {
			return new Promise((resolve, reject) => {
					const msgArr = state.message
					if (['4', OPTION_GET_FRIEND].includes(message.type)) {
						const list = message.data
						// 获取联系人信息
						if(list && Array.isArray(list)) {
							list.forEach(item => {
								item.type = '1'
								item.unReadNum = 0
								item.data = []
								const index =	msgArr.findIndex(e => e.type==='1' && e.userId === item.userId)
								if(index > -1){
									item.data = msgArr[index].data
									msgArr[index] = item
								} else {
									console.log(item)
									commit('PUSH_MESSAGE', item)
								}
							})
						}
					} else if(message.type === TYPE_REPLY) {
						// 响应消息发送成功
						for (let index = 0; index < msgArr.length; index++) {
							const element = msgArr[index];
							const item = element.data.find(e => e.id === message.data)
							if (item) {
								// 替换id 真实的接收时间createTime
								item.id = message.id
								item.createTime = message.createTime
								break
							}
						}

					} else {
						const chatItem = msgArr.find(e => e.userId === message.senderId || e.userId === message.targetId)
						if (chatItem) {
							if (chatItem.data) {
								chatItem.data.push(message)
							} else {
								chatItem.data = [message]
							}
							if(message.senderId !== state.id) {
								chatItem.unReadNum++
								resolve({
									userId: chatItem.userId,
									content: message.content,
									avatar: chatItem.avatar,
									role: chatItem.role,
									userName: chatItem.userName,
									riderName: chatItem.riderName
								})
								return
							}
						} else {
							const data = {
								userId: message.senderId,
								data: message,
								item: unReadNum = 1
							}
							commit('PUSH_MESSAGE', data)
						}
					}
					resolve(null)
			})
		}
	}
})

export default store
