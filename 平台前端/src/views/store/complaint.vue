<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="560px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
          <el-form-item label="分销商品id">
            <el-input v-model="form.id" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="会员id" prop="uid">
            <el-input v-model="form.uid" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="投诉原因" prop="reason">
            <el-input v-model="form.reason" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="投诉内容" prop="desc">
            <el-input v-model="form.desc" style="width: 370px;" />
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
        <el-table-column v-if="columns.visible('id')" prop="id" label="分销商品id" />
        <el-table-column v-if="columns.visible('uid')" prop="uid" label="会员id" />
        <el-table-column v-if="columns.visible('reason')" prop="reason" label="投诉原因" />
        <el-table-column v-if="columns.visible('desc')" prop="desc" label="投诉内容" />
        <el-table-column v-if="columns.visible('storeId')" prop="storeId" label="所属店铺" />
        <el-table-column v-permission="['admin','storeComplaint:edit','storeComplaint:del']" label="操作" width="150px" align="center">
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
import crudStoreComplaint from '@/api/storeComplaint'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '会员投诉', url: 'api/storeComplaint', sort: 'id,desc', crudMethod: { ...crudStoreComplaint }})
const defaultForm = { id: null, uid: null, reason: null, desc: null, storeId: null }
export default {
  name: 'StoreComplaint',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      permission: {
        add: ['admin', 'storeComplaint:add'],
        edit: ['admin', 'storeComplaint:edit'],
        del: ['admin', 'storeComplaint:del']
      },
      rules: {
        uid: [
          { required: true, message: '会员id不能为空', trigger: 'blur' }
        ],
        reason: [
          { required: true, message: '投诉原因不能为空', trigger: 'blur' }
        ],
        desc: [
          { required: true, message: '投诉内容不能为空', trigger: 'blur' }
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
