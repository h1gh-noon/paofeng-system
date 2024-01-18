import { ref, computed, reactive } from 'vue'
import { defineStore } from 'pinia'

import storage from '@/utils/storage'
import constant from '@/utils/constant'
import { login, logout, getInfo } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'

export const useAuthStore = defineStore('auth', () => {
  const tokenKey = 'token'

  const state = reactive({
    token: getToken(),
    name: storage.get(constant.name),
    avatar: storage.get(constant.avatar),
    roles: storage.get(constant.roles),
    permissions: storage.get(constant.permissions)
  })

  const setName = (state, name) => {
    state.name = name
    storage.set(constant.name, name)
  }

  const setAvatar = (state, avatar) => {
    state.avatar = avatar
    storage.set(constant.avatar, avatar)
  }

  const setRoles = (state, roles) => {
    state.roles = roles
    storage.set(constant.roles, roles)
  }

  const setPermissions = (state, permissions) => {
    state.permissions = permissions
    storage.set(constant.permissions, permissions)
  }

  // 登录
  const Login = ({ commit }, userInfo) => {
    const username = userInfo.username.trim()
    const password = userInfo.password
    const code = userInfo.code
    const uuid = userInfo.uuid
    return new Promise((resolve, reject) => {
      login(username, password, code, uuid)
        .then(res => {
          setToken(res.token)
          state.token = token
          resolve()
        })
        .catch(error => {
          reject(error)
        })
    })
  }

  // 获取用户信息
  const GetInfo = ({ commit, state }) => {
    return new Promise((resolve, reject) => {
      getInfo()
        .then(res => {
          const user = res.user
          const avatar =
            user == null || user.avatar == '' || user.avatar == null
              ? require('@/static/images/profile.jpg')
              : baseUrl + user.avatar
          const username =
            user == null || user.userName == '' || user.userName == null
              ? ''
              : user.userName
          if (res.roles && res.roles.length > 0) {
            setRoles(res.roles)
            setPermissions(res.permissions)
          } else {
            setRoles(['ROLE_DEFAULT'])
          }
          setName(username)
          setAvatar(avatar)
          resolve(res)
        })
        .catch(error => {
          reject(error)
        })
    })
  }

  // 退出系统
  const LogOut = ({ commit, state }) => {
    return new Promise((resolve, reject) => {
      logout(state.token)
        .then(() => {
          state.token = ''
          setRoles([])
          setPermissions([])
          removeToken()
          storage.clean()
          resolve()
        })
        .catch(error => {
          reject(error)
        })
    })
  }

  const userInfoComputed = computed(() => state)

  return {
    Login,
    GetInfo,
    LogOut,
    userInfoComputed
  }
})
