<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="helpCategory" :rules="rules" ref="helpCategoryFrom" label-width="150px">
      <el-form-item label="商品类目：" prop="catalogId">
        <el-cascader
            change-on-select
            v-model="helpCategory.catalogIdList"
            :options="productCateOptions">
          </el-cascader>
      </el-form-item>
      <el-form-item label="品牌名称：" prop="name">
        <el-input v-model="helpCategory.name"></el-input>
      </el-form-item>
      <el-form-item label="首字母：" prop="keywords">
        <el-input v-model="helpCategory.firstLetter"></el-input>
      </el-form-item>
      <el-form-item label="排序：" prop="keywords">
        <el-input v-model="helpCategory.sort"></el-input>
      </el-form-item>
      <el-form-item label="品牌制造商">
        <el-input v-model="helpCategory.factory" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="显示">
        <el-radio-group v-model="helpCategory.showStatus" style="width: 178px">
        <el-radio :label="1">显示</el-radio>
        <el-radio :label="0">隐藏</el-radio>
      </el-radio-group>
      </el-form-item>
      <el-form-item label="品牌logo" prop="iconUrl">
        <MaterialList v-model="iconArr" type="image" :num="1" :width="150" :height="150" />
      </el-form-item>
      <el-form-item label="专区大图" prop="picUrl">
        <MaterialList v-model="picArr" type="image" :num="1" :width="150" :height="150" />
      </el-form-item>
      <el-form-item label="排序">
          <el-input v-model="helpCategory.brandStory" style="width: 370px;" />
        </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('helpCategoryFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('helpCategoryFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {add as createHelpCategory, getBrandInfo, edit as updateHelpCategory} from '@/api/brand'
  import {getCatalogs as getHelpCategory} from '@/api/catalog'
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
          applyAmount: [
            {type: 'number', message: '提现金额必填'}
          ]
        }
      }
    },
    created() {
      if (this.$route.query.id) {
        getBrandInfo(this.$route.query.id).then(response => {
          if (response){
            this.helpCategory = response;
            console.log(this.helpCategory, "getBrandInfo")
            if (this.helpCategory.bigPic){
              this.picArr = this.helpCategory.bigPic.split(',')
            }
            if (this.helpCategory.logo){
              this.iconArr = this.helpCategory.logo.split(',')
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
        this.helpCategory.bigPic = val.join(',')
      },
      iconArr: function(val) {
        this.helpCategory.logo = val.join(',')
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


