import request from '@/utils/request'

export const shopAdd = data => request.post('/api/system/shop/add', data)
export const shopUpdate = data => request.post('/api/system/shop/update', data)
export const getShopInfo = () => request.get('/api/system/shop/getShopInfo')

export const orderAdd = data => request.post('/api/order/order/add', data)

export const updateDeliveryFee = data =>
  request.post('/api/order/order/updateDeliveryFee', data)
export const subOrder = params =>
  request.post(`/api/order/order/subOrder?orderId=${params.orderId}`, params)
export const cancelOrder = params =>
  request.post(`/api/order/order/cancelOrder?orderId=${params.orderId}`, params)

export const orderList = data => request.post('/api/order/order/shopList', data)

export const successOrder = params =>
  request.post(
    `/api/order/order/successOrder?orderId=${params.orderId}`,
    params
  )
