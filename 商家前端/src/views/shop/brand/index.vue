<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="560px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
          <el-form-item label="品牌名称">
            <el-input v-model="form.name" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="首字母">
            <el-input v-model="form.firstLetter" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="排序">
            <el-input v-model="form.sort" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="品牌制造商">
            <el-input v-model="form.factory" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="显示">
            <el-radio-group v-model="form.showStatus" style="width: 178px">
            <el-radio :label="1">显示</el-radio>
            <el-radio :label="0">隐藏</el-radio>
          </el-radio-group>
          </el-form-item>
          <el-form-item label="品牌logo">
            <MaterialList v-model="logoArr" type="image" :num="1" :width="150" :height="150" />
          </el-form-item>
          <el-form-item label="专区大图">
            <MaterialList v-model="picArr" type="image" :num="1" :width="150" :height="150" />
          </el-form-item>
          <el-form-item label="品牌故事">
            <el-input v-model="form.brandStory" rows="3" type="textarea" />
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
        <el-table-column v-if="columns.visible('name')" prop="name" label="品牌名称" />
        <el-table-column v-if="columns.visible('firstLetter')" prop="firstLetter" label="首字母" />
        <el-table-column v-if="columns.visible('sort')" prop="sort" label="排序" />
        <el-table-column v-if="columns.visible('factoryStatus')" prop="factoryStatus" label="品牌制造商" />
        <el-table-column v-if="columns.visible('showStatus')" prop="showStatus" label="显示" />
        <el-table-column v-if="columns.visible('productCount')" prop="productCount" label="产品数量" />
        <el-table-column ref="table" prop="logo" label="品牌logo">
          <template slot-scope="scope">
            <a :href="scope.row.logo" style="color: #42b983" target="_blank"><img :src="scope.row.logo" alt="点击打开" class="el-avatar"></a>
          </template>
        </el-table-column>
        <el-table-column ref="table" prop="bigPic" label="专区大图">
          <template slot-scope="scope">
            <a :href="scope.row.bigPic" style="color: #42b983" target="_blank"><img :src="scope.row.bigPic" alt="点击打开" class="el-avatar"></a>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('brandStory')" prop="brandStory" label="品牌故事" />
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
        <el-table-column v-permission="['admin','storeBrand:edit','storeBrand:del']" label="操作" width="150px" align="center">
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
import crudStoreBrand from '@/api/storeBrand'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import picUpload from '@/components/pic-upload'
import MaterialList from '@/components/material'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '品牌包', url: 'api/storeBrand', sort: 'id,desc', crudMethod: { ...crudStoreBrand }})
const defaultForm = { id: null, name: null, firstLetter: null, sort: null, factoryStatus: null, showStatus: null, productCount: null, logo: null, bigPic: null, brandStory: null, storeId: null, addTime: null, modifyTime: null, deleted: null }
export default {
  name: 'StoreBrand',
  components: { pagination, crudOperation, rrOperation, udOperation, picUpload, MaterialList },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      logoArr: [],
      picArr: [],
      permission: {
        add: ['admin', 'storeBrand:add'],
        edit: ['admin', 'storeBrand:edit'],
        del: ['admin', 'storeBrand:del']
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
  watch: {
    logoArr: function(val) {
      this.form.logo = val.join(',')
    },
    picArr: function(val) {
      this.form.bigPic = val.join(',')
    }
  },
  methods: {
    // 新增与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
      this.picArr = []
      if (form.bigPic && form.id) {
        this.picArr = form.bigPic.split(',')
      }

      this.logoArr = []
      if (form.logo && form.id) {
        this.logoArr = form.logo.split(',')
      }
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
