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
                        <el-input style="width: 203px" v-model="listQuery.companyName" placeholder="店铺名称"></el-input>
                    </el-form-item>
                    <el-form-item label="选择店铺审核状态：">
                      <el-select
                        v-model="listQuery.status"
                        placeholder="审核状态"
                        clearable
                        style="width: 150px"
                      >
                        <el-option
                          v-for="(item, index) in statusList"
                          :key="index"
                          :label="item.value"
                          :value="item.id"
                        />
                      </el-select>
                    </el-form-item>
                </el-form>
            </div>
        </el-card>
        <el-card class="operate-container" shadow="never">
            <i class="el-icon-tickets"></i>
            <span>数据列表</span>
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
                <el-table-column label="店铺名称" align="center">
                    <template slot-scope="scope">{{scope.row.companyName}}</template>
                </el-table-column>
                <el-table-column label="法人姓名" align="center">
                    <template slot-scope="scope">{{scope.row.representative}}</template>
                </el-table-column>
                <el-table-column label="营业执照号" align="center">
                    <template slot-scope="scope">{{scope.row.licenseNo}}</template>
                </el-table-column>
                <el-table-column label="法人身份证号" align="center">
                    <template slot-scope="scope">{{scope.row.idNo}}</template>
                </el-table-column>
                <el-table-column label="法人身份证反面" align="center">
                  <template slot-scope="scope"><a :href="scope.row.representIdentity1" style="color: #42b983" target="_blank"><img style="height: 80px" :src="scope.row.representIdentity1"></a></template>
                </el-table-column>
                <el-table-column label="法人身份证正面" align="center">
                  <template slot-scope="scope"><a :href="scope.row.representIdentity2" style="color: #42b983" target="_blank"><img style="height: 80px" :src="scope.row.representIdentity1"></a></template>
                </el-table-column>
                <el-table-column label="营业执照开始时间" align="center">
                    <template slot-scope="scope">
                    {{parseTime(scope.row.licenseStartTime)}}</template>
                </el-table-column>
                <el-table-column label="营业执照结束时间" align="center">
                    <template slot-scope="scope">
                    {{parseTime(scope.row.licenseEndTime)}}</template>
                </el-table-column>
                <el-table-column label="添加时间" align="center">
                  <template slot-scope="scope">
                    <span>{{ parseTime(scope.row.addTime) }}</span>
                  </template>
                </el-table-column>
                <el-table-column label="更新时间" align="center">
                  <template slot-scope="scope">
                    <span>{{ parseTime(scope.row.modifyTime) }}</span>
                  </template>
                </el-table-column>
                <!-- <el-table-column label="审核状态" align="center">
                  <template slot-scope="scope">
                    <el-switch
                      @change="handleVerifyStatusChange(scope.row, scope.$index)"
                      :active-value="1"
                      :inactive-value="0"
                      v-model="scope.row.status">
                    </el-switch>
                  </template>
                </el-table-column> -->
                <el-table-column label="排序" align="center">
                    <template slot-scope="scope">{{scope.row.sort}}</template>
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
              <el-table-column label="状态" align="center">
                <template slot-scope="scope">
                  <el-switch
                      @change="handleOnClosed(scope.row, scope.$index)"
                      :active-value="1"
                      :inactive-value="0"
                      v-model="scope.row.closed">
                  </el-switch>
                </template>
              </el-table-column>
            </el-table>
        </div>
        <el-dialog
      title="审核信息"
      :visible.sync="vertyProduct.dialogVisible"
      width="40%">
      <el-form :model="vertyProduct"  ref="brandFrom" label-width="150px">
        <el-form-item label="审核状态：" prop="status">
          <el-switch v-model="vertyProduct.status" :active-value="1" :inactive-value="0"></el-switch>
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
    </div>
</template>
<script>
    import { onVerify, onClosed } from '@/api/shop'
    import { getStoreSettle as getShopList, getStoreVertifyRecord as fetchVList } from '@/api/storeSettle'
    import { formatTimeTwo, parseTime } from '@/utils/index'
    export default {
        name: 'subjectCategoryList',
        components: {formatTimeTwo, parseTime},
        data() {
            return {
                listQuery: {
                    companyName: null,
                    status: null,
                    page: 0,
                    size: 10
                },
                vertyProduct:{
                    dialogVisible:false,
                    id:null,
                    detail:'',
                    status:null,
                    list:null,
                    storeId: null
                },
                list: null,
                total: null,
                listLoading: true,
                multipleSelection: [],
                statusList: [{
                    id:1,
                    value:'审核通过'
                  }, 
                  {
                    id: 2,
                    value: '审核不通过'
                  }]
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
                getShopList(this.listQuery).then(response => {
                    this.listLoading = false;
                    this.list = response.content;
                    this.total = response.totalElements;
                });
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            handleShowVeriyEditDialog(index,row){
                this.vertyProduct.dialogVisible=true;
                this.vertyProduct.id=row.id;
                this.vertyProduct.status=row.status;
                this.vertyProduct.storeId = row.storeId;
                fetchVList({storeId:row.storeId}).then(response=>{
                    this.vertyProduct.list=response.content;
                });
            },
            // handleVerifyStatusChange(row, status){
            //     this.$confirm(`确定进行[${status ? '不通过' : '通过'}]操作?`, '提示', {
            //       confirmButtonText: '确定',
            //       cancelButtonText: '取消',
            //       type: 'warning'
            //     })
            //       .then(() => {
            //         onVerify({ status: status, storeId: row.storeId }).then(({ data }) => {
            //           this.$message({
            //             message: '操作成功',
            //             type: 'success',
            //             duration: 1000,
            //             onClose: () => {
            //               this.init()
            //             }
            //           })
            //         })
            //       })
            //       .catch(() => { })
            // },
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
                        status: this.vertyProduct.status,
                        detail: this.vertyProduct.detail,
                        storeId: this.vertyProduct.storeId
                    };

                    onVerify(params).then(response => {
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
            handleOnClosed(row, status){
              this.$confirm(`确定进行[${status ? '关闭' : '开启'}]操作?`, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              })
                .then(() => {
                  onClosed({ status: status, storeId: row.storeId }).then(({ data }) => {
                    this.$message({
                      message: '操作成功',
                      type: 'success',
                      duration: 1000,
                      onClose: () => {
                        this.init()
                      }
                    })
                  })
                })
                .catch(() => { })
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
            }
        }
    }
</script>
<style rel="stylesheet/scss" lang="scss" scoped>


</style>


