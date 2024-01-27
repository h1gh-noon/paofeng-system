import request from '@/utils/request'

export const loginByPhone = (data) => request.post('/baseUrl/auth/loginByPhone', data)

export const register = (data) => request.post('/baseUrl/auth/register', data)
