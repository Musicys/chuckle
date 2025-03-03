<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['ry:blog:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['ry:blog:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['ry:blog:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['ry:blog:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="blogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="标题" align="center" prop="title" />
      <el-table-column label="分类" align="center" prop="types">
        <template slot-scope="scope">
          <el-tag size="mini" v-for="tag in scope.row.types" :key="tag.typeId" type="info">{{tag.typeName}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="标签" align="center" prop="tags">
        <template slot-scope="scope">
          <el-tag effect="plain" size="mini" v-for="tag in scope.row.tags" :key="tag.tagId" type="success">
            {{tag.tagName}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="阅读" align="center" prop="views" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="首页图片" align="center" prop="blogPic" >
        <template slot-scope="scope">
          <el-image
            style="width: 120px;height: 60px;"
            :src="scope.row.blogPic"
            lazy
            :preview-src-list="[scope.row.blogPic]">
          </el-image>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['ry:blog:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['ry:blog:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改博客信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="内容">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>

        <el-form-item label="首页图片" prop="blogPic">
          <el-radio-group v-model="form.blogPicType">
            <el-radio-button label="0">地址</el-radio-button>
            <el-radio-button label="1">上传</el-radio-button>
          </el-radio-group>
          <div v-show="form.blogPicType == '0'" class="tabBlock">
            <el-input v-model="form.blogPicLink" placeholder="请输入图片地址 https://" style="margin-bottom: 10px;" />
            <el-image :src="form.blogPicLink" :preview-src-list="[form.blogPicLink]" fit="cover" class="blogPic" >
              <div slot="error" class="image-slot">
                <el-image src="/errorImg.jpg" fit="cover" class="blogPic"></el-image>
              </div>
            </el-image>
          </div>
          <div v-show="form.blogPicType == '1'" class="tabBlock">
            <imageUpload v-model="form.blogPic"/>
          </div>
        </el-form-item>

        <el-form-item label="简介" prop="blogDesc">
          <el-input v-model="form.blogDesc" placeholder="请输入简介" />
        </el-form-item>
        <el-form-item label="标签">
          <el-checkbox-group v-model="form.tagIds">
            <el-checkbox v-for="item in tagOptions" :label="item.tagId" :key="item.tagId" :value="item.tagId">
              {{item.tagName}}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-col>
          <el-form-item label="分类">
            <el-select v-model="form.typeIds" multiple placeholder="请选择" filterable clearable>
              <el-option v-for="item in typeOptions" :key="item.typeId" :label="item.typeName" :value="item.typeId">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col>
          <el-form-item label="置顶">
            <el-checkbox v-model="Top"></el-checkbox>
          </el-form-item>
        </el-col>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listBlog, getBlog, delBlog, addBlog, updateBlog } from "@/api/ry/blog";
import {Loading} from 'element-ui'
import errorCode from '../../../utils/errorCode'
// import filesUpload from './components/filesUpload'

export default {
  // components: {
  //   filesUpload
  // },
  name: "Blog",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 博客信息表格数据
      blogList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      type: 1,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        type: 1,
        contentType: "1",
        content: null,
        contentMarkdown: null,
        Top: null,
        views: null,
        status: null,
        blogPicType: null,
        blogPic: null,
        blogPicLink: null,
        blogDesc: null,
        blogFiles: null
      },
      Top: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [
          { required: true, message: "标题不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "类型不能为空", trigger: "change" }
        ],
        contentType: [
          { required: true, message: "文本编辑器类型不能为空", trigger: "change" }
        ],
      },
      fileIds: [],
      // 类型选项
      typeOptions: [],
      // 标签选项
      tagOptions: [],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询博客信息列表 */
    getList() {
      this.loading = true;
      listBlog(this.queryParams).then(response => {
        this.blogList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        title: null,
        type: 1,
        contentType: "1",
        content: null,
        contentMarkdown: null,
        Top: "0",
        views: null,
        status: null,
        blogPicType: "0",
        blogPic: null,
        blogPicLink: null,
        blogDesc: null,
        blogFiles: null,
        tagIds: [],
        typeIds: [],
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      console.log("hh");
      this.open = true;
      getBlog().then(response =>{
        console.log("zz")
        this.typeOptions = response.types;
        this.tagOptions = response.tags;
        this.reset();
        this.open = true;
        this.title = "添加文章信息";
      })

    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBlog(id).then(response => {
        console.log("response =>" , response);
        this.typeOptions = response.types;
        this.tagOptions = response.tags;
        this.form = response.data;
        this.open = true;
        this.title = "修改博客信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {

          this.form.type = 1;
          this.form.status = 1;
          if(this.top){
            this.form.Top = 1;
          }
          if (this.form.id != null) {
            updateBlog(this.form).then(response => {
              console.log("response =" , response);
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {//没有id就是添加
            addBlog(this.form).then(response => {
              console.log("response =" , response);
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除博客信息编号为"' + ids + '"的数据项？').then(function() {
        return delBlog(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('ry/blog/export', {
        ...this.queryParams
      }, `blog_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style scoped lang="scss">
  .tabBlock{
    height: 180px;
    margin-top: 20px;
  }
  .blogPic{
    width: 200px;
    height: 180px;
  }
</style>
