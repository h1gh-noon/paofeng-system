<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px">
      <el-form-item label="骑手名称" prop="riderName">
        <el-input v-model="queryParams.riderName" placeholder="请输入骑手名称" clearable style="width: 240px"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="手机号码" prop="phonenumber">
        <el-input v-model="queryParams.phonenumber" placeholder="请输入手机号码" clearable style="width: 240px"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="骑手状态" clearable style="width: 240px">
          <el-option v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.label"
            :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select v-model="queryParams.sex" placeholder="性别" clearable style="width: 240px">
          <el-option v-for="dict in dict.type.sys_user_sex" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker v-model="dateRange" style="width: 240px" value-format="yyyy-MM-dd" type="daterange"
          range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="tableList">
      <el-table-column label="骑手名称" align="center" key="riderName" prop="riderName" />
      <el-table-column label="性别" align="center" key="sex" prop="sex">
        <template slot-scope="{row}">
          <dict-tag :options="dict.type.sys_user_sex" :value="row.sex" />
        </template>
      </el-table-column>
      <el-table-column label="本人照片" align="center" key="riderPhoto" prop="riderPhoto">
        <template slot-scope="{row}">
          <a class="link-type" @click.prevent="riderPhotos(row.riderPhoto)">查看</a>
        </template>
      </el-table-column>
      <el-table-column label="身份证号" align="center" key="cardId" prop="cardId" show-overflow-tooltip />
      <el-table-column label="身份证正面照片" align="center" key="cardPhotoZ" prop="cardPhotoZ">
        <template slot-scope="{row}">
          <a class="link-type" @click.prevent="riderPhotos(row.cardPhotoZ)">查看</a>
        </template>
      </el-table-column>
      <el-table-column label="身份证背面照片" align="center" key="cardPhotoB" prop="cardPhotoB">
        <template slot-scope="{row}">
          <a class="link-type" @click.prevent="riderPhotos(row.cardPhotoB)">查看</a>
        </template>
      </el-table-column>
      <el-table-column label="联系电话" align="center" key="phonenumber" prop="phonenumber" width="120" />
      <el-table-column label="第二联系电话" align="center" key="secondPhone" prop="secondPhone" width="120" />
      <el-table-column label="钱包金额" align="center" key="riderMoney" prop="riderMoney" width="120" />
      <el-table-column label="状态" align="center" key="status">
        <template slot-scope="{row}">
          <dict-tag :options="dict.type.sys_normal_disable" :value="row.status" />
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" prop="createTime" width="120">
        <template slot-scope="{row}">
          <a class="link-type" @click="handleStatusChange(row)">{{ row.status === '1' ? '启用' : '停用' }}</a>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 图片查看 -->
    <el-dialog title="查看" :visible.sync="riderPhotosDialog" append-to-body>
      <image-preview v-for="item in riderPhotosArr" key="item.url" :src="item.url" />
    </el-dialog>

    <!-- 用户导入对话框 -->
    <!-- <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog> -->
  </div>
</template>

<script>
import { getRiderList, updateStatus } from "@/api/rider";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "User",
  dicts: ['sys_normal_disable', 'sys_user_sex'],
  data() {
    return {
      // 遮罩层
      loading: true,
      dateRange: [],
      total: 0,
      tableList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        address: undefined,
        sex: undefined,
        phonenumber: undefined,
        status: undefined,
        riderName: undefined
      },
      riderPhotosDialog: false,
      riderPhotosArr: []
    }
  },
  created() {
    this.getList();
  },
  methods: {
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 查询用户列表 */
    getList() {
      this.loading = true;
      getRiderList(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.tableList = response.rows;
        this.total = response.total;
        this.loading = false;
      }
      );
    },
    riderPhotos(val) {
      this.riderPhotosArr = JSON.parse(val)
      this.riderPhotosDialog = true
    },
    handleStatusChange(val) {
      this.$confirm(`确认${val.status === '1' ? '启用' : '停用'} ${val.riderName}?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const data = { riderId: val.riderId, status: val.status === '1' ? '0' : '1' }
        updateStatus(data).then(res => {
          if (res.code === 200) {
            this.getList()
            this.$message({
              type: 'success',
              message: '操作成功!'
            });
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        });
      });
    }
  }
};
</script>
