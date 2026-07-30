import { request } from "../request";

export function fetchAddFriendLink(data: Api.FriendLink.AddFriendLinkParams) {
  return request<number>({
    url: "/admin/friendLink/add",
    method: "post",
    data,
  });
}

export function fetchUpdateFriendLink(data: Api.FriendLink.UpdateFriendLinkParams) {
  return request<boolean>({
    url: "/admin/friendLink/update",
    method: "post",
    data,
  });
}

export function fetchFriendLinkList(data: Api.FriendLink.FriendLinkListParams) {
  return request<Api.FriendLink.FriendLinkListResult>({
    url: "/admin/friendLink/list",
    method: "post",
    data,
  });
}

export function fetchDeleteFriendLink(id: number) {
  return request<boolean>({
    url: "/admin/friendLink/delete",
    method: "post",
    data: { id },
  });
}

export function fetchAllFriendLink() {
  return request<Api.FriendLink.FriendLinkInfo[]>({
    url: "/admin/friendLink/all",
    method: "get",
  });
}