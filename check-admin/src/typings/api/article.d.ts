declare namespace Api {
  namespace Article {
    interface Tag {
      id: number;
      name: string;
      color: string;
    }

    interface ArticleInfo {
      id: number;
      title: string;
      content: string;
      summary: string;
      categoryId: number;
      categoryName: string;
      tags: Tag[];
      wordCount: number;
      readCount: number;
      commentCount: number;
      status: number;
      createdAt: string;
      updatedAt: string;
    }

    interface AddArticleParams {
      title: string;
      content: string;
      summary: string;
      categoryId: number;
      status: number;
      tagIds?: number[];
    }

    interface UpdateArticleParams {
      id: number;
      title?: string;
      content?: string;
      summary?: string;
      categoryId?: number;
      status?: number;
      tagIds?: number[];
    }

    interface ArticleListParams {
      current?: number;
      pageSize?: number;
      title?: string;
      categoryId?: number;
      status?: number;
    }

    interface ArticleListResult {
      records: ArticleInfo[];
      total: number;
      current: number;
      size: number;
    }
  }
}