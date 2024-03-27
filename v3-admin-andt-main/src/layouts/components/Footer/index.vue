<script lang="ts" setup>
const VITE_APP_TITLE = import.meta.env.VITE_APP_TITLE
</script>

<template>
  <TextContent type="info" class="layout-footer">MIT © 2021-PRESENT {{ VITE_APP_TITLE }}</TextContent>
</template>

<style lang="scss" scoped>
.layout-footer {
  position: absolute;
  width: 100%;
  min-height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #c0c4cc;
  float: left;
  bottom: 0;
}
</style>
