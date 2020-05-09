<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '退款详情'" width="700px">
    <el-card>
      <div slot="header">
        <span>订单信息</span>
      </div>
      <div class="text item">退款单号:{{ form.orderReturnId }}</div>
      <div class="text item">订单号: {{ form.orderId }}</div>
      <div class="text item">订单金额: {{ form.orderAmount }}</div>
      <div class="text item">退款金额: {{ form.retunAmount }}</div>
      <div class="text item">实退金额: {{ form.realAmount }}</div>
    </el-card>
    <el-card>
      <div slot="header">
        <span>商品信息</span>
      </div>
      <el-row :gutter="24">
        <el-col :span="12">
          <div class="text item">商品实际支付单价: {{ form.productRealPrice }}</div>
          <div class="text item">申请退款原因: {{ form.reason }}</div>
          <div class="text item">处理人员: {{ form.handleMan }}</div>
          <div class="text item">所属店铺: {{ form.shopName }}</div>
          <div class="text item">申请状态: {{ form.status | formatStatus }}</div>
        </el-col>
      </el-row>
    </el-card>
  </el-dialog>
</template>

<script>
import { add, edit, express } from '@/api/orderRefund'
import { parseTime } from '@/utils/index'
export default {
  props: {
    isAdd: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      loading: false, dialog: false, expressInfo: [],
      form: {
        orderReturnId: '',
        orderId: '',
        orderAmount: '',
        retunAmount: '',
        realAmount: '',
        productRealPrice: '',
        reason: '',
        handleMan: '',
        shopName: '',
        status: ''
      }
    }
  },
  filters: {
      formatStatus: function(val){
          if (val==0){
              return '待处理'
          }else if (val==2){
              return '已完成'
          }
          else if (val==3){
              return '已拒绝'
          }else {
              return '退款中'
          }
      }
  },
  methods: {
    parseTime,
    cancel() {
      this.dialog = false
    },
    resetForm() {
      this.dialog = false
      this.$refs['form'].resetFields()
      this.form = {
        orderReturnId: '',
        orderId: '',
        orderAmount: '',
        retunAmount: '',
        realAmount: '',
        productRealPrice: '',
        reason: '',
        handleMan: '',
        shopName: '',
        status: ''
      }
    }
  }
}
</script>

<style scoped>
  .text {
    font-size: 12px;
  }

  .item {
    padding: 6px 0;
  }

</style>