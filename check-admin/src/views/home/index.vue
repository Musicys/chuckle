<script setup lang="ts">
import { ref, onMounted, computed, nextTick } from "vue";
import { useRouter } from "vue-router";
import { useAppStore } from "@/store/modules/app";
import { useAuthStore } from "@/store/modules/auth";
import { fetchDashboardStats } from "@/service/api/stats";
import { $t } from "@/locales";

defineOptions({
  name: "Dashboard",
});

const router = useRouter();
const appStore = useAppStore();
const authStore = useAuthStore();

const loading = ref(true);
const stats = ref<Api.Common.DashboardStats | null>(null);
const renderKey = ref(0);

const gap = computed(() => (appStore.isMobile ? 12 : 16));

const fetchData = async () => {
  try {
    loading.value = true;
    const res = await fetchDashboardStats();
    console.log("Dashboard API response:", res);
    const data = res?.data || res;
    console.log("articleCount:", data?.articleCount);
    stats.value = data as any;
    await nextTick();
    renderKey.value++;
    console.log("stats.value after assign:", stats.value);
    console.log("statCards computed:", statCards.value);
  } catch (error) {
    console.error("获取仪表盘数据失败:", error);
  } finally {
    loading.value = false;
  }
};

const statCards = computed(() => {
  const s = stats.value;
  const num = (v: any) => Number(v) || 0;
  return [
    {
      key: "article",
      title: "文章总数",
      value: num(s?.articleCount),
      icon: "ant-design:file-text-outlined",
      color: "#18a058",
      bgGradient: "linear-gradient(135deg, #667eea 0%, #764ba2 100%)",
    },
    {
      key: "comment",
      title: "评论总数",
      value: num(s?.commentCount),
      icon: "ant-design:message-outlined",
      color: "#2080f0",
      bgGradient: "linear-gradient(135deg, #f093fb 0%, #f5576c 100%)",
    },
    {
      key: "portfolio",
      title: "作品总数",
      value: num(s?.portfolioCount),
      icon: "ant-design:appstore-outlined",
      color: "#f0a020",
      bgGradient: "linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)",
    },
    {
      key: "friendLink",
      title: "友链总数",
      value: num(s?.friendLinkCount),
      icon: "ant-design:link-outlined",
      color: "#d03050",
      bgGradient: "linear-gradient(135deg, #43e97b 0%, #38f9d7 100%)",
    },
    {
      key: "todayPv",
      title: "今日 PV",
      value: num(s?.todayPv),
      icon: "ant-design:eye-outlined",
      color: "#18a058",
      bgGradient: "linear-gradient(135deg, #fa709a 0%, #fee140 100%)",
    },
    {
      key: "todayUv",
      title: "今日 UV",
      value: num(s?.todayUv),
      icon: "ant-design:team-outlined",
      color: "#2080f0",
      bgGradient: "linear-gradient(135deg, #a8edea 0%, #fed6e3 100%)",
    },
    {
      key: "totalPv",
      title: "总访问量",
      value: num(s?.totalPv),
      icon: "ant-design:global-outlined",
      color: "#f0a020",
      bgGradient: "linear-gradient(135deg, #667eea 0%, #764ba2 100%)",
    },
  ];
});

const recentArticles = computed(() => stats.value?.recentArticles || []);
const recentComments = computed(() => stats.value?.recentComments || []);

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div class="dashboard-container" :key="renderKey">
    <!-- 欢迎横幅 -->
    <NCard :bordered="false" class="welcome-card">
      <div class="welcome-content">
        <div class="welcome-left">
          <div class="welcome-text">
            <h2 class="welcome-title">
              {{
                $t("page.home.greeting", {
                  userName: authStore.userInfo.userName,
                })
              }}
            </h2>
            <p class="welcome-desc">欢迎来到博客管理后台，今天也要加油哦！</p>
          </div>
        </div>
        <div class="welcome-right">
          <div class="quick-stats">
            <div class="quick-stat-item">
              <span class="quick-stat-value">{{
                Number(stats?.totalPv || 0).toLocaleString()
              }}</span>
              <span class="quick-stat-label">总访问</span>
            </div>
            <div class="quick-stat-divider"></div>
            <div class="quick-stat-item">
              <span class="quick-stat-value">{{
                Number(stats?.todayPv || 0).toLocaleString()
              }}</span>
              <span class="quick-stat-label">今日 PV</span>
            </div>
            <div class="quick-stat-divider"></div>
            <div class="quick-stat-item">
              <span class="quick-stat-value">{{
                Number(stats?.todayUv || 0).toLocaleString()
              }}</span>
              <span class="quick-stat-label">今日 UV</span>
            </div>
          </div>
        </div>
      </div>
    </NCard>

    <!-- 统计卡片 -->
    <NCard :bordered="false" class="stat-cards-card">
      <NGrid
        cols="s:1 m:2 l:4 xl:7"
        responsive="screen"
        :x-gap="gap"
        :y-gap="16"
      >
        <NGi v-for="item in statCards" :key="item.key">
          <div class="stat-card" :style="{ background: item.bgGradient }">
            <div class="stat-card-icon">
              <SvgIcon :icon="item.icon" class="icon" />
            </div>
            <div class="stat-card-info">
              <div class="stat-card-value">
                {{ Number(item.value || 0).toLocaleString() }}
              </div>
              <div class="stat-card-title">{{ item.title }}</div>
            </div>
          </div>
        </NGi>
      </NGrid>
    </NCard>

    <!-- 内容区域 -->
    <NGrid :x-gap="gap" :y-gap="16" responsive="screen" item-responsive>
      <!-- 最近文章 -->
      <NGi span="24 s:24 m:14">
        <NCard :bordered="false" class="content-card">
          <template #header>
            <div class="card-header">
              <span class="card-title">
                <SvgIcon
                  icon="ant-design:file-text-outlined"
                  class="title-icon"
                />
                <span class="title-text">最近文章</span>
              </span>
              <NButton text type="primary" @click="router.push('/article')">
                查看全部
                <SvgIcon icon="ant-design:right-outlined" />
              </NButton>
            </div>
          </template>
          <NSpin :show="loading">
            <NEmpty
              v-if="!loading && recentArticles.length === 0"
              description="暂无文章"
            />
            <div v-else class="recent-list">
              <div
                v-for="article in recentArticles"
                :key="article.id"
                class="recent-item"
              >
                <div class="recent-item-content">
                  <div class="recent-item-title" :title="article.title">
                    {{ article.title }}
                  </div>
                  <div class="recent-item-meta">
                    <NTag
                      :type="article.status === 1 ? 'success' : 'warning'"
                      size="small"
                      round
                    >
                      {{ article.status === 1 ? "已发布" : "草稿" }}
                    </NTag>
                    <span class="recent-item-time">{{
                      article.createdAt
                    }}</span>
                  </div>
                </div>
              </div>
            </div>
          </NSpin>
        </NCard>
      </NGi>

      <!-- 最近评论 -->
      <NGi span="24 s:24 m:10">
        <NCard :bordered="false" class="content-card">
          <template #header>
            <div class="card-header">
              <span class="card-title">
                <SvgIcon
                  icon="ant-design:message-outlined"
                  class="title-icon"
                />
                <span class="title-text">最近评论</span>
              </span>
              <NButton text type="primary" @click="router.push('/comment')">
                查看全部
                <SvgIcon icon="ant-design:right-outlined" />
              </NButton>
            </div>
          </template>
          <NSpin :show="loading">
            <NEmpty
              v-if="!loading && recentComments.length === 0"
              description="暂无评论"
            />
            <div v-else class="recent-list">
              <div
                v-for="comment in recentComments"
                :key="comment.id"
                class="recent-item comment-item"
              >
                <div class="comment-avatar">
                  <SvgIcon
                    icon="ant-design:user-outlined"
                    class="avatar-icon"
                  />
                </div>
                <div class="comment-content">
                  <div class="comment-header">
                    <span class="comment-nickname">{{ comment.nickname }}</span>
                    <span class="comment-time">{{ comment.createdAt }}</span>
                  </div>
                  <div class="comment-text">{{ comment.content }}</div>
                </div>
              </div>
            </div>
          </NSpin>
        </NCard>
      </NGi>
    </NGrid>

    <!-- 快捷操作 -->
    <NCard :bordered="false" class="quick-actions-card">
      <template #header>
        <span class="card-title">快捷操作</span>
      </template>
      <div class="quick-actions">
        <NButton
          size="large"
          type="primary"
          :on-click="() => router.push('/article/edit')"
        >
          <template #icon>
            <SvgIcon icon="ant-design:plus-outlined" />
          </template>
          写文章
        </NButton>
        <NButton size="large" :on-click="() => router.push('/portfolio')">
          <template #icon>
            <SvgIcon icon="ant-design:appstore-outlined" />
          </template>
          管理作品
        </NButton>
        <NButton size="large" :on-click="() => router.push('/friend-link')">
          <template #icon>
            <SvgIcon icon="ant-design:link-outlined" />
          </template>
          管理友链
        </NButton>
        <NButton size="large" :on-click="() => router.push('/tag')">
          <template #icon>
            <SvgIcon icon="ant-design:tags-outlined" />
          </template>
          管理标签
        </NButton>
        <NButton size="large" :on-click="() => router.push('/category')">
          <template #icon>
            <SvgIcon icon="ant-design:folder-outlined" />
          </template>
          管理分类
        </NButton>
      </div>
    </NCard>
  </div>
</template>

<style scoped>
.dashboard-container {
  display: flex;
  flex-direction: column;
  gap: 16px;
  padding: 16px;
}

.welcome-card {
  overflow: hidden;
}

.welcome-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 16px;
}

.welcome-title {
  font-size: 24px;
  font-weight: 600;
  margin: 0 0 8px 0;
}

.welcome-desc {
  color: #999;
  margin: 0;
}

.quick-stats {
  display: flex;
  align-items: center;
  gap: 24px;
}

.quick-stat-item {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.quick-stat-value {
  font-size: 24px;
  font-weight: 600;
  color: #18a058;
}

.quick-stat-label {
  font-size: 12px;
  color: #999;
  margin-top: 4px;
}

.quick-stat-divider {
  width: 1px;
  height: 40px;
  background-color: #eee;
}

.stat-cards-card {
  background: transparent;
}

.stat-card {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 20px;
  border-radius: 12px;
  color: white;
  transition: transform 0.3s ease;
  cursor: pointer;
}

.stat-card:hover {
  transform: translateY(-4px);
}

.stat-card-icon {
  width: 56px;
  height: 56px;
  border-radius: 12px;
  background: rgba(255, 255, 255, 0.2);
  display: flex;
  align-items: center;
  justify-content: center;
}

.stat-card-icon .icon {
  font-size: 28px;
  color: white;
}

.stat-card-info {
  flex: 1;
}

.stat-card-value {
  font-size: 28px;
  font-weight: 600;
  line-height: 1;
}

.stat-card-title {
  font-size: 14px;
  opacity: 0.9;
  margin-top: 8px;
}

.content-card {
  height: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-title {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 16px;
  font-weight: 600;
}

.title-icon {
  font-size: 20px;
  color: var(--n-color-primary);
}

.title-text {
  margin-left: 4px;
}

.avatar-icon {
  font-size: 20px;
  color: #999;
}

.recent-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.recent-item {
  padding: 12px;
  border-radius: 8px;
  background: #f8f8f8;
  transition: background 0.2s;
}

.recent-item:hover {
  background: #f0f0f0;
}

.recent-item-content {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.recent-item-title {
  font-size: 14px;
  font-weight: 500;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.recent-item-meta {
  display: flex;
  align-items: center;
  gap: 12px;
}

.recent-item-time {
  font-size: 12px;
  color: #999;
}

.comment-item {
  display: flex;
  gap: 12px;
  align-items: flex-start;
}

.comment-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: #e0e0e0;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  color: #999;
}

.comment-content {
  flex: 1;
  min-width: 0;
}

.comment-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 4px;
}

.comment-nickname {
  font-weight: 500;
  font-size: 14px;
}

.comment-time {
  font-size: 12px;
  color: #999;
}

.comment-text {
  font-size: 13px;
  color: #666;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.quick-actions-card {
  background: transparent;
}

.quick-actions {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
}

.quick-actions .n-button {
  font-size: 14px;
}

@media (max-width: 768px) {
  .welcome-content {
    flex-direction: column;
    align-items: flex-start;
  }

  .quick-stats {
    width: 100%;
    justify-content: space-around;
  }

  .stat-card {
    padding: 16px;
  }

  .stat-card-value {
    font-size: 24px;
  }
}
</style>
