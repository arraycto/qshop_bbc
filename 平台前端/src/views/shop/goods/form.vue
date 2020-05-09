<template>
  <el-dialog :append-to-body="true" :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialog" :title="'审核'" width="900px">
    <el-form ref="form" :model="form" :inline="true" :rules="rules" size="small" label-width="100px">
      <el-form-item label="商品Id" style="display:none;">
        <el-input v-model="form.productId" style="width: 500px;" />
      </el-form-item>
      <el-form-item label="审核状态">
        <el-radio-group v-model="form.status" style="width: 178px">
        <el-radio :label="1">审核通过</el-radio>
        <el-radio :label="0">审核不通过</el-radio>
      </el-radio-group>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import curdProductRecord from '@/api/productVertifyRecord'
import editor from '../../components/Editor'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

export default {
  components: { editor, Treeselect },
  props: {
    isAdd: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      loading: false, 
      dialog: false,
      form: {
        id: '',
        productId: 0,
        status: 0,
      },
      rules: {
      }
    }
  },
  created() {
      let _this = this;
      curdProductRecord.getProductRecord(this.form.productId).then(res => {
        if (res.content.length>0){
          _this.form.status = res.content[0].status;
        }
      })
  },
  methods: {
    cancel() {
      this.resetForm()
    },
    doSubmit() {
      this.loading = true
      this.doAdd()
    },
    doAdd() {
      curdProductRecord.add(this.form).then(res => {
        this.resetForm()
        this.$notify({
          title: '审核成功',
          type: 'success',
          duration: 2500
        })
        this.loading = false
        this.$parent.init()
      }).catch(err => {
        this.loading = false
      })
    },
    resetForm() {
      this.dialog = false
      this.$refs['form'].resetFields()
      this.form = {
        id: '',
        productId: 0
      }
    }
  }
}
</script>

<style scoped>

</style>
