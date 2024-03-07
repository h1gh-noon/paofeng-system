import request from '@/utils/request'

export const shopAdd = data => request.post('/api/system/shop/add', data)
export const shopUpdate = data => request.post('/api/system/shop/update', data)
export const getShopInfo = () => request.get('/api/system/shop/getShopInfo')

export const orderAdd = data => request.post('/api/order/order/add', data)

export const orderList = data => request.post('/api/order/order/shopList', data)

