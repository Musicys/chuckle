import { request } from "../request";

export function fetchAddCategory(data: Api.Category.AddCategoryParams) {
  return request<number>({
    url: "/admin/category/add",
    method: "post",
    data,
  });
}

export function fetchUpdateCategory(data: Api.Category.UpdateCategoryParams) {
  return request<boolean>({
    url: "/admin/category/update",
    method: "post",
    data,
  });
}

export function fetchCategoryList(data: Api.Category.CategoryListParams) {
  return request<Api.Category.CategoryListResult>({
    url: "/admin/category/list",
    method: "post",
    data,
  });
}

export function fetchDeleteCategory(id: number) {
  return request<boolean>({
    url: "/admin/category/delete",
    method: "post",
    data: { id },
  });
}