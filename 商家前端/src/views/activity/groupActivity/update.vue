<template> 
  <GroupActivity-detail :is-edit='true'></GroupActivity-detail>
</template>
<script>
  import GroupActivityDetail from './components/GroupActivityDetail'
  export default {
    name: 'updateGroupActivity',
    components: { GroupActivityDetail }
  }
</script>
<style>
</style>


