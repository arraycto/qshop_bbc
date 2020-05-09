<template>
  <div style="margin-top: 50px">
    <el-form :model="value" :rules="rules" ref="productInfoForm" label-width="120px" style="width: 600px" size="small">
      <!-- <el-form-item label="平台商品分类：" prop="cateId">
        <el-cascader
          change-on-select
          v-model="value.categoryList"
          :options="productCateOptions">
        </el-cascader>
      </el-form-item> -->
      <el-form-item label="店铺分类：" prop="storeCateId">
        <el-cascader
          change-on-select
          v-model="value.storeCategoryList"
          :options="storeCateOptions">
        </el-cascader>
      </el-form-item>
      <el-form-item label="商品名称：" prop="productName">
        <el-input v-model="value.productName"></el-input>
      </el-form-item>
      <!-- <el-form-item label="副标题：" prop="subTitle">
        <el-input v-model="value.subTitle"></el-input>
      </el-form-item> -->
      <el-form-item label="商品品牌：" prop="brandId">
        <el-select
          v-model="value.brandId"
          @change="handleBrandChange"
          placeholder="请选择品牌">
          <el-option
            v-for="item in brandOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="商品介绍：">
        <el-input
          :autoSize="true"
          v-model="value.storeInfo"
          type="textarea"
          placeholder="请输入内容"></el-input>
      </el-form-item>
      <el-form-item label="商品货号：" prop="barCode">
        <el-input v-model="value.barCode"></el-input>
      </el-form-item>
      <el-form-item label="商品售价：" prop="price">
        <el-input v-model="value.price"></el-input>
      </el-form-item>
      <el-form-item label="市场价：" prop="otPrice">
        <el-input v-model="value.otPrice"></el-input>
      </el-form-item>
      <el-form-item label="成本价：">
        <el-input v-model="value.cost"></el-input>
      </el-form-item>
      <el-form-item label="商品库存：" prop="stock">
        <el-input v-model="value.stock"></el-input>
      </el-form-item>
      <el-form-item label="计量单位：">
        <el-select
                v-model="value.unitName"

                placeholder="请选择计量单位">
          <el-option
                  v-for="item in unitOptions"
                  :key="item.id"
                  :label="item.id"
                  :value="item.id">
          </el-option>
        </el-select>

      </el-form-item>
      <el-form-item label="商品重量：" prop="weight">
        <el-input v-model="value.weight" style="width: 300px"></el-input>
        <span style="margin-left: 20px">克</span>
      </el-form-item>
      <el-form-item label="固定运费：">
        <el-input v-model="value.postage" style="width: 300px"></el-input>
        <span style="margin-left: 20px">优先于运费模版</span>
      </el-form-item>
      <el-form-item label="运费模版:：">
         <el-select
              v-model="value.feightTemplateId"
              placeholder="请选择运费模版">
        <el-option
                v-for="item in feightTemplateOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
        </el-option>
      </el-select>

      </el-form-item>

      <el-form-item label="排序">
        <el-input v-model="value.sort"></el-input>
      </el-form-item>
      <el-form-item style="text-align: center">
        <el-button type="primary" size="medium" @click="handleNext('productInfoForm')">下一步，填写商品促销</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import {getPlatformCates} from '@/api/category'
  import {getCates} from '@/api/storeCategory'
  import {getPlatformBrands} from '@/api/brand'
  import {getFeightTemplate} from '@/api/feightTemplate';
  import {getProduct} from '@/api/storeProduct';

  export default {
    name: "ProductInfoDetail",
    props: {
      value: Object,
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        hasEditCreated:false,
        //选中商品分类的值
        selectProductCateValue: [],
        selectStoreCateValue: [],
        productCateOptions: [],
        storeCateOptions: [],
        //选中商品分类的值
        selectAreaValue: [],
        areaOptions: [],
        brandOptions: [],
        feightTemplateOptions:[],
        unitOptions:[{
          id: "个",
          name: "个",
        },
          {
            id: "箱",
            name: "箱",
          },
          {
            id: "条",
            name: "条",
          },

          {
            id: "盒",
            name: "盒",
          },{
            id: "包",
            name: "包",
          },{
            id: "斤",
            name: "斤",
          },{
            id: "两",
            name: "两",
          },{
            id: "件",
            name: "件",
          },{
            id: "瓶",
            name: "瓶",
          },{
            id: "千克",
            name: "千克",
          }],
        rules: {
          productName: [
            {required: true, message: '请输入商品名称', trigger: 'blur'},
            {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
          ],
          storeCateId: [{required: true, message: '请选择店铺分类', trigger: 'blur'}],
          brandId: [{required: true, message: '请选择商品品牌', trigger: 'blur'}],
          barCode: [{required: true, message: '请填写商品编码', trigger: 'blur'}],
          price: [{required: true, message: '请输入商品售价', trigger: 'blur'}],
          otPrice: [{required: true, message: '请输入商品市场价', trigger: 'blur'}],
          stock: [{required: true, message: '请输入商品库存', trigger: 'blur'}],
          weight: [{required: true, message: '请输入商品重量', trigger: 'blur'}]
        }
      };
    },
    created() {
      this.getProductCateList();
      this.getStoreCateList();
      this.getBrandList();
      this.getFeightTemplateList();
    },
    computed:{
      //商品的编号
      productId(){
        return this.value.id;
      }
    },
    watch: {
      productId:function(newValue){
        if(!this.isEdit)return;
        if(this.hasEditCreated)return;
        if(newValue===undefined||newValue==null||newValue===0)return;
        this.handleEditCreated();
      },
      'value.categoryList': function (newValue) {
        if (newValue != null) {
          this.value.cateId = newValue[newValue.length-1 >0?newValue.length-1:0];
          this.value.productCategoryName= this.getCateNameById(this.value.cateId);
        } else {
          this.value.cateId = null;
          this.value.productCategoryName=null;
        }
      },
      'value.storeCategoryList': function (newValue) {
        if (newValue != null) {
          this.value.storeCateId = newValue[newValue.length-1 >0?newValue.length-1:0];
          this.value.storeCateName= this.getStoreCateNameById(this.value.storeCateId);
        } else {
          this.value.storeCateId = null;
          this.value.storeCateName=null;
        }
      },
      'value.feightTemplateId': function (newValue){
        if (newValue!=null){
          this.value.feightTemplateId = newValue;
        }
      } 
    /*  selectAreaValue: [],
      areaOptions: [],*/
    },
    methods: {
      //处理编辑逻辑
      handleEditCreated(){
        if(this.value.cateId!=null){

          this.selectProductCateValue.push(this.value.cateParentId);
          this.selectProductCateValue.push(this.value.cateId);
        }
        if(this.value.storeCateId!=null){

          this.selectStoreCateValue.push(this.value.storeCateParentId);
          this.selectStoreCateValue.push(this.value.storeCateId);
        }
        this.hasEditCreated=true;
      },
      getProductCateList() {
        getPlatformCates().then(response => {
          let list = response.content;
          this.productCateOptions = [];
          for (let i = 0; i < list.length; i++) {
            let children = [];
            if (list[i].children != null && list[i].children.length > 0) {
              for (let j = 0; j < list[i].children.length; j++) {
                children.push({label: list[i].children[j].cateName, value: list[i].children[j].id});
              }
            }
            this.productCateOptions.push({label: list[i].cateName, value: list[i].id, children: children});
          }
        });
      },
      getStoreCateList() {
        getCates().then(response => {
          let list = response.content;
          this.storeCateOptions = [];
          for (let i = 0; i < list.length; i++) {
            let children = [];
            if (list[i].children != null && list[i].children.length > 0) {
              for (let j = 0; j < list[i].children.length; j++) {
                children.push({label: list[i].children[j].cateName, value: list[i].children[j].id});
              }
            }
            this.storeCateOptions.push({label: list[i].cateName, value: list[i].id, children: children});
          }
        });
      },
      getFeightTemplateList() {
        getFeightTemplate({pageNum: 1, pageSize: 100}).then(response => {
                  this.feightTemplateOptions = [];
                  let feightTemplateList = response.content;
                  for (let i = 0; i < feightTemplateList.length; i++) {
                    this.feightTemplateOptions.push({label: feightTemplateList[i].name, value: feightTemplateList[i].id});
                  }
                });
  },
      getBrandList() {
        getPlatformBrands({pageNum: 1, pageSize: 100}).then(response => {
          this.brandOptions = [];
          let brandList = response.content;
          for (let i = 0; i < brandList.length; i++) {
            this.brandOptions.push({label: brandList[i].name, value: brandList[i].id});
          }
        });
      },
       getAreaById(id){
        let name=null;
        for(let i=0;i<this.areaOptions.length;i++){
          for(let j=0;i<this.areaOptions[i].children.length;j++){
            if( this.areaOptions[i].children[j].value!=undefined && this.areaOptions[i].children[j].value===id){
              name=this.areaOptions[i].children[j].label;
              return name;
            }
          }
        }
        return name;
      },
      getCateNameById(id){
        let name=null;
        for(let i=0;i<this.productCateOptions.length;i++){
          for(let j=0;j<this.productCateOptions[i].children.length;j++){
            if( this.productCateOptions[i].children[j].value!=undefined && this.productCateOptions[i].children[j].value===id){
              name=this.productCateOptions[i].children[j].label;
              return name;
            }
          }
        }
        
        return name;
      },
      getStoreCateNameById(id){
        let name=null;
        for(let i=0;i<this.storeCateOptions.length;i++){
          for(let j=0;j<this.storeCateOptions[i].children.length;j++){
            if( this.storeCateOptions[i].children[j].value!=undefined && this.storeCateOptions[i].children[j].value===id){
              name=this.storeCateOptions[i].children[j].label;
              return name;
            }
          }
        }
        
        return name;
      },
      handleNext(formName){
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$emit('nextStep');
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
      handleBrandChange(val) {
        let brandName = '';
        for (let i = 0; i < this.brandOptions.length; i++) {
          if (this.brandOptions[i].value === val) {
            brandName = this.brandOptions[i].label;
            break;
          }
        }
        this.value.brandName = brandName;
      }
    }
  }
</script>

<style scoped>
</style>