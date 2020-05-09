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
            <el-button
                    class="btn-add"
                    @click="addHelpCategory()"
                    size="mini">
                添加
            </el-button>
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
                        <el-switch
                        @change="handleStatusStatusChange($event, scope.row)"
                        :active-value="1"
                        :inactive-value="0"
                        v-model="scope.row.applyStatus">
                        </el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="所属店铺" align="center">
                    <template slot-scope="scope">{{scope.row.storeId}}</template>
                </el-table-column>
                <el-table-column label="操作" width="200" align="center">
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="handleUpdate(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="handleDelete(scope.$index, scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="batch-operate-container">

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
    </div>
</template>
<script>
    import {getStoreWithdraw as fetchList, del as deleteHelpCategory, edit} from '@/api/storeWithdraw'
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
            getList() {
                this.listLoading = true;
                fetchList(this.listQuery).then(response => {
                    this.listLoading = false;
                    this.list = response.content;
                    this.total = response.totalElements;
            });
            },
            handleStatusStatusChange(val, row){
                edit({applyStatus: val, id: row.id})
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            handleUpdate(index, row) {
                this.$router.push({path: '/price/storeWithdrawUpdate', query: {id: row.id}})
            },
            handleDelete(index, row) {
                this.$confirm('是否要删除该品牌', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    deleteHelpCategory(row.id).then(response => {
                    this.$message({
                    message: '删除成功',
                    type: 'success',
                    duration: 1000
                });
                this.getList();
            });
            });
            },
            getProductList(index, row) {
                console.log(index, row);
            },
            getProductCommentList(index, row) {
                console.log(index, row);
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


