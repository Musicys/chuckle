declare namespace Api {
  namespace Tag {
    interface TagInfo {
      id: number;
      name: string;
      color: string;
    }

    interface AddTagParams {
      name: string;
      color?: string;
    }

    interface UpdateTagParams {
      id: number;
      name?: string;
      color?: string;
    }

    interface TagListParams {
      current?: number;
      pageSize?: number;
      name?: string;
    }

    interface TagListResult {
      records: TagInfo[];
      total: number;
      current: number;
      size: number;
    }
  }
}
