<template> 
    <div class="app-container">
        <el-card class="filter-container" shadow="never">
            <div>
                <i class="el-icon-search"></i>
                <span>筛选搜索</span>
                <el-button
                        style="float: right"
                        @click="searchSubjectList()"
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
                    @click="addSubject()"
                    size="mini">
                添加
            </el-button>
        </el-card>
        <div class="table-container">
            <el-table ref="subjectTable"
                      :data="list"
                      style="width: 100%"
                      @selection-change="handleSelectionChange"
                      v-loading="listLoading"
                      border>
                <el-table-column type="selection" width="60" align="center"></el-table-column>

                <el-table-column label="编号" align="center">
                    <template slot-scope="scope">{{scope.row.id}}</template>
                </el-table-column>
                <el-table-column label="分类" align="center">
                    <template slot-scope="scope">{{scope.row.categoryId}}</template>
                </el-table-column>
              <el-table-column label="专题分类名称" align="center">
                <template slot-scope="scope">{{scope.row.categoryName}}</template>
              </el-table-column>
                <el-table-column label="标题" align="center">
                    <template slot-scope="scope">{{scope.row.title}}</template>
                </el-table-column>
                <el-table-column label="专题主图" align="center">
                  <template slot-scope="scope"><img style="height: 80px" :src="scope.row.pic"></template>
                </el-table-column>
                <el-table-column label="关联产品数量" align="center">
                    <template slot-scope="scope">{{scope.row.productCount}}</template>
                </el-table-column>
                <el-table-column label="推荐" align="center">
                  <template slot-scope="scope">
                  <el-switch
                    @change="handleRecomChange(scope.$index, scope.row)"
                    :active-value="1"
                    :inactive-value="0"
                    v-model="scope.row.recommendStatus">
                  </el-switch>
                  </template>
                </el-table-column>

                <el-table-column label="收藏量" align="center">
                    <template slot-scope="scope">{{scope.row.collectCount}}</template>
                </el-table-column>
                <el-table-column label="点击量" align="center">
                    <template slot-scope="scope">{{scope.row.readCount}}</template>
                </el-table-column>
                <el-table-column label="评论量" align="center">
                    <template slot-scope="scope">{{scope.row.commentCount}}</template>
                </el-table-column>

                <el-table-column label="显示状态" align="center">
                  <template slot-scope="scope">
                    <el-switch
                      @change="handleShowChange(scope.$index, scope.row)"
                      :active-value="1"
                      :inactive-value="0"
                      v-model="scope.row.showStatus">
                    </el-switch>
                  </template>
                </el-table-column>

                <el-table-column label="转发数" align="center">
                    <template slot-scope="scope">{{scope.row.forwardCount}}</template>
                </el-table-column>

              <el-table-column label="创建时间" align="center">
                <template slot-scope="scope">{{parseTime(scope.row.addTime)}}</template>
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
  import { formatTimeTwo, parseTime } from '@/utils/index'
    import {getCmsSubject, del,edit} from '@/api/cmsSubject'

    export default {
        name: 'subjectList',
        data() {
            return {
                operates: [
                    {
                        label: "显示品牌",
                        value: "showSubject"
                    },
                    {
                        label: "隐藏品牌",
                        value: "hideSubject"
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
      filters: {
        formatStatus(value) {
          if (value === 1) {
            return '未开始';
          } else if (value === 2) {
            return '活动中';
          } else if (value === 3) {
            return '已结束';
          } else if (value === 4) {
            return '已失效';
          }
        },
      },
        methods: {
            parseTime,
            formatTimeTwo,
            getList() {
                this.listLoading = true;
                getCmsSubject(this.listQuery).then(response => {
                    this.listLoading = false;
                this.list = response.content;
                this.total = response.totalElements;
            });
            },
          handleRecomChange(index, row) {
            let params = new URLSearchParams();
            params.append('ids', row.id);
            params.append('recommendStatus', row.recommendStatus);
            edit(params).then(response => {
              this.$message({
              message: '修改成功',
              type: 'success',
              duration: 1000
            });
          });
          },
          handleShowChange(index, row) {
            let params = new URLSearchParams();
            params.append('ids', row.id);
            params.append('showStatus', row.showStatus);
            edit(params).then(response => {
              this.$message({
              message: '修改成功',
              type: 'success',
              duration: 1000
            });
          });
          },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            handleUpdate(index, row) {
                this.$router.push({path: '/cms/cmsSubjectUpdate', query: {id: row.id}})
            },
            handleDelete(index, row) {
                this.$confirm('是否要删除该品牌', '提示', {
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
                this.listQuery.size = val;
                this.getList();
            },
            handleCurrentChange(val) {
                this.listQuery.page = val-1;
                this.getList();
            },
            searchSubjectList() {
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
                if (this.operateType === 'showSubject') {
                    showStatus = 1;
                } else if (this.operateType === 'hideSubject') {
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
            addSubject() {
                this.$router.push({path: '/cms/cmsSubjectAdd'})
            }
        }
    }
</script>
<style rel="stylesheet/scss" lang="scss" scoped>


</style>


