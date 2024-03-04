import request from '@/utils/request'

// 获取rider 列表
export function getRiderList(data) {
  return request({
    url: '/system/rider/list',
    method: 'post',
    data
  })
}

// 启/禁用店铺
export function updateStatus(data) {
  return request({
    url: '/system/rider/updateStatus',
    method: 'post',
    data
  })
}
