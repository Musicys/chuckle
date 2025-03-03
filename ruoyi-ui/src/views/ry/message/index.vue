<template>
  <div class="app-container">
    <!--<el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">-->
    <!--  <el-form-item label="留言者" prop="userId">-->
    <!--    <el-input-->
    <!--      v-model="queryParams.userId"-->
    <!--      placeholder="请输入留言者"-->
    <!--      clearable-->
    <!--      @keyup.enter.native="handleQuery"-->
    <!--    />-->
    <!--  </el-form-item>-->
    <!--  <el-form-item>-->
    <!--    <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>-->
    <!--    <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>-->
    <!--  </el-form-item>-->
    <!--</el-form>-->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px"
             style="padding-left: 20px;">
      <el-form-item label="留言者" prop="content" v-if="isAdmin">
        <el-input v-model="queryParams.createBy" placeholder="请输入留言者" clearable size="small"
                  @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="留言内容" prop="content">
        <el-input v-model="queryParams.content" placeholder="请输入留言内容" clearable size="small"
                  @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!--<el-row :gutter="10" class="mb8">-->
    <!--  <el-col :span="1.5">-->
    <!--    <el-button-->
    <!--      type="primary"-->
    <!--      plain-->
    <!--      icon="el-icon-plus"-->
    <!--      size="mini"-->
    <!--      @click="handleAdd"-->
    <!--      v-hasPermi="['ry:message:add']"-->
    <!--    >新增</el-button>-->
    <!--  </el-col>-->
    <!--  <el-col :span="1.5">-->
    <!--    <el-button-->
    <!--      type="success"-->
    <!--      plain-->
    <!--      icon="el-icon-edit"-->
    <!--      size="mini"-->
    <!--      :disabled="single"-->
    <!--      @click="handleUpdate"-->
    <!--      v-hasPermi="['ry:message:edit']"-->
    <!--    >修改</el-button>-->
    <!--  </el-col>-->
    <!--  <el-col :span="1.5">-->
    <!--    <el-button-->
    <!--      type="danger"-->
    <!--      plain-->
    <!--      icon="el-icon-delete"-->
    <!--      size="mini"-->
    <!--      :disabled="multiple"-->
    <!--      @click="handleDelete"-->
    <!--      v-hasPermi="['ry:message:remove']"-->
    <!--    >删除</el-button>-->
    <!--  </el-col>-->
    <!--  <el-col :span="1.5">-->
    <!--    <el-button-->
    <!--      type="warning"-->
    <!--      plain-->
    <!--      icon="el-icon-download"-->
    <!--      size="mini"-->
    <!--      @click="handleExport"-->
    <!--      v-hasPermi="['ry:message:export']"-->
    <!--    >导出</el-button>-->
    <!--  </el-col>-->
    <!--  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
    <!--</el-row>-->

    <!--<el-table v-loading="loading" :data="messageList" @selection-change="handleSelectionChange">-->
    <!--  <el-table-column type="selection" width="55" align="center" />-->
    <!--  <el-table-column label="ID" align="center" prop="id" />-->
    <!--  <el-table-column label="父留言id" align="center" prop="parentId" />-->
    <!--  <el-table-column label="主留言id(第一级留言)" align="center" prop="mainId" />-->
    <!--  <el-table-column label="点赞数量" align="center" prop="likeNum" />-->
    <!--  <el-table-column label="内容" align="center" prop="content" />-->
    <!--  <el-table-column label="留言类型：对人评论，对项目评论，对资源评论" align="center" prop="type" />-->
    <!--  <el-table-column label="被留言者id，可以是人、项目、资源" align="center" prop="blogId" />-->
    <!--  <el-table-column label="留言者id" align="center" prop="userId" />-->
    <!--  <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
    <!--    <template slot-scope="scope">-->
    <!--      <el-button-->
    <!--        size="mini"-->
    <!--        type="text"-->
    <!--        icon="el-icon-edit"-->
    <!--        @click="handleUpdate(scope.row)"-->
    <!--        v-hasPermi="['ry:message:edit']"-->
    <!--      >修改</el-button>-->
    <!--      <el-button-->
    <!--        size="mini"-->
    <!--        type="text"-->
    <!--        icon="el-icon-delete"-->
    <!--        @click="handleDelete(scope.row)"-->
    <!--        v-hasPermi="['ry:message:remove']"-->
    <!--      >删除</el-button>-->
    <!--    </template>-->
    <!--  </el-table-column>-->
    <!--</el-table>-->
    <el-card>
      <div class="el-card__header" style="text-align: left;padding: 0">
        <h3 style="float: left;margin: 0;">留言列表</h3>
        <right-toolbar style="float: right;" :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </div>
      <ul style="padding: 0;" class="comment-list">
        <li v-show="messageList.length==0"
            style="text-align: center;border-bottom: 1px dashed #ccc;margin: 10px 0;list-style-type:none;">
          <span class="el-table__empty-text">暂无数据</span>
        </li>
        <li class="comment" v-for="mes in messageList" :key="mes.id">
          <el-avatar v-if="mes.avatar!==''&&mes.avatar!=null" :src="mes.avatar"></el-avatar>
          <el-avatar v-else icon="el-icon-user-solid"></el-avatar>
          <div class="content">
            <div style="display: flex;justify-content: space-between;width: 100%">
              <div class="nkname">
                <span class="name">{{mes.createBy}} </span>
                <span class="date">{{mes.createTime}}</span>
                <span v-show="mes.type=='0'" class="rp">给你留言</span>
                <span v-show="mes.type=='1'" class="rp">回复了</span>
                <span v-show="mes.type=='1'" class="parentCreateBy">{{mes.parentCreateBy}}</span>
                <span v-show="mes.type=='1'" class="rp">的留言</span>
              </div>
              <div class="op">
                <span @click="getBlogInfo(mes.id)" class="blog">查看</span>
                <span> | </span>
                <el-button type="text" @click="handleAdd(mes)" v-hasPermi="['cms:message:add']">回复</el-button>
                <span v-show="!isAdmin&&mes.createBy!=name" style="margin-right: 39.43px;"></span>
                <span v-show="!(!isAdmin&&mes.createBy!=name)"> | </span>
                <span v-show="!(!isAdmin&&mes.createBy!=name)" class="del" @click="handleDelete(mes)">删除</span>
              </div>
            </div>
            <p class="reply">{{mes.content}}</p>
          </div>
        </li>
      </ul>
    </el-card>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改留言对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="400px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!--<div class="blog-container" >-->
        <!--  <VueEmoji ref="emoji" :value="inputText" @input="onInput" width="auto" :placeholder="toName"></VueEmoji>-->
        <!--  <el-button type="primary" size="mini" @click="clearInput">清除</el-button>-->
        <!--</div>-->
        <el-input  v-model="form.content" type="textarea" maxlength="100" show-word-limit :placeholder="toName" />
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMessage, getMessage, delMessage, addMessage, updateMessage } from "@/api/ry/message";
import VueEmoji from "emoji-vue";
export default {
  name: "Message",
  components: {
    VueEmoji
  },
  data() {
    return {
      //对话框
      // textarea: {},
      // //表情
      // inputValue: "",
      // visibleEmoji: false,
      // blurPos: undefined,
      // inputText: "",
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],

      name: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: false,
      // 总条数
      total: 0,
      // 留言表格数据
      messageList: [],

      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        parentId: null,
        mainId: null,
        likeNum: null,
        content: null,
        type: null,
        blogId: null,
        userId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      toName:'',
      isAdmin: false,
      // cursorIndexStart: null,//光标选中开始的位置
      // cursorIndexEnd: null,//光标选中结束的位置
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询留言列表 */
    getList() {
      this.loading = true;
      console.log("thisqueryParams=",this.queryParams)
      listMessage(this.queryParams).then(response => {
        console.log("responsedata = " , response);
        //加阿里云头像
        // for (let i = 0; i < response.rows.length; i++) {
        //   let mes = response.rows[i];
        //   if(mes.avatar != null && mes.avatar != ""){
        //     //取出阿里云存的头像
        //     response.rows[i].avatar = mes.avatar;
        //   }
        // }
        this.messageList = response.rows;
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
        parentId: null,
        mainId: null,
        likeNum: null,
        content: null,
        type: null,
        blogId: null,
        delFlag: null,
        userId: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
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
    handleAdd(mes) {
      this.reset();
      if(mes.mainId != null){//如果有主id就设置
        this.form.mainId = mes.mainId;
      }else {//不然主id就是自己
        this.form.mainId = mes.id;
      }
      this.form.parentId = mes.id;
      this.form.type = '1';//回复1  留言0
      this.form.createBy = this.$store.getters.name;
      this.form.updateBy = this.$store.getters.name;
      this.toName = "@" + mes.createBy;
      this.open = true;
      this.title = "回复留言";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMessage(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改留言";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMessage(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMessage(this.form).then(response => {
              this.$modal.msgSuccess("回复成功");
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
      this.$modal.confirm('是否确认删除留言编号为"' + ids + '"的数据项？').then(function() {
        return delMessage(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('ry/message/export', {
        ...this.queryParams
      }, `message_${new Date().getTime()}.xlsx`)
    },
    onInput(emoji) {
      console.log(emoji);
      console.log("inputText = ",this.inputText)
      this.textarea = emoji;
      console.log("图片为 ",this.textarea)
    },
    clearInput() {
      this.$refs.emoji.clear();
    }
  }
};
</script>

<style scoped>
.blog-container {
  padding: 10px;

}
.nkname {
  margin-left: 10px;
  max-width: 530px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.content {
  text-align: left;
  font-size: 14px;
  flex-grow: 1;
}

.rp,
.date {
  color: #999;
  margin-left: 10px;
}

.parentCreateBy {
  margin-left: 10px;
}

.blog {
  color: #349edf;
  margin-left: 10px;
  cursor: pointer;
}

.reply {
  margin-left: 10px;
}

.op {
  color: #ddd;
  font-weight: lighter;
}

.rep {
  color: #349edf;
}

.del {
  color: red;
}

.op:hover {
  cursor: pointer;
}

.el-table__empty-text {
  line-height: 60px;
  width: 50%;
  color: #909399;
}
.comment {
  border-bottom: 1px dashed #ccc;
  margin: 10px 0;
  display: flex;
}



</style>
