<template>
  <div class="app-container center">
    <div>
      <el-input @focus="togglePopover(true)" v-model="cron" placeholder="cron" />
      <el-dialog v-model="cronPopover" @close="togglePopover(false)" width="40%">
        <vue3Cron @changeCron="changeCron" @close="togglePopover(false)" max-height="400px" i18n="cn" sys-radio="1" />
      </el-dialog>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue"
import Vue3Cron from "@/utils/cron/index.vue"

const cron = ref("")
const cronPopover = ref(false)

const changeCron = (val: string) => {
  cron.value = val
}

const togglePopover = (bol: boolean) => {
  cronPopover.value = bol
}
</script>
<style lang="scss" scoped>
.center {
  height: 100%;
  display: flex;
}
</style>
