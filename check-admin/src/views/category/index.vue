<script setup lang="ts">
import { ref, reactive, h } from "vue";
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
  useMessage,
  useDialog,
} from "naive-ui";
import {
  fetchAddCategory,
  fetchUpdateCategory,
  fetchCategoryList,
  fetchDeleteCategory,
} from "@/service/api/category";

const message = useMessage();
const dialog = useDialog();

const loading = ref(false);
const submitLoading = ref(false);

const data = ref<Api.Category.CategoryInfo[]>([]);

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
});

const searchForm = reactive({
  name: "",
});

const showModal = ref(false);
const isEdit = ref(false);
const form = reactive({
  id: 0,
  name: "",
  description: "",
  sortOrder: "0",
});

const columns = [
  { title: "ID", key: "id", width: 80 },
  { title: "分类名称", key: "name" },
  { title: "描述", key: "description" },
  { title: "排序", key: "sortOrder", width: 80 },
  {
    title: "创建时间",
    key: "createdAt",
    render(row: Api.Category.CategoryInfo) {
      return new Date(row.createdAt).toLocaleString();
    },
  },
  {
    title: "操作",
    key: "actions",
    width: 200,
    render(row: Api.Category.CategoryInfo) {
      return h(NSpace, null, {
        default: () => [
          h(
            NButton,
            { type: "primary", size: "small", onClick: () => handleEdit(row) },
            "编辑",
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
    const { data: result, error } = await fetchCategoryList({
      current: pagination.current,
      pageSize: pagination.pageSize,
      name: searchForm.name,
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
  searchForm.name = "";
  handleSearch();
}

function openAddModal() {
  isEdit.value = false;
  form.id = 0;
  form.name = "";
  form.description = "";
  form.sortOrder = "0";
  showModal.value = true;
}

function handleEdit(row: Api.Category.CategoryInfo) {
  isEdit.value = true;
  form.id = row.id;
  form.name = row.name;
  form.description = row.description;
  form.sortOrder = String(row.sortOrder);
  showModal.value = true;
}

function handleDelete(row: Api.Category.CategoryInfo) {
  dialog.warning({
    title: "确认删除",
    content: `确定删除分类「${row.name}」？`,
    positiveText: "删除",
    negativeText: "取消",
    onPositiveClick: async () => {
      try {
        const { error } = await fetchDeleteCategory(row.id);
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

async function handleSubmit() {
  if (!form.name.trim()) {
    message.warning("请输入分类名称");
    return;
  }

  submitLoading.value = true;
  try {
    if (isEdit.value) {
      const { error } = await fetchUpdateCategory({
        id: form.id,
        name: form.name,
        description: form.description,
        sortOrder: Number(form.sortOrder) || 0,
      });
      if (!error) {
        message.success("更新成功");
      }
    } else {
      const { error } = await fetchAddCategory({
        name: form.name,
        description: form.description,
        sortOrder: Number(form.sortOrder) || 0,
      });
      if (!error) {
        message.success("新增成功");
      }
    }
    showModal.value = false;
    loadData();
  } catch (error) {
    message.error("操作失败");
  } finally {
    submitLoading.value = false;
  }
}

loadData();
</script>

<template>
  <div>
    <NCard :bordered="false">
      <template #header>
        <NSpace justify="space-between" align="center">
          <NText strong>分类管理</NText>
          <NButton type="primary" @click="openAddModal">新增分类</NButton>
        </NSpace>
      </template>

      <NForm :model="searchForm" inline>
        <NFormItem label="分类名称">
          <NInput
            v-model:value="searchForm.name"
            placeholder="请输入分类名称"
            @keyup.enter="handleSearch"
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

    <NModal
      v-model:show="showModal"
      preset="card"
      :title="isEdit ? '编辑分类' : '新增分类'"
      style="width: 450px"
    >
      <NForm :model="form" label-placement="left" :label-width="80">
        <NFormItem label="分类名称" required>
          <NInput v-model:value="form.name" placeholder="请输入分类名称" />
        </NFormItem>
        <NFormItem label="描述">
          <NInput
            v-model:value="form.description"
            placeholder="请输入分类描述"
          />
        </NFormItem>
        <NFormItem label="排序">
          <NInput v-model:value="form.sortOrder" placeholder="请输入排序值" />
        </NFormItem>
      </NForm>
      <template #action>
        <NSpace>
          <NButton @click="showModal = false">取消</NButton>
          <NButton
            type="primary"
            :loading="submitLoading"
            @click="handleSubmit"
          >
            {{ isEdit ? "保存修改" : "创建" }}
          </NButton>
        </NSpace>
      </template>
    </NModal>
  </div>
</template>
