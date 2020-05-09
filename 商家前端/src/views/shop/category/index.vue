<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="560px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
          <el-form-item label="商品分类表ID">
            <el-input v-model="form.id" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="店铺id" prop="storeId">
            <el-input v-model="form.storeId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="分类名称" prop="cateName">
            <el-input v-model="form.cateName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="排序">
            <el-input v-model="form.sort" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="图标">
            <el-input v-model="form.pic" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="是否显示">
            <el-input v-model="form.isShow" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="添加时间">
            <el-input v-model="form.addTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="更新时间">
            <el-input v-model="form.modifyTime" style="width: 370px;" />
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
        <el-table-column v-if="columns.visible('id')" prop="id" label="商品分类表ID" />
        <el-table-column v-if="columns.visible('storeId')" prop="storeId" label="店铺id" />
        <el-table-column v-if="columns.visible('cateName')" prop="cateName" label="分类名称" />
        <el-table-column v-if="columns.visible('sort')" prop="sort" label="排序" />
        <el-table-column v-if="columns.visible('pic')" prop="pic" label="图标" />
        <el-table-column v-if="columns.visible('isShow')" prop="isShow" label="是否显示" />
        <el-table-column v-if="columns.visible('addTime')" prop="addTime" label="添加时间" />
        <el-table-column v-if="columns.visible('modifyTime')" prop="modifyTime" label="更新时间" />
        <el-table-column v-permission="['admin','storeCategory:edit','storeCategory:del']" label="操作" width="150px" align="center">
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
import crudStoreCategory from '@/api/storeCategory'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '店铺分类', url: 'api/storeCategory', sort: 'id,desc', crudMethod: { ...crudStoreCategory }})
const defaultForm = { id: null, storeId: null, cateName: null, sort: null, pic: null, isShow: null, addTime: null, modifyTime: null, deleted: null }
export default {
  name: 'StoreCategory',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      permission: {
        add: ['admin', 'storeCategory:add'],
        edit: ['admin', 'storeCategory:edit'],
        del: ['admin', 'storeCategory:del']
      },
      rules: {
        storeId: [
          { required: true, message: '店铺id不能为空', trigger: 'blur' }
        ],
        cateName: [
          { required: true, message: '分类名称不能为空', trigger: 'blur' }
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
