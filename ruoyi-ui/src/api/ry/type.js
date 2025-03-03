import request from '@/utils/request'

// 查询分类信息列表
export function listType(query) {
  return request({
    url: '/ry/type/list',
    method: 'get',
    params: query
  })
}

// 查询分类信息详细
export function getType(typeId) {
  return request({
    url: '/ry/type/' + typeId,
    method: 'get'
  })
}

// 新增分类信息
export function addType(data) {
  return request({
    url: '/ry/type',
    method: 'post',
    data: data
  })
}

// 修改分类信息
export function updateType(data) {
  return request({
    url: '/ry/type',
    method: 'put',
    data: data
  })
}

// 删除分类信息
export function delType(typeId) {
  return request({
    url: '/ry/type/' + typeId,
    method: 'delete'
  })
}
