<template> 
    <div class="app-container">
        <el-card class="filter-container" shadow="never">
            <div>
                <i class="el-icon-search"></i>
                <span>筛选搜索</span>
                <el-button
                        style="float: right"
                        @click="searchHelpCategoryList()"
                        type="primary"
                        size="small">
                    查询结果
                </el-button>
            </div>
            <div style="margin-top: 15px">
                <el-form :inline="true" :model="listQuery" size="small" label-width="140px">
                    <el-form-item label="输入搜索：">
                        <el-input style="width: 203px" v-model="listQuery.name" placeholder="名称"></el-input>
                    </el-form-item>
                </el-form>
            </div>
        </el-card>
        <el-card class="operate-container" shadow="never">
            <i class="el-icon-tickets"></i>
            <span>数据列表</span>
            <!-- <el-button
                    class="btn-add"
                    @click="addHelpCategory()"
                    size="mini">
                添加
            </el-button> -->
        </el-card>
        <div class="table-container">
            <el-table ref="helpCategoryTable"
                      :data="list"
                      style="width: 100%"
                      @selection-change="handleSelectionChange"
                      v-loading="listLoading"
                      border>
                <el-table-column type="selection" width="60" align="center"></el-table-column>

                <el-table-column label="编号" align="center">
                    <template slot-scope="scope">{{scope.row.id}}</template>
                </el-table-column>
                <el-table-column label="申请金额" align="center">
                    <template slot-scope="scope">{{scope.row.applyAmount}}</template>
                </el-table-column>
                <el-table-column label="审核时间" align="center">
                    <template slot-scope="scope">{{parseTime(scope.row.verifyTime)}}</template>
                </el-table-column>
                <el-table-column label="审核状态" align="center">
                    <template slot-scope="scope">
                        <div>
                            <el-tag v-if="scope.row.applyStatus === 2" style="cursor: pointer" :type="''">审核通过</el-tag>
                            <el-tag v-else-if="scope.row.applyStatus === 1" style="cursor: pointer" :type="''">待审核</el-tag>
                            <el-tag v-else-if="scope.row.applyStatus === 0" style="cursor: pointer" :type="''">初始化</el-tag>
                            <el-tag v-else style="cursor: pointer" :type=" 'info' ">审核不通过</el-tag>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column label="所属店铺" align="center">
                    <template slot-scope="scope">{{scope.row.shopName}}</template>
                </el-table-column>
                <el-table-column label="操作" width="160" align="center">
                    <template slot-scope="scope">
                    <p>
                        <el-button
                        size="mini"
                        @click="handleShowVeriyEditDialog(scope.$index, scope.row)">审核
                        </el-button>
                    </p>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="batch-operate-container">

        </div>
        <el-dialog
      title="审核信息"
      :visible.sync="vertyProduct.dialogVisible"
      width="40%">
      <el-form :model="vertyProduct"  ref="brandFrom" label-width="150px">
        <el-form-item label="审核状态：" prop="verifyStatus">
          <el-switch v-model="vertyProduct.applyStatus" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
        <el-form-item label="审核备注：" prop="detail">
          <el-input v-model="vertyProduct.detail"></el-input>
          <el-input v-model="vertyProduct.storeId" style="display:none;"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button @click="vertyProduct.dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleEditVConfirm">确 定</el-button>
        </el-form-item>
      </el-form>
      <el-table style="width: 100%;margin-top: 20px"
                :data="vertyProduct.list"
                border>
        <el-table-column label="审核状态" width="160" align="center">
          <template slot-scope="scope">{{verifyStatusFilter(scope.row.status)}}</template>
        </el-table-column>
        <el-table-column label="审核人" width="160" align="center">
          <template slot-scope="scope">{{scope.row.vertifier}}</template>
        </el-table-column>
        <el-table-column label="审核信息" width="160" align="center">
          <template slot-scope="scope">{{scope.row.detail}}</template>
        </el-table-column>
        <el-table-column label="审核时间" width="160" align="center">
          <template slot-scope="scope">{{parseTime(scope.row.addTime)}}</template>
        </el-table-column>
      </el-table>
    </el-dialog>
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
    </div>
</template>
<script>
    import {getStoreWithdraw as fetchList, del as deleteHelpCategory, edit, updateVerifyStatus} from '@/api/storeWithdraw'
    import {getStoreWithdrawRecord as fetchVList} from '@/api/storeWithdrawRecord'
    import { formatTimeTwo, parseTime } from '@/utils/index'

    export default {
        name: 'storeWithdrawList',
        components: {formatTimeTwo, parseTime},
        data() {
            return {
                operates: [

                ],
                operateType: null,
                listQuery: {
                    keyword: null,
                    page: 0,
                    size: 10
                },
                vertyProduct:{
                    dialogVisible:false,
                    productId:null,
                    detail:'',
                    applyStatus:null,
                    list:null,
                    storeId: null
                },
                list: null,
                total: null,
                listLoading: true,
                multipleSelection: []
            }
        },
        created() {
            this.getList();
        },
        methods: {
            formatTimeTwo, 
            parseTime,
            verifyStatusFilter(value) {
                if (value === 1) {
                    return '审核通过';
                } else {
                    return '审核不通过';
                }
            },
            getList() {
                this.listLoading = true;
                fetchList(this.listQuery).then(response => {
                    this.listLoading = false;
                    this.list = response.content;
                    this.total = response.totalElements;
            });
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            getProductList(index, row) {
                console.log(index, row);
            },
            getProductCommentList(index, row) {
                console.log(index, row);
            },

            handleShowVeriyEditDialog(index,row){
                this.vertyProduct.dialogVisible=true;
                this.vertyProduct.productId=row.id;
                this.vertyProduct.applyStatus=row.applyStatus;
                this.vertyProduct.storeId = row.storeId;
                fetchVList({storeWithdrawId:row.id}).then(response=>{
                    this.vertyProduct.list=response.content;
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
            searchHelpCategoryList() {
                this.listQuery.page = 0;
                this.getList();
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

                let params = {
                    id: this.vertyProduct.productId,
                    applyStatus: this.vertyProduct.applyStatus,
                    detail: this.vertyProduct.detail,
                    storeId: this.vertyProduct.storeId
                };

                updateVerifyStatus(params).then(response => {
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
            handleBatchOperate() {
                if (this.multipleSelection < 1) {
                    this.$message({
                        message: '请选择一条记录',
                        type: 'warning',
                        duration: 1000
                    });
                    return;
                }
                let showStatus = 0;
                if (this.operateType === 'showHelpCategory') {
                    showStatus = 1;
                } else if (this.operateType === 'hideHelpCategory') {
                    showStatus = 0;
                } else {
                    this.$message({
                        message: '请选择批量操作类型',
                        type: 'warning',
                        duration: 1000
                    });
                    return;
                }
                let ids = [];
                for (let i = 0; i < this.multipleSelection.length; i++) {
                    ids.push(this.multipleSelection[i].id);
                }
                let data = new URLSearchParams();
                data.append("ids", ids);
                data.append("status", showStatus);
                updateShowStatus(data).then(response => {
                    this.getList();
                this.$message({
                    message: '修改成功',
                    type: 'success',
                    duration: 1000
                });
            });
            },
            addHelpCategory() {
                this.$router.push({path: '/price/storeWithdrawAdd'})
            }
        }
    }
</script>
<style rel="stylesheet/scss" lang="scss" scoped>


</style>


