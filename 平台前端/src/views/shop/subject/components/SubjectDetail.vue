<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="subject" :rules="rules" ref="subjectFrom" label-width="150px">

      <el-form-item label="分类：" prop="categoryId">
        <el-select
          v-model="subject.categoryId"
          @change="handlecateChange"
          placeholder="请选择分类">
          <el-option
            v-for="item in cateOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="标题：" prop="title">
        <el-input v-model="subject.title"></el-input>
      </el-form-item>
      <el-form-item label="专题主图：" prop="pic">
        <MaterialList v-model="picAttr" type="image" :num="1" :width="150" :height="150" />
      </el-form-item>

      <el-form-item label="推荐：" prop="recommendStatus">
        <el-radio-group v-model="subject.recommendStatus">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>


      <el-form-item label="画册：">
        <MaterialList v-model="selectProductPics" type="image" :num="6" :width="150" :height="150" />
      </el-form-item>


      <el-form-item label="是否显示：">
        <el-radio-group v-model="subject.showStatus">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="描述：" prop="description">
        <el-input
          :autoSize="true"
          v-model="subject.description"
          type="textarea"
          placeholder="请输入描述"></el-input>
      </el-form-item>

      <el-form-item label="内容：" prop="content">
        <el-tabs v-model="activeHtmlName" type="card">
          <el-tab-pane label="电脑端详情" name="pc">
            <editor :width="595" :height="300" v-model="subject.content" />
          </el-tab-pane>
          <el-tab-pane label="移动端详情" name="mobile">
            <editor :width="595" :height="300" v-model="subject.content" />
          </el-tab-pane>
        </el-tabs>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('subjectFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('subjectFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {add, getCmsSubject, edit} from '@/api/cmsSubject'
  import {getCmsSubjectCategory, del as deleteSubjectCategory} from '@/api/cmsSubjectCategory'
  import picUpload from '@/components/pic-upload'
  import MaterialList from '@/components/material'
  import editor from '../../../components/Editor'
  const defaultSubject={
    name: ''
  };
  export default {
    name: 'SubjectDetail',
    components:{ picUpload, MaterialList, editor },
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        subject:Object.assign({}, defaultSubject),
        picAttr: [],
        categoryName:'',
        selectProductPics: [],
        cateOptions:null,
        activeHtmlName: 'pc',
        rules: {
          name: [
            {required: true, message: '请输入品牌名称', trigger: 'blur'},
            {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
          ],
          logo: [
            {required: true, message: '请输入品牌logo', trigger: 'blur'}
          ],
          sort: [
            {type: 'number', message: '排序必须为数字'}
          ],
        }
      }
    },
    watch: {
      'picAttr': function (val){
          if (val){
            this.subject.pic = val.join(',');
          }
      },
      'selectProductPics': function (val) {
        if (val){
            this.subject.albumPics = val.join(',');
          }
      }
    },
    created() {
      if (this.isEdit) {
        getCmsSubject({id:this.$route.query.id}).then(response => {
          if (response.content){
            this.subject = response.content[0];
            if (this.subject.pic){
                this.picAttr = this.subject.pic.split(',');
            }
            if (this.subject.albumPics){
              this.selectProductPics = this.subject.albumPics.split(',');
            }
          
          }

        });
      }else{
        this.subject = Object.assign({},defaultSubject);
      }
      this.getCateList();
    },
    methods: {
      getCateList() {
        getCmsSubjectCategory({page: 0, size: 100}).then(response => {
          this.cateOptions = [];
        let brandList = response.content;
        for (let i = 0; i < brandList.length; i++) {
          this.cateOptions.push({label: brandList[i].name, value: brandList[i].id});
        }
      });
      },
      handlecateChange(val) {
        let brandName = '';
        for (let i = 0; i < this.cateOptions.length; i++) {
          if (this.cateOptions[i].value === val) {
            brandName = this.cateOptions[i].label;
            break;
          }
        }

        this.categoryName = brandName;
      },
      onSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('是否提交数据', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.subject.categoryName=this.categoryName;
              if (this.isEdit) {
                edit(this.subject).then(response => {
                  this.$refs[formName].resetFields();
                  this.$message({
                    message: '修改成功',
                    type: 'success',
                    duration:1000
                  });
                  this.$router.back();
                });
              } else {
                add(this.subject).then(response => {
                  this.$refs[formName].resetFields();
                  this.subject = Object.assign({},defaultSubject);
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
        this.subject = Object.assign({},defaultSubject);
      },
      handleBrandChange(val) {

      }
    }
  }
</script>
<style>
</style>


