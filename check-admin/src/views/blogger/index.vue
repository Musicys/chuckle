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
  NModal,
  NSelect,
  NSpace,
  NText,
  NTag,
  useMessage,
  useDialog,
} from "naive-ui";
import {
  fetchAddBlogger,
  fetchUpdateBlogger,
  fetchBloggerList,
  fetchDeleteBlogger,
  fetchSetBloggerUsed,
} from "@/service/api/blogger";

const message = useMessage();
const dialog = useDialog();
const router = useRouter();

const loading = ref(false);

const data = ref<Api.Blogger.BloggerInfo[]>([]);

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
});

const searchForm = reactive({
  nickname: "",
  blogTitle: "",
  isUsed: "",
});

const isUsedOptions = [
  { label: "启用", value: 1 },
  { label: "禁用", value: 0 },
];

const columns = [
  { title: "ID", key: "id", width: 80 },
  {
    title: "头像",
    key: "avatar",
    width: 80,
    render(row: Api.Blogger.BloggerInfo) {
      return h("img", {
        src: row.avatar || "https://via.placeholder.com/40",
        style: { width: "40px", height: "40px", borderRadius: "50%" },
      });
    },
  },
  { title: "昵称", key: "nickname" },
  { title: "博客标题", key: "blogTitle" },
  {
    title: "性别",
    key: "gender",
    width: 80,
    render(row: Api.Blogger.BloggerInfo) {
      return h(NTag, { type: "info" }, row.gender === 1 ? "男" : "女");
    },
  },
  {
    title: "状态",
    key: "isUsed",
    width: 80,
    render(row: Api.Blogger.BloggerInfo) {
      return h(
        NTag,
        { type: row.isUsed === 1 ? "success" : "default" },
        row.isUsed === 1 ? "启用" : "禁用",
      );
    },
  },
  {
    title: "创建时间",
    key: "createdAt",
    render(row: Api.Blogger.BloggerInfo) {
      return new Date(row.createdAt).toLocaleString();
    },
  },
  {
    title: "操作",
    key: "actions",
    width: 280,
    render(row: Api.Blogger.BloggerInfo) {
      return h(NSpace, null, {
        default: () => [
          h(
            NButton,
            {
              type: "primary",
              size: "small",
              onClick: () => router.push(`/blogger/edit/${row.id}`),
            },
            "编辑",
          ),
          h(
            NButton,
            {
              type: row.isUsed === 1 ? "default" : "success",
              size: "small",
              onClick: () => handleSetUsed(row),
            },
            row.isUsed === 1 ? "禁用" : "启用",
          ),
          h(
            NButton,
            { type: "error", size: "small", onClick: () => handleDelete(row) },
            "删除",
          ),
        ],
      });
    },
  },
];

async function loadData() {
  loading.value = true;
  try {
    const { data: result, error } = await fetchBloggerList({
      current: pagination.current,
      pageSize: pagination.pageSize,
      nickname: searchForm.nickname,
      blogTitle: searchForm.blogTitle,
      isUsed: searchForm.isUsed ? Number(searchForm.isUsed) : undefined,
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
  searchForm.nickname = "";
  searchForm.blogTitle = "";
  searchForm.isUsed = "";
  handleSearch();
}

function handleSetUsed(row: Api.Blogger.BloggerInfo) {
  dialog.warning({
    title: "确认操作",
    content: `确定${row.isUsed === 1 ? "禁用" : "启用"}博主信息「${row.nickname}」？`,
    positiveText: "确认",
    negativeText: "取消",
    onPositiveClick: async () => {
      try {
        const { error } = await fetchSetBloggerUsed(row.id);
        if (!error) {
          message.success(`${row.isUsed === 1 ? "禁用" : "启用"}成功`);
          loadData();
        }
      } catch (error) {
        message.error("操作失败");
      }
    },
  });
}

function handleDelete(row: Api.Blogger.BloggerInfo) {
  dialog.warning({
    title: "确认删除",
    content: `确定删除博主信息「${row.nickname}」？`,
    positiveText: "删除",
    negativeText: "取消",
    onPositiveClick: async () => {
      try {
        const { error } = await fetchDeleteBlogger(row.id);
        if (!error) {
          message.success("删除成功");
          loadData();
        }
      } catch (error) {
        message.error("删除失败");
      }
    },
  });
}

loadData();
</script>

<template>
  <div>
    <NCard :bordered="false">
      <template #header>
        <NSpace justify="space-between" align="center">
          <NText strong>博主信息管理</NText>
          <NButton type="primary" @click="() => router.push('/blogger/edit')"
            >新增博主信息</NButton
          >
        </NSpace>
      </template>

      <NForm :model="searchForm" inline>
        <NFormItem label="昵称">
          <NInput
            v-model:value="searchForm.nickname"
            placeholder="请输入昵称"
            @keyup.enter="handleSearch"
          />
        </NFormItem>
        <NFormItem label="博客标题">
          <NInput
            v-model:value="searchForm.blogTitle"
            placeholder="请输入博客标题"
            @keyup.enter="handleSearch"
          />
        </NFormItem>
        <NFormItem label="状态">
          <NSelect
            v-model:value="searchForm.isUsed"
            :options="isUsedOptions"
            placeholder="全部"
          />
        </NFormItem>
        <NSpace>
          <NButton type="primary" @click="handleSearch">搜索</NButton>
          <NButton @click="handleReset">重置</NButton>
        </NSpace>
      </NForm>

      <NDataTable
        :columns="columns"
        :data="data"
        :loading="loading"
        :pagination="{
          page: pagination.current,
          pageSize: pagination.pageSize,
          itemCount: pagination.total,
          onUpdatePage: handlePageChange,
          onUpdatePageSize: handlePageSizeChange,
        }"
      />
    </NCard>
  </div>
</template>
