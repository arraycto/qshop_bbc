<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="560px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
          <el-form-item label="id" v-show="false">
            <el-input v-model="form.id" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="退货类型">
            <el-input v-model="form.name" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="排序">
            <el-input v-model="form.sort" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="状态">
            <el-radio v-model="form.status" :label="1">启用</el-radio>
            <el-radio v-model="form.status" :label="0" style="width: 110px;">不启用</el-radio>
          </el-form-item>
          <el-form-item label="所属店铺">
            <treeselect v-model="form.storeId" :options="depts" style="width: 370px;" placeholder="选择店铺" />
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
        <el-table-column v-if="columns.visible('name')" prop="name" label="退货类型" />
        <el-table-column v-if="columns.visible('sort')" prop="sort" label="sort" />
        <el-table-column v-if="columns.visible('status')" prop="status" label="状态" >
          <template slot-scope="scope">
          <div>
              <el-tag v-if="scope.row.status === 0" style="cursor: pointer" :type="''">未启用</el-tag>
              <el-tag v-else style="cursor: pointer" :type=" 'using' ">启用</el-tag>
            </div>
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
        <el-table-column v-permission="['admin','storeOrderReturnReason:edit','storeOrderReturnReason:del']" label="操作" width="150px" align="center">
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
import crudStoreOrderReturnReason from '@/api/storeOrderReturnReason'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import crudShop from '@/api/shop'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '商家退货原因表', url: 'api/storeOrderReturnReason', sort: 'id,desc', crudMethod: { ...crudStoreOrderReturnReason, crudShop }})
const defaultForm = { id: null, name: null, sort: null, status: null, storeId: null, addTime: null, modifyTime: null, deleted: null }
export default {
  name: 'StoreOrderReturnReason',
  components: { pagination, crudOperation, rrOperation, udOperation, Treeselect },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      depts: [],
      permission: {
        add: ['admin', 'storeOrderReturnReason:add'],
        edit: ['admin', 'storeOrderReturnReason:edit'],
        del: ['admin', 'storeOrderReturnReason:del']
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
    },
    // 新增与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
      // 获取所有部门
      crudShop.getAllShopList({ isShow: true }).then(res => {
        this.depts = []
        const dept = { id: 0, label: '顶级店铺', children: [] }
        dept.children = res.content
        this.depts.push(dept)
      })
    },
    // 提交前的验证
    [CRUD.HOOK.afterValidateCU]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
