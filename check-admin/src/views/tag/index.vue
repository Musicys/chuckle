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
  NSpace,
  NTag,
  NText,
  useMessage,
} from "naive-ui";
import {
  fetchAddTag,
  fetchUpdateTag,
  fetchTagList,
  fetchDeleteTag,
} from "@/service/api/tag";

const message = useMessage();

const data = ref<Api.Tag.TagInfo[]>([]);
const loading = ref(false);
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
const modalLoading = ref(false);

const form = reactive({
  id: undefined as number | undefined,
  name: "",
  color: "#409EFF",
});

async function loadData() {
  loading.value = true;
  try {
    const { data: result, error } = await fetchTagList({
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
  form.id = undefined;
  form.name = "";
  form.color = "#409EFF";
  showModal.value = true;
}

function openEditModal(row: Api.Tag.TagInfo) {
  isEdit.value = true;
  form.id = row.id;
  form.name = row.name;
  form.color = row.color;
  showModal.value = true;
}

async function handleSubmit() {
  if (!form.name.trim()) {
    message.warning("请输入标签名称");
    return;
  }

  modalLoading.value = true;
  try {
    if (isEdit.value && form.id) {
      const { error } = await fetchUpdateTag({
        id: form.id,
        name: form.name,
        color: form.color,
      });
      if (!error) {
        message.success("更新成功");
      }
    } else {
      const { error } = await fetchAddTag({
        name: form.name,
        color: form.color,
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
    modalLoading.value = false;
  }
}

async function handleDelete(row: Api.Tag.TagInfo) {
  try {
    const { error } = await fetchDeleteTag(row.id);
    if (!error) {
      message.success("删除成功");
    }
    loadData();
  } catch (error) {
    message.error("删除失败");
  }
}

const columns = [
  { title: "ID", key: "id", width: 80 },
  { title: "名称", key: "name" },
  {
    title: "颜色",
    key: "color",
    width: 120,
    render(row: Api.Tag.TagInfo) {
      return h(
        NTag,
        { type: "info", style: { backgroundColor: row.color, color: "#fff" } },
        row.color,
      );
    },
  },
  {
    title: "预览",
    key: "preview",
    width: 120,
    render(row: Api.Tag.TagInfo) {
      return h(
        NTag,
        { style: { backgroundColor: row.color, color: "#fff" } },
        row.name,
      );
    },
  },
  {
    title: "操作",
    key: "actions",
    width: 160,
    render(row: Api.Tag.TagInfo) {
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
          <NText strong>标签管理</NText>
          <NButton type="primary" @click="openAddModal">新增标签</NButton>
        </NSpace>
      </template>

      <NForm :model="searchForm" inline>
        <NFormItem label="标签名称">
          <NInput
            v-model:value="searchForm.name"
            placeholder="请输入标签名称"
            style="width: 200px"
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

    <NModal
      v-model:show="showModal"
      preset="card"
      :title="isEdit ? '编辑标签' : '新增标签'"
      style="width: 500px"
    >
      <NForm :model="form" label-placement="left" :label-width="80">
        <NFormItem label="标签名称" required>
          <NInput v-model:value="form.name" placeholder="请输入标签名称" />
        </NFormItem>
        <NFormItem label="标签颜色">
          <NInput
            v-model:value="form.color"
            placeholder="请输入颜色值，如 #409EFF"
          />
        </NFormItem>
        <NFormItem label="颜色预览">
          <NTag :style="{ backgroundColor: form.color, color: '#fff' }">{{
            form.name || "标签预览"
          }}</NTag>
        </NFormItem>
      </NForm>
      <template #action>
        <NSpace>
          <NButton @click="showModal = false">取消</NButton>
          <NButton type="primary" :loading="modalLoading" @click="handleSubmit">
            确定
          </NButton>
        </NSpace>
      </template>
    </NModal>
  </div>
</template>
