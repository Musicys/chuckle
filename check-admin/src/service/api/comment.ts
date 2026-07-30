import { request } from "../request";

export function fetchCommentList(data: Api.Comment.CommentListParams) {
  return request<Api.Comment.CommentListResult>({
    url: "/admin/comment/list",
    method: "post",
    data
  });
}

export function fetchReviewComment(id: number, status: number) {
  return request<boolean>({
    url: "/admin/comment/review",
    method: "post",
    data: { id, status }
  });
}

export function fetchReplyComment(data: Api.Comment.ReplyCommentParams) {
  return request<number>({
    url: "/admin/comment/reply",
    method: "post",
    data
  });
}

export function fetchDeleteComment(id: number) {
  return request<boolean>({
    url: "/admin/comment/delete",
    method: "post",
    data: { id }
  });
}

export function fetchReplyCommentByEmail(data: Api.Comment.EmailReplyParams) {
  return request<boolean>({
    url: "/admin/comment/reply-by-email",
    method: "post",
    data
  });
}