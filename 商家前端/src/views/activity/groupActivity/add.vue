<template> 
  <GroupActivity-detail :is-edit='false'></GroupActivity-detail>
</template>
<script>
  import GroupActivityDetail from './components/GroupActivityDetail'
  export default {
    name: 'addGroupActivity',
    components: { GroupActivityDetail }
  }
</script>
<style>
</style>


