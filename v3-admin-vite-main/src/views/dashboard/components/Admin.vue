<template>
  <div>
    <el-input @focus="togglePopover(true)" v-model="state.cron" placeholder="state.cron" />
    <el-dialog v-model="state.cronPopover" @close="togglePopover(false)" width="40%">
      <vue3Cron @change="changeCron" @close="togglePopover(false)" max-height="400px" i18n="cn" />
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue"
import Vue3Cron from "@/utils/cron/vue3-cron/index.vue";

const state = ref({
  cronPopover: false,
  cron: "* * * * * ? *"
})
const changeCron = (val: string) => {
  state.value.cron = val
}
const togglePopover = (bol: boolean) => {
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
