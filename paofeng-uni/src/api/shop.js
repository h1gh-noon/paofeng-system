import request from '@/utils/request'

export const shopAdd = (data) => request.post('/api/shop/shopAdd', data)
