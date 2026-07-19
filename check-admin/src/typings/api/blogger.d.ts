declare namespace Api {
  namespace Blogger {
    interface BloggerInfo {
      id: number;
      avatar: string;
      nickname: string;
      blogTitle: string;
      qq: string;
      github: string;
      gitee: string;
      bilibili: string;
      birthDate: string;
      gender: number;
      occupation: string;
      tags: string;
      hobbies: string;
      games: string;
      latitude: number;
      longitude: number;
      address: string;
      announcement: string;
      config: string;
      isUsed: number;
      createdAt: string;
      updatedAt: string;
    }

    interface AddBloggerParams {
      avatar?: string;
      nickname: string;
      blogTitle?: string;
      qq?: string;
      github?: string;
      gitee?: string;
      bilibili?: string;
      birthDate?: string;
      gender?: number;
      occupation?: string;
      tags?: string;
      hobbies?: string;
      games?: string;
      latitude?: number;
      longitude?: number;
      address?: string;
      announcement?: string;
      config?: string;
    }

    interface UpdateBloggerParams {
      id: number;
      avatar?: string;
      nickname?: string;
      blogTitle?: string;
      qq?: string;
      github?: string;
      gitee?: string;
      bilibili?: string;
      birthDate?: string;
      gender?: number;
      occupation?: string;
      tags?: string;
      hobbies?: string;
      games?: string;
      latitude?: number;
      longitude?: number;
      address?: string;
      announcement?: string;
      config?: string;
    }

    interface BloggerListParams {
      current?: number;
      pageSize?: number;
      id?: number;
      nickname?: string;
      blogTitle?: string;
      isUsed?: number;
    }

    interface BloggerListResult {
      records: BloggerInfo[];
      total: number;
      current: number;
      size: number;
    }
  }
}
