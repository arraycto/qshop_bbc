<template>
  <el-card class="form-container" shadow="never">
    <el-form
      ref="baseMarkFrom"
      :model="baseMark"
      :rules="rules"
      label-width="150px"
      size="small"
    >
      <el-form-item label="活动类型：">
        <el-select v-model="baseMark.bigType">
          <el-option
            v-for="type in typeOptions"
            :key="type.value"
            :label="type.label"
            :value="type.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="活动名称：" prop="name">
        <el-input v-model="baseMark.name" class="input-width" />
      </el-form-item>

      <el-form-item label="开始时间：" prop="startTime">
        <el-date-picker
          v-model="baseMark.startTime"
          type="datetime"
          placeholder="选择日期"
        />
      </el-form-item>
      <el-form-item label="到期时间：" prop="endTime">
        <el-date-picker
          v-model="baseMark.endTime"
          type="datetime"
          placeholder="选择日期"
        />
      </el-form-item>
      <el-form-item label="活动对象：">
        <el-radio-group v-model="baseMark.activiUser">
          <el-radio :label="1">所有用户</el-radio>
          <el-radio :label="2">指定级别</el-radio>
        </el-radio-group>
        <el-form-item v-show="baseMark.activiUser===2">

          <el-select
            v-model="selectMemberLevel"
            filterable
            remote
            reserve-keyword
            placeholder="请选择会员级别"
          >
            <el-option
              v-for="item in memberLevleOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
              <span style="float: left">{{ item.name }}</span>

            </el-option>
          </el-select>
          <el-button @click="handleAddMemberLevel()">添加</el-button>
          <el-table
            ref="memberLevelTable"
            :data="baseMark.memberLevelList"
            style="width: 100%;margin-top: 20px"
            border
          >
            <el-table-column label="会员级别名称" align="center">
              <template slot-scope="scope">{{ scope.row.name }}</template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="100">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  @click="handleDeleteMemberLevel(scope.$index, scope.row)"
                >删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
      </el-form-item>
      <el-form-item label="可使用商品：">
        <el-radio-group v-model="baseMark.activiGoods">
          <el-radio-button :label="3">全场通用</el-radio-button>
          <el-radio-button :label="1">指定分类</el-radio-button>
          <el-radio-button :label="2">指定商品</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-show="baseMark.activiGoods===1">
        <el-cascader
          v-model="selectProductCate"
          clearable
          placeholder="请选择分类名称"
          :options="productCateOptions"
        />
        <el-button @click="handleAddProductCategoryRelation()">添加</el-button>
        <el-table
          ref="productCateRelationTable"
          :data="baseMark.productCategoryRelationList"
          style="width: 100%;margin-top: 20px"
          border
        >
          <el-table-column label="分类名称" align="center">
            <template slot-scope="scope">{{ scope.row.name1 }}>{{ scope.row.name }}</template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="100">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                @click="handleDeleteProductCateRelation(scope.$index, scope.row)"
              >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form-item>
      <el-form-item v-show="baseMark.activiGoods===2">
        <el-select
          v-model="selectProduct"
          filterable
          remote
          reserve-keyword
          placeholder="商品名称/商品货号"
          :remote-method="searchProductMethod"
          :loading="selectProductLoading"
        >
          <el-option
            v-for="item in selectProductOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
            <span style="float: left">{{ item.name }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">NO.{{ item.name1 }}</span>
          </el-option>
        </el-select>
        <el-button @click="handleAddProductRelation()">添加</el-button>
        <el-table
          ref="productRelationTable"
          :data="baseMark.productRelationList"
          style="width: 100%;margin-top: 20px"
          border
        >
          <el-table-column label="商品名称" align="center">
            <template slot-scope="scope">{{ scope.row.name }}</template>
          </el-table-column>

          <el-table-column label="操作" align="center" width="100">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                @click="handleDeleteProductRelation(scope.$index, scope.row)"
              >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form-item>
      <el-form-item label="优惠类型：">
        <el-radio-group v-model="baseMark.smallType">
          <el-radio :label="1">消费金额</el-radio>
          <el-radio :label="2">指定件数</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-show="baseMark.smallType===1" label="优惠设置：">

        <el-table
          :data="baseMark.actrule"
          style="width: 80%"
          border
        >
          <el-table-column
            label="满"
            align="center"
            width="120"
          >
            <template slot-scope="scope">
              <el-input v-model="scope.row.fullPrice" />
            </template>
          </el-table-column>
          <el-table-column
            label="立减"
            align="center"
            width="120"
          >
            <template slot-scope="scope">
              <el-input v-model="scope.row.reducePrice" />
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            label="操作"
          >
            <template slot-scope="scope">
              <el-button type="text" @click="handleRemoveFullReduction(scope.$index, scope.row)">删除</el-button>
              <el-button type="text" @click="handleAddFullReduction(scope.$index, scope.row)">添加</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form-item>
      <el-form-item v-show="baseMark.smallType===2" label="优惠设置：">

        <el-table
          :data="baseMark.actrule1"
          style="width: 80%"
          border
        >
          <el-table-column
            label="满件"
            align="center"
            width="120"
          >
            <template slot-scope="scope">
              <el-input v-model="scope.row.fullPrice" />
            </template>
          </el-table-column>
          <el-table-column
            label="立减"
            align="center"
            width="120"
          >
            <template slot-scope="scope">
              <el-input v-model="scope.row.reducePrice" />
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            label="操作"
          >
            <template slot-scope="scope">
              <el-button type="text" @click="handleRemoveFullReduction1(scope.$index, scope.row)">删除</el-button>
              <el-button type="text" @click="handleAddFullReduction1(scope.$index, scope.row)">添加</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form-item>
      <el-form-item label="备注：">
        <el-input
          v-model="baseMark.note"
          class="input-width"
          type="textarea"
          :rows="5"
          placeholder="请输入内容"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('baseMarkFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('baseMarkFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
// eslint-disable-next-line no-unused-vars
import { add, del, getMarkingDetail, edit } from '@/api/basicMarking'
import { getProduct } from '@/api/product'
import { getCates } from '@/api/category'
import { getUserLevel } from '@/api/systemUserLevel'
const defaultTypeOptions = [
  {
    label: '满额立减',
    value: 1
  },
  {
    label: '限时折扣',
    value: 2
  }
]
const defaultHomeAdvertise = {
  productRelationList: [],
  memberLevelList: [],
  productCategoryRelationList: [],
  // 商品满减
  actrule: [{ fullPrice: 0, reducePrice: 0 }],
  // 购买件数
  actrule1: [{ fullPrice: 0, reducePrice: 0 }],
  name: null,
  type: 1,
  pic: null,
  startTime: null,
  endTime: null,
  status: 0,
  url: null,
  note: null,
  sort: 0
}
export default {
  name: 'BaseMarkDetail',
  components: {},
  props: {
    // isEdit: {
    //   type: Boolean,
    //   default: false
    // }
  },
  data() {
    return {
      isEdit: false,
      baseMark: null,
      rules: {
        name: [
          { required: true, message: '请输入广告名称', trigger: 'blur' },
          { min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur' }
        ],
        url: [
          { required: true, message: '请输入广告链接', trigger: 'blur' }
        ],
        startTime: [
          { required: true, message: '请选择开始时间', trigger: 'blur' }
        ],
        endTime: [
          { required: true, message: '请选择到期时间', trigger: 'blur' }
        ],
        pic: [
          { required: true, message: '请选择广告图片', trigger: 'blur' }
        ]
      },
      typeOptions: Object.assign({}, defaultTypeOptions),
      selectProduct: null,
      selectProductLoading: false,
      selectProductOptions: [],
      selectProductCate: null,
      selectMemberLevel: null,
      memberLevleOptions: [],
      productCateOptions: []
    }
  },
  created() {
    if (this.$route.query.id) {
      this.isEdit = true
      getMarkingDetail(this.$route.query.id).then(response => {
        this.baseMark = response
        if (this.baseMark.productCategoryRelationList === null) {
          this.baseMark.productCategoryRelationList = []
        }
        if (this.baseMark.productRelationList === null) {
          this.baseMark.productRelationList = []
        }
        if (this.baseMark.memberLevelList === null) {
          this.baseMark.memberLevelList = []
        }
        if (this.baseMark.memberLevelList === null) {
          this.baseMark.memberLevelList = []
        }
      })
    } else {
      this.baseMark = Object.assign({}, defaultHomeAdvertise)
    }
    this.getProductCateList()
    this.searchMemberLevel()
  },
  methods: {
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$confirm('是否提交数据', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            if (this.isEdit) {
              edit(this.baseMark).then(response => {
                this.$refs[formName].resetFields()
                this.$message({
                  message: '修改成功',
                  type: 'success',
                  duration: 1000
                })
                this.$router.back()
              })
            } else {
              add(this.baseMark).then(response => {
                this.$refs[formName].resetFields()
                this.baseMark = Object.assign({}, defaultHomeAdvertise)
                this.$message({
                  message: '提交成功',
                  type: 'success',
                  duration: 1000
                })
                this.$router.back()
              })
            }
          })
        } else {
          this.$message({
            message: '验证失败',
            type: 'error',
            duration: 1000
          })
          return false
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
      this.baseMark = Object.assign({}, defaultHomeAdvertise)
    },
    searchMemberLevel() {
      getUserLevel({ defaultStatus: 0 }).then(response => {
        const memberPriceList = []
        for (let i = 0; i < response.content.length; i++) {
          const item = response.content[i]
          memberPriceList.push({ id: item.id, name: item.name })
        }
        this.memberLevleOptions = memberPriceList
      })
    },
    searchProductMethod(query) {
      if (query !== '') {
        this.loading = true
        getProduct({ productName: query }).then(response => {
          this.loading = false
          const productList = response.content
          this.selectProductOptions = []
          for (let i = 0; i < productList.length; i++) {
            const item = productList[i]
            this.selectProductOptions.push({ id: item.id, name: item.productName, name1: item.barCode })
          }
        })
      } else {
        this.selectProductOptions = []
      }
    },
    handleAddProductRelation() {
      if (this.selectProduct === null) {
        this.$message({
          message: '请先选择商品',
          type: 'warning'
        })
        return
      }
      this.baseMark.productRelationList.push(this.getProductById(this.selectProduct))
      this.selectProduct = null
    },
    handleDeleteProductRelation(index, row) {
      this.baseMark.productRelationList.splice(index, 1)
    },
    handleAddProductCategoryRelation() {
      if (this.selectProductCate === null || this.selectProductCate.length === 0) {
        this.$message({
          message: '请先选择商品分类',
          type: 'warning'
        })
        return
      }
      this.baseMark.productCategoryRelationList.push(this.getProductCateByIds(this.selectProductCate))
      this.selectProductCate = []
    },
    handleDeleteProductCateRelation(index, row) {
      this.baseMark.productCategoryRelationList.splice(index, 1)
    },
    getMemberLevelById(id) {
      for (let i = 0; i < this.memberLevleOptions.length; i++) {
        if (id === this.memberLevleOptions[i].id) {
          return this.memberLevleOptions[i]
        }
      }
      return null
    },
    getProductById(id) {
      for (let i = 0; i < this.selectProductOptions.length; i++) {
        if (id === this.selectProductOptions[i].id) {
          return this.selectProductOptions[i]
        }
      }
      return null
    },
    getProductCateList() {
      getCates().then(response => {
        const list = response.content
        this.productCateOptions = []
        for (let i = 0; i < list.length; i++) {
          const children = []
          if (list[i].children != null && list[i].children.length > 0) {
            for (let j = 0; j < list[i].children.length; j++) {
              children.push({ label: list[i].children[j].cateName, value: list[i].children[j].id })
            }
          }
          this.productCateOptions.push({ label: list[i].cateName, value: list[i].id, children: children })
        }
      })
    },
    getProductCateByIds(ids) {
      let name
      let parentName
      for (let i = 0; i < this.productCateOptions.length; i++) {
        if (this.productCateOptions[i].value === ids[0]) {
          parentName = this.productCateOptions[i].label
          for (let j = 0; j < this.productCateOptions[i].children.length; j++) {
            if (this.productCateOptions[i].children[j].value === ids[1]) {
              name = this.productCateOptions[i].children[j].label
            }
          }
        }
      }
      return { id: ids[1], name: name, name1: parentName }
    },
    handleAddMemberLevel() {
      if (this.selectMemberLevel === null || this.selectMemberLevel.length === 0) {
        this.$message({
          message: '请先选择会员级别',
          type: 'warning'
        })
        return
      }
      this.baseMark.memberLevelList.push(this.getMemberLevelById(this.selectMemberLevel))
      this.selectMemberLevel = []
    },
    handleDeleteMemberLevel(index, row) {
      this.baseMark.memberLevelList.splice(index, 1)
    },

    handleRemoveFullReduction(index, row) {
      const fullReductionList = this.baseMark.actrule
      if (fullReductionList.length === 1) {
        fullReductionList.pop()
        fullReductionList.push({
          fullPrice: 0,
          reducePrice: 0
        })
      } else {
        fullReductionList.splice(index, 1)
      }
    },
    handleAddFullReduction(index, row) {
      const fullReductionList = this.baseMark.actrule
      if (fullReductionList.length < 3) {
        fullReductionList.push({
          fullPrice: 0,
          reducePrice: 0
        })
      } else {
        this.$message({
          message: '最多只能添加三条',
          type: 'warning'
        })
      }
    },
    handleRemoveFullReduction1(index, row) {
      const fullReductionList = this.baseMark.actrule1
      if (fullReductionList.length === 1) {
        fullReductionList.pop()
        fullReductionList.push({
          fullPrice: 0,
          reducePrice: 0
        })
      } else {
        fullReductionList.splice(index, 1)
      }
    },
    handleAddFullReduction1(index, row) {
      const fullReductionList = this.baseMark.actrule1
      if (fullReductionList.length < 3) {
        fullReductionList.push({
          fullPrice: 0,
          reducePrice: 0
        })
      } else {
        this.$message({
          message: '最多只能添加三条',
          type: 'warning'
        })
      }
    }

  }

}
</script>
<style scoped>
  .input-width {
    width: 70%;
  }
</style>
