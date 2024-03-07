import request from '@/utils/request'

export const loginByPhone = (data) => request.post('/api/auth/loginByPhone', data)

export const register = (data) => request.post('/api/auth/register', data)

export const getInfo = (data) => request.get('/api/system/user/getInfo', data)
