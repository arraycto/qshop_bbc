<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="560px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
          <el-form-item label="编号">
            <el-input v-model="form.id" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="有效期(天)">
            <el-input v-model="form.validDay" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="最小积分">
            <el-input v-model="form.minIntegration" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="最大积分">
            <el-input v-model="form.maxIntegration" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="备注">
            <el-input v-model="form.remark" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="创建时间">
            <el-input v-model="form.createTime" style="width: 370px;" />
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
          <el-form-item label="count">
            <el-input v-model="form.count" style="width: 370px;" />
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
        <el-table-column v-if="columns.visible('id')" prop="id" label="编号" />
        <el-table-column v-if="columns.visible('validDay')" prop="validDay" label="有效期(天)" />
        <el-table-column v-if="columns.visible('minIntegration')" prop="minIntegration" label="最小积分" />
        <el-table-column v-if="columns.visible('maxIntegration')" prop="maxIntegration" label="最大积分" />
        <el-table-column v-if="columns.visible('remark')" prop="remark" label="备注" />
        <el-table-column v-if="columns.visible('createTime')" prop="createTime" label="创建时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
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
        <el-table-column v-if="columns.visible('count')" prop="count" label="count" />
        <el-table-column v-permission="['admin','pointsCoupon:edit','pointsCoupon:del']" label="操作" width="150px" align="center">
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
import crudPointsCoupon from '@/api/pointsCoupon'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '积分券', url: 'api/pointsCoupon', sort: 'id,desc', crudMethod: { ...crudPointsCoupon }})
const defaultForm = { id: null, validDay: null, minIntegration: null, maxIntegration: null, remark: null, createTime: null, storeId: null, addTime: null, modifyTime: null, deleted: null, count: null }
export default {
  name: 'PointsCoupon',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      permission: {
        add: ['admin', 'pointsCoupon:add'],
        edit: ['admin', 'pointsCoupon:edit'],
        del: ['admin', 'pointsCoupon:del']
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
