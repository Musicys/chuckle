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
  NText,
  NModal,
  NTag,
  useMessage,
} from "naive-ui";
import ImageSelector from "@/components/common/image-selector.vue";
import {
  fetchAddBlogger,
  fetchUpdateBlogger,
  fetchGetBlogger,
} from "@/service/api/blogger";

interface GameItem {
  icon: string;
  title: string;
  value: string;
}

const route = useRoute();
const router = useRouter();
const message = useMessage();

const bloggerId = route.params.id as string;
const isEdit = !!bloggerId;

const loading = ref(false);
const submitLoading = ref(false);

const form = reactive({
  avatar: "",
  nickname: "",
  blogTitle: "",
  qq: "",
  github: "",
  gitee: "",
  bilibili: "",
  birthDate: "",
  gender: "1",
  occupation: "",
  tags: [] as string[],
  hobbies: [] as string[],
  games: [] as GameItem[],
  latitude: "",
  longitude: "",
  address: "",
  announcement: "",
  config: '{"theme":"default","layout":"blog","commentEnabled":true}',
});

const genderOptions = [
  { label: "1", value: "1" },
  { label: "2", value: "2" },
];

const showTagModal = ref(false);
const showHobbyModal = ref(false);
const showGameModal = ref(false);

const newTag = ref("");
const newHobby = ref("");
const newGame = reactive<GameItem>({
  icon: "",
  title: "",
  value: "",
});

const tagsJson = computed(() => JSON.stringify(form.tags));
const hobbiesJson = computed(() => JSON.stringify(form.hobbies));
const gamesJson = computed(() => JSON.stringify(form.games));

async function loadBlogger() {
  if (!isEdit) return;

  loading.value = true;
  try {
    const { data: blogger, error } = await fetchGetBlogger(Number(bloggerId));
    if (!error && blogger) {
      form.avatar = blogger.avatar;
      form.nickname = blogger.nickname;
      form.blogTitle = blogger.blogTitle;
      form.qq = blogger.qq;
      form.github = blogger.github;
      form.gitee = blogger.gitee;
      form.bilibili = blogger.bilibili;
      form.birthDate = blogger.birthDate;
      form.gender = blogger.gender === 0 ? "2" : "1";
      form.occupation = blogger.occupation;
      try {
        form.tags = blogger.tags ? JSON.parse(blogger.tags) : [];
      } catch {
        form.tags = [];
      }
      try {
        form.hobbies = blogger.hobbies ? JSON.parse(blogger.hobbies) : [];
      } catch {
        form.hobbies = [];
      }
      try {
        form.games = blogger.games ? JSON.parse(blogger.games) : [];
      } catch {
        form.games = [];
      }
      form.latitude = String(blogger.latitude || "");
      form.longitude = String(blogger.longitude || "");
      form.address = blogger.address;
      form.announcement = blogger.announcement;
      form.config = blogger.config;
    }
  } catch (error) {
    message.error("获取博主信息失败");
  } finally {
    loading.value = false;
  }
}

function addTag() {
  if (!newTag.value.trim()) {
    message.warning("请输入标签内容");
    return;
  }
  if (form.tags.includes(newTag.value.trim())) {
    message.warning("标签已存在");
    return;
  }
  form.tags.push(newTag.value.trim());
  newTag.value = "";
  showTagModal.value = false;
}

function removeTag(index: number) {
  form.tags.splice(index, 1);
}

function addHobby() {
  if (!newHobby.value.trim()) {
    message.warning("请输入爱好内容");
    return;
  }
  if (form.hobbies.includes(newHobby.value.trim())) {
    message.warning("爱好已存在");
    return;
  }
  form.hobbies.push(newHobby.value.trim());
  newHobby.value = "";
  showHobbyModal.value = false;
}

function removeHobby(index: number) {
  form.hobbies.splice(index, 1);
}

function addGame() {
  if (!newGame.title.trim()) {
    message.warning("请输入游戏标题");
    return;
  }
  form.games.push({ ...newGame });
  newGame.icon = "";
  newGame.title = "";
  newGame.value = "";
  showGameModal.value = false;
}

function removeGame(index: number) {
  form.games.splice(index, 1);
}

async function handleSubmit() {
  if (!form.nickname.trim()) {
    message.warning("请输入昵称");
    return;
  }

  submitLoading.value = true;
  try {
    const submitData = {
      avatar: form.avatar,
      nickname: form.nickname,
      blogTitle: form.blogTitle,
      qq: form.qq,
      github: form.github,
      gitee: form.gitee,
      bilibili: form.bilibili,
      birthDate: form.birthDate,
      gender: form.gender === "2" ? 0 : 1,
      occupation: form.occupation,
      tags: tagsJson.value,
      hobbies: hobbiesJson.value,
      games: gamesJson.value,
      latitude: form.latitude ? Number(form.latitude) : undefined,
      longitude: form.longitude ? Number(form.longitude) : undefined,
      address: form.address,
      announcement: form.announcement,
      config: form.config,
    };

    if (isEdit) {
      const { error } = await fetchUpdateBlogger({
        id: Number(bloggerId),
        ...submitData,
      });
      if (!error) {
        message.success("更新成功");
      }
    } else {
      const { error } = await fetchAddBlogger(submitData);
      if (!error) {
        message.success("新增成功");
      }
    }
    router.push("/blogger");
  } catch (error) {
    message.error("操作失败");
  } finally {
    submitLoading.value = false;
  }
}

function handleBack() {
  router.push("/blogger");
}

onMounted(() => {
  loadBlogger();
});
</script>

<template>
  <div class="blogger-edit-page">
    <NCard :bordered="false" class="blogger-card">
      <template #header>
        <div class="header-content">
          <NText strong class="page-title">
            {{ isEdit ? "编辑博主信息" : "新增博主信息" }}
          </NText>
          <NSpace class="header-actions">
            <NButton @click="handleBack" class="back-btn">返回</NButton>
            <NButton
              type="primary"
              :loading="submitLoading"
              @click="handleSubmit"
              class="submit-btn"
            >
              {{ isEdit ? "保存修改" : "创建" }}
            </NButton>
          </NSpace>
        </div>
      </template>

      <div class="form-container">
        <div class="basic-section">
          <div class="section-title">基本信息</div>
          <NForm
            :model="form"
            label-placement="left"
            :label-width="100"
            :disabled="loading"
            class="form-grid"
          >
            <NFormItem label="头像" class="avatar-item">
              <div class="avatar-selector">
                <ImageSelector
                  v-model="form.avatar"
                  :multiple="false"
                  source="avatar"
                />
                <div v-if="form.avatar" class="avatar-preview">
                  <img :src="form.avatar" alt="头像预览" />
                </div>
              </div>
            </NFormItem>

            <NFormItem label="昵称" required>
              <NInput
                v-model:value="form.nickname"
                placeholder="请输入昵称"
                class="full-width"
              />
            </NFormItem>

            <NFormItem label="博客标题">
              <NInput
                v-model:value="form.blogTitle"
                placeholder="请输入博客标题"
                class="full-width"
              />
            </NFormItem>

            <NFormItem label="性别">
              <NSelect
                v-model:value="form.gender"
                :options="genderOptions"
                placeholder="请选择性别"
                class="gender-select"
              />
            </NFormItem>

            <NFormItem label="出生日期">
              <NInput
                v-model:value="form.birthDate"
                placeholder="请输入出生日期，如 1998-06-15"
                class="full-width"
              />
            </NFormItem>

            <NFormItem label="职业">
              <NInput
                v-model:value="form.occupation"
                placeholder="请输入职业"
                class="full-width"
              />
            </NFormItem>

            <NFormItem label="地址">
              <NInput
                v-model:value="form.address"
                placeholder="请输入地址"
                class="full-width"
              />
            </NFormItem>

            <NFormItem label="纬度">
              <NInput
                v-model:value="form.latitude"
                placeholder="请输入纬度"
                class="full-width"
              />
            </NFormItem>

            <NFormItem label="经度">
              <NInput
                v-model:value="form.longitude"
                placeholder="请输入经度"
                class="full-width"
              />
            </NFormItem>
          </NForm>
        </div>

        <div class="social-section">
          <div class="section-title">社交链接</div>
          <NForm
            :model="form"
            label-placement="left"
            :label-width="100"
            :disabled="loading"
            class="form-grid"
          >
            <NFormItem label="QQ">
              <NInput
                v-model:value="form.qq"
                placeholder="请输入QQ邮箱"
                class="full-width"
              />
            </NFormItem>

            <NFormItem label="GitHub">
              <NInput
                v-model:value="form.github"
                placeholder="请输入GitHub地址"
                class="full-width"
              />
            </NFormItem>

            <NFormItem label="Gitee">
              <NInput
                v-model:value="form.gitee"
                placeholder="请输入Gitee地址"
                class="full-width"
              />
            </NFormItem>

            <NFormItem label="Bilibili">
              <NInput
                v-model:value="form.bilibili"
                placeholder="请输入B站地址"
                class="full-width"
              />
            </NFormItem>
          </NForm>
        </div>

        <div class="detail-section">
          <div class="section-title">详细信息</div>

          <div class="list-field">
            <div class="field-label">标签</div>
            <div class="field-content">
              <div class="tags-list">
                <NTag
                  v-for="(tag, index) in form.tags"
                  :key="index"
                  closable
                  @close="removeTag(index)"
                  class="tag-item"
                >
                  {{ tag }}
                </NTag>
                <NButton
                  v-if="form.tags.length < 10"
                  dashed
                  size="small"
                  @click="showTagModal = true"
                  class="add-btn"
                >
                  + 添加标签
                </NButton>
                <span v-else class="limit-text">最多添加10个标签</span>
              </div>
            </div>
          </div>

          <div class="list-field">
            <div class="field-label">爱好</div>
            <div class="field-content">
              <div class="tags-list">
                <NTag
                  v-for="(hobby, index) in form.hobbies"
                  :key="index"
                  closable
                  @close="removeHobby(index)"
                  class="tag-item"
                  type="success"
                >
                  {{ hobby }}
                </NTag>
                <NButton
                  v-if="form.hobbies.length < 10"
                  dashed
                  size="small"
                  @click="showHobbyModal = true"
                  class="add-btn"
                >
                  + 添加爱好
                </NButton>
                <span v-else class="limit-text">最多添加10个爱好</span>
              </div>
            </div>
          </div>

          <div class="list-field">
            <div class="field-label">游戏</div>
            <div class="field-content">
              <div class="games-list">
                <div
                  v-for="(game, index) in form.games"
                  :key="index"
                  class="game-card"
                >
                  <div class="game-icon">
                    <img
                      v-if="game.icon"
                      :src="game.icon"
                      alt="游戏图标"
                      class="icon-img"
                    />
                    <span v-else class="icon-placeholder">🎮</span>
                  </div>
                  <div class="game-info">
                    <div class="game-title">{{ game.title }}</div>
                    <div class="game-value">{{ game.value }}</div>
                  </div>
                  <NButton
                    size="small"
                    type="error"
                    @click="removeGame(index)"
                    class="remove-btn"
                  >
                    删除
                  </NButton>
                </div>
                <NButton
                  v-if="form.games.length < 10"
                  dashed
                  size="small"
                  @click="showGameModal = true"
                  class="add-btn"
                >
                  + 添加游戏
                </NButton>
                <span v-else class="limit-text">最多添加10个游戏</span>
              </div>
            </div>
          </div>

          <div class="list-field">
            <div class="field-label">公告</div>
            <div class="field-content">
              <NInput
                v-model:value="form.announcement"
                placeholder="请输入站点公告"
                class="full-width"
              />
            </div>
          </div>

          <div class="list-field">
            <div class="field-label">配置</div>
            <div class="field-content">
              <NInput
                v-model:value="form.config"
                placeholder='请输入配置JSON，如 {"theme":"default","layout":"blog"}'
                class="full-width"
              />
            </div>
          </div>
        </div>
      </div>
    </NCard>

    <NModal
      v-model:show="showTagModal"
      preset="card"
      title="添加标签"
      style="width: 400px"
    >
      <NForm :model="{ tag: newTag }" label-placement="left" :label-width="60">
        <NFormItem label="标签">
          <NInput
            v-model:value="newTag"
            placeholder="请输入标签名称"
            @keyup.enter="addTag"
          />
        </NFormItem>
      </NForm>
      <template #action>
        <NSpace>
          <NButton @click="showTagModal = false">取消</NButton>
          <NButton type="primary" @click="addTag">确定</NButton>
        </NSpace>
      </template>
    </NModal>

    <NModal
      v-model:show="showHobbyModal"
      preset="card"
      title="添加爱好"
      style="width: 400px"
    >
      <NForm
        :model="{ hobby: newHobby }"
        label-placement="left"
        :label-width="60"
      >
        <NFormItem label="爱好">
          <NInput
            v-model:value="newHobby"
            placeholder="请输入爱好名称"
            @keyup.enter="addHobby"
          />
        </NFormItem>
      </NForm>
      <template #action>
        <NSpace>
          <NButton @click="showHobbyModal = false">取消</NButton>
          <NButton type="primary" @click="addHobby">确定</NButton>
        </NSpace>
      </template>
    </NModal>

    <NModal
      v-model:show="showGameModal"
      preset="card"
      title="添加游戏"
      style="width: 450px"
    >
      <NForm
        :model="newGame"
        label-placement="left"
        :label-width="60"
        class="game-form"
      >
        <NFormItem label="图标">
          <div class="game-icon-selector">
            <ImageSelector
              v-model="newGame.icon"
              :multiple="false"
              source="game"
            />
            <div v-if="newGame.icon" class="game-icon-preview">
              <img :src="newGame.icon" alt="游戏图标预览" />
            </div>
          </div>
        </NFormItem>
        <NFormItem label="标题" required>
          <NInput v-model:value="newGame.title" placeholder="请输入游戏标题" />
        </NFormItem>
        <NFormItem label="值">
          <NInput
            v-model:value="newGame.value"
            placeholder="请输入游戏值（如等级、分数等）"
          />
        </NFormItem>
      </NForm>
      <template #action>
        <NSpace>
          <NButton @click="showGameModal = false">取消</NButton>
          <NButton type="primary" @click="addGame">确定</NButton>
        </NSpace>
      </template>
    </NModal>
  </div>
</template>

<style scoped>
.blogger-edit-page {
  padding: 20px;
  min-height: 100vh;
  background: #f5f7fa;
}

.blogger-card {
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.page-title {
  font-size: 18px;
  font-weight: 600;
  color: #1f2937;
}

.header-actions {
  display: flex;
  gap: 12px;
}

.back-btn {
  padding: 8px 16px;
  font-size: 14px;
}

.submit-btn {
  padding: 8px 24px;
  font-size: 14px;
}

.form-container {
  padding-top: 20px;
}

.basic-section,
.social-section,
.detail-section {
  margin-bottom: 30px;
}

.section-title {
  font-size: 16px;
  font-weight: 600;
  color: #374151;
  margin-bottom: 16px;
  padding-bottom: 8px;
  border-bottom: 2px solid #e5e7eb;
  position: relative;
}

.section-title::after {
  content: "";
  position: absolute;
  bottom: -2px;
  left: 0;
  width: 60px;
  height: 2px;
  background: #409eff;
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
}

.form-full {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.avatar-item {
  grid-column: span 1;
}

.avatar-selector {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.avatar-preview {
  width: 120px;
  height: 120px;
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

.full-width {
  width: 100%;
}

.gender-select {
  width: 120px;
}

.list-field {
  display: flex;
  margin-bottom: 20px;
}

.field-label {
  width: 100px;
  font-weight: 500;
  color: #4b5563;
  line-height: 36px;
  flex-shrink: 0;
}

.field-content {
  flex: 1;
  min-width: 0;
}

.tags-list {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  align-items: center;
}

.tag-item {
  padding: 4px 12px;
}

.add-btn {
  padding: 4px 12px;
}

.limit-text {
  font-size: 12px;
  color: #9ca3af;
}

.games-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.game-card {
  display: flex;
  align-items: center;
  padding: 12px;
  background: #f9fafb;
  border-radius: 8px;
  border: 1px solid #e5e7eb;
  gap: 12px;
}

.game-icon {
  width: 48px;
  height: 48px;
  border-radius: 8px;
  overflow: hidden;
  flex-shrink: 0;
}

.icon-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.icon-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
  background: #e5e7eb;
}

.game-info {
  flex: 1;
  min-width: 0;
}

.game-title {
  font-weight: 500;
  color: #1f2937;
  font-size: 14px;
}

.game-value {
  font-size: 12px;
  color: #6b7280;
  margin-top: 4px;
}

.remove-btn {
  flex-shrink: 0;
}

.game-form {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.game-icon-selector {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.game-icon-preview {
  width: 80px;
  height: 80px;
  border-radius: 8px;
  overflow: hidden;
  border: 2px solid #e5e7eb;
}

.game-icon-preview img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

:deep(.n-form-item-label) {
  font-weight: 500;
  color: #4b5563;
}

:deep(.n-input__input) {
  border-radius: 6px;
  transition: all 0.2s ease;
}

:deep(.n-input__input:hover) {
  border-color: #409eff;
}

:deep(.n-select) {
  border-radius: 6px;
}

:deep(.n-button) {
  border-radius: 6px;
}

:deep(.n-tag) {
  border-radius: 4px;
}
</style>
