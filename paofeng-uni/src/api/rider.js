import request from '@/utils/request'

export const riderAdd = data => request.post('/api/system/rider/add', data)

export const takeOrder = data => request.get('/api/order/order/takeOrder', data)

export const riderTakeOrderList = (data, params) =>
  request.post('/api/order/order/riderTakeOrderList', data, params)

export const riderList = (data, params) =>
  request.post('/api/order/order/riderList', data, params)

export const takeOrderGoods = params =>
  request.get('/api/order/order/takeOrderGoods', params)

export const successOrder = params =>
  request.get('/api/order/order/successOrder', params)

export const cancelOrder = params =>
  request.get('/api/order/order/cancelOrder', params)
