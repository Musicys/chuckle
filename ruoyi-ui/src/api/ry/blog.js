import request from '@/utils/request'
import { praseStrEmpty } from "@/utils/ruoyi";
import { parseStrEmpty } from '../../utils/ruoyi'

// 查询博客信息列表
export function listBlog(query) {
  return request({
    url: '/ry/blog/list',
    method: 'get',
    params: query
  })
}

// 查询博客信息详细
export function getBlog(id) {
  console.log("getBlog被调用")
  return request({
    url: '/ry/blog/' + parseStrEmpty(id),
    method: 'get'
  })
}

// 新增博客信息
export function addBlog(data) {
  return request({
    url: '/ry/blog',
    method: 'post',
    data: data
  })
}

// 修改博客信息
export function updateBlog(data) {
  return request({
    url: '/ry/blog',
    method: 'put',
    data: data
  })
}

// 删除博客信息
export function delBlog(id) {
  return request({
    url: '/ry/blog/' + id,
    method: 'delete'
  })
}
