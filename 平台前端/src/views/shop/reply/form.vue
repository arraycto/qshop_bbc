<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="isAdd ? '新增' : '编辑'" width="700px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
      <el-form-item label="评论内容">
        {{form.comment}}
      </el-form-item>
        <!-- <el-input v-model="form.comment" :disabled="true" style="width: 370px;" /> -->
      <el-form-item label="评论图片">
        <!-- <el-input v-model="form.pics" disabled="true" style="width: 370px;" /> -->
        <MaterialList v-model="picsArr" style="width: 500px" type="image" :num="3" :width="150" :height="150" />
      </el-form-item>
      <el-form-item label="评论时间">  
          {{parseTime(form.addTime)}}
      </el-form-item>
      <el-form-item label="管理员回复内容">
        <textarea v-model="form.merchantReplyContent" style="width: 370px;" rows="3" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { add, edit } from '@/api/storeProductReply'
import { parseTime } from '@/utils/index'
import MaterialList from '@/components/material'
export default {
  components: {parseTime, MaterialList},
  props: {
    isAdd: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      picsArr: [],
      loading: false, dialog: false,
      form: {
        id: '',
        comment: '',
        pics: '',
        addTime: '',
        merchantReplyContent: '',
        isRely: 0
      },
      rules: {
      }
    }
  },
  created() {
    if (this.form != null && this.form != undefined)
      this.picsArr = this.form.pics.split(",")
    console.log(this.$refs, "created")
  },
  watch: {
    'picsArr': function(val) {
      if (val != null && val != ""){
        this.form.pics = val.join(',');
      }
        
    }
  },
  methods: {
    parseTime,
    cancel() {
      this.resetForm()
    },
    doSubmit() {
      this.loading = true
      if (this.isAdd) {
        this.doAdd()
      } else this.doEdit()
    },
    doAdd() {
      add(this.form).then(res => {
        this.resetForm()
        this.$notify({
          title: '添加成功',
          type: 'success',
          duration: 2500
        })
        this.loading = false
        this.$parent.init()
      }).catch(err => {
        this.loading = false
        console.log(err)
      })
    },
    doEdit() {
      this.form.isReply = 1;
      edit(this.form).then(res => {
        this.resetForm()
        this.$notify({
          title: '修改成功',
          type: 'success',
          duration: 2500
        })
        this.loading = false
        this.$parent.init()
      }).catch(err => {
        this.loading = false
        console.log(err.response.data.message)
      })
    },
    resetForm() {
      this.dialog = false
      this.$refs['form'].resetFields()
      this.form = {
        id: '',
        comment: '',
        pics: '',
        addTime: '',
        merchantReplyContent: ''
      }
    }
  }
}
</script>

<style scoped>

</style>
