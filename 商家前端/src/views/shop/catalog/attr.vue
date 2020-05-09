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
          <el-form-item label="attrName">
            <el-input v-model="form.attrName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="属性数量">
            <el-input v-model="form.attrValue" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="是否规格">
            <el-input v-model="form.isSpec" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="pic">
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
        <el-table-column v-if="columns.visible('id')" prop="id" label="id" />
        <el-table-column v-if="columns.visible('attrName')" prop="attrName" label="attrName" />
        <el-table-column v-if="columns.visible('attrValue')" prop="attrValue" label="属性数量" />
        <el-table-column v-if="columns.visible('isSpec')" prop="isSpec" label="是否规格" />
        <el-table-column v-if="columns.visible('pic')" prop="pic" label="pic" />
        <el-table-column v-if="columns.visible('isShow')" prop="isShow" label="是否显示" />
        <el-table-column v-if="columns.visible('addTime')" prop="addTime" label="添加时间" />
        <el-table-column v-if="columns.visible('modifyTime')" prop="modifyTime" label="更新时间" />
        <el-table-column v-permission="['admin','catalogAttr:edit','catalogAttr:del']" label="操作" width="150px" align="center">
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
import crudCatalogAttr from '@/api/catalogAttr'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '平台类目属性', url: 'api/catalogAttr', sort: 'id,desc', crudMethod: { ...crudCatalogAttr }})
const defaultForm = { id: null, attrName: null, attrValue: null, isSpec: null, pic: null, isShow: null, addTime: null, modifyTime: null, deleted: null }
export default {
  name: 'CatalogAttr',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      permission: {
        add: ['admin', 'catalogAttr:add'],
        edit: ['admin', 'catalogAttr:edit'],
        del: ['admin', 'catalogAttr:del']
      },
      rules: {
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
