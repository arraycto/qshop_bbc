<template>
  <div class="dashboard-container">
    <div class="dashboard-editor-container">
      <qshop-info />
      <panel-group />
      <panel-group-t />
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import PanelGroup from '../../dashboard/PanelGroup'
import PanelGroupT from '../../dashboard/PanelGroupT'
import QshopInfo from '../../dashboard/QshopInfo'

/**
   * 记录访问，只有页面刷新或者第一次加载才会记录
   */
// count().then(res => {
// })

export default {
  name: 'TotalStatics',
  components: {
    PanelGroup,
    PanelGroupT,
    QshopInfo
  },
  computed: {
    ...mapGetters([
      'roles'
    ])
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
