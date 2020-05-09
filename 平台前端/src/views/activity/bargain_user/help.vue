<template>
  <div class="app-container">
    <el-table v-loading="loading" :data="data" size="small" style="width: 100%;">
      <el-table-column prop="id" label="用户参与砍价表ID" />
      <el-table-column prop="uid" label="用户ID" />
      <el-table-column prop="bargainId" label="砍价产品id" />
      <el-table-column prop="bargainUserId" label="用户参与砍价表id" />
      <el-table-column prop="price" label="帮助砍价多少金额" />
      <el-table-column prop="addTime" label="结参与时间束时间">
        <template slot-scope="scope">
          <span>{{ formatTimeTwo(scope.row.addTime) }}</span>
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
import { del } from '@/api/storeBargainUserHelp'
import eForm from './form'
import { formatTimeTwo, parseTime } from '@/utils/index'
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
    parseTime,
    formatTimeTwo,
    checkPermission,
    beforeInit() {
      this.url = 'api/storeBargainUserHelp'
      const sort = 'id,desc'
      this.params = { page: this.page, size: this.size, sort: sort }
      return true
    }
  }
}
</script>

<style scoped>

</style>
