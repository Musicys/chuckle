declare namespace Api {
  namespace FriendLink {
    interface FriendLinkInfo {
      id: number;
      name: string;
      url: string;
      avatar: string;
      description: string;
      email: string;
      sortOrder: number;
      status: number;
      createdAt: string;
      updatedAt: string;
    }

    interface AddFriendLinkParams {
      name: string;
      url?: string;
      avatar?: string;
      description?: string;
      email?: string;
      sortOrder?: number;
      status?: number;
    }

    interface UpdateFriendLinkParams {
      id: number;
      name?: string;
      url?: string;
      avatar?: string;
      description?: string;
      email?: string;
      sortOrder?: number;
      status?: number;
    }

    interface FriendLinkListParams {
      current?: number;
      pageSize?: number;
      name?: string;
      status?: number;
    }

    interface FriendLinkListResult {
      records: FriendLinkInfo[];
      total: number;
      current: number;
      size: number;
    }
  }
}