declare namespace Api {
  namespace Image {
    interface ImageInfo {
      id: number;
      originalName: string;
      url: string;
      storagePath?: string;
      fileSize: number;
      fileType: string;
      width: number;
      height: number;
      md5?: string;
      source: string;
      remark: string;
      createdAt: string;
      updatedAt?: string;
    }

    interface UpdateImageParams {
      id: number;
      remark?: string;
    }

    interface ImageListParams {
      current?: number;
      pageSize?: number;
      originalName?: string;
      fileType?: string;
      source?: string;
    }

    interface ImageListResult {
      records: ImageInfo[];
      total: number;
      current: number;
      size: number;
    }
  }
}
