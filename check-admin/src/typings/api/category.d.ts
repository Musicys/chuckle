declare namespace Api {
  namespace Category {
    interface CategoryInfo {
      id: number;
      name: string;
      description: string;
      sortOrder: number;
      createdAt: string;
    }

    interface AddCategoryParams {
      name: string;
      description?: string;
      sortOrder?: number;
    }

    interface UpdateCategoryParams {
      id: number;
      name?: string;
      description?: string;
      sortOrder?: number;
    }

    interface CategoryListParams {
      current?: number;
      pageSize?: number;
      name?: string;
    }

    interface CategoryListResult {
      records: CategoryInfo[];
      total: number;
      current: number;
      size: number;
    }
  }
}