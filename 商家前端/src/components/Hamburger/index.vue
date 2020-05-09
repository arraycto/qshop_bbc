<template>
  <div style="padding: 0 15px;" @click="toggleClick">
    <svg
      :class="{'is-active':isActive}"
      class="hamburger"
      viewBox="0 0 1024 1024"
      xmlns="http://www.w3.org/2000/svg"
      width="64"
      height="64"
    >
    <path d="M888.838 319.775l-751.1 0c-23.944 0-43.339-19.553-43.339-43.646l0-40.012c0-24.093 19.395-43.645 43.339-43.645l751.1 0c23.923 0 43.334 19.554 43.334 43.645l0 40.012c-0.001 24.093-19.411 43.646-43.333 43.646l0 0zM888.838 587.509l-751.1 0c-23.944 0-43.339-19.533-43.339-43.64l0-39.998c0-24.115 19.395-43.647 43.339-43.647l751.1 0c23.923 0 43.334 19.533 43.334 43.647l0 39.998c-0.001 24.107-19.411 43.64-43.333 43.64l0 0zM888.838 876.17l-751.1 0c-23.944 0-43.339-19.532-43.339-43.627l0-40.017c0-24.093 19.395-43.641 43.339-43.641l751.1 0c23.923 0 43.334 19.548 43.334 43.641l0 40.017c-0.001 24.094-19.411 43.627-43.333 43.627l0 0z" p-id="2222" fill="#ffffff"></path>
    </svg>
  </div>
</template>

<script>
export default {
  name: 'Hamburger',
  props: {
    isActive: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    toggleClick() {
      this.$emit('toggleClick')
    }
  }
}
</script>

<style scoped>
.hamburger {
  display: inline-block;
  vertical-align: middle;
  width: 20px;
  height: 20px;
}

.hamburger.is-active {
  transform: rotate(180deg);
}
</style>
