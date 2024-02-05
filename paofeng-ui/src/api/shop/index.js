import request from '@/utils/request'

// 获取shop 列表
export function getShopList(data) {
  return request({
    url: '/system/shop/list',
    method: 'post',
    data
  })
}

// 启/禁用店铺
export function updateStatus(data) {
  return request({
    url: '/system/shop/updateStatus',
    method: 'post',
    data
  })
}
