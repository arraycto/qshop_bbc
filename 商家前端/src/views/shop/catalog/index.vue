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
          <el-form-item label="类目名称" prop="name">
            <el-input v-model="form.name" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="类目关键字，以JSON数组格式" prop="keywords">
            <el-input v-model="form.keywords" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="类目广告语介绍">
            <el-input v-model="form.descs" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="父类目ID" prop="pid">
            <el-input v-model="form.pid" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="类目图标" prop="iconUrl">
            <el-input v-model="form.iconUrl" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="类目图片" prop="picUrl">
            <el-input v-model="form.picUrl" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="level">
            <el-input v-model="form.level" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="排序">
            <el-input v-model="form.sortOrder" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="创建时间" prop="createTime">
            <el-input v-model="form.createTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="更新时间" prop="modifyTime">
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
        <el-table-column v-if="columns.visible('name')" prop="name" label="类目名称" />
        <el-table-column v-if="columns.visible('keywords')" prop="keywords" label="类目关键字，以JSON数组格式" />
        <el-table-column v-if="columns.visible('descs')" prop="descs" label="类目广告语介绍" />
        <el-table-column v-if="columns.visible('pid')" prop="pid" label="父类目ID" />
        <el-table-column v-if="columns.visible('iconUrl')" prop="iconUrl" label="类目图标" />
        <el-table-column v-if="columns.visible('picUrl')" prop="picUrl" label="类目图片" />
        <el-table-column v-if="columns.visible('level')" prop="level" label="level" />
        <el-table-column v-if="columns.visible('sortOrder')" prop="sortOrder" label="排序" />
        <el-table-column v-if="columns.visible('createTime')" prop="createTime" label="创建时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('modifyTime')" prop="modifyTime" label="更新时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.modifyTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-permission="['admin','catalog:edit','catalog:del']" label="操作" width="150px" align="center">
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
import crudCatalog from '@/api/catalog'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '平台类目', url: 'api/catalog', sort: 'id,desc', crudMethod: { ...crudCatalog }})
const defaultForm = { id: null, name: null, keywords: null, descs: null, pid: null, iconUrl: null, picUrl: null, level: null, sortOrder: null, createTime: null, modifyTime: null, deleted: null }
export default {
  name: 'Catalog',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      permission: {
        add: ['admin', 'catalog:add'],
        edit: ['admin', 'catalog:edit'],
        del: ['admin', 'catalog:del']
      },
      rules: {
        name: [
          { required: true, message: '类目名称不能为空', trigger: 'blur' }
        ],
        keywords: [
          { required: true, message: '类目关键字，以JSON数组格式不能为空', trigger: 'blur' }
        ],
        pid: [
          { required: true, message: '父类目ID不能为空', trigger: 'blur' }
        ],
        iconUrl: [
          { required: true, message: '类目图标不能为空', trigger: 'blur' }
        ],
        picUrl: [
          { required: true, message: '类目图片不能为空', trigger: 'blur' }
        ],
        createTime: [
          { required: true, message: '创建时间不能为空', trigger: 'blur' }
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
