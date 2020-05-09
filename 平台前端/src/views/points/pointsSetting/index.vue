<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="560px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
          <el-form-item label="id">
            <el-input v-model="form.id" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="每一元需要抵扣的积分数量">
            <el-input v-model="form.deductionPerAmount" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="每笔订单最高抵用百分比">
            <el-input v-model="form.maxPercentPerOrder" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="每次使用积分最小单位100">
            <el-input v-model="form.useUnit" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="是否可以和优惠券同用；0->不可以；1->可以">
            <el-input v-model="form.couponStatus" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="登录送积分">
            <el-input v-model="form.login" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="注册送积分">
            <el-input v-model="form.register" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="签到送积分">
            <el-input v-model="form.sign" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="下单 送积分比例">
            <el-input v-model="form.orders" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="所属店铺">
            <el-input v-model="form.storeId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="添加时间" prop="addTime">
            <el-input v-model="form.addTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="更新时间" prop="modifyTime">
            <el-input v-model="form.modifyTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="逻辑删除" prop="deleted">
            <el-input v-model="form.deleted" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column v-if="columns.visible('id')" prop="id" label="id" />
        <el-table-column v-if="columns.visible('deductionPerAmount')" prop="deductionPerAmount" label="每一元需要抵扣的积分数量" />
        <el-table-column v-if="columns.visible('maxPercentPerOrder')" prop="maxPercentPerOrder" label="每笔订单最高抵用百分比" />
        <el-table-column v-if="columns.visible('useUnit')" prop="useUnit" label="每次使用积分最小单位100" />
        <el-table-column v-if="columns.visible('couponStatus')" prop="couponStatus" label="是否可以和优惠券同用；0->不可以；1->可以" />
        <el-table-column v-if="columns.visible('login')" prop="login" label="登录送积分" />
        <el-table-column v-if="columns.visible('register')" prop="register" label="注册送积分" />
        <el-table-column v-if="columns.visible('sign')" prop="sign" label="签到送积分" />
        <el-table-column v-if="columns.visible('orders')" prop="orders" label="下单 送积分比例" />
        <el-table-column v-if="columns.visible('storeId')" prop="storeId" label="所属店铺" />
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
        <el-table-column v-if="columns.visible('deleted')" prop="deleted" label="逻辑删除" />
        <el-table-column v-permission="['admin','pointsConsumeSetting:edit','pointsConsumeSetting:del']" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudPointsConsumeSetting from '@/api/pointsConsumeSetting'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '积分消费设置', url: 'api/pointsConsumeSetting', sort: 'id,desc', crudMethod: { ...crudPointsConsumeSetting }})
const defaultForm = { id: null, deductionPerAmount: null, maxPercentPerOrder: null, useUnit: null, couponStatus: null, login: null, register: null, sign: null, orders: null, storeId: null, addTime: null, modifyTime: null, deleted: null }
export default {
  name: 'PointsConsumeSetting',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      permission: {
        add: ['admin', 'pointsConsumeSetting:add'],
        edit: ['admin', 'pointsConsumeSetting:edit'],
        del: ['admin', 'pointsConsumeSetting:del']
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
  methods: {
    // 获取数据前设置好接口地址
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
