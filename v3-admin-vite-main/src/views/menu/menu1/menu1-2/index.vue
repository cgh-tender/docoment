<script setup>
import { ref } from "vue"

const text = ref("")
</script>
<template>
  <div class="app-container">
    <el-card header="三级路由 - menu1-2">
      {{ text }}
      <router-view />
      <el-input v-model="text" />
    </el-card>
  </div>
</template>
