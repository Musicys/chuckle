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
  NTag,
  NText,
  useMessage,
} from "naive-ui";
import {
  fetchCommentList,
  fetchReviewComment,
  fetchReplyComment,
  fetchDeleteComment,
  fetchReplyCommentByEmail,
} from "@/service/api/comment";

const message = useMessage();

const data = ref<Api.Comment.CommentInfo[]>([]);
const loading = ref(false);
const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
});

const searchForm = reactive({
  articleId: "",
  status: "" as string,
  nickname: "",
});

const showReplyModal = ref(false);
const replyLoading = ref(false);
const replyForm = reactive<Api.Comment.ReplyCommentParams>({
  articleId: 0,
  replyToCommentId: 0,
  content: "",
});

const showEmailReplyModal = ref(false);
const emailReplyLoading = ref(false);
const emailReplyForm = reactive<Api.Comment.EmailReplyParams>({
  commentId: 0,
  toEmail: "",
  subject: "",
  content: "",
});

function openEmailReplyModal(row: Api.Comment.CommentInfo) {
  emailReplyForm.commentId = row.id;
  emailReplyForm.toEmail = row.email;
  emailReplyForm.subject = `回复：关于您在「${row.articleTitle}」的评论`;
  emailReplyForm.content = "";
  showEmailReplyModal.value = true;
}

async function handleSendEmailReply() {
  if (!emailReplyForm.toEmail) {
    message.warning("该评论者没有邮箱地址");
    return;
  }
  if (!emailReplyForm.subject.trim()) {
    message.warning("请输入邮件主题");
    return;
  }
  if (!emailReplyForm.content.trim()) {
    message.warning("请输入回复内容");
    return;
  }

  emailReplyLoading.value = true;
  try {
    const { error } = await fetchReplyCommentByEmail({
      commentId: emailReplyForm.commentId,
      toEmail: emailReplyForm.toEmail,
      subject: emailReplyForm.subject,
      content: emailReplyForm.content,
    });
    if (!error) {
      message.success("邮件回复成功");
    }
    showEmailReplyModal.value = false;
  } catch (error) {
    message.error("邮件发送失败");
  } finally {
    emailReplyLoading.value = false;
  }
}

const statusOptions = [
  { label: "待审核", value: 0 },
  { label: "已通过", value: 1 },
  { label: "垃圾评论", value: 2 },
];

function getStatusLabel(status: number) {
  const option = statusOptions.find((item) => item.value === status);
  return option?.label || "未知";
}

function getStatusType(status: number) {
  switch (status) {
    case 0:
      return "warning";
    case 1:
      return "success";
    case 2:
      return "error";
    default:
      return "default";
  }
}

async function loadData() {
  loading.value = true;
  try {
    const { data: result, error } = await fetchCommentList({
      current: pagination.current,
      pageSize: pagination.pageSize,
      articleId: searchForm.articleId
        ? Number(searchForm.articleId)
        : undefined,
      status: searchForm.status ? Number(searchForm.status) : undefined,
      nickname: searchForm.nickname,
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
  searchForm.articleId = "";
  searchForm.status = "";
  searchForm.nickname = "";
  handleSearch();
}

async function handleReview(row: Api.Comment.CommentInfo, status: number) {
  try {
    const { error } = await fetchReviewComment(row.id, status);
    if (!error) {
      message.success("审核成功");
    }
    loadData();
  } catch (error) {
    message.error("审核失败");
  }
}

function openReplyModal(row: Api.Comment.CommentInfo) {
  replyForm.articleId = row.articleId;
  replyForm.replyToCommentId = row.id;
  replyForm.content = "";
  showReplyModal.value = true;
}

async function handleReply() {
  if (!replyForm.content.trim()) {
    message.warning("请输入回复内容");
    return;
  }

  replyLoading.value = true;
  try {
    const { error } = await fetchReplyComment(replyForm);
    if (!error) {
      message.success("回复成功");
    }
    showReplyModal.value = false;
    loadData();
  } catch (error) {
    message.error("回复失败");
  } finally {
    replyLoading.value = false;
  }
}

async function handleDelete(row: Api.Comment.CommentInfo) {
  try {
    const { error } = await fetchDeleteComment(row.id);
    if (!error) {
      message.success("删除成功");
    }
    loadData();
  } catch (error) {
    message.error("删除失败");
  }
}

const columns = [
  { title: "文章标题", key: "articleTitle", ellipsis: true },
  { title: "评论者", key: "nickname" },
  { title: "邮箱", key: "email" },
  { title: "内容", key: "content", ellipsis: true },
  {
    title: "状态",
    key: "status",
    render(row: Api.Comment.CommentInfo) {
      return h(
        NTag,
        { type: getStatusType(row.status) },
        getStatusLabel(row.status),
      );
    },
  },
  { title: "IP地址", key: "ipAddress" },
  { title: "创建时间", key: "createdAt" },
  {
    title: "操作",
    key: "actions",
    render(row: Api.Comment.CommentInfo) {
      const children: ReturnType<typeof h>[] = [];
      if (row.status === 0) {
        children.push(
          h(
            NButton,
            {
              size: "small",
              type: "success",
              onClick: () => handleReview(row, 1),
            },
            "通过",
          ),
          h(
            NButton,
            {
              size: "small",
              type: "error",
              onClick: () => handleReview(row, 2),
            },
            "拒绝",
          ),
        );
      }
      children.push(
        h(
          NButton,
          { size: "small", onClick: () => openReplyModal(row) },
          "回复",
        ),
        h(
          NButton,
          {
            size: "small",
            type: "primary",
            ghost: true,
            onClick: () => openEmailReplyModal(row),
          },
          "邮件回复",
        ),
        h(
          NButton,
          { size: "small", type: "error", onClick: () => handleDelete(row) },
          "删除",
        ),
      );
      return h(NSpace, null, { default: () => children });
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
          <NText strong>评论管理</NText>
        </NSpace>
      </template>

      <NForm :model="searchForm" inline>
        <NFormItem label="文章ID">
          <NInput
            v-model:value="searchForm.articleId"
            :placeholder="'请输入文章ID'"
            style="width: 150px"
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
        <NFormItem label="昵称">
          <NInput
            v-model:value="searchForm.nickname"
            placeholder="请输入昵称"
            style="width: 150px"
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
      v-model:show="showReplyModal"
      preset="card"
      title="回复评论"
      style="width: 600px"
    >
      <NForm :model="replyForm" label-placement="left" :label-width="80">
        <NFormItem label="回复内容" required>
          <NInput
            v-model:value="replyForm.content"
            placeholder="请输入回复内容"
          />
        </NFormItem>
      </NForm>
      <template #action>
        <NSpace>
          <NButton @click="showReplyModal = false">取消</NButton>
          <NButton type="primary" :loading="replyLoading" @click="handleReply">
            回复
          </NButton>
        </NSpace>
      </template>
    </NModal>

    <NModal
      v-model:show="showEmailReplyModal"
      preset="card"
      title="邮件回复"
      style="width: 600px"
    >
      <NForm
        :model="emailReplyForm"
        label-placement="left"
        :label-width="80"
      >
        <NFormItem label="收件人">
          <NInput :value="emailReplyForm.toEmail" disabled />
        </NFormItem>
        <NFormItem label="主题" required>
          <NInput v-model:value="emailReplyForm.subject" placeholder="请输入邮件主题" />
        </NFormItem>
        <NFormItem label="内容" required>
          <NInput
            v-model:value="emailReplyForm.content"
            type="textarea"
            :rows="5"
            placeholder="请输入回复内容"
          />
        </NFormItem>
      </NForm>
      <template #action>
        <NSpace>
          <NButton @click="showEmailReplyModal = false">取消</NButton>
          <NButton
            type="primary"
            :loading="emailReplyLoading"
            @click="handleSendEmailReply"
          >
            发送邮件
          </NButton>
        </NSpace>
      </template>
    </NModal>
  </div>
</template>
