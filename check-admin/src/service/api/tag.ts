import { request } from "../request";

export function fetchAddTag(data: Api.Tag.AddTagParams) {
  return request<number>({
    url: "/admin/tag/add",
    method: "post",
    data,
  });
}

export function fetchUpdateTag(data: Api.Tag.UpdateTagParams) {
  return request<boolean>({
    url: "/admin/tag/update",
    method: "post",
    data,
  });
}

export function fetchTagList(data: Api.Tag.TagListParams) {
  return request<Api.Tag.TagListResult>({
    url: "/admin/tag/list",
    method: "post",
    data,
  });
}

export function fetchDeleteTag(id: number) {
  return request<boolean>({
    url: "/admin/tag/delete",
    method: "post",
    data: { id },
  });
}
