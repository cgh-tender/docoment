<script lang="ts" setup>
const VITE_APP_TITLE = import.meta.env.VITE_APP_TITLE
</script>

<template>
  <el-footer class="layout-footer">MIT © 2021-PRESENT {{ VITE_APP_TITLE }}</el-footer>
</template>

<style lang="scss" scoped>
.layout-footer {
  width: 100%;
  min-height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #c0c4cc;
}
</style>
