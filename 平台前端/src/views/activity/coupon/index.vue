<template>
  <div class="app-container">
    <!--表格渲染-->
    <el-table v-loading="loading" :data="data" size="small" style="width: 100%;">
      <!--<el-table-column prop="id" label="ID"/>-->
      <el-table-column prop="title" label="优惠券名称" />
      <el-table-column prop="couponPrice" label="优惠券面值" />
      <el-table-column prop="useMinPrice" label="优惠券最低消费" />
      <el-table-column label="优惠券有效期限">
        <template slot-scope="scope">
          <span>{{ scope.row.couponTime }}天</span>
        </template>
      </el-table-column>
      <el-table-column prop="sort" label="排序" width="100" />
      <el-table-column label="状态" width="100" align="center">
        <template slot-scope="scope">
          <div>
            <el-tag v-if="scope.row.status === 1" style="cursor: pointer" :type="''">开启</el-tag>
            <el-tag v-else :type=" 'info' ">关闭</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="addTime" label="创建时间">
        <template slot-scope="scope">
          <span>{{ formatTime(scope.row.addTime) }}</span>
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
import { del, onVerify } from '@/api/storeCoupon'
import eForm from './form'
import eIForm from '../couponissue/form'
import { formatTime } from '@/utils/index'
export default {
  components: { eForm, eIForm },
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
    formatTime,
    checkPermission,
    beforeInit() {
      this.url = 'api/storeCoupon'
      const sort = 'id,desc'
      this.params = { page: this.page, size: this.size, sort: sort, isDel: 0 }
      return true
    }
  }
}
</script>

<style scoped>

</style>
