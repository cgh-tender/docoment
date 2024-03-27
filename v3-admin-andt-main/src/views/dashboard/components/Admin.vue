<template>
  <div class="VueScroller">
    <a-input @click="togglePopover(true)" v-model:value="cron" placeholder="cron" />
    <a-modal v-model:open="cronPopover" style="width: 50%" :key="Math.random()">
      <vue3Cron @changeCron="changeCron" @close="togglePopover(false)" max-height="400px" i18n="cn" sys-radio="1" />
    </a-modal>
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
  justify-content: center;
  align-items: center;
}
</style>
