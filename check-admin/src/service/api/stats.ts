import { request } from "../request";

/**
 * 获取仪表盘统计数据
 */
export function fetchDashboardStats() {
  return request<Api.Common.DashboardStats>({
    url: "/admin/dashboard/stats",
    method: "get",
  });
}

/**
 * 获取访问统计
 */
export function fetchVisitStats() {
  return request<Api.Common.VisitStats>({
    url: "/admin/visit-logs/stats",
    method: "get",
  });
}

/**
 * 获取访问日志列表
 */
export function fetchVisitLogsList(params: {
  current: number;
  pageSize: number;
  ip?: string;
  pageUrl?: string;
  startDate?: string;
  endDate?: string;
}) {
  return request<Api.Common.PageData<Api.Common.VisitLog>>({
    url: "/admin/visit-logs/list",
    method: "post",
    data: params,
  });
}

/**
 * 删除访问日志
 */
export function fetchDeleteVisitLog(id: number) {
  return request<boolean>({
    url: "/admin/visit-logs/delete",
    method: "post",
    data: { id },
  });
}

/**
 * 获取每日统计列表
 */
export function fetchDailyStatsList(params: {
  current: number;
  pageSize: number;
  startDate?: string;
  endDate?: string;
}) {
  return request<Api.Common.PageData<Api.Common.DailyStat>>({
    url: "/admin/daily-stats/list",
    method: "post",
    data: params,
  });
}

/**
 * 获取每日统计汇总
 */
export function fetchDailyStatsSummary() {
  return request<Api.Common.DailyStatsSummary>({
    url: "/admin/daily-stats/summary",
    method: "get",
  });
}

/**
 * 获取系统设置列表
 */
export function fetchSystemSettingsList(params: {
  current: number;
  pageSize: number;
  settingKey?: string;
}) {
  return request<Api.Common.PageData<Api.Common.SystemSetting>>({
    url: "/admin/system-settings/list",
    method: "post",
    data: params,
  });
}

/**
 * 获取所有系统设置
 */
export function fetchSystemSettingsAll() {
  return request<Api.Common.SystemSetting[]>({
    url: "/admin/system-settings/all",
    method: "get",
  });
}

/**
 * 新增系统设置
 */
export function fetchAddSystemSetting(data: {
  settingKey: string;
  settingValue: string;
  description?: string;
}) {
  return request<number>({
    url: "/admin/system-settings/add",
    method: "post",
    data,
  });
}

/**
 * 更新系统设置
 */
export function fetchUpdateSystemSetting(data: {
  id: number;
  settingKey?: string;
  settingValue?: string;
  description?: string;
}) {
  return request<boolean>({
    url: "/admin/system-settings/update",
    method: "post",
    data,
  });
}

/**
 * 删除系统设置
 */
export function fetchDeleteSystemSetting(id: number) {
  return request<boolean>({
    url: "/admin/system-settings/delete",
    method: "post",
    data: { id },
  });
}
