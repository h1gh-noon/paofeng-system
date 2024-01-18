import request from '@/common/http'

// 登录
export function login() {
  return request.post('/login')
}

// 退出方法
export function logout() {
  return request.post('/logout')
}

// 获取用户详细信息
export function getInfo() {
  return request.get('/getInfo')
}
