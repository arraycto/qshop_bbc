<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="560px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
          <el-form-item label="所属专题">
            <el-select v-model="form.subjectId">
            <el-option
                v-for="type in catalogs"
                :key="type.id"
                :label="type.title"
                :value="type.id"
            />
            </el-select>
          </el-form-item>
          <el-form-item label="用户名">
            <el-input v-model="form.memberNickName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="用户图标">
            <MaterialList v-model="memberIconAttr" type="image" :num="1" :width="150" :height="150" />
          </el-form-item>
          <el-form-item label="内容">
            <el-input v-model="form.content" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="状态">
            <el-radio-group v-model="form.showStatus" style="width: 178px">
                <el-radio :label="1">显示</el-radio>
                <el-radio :label="0">隐藏</el-radio>
            </el-radio-group>
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
        <el-table-column v-if="columns.visible('subjectId')" prop="subjectId" label="所属专题" />
        <el-table-column v-if="columns.visible('memberNickName')" prop="memberNickName" label="用户名" />
        <el-table-column v-if="columns.visible('memberIcon')" prop="memberIcon" label="用户图标">
            <template slot-scope="scope">
                <a :href="scope.row.memberIcon" style="color: #42b983" target="_blank"><img :src="scope.row.memberIcon" alt="点击打开" class="el-avatar"></a>
            </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('content')" prop="content" label="内容" />
        <el-table-column v-if="columns.visible('showStatus')" prop="showStatus" label="状态">
            <template slot-scope="scope">
            <div>
                <el-tag v-if="scope.row.showStatus === 0" style="cursor: pointer" :type="''">隐藏</el-tag>
                <el-tag v-else  style="cursor: pointer" :type=" 'h5' ">显示</el-tag>
            </div>
          </template>
        </el-table-column>
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
        <el-table-column v-permission="['admin','cmsSubjectComment:edit','cmsSubjectComment:del']" label="操作" width="150px" align="center">
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
import crudCmsSubjectComment from '@/api/cmsSubjectComment'
import curdSubject from '@/api/cmsSubject'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import picUpload from '@/components/pic-upload'
import MaterialList from '@/components/material'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '专题评论表', url: 'api/cmsSubjectComment', sort: 'id,desc', crudMethod: { ...crudCmsSubjectComment }})
const defaultForm = { id: null, subjectId: null, memberNickName: null, memberIcon: null, content: null, createTime: null, showStatus: null, storeId: null, addTime: null, modifyTime: null, deleted: null }
export default {
  name: 'CmsSubjectComment',
  components: { pagination, crudOperation, rrOperation, udOperation, picUpload, MaterialList },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
        memberIconAttr: [],
        catalogs: [],
      permission: {
        add: ['admin', 'cmsSubjectComment:add'],
        edit: ['admin', 'cmsSubjectComment:edit'],
        del: ['admin', 'cmsSubjectComment:del']
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
      'memberIconAttr': function (val) {
          if (val) {
              this.form.memberIcon = val.join(',')
          }
      }
  },
  methods: {
    [CRUD.HOOK.afterToCU](crud, form) {
      this.memberIconAttr = []
      if (form.memberIcon && form.id) {
        this.memberIconAttr = form.memberIcon.split(',')
      }

      // 获取所有类目
      curdSubject.getCmsSubject({ }).then(res => {
        this.catalogs = res.content
        console.log(this.catalogs)
      })
    },
    // 获取数据前设置好接口地址
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
