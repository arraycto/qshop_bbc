<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="560px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
          <el-form-item label="红包编号">
            <el-input v-model="form.id" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="发红包的用户id" prop="userId">
            <el-input v-model="form.userId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="红包金额" prop="amount">
            <el-input v-model="form.amount" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="发红包日期">
            <el-input v-model="form.sendDate" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="红包总数" prop="total">
            <el-input v-model="form.total" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="单个红包的金额">
            <el-input v-model="form.unitAmount" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="红包剩余个数">
            <el-input v-model="form.stock" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="红包类型" prop="type">
            <el-input v-model="form.type" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="备注">
            <el-input v-model="form.note" style="width: 370px;" />
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
        <el-table-column v-if="columns.visible('id')" prop="id" label="红包编号" />
        <el-table-column v-if="columns.visible('userId')" prop="userId" label="发红包的用户id" />
        <el-table-column v-if="columns.visible('amount')" prop="amount" label="红包金额" />
        <el-table-column v-if="columns.visible('sendDate')" prop="sendDate" label="发红包日期">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.sendDate) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('total')" prop="total" label="红包总数" />
        <el-table-column v-if="columns.visible('unitAmount')" prop="unitAmount" label="单个红包的金额" />
        <el-table-column v-if="columns.visible('stock')" prop="stock" label="红包剩余个数" />
        <el-table-column v-if="columns.visible('type')" prop="type" label="红包类型" />
        <el-table-column v-if="columns.visible('note')" prop="note" label="备注" />
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
        <el-table-column v-permission="['admin','redPacket:edit','redPacket:del']" label="操作" width="150px" align="center">
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
import crudRedPacket from '@/api/redPacket'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '红包', url: 'api/redPacket', sort: 'id,desc', crudMethod: { ...crudRedPacket }})
const defaultForm = { id: null, userId: null, amount: null, sendDate: null, total: null, unitAmount: null, stock: null, type: null, note: null, storeId: null, addTime: null, modifyTime: null, deleted: null }
export default {
  name: 'RedPacket',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      permission: {
        add: ['admin', 'redPacket:add'],
        edit: ['admin', 'redPacket:edit'],
        del: ['admin', 'redPacket:del']
      },
      rules: {
        userId: [
          { required: true, message: '发红包的用户id不能为空', trigger: 'blur' }
        ],
        amount: [
          { required: true, message: '红包金额不能为空', trigger: 'blur' }
        ],
        total: [
          { required: true, message: '红包总数不能为空', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '红包类型不能为空', trigger: 'blur' }
        ],
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
