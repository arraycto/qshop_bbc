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
          <el-form-item label="分类">
            <el-input v-model="form.categoryId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="标题">
            <el-input v-model="form.title" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="专题主图">
            <el-input v-model="form.pic" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="关联产品数量">
            <el-input v-model="form.productCount" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="推荐">
            <el-input v-model="form.recommendStatus" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="创建时间">
            <el-input v-model="form.createTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="收藏量">
            <el-input v-model="form.collectCount" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="点击量">
            <el-input v-model="form.readCount" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="评论量">
            <el-input v-model="form.commentCount" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="画册图片用逗号分割">
            <el-input v-model="form.albumPics" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="描述">
            <el-input v-model="form.description" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="显示状态：0->不显示；1->显示">
            <el-input v-model="form.showStatus" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="内容">
            <el-input v-model="form.content" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="转发数">
            <el-input v-model="form.forwardCount" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="专题分类名称">
            <el-input v-model="form.categoryName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="memberId">
            <el-input v-model="form.memberId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="打赏">
            <el-input v-model="form.reward" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="areaName">
            <el-input v-model="form.areaName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="schoolName">
            <el-input v-model="form.schoolName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="memberName">
            <el-input v-model="form.memberName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="videoSrc">
            <el-input v-model="form.videoSrc" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="type">
            <el-input v-model="form.type" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="所属店铺">
            <el-input v-model="form.storeId" style="width: 370px;" />
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
        <el-table-column v-if="columns.visible('id')" prop="id" label="id" />
        <el-table-column v-if="columns.visible('categoryId')" prop="categoryId" label="分类" />
        <el-table-column v-if="columns.visible('title')" prop="title" label="标题" />
        <el-table-column v-if="columns.visible('pic')" prop="pic" label="专题主图" />
        <el-table-column v-if="columns.visible('productCount')" prop="productCount" label="关联产品数量" />
        <el-table-column v-if="columns.visible('recommendStatus')" prop="recommendStatus" label="推荐" />
        <el-table-column v-if="columns.visible('createTime')" prop="createTime" label="创建时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('collectCount')" prop="collectCount" label="收藏量" />
        <el-table-column v-if="columns.visible('readCount')" prop="readCount" label="点击量" />
        <el-table-column v-if="columns.visible('commentCount')" prop="commentCount" label="评论量" />
        <el-table-column v-if="columns.visible('albumPics')" prop="albumPics" label="画册图片用逗号分割" />
        <el-table-column v-if="columns.visible('description')" prop="description" label="描述" />
        <el-table-column v-if="columns.visible('showStatus')" prop="showStatus" label="显示状态：0->不显示；1->显示" />
        <el-table-column v-if="columns.visible('content')" prop="content" label="内容" />
        <el-table-column v-if="columns.visible('forwardCount')" prop="forwardCount" label="转发数" />
        <el-table-column v-if="columns.visible('categoryName')" prop="categoryName" label="专题分类名称" />
        <el-table-column v-if="columns.visible('memberId')" prop="memberId" label="memberId" />
        <el-table-column v-if="columns.visible('reward')" prop="reward" label="打赏" />
        <el-table-column v-if="columns.visible('areaName')" prop="areaName" label="areaName" />
        <el-table-column v-if="columns.visible('schoolName')" prop="schoolName" label="schoolName" />
        <el-table-column v-if="columns.visible('memberName')" prop="memberName" label="memberName" />
        <el-table-column v-if="columns.visible('videoSrc')" prop="videoSrc" label="videoSrc" />
        <el-table-column v-if="columns.visible('type')" prop="type" label="type" />
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
        <el-table-column v-if="columns.visible('deleted')" prop="deleted" label="逻辑删除" />
        <el-table-column v-permission="['admin','cmsSubject:edit','cmsSubject:del']" label="操作" width="150px" align="center">
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
import crudCmsSubject from '@/api/cmsSubject'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '专题表', url: 'api/cmsSubject', sort: 'id,desc', crudMethod: { ...crudCmsSubject }})
const defaultForm = { id: null, categoryId: null, title: null, pic: null, productCount: null, recommendStatus: null, createTime: null, collectCount: null, readCount: null, commentCount: null, albumPics: null, description: null, showStatus: null, content: null, forwardCount: null, categoryName: null, memberId: null, reward: null, areaName: null, schoolName: null, memberName: null, videoSrc: null, type: null, storeId: null, addTime: null, modifyTime: null, deleted: null }
export default {
  name: 'CmsSubject',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      permission: {
        add: ['admin', 'cmsSubject:add'],
        edit: ['admin', 'cmsSubject:edit'],
        del: ['admin', 'cmsSubject:del']
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
    }
  }
}
</script>

<style scoped>

</style>
