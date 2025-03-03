import request from '@/utils/request'

// 查询标签信息列表
export function listTag(query) {
  return request({
    url: '/ry/tag/list',
    method: 'get',
    params: query
  })
}

// 查询标签信息详细
export function getTag(tagId) {
  return request({
    url: '/ry/tag/' + tagId,
    method: 'get'
  })
}

// 新增标签信息
export function addTag(data) {
  return request({
    url: '/ry/tag',
    method: 'post',
    data: data
  })
}

// 修改标签信息
export function updateTag(data) {
  return request({
    url: '/ry/tag',
    method: 'put',
    data: data
  })
}

// 删除标签信息
export function delTag(tagId) {
  return request({
    url: '/ry/tag/' + tagId,
    method: 'delete'
  })
}
