<script setup lang="ts">
import { ref, reactive, computed, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import {
  NButton,
  NCard,
  NForm,
  NFormItem,
  NInput,
  NSelect,
  NSpace,
  NTag,
  NText,
  NModal,
  useMessage,
} from "naive-ui";
import { Icon } from "@iconify/vue";
import { MdEditor } from "md-editor-v3";
import "md-editor-v3/lib/style.css";
import {
  fetchAddArticle,
  fetchUpdateArticle,
  fetchGetArticle,
} from "@/service/api/article";
import { fetchTagList, fetchAddTag } from "@/service/api/tag";
import { fetchCategoryList } from "@/service/api/category";
import ImageSelector from "@/components/common/image-selector.vue";

const route = useRoute();
const router = useRouter();
const message = useMessage();

const articleId = route.params.id as string;
const isEdit = !!articleId;

const loading = ref(false);
const submitLoading = ref(false);

const form = reactive({
  title: "",
  content: "",
  summary: "",
  cover: "",
  categoryId: "1",
  status: 1 as 0 | 1 | 2,
  tagIds: [] as number[],
});

const allTags = ref<Api.Tag.TagInfo[]>([]);
const selectedTags = ref<Api.Tag.TagInfo[]>([]);

const allCategories = ref<Api.Category.CategoryInfo[]>([]);

const showAddTagModal = ref(false);
const newTagName = ref("");
const newTagColor = ref("#409EFF");

const fileInputRef = ref<HTMLInputElement | null>(null);

const statusOptions = [
  { label: "草稿", value: 0 },
  { label: "已发布", value: 1 },
  { label: "下架", value: 2 },
];

const categoryOptions = computed(() =>
  allCategories.value.map((cat) => ({
    label: cat.name,
    value: String(cat.id),
  })),
);

async function loadTags() {
  try {
    const { data: result, error } = await fetchTagList({ pageSize: 100 });
    if (!error && result) {
      allTags.value = result.records;
    }
  } catch (error) {
    message.error("加载标签失败");
  }
}

async function loadCategories() {
  try {
    const { data: result, error } = await fetchCategoryList({ pageSize: 100 });
    if (!error && result) {
      allCategories.value = result.records;
    }
  } catch (error) {
    message.error("加载分类失败");
  }
}

async function loadArticle() {
  if (!isEdit) return;

  loading.value = true;
  try {
    const { data: article, error } = await fetchGetArticle(Number(articleId));
    if (!error && article) {
      form.title = article.title;
      form.content = article.content;
      form.summary = article.summary;
      form.cover = article.cover || "";
      form.categoryId = String(article.categoryId);
      form.status = article.status as 0 | 1 | 2;
      form.tagIds = article.tags.map((tag) => tag.id);
      selectedTags.value = article.tags;
    }
  } catch (error) {
    message.error("获取文章失败");
  } finally {
    loading.value = false;
  }
}

function toggleTag(tag: Api.Tag.TagInfo) {
  const index = selectedTags.value.findIndex((t) => t.id === tag.id);
  if (index > -1) {
    selectedTags.value.splice(index, 1);
    form.tagIds.splice(form.tagIds.indexOf(tag.id), 1);
  } else {
    selectedTags.value.push(tag);
    form.tagIds.push(tag.id);
  }
}

function openAddTagModal() {
  newTagName.value = "";
  newTagColor.value = "#409EFF";
  showAddTagModal.value = true;
}

async function handleAddTag() {
  if (!newTagName.value.trim()) {
    message.warning("请输入标签名称");
    return;
  }

  try {
    const { data: tagId, error } = await fetchAddTag({
      name: newTagName.value.trim(),
      color: newTagColor.value,
    });
    if (!error && tagId) {
      message.success("标签创建成功");
      showAddTagModal.value = false;
      await loadTags();
      const newTag = allTags.value.find((t) => t.id === tagId);
      if (newTag) {
        toggleTag(newTag);
      }
    }
  } catch (error) {
    message.error("创建标签失败");
  }
}

async function handleSubmit() {
  if (!form.title.trim()) {
    message.warning("请输入标题");
    return;
  }
  if (!form.content.trim()) {
    message.warning("请输入内容");
    return;
  }

  submitLoading.value = true;
  try {
    const submitData = {
      ...form,
      categoryId: Number(form.categoryId) || 1,
      tagIds: form.tagIds,
      cover: form.cover || undefined,
    };

    if (isEdit) {
      const { error } = await fetchUpdateArticle({
        ...submitData,
        id: Number(articleId),
      });
      if (!error) {
        message.success("更新成功");
      }
    } else {
      const { error } = await fetchAddArticle(submitData);
      if (!error) {
        message.success("新增成功");
      }
    }
    router.push("/article");
  } catch (error) {
    message.error("操作失败");
  } finally {
    submitLoading.value = false;
  }
}

function handleBack() {
  router.push("/article");
}

function openFileInput() {
  fileInputRef.value?.click();
}

async function handleFileUpload(event: Event) {
  const target = event.target as HTMLInputElement;
  const file = target.files?.[0];

  if (!file) return;

  if (!file.name.toLowerCase().endsWith(".md")) {
    message.warning("请上传 Markdown 文件（.md）");
    return;
  }

  try {
    const text = await readFileAsText(file);
    parseMarkdownFile(text);
    message.success("文件解析成功");
  } catch (error) {
    message.error("文件解析失败");
  } finally {
    target.value = "";
  }
}

function readFileAsText(file: File): Promise<string> {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = (e) => resolve(e.target?.result as string);
    reader.onerror = reject;
    reader.readAsText(file, "utf-8");
  });
}

function parseMarkdownFile(content: string) {
  const lines = content.split("\n");
  let title = "";
  let summary = "";
  let body = "";

  let inFrontmatter = false;
  const frontmatterData: Record<string, string> = {};

  for (let i = 0; i < lines.length; i++) {
    const line = lines[i];

    if (line.trim() === "---") {
      inFrontmatter = !inFrontmatter;
      continue;
    }

    if (inFrontmatter) {
      const match = line.match(/^(\w+):\s*(.+)$/);
      if (match) {
        frontmatterData[match[1].toLowerCase()] = match[2].trim();
      }
      continue;
    }

    if (!title && line.startsWith("# ")) {
      title = line.slice(2).trim();
      continue;
    }

    if (!title && line.startsWith("## ")) {
      title = line.slice(3).trim();
      continue;
    }

    if (!title && line.startsWith("### ")) {
      title = line.slice(4).trim();
      continue;
    }

    body += line + "\n";
  }

  if (frontmatterData.title) {
    title = frontmatterData.title;
  }

  if (frontmatterData.summary || frontmatterData.description) {
    summary = frontmatterData.summary || frontmatterData.description;
  } else {
    const firstParagraph = body.trim().split("\n\n")[0];
    if (firstParagraph && firstParagraph.length > 0) {
      summary = firstParagraph.replace(/[#*`_~\[\]]/g, "").substring(0, 200);
    }
  }

  if (title) {
    form.title = title;
  }
  if (summary) {
    form.summary = summary;
  }
  form.content = content;
}

onMounted(() => {
  loadTags();
  loadCategories();
  loadArticle();
});
</script>

<template>
  <div>
    <NCard :bordered="false">
      <template #header>
        <NSpace justify="space-between" align="center">
          <NText strong>{{ isEdit ? "编辑文章" : "新增文章" }}</NText>
          <NSpace>
            <NButton @click="handleBack">返回</NButton>
            <NButton
              type="primary"
              :loading="submitLoading"
              @click="handleSubmit"
            >
              {{ isEdit ? "保存修改" : "发布文章" }}
            </NButton>
          </NSpace>
        </NSpace>
      </template>

      <NForm
        :model="form"
        label-placement="left"
        :label-width="80"
        :disabled="loading"
      >
        <NFormItem label="标题" required>
          <NSpace align="center" class="w-full">
            <NInput
              v-model:value="form.title"
              placeholder="请输入文章标题"
              class="flex-1"
            />
            <input
              ref="fileInputRef"
              type="file"
              accept=".md"
              class="hidden"
              @change="handleFileUpload"
            />
            <NButton type="primary" ghost size="small" @click="openFileInput">
              <template #icon>
                <Icon icon="material-symbols:upload" :size="16" />
              </template>
              上传 MD 文件
            </NButton>
          </NSpace>
        </NFormItem>

        <NFormItem label="分类" required>
          <NSelect
            v-model:value="form.categoryId"
            :options="categoryOptions"
            placeholder="请选择分类"
          />
        </NFormItem>

        <NFormItem label="封面">
          <div class="cover-selector">
            <ImageSelector
              v-model="form.cover"
              :multiple="false"
              source="cover"
            />
            <div v-if="form.cover" class="cover-preview">
              <img :src="form.cover" alt="封面预览" />
            </div>
          </div>
        </NFormItem>

        <NFormItem label="状态">
          <NSelect v-model:value="form.status" :options="statusOptions" />
        </NFormItem>

        <NFormItem label="标签">
          <NSpace wrap>
            <NTag
              v-for="tag in allTags"
              :key="tag.id"
              :type="
                selectedTags.some((t) => t.id === tag.id)
                  ? 'primary'
                  : 'default'
              "
              :style="{
                backgroundColor: selectedTags.some((t) => t.id === tag.id)
                  ? tag.color
                  : undefined,
              }"
              closable
              @click="toggleTag(tag)"
              @close.prevent="toggleTag(tag)"
            >
              {{ tag.name }}
            </NTag>
            <NButton text size="small" @click="openAddTagModal"
              >+ 添加标签</NButton
            >
          </NSpace>
        </NFormItem>

        <NFormItem label="简介">
          <NInput v-model:value="form.summary" placeholder="请输入文章简介" />
        </NFormItem>

        <NFormItem label="内容" required>
          <MdEditor v-model="form.content" height="600px" />
        </NFormItem>
      </NForm>
    </NCard>

    <NModal
      v-model:show="showAddTagModal"
      preset="card"
      title="新建标签"
      style="width: 450px"
    >
      <NForm
        :model="{ name: newTagName, color: newTagColor }"
        label-placement="left"
        :label-width="80"
      >
        <NFormItem label="标签名称" required>
          <NInput v-model:value="newTagName" placeholder="请输入标签名称" />
        </NFormItem>
        <NFormItem label="标签颜色">
          <NInput
            v-model:value="newTagColor"
            placeholder="请输入颜色值，如 #409EFF"
          />
        </NFormItem>
        <NFormItem label="预览">
          <NTag :style="{ backgroundColor: newTagColor, color: '#fff' }">{{
            newTagName || "标签预览"
          }}</NTag>
        </NFormItem>
      </NForm>
      <template #action>
        <NSpace>
          <NButton @click="showAddTagModal = false">取消</NButton>
          <NButton type="primary" @click="handleAddTag">创建</NButton>
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
