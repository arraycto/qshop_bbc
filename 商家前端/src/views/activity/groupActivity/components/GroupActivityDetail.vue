<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="helpCategory" :rules="rules" ref="helpCategoryFrom" label-width="150px">
      <el-form-item label="名称：" prop="name">
        <el-input v-model="helpCategory.name"></el-input>
      </el-form-item>
      <el-form-item label="图片：" prop="pic">
        <MaterialList v-model="helpCategory.pic" type="image" :num="1" :width="150" :height="150" />
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
                     <el-form-item label="原价：" prop="originprice">
                           <el-input v-model.number="helpCategory.originprice"></el-input>
                         </el-form-item>
       <el-form-item label="套餐价格：" prop="price">
              <el-input v-model.number="helpCategory.price"></el-input>
            </el-form-item>
             <el-form-item label="运费：" prop="transfee">
                    <el-input v-model.number="helpCategory.transfee"></el-input>
                  </el-form-item>
      <el-form-item label="是否显示：">
        <el-radio-group v-model="helpCategory.status">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
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
  import { getProduct } from '@/api/storeProduct'
  import {add, getGroupActivityDetail, edit} from '@/api/groupActivity'
  import picUpload from '@/components/pic-upload'
  import MaterialList from '@/components/material'
  const defaultHelpCategory={
    name: ''
  };
  export default {
    name: 'GroupActivityDetail',
    components:{ picUpload, MaterialList },
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
        dialogData:{
                  list: null,
                  total: null,
                  multipleSelection:[],
                  listQuery:{
                    productName: null,
                    page: 0,
                    size: 5
                  }
                },
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
    created() {
      if (this.isEdit) {
        getGroupActivityDetail(this.$route.query.id).then(response => {
          this.helpCategory = response;
        });
      }else{
        this.helpCategory = Object.assign({},defaultHelpCategory);
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
                edit(this.helpCategory).then(response => {
                  this.$refs[formName].resetFields();
                  this.$message({
                    message: '修改成功',
                    type: 'success',
                    duration:1000
                  });
                  this.$router.back();
                });
              } else {
                add(this.helpCategory).then(response => {
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
                     getList() {
                          this.listLoading = true;
                          fetchList(this.listQuery).then(response => {
                            this.listLoading = false;
                            this.list = response.data.data;
                            this.total = response.data.total;
                          });
                        },
                        getDialogList(){
                          getProduct(this.dialogData.listQuery).then(response=>{
                            this.dialogData.list=response.content;
                            this.dialogData.total=response.totalElement;
                          })
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
                        name:this.dialogData.multipleSelection[i].name,
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
                    handleDeleteProductRelation(index,row){
                              this.helpCategory.productList.splice(index,1);
                            },
                  handleEditDialogConfirm(){
                    this.$confirm('是否要确认?', '提示', {
                      confirmButtonText: '确定',
                      cancelButtonText: '取消',
                      type: 'warning'
                    }).then(() => {
                         this.editDialogVisible =false;
                    })
                  },
    }
  }
</script>
<style>
</style>


