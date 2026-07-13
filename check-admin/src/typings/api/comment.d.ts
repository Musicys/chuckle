declare namespace Api {
  namespace Comment {
    interface CommentInfo {
      id: number;
      articleId: number;
      articleTitle: string;
      parentId: number | null;
      replyToCommentId: number | null;
      nickname: string;
      email: string;
      avatar: string;
      content: string;
      status: number;
      ipAddress: string;
      createdAt: string;
    }

    interface CommentListParams {
      current?: number;
      pageSize?: number;
      articleId?: number;
      status?: number;
      nickname?: string;
    }

    interface CommentListResult {
      records: CommentInfo[];
      total: number;
      current: number;
      size: number;
    }

    interface ReplyCommentParams {
      articleId: number;
      replyToCommentId: number;
      content: string;
    }
  }
}