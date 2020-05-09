<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="560px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
          <el-form-item label="配置id">
            <el-input v-model="form.id" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="字段名称" prop="menuName">
            <el-input v-model="form.menuName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="默认值">
            <el-input v-model="form.value" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="排序">
            <el-input v-model="form.sort" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="是否隐藏">
            <el-input v-model="form.status" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="所属店铺">
            <el-input v-model="form.storeId" style="width: 370px;" />
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
        <el-table-column v-if="columns.visible('id')" prop="id" label="配置id" />
        <el-table-column v-if="columns.visible('menuName')" prop="menuName" label="字段名称" />
        <el-table-column v-if="columns.visible('value')" prop="value" label="默认值" />
        <el-table-column v-if="columns.visible('sort')" prop="sort" label="排序" />
        <el-table-column v-if="columns.visible('status')" prop="status" label="是否隐藏" />
        <el-table-column v-if="columns.visible('storeId')" prop="storeId" label="所属店铺" />
        <el-table-column v-permission="['admin','storeSystemConfig:edit','storeSystemConfig:del']" label="操作" width="150px" align="center">
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
import crudStoreSystemConfig from '@/api/storeSystemConfig'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '店铺配置', url: 'api/storeSystemConfig', sort: 'id,desc', crudMethod: { ...crudStoreSystemConfig }})
const defaultForm = { id: null, menuName: null, value: null, sort: null, status: null, storeId: null }
export default {
  name: 'StoreSystemConfig',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      permission: {
        add: ['admin', 'storeSystemConfig:add'],
        edit: ['admin', 'storeSystemConfig:edit'],
        del: ['admin', 'storeSystemConfig:del']
      },
      rules: {
        menuName: [
          { required: true, message: '字段名称不能为空', trigger: 'blur' }
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
