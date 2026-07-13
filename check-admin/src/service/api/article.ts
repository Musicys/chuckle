import { request } from "../request";

export function fetchAddArticle(data: Api.Article.AddArticleParams) {
  return request<number>({
    url: "/admin/article/add",
    method: "post",
    data,
  });
}

export function fetchUpdateArticle(data: Api.Article.UpdateArticleParams) {
  return request<boolean>({
    url: "/admin/article/update",
    method: "post",
    data,
  });
}

export function fetchGetArticle(id: number) {
  return request<Api.Article.ArticleInfo>({
    url: "/admin/article/get",
    method: "get",
    params: { id },
  });
}

export function fetchArticleList(data: Api.Article.ArticleListParams) {
  return request<Api.Article.ArticleListResult>({
    url: "/admin/article/list",
    method: "post",
    data,
  });
}

export function fetchDeleteArticle(id: number) {
  return request<boolean>({
    url: "/admin/article/delete",
    method: "post",
    data: { id },
  });
}
