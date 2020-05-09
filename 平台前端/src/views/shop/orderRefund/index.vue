<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <eDetail ref="form" :is-add="isAdd" />
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column v-if="columns.visible('id')" prop="id" label="编号" />
        <el-table-column v-if="columns.visible('orderReturnId')" prop="orderReturnId" label="退款单编号" />
        <el-table-column v-if="columns.visible('orderId')" prop="orderId" label="订单编号" />
        <el-table-column v-if="columns.visible('status')" prop="status" label="申请状态" >
          <template slot-scope="scope">
          <div>
              <el-tag v-if="scope.row.status === 0" style="cursor: pointer" :type="''">待处理</el-tag>
              <el-tag v-else-if="scope.row.status === 2" style="cursor: pointer" :type="'finish'">已完成</el-tag>
              <el-tag v-else-if="scope.row.status === 3" style="cursor: pointer" :type="'refuse'">已拒绝</el-tag>
              <el-tag v-else style="cursor: pointer" :type=" 'refunding' ">退款中</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('orderAmount')" prop="orderAmount" label="订单金额" />
        <el-table-column v-if="columns.visible('returnAmount')" prop="returnAmount" label="退款金额" />
        <el-table-column v-if="columns.visible('realAmount')" prop="realAmount" label="实退金额" />
        <el-table-column v-if="columns.visible('productRealPrice')" prop="productRealPrice" label="商品实际支付单价" />
        <el-table-column v-if="columns.visible('reason')" prop="reason" label="申请退款原因" />
        <el-table-column v-if="columns.visible('handleMan')" prop="handleMan" label="处理人员" />
        <el-table-column v-if="columns.visible('shopName')" prop="shopName" label="所属店铺" />
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
        <el-table-column v-permission="['admin','orderRefund:edit','orderRefund:del']" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <el-button
            v-permission="['admin','orderRefund:list','orderRefund:edit']"
            size="mini"
            type="primary"
            @click="refund(scope.row)"
             v-if="scope.row.status==1"
          >
            退款</el-button>
            <el-button
            v-permission="['admin','orderRefund:list','orderRefund:edit']"
            size="mini"
            type="primary"
            @click="detail(scope.row)"
          >
            退款详情</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudOrderRefund from '@/api/orderRefund'
import eDetail from './detail'
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
  components: { pagination, crudOperation, rrOperation, udOperation, parseTime, formatTimeTwo, eDetail },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      isAdd: false,
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
    detail(data) {
      this.isAdd = false
      const _this = this.$refs.form
      _this.form = {
        orderReturnId: data.orderReturnId,
        orderId: data.orderId,
        orderAmount: data.orderAmount,
        retunAmount: data.retunAmount,
        realAmount: data.realAmount,
        productRealPrice: data.productRealPrice,
        reason: data.reason,
        handleMan: data.handleMan,
        shopName: data.shopName,
        status: data.status
      }
      _this.dialog = true
    },
    refund(row) {
      let that = this
      this.$confirm(`确定进行['退款'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          crudOrderRefund.refund({ id: row.id }).then(({ data }) => {
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
        .catch((err) => { console.log(err)})
    }
  }
}
</script>

<style scoped>

</style>
