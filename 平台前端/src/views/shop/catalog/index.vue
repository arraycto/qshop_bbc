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
                <el-table-column label="类目名称" align="center">
                    <template slot-scope="scope">{{scope.row.name}}</template>
                </el-table-column>
                <el-table-column label="类目关键字" align="center">
                    <template slot-scope="scope">{{scope.row.keywords}}</template>
                </el-table-column>
                <el-table-column label="类目广告语介绍" align="center">
                    <template slot-scope="scope">{{scope.row.descs}}</template>
                </el-table-column>
                <el-table-column label="父类目ID" align="center">
                    <template slot-scope="scope">{{scope.row.pid}}</template>
                </el-table-column>
                <el-table-column label="层级" align="center">
                    <template slot-scope="scope">{{scope.row.level}}</template>
                </el-table-column>
                <el-table-column label="佣金率" align="center">
                    <template slot-scope="scope">{{scope.row.chargeRate}}</template>
                </el-table-column>
                <el-table-column label="排序" align="center">
                    <template slot-scope="scope">{{scope.row.sortOrder}}</template>
                </el-table-column>
                <el-table-column label="创建时间" align="center">
                    <template slot-scope="scope">{{parseTime(scope.row.addTime)}}</template>
                </el-table-column>
                <el-table-column label="操作" width="120" align="center">
          <template slot-scope="scope">
            <el-button size="mini"
                       type="text"
                       @click="handleUpdate(scope.$index, scope.row)">编辑
            </el-button>
            <el-button size="mini"
                       type="text"
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
    import {getCatalogList as fetchList, del as deleteHelpCategory, edit, updateVerifyStatus} from '@/api/catalog'
    import { formatTimeTwo, parseTime } from '@/utils/index'

    export default {
        name: 'catalogList',
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
            handleSelectionChange(val) {
                this.multipleSelection = val;
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
            handleDelete(index,row){
              this.del(row.id);
            },
            handleUpdate(index,row){
              this.$router.push({path: '/shop/catalogUpdate', query: {id: row.id}})
            },
            addHelpCategory() {
                this.$router.push({path: '/shop/catalogAdd'})
            }
        }
    }
</script>
<style rel="stylesheet/scss" lang="scss" scoped>


</style>


