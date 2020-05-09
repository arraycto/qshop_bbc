<template>
  <div style="margin-top: 50px">
    <el-form :model="value" ref="productAttrForm" label-width="150px" style="width: 100%;" size="small">
      <el-form-item label="商品类目：">
        <el-cascader v-model="value.categoryList"
                   change-on-select
                   @change="handleProductAttrChange"
                   :options="productAttributeCategoryOptions">
        </el-cascader>
      </el-form-item>
      <el-form-item label="商品规格：">
        <el-card shadow="never" class="cardBg">
          <div v-for="(productAttr,idx) in selectProductAttr" :key="idx">
            {{productAttr.name}}：
            <el-checkbox-group v-if="productAttr.handAddStatus===0" v-model="selectProductAttr[idx].values">
              <el-checkbox v-for="item in getInputListArr(productAttr.inputList)" :label="item" :key="item"
                           class="littleMarginLeft"></el-checkbox>
            </el-checkbox-group>
            <div v-else>
              <el-checkbox-group v-model="selectProductAttr[idx].values">
                <div v-for="(item,index) in selectProductAttr[idx].options" :key="index" style="display: inline-block"
                     class="littleMarginLeft">
                  <el-checkbox :label="item" :key="item"></el-checkbox>
                  <el-button type="text" class="littleMarginLeft" @click="handleRemoveProductAttrValue(idx,index)">删除
                  </el-button>
                </div>
              </el-checkbox-group>
              <el-input v-model="addProductAttrValue" style="width: 160px;margin-left: 10px" clearable></el-input>
              <el-button class="littleMarginLeft" @click="handleAddProductAttrValue(idx)">增加</el-button>
            </div>
          </div>
        </el-card>
        <el-table style="width: 100%;margin-top: 20px"
                  :data="value.skuStockList"
                  border>
          <el-table-column
                  v-for="(item,index) in selectProductAttr"
                  :label="item.name"
                  :key="item.id"
                  align="center">
            <template slot-scope="scope">
              {{getProductSkuSp(scope.row,index)}}
            </template>
          </el-table-column>
          <el-table-column
                  label="销售价格"
                  width="80"
                  align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.price"></el-input>
            </template>
          </el-table-column>
          <el-table-column
                  label="商品库存"
                  width="80"
                  align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.stock"></el-input>
            </template>
          </el-table-column>
          <el-table-column
                  label="商品销量"
                  width="80"
                  align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.sales"></el-input>
            </template>
          </el-table-column>
          <el-table-column
                  label="商品成本"
                  width="80"
                  align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.cost"></el-input>
            </template>
          </el-table-column>
          <el-table-column
                  label="SKU编号"
                  align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.barCode"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="属性图片：" align="left">
            <template slot-scope="scope">
                <MaterialList v-model="scope.row.pic" type="image" :num="1" :width="150" :height="150" />
            </template>
          </el-table-column>
          <el-table-column
                  label="操作"
                  width="80"
                  align="center">
            <template slot-scope="scope">
              <el-button
                      type="text"
                      @click="handleRemoveProductSku(scope.$index, scope.row)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-button
                type="primary"
                style="margin-top: 20px"
                @click="handleRefreshProductSkuList">刷新列表
        </el-button>
        <el-button
                type="primary"
                style="margin-top: 20px"
                @click="handleSyncProductSkuPrice">同步价格
        </el-button>
      </el-form-item>
      <!--<el-form-item label="属性图片：" v-if="hasAttrPic">
        <el-card shadow="never" class="cardBg">
          <div v-for="(item,index) in selectProductAttrPics">
            <span>{{item.name}}:</span>
            <single-upload v-model="item.pic"
                           style="width: 300px;display: inline-block;margin-left: 10px"></single-upload>
          </div>
        </el-card>
      </el-form-item>-->
      <el-form-item label="商品参数：">
        <el-card shadow="never" class="cardBg">
          <div v-for="(item, index) in selectProductParam" :key="index" :class="{littleMarginTop:index!==0}">
            <div class="paramInputLabel">{{item.name}}:</div>
            <el-select v-if="item.inputType===1" class="paramInput" v-model="selectProductParam[index].value">
              <el-option
                      v-for="item in getParamInputList(item.inputList)"
                      :key="item"
                      :label="item"
                      :value="item">
              </el-option>
            </el-select>
            <el-input v-else class="paramInput" v-model="selectProductParam[index].value"></el-input>
          </div>
        </el-card>
      </el-form-item>
      <el-form-item label="商品相册：">
        <MaterialList v-model="value.galleryList" type="image" :num="6" :width="150" :height="150" />
      </el-form-item>
      <!-- <el-form-item label="商品轮播图：">
        <MaterialList v-model="selectProductPics" type="image" :num="6" :width="150" :height="150" />
      </el-form-item> -->
      <el-form-item label="商品描述：">
        <editor v-model="value.description" />
      </el-form-item>
      <el-form-item style="text-align: center">
        <el-button size="medium" @click="handlePrev">上一步，填写商品促销</el-button>
        <el-button type="primary" size="medium" @click="handleNext">下一步，完善商品关联专题</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
    import {getPlatformCatalogCList} from '@/api/catalog'
    import {getCatalogAttr} from '@/api/catalogAttr'
    import {getAttr} from '@/api/storeProduct'
    import picUpload from '@/components/pic-upload'
    import MaterialList from '@/components/material'
    import editor from '../../../components/Editor'
    //import Tinymce from './Tinymce'

    export default {
        name: "ProductAttrDetail",
        components: {picUpload, MaterialList, editor},
        props: {
            value: Object,
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
                //编辑模式时是否初始化成功
                hasEditCreated:false,
                //选中的商品属性
                selectProductAttr: [],
                //商品属性分类下拉选项
                productAttributeCategoryOptions: [],
                //选中的商品参数
                selectProductParam: [],
                selectProductPics: [],
                //选中的商品属性图片
                selectProductAttrPics: [],
                //可手动添加的商品属性
                addProductAttrValue: '',
                //商品富文本详情激活类型
                activeHtmlName: 'pc'
            }
        },
        computed: {
            //是否有商品属性图片
            hasAttrPic() {
                if (this.selectProductAttrPics.length < 1) {
                    return false;
                }
                return true;
            },
            //商品的编号
            productId(){
                return this.value.id;
            }
        },
        created() {
            this.getProductAttrCateList();
        },
        watch: {
            productId:function (newValue) {
                if(!this.isEdit)return;
                if(this.hasEditCreated)return;
                if(newValue===undefined||newValue==null||newValue===0)return;
                this.handleEditCreated();
            },
            'value.galleryList': function (newValue) {
                if (newValue == null || newValue.length === 0) {
                        this.value.image = null;
                        this.value.sliderImage = null;
                } else {
                    this.value.image = newValue[0];
                    this.value.sliderImage = '';
                    if (newValue.length > 1) {
                        for (let i = 1; i < newValue.length; i++) {
                            this.value.sliderImage += newValue[i];
                            if (i !== newValue.length - 1) {
                                this.value.sliderImage += ',';
                            }
                        }
                    }
                }
                console.log(this.value.image, this.value.sliderImage)
            }
        },
        methods: {
            handleEditCreated() {
                //根据商品属性分类id获取属性和参数
                if(this.value.productAttributeCategoryId!=null){
                    this.handleProductAttrChange(this.value.productAttributeCategoryId);
                    let length = this.value.productAttributeCategoryId.length;
                    if (length>0){
                        this.value.catalogId =  this.value.productAttributeCategoryId[length-1>0?length-1:0];
                    }
                }
                this.hasEditCreated=true;
            },
            getProductAttrCateList() {
                let param = {pageNum: 1, pageSize: 100};
                getPlatformCatalogCList(param).then(response => {
                    this.productAttributeCategoryOptions = [];
                    let list = response.content;
                    for (let i = 0; i < list.length; i++) {
                        let children = [];
                        if (list[i].children != null && list[i].children.length > 0) {
                            for (let j = 0; j < list[i].children.length; j++) {
                                children.push({label: list[i].children[j].name, value: list[i].children[j].id});
                            }
                        }
                        this.productAttributeCategoryOptions.push({label: list[i].name, value: list[i].id, children: children});
                    }

                    this.getProductAttrList(0, this.value.categoryList);
                    this.getProductAttrList(1, this.value.categoryList);
                });
            },
            getProductAttrList(type, cid) {
                console.log(type, cid, "getProductAttrList");
                let param = {catalogId: 0, page: 0, size: 100, isSpec: (type===0)?1:0};
                if (cid!==null && cid.length>0){
                    param.catalogId = cid[cid.length-1 >0?cid.length-1:0];
                }
                getCatalogAttr(param).then(response => {
                    let list = response.content;
                    if (type === 0) {
                        this.selectProductAttr = [];
                        for (let i = 0; i < list.length; i++) {
                            let options = [];
                            let values = [];
                            if (this.isEdit) {
                                //编辑状态下获取选中属性
                                if (param.catalogId===this.value.catalogId){
                                    values = this.getEditAttrValues(i);
                                }
                            }
                            if (list[i].handAddStatus === 1) {
                                //编辑状态下获取手动添加编辑属性
                                options = this.getEditAttrOptions(list[i].id, list[i].inputList);
                            }
                            this.selectProductAttr.push({
                                id: list[i].id,
                                name: list[i].attrName,
                                handAddStatus: list[i].handAddStatus,
                                inputList: list[i].inputList,
                                values: values,
                                options: options
                            });
                        }
                        if(this.isEdit){
                            //编辑模式下刷新商品属性图片
                            this.refreshProductAttrPics();
                        }
                    } else {
                        this.selectProductParam = [];
                        for (let i = 0; i < list.length; i++) {
                            let value=null;
                            if(this.isEdit){
                                //编辑模式下获取参数属性
                                value= this.getEditParamValue(list[i].id);
                            }
                            this.selectProductParam.push({
                                id: list[i].id,
                                name: list[i].attrName,
                                value: value,
                                inputType: list[i].inputType,
                                inputList: list[i].inputList
                            });
                        }
                    }
                    if (cid!==null && cid.length>0){
                        this.value.catalogId =  cid[cid.length-1 >0?cid.length-1:0];
                    }
                });
            },
            //获取设置的可手动添加属性值
            getEditAttrOptions(id, inputList) {
                let options = inputList.split(",");
                // for (let i = 0; i < this.value.productAttributeValueList.length; i++) {
                //     let attrValue = this.value.productAttributeValueList[i];
                //     if (attrValue.productAttributeId === id) {
                //         let strArr = attrValue.value.split(',');
                //         for (let j = 0; j < strArr.length; j++) {
                //             options.push(strArr[j]);
                //         }
                //         break;
                //     }
                // }
                return options;
            },
            //获取选中的属性值
            getEditAttrValues(index) {
                let values = [];
                if (index === 0) {
                    for (let i = 0; i < this.value.skuStockList.length; i++) {
                        let sku = this.value.skuStockList[i];
                        if (sku.sp1 != null && values.indexOf(sku.sp1) === -1) {
                            values.push(sku.sp1);
                        }
                    }
                } else if (index === 1) {
                    for (let i = 0; i < this.value.skuStockList.length; i++) {
                        let sku = this.value.skuStockList[i];
                        if (sku.sp2 != null && values.indexOf(sku.sp2) === -1) {
                            values.push(sku.sp2);
                        }
                    }
                } else {
                    for (let i = 0; i < this.value.skuStockList.length; i++) {
                        let sku = this.value.skuStockList[i];
                        if (sku.sp3 != null && values.indexOf(sku.sp3) === -1) {
                            values.push(sku.sp3);
                        }
                    }
                }

                return values;
            },
            //获取属性的值
            getEditParamValue(id){
                for(let i=0;i<this.value.productAttributeValueList.length;i++){
                    if(id===this.value.productAttributeValueList[i].productAttributeId){
                        return this.value.productAttributeValueList[i].value;
                    }
                }
            },
            handleProductAttrChange(value) {
                let length = value.length;
                this.getProductAttrList(0, value);
                this.getProductAttrList(1, value);
            },
            getInputListArr(inputList) {
                return inputList.split(',');
            },
            handleAddProductAttrValue(idx) {
                let options = this.selectProductAttr[idx].options;
                if (this.addProductAttrValue == null || this.addProductAttrValue == '') {
                    this.$message({
                        message: '属性值不能为空',
                        type: 'warning',
                        duration: 1000
                    });
                    return
                }
                if (options.indexOf(this.addProductAttrValue) !== -1) {
                    this.$message({
                        message: '属性值不能重复',
                        type: 'warning',
                        duration: 1000
                    });
                    return;
                }
                this.selectProductAttr[idx].options.push(this.addProductAttrValue);
                this.addProductAttrValue = null;
            },
            handleRemoveProductAttrValue(idx, index) {
                this.selectProductAttr[idx].options.splice(index, 1);
            },
            getProductSkuSp(row, index) {
                if (index === 0) {
                    return row.sp1;
                } else if (index === 1) {
                    return row.sp2;
                } else  {
                    return row.sp3;
                }
            },
            handleRefreshProductSkuList() {
                this.$confirm('刷新列表将导致sku信息重新生成，是否要刷新', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {

                    // this.refreshProductAttrPics();
                    this.refreshProductSkuList();
                });
            },
            handleSyncProductSkuPrice(){
                this.$confirm('将同步第一个sku的价格到所有sku,是否继续', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    if(this.value.skuStockList!==null&&this.value.skuStockList.length>0){
                        let price=this.value.skuStockList[0].price;
                        for(let i=0;i<this.value.skuStockList.length;i++){
                            this.value.skuStockList[i].price=price;
                        }
                    }
                });
            },
            refreshProductSkuList() {
                this.value.skuStockList = [];
                // let skuList = this.value.skuStockList;
                //只有一个属性时
                
                if (this.selectProductAttr.length === 1) {
                    let values = this.selectProductAttr[0].values;
                    console.log(values, "refreshProductSkuList")
                    for (let i = 0; i < values.length; i++) {
                        this.value.skuStockList.push({
                            sp1: values[i]
                        });
                    }
                } else if (this.selectProductAttr.length === 2) {
                    let values0 = this.selectProductAttr[0].values;
                    let values1 = this.selectProductAttr[1].values;
                    console.log(values0, values1, "refreshProductSkuList")
                    for (let i = 0; i < values0.length; i++) {
                        if (values1.length === 0) {
                            this.value.skuStockList.push({
                                sp1: values0[i]
                            });
                            continue;
                        }
                        for (let j = 0; j < values1.length; j++) {
                            this.value.skuStockList.push({
                                sp1: values0[i],
                                sp2: values1[j]
                            });
                        }
                    }
                }  else  {
                    let values0 = this.selectProductAttr[0].values;
                    let values1 = this.selectProductAttr[1].values;
                    let values2 = this.selectProductAttr[2].values;
                    for (let i = 0; i < values0.length; i++) {
                        if (values1.length === 0) {
                            this.value.skuStockList.push({
                                sp1: values0[i]
                            });
                            continue;
                        }
                        for (let j = 0; j < values1.length; j++) {
                            if (values2.length === 0) {
                                this.value.skuStockList.push({
                                    sp1: values0[i],
                                    sp2: values1[j]
                                });
                                continue;
                            }
                            for (let k = 0; k < values2.length; k++) {
                                this.value.skuStockList.push({
                                    sp1: values0[i],
                                    sp2: values1[j],
                                    sp3: values2[k]
                                });
                            }
                        }
                    }
                }
            },
            refreshProductAttrPics() {
                this.selectProductAttrPics = [];
                if (this.selectProductAttr.length >= 1) {
                    let values = this.selectProductAttr[0].values;
                    for (let i = 0; i < values.length; i++) {
                        let pic=null;
                        if(this.isEdit){
                            //编辑状态下获取图片
                            pic=this.getProductSkuPic(values[i]);
                        }
                        this.selectProductAttrPics.push({name: values[i], pic: pic})
                    }
                }
            },
            //获取商品相关属性的图片
            getProductSkuPic(name){
                for(let i=0;i<this.value.skuStockList.length;i++){
                    if(name===this.value.skuStockList[i].sp1){
                        return this.value.skuStockList[i].pic;
                    }
                }
                return null;
            },
            //合并商品属性
            mergeProductAttrValue() {
                this.value.productAttributeValueList = [];
                for (let i = 0; i < this.selectProductAttr.length; i++) {
                    let attr = this.selectProductAttr[i];
                    if (attr.handAddStatus === 1 && attr.values != null && attr.values.length > 0) {
                        this.value.productAttributeValueList.push({
                            productAttributeId: attr.id,
                            name: attr.name,
                            type:0,
                            value: this.getOptionStr(attr.values)
                        });
                    }
                }
                for (let i = 0; i < this.selectProductParam.length; i++) {
                    let param = this.selectProductParam[i];
                    this.value.productAttributeValueList.push({
                        productAttributeId: param.id,
                        name: param.name,
                        type:1,
                        value: param.value
                    });
                }
            },
            //合并商品属性图片
            mergeProductAttrPics() {
                for (let i = 0; i < this.selectProductAttrPics.length; i++) {
                    for (let j = 0; j < this.value.skuStockList.length; j++) {
                        if (this.value.skuStockList[j].sp1 === this.selectProductAttrPics[i].name) {
                            this.value.skuStockList[j].pic = this.selectProductAttrPics[i].pic;
                        }
                    }
                }
            },
            getOptionStr(arr) {
                let str = '';
                for (let i = 0; i < arr.length; i++) {
                    str += arr[i];
                    if (i != arr.length - 1) {
                        str += ',';
                    }
                }
                return str;
            },
            handleRemoveProductSku(index, row) {
                let list = this.value.skuStockList;
                if (list.length === 1) {
                    list.pop();
                } else {
                    list.splice(index, 1);
                }
            },
            getParamInputList(inputList) {
                return inputList.split(',');
            },
            handlePrev() {
                this.$emit('prevStep')
            },
            handleNext() {
                this.mergeProductAttrValue();
                this.mergeProductAttrPics();
                this.$emit('nextStep')
                console.log(1111, "handleNext")
            }
            // ,
            // handleFinishCommit(){
            //     this.mergeProductAttrValue();
            //     this.mergeProductAttrPics();
            //     console.log(this.value.skuStockList, "commiit")
            //     console.log(this.value.productAttributeValueList, "commiit")
            //     this.$emit('finishCommit',this.isEdit);
            // }
        }
    }
</script>

<style scoped>
  .littleMarginLeft {
    margin-left: 10px;
  }

  .littleMarginTop {
    margin-top: 10px;
  }

  .paramInput {
    width: 250px;
  }

  .paramInputLabel {
    display: inline-block;
    width: 100px;
    text-align: right;
    padding-right: 10px
  }

  .cardBg {
    background: #F8F9FC;
  }

  .w-e-menu{
    z-index: 2 !important;
    }
    .w-e-text-container{
    z-index: 1 !important;
    }
</style>