<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="150px">
     <el-form-item label="ID" v-show="false">
        <el-input v-model="form.id" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="联系电话">
        <el-input v-model="form.contactMobile" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="服务电话">
        <el-input v-model="form.servicePhone" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="地址纬度">
        <el-input v-model="form.addressLat" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="地址精度">
        <el-input v-model="form.addressLng" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="联系人名">
        <el-input v-model="form.contactName" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="店主身份证正面">
        <MaterialList v-model="form.ownerIdentityArr1" style="width: 500px" type="image" :num="1" :width="150" :height="150" />
      </el-form-item>
      <el-form-item label="店主身份证反面">
        <MaterialList v-model="form.ownerIdentityArr2" style="width: 500px" type="image" :num="1" :width="150" :height="150" />
      </el-form-item>
      <el-form-item label="logo">
        <el-input v-model="form.logo" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="地址细节">
        <el-input v-model="form.addressDetail" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="店铺名称">
        <el-input v-model="form.name" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="类目">
        <el-input v-model="form.catalogName" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="联系QQ">
        <el-input v-model="form.contactQq" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="支持电话">
        <el-input v-model="form.supportPhone" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="描述">
        <el-input v-model="form.description" style="width: 370px;" />
      </el-form-item>
      <el-form-item label="">
        <el-button type="primary" @click="doSubmit">提交</el-button>
      </el-form-item>

    </el-form>

  </div>
</template>

<script>
import checkPermission from '@/utils/permission'
import initData from '@/mixins/crud'
import { del, add, get } from '@/api/shop'
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
        id: 1,
        contactMobile: '',
        servicePhone: '',
        addressLat: '',
        addressLng: '',
        contactName: '',
        catalogName: '',
        name: '',
        logo: '',
        catalogId: '',
        addressDetail: '',
        contactQq: '',
        supportPhone: '',
        description: '',
        ownerIdentityArr1: [],
        ownerIdentityArr2: [],
        ownerIdentity2: '',
        ownerIdentity1: ''
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
      
      that.form.ownerIdentityArr1 = []
      if (that.form.ownerIdentity1 && that.form.id) {
        that.form.ownerIdentityArr1 = that.form.ownerIdentity1.split(',')
      }

      that.form.ownerIdentityArr2 = []
      if (that.form.ownerIdentity2 && that.form.id) {
        that.form.ownerIdentityArr2 = that.form.ownerIdentity2.split(',')
      }
    })
  },
  watch: {
    'form.ownerIdentityArr1': function(val) {
      if (val) {
        this.form.ownerIdentity1 = val.join(',')
      }
    },
    'form.ownerIdentityArr2': function(val) {
      if (val) {
        this.form.ownerIdentity2 = val.join(',')
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
