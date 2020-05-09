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
                <el-table-column label="品牌名称" align="center">
                    <template slot-scope="scope">{{scope.row.name}}</template>
                </el-table-column>
                <el-table-column label="首字母" align="center">
                    <template slot-scope="scope">{{scope.row.firstLetter}}</template>
                </el-table-column>
                <el-table-column label="排序" align="center">
                    <template slot-scope="scope">{{scope.row.sort}}</template>
                </el-table-column>
                <el-table-column label="品牌制造商" align="center">
                    <template slot-scope="scope">{{scope.row.factory}}</template>
                </el-table-column>
                <el-table-column label="显示" align="center">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.showStatus === 0" style="cursor: pointer" :type="''">隐藏</el-tag>
                        <el-tag v-else  style="cursor: pointer" :type=" 'h5' ">显示</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="产品数量" align="center">
                    <template slot-scope="scope">{{scope.row.productCount|filterCount}}</template>
                </el-table-column>
                <el-table-column label="品牌logo">
                <template slot-scope="scope">
                    <a :href="scope.row.logo" style="color: #42b983" target="_blank"><img :src="scope.row.logo" alt="点击打开" class="el-avatar"></a>
                </template>
                </el-table-column>
                <el-table-column label="专区大图">
                <template slot-scope="scope">
                    <a :href="scope.row.bigPic" style="color: #42b983" target="_blank"><img :src="scope.row.bigPic" alt="点击打开" class="el-avatar"></a>
                </template>
                </el-table-column>
                <el-table-column label="品牌故事" align="center">
                    <template slot-scope="scope">{{scope.row.brandStory}}</template>
                </el-table-column>
                <el-table-column label="创建时间" align="center">
                    <template slot-scope="scope">{{parseTime(scope.row.addTime)}}</template>
                </el-table-column>
                <el-table-column label="更新时间">
                <template slot-scope="scope">
                    <span>{{ parseTime(scope.row.modifyTime) }}</span>
                </template>
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
    import {getBrandList as fetchList, del as deleteHelpCategory, edit} from '@/api/brand'
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
        filters: {
            filterCount: function(val){
                if (val==null || val == undefined){
                    return 0;
                }
            }
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
              this.$router.push({path: '/shop/shopBrandUpdate', query: {id: row.id}})
            },
            addHelpCategory() {
                this.$router.push({path: '/shop/shopBrandAdd'})
            }
        }
    }
</script>
<style rel="stylesheet/scss" lang="scss" scoped>


</style>


