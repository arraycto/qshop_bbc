<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="560px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
          <el-form-item label="属性名称">
            <el-input v-model="form.attrName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="属性值">
            <el-input v-model="form.attrValue" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="分类ID" prop="category">
            <treeselect v-model="form.category" :options="depts" style="width: 370px;" placeholder="选择上级分类" />
          </el-form-item>
          <el-form-item label="是否显示">
            <el-radio v-model="form.isShow" :label="1">是</el-radio>
            <el-radio v-model="form.isShow" :label="0" style="width: 110px;">否</el-radio>
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
        <el-table-column v-if="columns.visible('id')" prop="id" label="序号" />
        <el-table-column v-if="columns.visible('attrName')" prop="attrName" label="属性名称" />
        <el-table-column v-if="columns.visible('attrValue')" prop="attrValue" label="属性值" />
        <el-table-column v-if="columns.visible('category')" prop="category" label="分类ID" />
        <el-table-column v-if="columns.visible('isShow')" prop="isShow" label="是否显示" />
        <el-table-column v-if="columns.visible('createTime')" prop="createTime" label="创建时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.addTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('modifyTime')" prop="modifyTime" label="更新时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.modifyTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-permission="['admin','categoryAttr:edit','categoryAttr:del']" label="操作" width="150px" align="center">
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
import crudDept from '@/api/category'
import crudCategoryAttr from '@/api/categoryAttr'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '平台分类属性', url: 'api/categoryAttr', sort: 'id,desc', crudMethod: { ...crudCategoryAttr,crudDept }})
const defaultForm = { id: null, addTime: null, attrName: null, attrValue: null, category: null, deleted: null, isShow: null, modifyTime: null, storeId: null }
export default {
  name: 'CategoryAttr',
  components: { Treeselect, pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      depts: [],
      permission: {
        add: ['admin', 'categoryAttr:add'],
        edit: ['admin', 'categoryAttr:edit'],
        del: ['admin', 'categoryAttr:del']
      },
      rules: {
        category: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        deleted: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        storeId: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ]
      }    }
  },
  methods: {
    // 新增与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
      this.picArr = []
      if (form.pic && form.id) {
        this.picArr = form.pic.split(',')
      }

      // 获取所有部门
      crudDept.getCates({ isShow: true }).then(res => {
        this.depts = []
        const dept = { id: 0, label: '顶级分类', children: [] }
        dept.children = res.content
        this.depts.push(dept)
      })
    },
    // 获取数据前设置好接口地址
    [CRUD.HOOK.beforeRefresh]() {
      return true
    },
    // 提交前的验证
    [CRUD.HOOK.afterValidateCU]() {
      return true
    },
    checkboxT(row, rowIndex) {
      return row.id !== 1
    }
  }
}
</script>

<style scoped>

</style>
