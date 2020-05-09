<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="150px">
     <el-form-item label="ID" v-show="false">
        <el-input v-model="form.id" style="width: 370px;" readonly="true"/>
      </el-form-item>
      <el-form-item label="公司名称">
        <el-input v-model="form.companyName" style="width: 370px;" readonly="true"/>
      </el-form-item>
      <el-form-item label="营业执照注册号">
        <el-input v-model="form.licenseNo" style="width: 370px;" readonly="true"/>
      </el-form-item>
      <el-form-item label="法定代表人姓名">
        <el-input v-model="form.representative" style="width: 370px;" readonly="true"/>
      </el-form-item>
      <el-form-item label="法定代表人身份证号">
        <el-input v-model="form.idNo" style="width: 370px;" readonly="true"/>
      </el-form-item>
      <el-form-item label="店主身份证正面">
        <MaterialList v-model="form.representIdentity1Arr"  readonly="true" style="width: 500px" type="image" :num="1" :width="150" :height="150" />
      </el-form-item>
      <el-form-item label="店主身份证反面">
        <MaterialList v-model="form.representIdentity2Arr" readonly="true" style="width: 500px" type="image" :num="1" :width="150" :height="150" />
      </el-form-item>
       <el-form-item label="营业执照开始时间">
        <el-input v-model="form.licenseStartTime" readonly="true" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="营业执照结束时间">
        <el-input v-model="form.licenseEndTime"  readonly="true" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="公司所在地">
        <el-input v-model="form.area" readonly="true" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="公司详细地址">
        <el-input v-model="form.addressDetail" readonly="true" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="公司联系电话">
        <el-input v-model="form.tel" readonly="true" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="公司联系人手机">
        <el-input v-model="form.phone" readonly="true" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="注册资本">
        <el-input v-model="form.capital" readonly="true" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="公司官网">
        <el-input v-model="form.websiteUrl" readonly="true"  style="width: 370px;" />
      </el-form-item>
      <!-- <el-form-item label="">
        <el-button type="primary" @click="doSubmit">提交</el-button>
      </el-form-item> -->

    </el-form>

  </div>
</template>

<script>
import checkPermission from '@/utils/permission'
import initData from '@/mixins/crud'
import { del, add, get } from '@/api/shopSettle'
import MaterialList from '@/components/material'
import picUpload from '@/components/pic-upload'
import { Message } from 'element-ui'

export default {
  components: { Message, picUpload, MaterialList },
  mixins: [initData],
  data() {
    return {
      delLoading: false,
      form: {
        id: '',
        companyName: '',
        licenseNo: '',
        representative: '',
        idNo: '',
        representIdentity1: '',
        representIdentity2: '',
        licenseStartTime: '',
        licenseEndTime: '',
        area: '',
        addressDetail: '',
        tel: '',
        contact: '',
        phone: '',
        capital: '',
        websiteUrl: '',
        representIdentity1Arr: [],
        representIdentity2Arr: []
      },
      rules: {
      }
    }
  },
  created() {
    get().then(rese => {
      const that = this
      for (var key in rese.content[0]){
        if(key in that.form){
          that.form[key] = rese.content[0][key]
        }
      }

      that.form.representIdentity1Arr = []
      if (that.form.representIdentity1 && that.form.id) {
        that.form.representIdentity1Arr = that.form.representIdentity1.split(',')
      }

      that.form.representIdentity2Arr = []
      if (that.form.representIdentity2 && that.form.id) {
        that.form.representIdentity2Arr = that.form.representIdentity2.split(',')
      }
    })
  },
  watch: {
    'form.representIdentity1Arr': function(val) {
      if (val) {
        this.form.representIdentity1 = val.join(',')
      }
    },
    'form.representIdentity2Arr': function(val) {
      if (val) {
        this.form.representIdentity2 = val.join(',')
      }
    }
  },
  methods: {
    checkPermission,
    doSubmit() {
      add(this.form).then(res => {
        Message({ message: '设置成功', type: 'success' })
      }).catch(err => {
        // this.loading = false
        console.log(err.response.data.message)
      })
    }

  }
}
</script>

<style scoped>

</style>
