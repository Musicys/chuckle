import { request } from "../request";

export function fetchAddBlogger(data: Api.Blogger.AddBloggerParams) {
  return request<number>({
    url: "/admin/blogger/add",
    method: "post",
    data,
  });
}

export function fetchUpdateBlogger(data: Api.Blogger.UpdateBloggerParams) {
  return request<boolean>({
    url: "/admin/blogger/update",
    method: "post",
    data,
  });
}

export function fetchGetBlogger(id: number) {
  return request<Api.Blogger.BloggerInfo>({
    url: "/admin/blogger/get",
    method: "get",
    params: { id },
  });
}

export function fetchBloggerList(data: Api.Blogger.BloggerListParams) {
  return request<Api.Blogger.BloggerListResult>({
    url: "/admin/blogger/list",
    method: "post",
    data,
  });
}

export function fetchDeleteBlogger(id: number) {
  return request<boolean>({
    url: "/admin/blogger/delete",
    method: "post",
    data: { id },
  });
}

export function fetchSetBloggerUsed(id: number) {
  return request<boolean>({
    url: "/admin/blogger/setUsed",
    method: "post",
    params: { id },
  });
}
