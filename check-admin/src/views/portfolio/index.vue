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
  NSelect,
} from "naive-ui";
import {
  fetchAddPortfolio,
  fetchUpdatePortfolio,
  fetchPortfolioList,
  fetchDeletePortfolio,
} from "@/service/api/portfolio";
import ImageSelector from "@/components/common/image-selector.vue";

const message = useMessage();

const data = ref<Api.Portfolio.PortfolioInfo[]>([]);
const loading = ref(false);
const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
});

const searchForm = reactive({
  title: "",
  category: "",
  status: undefined as number | undefined,
});

const showModal = ref(false);
const isEdit = ref(false);
const modalLoading = ref(false);

const form = reactive({
  id: undefined as number | undefined,
  title: "",
  description: "",
  category: "",
  cover: "",
  projectUrl: "",
  sortOrder: 0,
  status: 1,
});

async function loadData() {
  loading.value = true;
  try {
    const { data: result, error } = await fetchPortfolioList({
      current: pagination.current,
      pageSize: pagination.pageSize,
      title: searchForm.title,
      category: searchForm.category,
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
  searchForm.category = "";
  searchForm.status = undefined;
  handleSearch();
}

function openAddModal() {
  isEdit.value = false;
  form.id = undefined;
  form.title = "";
  form.description = "";
  form.category = "";
  form.cover = "";
  form.projectUrl = "";
  form.sortOrder = 0;
  form.status = 1;
  showModal.value = true;
}

function openEditModal(row: Api.Portfolio.PortfolioInfo) {
  isEdit.value = true;
  form.id = row.id;
  form.title = row.title;
  form.description = row.description;
  form.category = row.category;
  form.cover = row.cover;
  form.projectUrl = row.projectUrl;
  form.sortOrder = row.sortOrder;
  form.status = row.status;
  showModal.value = true;
}

async function handleSubmit() {
  if (!form.title.trim()) {
    message.warning("请输入作品名称");
    return;
  }

  modalLoading.value = true;
  try {
    if (isEdit.value && form.id) {
      const { error } = await fetchUpdatePortfolio({
        id: form.id,
        title: form.title,
        description: form.description,
        category: form.category,
        cover: form.cover,
        projectUrl: form.projectUrl,
        sortOrder: form.sortOrder,
        status: form.status,
      });
      if (!error) {
        message.success("更新成功");
      }
    } else {
      const { error } = await fetchAddPortfolio({
        title: form.title,
        description: form.description,
        category: form.category,
        cover: form.cover,
        projectUrl: form.projectUrl,
        sortOrder: form.sortOrder,
        status: form.status,
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

async function handleDelete(row: Api.Portfolio.PortfolioInfo) {
  try {
    const { error } = await fetchDeletePortfolio(row.id);
    if (!error) {
      message.success("删除成功");
    }
    loadData();
  } catch (error) {
    message.error("删除失败");
  }
}

function getStatusTag(status: number) {
  if (status === 1) {
    return h(NTag, { type: "success" }, "展示");
  }
  return h(NTag, { type: "info" }, "隐藏");
}

const columns = [
  { title: "ID", key: "id", width: 80 },
  { title: "作品名称", key: "title" },
  { title: "分类", key: "category", width: 120 },
  {
    title: "封面",
    key: "cover",
    width: 150,
    render(row: Api.Portfolio.PortfolioInfo) {
      return row.cover
        ? h("img", {
            src: row.cover,
            style: {
              width: "80px",
              height: "60px",
              objectFit: "cover",
              borderRadius: "4px",
            },
          })
        : h(NTag, { type: "default" }, "无封面");
    },
  },
  { title: "项目地址", key: "projectUrl", width: 200 },
  { title: "排序", key: "sortOrder", width: 80 },
  {
    title: "状态",
    key: "status",
    width: 100,
    render(row: Api.Portfolio.PortfolioInfo) {
      return getStatusTag(row.status);
    },
  },
  {
    title: "操作",
    key: "actions",
    width: 160,
    render(row: Api.Portfolio.PortfolioInfo) {
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
          <NText strong>作品管理</NText>
          <NButton type="primary" @click="openAddModal">新增作品</NButton>
        </NSpace>
      </template>

      <NForm :model="searchForm" inline>
        <NFormItem label="作品名称">
          <NInput
            v-model:value="searchForm.title"
            placeholder="请输入作品名称"
            style="width: 200px"
          />
        </NFormItem>
        <NFormItem label="分类">
          <NInput
            v-model:value="searchForm.category"
            placeholder="请输入分类"
            style="width: 150px"
          />
        </NFormItem>
        <NFormItem label="状态">
          <NSelect
            v-model:value="searchForm.status"
            placeholder="全部"
            style="width: 120px"
            :options="[
              { label: '展示', value: 1 },
              { label: '隐藏', value: 0 },
            ]"
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
      :title="isEdit ? '编辑作品' : '新增作品'"
      style="width: 600px"
    >
      <NForm :model="form" label-placement="left" :label-width="100">
        <NFormItem label="作品名称" required>
          <NInput v-model:value="form.title" placeholder="请输入作品名称" />
        </NFormItem>
        <NFormItem label="分类">
          <NInput v-model:value="form.category" placeholder="请输入分类" />
        </NFormItem>
        <NFormItem label="描述">
          <NInput
            v-model:value="form.description"
            type="textarea"
            placeholder="请输入作品描述"
            :rows="3"
          />
        </NFormItem>
        <NFormItem label="封面">
          <div class="cover-selector">
            <ImageSelector
              v-model="form.cover"
              :multiple="false"
              source="portfolio"
            />
            <div v-if="form.cover" class="cover-preview">
              <img :src="form.cover" alt="封面预览" />
            </div>
          </div>
        </NFormItem>
        <NFormItem label="项目地址">
          <NInput
            v-model:value="form.projectUrl"
            placeholder="请输入项目地址"
          />
        </NFormItem>
        <NFormItem label="排序">
          <NInput
            v-model:value="form.sortOrder"
            type="number"
            placeholder="请输入排序值"
          />
        </NFormItem>
        <NFormItem label="状态">
          <NSelect
            v-model:value="form.status"
            :options="[
              { label: '展示', value: 1 },
              { label: '隐藏', value: 0 },
            ]"
          />
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

<style scoped>
.cover-selector {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.cover-preview {
  width: 150px;
  height: 100px;
  border-radius: 4px;
  overflow: hidden;
  border: 2px solid #e5e7eb;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.cover-preview img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>
