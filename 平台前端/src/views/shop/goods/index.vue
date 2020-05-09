<template>
  <div class="app-container">
    <div class="head-container">
      <el-button
          type="danger"
          class="filter-item"
          size="mini"
          icon="el-icon-refresh"
          @click="crud.toQuery"
        >刷新</el-button>
    </div>
    <!--工具栏-->
    <div class="head-container">
      <!-- 搜索 -->
      <el-input v-model="query.value" clearable placeholder="输入搜索内容" style="width: 200px;" class="filter-item" @keyup.enter.native="toQuery" />
      <el-select v-model="query.type" clearable placeholder="类型" class="filter-item" style="width: 130px">
        <el-option v-for="item in queryTypeOptions" :key="item.key" :label="item.display_name" :value="item.key" />
      </el-select>
      <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="crud.toQuery">搜索</el-button>
    </div>
    <!--表单组件-->
    <!--表格渲染-->
    <el-table v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;">
      <el-table-column prop="id" label="商品id" />
      <el-table-column ref="table" prop="image" label="商品图片">
        <template slot-scope="scope">
          <a :href="scope.row.image" style="color: #42b983" target="_blank"><img :src="scope.row.image" alt="点击打开" class="el-avatar"></a>
        </template>
      </el-table-column>
      <el-table-column prop="productName" label="商品名称" />
      <el-table-column prop="cateName" label="分类名称" />
      <el-table-column prop="storeCateName" label="店铺分类名称" />
      <el-table-column prop="brandName" label="品牌名称" />
      <el-table-column prop="price" label="商品价格" />
      <el-table-column prop="sales" label="销量" />
      <el-table-column prop="stock" label="库存" />
      <el-table-column label="状态" align="center">
          <template slot-scope="scope">
            <div @click="add(scope.row)">
              <el-tag v-if="scope.row.verifyStatus === 1" style="cursor: pointer" :type="''">审核通过</el-tag>
              <el-tag v-else style="cursor: pointer" :type=" 'info' ">审核不通过</el-tag>
            </div>
          </template>
        </el-table-column>
    </el-table>
    <!--分页组件-->
    <pagination />
  </div>
</template>

<script>
import { add }from '@/api/productVertifyRecord'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

//const defaultCrud = CRUD({ title: '商品', url: 'api/product', crudmethod: { ...curdproduct }})
const defaultCrud = CRUD({ title: '商品', url: 'api/product'})
const defaultForm = { id: null, productName: null, cateName: null, price: 0, sales: 0, stock: 0 }

export default {
  name: 'Product',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      delLoading: false,
      visible: false,
      queryTypeOptions: [
        { key: 'productName', display_name: '商品名称' }
      ],
      isAttr: false,
      isAdd: true,
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 12, // 每页显示多少条
        ascs: [], // 升序字段
        descs: 'add_time'// 降序字段
      },
    }
  },
  methods: {
    // 提交前的验证
    [CRUD.HOOK.afterValidateCU]() {
      return true
    },
    add(data) {
      // this.isAdd = false
      // const _this = this.$refs.form
      // _this.form = {
      //   productId: data.id,
      //   category: data.cateId
      // }
      // _this.dialog = true
      this.$confirm(`确定进行[${data.verifyStatus ? '不通过' : '通过'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          var that = this;
          add({ status: data.verifyStatus, productId: data.id }).then(({ res }) => {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1000,
              onClose: () => {
                that.toQuery()
              }
            })
          })
        })
        .catch(() => { })
    },
    toQuery(){
      this.crud.toQuery()
    }
  }
}
</script>

<style scoped>

</style>
