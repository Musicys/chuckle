<script setup lang="ts">
import { ref, reactive, computed, watch, h } from "vue";
import {
  NButton,
  NModal,
  NForm,
  NFormItem,
  NInput,
  NDataTable,
  NSelect,
  NSpace,
  NText,
  NTag,
  useMessage,
  useDialog,
} from "naive-ui";
import {
  fetchUploadImage,
  fetchImageList,
  fetchDeleteImage,
  fetchUpdateImage,
} from "@/service/api/image";

const props = defineProps<{
  modelValue: string;
  multiple?: boolean;
  source?: string;
}>();

const emit = defineEmits<{
  "update:modelValue": [value: string];
}>();

const message = useMessage();
const dialog = useDialog();

const showModal = ref(false);
const loading = ref(false);
const uploadLoading = ref(false);

const fileInputRef = ref<HTMLInputElement | null>(null);

const data = ref<Api.Image.ImageInfo[]>([]);

const pagination = reactive({
  current: 1,
  pageSize: 12,
  total: 0,
});

const searchForm = reactive({
  originalName: "",
  source: props.source || "",
});

const selectedIds = ref<number[]>([]);

const showRemarkModal = ref(false);
const editingImage = ref<Api.Image.ImageInfo | null>(null);
const editRemark = ref("");

const sourceOptions = [
  { label: "博客", value: "blog" },
  { label: "文章", value: "article" },
  { label: "头像", value: "avatar" },
];

const columns = [
  {
    title: "图片",
    key: "url",
    width: 120,
    render(row: Api.Image.ImageInfo) {
      return isVideoType(row.fileType)
        ? h("video", {
            src: row.url,
            style: "width:100px;height:100px;object-fit:cover;border-radius:4px;background:#000",
            muted: true,
            playsinline: true,
            preload: "metadata",
          })
        : `<img src="${row.url}" style="width:100px;height:100px;object-fit:cover;border-radius:4px;" />`;
    },
  },
  {
    title: "文件名",
    key: "originalName",
    ellipsis: { tooltip: true },
  },
  {
    title: "大小",
    key: "fileSize",
    width: 100,
    render(row: Api.Image.ImageInfo) {
      return formatFileSize(row.fileSize);
    },
  },
  {
    title: "类型",
    key: "fileType",
    width: 100,
  },
  {
    title: "尺寸",
    key: "width",
    width: 100,
    render(row: Api.Image.ImageInfo) {
      return row.width ? `${row.width}×${row.height}` : "-";
    },
  },
  {
    title: "来源",
    key: "source",
    width: 80,
    render(row: Api.Image.ImageInfo) {
      return h(NTag, { type: "info" }, getSourceLabel(row.source));
    },
  },
  {
    title: "备注",
    key: "remark",
    ellipsis: { tooltip: true },
  },
  {
    title: "操作",
    key: "actions",
    width: 160,
    render(row: Api.Image.ImageInfo) {
      return h(NSpace, null, {
        default: () => [
          h(
            NButton,
            { size: "small", onClick: () => openEditRemark(row) },
            "编辑备注",
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

const selectedImages = computed(() => {
  return data.value.filter((img) => selectedIds.value.includes(img.id));
});

watch(
  () => props.modelValue,
  (val) => {
    if (!val) {
      selectedIds.value = [];
      return;
    }
    if (props.multiple) {
      try {
        const urls = JSON.parse(val);
        selectedIds.value = data.value
          .filter((img) => urls.includes(img.url))
          .map((img) => img.id);
      } catch {
        selectedIds.value = [];
      }
    } else {
      selectedIds.value = data.value
        .filter((img) => img.url === val)
        .map((img) => img.id);
    }
  },
);

function formatFileSize(bytes: number): string {
  if (bytes < 1024) return bytes + " B";
  if (bytes < 1024 * 1024) return (bytes / 1024).toFixed(1) + " KB";
  return (bytes / (1024 * 1024)).toFixed(1) + " MB";
}

function getSourceLabel(source: string): string {
  const option = sourceOptions.find((o) => o.value === source);
  return option ? option.label : source;
}

function isVideoType(fileType: string): boolean {
  return fileType.startsWith("video/");
}

function openModal() {
  showModal.value = true;
  searchForm.source = props.source || "";
  loadData();
}

function closeModal() {
  showModal.value = false;
}

async function loadData() {
  loading.value = true;
  try {
    const { data: result, error } = await fetchImageList({
      current: pagination.current,
      pageSize: pagination.pageSize,
      originalName: searchForm.originalName,
      source: searchForm.source || undefined,
    });
    if (!error && result) {
      data.value = result.records;
      pagination.total = result.total;
    }
  } catch (error) {
    message.error("加载图片失败");
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
  searchForm.originalName = "";
  searchForm.source = props.source || "";
  handleSearch();
}

function openFileInput() {
  fileInputRef.value?.click();
}

function handleFileUpload(event: Event) {
  const target = event.target as HTMLInputElement;
  const file = target.files?.[0];
  if (!file) return;

  if (!file.type.startsWith("image/") && !file.type.startsWith("video/")) {
    message.warning("请上传图片或 MP4 视频文件");
    return;
  }

  uploadImage(file);
  target.value = "";
}

async function uploadImage(file: File) {
  uploadLoading.value = true;
  try {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("source", searchForm.source || "blog");

    const { data: result, error } = await fetchUploadImage(formData);
    if (!error && result) {
      message.success("上传成功");
      loadData();
    }
  } catch (error) {
    message.error("上传失败");
  } finally {
    uploadLoading.value = false;
  }
}

function toggleSelect(image: Api.Image.ImageInfo) {
  const index = selectedIds.value.indexOf(image.id);
  if (props.multiple) {
    if (index > -1) {
      selectedIds.value.splice(index, 1);
    } else {
      selectedIds.value.push(image.id);
    }
  } else {
    selectedIds.value = [image.id];
  }
}

function isSelected(image: Api.Image.ImageInfo): boolean {
  return selectedIds.value.includes(image.id);
}

function handleConfirm() {
  if (selectedImages.value.length === 0) {
    message.warning("请选择图片");
    return;
  }

  if (props.multiple) {
    const urls = selectedImages.value.map((img) => img.url);
    emit("update:modelValue", JSON.stringify(urls));
  } else {
    emit("update:modelValue", selectedImages.value[0].url);
  }
  closeModal();
}

function openEditRemark(image: Api.Image.ImageInfo) {
  editingImage.value = image;
  editRemark.value = image.remark;
  showRemarkModal.value = true;
}

async function handleUpdateRemark() {
  if (!editingImage.value) return;

  try {
    const { error } = await fetchUpdateImage({
      id: editingImage.value.id,
      remark: editRemark.value,
    });
    if (!error) {
      message.success("更新成功");
      showRemarkModal.value = false;
      loadData();
    }
  } catch (error) {
    message.error("更新失败");
  }
}

function handleDelete(image: Api.Image.ImageInfo) {
  dialog.warning({
    title: "确认删除",
    content: `确定删除图片「${image.originalName}」？删除后不可恢复。`,
    positiveText: "删除",
    negativeText: "取消",
    onPositiveClick: async () => {
      try {
        const { error } = await fetchDeleteImage(image.id);
        if (!error) {
          message.success("删除成功");
          const idx = selectedIds.value.indexOf(image.id);
          if (idx > -1) {
            selectedIds.value.splice(idx, 1);
          }
          loadData();
        }
      } catch (error) {
        message.error("删除失败");
      }
    },
  });
}

defineExpose({
  open: openModal,
});
</script>

<template>
  <div class="image-selector">
    <NButton type="primary" ghost @click="openModal">
      {{ multiple ? "选择图片（多选）" : "选择图片" }}
    </NButton>

    <NModal
      v-model:show="showModal"
      preset="card"
      title="图床管理"
      style="width: 900px; max-height: 80vh"
    >
      <template #header-extra>
        <NSpace>
          <input
            type="file"
            accept="image/*,video/mp4,video/quicktime"
            class="hidden"
            @change="handleFileUpload"
            ref="fileInputRef"
          />
          <NButton
            type="primary"
            :loading="uploadLoading"
            @click="openFileInput"
          >
            上传图片
          </NButton>
        </NSpace>
      </template>

      <NForm :model="searchForm" inline>
        <NFormItem label="文件名">
          <NInput
            v-model:value="searchForm.originalName"
            placeholder="请输入文件名"
            @keyup.enter="handleSearch"
          />
        </NFormItem>
        <NFormItem label="来源">
          <NSelect
            v-model:value="searchForm.source"
            :options="sourceOptions"
            placeholder="全部"
          />
        </NFormItem>
        <NSpace>
          <NButton type="primary" @click="handleSearch">搜索</NButton>
          <NButton @click="handleReset">重置</NButton>
        </NSpace>
      </NForm>

      <div class="grid gap-3 mt-4 max-h-96 overflow-y-auto">
        <div
          v-for="image in data"
          :key="image.id"
          class="image-item"
          :class="{ selected: isSelected(image) }"
          @click="toggleSelect(image)"
        >
          <video
            v-if="isVideoType(image.fileType)"
            :src="image.url"
            class="image-thumb"
            muted
            playsinline
            preload="metadata"
            style="background: #000"
          ></video>
          <img
            v-else
            :src="image.url"
            :alt="image.originalName"
            class="image-thumb"
          />
          <div class="image-info">
            <NText :ellipsis="{ tooltip: image.originalName }">
              {{ image.originalName }}
            </NText>
            <span class="image-size">{{ formatFileSize(image.fileSize) }}</span>
          </div>
          <div class="select-indicator" v-if="isSelected(image)">
            <svg
              width="20"
              height="20"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="3"
              stroke-linecap="round"
              stroke-linejoin="round"
            >
              <polyline points="20 6 9 17 4 12"></polyline>
            </svg>
          </div>
        </div>
      </div>

      <div class="mt-4 flex justify-between items-center">
        <NText>已选择 {{ selectedImages.length }} 张图片</NText>
        <NSpace>
          <NButton @click="closeModal">取消</NButton>
          <NButton type="primary" @click="handleConfirm">确认选择</NButton>
        </NSpace>
      </div>
    </NModal>

    <NModal
      v-model:show="showRemarkModal"
      preset="card"
      title="编辑备注"
      style="width: 400px"
    >
      <NForm
        :model="{ remark: editRemark }"
        label-placement="left"
        :label-width="60"
      >
        <NFormItem label="备注">
          <NInput v-model:value="editRemark" placeholder="请输入备注" />
        </NFormItem>
      </NForm>
      <template #action>
        <NSpace>
          <NButton @click="showRemarkModal = false">取消</NButton>
          <NButton type="primary" @click="handleUpdateRemark">保存</NButton>
        </NSpace>
      </template>
    </NModal>
  </div>
</template>

<style scoped>
.image-selector {
  display: inline-block;
}

.grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 12px;
}

.image-item {
  position: relative;
  border: 2px solid transparent;
  border-radius: 8px;
  cursor: pointer;
  overflow: hidden;
  transition: all 0.2s ease;
}

.image-item:hover {
  border-color: #409eff;
}

.image-item.selected {
  border-color: #409eff;
  background-color: rgba(64, 158, 255, 0.1);
}

.image-thumb {
  width: 100%;
  height: 120px;
  object-fit: cover;
}

.image-info {
  padding: 8px;
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.image-info :deep(.n-text) {
  font-size: 12px;
  color: #666;
}

.image-size {
  font-size: 11px;
  color: #999;
}

.select-indicator {
  position: absolute;
  top: 8px;
  right: 8px;
  width: 24px;
  height: 24px;
  background: #409eff;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.mt-4 {
  margin-top: 16px;
}

.max-h-96 {
  max-height: 24rem;
}

.overflow-y-auto {
  overflow-y: auto;
}

.flex {
  display: flex;
}

.justify-between {
  justify-content: space-between;
}

.items-center {
  align-items: center;
}
</style>
