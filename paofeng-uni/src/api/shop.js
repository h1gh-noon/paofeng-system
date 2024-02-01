import request from '@/utils/request'

export const shopAdd = (data) => request.post('/api/system/shop/add', data)
