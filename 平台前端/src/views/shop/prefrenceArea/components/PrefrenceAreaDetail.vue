<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="prefrenceArea" :rules="rules" ref="prefrenceAreaFrom" label-width="150px">

      <el-form-item label="标题：" prop="name">
        <el-input v-model="prefrenceArea.name"></el-input>
      </el-form-item>
      <el-form-item label="子标题：" prop="subTitle">
        <el-input v-model="prefrenceArea.subTitle"></el-input>
      </el-form-item>
      <el-form-item label="展示图片：" prop="pic">
        <MaterialList v-model="picAttr" type="image" :num="1" :width="150" :height="150" />
      </el-form-item>
      <el-form-item label="排序：" prop="sort">
        <el-input-number v-model="prefrenceArea.sort"></el-input-number>
      </el-form-item>
      <el-form-item label="状态：" prop="showStatus">
        <el-radio-group v-model="prefrenceArea.showStatus">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('prefrenceAreaFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('prefrenceAreaFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {add, getPrefrenceArea, edit} from '@/api/cmsPrefrenceArea'
  import picUpload from '@/components/pic-upload'
  import MaterialList from '@/components/material'
  const defaultPrefrenceArea={
    name: ''
  };
  export default {
    name: 'PrefrenceAreaDetail',
    components:{picUpload, MaterialList},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        picAttr: [],
        prefrenceArea:Object.assign({}, defaultPrefrenceArea),
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
            this.prefrenceArea.pic = val.join(',');
          }
      }
    },
    created() {
      if (this.isEdit) {
        getPrefrenceArea({id:this.$route.query.id}).then(response => {
          if (response.content){
            this.prefrenceArea = response.content[0];
            if (this.prefrenceArea.pic){
                this.picAttr = this.prefrenceArea.pic.split(',');
            }
          }else {
            this.prefrenceArea.sort = 1;
          }
        });
      }else{
        this.prefrenceArea = Object.assign({},defaultPrefrenceArea);
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
                edit(this.prefrenceArea).then(response => {
                  this.$refs[formName].resetFields();
                  this.$message({
                    message: '修改成功',
                    type: 'success',
                    duration:1000
                  });
                  this.$router.back();
                });
              } else {
                add(this.prefrenceArea).then(response => {
                  this.$refs[formName].resetFields();
                  this.prefrenceArea = Object.assign({},defaultPrefrenceArea);
                  this.$message({
                    message: '提交成功',
                    type: 'success',
                    duration:1000
                  });
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
        this.prefrenceArea = Object.assign({},defaultPrefrenceArea);
      }
    }
  }
</script>
<style>
</style>


