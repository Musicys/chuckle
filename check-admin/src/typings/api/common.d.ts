/**
 * Namespace Api
 *
 * All backend api type
 */
declare namespace Api {
  namespace Common {
    /** common params of paginating */
    interface PaginatingCommonParams {
      /** current page number */
      current: number;
      /** page size */
      size: number;
      /** total count */
      total: number;
    }

    /** common params of paginating query list data */
    interface PaginatingQueryRecord<T = any> extends PaginatingCommonParams {
      records: T[];
    }

    /** common search params of table */
    type CommonSearchParams = Pick<
      Common.PaginatingCommonParams,
      "current" | "size"
    >;

    /**
     * enable status
     *
     * - "1": enabled
     * - "2": disabled
     */
    type EnableStatus = "1" | "2";

    /** common record */
    type CommonRecord<T = any> = {
      /** record id */
      id: number;
      /** record creator */
      createBy: string;
      /** record create time */
      createTime: string;
      /** record updater */
      updateBy: string;
      /** record update time */
      updateTime: string;
      /** record status */
      status: EnableStatus | null;
    } & T;

    /** dashboard stats */
    interface DashboardStats {
      articleCount: number;
      commentCount: number;
      portfolioCount: number;
      friendLinkCount: number;
      todayPv: number;
      todayUv: number;
      totalPv: number;
      recentArticles: Array<{
        id: number;
        title: string;
        status: number;
        createdAt: string;
      }>;
      recentComments: Array<{
        id: number;
        nickname: string;
        content: string;
        createdAt: string;
      }>;
    }

    /** visit stats */
    interface VisitStats {
      todayPv: number;
      todayUv: number;
      monthPv: number;
      totalPv: number;
    }

    /** page data */
    interface PageData<T> {
      records: T[];
      total: number;
      current: number;
      size: number;
    }

    /** visit log */
    interface VisitLog {
      id: number;
      ip: string;
      userAgent: string;
      pageUrl: string;
      visitDate: string;
      createdAt: string;
    }

    /** daily stat */
    interface DailyStat {
      id: number;
      statDate: string;
      pv: number;
      uv: number;
      createdAt: string;
      updatedAt: string;
    }

    /** daily stats summary */
    interface DailyStatsSummary {
      totalPv: number;
      totalUv: number;
      todayPv: number;
      todayUv: number;
    }

    /** system setting */
    interface SystemSetting {
      id: number;
      settingKey: string;
      settingValue: string;
      description: string;
      createdAt: string;
      updatedAt: string;
    }
  }
}
