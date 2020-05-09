<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="helpCategory" :rules="rules" ref="helpCategoryFrom" label-width="150px">
      <el-form-item label="提现金额：" prop="applyAmount">
        <el-input v-model.number="helpCategory.applyAmount"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('helpCategoryFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('helpCategoryFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {add as createHelpCategory, getStoreWithdraw as getHelpCategory, edit as updateHelpCategory} from '@/api/storeWithdraw'
  const defaultHelpCategory={
    name: ''
  };
  export default {
    name: 'StoreWithdrawDetail',
    components:{},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        helpCategory:Object.assign({}, defaultHelpCategory),
        rules: {
          applyAmount: [
            {type: 'number', message: '提现金额必填'}
          ]
        }
      }
    },
    created() {
      if (this.$route.query.id) {
        getHelpCategory({id:this.$route.query.id}).then(response => {
          if (response.content){
            this.helpCategory = response.content[0];
          }
        });
      }else{
        this.helpCategory = Object.assign({},defaultHelpCategory);
      }
    },
    methods: {
      onSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('是否提交数据', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              if (this.isEdit) {
                this.helpCategory.applyStatus = 0;
                updateHelpCategory(this.helpCategory).then(response => {
                  this.$refs[formName].resetFields();
                  this.$message({
                    message: '修改成功',
                    type: 'success',
                    duration:1000
                  });
                  this.$router.back();
                });
              } else {
                createHelpCategory(this.helpCategory).then(response => {
                  this.$refs[formName].resetFields();
                  this.helpCategory = Object.assign({},defaultHelpCategory);
                  this.$message({
                    message: '提交成功',
                    type: 'success',
                    duration:1000
                  });
                  this.$router.back();
                });
              }
            });

          } else {
            this.$message({
              message: '验证失败',
              type: 'error',
              duration:1000
            });
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
        this.helpCategory = Object.assign({},defaultHelpCategory);
      }
    }
  }
</script>
<style>
</style>


