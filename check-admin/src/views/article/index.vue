<script setup lang="ts">
import { ref, reactive, h } from "vue";
import { useRouter } from "vue-router";
import {
  NButton,
  NCard,
  NDataTable,
  NForm,
  NFormItem,
  NInput,
  NSelect,
  NSpace,
  NTag,
  NText,
  useMessage,
} from "naive-ui";
import { fetchArticleList, fetchDeleteArticle } from "@/service/api/article";

const router = useRouter();
const message = useMessage();

const data = ref<Api.Article.ArticleInfo[]>([]);
const loading = ref(false);
const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
});

const searchForm = reactive({
  title: "",
  categoryId: undefined as number | undefined,
  status: undefined as number | undefined,
});

const statusOptions = [
  { label: "草稿", value: 0 },
  { label: "已发布", value: 1 },
  { label: "下架", value: 2 },
];

function getStatusLabel(status: number) {
  const option = statusOptions.find((item) => item.value === status);
  return option?.label || "未知";
}

function getStatusType(status: number) {
  switch (status) {
    case 0:
      return "default";
    case 1:
      return "success";
    case 2:
      return "warning";
    default:
      return "default";
  }
}

async function loadData() {
  loading.value = true;
  try {
    const { data: result, error } = await fetchArticleList({
      current: pagination.current,
      pageSize: pagination.pageSize,
      title: searchForm.title,
      categoryId: searchForm.categoryId,
      status: searchForm.status,
    });
    if (!error && result) {
      data.value = result.records;
      pagination.total = result.total;
    }
  } catch (error) {
    message.error("加载失败");
  } finally {
    loading.value = false;
  }
}

function handlePageChange(page: number) {
  pagination.current = page;
  loadData();
}

function handlePageSizeChange(pageSize: number) {
  pagination.pageSize = pageSize;
  pagination.current = 1;
  loadData();
}

function handleSearch() {
  pagination.current = 1;
  loadData();
}

function handleReset() {
  searchForm.title = "";
  searchForm.categoryId = undefined;
  searchForm.status = undefined;
  handleSearch();
}

function openAddModal() {
  router.push("/article/edit");
}

function openEditModal(row: Api.Article.ArticleInfo) {
  router.push(`/article/edit/${row.id}`);
}

async function handleDelete(row: Api.Article.ArticleInfo) {
  try {
    const { error } = await fetchDeleteArticle(row.id);
    if (!error) {
      message.success("删除成功");
    }
    loadData();
  } catch (error) {
    message.error("删除失败");
  }
}

const columns = [
  {
    title: "封面",
    key: "cover",
    width: 80,
    render(row: Api.Article.ArticleInfo) {
      return row.cover
        ? h("img", {
            src: row.cover,
            style: {
              width: "60px",
              height: "40px",
              objectFit: "cover",
              borderRadius: "4px",
            },
          })
        : "-";
    },
  },
  { title: "标题", key: "title", ellipsis: true },
  { title: "分类", key: "categoryName" },
  {
    title: "标签",
    key: "tags",
    render(row: Api.Article.ArticleInfo) {
      return h(NSpace, null, {
        default: () =>
          row.tags.map((tag) =>
            h(NTag, { type: "info", style: { marginRight: 4 } }, tag.name),
          ),
      });
    },
  },
  { title: "字数", key: "wordCount" },
  { title: "阅读", key: "readCount" },
  { title: "评论", key: "commentCount" },
  {
    title: "状态",
    key: "status",
    render(row: Api.Article.ArticleInfo) {
      return h(
        NTag,
        { type: getStatusType(row.status) },
        getStatusLabel(row.status),
      );
    },
  },
  { title: "创建时间", key: "createdAt" },
  {
    title: "操作",
    key: "actions",
    render(row: Api.Article.ArticleInfo) {
      return h(NSpace, null, {
        default: () => [
          h(
            NButton,
            { size: "small", onClick: () => openEditModal(row) },
            "编辑",
          ),
          h(
            NButton,
            { size: "small", type: "error", onClick: () => handleDelete(row) },
            "删除",
          ),
        ],
      });
    },
  },
];

loadData();
</script>

<template>
  <div>
    <NCard :bordered="false">
      <template #header>
        <NSpace justify="space-between" align="center">
          <NText strong>文章管理</NText>
          <NButton type="primary" @click="openAddModal">新增文章</NButton>
        </NSpace>
      </template>

      <NForm :model="searchForm" inline>
        <NFormItem label="标题">
          <NInput
            v-model:value="searchForm.title"
            placeholder="请输入标题"
            style="width: 200px"
          />
        </NFormItem>
        <NFormItem label="状态">
          <NSelect
            v-model:value="searchForm.status"
            :options="statusOptions"
            placeholder="全部"
            style="width: 120px"
          />
        </NFormItem>
        <NFormItem>
          <NSpace>
            <NButton type="primary" @click="handleSearch">搜索</NButton>
            <NButton @click="handleReset">重置</NButton>
          </NSpace>
        </NFormItem>
      </NForm>

      <NDataTable
        :columns="columns"
        :data="data"
        :loading="loading"
        :pagination="{
          page: pagination.current,
          pageSize: pagination.pageSize,
          itemCount: pagination.total,
          showSizePicker: true,
          pageSizes: [10, 20, 50],
          onUpdatePage: handlePageChange,
          onUpdatePageSize: handlePageSizeChange,
        }"
      />
    </NCard>
  </div>
</template>
