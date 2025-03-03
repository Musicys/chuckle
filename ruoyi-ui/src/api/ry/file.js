import request from '@/utils/request'

// 查询blog文件列表
export function listFile(query) {
  return request({
    url: '/ry/file/list',
    method: 'get',
    params: query
  })
}

// 查询blog文件详细
export function getFile(fileId) {
  return request({
    url: '/ry/file/' + fileId,
    method: 'get'
  })
}

// 新增blog文件
export function addFile(data) {
  return request({
    url: '/ry/file',
    method: 'post',
    data: data
  })
}

// 修改blog文件
export function updateFile(data) {
  return request({
    url: '/ry/file',
    method: 'put',
    data: data
  })
}

// 删除blog文件
export function delFile(fileId) {
  return request({
    url: '/ry/file/' + fileId,
    method: 'delete'
  })
}
