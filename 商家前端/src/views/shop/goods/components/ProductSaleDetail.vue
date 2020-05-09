<template>
  <div style="margin-top: 50px">
    <el-form :model="value" :rules="rules" ref="productSaleForm" label-width="120px" style="width: 600px" size="small">
        <el-form-item label="赠送积分：">
        <el-input v-model="value.giveIntegral"></el-input>
      </el-form-item>
      <el-form-item label="商品上架：">
        <el-switch
          v-model="value.isShow"
          :active-value="1"
          :inactive-value="0">
        </el-switch>
      </el-form-item>
      <!-- <el-form-item label="商品推荐：">
        <span style="margin-right: 10px">新品</span>
        <el-switch
          v-model="value.isNew"
          :active-value="1"
          :inactive-value="0">
        </el-switch>
        <span style="margin-left: 10px;margin-right: 10px">推荐</span>
        <el-switch
          v-model="value.isGood"
          :active-value="1"
          :inactive-value="0">
        </el-switch>
        <span style="margin-left: 10px;margin-right: 10px">热卖</span>
        <el-switch
          v-model="value.isHot"
          :active-value="1"
          :inactive-value="0">
        </el-switch>
        <span style="margin-left: 10px;margin-right: 10px">精品</span>
        <el-switch
          v-model="value.isBest"
          :active-value="1"
          :inactive-value="0">
        </el-switch>
        <span style="margin-left: 10px;margin-right: 10px">优惠</span>
        <el-switch
          v-model="value.isBenefit"
          :active-value="1"
          :inactive-value="0">
        </el-switch>
      </el-form-item> -->
      <el-form-item label="是否包邮：">
        <el-switch
          v-model="value.isPostage"
          :active-value="1"
          :inactive-value="0">
        </el-switch>
      </el-form-item>
      <el-form-item label="商品销量：">
        <el-input v-model="value.sales"></el-input>
      </el-form-item>
      <el-form-item label="商品关键字：" prop="keyword">
        <el-input v-model="value.keyword"></el-input>
      </el-form-item>
      <el-form-item style="text-align: center">
        <el-button size="medium" @click="handlePrev">上一步，填写商品信息</el-button>
        <el-button type="primary" size="medium" @click="handleNext">下一步，填写商品属性</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import { getUserLevel } from '@/api/systemUserLevel'

  export default {
    name: "ProductSaleDetail",
    props: {
      value: Object,
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        //日期选择器配置
        pickerOptions1: {
          disabledDate(time) {
            return time.getTime() < Date.now();
          }
        },
        rules: {
          keyword: [{required: true, message: '请输入商品关键字', trigger: 'blur'}]
        }
      }
    },
    created() {
      if (this.isEdit) {
        // this.handleEditCreated();
      } else {
        getUserLevel({defaultStatus: 0}).then(response => {
          let memberPriceList = [];
          for (let i = 0; i < response.content.length; i++) {
            let item = response.content[i];
            memberPriceList.push({memberLevelId: item.id, memberLevelName: item.name})
          }
          this.value.memberPriceList = memberPriceList;
        });
      }
    },
    computed: {
      //选中的服务保证
      selectServiceList: {
        get() {
          let list = [];
          if (this.value.serviceIds === undefined || this.value.serviceIds == null || this.value.serviceIds === '') return list;
          let ids = this.value.serviceIds.split(',');
          for (let i = 0; i < ids.length; i++) {
            list.push(Number(ids[i]));
          }
          return list;
        },
        set(newValue) {
          let serviceIds = '';
          if (newValue != null && newValue.length > 0) {
            for (let i = 0; i < newValue.length; i++) {
              serviceIds += newValue[i] + ',';
            }
            if (serviceIds.endsWith(',')) {
              serviceIds = serviceIds.substr(0, serviceIds.length - 1)
            }
            this.value.serviceIds = serviceIds;
          } else {
            this.value.serviceIds = null;
          }
        }
      }
    },
    methods: {
      handleEditCreated() {
        let ids = this.value.serviceIds.split(',');
        console.log('handleEditCreated', ids);
        for (let i = 0; i < ids.length; i++) {
          this.selectServiceList.push(Number(ids[i]));
        }
      },
      handleRemoveProductLadder(index, row) {
        let productLadderList = this.value.productLadderList;
        if (productLadderList.length === 1) {
          productLadderList.pop();
          productLadderList.push({
            count: 0,
            discount: 0,
            price: 0
          })
        } else {
          productLadderList.splice(index, 1);
        }
      },
      handleAddProductLadder(index, row) {
        let productLadderList = this.value.productLadderList;
        if (productLadderList.length < 3) {
          productLadderList.push({
            count: 0,
            discount: 0,
            price: 0
          })
        } else {
          this.$message({
            message: '最多只能添加三条',
            type: 'warning'
          });
        }
      },
      handleRemoveFullReduction(index, row) {
        let fullReductionList = this.value.productFullReductionList;
        if (fullReductionList.length === 1) {
          fullReductionList.pop();
          fullReductionList.push({
            fullPrice: 0,
            reducePrice: 0
          });
        } else {
          fullReductionList.splice(index, 1);
        }
      },
      handleAddFullReduction(index, row) {
        let fullReductionList = this.value.productFullReductionList;
        if (fullReductionList.length < 3) {
          fullReductionList.push({
            fullPrice: 0,
            reducePrice: 0
          });
        } else {
          this.$message({
            message: '最多只能添加三条',
            type: 'warning'
          });
        }
      },
      handlePrev() {
        this.$emit('prevStep')
      },
      handleNext() {
        this.$emit('nextStep')
      }
    }
  }
</script>

<style scoped>
  .littleMargin {
    margin-top: 10px;
  }
</style>