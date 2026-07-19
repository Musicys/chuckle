import { request } from "../request";

export function fetchUploadImage(data: FormData) {
  return request<Api.Image.ImageInfo>({
    url: "/admin/image/upload",
    method: "post",
    data,
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
}

export function fetchUpdateImage(data: Api.Image.UpdateImageParams) {
  return request<boolean>({
    url: "/admin/image/update",
    method: "post",
    data,
  });
}

export function fetchGetImage(id: number) {
  return request<Api.Image.ImageInfo>({
    url: "/admin/image/get",
    method: "get",
    params: { id },
  });
}

export function fetchImageList(data: Api.Image.ImageListParams) {
  return request<Api.Image.ImageListResult>({
    url: "/admin/image/list",
    method: "post",
    data,
  });
}

export function fetchDeleteImage(id: number) {
  return request<boolean>({
    url: "/admin/image/delete",
    method: "post",
    data: { id },
  });
}