import request from '@/utils/request'

export const riderAdd = (data) => request.post('/api/system/rider/add', data)
