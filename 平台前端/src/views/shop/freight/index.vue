<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column v-if="columns.visible('name')" prop="name" label="名称" />
        <el-table-column v-if="columns.visible('chargeType')" prop="chargeType" label="计费类型" >
          <template slot-scope="scope">
            <div>
                <el-tag v-if="scope.row.chargeType === 0" style="cursor: pointer" :type="''">按重量</el-tag>
                <el-tag v-else style="cursor: pointer" :type=" 'refunding' ">按件数</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('firstWeight')" prop="firstWeight" label="首重kg" />
        <el-table-column v-if="columns.visible('firstFee')" prop="firstFee" label="首费（元）" />
        <el-table-column v-if="columns.visible('continueWeight')" prop="continueWeight" label="后重量" />
        <el-table-column v-if="columns.visible('continmeFee')" prop="continmeFee" label="后费用" />
        <el-table-column v-if="columns.visible('dest')" prop="dest" label="目的地（省、市）" />
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
        <el-table-column v-if="columns.visible('shopName')" prop="shopName" label="所属店铺" />
        <!-- <el-table-column v-permission="['admin','feightTemplate:edit','feightTemplate:del']" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column> -->
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudFeightTemplate from '@/api/feightTemplate'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '订单运费模板', url: 'api/feightTemplate', sort: 'id,desc', crudMethod: { ...crudFeightTemplate }})
const defaultForm = { id: null, name: null, chargeType: null, firstWeight: null, firstFee: null, continueWeight: null, continmeFee: null, dest: null, storeId: null, addTime: null, modifyTime: null, deleted: null }
export default {
  name: 'FeightTemplate',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      permission: {
        add: ['admin', 'feightTemplate:add'],
        edit: ['admin', 'feightTemplate:edit'],
        del: ['admin', 'feightTemplate:del']
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
  created() {
    this.crud.optShow = { add: false, edit: false, del: false, download: true }
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
