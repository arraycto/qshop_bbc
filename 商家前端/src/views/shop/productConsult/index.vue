<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="560px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
          <el-form-item label="咨询编号">
            <el-input v-model="form.id" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="商品编号">
            <el-input v-model="form.productId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="商品名称">
            <el-input v-model="form.productName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="咨询发布者会员编号(0：游客)" prop="uid">
            <el-input v-model="form.uid" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="会员名称">
            <el-input v-model="form.memberName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="店铺编号">
            <el-input v-model="form.storeId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="咨询发布者邮箱">
            <el-input v-model="form.email" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="咨询内容">
            <el-input v-model="form.consultContent" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="咨询添加时间">
            <el-input v-model="form.consultAddtime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="咨询回复内容">
            <el-input v-model="form.consultReply" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="咨询回复时间">
            <el-input v-model="form.consultReplyTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="0表示不匿名 1表示匿名">
            <el-input v-model="form.isanonymous" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="isDel">
            <el-input v-model="form.isDel" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="pic">
            <el-input v-model="form.pic" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="attr">
            <el-input v-model="form.attr" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="stars">
            <el-input v-model="form.stars" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="oid">
            <el-input v-model="form.oid" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="1 商品 2 订单">
            <el-input v-model="form.type" style="width: 370px;" />
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
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column v-if="columns.visible('id')" prop="id" label="咨询编号" />
        <el-table-column v-if="columns.visible('productId')" prop="productId" label="商品编号" />
        <el-table-column v-if="columns.visible('productName')" prop="productName" label="商品名称" />
        <el-table-column v-if="columns.visible('uid')" prop="uid" label="咨询发布者会员编号(0：游客)" />
        <el-table-column v-if="columns.visible('memberName')" prop="memberName" label="会员名称" />
        <el-table-column v-if="columns.visible('storeId')" prop="storeId" label="店铺编号" />
        <el-table-column v-if="columns.visible('email')" prop="email" label="咨询发布者邮箱" />
        <el-table-column v-if="columns.visible('consultContent')" prop="consultContent" label="咨询内容" />
        <el-table-column v-if="columns.visible('consultAddtime')" prop="consultAddtime" label="咨询添加时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.consultAddtime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('consultReply')" prop="consultReply" label="咨询回复内容" />
        <el-table-column v-if="columns.visible('consultReplyTime')" prop="consultReplyTime" label="咨询回复时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.consultReplyTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('isanonymous')" prop="isanonymous" label="0表示不匿名 1表示匿名" />
        <el-table-column v-if="columns.visible('isDel')" prop="isDel" label="isDel" />
        <el-table-column v-if="columns.visible('pic')" prop="pic" label="pic" />
        <el-table-column v-if="columns.visible('attr')" prop="attr" label="attr" />
        <el-table-column v-if="columns.visible('stars')" prop="stars" label="stars" />
        <el-table-column v-if="columns.visible('oid')" prop="oid" label="oid" />
        <el-table-column v-if="columns.visible('type')" prop="type" label="1 商品 2 订单" />
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
        <el-table-column v-permission="['admin','storeProductConsult:edit','storeProductConsult:del']" label="操作" width="150px" align="center">
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
import crudStoreProductConsult from '@/api/storeProductConsult'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '商品资讯', url: 'api/storeProductConsult', sort: 'id,desc', crudMethod: { ...crudStoreProductConsult }})
const defaultForm = { id: null, productId: null, productName: null, uid: null, memberName: null, storeId: null, email: null, consultContent: null, consultAddtime: null, consultReply: null, consultReplyTime: null, isanonymous: null, isDel: null, pic: null, attr: null, stars: null, oid: null, type: null, addTime: null, modifyTime: null, deleted: null }
export default {
  name: 'StoreProductConsult',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      permission: {
        add: ['admin', 'storeProductConsult:add'],
        edit: ['admin', 'storeProductConsult:edit'],
        del: ['admin', 'storeProductConsult:del']
      },
      rules: {
        uid: [
          { required: true, message: '咨询发布者会员编号(0：游客)不能为空', trigger: 'blur' }
        ],
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
