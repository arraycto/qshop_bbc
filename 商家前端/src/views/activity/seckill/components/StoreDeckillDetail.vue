<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="helpCategory" :rules="rules" ref="helpCategoryFrom" label-width="150px">
      <el-form-item label="秒杀名称">
        <el-input v-model="helpCategory.title" style="width: 500px;" />
      </el-form-item>
      <el-form-item label="秒杀简介">
        <el-input v-model="helpCategory.info" style="width: 500px;" />
      </el-form-item>
      <el-form-item label="单位">
        <el-input v-model="helpCategory.unitName" style="width: 500px;" />
      </el-form-item>
      <el-form-item>
        <el-button size="mini" class="btn-add" @click="handleSelectProduct()" style="margin-left: 20px">添加</el-button>
        <el-table ref="productRelationTable"
                  :data="helpCategory.productList"
                  style="width: 100%;margin-top: 20px"
                  border>
          <el-table-column label="商品名称" align="center">
            <template slot-scope="scope">{{scope.row.name}}</template>
          </el-table-column>
      <el-table-column label="价格" align="center">
            <template slot-scope="scope">{{scope.row.price}}</template>
          </el-table-column>
          <el-table-column label="库存" align="center">
                                  <template slot-scope="scope">{{scope.row.stock}}</template>
                                </el-table-column>
          <el-table-column label="操作" align="center" width="100">
            <template slot-scope="scope">
              <el-button size="mini"
                        type="text"
                        @click="handleDeleteProductRelation(scope.$index, scope.row)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form-item>
      <el-form-item label="秒杀开始时间">
        <template>
          <el-date-picker
            v-model="helpCategory.startTimeDate"
            type="datetime"
            placeholder="选择日期时间"
          />
        </template>
      </el-form-item>
      <el-form-item label="秒杀结束时间">
        <template>
          <el-date-picker
            v-model="helpCategory.endTimeDate"
            type="datetime"
            placeholder="选择日期时间"
          />
        </template>
      </el-form-item>
      <el-form-item label="产品主图片">
        <MaterialList v-model="helpCategory.imageArr" style="width: 500px" type="image" :num="1" :width="150" :height="150" />
      </el-form-item>
      <el-form-item label="产品轮播图">
        <MaterialList v-model="helpCategory.sliderImageArr" style="width: 500px" type="image" :num="4" :width="150" :height="150" />
      </el-form-item>
      <el-form-item label="秒杀价">
        <el-input-number v-model="helpCategory.price" />
      </el-form-item>
      <el-form-item label="成本">
        <el-input-number v-model="helpCategory.cost" />
      </el-form-item>
      <el-form-item label="原价">
        <el-input-number v-model="helpCategory.otPrice" />
      </el-form-item>
      <el-form-item label="库存">
        <el-input-number v-model="helpCategory.stock" />
      </el-form-item>
      <el-form-item label="虚拟销量">
        <el-input-number v-model="helpCategory.sales" />
      </el-form-item>
      <el-form-item label="排序">
        <el-input-number v-model="helpCategory.sort" />
      </el-form-item>
      <el-form-item label="限购">
        <el-input-number v-model="helpCategory.num" />
      </el-form-item>
      <el-form-item label="邮费">
        <el-input-number v-model="helpCategory.postage" />
      </el-form-item>
      <el-form-item label="是否包邮">
        <el-radio v-model="helpCategory.isPostage" :label="1">是</el-radio>
        <el-radio v-model="helpCategory.isPostage" :label="0" style="width: 110px;">否</el-radio>
      </el-form-item>
      <el-form-item label="活动状态">
        <el-radio v-model="helpCategory.isShow" :label="1">开启</el-radio>
        <el-radio v-model="helpCategory.isShow" :label="0" style="width: 200px;">关闭</el-radio>
      </el-form-item>
      <el-form-item label="详情">
        <editor v-model="helpCategory.description" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('helpCategoryFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('helpCategoryFrom')">重置</el-button>
      </el-form-item>
    </el-form>
    <el-dialog title="选择商品" :visible.sync="selectDialogVisible" width="50%">
        <el-input v-model="dialogData.listQuery.productName"
                  style="width: 250px;margin-bottom: 20px"
                  size="small"
                  placeholder="商品名称搜索">
          <el-button slot="append" icon="el-icon-search" @click="handleSelectSearch()"></el-button>
        </el-input>
        <el-table :data="dialogData.list"
                  @selection-change="handleDialogSelectionChange" border>
          <el-table-column type="selection" width="60" align="center"></el-table-column>
          <el-table-column label="商品名称" align="center">
            <template slot-scope="scope">{{scope.row.productName}}</template>
          </el-table-column>
          <el-table-column label="货号" width="160" align="center">
            <template slot-scope="scope">NO.{{scope.row.barCode}}</template>
          </el-table-column>
          <el-table-column label="价格" width="120" align="center">
            <template slot-scope="scope">￥{{scope.row.price}}</template>
          </el-table-column>
        </el-table>
        <div class="pagination-container">
          <el-pagination
            background
            @size-change="handleDialogSizeChange"
            @current-change="handleDialogCurrentChange"
            layout="prev, pager, next"
            :current-page="dialogData.listQuery.page+1"
            :page-size="dialogData.listQuery.size"
            :page-sizes="[5,10,15]"
            :total="dialogData.total">
          </el-pagination>
        </div>
        <div style="clear: both;"></div>
        <div slot="footer">
          <el-button  size="small" @click="selectDialogVisible = false">取 消</el-button>
          <el-button  size="small" type="primary" @click="handleSelectDialogConfirm()">确 定</el-button>
        </div>
      </el-dialog>
  </el-card>
</template>
<script>
  import {add as createHelpCategory, getSeckills as getHelpCategory, edit as updateHelpCategory} from '@/api/storeSeckill'
  import {getProduct} from '@/api/storeProduct';
  import editor from '../../../components/Editor'
  import MaterialList from '@/components/material'
  const defaultHelpCategory={
    name: ''
  };
  export default {
    name: 'StoreSeckillDetail',
    components:{ editor, MaterialList },
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        helpCategory:Object.assign({}, defaultHelpCategory),
        selectDialogVisible:false,
        list: null,
        total: null,
        listLoading: false,
        dialogData:{
          list: null,
          total: null,
          multipleSelection:[],
          listQuery:{
            keyword: null,
            page: 0,
            size: 5
          }
        },
        sortDialogVisible:false,
        sortDialogData:{sort:0,id:null},
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
            if (this.helpCategory.image){
              this.helpCategory.imageArr = this.helpCategory.image.split(',')
            }
            if (this.helpCategory.images){
              this.helpCategory.sliderImageArr = this.helpCategory.images.split(',')
            }
          }
        });
      }else{
        this.helpCategory = Object.assign({},defaultHelpCategory);
      }
    },
    watch: {
      'helpCategory.imageArr': function(val){
        if (val){
          this.helpCategory.image = val.join(',');
        }
      },
      'helpCategory.sliderImageArr': function(val){
        if (val){
          this.helpCategory.images = val.join(',');
        }
      }
    },
    methods: {
      handleSelectionChange(val){
        this.multipleSelection = val;
      },
      handleSelectProduct(){
        this.selectDialogVisible=true;
        this.getDialogList();
      },
      handleSelectSearch(){
        this.getDialogList();
      },
      handleDialogSizeChange(val) {
        this.dialogData.listQuery.page = 0;
        this.dialogData.listQuery.size = val;
        this.getDialogList();
      },
      handleDialogCurrentChange(val) {
        this.dialogData.listQuery.page = val-1;
        this.getDialogList();
      },
      handleDialogSelectionChange(val){
        this.dialogData.multipleSelection = val;
      },
      handleSelectDialogConfirm(){
        if (this.dialogData.multipleSelection < 1) {
          this.$message({
            message: '请选择一条记录',
            type: 'warning',
            duration: 1000
          });
          return;
        }
        let selectProducts = [];
        let totalPrice =0;
        for (let i = 0; i < this.dialogData.multipleSelection.length; i++) {
        totalPrice =totalPrice+this.dialogData.multipleSelection[i].price;
          selectProducts.push({
            id:this.dialogData.multipleSelection[i].id,
            name:this.dialogData.multipleSelection[i].productName,
            price:this.dialogData.multipleSelection[i].price,
            stock:this.dialogData.multipleSelection[i].stock
          });
        }
        this.$confirm('使用要进行添加操作?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.selectDialogVisible=false;
        this.helpCategory.productList= selectProducts;
        this.helpCategory.originprice= totalPrice;
        });
      },
      handleEditSort(index,row){
        this.sortDialogVisible=true;
        this.sortDialogData.sort=row.sort;
        this.sortDialogData.id=row.id;
      },
      handleUpdateSort(){
        this.$confirm('是否要修改排序?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          edit(this.sortDialogData).then(response=>{
            this.sortDialogVisible=false;
            this.getList();
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
          });
        })
      },
      onSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('是否提交数据', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              console.log(this.helpCategory)
              if (this.isEdit) {
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
      handleDeleteProductRelation(index,row){
        this.helpCategory.productList.splice(index,1);
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
        this.helpCategory = Object.assign({},defaultHelpCategory);
      },
      getDialogList(){
        getProduct(this.dialogData.listQuery).then(response=>{
          this.dialogData.list=response.content;
          this.dialogData.total=response.totalElements;
        })
      }
    }
  }
</script>
<style>
</style>


