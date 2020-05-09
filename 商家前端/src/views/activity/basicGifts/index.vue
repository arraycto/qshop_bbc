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
          <el-form-item label="name">
            <el-input v-model="form.name" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="1 有效2 无效">
            <el-input v-model="form.status" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="活动对象1全部用户2 会员级别">
            <el-input v-model="form.activiUser" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="活动商品">
            <el-input v-model="form.activiGoods" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="1 首购礼 2 满 购礼 3 单品礼赠">
            <el-input v-model="form.bigType" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="首购礼 1第一单获取 2所有订单获取 ； 满购礼1选赠礼 获取规则 2满赠礼">
            <el-input v-model="form.smallType" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="规则">
            <el-input v-model="form.rules" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="部分商品列表">
            <el-input v-model="form.goodsIds" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="会员级别">
            <el-input v-model="form.userLevel" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="startTime">
            <el-input v-model="form.startTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="endTime">
            <el-input v-model="form.endTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="赠品">
            <el-input v-model="form.giftIds" style="width: 370px;" />
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
          <el-form-item label="note">
            <el-input v-model="form.note" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="所属店铺" prop="storeId">
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
        <el-table-column v-if="columns.visible('id')" prop="id" label="id" />
        <el-table-column v-if="columns.visible('name')" prop="name" label="name" />
        <el-table-column v-if="columns.visible('status')" prop="status" label="1 有效2 无效" />
        <el-table-column v-if="columns.visible('activiUser')" prop="activiUser" label="活动对象1全部用户2 会员级别" />
        <el-table-column v-if="columns.visible('activiGoods')" prop="activiGoods" label="活动商品" />
        <el-table-column v-if="columns.visible('bigType')" prop="bigType" label="1 首购礼 2 满 购礼 3 单品礼赠" />
        <el-table-column v-if="columns.visible('smallType')" prop="smallType" label="首购礼 1第一单获取 2所有订单获取 ； 满购礼1选赠礼 获取规则 2满赠礼" />
        <el-table-column v-if="columns.visible('rules')" prop="rules" label="规则" />
        <el-table-column v-if="columns.visible('goodsIds')" prop="goodsIds" label="部分商品列表" />
        <el-table-column v-if="columns.visible('userLevel')" prop="userLevel" label="会员级别" />
        <el-table-column v-if="columns.visible('startTime')" prop="startTime" label="startTime">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.startTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('endTime')" prop="endTime" label="endTime">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.endTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('giftIds')" prop="giftIds" label="赠品" />
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
        <el-table-column v-if="columns.visible('note')" prop="note" label="note" />
        <el-table-column v-if="columns.visible('storeId')" prop="storeId" label="所属店铺" />
        <el-table-column v-permission="['admin','basicGifts:edit','basicGifts:del']" label="操作" width="150px" align="center">
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
import crudBasicGifts from '@/api/basicGifts'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '基础营销赠品', url: 'api/basicGifts', sort: 'storeId,desc', crudMethod: { ...crudBasicGifts }})
const defaultForm = { id: null, name: null, status: null, activiUser: null, activiGoods: null, bigType: null, smallType: null, rules: null, goodsIds: null, userLevel: null, startTime: null, endTime: null, giftIds: null, addTime: null, modifyTime: null, deleted: null, note: null, storeId: null }
export default {
  name: 'BasicGifts',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      permission: {
        add: ['admin', 'basicGifts:add'],
        edit: ['admin', 'basicGifts:edit'],
        del: ['admin', 'basicGifts:del']
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
        ],
        storeId: [
          { required: true, message: '所属店铺不能为空', trigger: 'blur' }
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
