<template> 
    <div class="app-container">
        <el-card class="filter-container" shadow="never">
            <div>
                <i class="el-icon-search"></i>
                <span>筛选搜索</span>
                <el-button
                        style="float: right"
                        @click="searchSubjectCategoryList()"
                        type="primary"
                        size="small">
                    查询结果
                </el-button>
            </div>
            <div style="margin-top: 15px">
                <el-form :inline="true" :model="listQuery" size="small" label-width="140px">
                    <el-form-item label="输入搜索：">
                        <el-input style="width: 203px" v-model="listQuery.keyword" placeholder="品牌名称/关键字"></el-input>
                    </el-form-item>
                </el-form>
            </div>
        </el-card>
        <el-card class="operate-container" shadow="never">
            <i class="el-icon-tickets"></i>
            <span>数据列表</span>
            <el-button
                    class="btn-add"
                    @click="addSubjectCategory()"
                    size="mini">
                添加
            </el-button>
        </el-card>
        <div class="table-container">
            <el-table ref="subjectCategoryTable"
                      :data="list"
                      style="width: 100%"
                      @selection-change="handleSelectionChange"
                      v-loading="listLoading"
                      border>
                <el-table-column type="selection" width="60" align="center"></el-table-column>

                <el-table-column label="编号" align="center">
                    <template slot-scope="scope">{{scope.row.id}}</template>
                </el-table-column>
                <el-table-column label="标题" align="center">
                    <template slot-scope="scope">{{scope.row.name}}</template>
                </el-table-column>
                <el-table-column label="分类图标" align="center">
                  <template slot-scope="scope"><img style="height: 80px" :src="scope.row.icon"></template>
                </el-table-column>
                <el-table-column label="专题数量" align="center">
                    <template slot-scope="scope">{{scope.row.subjectCount}}</template>
                </el-table-column>
                <el-table-column label="状态" align="center">
                  <template slot-scope="scope">
                    <el-switch
                      @change="handleStatusStatusChange(scope.$index, scope.row)"
                      :active-value="1"
                      :inactive-value="0"
                      v-model="scope.row.showStatus">
                    </el-switch>
                  </template>
                </el-table-column>
                <el-table-column label="排序" align="center">
                    <template slot-scope="scope">{{scope.row.sort}}</template>
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
    </div>
</template>
<script>
    import {getCmsSubjectCategory, del} from '@/api/cmsSubjectCategory'

    export default {
        name: 'subjectCategoryList',
        data() {
            return {
                operates: [
                    {
                        label: "显示品牌",
                        value: "showSubjectCategory"
                    },
                    {
                        label: "隐藏品牌",
                        value: "hideSubjectCategory"
                    }
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
            getList() {
                this.listLoading = true;
                getCmsSubjectCategory(this.listQuery).then(response => {
                    this.listLoading = false;
                this.list = response.content;
                this.total = response.totalElements;
            });
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            handleUpdate(index, row) {
                this.$router.push({path: '/cms/cmsSubjectCateUpdate', query: {id: row.id}})
            },
            handleDelete(index, row) {
                this.$confirm('是否要删除该数据', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
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
            getProductList(index, row) {
                console.log(index, row);
            },
            getProductCommentList(index, row) {
                console.log(index, row);
            },


            handleSizeChange(val) {
                this.listQuery.page = 0;
                this.listQuery.pageSize = val;
                this.getList();
            },
            handleCurrentChange(val) {
                this.listQuery.page = val-1;
                this.getList();
            },
            searchSubjectCategoryList() {
                this.listQuery.page = 0;
                this.getList();
            },
            handleBatchOperate() {
                console.log(this.multipleSelection);
                if (this.multipleSelection < 1) {
                    this.$message({
                        message: '请选择一条记录',
                        type: 'warning',
                        duration: 1000
                    });
                    return;
                }
                let showStatus = 0;
                if (this.operateType === 'showSubjectCategory') {
                    showStatus = 1;
                } else if (this.operateType === 'hideSubjectCategory') {
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
                data.append("showStatus", showStatus);
                updateShowStatus(data).then(response => {
                    this.getList();
                this.$message({
                    message: '修改成功',
                    type: 'success',
                    duration: 1000
                });
            });
            },
            addSubjectCategory() {
                this.$router.push({path: '/cms/cmsSubjectCateAdd'})
            }
        }
    }
</script>
<style rel="stylesheet/scss" lang="scss" scoped>


</style>


