<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <el-button
        type="danger"
        class="filter-item"
        size="mini"
        icon="el-icon-refresh"
        @click="toQuery"
      >刷新</el-button>
    </div>

    <el-table ref="table" v-loading="loading" :data="data" size="small" style="width: 100%;">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="id" />
        <el-table-column prop="name" label="店铺名称" />
        <el-table-column prop="contactMobile" label="联系电话" />
        <el-table-column prop="servicePhone" label="服务电话" />
        <el-table-column prop="isStar" label="是否收藏" />
        <el-table-column ref="table" prop="logo" label="图标">
          <template slot-scope="scope">
            <a :href="scope.row.logo" style="color: #42b983" target="_blank"><img :src="scope.row.logo" alt="点击打开" class="el-avatar"></a>
          </template>
        </el-table-column>
        <el-table-column prop="addressDetail" label="地址细节" />
        <el-table-column prop="description" label="描述" />
        <el-table-column prop="collect" label="收藏数量" />
        <el-table-column prop="memberCount" label="会员数量" />
        <el-table-column prop="orderCount" label="订单数量" />
        <el-table-column prop="payAmount" label="付款总额" />
        <el-table-column prop="articleCount" label="文章总数" />
        <el-table-column prop="shopStatus" label="店铺入驻状态" />
        <el-table-column prop="closedStatus" label="关店状态" />
        <el-table-column label="精品店铺标识" align="center">
          <template slot-scope="scope">
            <div>
              <el-tag v-if="scope.row.isBoutique === 1" style="cursor: pointer" :type="''">精品</el-tag>
              <el-tag v-else style="cursor: pointer" :type=" 'info' ">普通</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column ref="table" prop="ownerIdentity1" label="法定代表人身份证正面">
          <template slot-scope="scope">
            <a :href="scope.row.ownerIdentity1" style="color: #42b983" target="_blank"><img :src="scope.row.ownerIdentity1" alt="点击打开" class="el-avatar"></a>
          </template>
        </el-table-column>
        <el-table-column ref="table" prop="ownerIdentity2" label="法定代表人身份证反面">
          <template slot-scope="scope">
            <a :href="scope.row.ownerIdentity2" style="color: #42b983" target="_blank"><img :src="scope.row.ownerIdentity2" alt="点击打开" class="el-avatar"></a>
          </template>
        </el-table-column>
        <el-table-column prop="expireTime" label="到期时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.expireTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="deleteTime" label="关店时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.deleteTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="addTime" label="添加时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.addTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="modifyTime" label="更新时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.modifyTime) }}</span>
          </template>
        </el-table-column>
      </el-table>
  </div>
</template>


<script>
import checkPermission from '@/utils/permission'
import initData from '@/mixins/crud'
import { formatTimeTwo, parseTime } from '@/utils/index'


// crud交由presenter持有
export default {
  components: {},
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
      this.url = 'api/shop'
      const sort = 'id,desc'
      this.params = { page: this.page, size: this.size, sort: sort }
      return true
    }
  }
}
</script>

<style scoped>

</style>
