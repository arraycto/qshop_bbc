<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="helpCategory" :rules="rules" ref="helpCategoryFrom" label-width="150px">
      <el-form-item label="类目名称：" prop="name">
        <el-input v-model="helpCategory.name"></el-input>
      </el-form-item>
      <el-form-item label="类目关键字：" prop="keywords">
        <el-input v-model="helpCategory.keywords"></el-input>
      </el-form-item>
      <el-form-item label="类目广告语介绍：" prop="keywords">
        <el-input v-model="helpCategory.descs"></el-input>
      </el-form-item>
      <el-form-item label="父类目ID：" prop="pid">
        <el-cascader
            change-on-select
            v-model="helpCategory.catalogIdList"
            :options="productCateOptions">
          </el-cascader>
      </el-form-item>
      <el-form-item label="类目图标" prop="iconUrl">
        <MaterialList v-model="iconArr" type="image" :num="1" :width="150" :height="150" />
      </el-form-item>
      <el-form-item label="类目图片" prop="picUrl">
        <MaterialList v-model="picArr" type="image" :num="1" :width="150" :height="150" />
      </el-form-item>
      <el-form-item label="佣金率：" prop="keywords">
        <el-input v-model="helpCategory.chargeRate"></el-input>
      </el-form-item>
      <el-form-item label="层级">
        <el-input v-model="helpCategory.level" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="排序">
          <el-input v-model="helpCategory.sortOrder" style="width: 370px;" />
        </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('helpCategoryFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('helpCategoryFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {add as createHelpCategory, getCatalogs as getHelpCategory, getCatalogInfo, edit as updateHelpCategory} from '@/api/catalog'
  import picUpload from '@/components/pic-upload'
  import MaterialList from '@/components/material'
  const defaultHelpCategory={
    name: ''
  };
  export default {
    name: 'StoreWithdrawDetail',
    components:{picUpload,MaterialList},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        helpCategory:Object.assign({}, defaultHelpCategory),
        productCateOptions: [],
        picArr: [],
        iconArr: [],
        rules: {
          name: [
            {required: true, message: '分类名称必填'}
          ],
          keywords: [
            {required: true, message: 'keywords必填'}
          ],
          iconArr: [
            {required: true, message: '请选择分类图标'}
          ],
          picArr: [
            {required: true, message: '请选择分类图片'}
          ]
        }
      }
    },
    created() {
      if (this.$route.query.id) {
        getCatalogInfo(this.$route.query.id).then(response => {
          if (response){
            this.helpCategory = response;
            if (this.helpCategory.picUrl){
              this.picArr = this.helpCategory.picUrl.split(',')
            }
            if (this.helpCategory.iconUrl){
              this.iconArr = this.helpCategory.iconUrl.split(',')
            }
          }
        });
      }else{
        this.helpCategory = Object.assign({},defaultHelpCategory);
      }
      this.getProductCateList();
    },
    watch: {
      picArr: function(val) {
        this.helpCategory.picUrl = val.join(',')
      },
      iconArr: function(val) {
        this.helpCategory.iconUrl = val.join(',')
      }
    },
    methods: {
      getProductCateList() {
        getHelpCategory().then(response => {
          let list = response.content;
          this.productCateOptions = [];
          for (let i = 0; i < list.length; i++) {
            let children = [];
            if (list[i].children != null && list[i].children.length > 0) {
              for (let j = 0; j < list[i].children.length; j++) {
                children.push({label: list[i].children[j].name, value: list[i].children[j].id});
              }
            }
            this.productCateOptions.push({label: list[i].name, value: list[i].id, children: children});
          }
        });
      },
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


