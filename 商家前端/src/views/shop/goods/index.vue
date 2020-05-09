<template> 
  <div class="app-container">
    <el-card class="filter-container" shadow="never">
      <div>
        <i class="el-icon-search"></i>
        <span>筛选搜索</span>
        <el-button
          style="float: right"
          @click="handleSearchList()"
          type="primary"
          size="small">
          查询结果
        </el-button>
        <el-button
          style="float: right;margin-right: 15px"
          @click="handleResetSearch()"
          size="small">
          重置
        </el-button>
      </div>
      <div style="margin-top: 15px">
        <el-form :inline="true" :model="listQuery" size="small" label-width="140px">
          <el-form-item label="输入搜索：">
            <el-input style="width: 203px" v-model="listQuery.keyword" placeholder="商品名称"></el-input>
          </el-form-item>
          <el-form-item label="商品货号：">
            <el-input style="width: 203px" v-model="listQuery.barCode" placeholder="商品货号"></el-input>
          </el-form-item>
          <el-form-item label="商品分类：">
            <el-cascader
              clearable
              change-on-select
              v-model="selectProductCateValue"
              :options="productCateOptions">
            </el-cascader>
          </el-form-item>
          <el-form-item label="商品店铺分类：">
            <el-cascader
              clearable
              change-on-select
              v-model="selectProductCateValue"
              :options="productStoreCateOptions">
            </el-cascader>
          </el-form-item>
          <el-form-item label="商品品牌：">
            <el-select v-model="listQuery.brandId" placeholder="请选择品牌" clearable>
              <el-option
                v-for="item in brandOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="上架状态：">
            <el-select v-model="listQuery.publishStatus" placeholder="全部" clearable>
              <el-option
                v-for="item in publishStatusOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="审核状态：">
            <el-select v-model="listQuery.verifyStatus" placeholder="全部" clearable>
              <el-option
                v-for="item in verifyStatusOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <el-card class="operate-container" shadow="never">
      <!--<i class="el-icon-tickets"></i>
      <span>数据列表</span>-->
      <el-button
        class="btn-add"
        @click="handleAddProduct()"
        size="mini">
        添加
      </el-button>
      <!--<el-tabs
               @on-click="handleSearchList()" v-model="listQuery.status">
        <el-tab-pane label="出售中" name="1">出售中</el-tab-pane>
        <el-tab-pane label="已售馨" name="2">已售馨</el-tab-pane>
        <el-tab-pane label="仓库中" name="3">仓库中</el-tab-pane>
        <el-tab-pane label="回收站" name="4">回收站</el-tab-pane>
        <el-tab-pane name="more" class="more-btn"><span slot="label"><router-link to="/page/wholeList/wholeList">更多+</router-link>
        </span></el-tab-pane>
      </el-tabs>-->
    </el-card>
    <div class="table-container">
      <el-table ref="productTable"
                :data="list"
                style="width: 100%"
                @selection-change="handleSelectionChange"
                v-loading="listLoading"
                border>
        <el-table-column type="selection" width="60" align="center"></el-table-column>
        <el-table-column label="编号" width="100" align="center">
          <template slot-scope="scope">{{scope.row.id}}</template>
        </el-table-column>
        <el-table-column label="商品图片" width="120" align="center">
          <template slot-scope="scope"><img style="height: 80px" :src="scope.row.image"></template>
        </el-table-column>
        <el-table-column label="商品名称" align="center">
          <template slot-scope="scope">
            <p>{{scope.row.productName}}</p>
            <p>品牌：{{scope.row.brandName}}</p>
          </template>
        </el-table-column>
        <el-table-column label="价格/货号" width="120" align="center">
          <template slot-scope="scope">
            <p>价格：￥{{scope.row.price}}</p>
            <p>货号：{{scope.row.barCode}}</p>
          </template>
        </el-table-column>
        <el-table-column label="标签" width="140" align="center">
          <template slot-scope="scope">
            <p>上架：
              <el-switch
                @change="handlePublishStatusChange(scope.$index, scope.row)"
                :active-value="1"
                :inactive-value="0"
                v-model="scope.row.isShow">
              </el-switch>
            </p>
            <p>包邮：
              <el-switch
                @change="handlePostageStatusChange(scope.$index, scope.row)"
                :active-value="1"
                :inactive-value="0"
                v-model="scope.row.isPostage">
              </el-switch>
            </p>
          </template>
        </el-table-column>
        <el-table-column label="排序" width="100" align="center">
          <template slot-scope="scope">{{scope.row.sort}}</template>
        </el-table-column>
        <el-table-column label="SKU库存" width="100" align="center">
          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" @click="handleShowSkuEditDialog(scope.$index, scope.row)" circle></el-button>
          </template>
        </el-table-column>
        <el-table-column label="销量" width="100" align="center">
          <template slot-scope="scope">{{scope.row.sales}}</template>
        </el-table-column>
        <el-table-column label="审核状态" width="100" align="center">
          <template slot-scope="scope">
            <p>{{scope.row.verifyStatus | verifyStatusFilter}}</p>
            <!-- <p>
              <el-button
                type="text"
                @click="handleShowVeriyEditDialog(scope.$index, scope.row)">审核详情
              </el-button>
            </p> -->
          </template>
        </el-table-column>
        <el-table-column label="操作" width="160" align="center">
          <template slot-scope="scope">
            <p>
              <el-button
                size="mini"
                @click="handleUpdateProduct(scope.$index, scope.row)">查看
              </el-button>
              <el-button
                size="mini"
                @click="handleUpdateProduct(scope.$index, scope.row)">编辑
              </el-button>
            </p>
            <p>
              <!-- <el-button
                size="mini"
                @click="handleShowVeriyEditDialog(scope.$index, scope.row)">日志
              </el-button> -->
              <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)">删除
              </el-button>
            </p>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="batch-operate-container">
      <el-select
        size="small"
        v-model="operateType" placeholder="批量操作">
        <el-option
          v-for="item in operates"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <el-button
        style="margin-left: 20px"
        class="search-button"
        @click="handleBatchOperate()"
        type="primary"
        size="small">
        确定
      </el-button>
    </div>
    <div class="pagination-container">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="total, sizes,prev, pager, next,jumper"
        :page-size="listQuery.size"
        :page-sizes="[5,10,15]"
        :current-page="listQuery.page+1"
        :total="total">
      </el-pagination>
    </div>
    <el-dialog
      title="审核信息"
      :visible.sync="vertyProduct.dialogVisible"
      width="40%">
      <el-form :model="vertyProduct"  ref="brandFrom" label-width="150px">
        <el-form-item label="审核状态：" prop="verifyStatus">
          <el-switch v-model="vertyProduct.verifyStatus" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
        <!-- <el-form-item label="审核备注：" prop="detail">
          <el-input v-model="vertyProduct.detail"></el-input>
        </el-form-item> -->

        <el-form-item>
          <el-button @click="vertyProduct.dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleEditVConfirm">确 定</el-button>
        </el-form-item>
      </el-form>
      <el-table style="width: 100%;margin-top: 20px"
                :data="vertyProduct.list"
                border>
        <el-table-column label="审核状态" width="160" align="center">
          <template slot-scope="scope">{{scope.row.status | verifyStatusFilter}}</template>
        </el-table-column>
        <el-table-column label="审核人" width="160" align="center">
          <template slot-scope="scope">{{scope.row.vertifier}}</template>
        </el-table-column>
        <el-table-column label="审核信息" width="160" align="center">
          <template slot-scope="scope">{{scope.row.detail}}</template>
        </el-table-column>
        <el-table-column label="审核时间" width="160" align="center">
          <template slot-scope="scope">{{formatTime(scope.row.addTime)}}</template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <el-dialog
      title="编辑货品信息"
      :visible.sync="editSkuInfo.dialogVisible"
      width="40%">
      <span>商品货号：</span>
      <span>{{editSkuInfo.barCode}}</span>
      <el-input placeholder="按sku编号搜索" v-model="editSkuInfo.keyword" size="small" style="width: 50%;margin-left: 20px">
        <el-button slot="append" icon="el-icon-search" @click="handleSearchEditSku"></el-button>
      </el-input>
      <el-table style="width: 100%;margin-top: 20px"
                :data="editSkuInfo.stockList"
                border>
        <!-- <el-table-column
          label="SKU编号"
          align="center">
          <template slot-scope="scope">
            <el-input v-model="scope.row.barCode"></el-input>
          </template>
        </el-table-column> -->
        <el-table-column
          v-for="(item,index) in editSkuInfo.productAttr"
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
        <!-- <el-table-column
          label="库存预警值"
          width="100"
          align="center">
          <template slot-scope="scope">
            <el-input v-model="scope.row.lowStock"></el-input>
          </template>
        </el-table-column> -->
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editSkuInfo.dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleEditSkuConfirm">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
  import { formatTime } from '@/utils/index'
  import {
    getProduct,
    edit,
    del,
    getAttr,
    setAttr,
    updateStatus
  } from '@/api/storeProduct'
  import {getPlatformBrands} from '@/api/brand'
  import {getPlatformCates} from '@/api/category'
  import {getCates} from '@/api/storeCategory'

  const defaultListQuery = {
    keyword: null,
    page: 0,
    size: 5,
    status: 1,
    isShow: null,
    verifyStatus: null,
    barCode: null,
    productCategoryId: null,
    brandId: null
  };
  export default {
    name: "productList",
    data() {
      return {
        dialogVVisible:false,
        editSkuInfo:{
          dialogVisible:false,
          productId:null,
          barCode:'',
          productAttributeCategoryId:null,
          stockList:[],
          productAttr:[],
          keyword:null
        },
        vertyProduct:{
          dialogVisible:false,
          productId:null,
          detail:'',
          verifyStatus:null,
          list:null
        },
        operates: [
          {
            label: "商品上架",
            value: "publishOn"
          },
          {
            label: "商品下架",
            value: "publishOff"
          },
          {
            label: "设为推荐",
            value: "recommendOn"
          },
          {
            label: "取消推荐",
            value: "recommendOff"
          },
          {
            label: "设为新品",
            value: "newOn"
          },
          {
            label: "取消新品",
            value: "newOff"
          },
          {
            label: "转移到分类",
            value: "transferCategory"
          },
          {
            label: "移入回收站",
            value: "recycle"
          }
        ], tabsName: '1',
        operateType: null,
        listQuery: Object.assign({}, defaultListQuery),
        list: null,
        total: null,
        listLoading: true,
        selectProductCateValue: null,
        multipleSelection: [],
        productCateOptions: [],
        productStoreCateOptions: [],
        brandOptions: [],
        publishStatusOptions: [{
          value: 1,
          label: '上架'
        }, {
          value: 0,
          label: '下架'
        }],
        verifyStatusOptions: [{
          value: 1,
          label: '审核通过'
        }, {
          value: 0,
          label: '未审核'
        }]
      }
    },
    created() {
      this.getList();
      this.getBrandList();
      this.getProductCateList();
      this.getCates();
    },
    watch: {
      selectProductCateValue: function (newValue) {
        console.log(newValue)
        if (newValue != null) {
          this.listQuery.productCategoryId = newValue[newValue.length-1 >0?newValue.length-1:0];
        } else {
          this.listQuery.productCategoryId = null;
        }

      }
    },
    filters: {
      verifyStatusFilter(value) {
        if (value === 1) {
          return '审核通过';
        } else {
          return '未审核';
        }
      }
    },
    methods: {
      formatTime,
      getProductSkuSp(row, index) {
        if (index === 0) {
          return row.sp1;
        } else if (index === 1) {
          return row.sp2;
        } else {
          return row.sp3;
        }
      },
      getList() {
        this.listLoading = true;
        console.log(this.listQuery)
        getProduct(this.listQuery).then(response => {
          this.listLoading = false;
          this.list = response.content;
          this.total = response.totalElements;
        });
      },
      getBrandList() {
        getPlatformBrands({page: 0, size: 100}).then(response => {
          this.brandOptions = [];
          let brandList = response.content;
          for (let i = 0; i < brandList.length; i++) {
            this.brandOptions.push({label: brandList[i].name, value: brandList[i].id});
          }
        });
      },
      getProductCateList() {
        getPlatformCates().then(response => {
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
      getCates() {
        getCates().then(response => {
          let list = response.content;
          this.productStoreCateOptions = [];
          for (let i = 0; i < list.length; i++) {
            let children = [];
            if (list[i].children != null && list[i].children.length > 0) {
              for (let j = 0; j < list[i].children.length; j++) {
                children.push({label: list[i].children[j].name, value: list[i].children[j].id});
              }
            }
            this.productStoreCateOptions.push({label: list[i].name, value: list[i].id, children: children});
          }
        });
      },
      handleShowVeriyEditDialog(index,row){
        this.vertyProduct.dialogVisible=true;
        this.vertyProduct.productId=row.id;
        this.vertyProduct.verifyStatus=row.verifyStatus;
        getProduct(row.id).then(response=>{
          this.vertyProduct.list=response.data;
      });
      },
      handleShowSkuEditDialog(index,row){
        this.editSkuInfo.dialogVisible=true;
        this.editSkuInfo.productId=row.id;
        this.editSkuInfo.barCode=row.barCode;
        this.editSkuInfo.productAttributeCategoryId = row.productAttributeCategoryId;
        this.editSkuInfo.keyword=null;
        getAttr(row.id,{keyword:this.editSkuInfo.keyword}).then(response=>{
          this.editSkuInfo.stockList=response.data;
        });
        getAttr(row.productAttributeCategoryId,{type:0}).then(response=>{
          this.editSkuInfo.productAttr=response.content;
        });
      },
      handleSearchEditSku(){
        getAttr(this.editSkuInfo.productId,{keyword:this.editSkuInfo.keyword}).then(response=>{
          this.editSkuInfo.stockList=response.content;
        });
      },
      handleEditSkuConfirm(){
        if(this.editSkuInfo.stockList==null||this.editSkuInfo.stockList.length<=0){
          this.$message({
            message: '暂无sku信息',
            type: 'warning',
            duration: 1000
          });
          return
        }
        this.$confirm('是否要进行修改', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
          setAttr(this.editSkuInfo.productId,this.editSkuInfo.stockList).then(response=>{
            this.$message({
              message: '修改成功',
              type: 'success',
              duration: 1000
            });
            this.editSkuInfo.dialogVisible=false;
          });
        });
      },
      handleEditVConfirm(){
        if(this.vertyProduct.detail==null){
          this.$message({
            message: '暂无审核信息',
            type: 'warning',
            duration: 1000
          });
          return
        }
        this.$confirm('是否要进行审核', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{

          let params = new URLSearchParams();

        params.append('ids', this.vertyProduct.productId);
        params.append('verifyStatus', this.vertyProduct.verifyStatus);
        params.append('detail', this.vertyProduct.detail);
        edit(params).then(response => {
          this.$message({
          message: '修改成功',
          type: 'success',
          duration: 1000
        });
        this.getList();
      });
        this.vertyProduct.dialogVisible=false;

      });
      },

      handleSearchList() {
        this.listQuery.page = 0;
        this.getList();
      },
      handleAddProduct() {
        this.$router.push({path:'/shop/addGoods'});
      },
      handleBatchOperate() {
        if(this.operateType==null){
          this.$message({
            message: '请选择操作类型',
            type: 'warning',
            duration: 1000
          });
          return;
        }
        if(this.multipleSelection==null||this.multipleSelection.length<1){
          this.$message({
            message: '请选择要操作的商品',
            type: 'warning',
            duration: 1000
          });
          return;
        }
        this.$confirm('是否要进行该批量操作?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let ids=[];
          for(let i=0;i<this.multipleSelection.length;i++){
            ids.push(this.multipleSelection[i].id);
          }
          switch (this.operateType) {
            case this.operates[0].value:
              this.updatePublishStatus(1,ids);
              break;
            case this.operates[1].value:
              this.updatePublishStatus(0,ids);
              break;
            case this.operates[2].value:
              this.updateRecommendStatus(1,ids);
              break;
            case this.operates[3].value:
              this.updateRecommendStatus(0,ids);
              break;
            case this.operates[4].value:
              this.updateNewStatus(1,ids);
              break;
            case this.operates[5].value:
              this.updateNewStatus(0,ids);
              break;
            case this.operates[6].value:
              break;
            case this.operates[7].value:
              this.updateDeleteStatus(1,ids);
              break;
            default:
              break;
          }
          this.getList();
        });
      },
      handleSizeChange(val) {
        this.listQuery.page = 0;
        this.listQuery.size = val;
        this.getList();
      },
      handleCurrentChange(val) {
        this.listQuery.page = val-1;
        this.getList();
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      handlePublishStatusChange(index, row) {
        let ids = [];
        ids.push(row.id);
        this.updatePublishStatus(row.isShow, ids);
      },
      handleupdatePaiMaiChange(index, row) {
        let ids = [];
        ids.push(row.id);
        this.edit(row.publishStatus, ids);
      },
      handleNewStatusChange(index, row) {
        let ids = [];
        ids.push(row.id);
        this.updateNewStatus(row.newStatus, ids);
      },
      handleVerifyStatusChange(index, row) {
        let ids = [];
        ids.push(row.id);
        this.updateVerifyStatus(row.verifyStatus, ids);
      },
      handleRecommendStatusChange(index, row) {
        let ids = [];
        ids.push(row.id);
        this.updateRecommendStatus(row.recommandStatus, ids);
      },
      handleNewStatusChange(index, row) {
        let ids = [];
        ids.push(row.id);
        this.updateNewStatus(row.isNew, ids);
      },
      handleGoodStatusChange(index, row) {
        let ids = [];
        ids.push(row.id);
        this.updateGoodStatus(row.isGood, ids);
      },
      handleHotStatusChange(index, row) {
        let ids = [];
        ids.push(row.id);
        this.updateHotStatus(row.isHot, ids);
      },
      handleBestStatusChange(index, row) {
        let ids = [];
        ids.push(row.id);
        this.updateBestStatus(row.isBest, ids);
      },
      handleBenefitStatusChange(index, row) {
        let ids = [];
        ids.push(row.id);
        this.updateBenefitStatus(row.isBenefit, ids);
      },
      handlePostageStatusChange(index, row) {
        let ids = [];
        ids.push(row.id);
        this.updatePostageStatus(row.isPostage, ids);
      },
      handleResetSearch() {
        this.selectProductCateValue = [];
        this.listQuery = Object.assign({}, defaultListQuery);
      },
      handleDelete(index, row){
        this.$confirm('是否要进行删除操作?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let ids = [];
          ids.push(row.id);
         // this.updateDeleteStatus(1,ids);
          del(row.id).then(response => {
            this.$message({
              message: '删除成功',
              type: 'success',
              duration: 1000
            });
            this.getList();
          });
        });
      },
      handleUpdateProduct(index,row){
        this.$router.push({path:'/shop/updateGoods',query:{id:row.id}});
      },
      handleShowProduct(index,row){
        console.log("handleShowProduct",row);
      },
      handleShowVerifyDetail(index,row){
        console.log("handleShowVerifyDetail",row);
      },
      handleShowLog(index,row){
        console.log("handleShowLog",row);
      },
      updateVerifyStatus(publishStatus, ids) {
        let params = {
          ids: ids,
          verifyStatus: publishStatus
        };
        updateStatus(params).then(response => {
          this.$message({
            message: '修改成功',
            type: 'success',
            duration: 1000
          });
        });
      },
      updatePublishStatus(publishStatus, ids) {
        let params = {
          ids: ids,
          isShow: publishStatus
        };
        updateStatus(params).then(response => {
          this.$message({
          message: '修改成功',
          type: 'success',
          duration: 1000
        });
      });
      },
      updateNewStatus(newStatus, ids) {
        let params = {
          ids: ids,
          newStatus: newStatus
        };
        updateStatus(params).then(response => {
          this.$message({
            message: '修改成功',
            type: 'success',
            duration: 1000
          });
        });
      },
      updateRecommendStatus(recommendStatus, ids) {
        let params = {
          ids: ids,
          recommendStatus: recommendStatus
        };
        updateStatus(params).then(response => {
          this.$message({
            message: '修改成功',
            type: 'success',
            duration: 1000
          });
        });
      },
      updateBenefitStatus(recommendStatus, ids) {
        let params = {
          ids: ids,
          isBenefit: recommendStatus
        };
        updateStatus(params).then(response => {
          this.$message({
            message: '修改成功',
            type: 'success',
            duration: 1000
          });
        });
      },
      updateBestStatus(recommendStatus, ids) {
        let params = {
          ids: ids,
          isBest: recommendStatus
        };
        updateStatus(params).then(response => {
          this.$message({
            message: '修改成功',
            type: 'success',
            duration: 1000
          });
        });
      },
      updateGoodStatus(recommendStatus, ids) {
        let params = {
          ids: ids,
          isGood: recommendStatus
        };
        updateStatus(params).then(response => {
          this.$message({
            message: '修改成功',
            type: 'success',
            duration: 1000
          });
        });
      },
      updateHotStatus(recommendStatus, ids) {
        let params = {
          ids: ids,
          isHot: recommendStatus
        };
        updateStatus(params).then(response => {
          this.$message({
            message: '修改成功',
            type: 'success',
            duration: 1000
          });
        });
      },
      updateBestStatus(recommendStatus, ids) {
        let params = {
          ids: ids,
          isBest: recommendStatus
        };
        updateStatus(params).then(response => {
          this.$message({
            message: '修改成功',
            type: 'success',
            duration: 1000
          });
        });
      },
      updateNewStatus(recommendStatus, ids) {
        let params = {
          ids: ids,
          isNew: recommendStatus
        };
        updateStatus(params).then(response => {
          this.$message({
            message: '修改成功',
            type: 'success',
            duration: 1000
          });
        });
      },
      updatePostageStatus(recommendStatus, ids) {
        let params = {
          ids: ids,
          isPostage: recommendStatus
        };
        updateStatus(params).then(response => {
          this.$message({
            message: '修改成功',
            type: 'success',
            duration: 1000
          });
        });
      },
      updateDeleteStatus(deleteStatus, ids) {
        let params = {
          ids: ids,
          isDel: recommendStatus
        };
        updateStatus(params).then(response => {
          this.$message({
            message: '删除成功',
            type: 'success',
            duration: 1000
          });
        });
        this.getList();
      }
    }
  }
</script>
<style></style>