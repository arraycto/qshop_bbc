<template> 
  <StoreWithdraw-detail :is-edit='false'></StoreWithdraw-detail>
</template>
<script>
  import StoreWithdrawDetail from './components/StoreWithdrawDetail'
  export default {
    name: 'addStoreWithdraw',
    components: { StoreWithdrawDetail }
  }
</script>
<style>
</style>


