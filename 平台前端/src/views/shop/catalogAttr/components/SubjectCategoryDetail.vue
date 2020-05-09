<template> 
  <el-card class="form-container" shadow="never">
    <el-form ref="subjectCategoryFrom" :model="subjectCategory" :rules="rules" size="small" label-width="150px">
          <el-form-item label="属性名称">
            <el-input v-model="subjectCategory.attrName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="属性值">
            <el-input v-model="subjectCategory.inputList" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="父类目ID" prop="pid">
            <!-- <treeselect v-model="subjectCategory.catalogId" :options="catalogs" style="width: 370px;" placeholder="选择上级类目" /> -->
            <el-cascader
            change-on-select
            v-model="subjectCategory.catalogList"
            :options="productCateOptions">
          </el-cascader>
          </el-form-item>
          <el-form-item label="是否规格">
            <el-radio v-model="subjectCategory.isSpec" :label="1">是</el-radio>
            <el-radio v-model="subjectCategory.isSpec" :label="0" style="width: 110px;">否</el-radio>
          </el-form-item>
          <el-form-item label="属性选择类型">
            <el-radio v-model="subjectCategory.selectType" :label="0">唯一</el-radio>
            <el-radio v-model="subjectCategory.selectType" :label="1">单选</el-radio>
            <el-radio v-model="subjectCategory.selectType" :label="2" style="width: 110px;">多选</el-radio>
          </el-form-item>
          <el-form-item label="属性录入方式">
            <el-radio v-model="subjectCategory.inputType" :label="1">手工录入</el-radio>
            <el-radio v-model="subjectCategory.inputType" :label="0" style="width: 110px;">从列表中选取</el-radio>
          </el-form-item>
          <el-form-item label="分类筛选样式">
            <el-radio v-model="subjectCategory.filterType" :label="0">普通</el-radio>
            <el-radio v-model="subjectCategory.filterType" :label="1" style="width: 110px;">颜色</el-radio>
          </el-form-item>
          <el-form-item label="检索类型">
            <el-radio v-model="subjectCategory.searchType" :label="0">不需要进行检索</el-radio>
            <el-radio v-model="subjectCategory.searchType" :label="1" style="width: 110px;">关键字检索</el-radio>
            <el-radio v-model="subjectCategory.searchType" :label="2" style="width: 110px;">范围检索</el-radio>
          </el-form-item>
          <el-form-item label="相同属性产品是否关联">
            <el-radio v-model="subjectCategory.relatedStatus" :label="0">不关联</el-radio>
            <el-radio v-model="subjectCategory.relatedStatus" :label="1" style="width: 110px;">关联</el-radio>
          </el-form-item>
          <el-form-item label="是否支持手动新增">
            <el-radio v-model="subjectCategory.handAddStatus" :label="0">不支持</el-radio>
            <el-radio v-model="subjectCategory.handAddStatus" :label="1" style="width: 110px;">支持</el-radio>
          </el-form-item>
          <el-form-item label="属性图片">
          <MaterialList v-model="picArr" type="image" :num="1" :width="150" :height="150" />
        </el-form-item>
        <el-form-item label="排序">
            <el-input v-model="subjectCategory.sort" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="是否显示">
            <el-radio v-model="subjectCategory.isShow" :label="1">是</el-radio>
            <el-radio v-model="subjectCategory.isShow" :label="0" style="width: 110px;">否</el-radio>
          </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit('subjectCategoryFrom')">提交</el-button>
          <el-button v-if="!isEdit" @click="resetForm('subjectCategoryFrom')">重置</el-button>
        </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {add, getCatalogAttr, edit} from '@/api/catalogAttr'
  import { getCatalogs } from '@/api/catalog'
  import picUpload from '@/components/pic-upload'
  import MaterialList from '@/components/material'
  const defaultSubjectCategory={
    name: ''
  };
  export default {
    name: 'SubjectCategoryDetail',
    components:{ MaterialList },
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        subjectCategory:Object.assign({}, defaultSubjectCategory),
        picArr: [],
        productCateOptions: [],
        rules: {
          name: [
            {required: true, message: '请输入名称', trigger: 'blur'},
            {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
          ]
          ,
          sort: [
            {type: 'number', message: '排序必须为数字'}
          ]
        }
      }
    },
    created() {
      console.log(this.$route.query.id, "created")
      if (this.$route.query.id) {
        getCatalogAttr(this.$route.query.id).then(response => {
          this.subjectCategory = response;
            if (this.subjectCategory.pic != null){
                this.picArr = this.subjectCategory.pic.split(',');
            }
        });
      }else{
        this.subjectCategory = Object.assign({},defaultSubjectCategory);
      }
      this.getProductCateList();
    },
    watch: {
        'iconArr': function(val) {
            if (val) {
              this.subjectCategory.pic = val.join(',')
            }
        }
    },
    methods: {
      getProductCateList() {
        getCatalogs().then(response => {
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
                edit(this.subjectCategory).then(response => {
                  this.$refs[formName].resetFields();
                  this.$message({
                    message: '修改成功',
                    type: 'success',
                    duration:1000
                  });
                  this.$router.back();
                });
              } else {
                add(this.subjectCategory).then(response => {
                  this.$refs[formName].resetFields();
                  this.subjectCategory = Object.assign({},defaultSubjectCategory);
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
        this.subjectCategory = Object.assign({},defaultSubjectCategory);
      }
    }
  }
</script>
<style>
</style>


