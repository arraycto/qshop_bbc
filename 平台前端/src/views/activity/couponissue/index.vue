<template>
  <div class="app-container">
     <!--工具栏-->
    <div class="head-container">
      <!-- 新增 -->
      <el-button
        type="danger"
        class="filter-item"
        size="mini"
        icon="el-icon-refresh"
        @click="toQuery"
      >刷新</el-button>
    </div>
    <!--表格渲染-->
    <el-table v-loading="loading" :data="data" size="small" style="width: 100%;">
      <el-table-column prop="id" label="id" />
      <el-table-column prop="cname" label="优惠券名称" />
      <el-table-column label="领取日期">
        <template slot-scope="scope">
          <p>{{ formatTimeTwo(scope.row.startTime) }}</p>
          <p>{{ formatTimeTwo(scope.row.endTime) }}</p>
        </template>
      </el-table-column>
      <el-table-column label="发布数量">
        <template slot-scope="scope">
          <p>发布:{{ scope.row.totalCount }}</p>
          <p>剩余:{{ scope.row.remainCount }}</p>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center">
        <template slot-scope="scope">
          <div>
            <el-tag v-if="scope.row.status === 1" style="cursor: pointer" :type="''">开启</el-tag>
            <el-tag v-else :type=" 'info' ">关闭</el-tag>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <!--分页组件-->
    <el-pagination
      :total="total"
      :current-page="page + 1"
      style="margin-top: 8px;"
      layout="total, prev, pager, next, sizes"
      @size-change="sizeChange"
      @current-change="pageChange"
    />
  </div>
</template>

<script>
import checkPermission from '@/utils/permission'
import initData from '@/mixins/crud'
import { del } from '@/api/storeCouponIssue'
import eForm from './formt'
import { formatTimeTwo } from '@/utils/index'
export default {
  components: { eForm },
  mixins: [initData],
  data() {
    return {
      delLoading: false
    }
  },
  created() {
    this.$nextTick(() => {
      this.init()
    })
  },
  methods: {
    formatTimeTwo,
    checkPermission,
    beforeInit() {
      this.url = 'api/storeCouponIssue'
      const sort = 'id,desc'
      this.params = { page: this.page, size: this.size, sort: sort }
      return true
    }
  }
}
</script>

<style scoped>

</style>
