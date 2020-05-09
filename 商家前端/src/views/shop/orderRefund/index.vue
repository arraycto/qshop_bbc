<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column v-if="columns.visible('id')" prop="id" label="编号" />
        <el-table-column v-if="columns.visible('orderReturnId')" prop="orderReturnId" label="售后申请id" />
        <el-table-column v-if="columns.visible('orderId')" prop="orderId" label="订单编号" />
        <el-table-column v-if="columns.visible('status')" prop="status" label="申请状态" >
          <template slot-scope="scope">
          <div>
              <el-tag v-if="scope.row.status === 0" style="cursor: pointer" :type="''">待处理</el-tag>
              <el-tag v-else-if="scope.row.status === 2" style="cursor: pointer" :type="'finish'">已完成</el-tag>
              <el-tag v-else-if="scope.row.status === 3" style="cursor: pointer" :type="'refuse'">已拒绝</el-tag>
              <el-tag v-else style="cursor: pointer" :type=" 'refunding' ">退货中</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('orderAmount')" prop="orderAmount" label="订单金额" />
        <el-table-column v-if="columns.visible('returnAmount')" prop="returnAmount" label="退款金额" />
        <el-table-column v-if="columns.visible('realAmount')" prop="realAmount" label="实退金额" />
        <el-table-column v-if="columns.visible('reason')" prop="reason" label="申请退款原因" />
        <el-table-column v-if="columns.visible('handleMan')" prop="handleMan" label="处理人员" />
        <!-- <el-table-column v-if="columns.visible('status')" prop="status" label="审核状态" >
          <template slot-scope="scope">
          <div @click="onVerify(scope.row.id,scope.row.verifyStatus)">
              <el-tag v-if="scope.row.verifyStatus === 0" style="cursor: pointer" :type="''">待审核</el-tag>
              <el-tag v-else-if="scope.row.verifyStatus === 1" style="cursor: pointer" :type="'finish'">审核通过</el-tag>
              <el-tag v-else style="cursor: pointer" :type=" 'refunding' ">审核不通过</el-tag>
            </div>
          </template>
        </el-table-column> -->
        <el-table-column v-if="columns.visible('addTime')" prop="addTime" label="添加时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.addTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('modifyTime')" prop="modifyTime" label="更新时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.modifyTime) }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column v-permission="['admin','orderRefund:edit','orderRefund:del']" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column> -->
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudOrderRefund from '@/api/orderRefund'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import { formatTimeTwo, parseTime } from '@/utils/index'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '订单退货申请', url: 'api/orderRefund', sort: 'id,desc', crudMethod: { ...crudOrderRefund }})
const defaultForm = { id: null, orderReturnId: null, orderId: null, status: null, orderAmount: null, returnAmount: null, realAmount: null, productRealPrice: null, reason: null, handleMan: null, storeId: null, addTime: null, modifyTime: null, deleted: null }
export default {
  name: 'OrderRefund',
  components: { pagination, crudOperation, rrOperation, udOperation, parseTime, formatTimeTwo },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      permission: {
        add: ['admin', 'orderRefund:add'],
        edit: ['admin', 'orderRefund:edit'],
        del: ['admin', 'orderRefund:del']
      },
      rules: {
        addTime: [
          { required: true, message: '添加时间不能为空', trigger: 'blur' }
        ],
        modifyTime: [
          { required: true, message: '更新时间不能为空', trigger: 'blur' }
        ],
        deleted: [
          { required: true, message: '逻辑删除不能为空', trigger: 'blur' }
        ]
      }    }
  },
  created() {
    this.crud.optShow = { add: false, edit: false, del: false, download: true }
  },
  methods: {
    // 获取数据前设置好接口地址
    [CRUD.HOOK.beforeRefresh]() {
      return true
    },
    onVerify(id, status) {
      let that = this
      this.$confirm(`确定进行[${status ? '不通过' : '通过'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          crudOrderRefund.edit({ verifyStatus: (status==0)?1:0, id: id }).then(({ data }) => {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1000,
              onClose: () => {
                that.$router.go(0)
              }
            })
          })
        })
        .catch(() => { })
    }
  }
}
</script>

<style scoped>

</style>
