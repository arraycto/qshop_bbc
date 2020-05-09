<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="560px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
          <el-form-item label="名称">
            <el-input v-model="form.name" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="轮播位置">
            <el-radio-group v-model="form.type" style="width: 178px">
              <el-radio :label="2">h5、小程序</el-radio>
              <el-radio :label="1">app首页轮播</el-radio>
              <el-radio :label="0">PC首页轮播</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="轮播图">
            <MaterialList v-model="picArr" type="image" :num="3" :width="150" :height="150" />
          </el-form-item>
          <el-form-item label="开始时间">
            <el-date-picker
              class="input-width"
              v-model="form.startTime"
              value-format="yyyy-MM-dd"
              type="date"
              placeholder="请选择时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="结束时间">
            <el-date-picker
              class="input-width"
              v-model="form.endTime"
              value-format="yyyy-MM-dd"
              type="date"
              placeholder="请选择时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="上下线状态">
            <el-radio-group v-model="form.status" style="width: 178px">
              <el-radio :label="1">上线</el-radio>
              <el-radio :label="0">下线</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="链接地址">
            <el-input v-model="form.url" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="备注">
            <el-input v-model="form.note" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="排序">
            <el-input v-model="form.sort" style="width: 370px;" />
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
        <el-table-column v-if="columns.visible('id')" prop="id" label="编号" />
        <el-table-column v-if="columns.visible('name')" prop="name" label="名称" />
        <el-table-column v-if="columns.visible('type')" prop="type" label="轮播位置">
          <template slot-scope="scope">
            <div>
                <el-tag v-if="scope.row.type === 0" style="cursor: pointer" :type="''">PC首页轮播</el-tag>
                <el-tag v-else-if="scope.row.type === 1" style="cursor: pointer" :type=" 'app' ">app首页轮播</el-tag>
                <el-tag v-else  style="cursor: pointer" :type=" 'h5' ">h5、小程序</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('pic')" prop="pic" label="轮播图"  width="55">
          <template slot-scope="scope">
            <a v-for="(item,index) in handlePic(scope.row.pic)" :key="index" :href="item" style="color: #42b983" target="_blank">
              <img :src="item" alt="点击打开" class="el-avatar">
            </a>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('startTime')" prop="startTime" label="开始时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.startTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('endTime')" prop="endTime" label="结束时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.endTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('status')" prop="status" label="上下线状态">
          <template slot-scope="scope">
            <div>
                <el-tag v-if="scope.row.type === 0" style="cursor: pointer" :type="''">下线</el-tag>
                <el-tag v-else  style="cursor: pointer" :type=" 'online' ">上线</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('clickCount')" prop="clickCount" label="点击数" />
        <el-table-column v-if="columns.visible('orderCount')" prop="orderCount" label="下单数" />
        <el-table-column v-if="columns.visible('url')" prop="url" label="链接地址" />
        <el-table-column v-if="columns.visible('note')" prop="note" label="备注" />
        <el-table-column v-if="columns.visible('sort')" prop="sort" label="排序" />
        <el-table-column v-if="columns.visible('storeId')" prop="storeId" label="广告所属店铺" />
        <el-table-column v-permission="['admin','homeAdvertise:edit','homeAdvertise:del']" label="操作" width="150px" align="center">
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
import crudHomeAdvertise from '@/api/homeAdvertise'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import picUpload from '@/components/pic-upload'
import MaterialList from '@/components/material'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '广告表', url: 'api/homeAdvertise', sort: 'id,desc', crudMethod: { ...crudHomeAdvertise }})
const defaultForm = { id: null, name: null, type: null, pic: null, startTime: null, endTime: null, status: null, clickCount: null, orderCount: null, url: null, note: null, sort: null, storeId: null }
export default {
  name: 'HomeAdvertise',
  components: { pagination, crudOperation, rrOperation, udOperation, picUpload, MaterialList },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      pic1: "",
      picArr: [],
      permission: {
        add: ['admin', 'homeAdvertise:add'],
        edit: ['admin', 'homeAdvertise:edit'],
        del: ['admin', 'homeAdvertise:del']
      },
      rules: {
        storeId: [
          { required: true, message: '广告所属店铺不能为空', trigger: 'blur' }
        ]
      }    }
  },
  watch: {
    picArr: function(val) {
      this.form.pic = val.join(',')
    }
  },
  methods: {
    handlePic(pics) {
      return pics.split(',')
    },
    // 新增与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
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
