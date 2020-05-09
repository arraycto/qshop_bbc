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
          <el-form-item label="注册类型">
            <el-input v-model="form.registerType" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="到期时间">
            <el-input v-model="form.expireTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="尝试时间">
            <el-input v-model="form.tryTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="联系电话">
            <el-input v-model="form.contactMobile" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="地区省">
            <el-input v-model="form.addressProvince" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="是否选中">
            <el-input v-model="form.isChecked" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="服务电话">
            <el-input v-model="form.servicePhone" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="地址名">
            <el-input v-model="form.addressLat" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="联系人名">
            <el-input v-model="form.contactName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="删除时间">
            <el-input v-model="form.deleteTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="isStar">
            <el-input v-model="form.isStar" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="尝试">
            <el-input v-model="form.isTry" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="图标">
            <el-input v-model="form.logo" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="地址细节">
            <el-input v-model="form.addressDetail" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="name">
            <el-input v-model="form.name" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="1 通过 2 审核中 3 拒绝">
            <el-input v-model="form.status" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="uid">
            <el-input v-model="form.uid" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="联系QQ">
            <el-input v-model="form.contactQq" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="addressLng">
            <el-input v-model="form.addressLng" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="lastLoginTime">
            <el-input v-model="form.lastLoginTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="地址区域">
            <el-input v-model="form.addressArea" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="contactQrcode">
            <el-input v-model="form.contactQrcode" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="描述">
            <el-input v-model="form.description" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="地址城市">
            <el-input v-model="form.addressCity" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="collect">
            <el-input v-model="form.collect" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="hit">
            <el-input v-model="form.hit" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="goodsCount">
            <el-input v-model="form.goodsCount" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="memberCount">
            <el-input v-model="form.memberCount" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="orderCount">
            <el-input v-model="form.orderCount" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="payAmount">
            <el-input v-model="form.payAmount" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="精品店铺标识,0:否，1:是" prop="isBoutique">
            <el-input v-model="form.isBoutique" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="添加时间">
            <el-input v-model="form.addTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="更新时间">
            <el-input v-model="form.modifyTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="逻辑删除" prop="deleted">
            <el-input v-model="form.deleted" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="类目id" prop="catalogId">
            <el-input v-model="form.catalogId" style="width: 370px;" />
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
        <el-table-column v-if="columns.visible('registerType')" prop="registerType" label="注册类型" />
        <el-table-column v-if="columns.visible('expireTime')" prop="expireTime" label="到期时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.expireTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('tryTime')" prop="tryTime" label="尝试时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.tryTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('contactMobile')" prop="contactMobile" label="联系电话" />
        <el-table-column v-if="columns.visible('addressProvince')" prop="addressProvince" label="地区省" />
        <el-table-column v-if="columns.visible('isChecked')" prop="isChecked" label="是否选中" />
        <el-table-column v-if="columns.visible('servicePhone')" prop="servicePhone" label="服务电话" />
        <el-table-column v-if="columns.visible('addressLat')" prop="addressLat" label="地址名" />
        <el-table-column v-if="columns.visible('contactName')" prop="contactName" label="联系人名" />
        <el-table-column v-if="columns.visible('deleteTime')" prop="deleteTime" label="删除时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.deleteTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('isStar')" prop="isStar" label="isStar" />
        <el-table-column v-if="columns.visible('isTry')" prop="isTry" label="尝试" />
        <el-table-column v-if="columns.visible('logo')" prop="logo" label="图标" />
        <el-table-column v-if="columns.visible('addressDetail')" prop="addressDetail" label="地址细节" />
        <el-table-column v-if="columns.visible('name')" prop="name" label="name" />
        <el-table-column v-if="columns.visible('status')" prop="status" label="1 通过 2 审核中 3 拒绝" />
        <el-table-column v-if="columns.visible('uid')" prop="uid" label="uid" />
        <el-table-column v-if="columns.visible('contactQq')" prop="contactQq" label="联系QQ" />
        <el-table-column v-if="columns.visible('addressLng')" prop="addressLng" label="addressLng" />
        <el-table-column v-if="columns.visible('lastLoginTime')" prop="lastLoginTime" label="lastLoginTime">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.lastLoginTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('addressArea')" prop="addressArea" label="地址区域" />
        <el-table-column v-if="columns.visible('contactQrcode')" prop="contactQrcode" label="contactQrcode" />
        <el-table-column v-if="columns.visible('description')" prop="description" label="描述" />
        <el-table-column v-if="columns.visible('addressCity')" prop="addressCity" label="地址城市" />
        <el-table-column v-if="columns.visible('collect')" prop="collect" label="collect" />
        <el-table-column v-if="columns.visible('hit')" prop="hit" label="hit" />
        <el-table-column v-if="columns.visible('goodsCount')" prop="goodsCount" label="goodsCount" />
        <el-table-column v-if="columns.visible('memberCount')" prop="memberCount" label="memberCount" />
        <el-table-column v-if="columns.visible('orderCount')" prop="orderCount" label="orderCount" />
        <el-table-column v-if="columns.visible('payAmount')" prop="payAmount" label="payAmount" />
        <el-table-column v-if="columns.visible('isBoutique')" prop="isBoutique" label="精品店铺标识,0:否，1:是" />
        <el-table-column v-if="columns.visible('addTime')" prop="addTime" label="添加时间" />
        <el-table-column v-if="columns.visible('modifyTime')" prop="modifyTime" label="更新时间" />
        <el-table-column v-if="columns.visible('deleted')" prop="deleted" label="逻辑删除" />
        <el-table-column v-if="columns.visible('catalogId')" prop="catalogId" label="类目id" />
        <el-table-column v-permission="['admin','store:edit','store:del']" label="操作" width="150px" align="center">
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
import crudStore from '@/api/store'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '店铺', url: 'api/store', sort: 'id,desc', crudMethod: { ...crudStore }})
const defaultForm = { id: null, registerType: null, expireTime: null, tryTime: null, contactMobile: null, addressProvince: null, isChecked: null, servicePhone: null, addressLat: null, contactName: null, deleteTime: null, isStar: null, isTry: null, logo: null, addressDetail: null, name: null, status: null, uid: null, contactQq: null, addressLng: null, lastLoginTime: null, addressArea: null, contactQrcode: null, description: null, addressCity: null, collect: null, hit: null, goodsCount: null, memberCount: null, orderCount: null, payAmount: null, isBoutique: null, addTime: null, modifyTime: null, deleted: null, catalogId: null }
export default {
  name: 'Store',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      permission: {
        add: ['admin', 'store:add'],
        edit: ['admin', 'store:edit'],
        del: ['admin', 'store:del']
      },
      rules: {
        isBoutique: [
          { required: true, message: '精品店铺标识,0:否，1:是不能为空', trigger: 'blur' }
        ],
        deleted: [
          { required: true, message: '逻辑删除不能为空', trigger: 'blur' }
        ],
        catalogId: [
          { required: true, message: '类目id不能为空', trigger: 'blur' }
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
