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
                <el-table-column label="属性名称" align="center">
                    <template slot-scope="scope">{{scope.row.attrName}}</template>
                </el-table-column>
                <el-table-column label="属性值" align="center">
                    <template slot-scope="scope">{{scope.row.inputlist}}</template>
                </el-table-column>
                <el-table-column label="是否规格">
          <template slot-scope="scope">
            <div>
                <el-tag v-if="scope.row.isSpec === 0" style="cursor: pointer" :type="''">否</el-tag>
                <el-tag v-else style="cursor: pointer" :type=" 'online' ">是</el-tag>
            </div>
          </template>
        </el-table-column>
                <el-table-column label="是否显示">
          <template slot-scope="scope">
            <div>
                <el-tag v-if="scope.row.isShow === 0" style="cursor: pointer" :type="''">隐藏</el-tag>
                <el-tag v-else style="cursor: pointer" :type=" 'online' ">显示</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="属性选择类型">
          <template slot-scope="scope">
            <div>
                <el-tag v-if="scope.row.selectType === 0" style="cursor: pointer" :type="''">唯一</el-tag>
                <el-tag v-else-if="scope.row.selectType === 1" style="cursor: pointer" :type=" 'single' ">单选</el-tag>
                <el-tag v-else style="cursor: pointer" :type=" 'mutiple' ">多选</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="分类筛选样式">
          <template slot-scope="scope">
            <div>
                <el-tag v-if="scope.row.filterType === 0" style="cursor: pointer" :type="''">普通</el-tag>
                <el-tag v-else style="cursor: pointer" :type=" 'mutiple' ">颜色</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="检索类型">
          <template slot-scope="scope">
            <div>
                <el-tag v-if="scope.row.searchType === 0" style="cursor: pointer" :type="''">不需要进行检索</el-tag>
                <el-tag v-else-if="scope.row.searchType === 1" style="cursor: pointer" :type=" 'single' ">关键字检索</el-tag>
                <el-tag v-else style="cursor: pointer" :type=" 'mutiple' ">范围检索</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="属性录入方式">
          <template slot-scope="scope">
            <div>
                <el-tag v-if="scope.row.inputType === 1" style="cursor: pointer" :type="''">手工录入</el-tag>
                <el-tag v-else style="cursor: pointer" :type=" 'mutiple' ">从列表中选取</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="相同属性产品是否关联">
          <template slot-scope="scope">
            <div>
                <el-tag v-if="scope.row.relatedStatus === 0" style="cursor: pointer" :type="''">不关联</el-tag>
                <el-tag v-else style="cursor: pointer" :type=" 'mutiple' ">关联</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="是否支持手动新增">
          <template slot-scope="scope">
            <div>
                <el-tag v-if="scope.row.handAddStatus === 0" style="cursor: pointer" :type="''">不支持</el-tag>
                <el-tag v-else style="cursor: pointer" :type=" 'mutiple' ">支持</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="创建时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.addTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="更新时间">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.modifyTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column>
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
    import {getCatalogAttrs, del} from '@/api/catalogAttr'
    import { formatTimeTwo, parseTime } from '@/utils/index'

    export default {
        name: 'CatalogAttr',
        components: {formatTimeTwo, parseTime},
        data() {
            return {
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
                getCatalogAttrs(this.listQuery).then(response => {
                    this.listLoading = false;
                    this.list = response.content;
                    this.total = response.totalElements;
            });
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            handleUpdate(index, row) {
                this.$router.push({path: '/shop/catalogAttrUpdate', query: {id: row.id}})
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
            addSubjectCategory() {
                this.$router.push({path: '/shop/catalogAttrAdd'})
            }
        }
    }
</script>
<style rel="stylesheet/scss" lang="scss" scoped>


</style>


