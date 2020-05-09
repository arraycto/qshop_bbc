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
    <!--表单组件-->
    <eForm ref="form" :is-add="isAdd" />
    <!--表格渲染-->
    <el-table v-loading="loading" :data="data" size="small" style="width: 100%;">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="username" label="用户" />
      <el-table-column prop="productName" label="商品信息" />
      <el-table-column prop="productScore" label="商品分数" />
      <el-table-column prop="serviceScore" label="服务分数" />
      <el-table-column prop="comment" label="评论内容" />
      <el-table-column prop="" label="评论图片">
        <template slot-scope="scope">
          <div v-if="scope.row.pics">
            <a v-for="pic in handlePic(scope.row.pics)" :href="pic" style="color: #42b983" target="_blank">
              <img :src="pic" alt="点击打开" class="el-avatar">
            </a>
          </div>
          <div v-else>无图</div>
        </template>
      </el-table-column>
      <el-table-column prop="addTime" label="评论时间">
        <template slot-scope="scope">
          <span>{{ formatTime(scope.row.addTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="isReply" label="评论回复状态">
        <template slot-scope="scope">
          <span>{{ scope.row.isReply | filterStatus }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="isDel" label="显示状态">
        <template slot-scope="scope">
          <span>{{ scope.row.isDel | filterDStatus }}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="checkPermission(['admin','STOREPRODUCTREPLY_ALL','STOREPRODUCTREPLY_EDIT','STOREPRODUCTREPLY_DELETE'])" label="操作" width="150px" align="center">
        <template slot-scope="scope">
          <el-button
            v-permission="['admin','orderRefund:list','orderRefund:edit']"
            size="mini"
            type="primary"
            @click="reply(scope.row)"
          >
            回复</el-button>
          <!-- <el-popover
            :ref="scope.row.id"
            v-permission="['admin','STOREPRODUCTREPLY_ALL','STOREPRODUCTREPLY_DELETE']"
            placement="top"
            width="180"
          >
            <p>确定删除本条数据吗？</p>
            <div style="text-align: right; margin: 0">
              <el-button size="mini" type="text" @click="$refs[scope.row.id].doClose()">取消</el-button>
              <el-button :loading="delLoading" type="primary" size="mini" @click="subDelete(scope.row.id)">确定</el-button>
            </div>
            <el-button slot="reference" type="danger" icon="el-icon-delete" size="mini" />
          </el-popover> -->
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
import { del } from '@/api/storeProductReply'
import eForm from './form'
import { formatTime } from '@/utils/index'
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
  filters: {
      filterStatus: function(status) {
        if (status==1){
          return '已回复'
        }else{
          return '未回复'
        }
      },
      filterDStatus: function(status) {
        if (status==1){
          return '隐藏'
        }else{
          return '显示'
        }
      }
  },
  methods: {
    handlePic(pics) {
      return pics.split(',')
    },
    formatTime,
    checkPermission,
    beforeInit() {
      this.url = 'api/storeProductReply'
      const sort = 'id,desc'
      this.params = { page: this.page, size: this.size, sort: sort }
      return true
    },
    // subDelete(id) {
    //   this.delLoading = true
    //   del(id).then(res => {
    //     this.delLoading = false
    //     this.$refs[id].doClose()
    //     this.dleChangePage()
    //     this.init()
    //     this.$notify({
    //       title: '删除成功',
    //       type: 'success',
    //       duration: 2500
    //     })
    //   }).catch(err => {
    //     this.delLoading = false
    //     this.$refs[id].doClose()
    //     console.log(err.response.data.message)
    //   })
    // },
    reply(data) {
      this.isAdd = false
      const _this = this.$refs.form
      _this.form = {
        id: data.id,
        comment: data.comment,
        pics: data.pics,
        addTime: data.addTime,
        merchantReplyContent: data.merchantReplyContent
      }
      _this.dialog = true
    }
  }
}
</script>

<style scoped>

</style>
