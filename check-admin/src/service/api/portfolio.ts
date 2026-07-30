import { request } from "../request";

export function fetchAddPortfolio(data: Api.Portfolio.AddPortfolioParams) {
  return request<number>({
    url: "/admin/portfolio/add",
    method: "post",
    data,
  });
}

export function fetchUpdatePortfolio(
  data: Api.Portfolio.UpdatePortfolioParams,
) {
  return request<boolean>({
    url: "/admin/portfolio/update",
    method: "post",
    data,
  });
}

export function fetchPortfolioList(data: Api.Portfolio.PortfolioListParams) {
  return request<Api.Portfolio.PortfolioListResult>({
    url: "/admin/portfolio/list",
    method: "post",
    data,
  });
}

export function fetchDeletePortfolio(id: number) {
  return request<boolean>({
    url: "/admin/portfolio/delete",
    method: "post",
    data: { id },
  });
}

export function fetchAllPortfolio() {
  return request<Api.Portfolio.PortfolioInfo[]>({
    url: "/admin/portfolio/all",
    method: "get",
  });
}
