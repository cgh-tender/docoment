<template>
  <div>
    <el-input @focus="togglePopover(true)" v-model="state.cron" placeholder="state.cron" />
    <el-dialog v-model="state.cronPopover" @close="togglePopover(false)" width="40%">
      <vue3Cron @change="changeCron" @close="togglePopover(false)" max-height="40%" i18n="cn" />
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import vue3Cron from "@/cron/vue3-cron/index.vue"
import { ref } from "vue"

const state = ref({
  cronPopover: false,
  cron: "* * * * * ? *"
})
const changeCron = (val) => {
  if (typeof val !== "string") return false
  state.value.cron = val
}
const togglePopover = (bol) => {
  console.log(bol)
  state.value.cronPopover = bol
}
</script>
<style lang="scss" scoped>
.center {
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
