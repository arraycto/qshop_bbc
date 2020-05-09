<template>
  <div class="dashboard-container">
    <div class="dashboard-editor-container">
      <qshop-info />
      <panel-group />
      <panel-group-t />

      <el-row :gutter="32">
        <el-col :xs="24" :sm="24" :lg="12">

          <div class="chart-wrapper">
            <p>本月成交额</p>
            <bar-chart ref="barChat" v-bind:day="day" v-bind:num="num" />
          </div>
        </el-col>
        <el-col :xs="24" :sm="24" :lg="12">
          <div class="chart-wrapper">
            <p>本月订单数</p>
            <pie-chart ref="pieChat" v-bind:dayT="dayT" v-bind:numT="numT" />
          </div>
        </el-col>
      </el-row>

    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import PanelGroup from './dashboard/PanelGroup'
import PanelGroupT from './dashboard/PanelGroupT'
import PieChart from './dashboard/BarChartT'
import BarChart from './dashboard/BarChart'
import { count } from '@/api/visits'
import QshopInfo from './dashboard/QshopInfo'
import { chart } from '@/api/visits'

/**
   * 记录访问，只有页面刷新或者第一次加载才会记录
   */
count().then(res => {
})

export default {
  name: 'Dashboard',
  components: {
    PanelGroup,
    PanelGroupT,
    PieChart,
    BarChart,
    QshopInfo
  },
  data() {
    return {
      day: [],
      num: [],
      dayT: [],
      numT: []
    }
  },
  mounted() {
    this.getChat();
  },
  computed: {
    ...mapGetters([
      'roles'
    ])
  },
  methods: {
    getChat: function(){
      const that = this
      chart().then(res => {
          var _info = res.chart
          _info.forEach(function(item) {
            that.day.push(item.time)
            that.num.push(item.num)
          })
          that.$refs['barChat'].initChart()

          var _infoT = res.chartT
          _info.forEach(function(item) {
            that.dayT.push(item.time)
            that.numT.push(item.num)
          })

          that.$refs['pieChat'].initChart()
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .dashboard-editor-container {
    padding: 18px 22px 22px 22px;
    background-color: rgb(240, 242, 245);

    .chart-wrapper {
      background: #fff;
      padding: 16px 16px 0;
      margin-bottom: 32px;
    }
  }
</style>
