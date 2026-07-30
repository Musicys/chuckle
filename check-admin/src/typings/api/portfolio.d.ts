declare namespace Api {
  namespace Portfolio {
    interface PortfolioInfo {
      id: number;
      title: string;
      description: string;
      category: string;
      cover: string;
      projectUrl: string;
      sortOrder: number;
      status: number;
      createdAt: string;
      updatedAt: string;
    }

    interface AddPortfolioParams {
      title: string;
      description?: string;
      category?: string;
      cover?: string;
      projectUrl?: string;
      sortOrder?: number;
      status?: number;
    }

    interface UpdatePortfolioParams {
      id: number;
      title?: string;
      description?: string;
      category?: string;
      cover?: string;
      projectUrl?: string;
      sortOrder?: number;
      status?: number;
    }

    interface PortfolioListParams {
      current?: number;
      pageSize?: number;
      title?: string;
      category?: string;
      status?: number;
    }

    interface PortfolioListResult {
      records: PortfolioInfo[];
      total: number;
      current: number;
      size: number;
    }
  }
}