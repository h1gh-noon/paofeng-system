import request from '@/utils/request'

export const shopAdd = (data) => request.post('/api/system/shop/add', data)

export const orderAdd = (data) => request.post('/api/order/order/add', data)
