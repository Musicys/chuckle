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
  fetchAddFriendLink,
  fetchUpdateFriendLink,
  fetchFriendLinkList,
  fetchDeleteFriendLink,
} from "@/service/api/friend-link";
import ImageSelector from "@/components/common/image-selector.vue";

const message = useMessage();

const data = ref<Api.FriendLink.FriendLinkInfo[]>([]);
const loading = ref(false);
const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
});

const searchForm = reactive({
  name: "",
  status: undefined as number | undefined,
});

const showModal = ref(false);
const isEdit = ref(false);
const modalLoading = ref(false);

const form = reactive({
  id: undefined as number | undefined,
  name: "",
  url: "",
  avatar: "",
  description: "",
  email: "",
  sortOrder: 0,
  status: 1,
});

async function loadData() {
  loading.value = true;
  try {
    const { data: result, error } = await fetchFriendLinkList({
      current: pagination.current,
      pageSize: pagination.pageSize,
      name: searchForm.name,
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
  searchForm.name = "";
  searchForm.status = undefined;
  handleSearch();
}

function openAddModal() {
  isEdit.value = false;
  form.id = undefined;
  form.name = "";
  form.url = "";
  form.avatar = "";
  form.description = "";
  form.email = "";
  form.sortOrder = 0;
  form.status = 1;
  showModal.value = true;
}

function openEditModal(row: Api.FriendLink.FriendLinkInfo) {
  isEdit.value = true;
  form.id = row.id;
  form.name = row.name;
  form.url = row.url;
  form.avatar = row.avatar;
  form.description = row.description;
  form.email = row.email;
  form.sortOrder = row.sortOrder;
  form.status = row.status;
  showModal.value = true;
}

async function handleSubmit() {
  if (!form.name.trim()) {
    message.warning("请输入友链名称");
    return;
  }
  if (!form.url.trim()) {
    message.warning("请输入友链地址");
    return;
  }

  modalLoading.value = true;
  try {
    if (isEdit.value && form.id) {
      const { error } = await fetchUpdateFriendLink({
        id: form.id,
        name: form.name,
        url: form.url,
        avatar: form.avatar,
        description: form.description,
        email: form.email,
        sortOrder: form.sortOrder,
        status: form.status,
      });
      if (!error) {
        message.success("更新成功");
      }
    } else {
      const { error } = await fetchAddFriendLink({
        name: form.name,
        url: form.url,
        avatar: form.avatar,
        description: form.description,
        email: form.email,
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

async function handleDelete(row: Api.FriendLink.FriendLinkInfo) {
  try {
    const { error } = await fetchDeleteFriendLink(row.id);
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
  { title: "友链名称", key: "name" },
  {
    title: "头像",
    key: "avatar",
    width: 100,
    render(row: Api.FriendLink.FriendLinkInfo) {
      return row.avatar
        ? h("img", {
            src: row.avatar,
            style: {
              width: "40px",
              height: "40px",
              borderRadius: "50%",
              objectFit: "cover",
            },
          })
        : h(NTag, { type: "default" }, "无头像");
    },
  },
  { title: "友链地址", key: "url", width: 200 },
  { title: "描述", key: "description", width: 150 },
  { title: "邮箱", key: "email", width: 150 },
  { title: "排序", key: "sortOrder", width: 80 },
  {
    title: "状态",
    key: "status",
    width: 100,
    render(row: Api.FriendLink.FriendLinkInfo) {
      return getStatusTag(row.status);
    },
  },
  {
    title: "操作",
    key: "actions",
    width: 160,
    render(row: Api.FriendLink.FriendLinkInfo) {
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
          <NText strong>友链管理</NText>
          <NButton type="primary" @click="openAddModal">新增友链</NButton>
        </NSpace>
      </template>

      <NForm :model="searchForm" inline>
        <NFormItem label="友链名称">
          <NInput
            v-model:value="searchForm.name"
            placeholder="请输入友链名称"
            style="width: 200px"
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
      :title="isEdit ? '编辑友链' : '新增友链'"
      style="width: 600px"
    >
      <NForm :model="form" label-placement="left" :label-width="100">
        <NFormItem label="友链名称" required>
          <NInput v-model:value="form.name" placeholder="请输入友链名称" />
        </NFormItem>
        <NFormItem label="友链地址" required>
          <NInput v-model:value="form.url" placeholder="请输入友链地址" />
        </NFormItem>
        <NFormItem label="头像">
          <div class="avatar-selector">
            <ImageSelector
              v-model="form.avatar"
              :multiple="false"
              source="friend-link"
            />
            <div v-if="form.avatar" class="avatar-preview">
              <img :src="form.avatar" alt="头像预览" />
            </div>
          </div>
        </NFormItem>
        <NFormItem label="描述">
          <NInput
            v-model:value="form.description"
            type="textarea"
            placeholder="请输入描述"
            :rows="3"
          />
        </NFormItem>
        <NFormItem label="邮箱">
          <NInput v-model:value="form.email" placeholder="请输入邮箱" />
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
.avatar-selector {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.avatar-preview {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  overflow: hidden;
  border: 2px solid #e5e7eb;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.avatar-preview img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>
