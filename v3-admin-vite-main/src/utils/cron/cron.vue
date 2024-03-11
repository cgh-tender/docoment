<template lang="html">
  <div class="cron" :val="value_">
    <el-tabs v-model="activeName">
      <el-tab-pane label="秒" name="s">
        <second-and-minute v-model="sVal" lable="秒" />
      </el-tab-pane>
      <el-tab-pane label="分" name="m">
        <second-and-minute v-model="mVal" lable="分" />
      </el-tab-pane>
      <el-tab-pane label="时" name="h">
        <hour v-model="hVal" lable="时" />
      </el-tab-pane>
      <el-tab-pane label="日" name="d">
        <day v-model="dVal" lable="日" />
      </el-tab-pane>
      <el-tab-pane label="月" name="month">
        <month v-model="monthVal" lable="月" />
      </el-tab-pane>
      <el-tab-pane label="周" name="week">
        <week v-model="weekVal" lable="周" />
      </el-tab-pane>
      <el-tab-pane label="年" name="year">
        <year v-model="yearVal" lable="年" />
      </el-tab-pane>
    </el-tabs>
    <!-- table -->
    <el-table :data="tableData" size="small" border style="width: 100%">
      <el-table-column prop="sVal" label="秒" width="70" />
      <el-table-column prop="mVal" label="分" width="70" />
      <el-table-column prop="hVal" label="时" width="70" />
      <el-table-column prop="dVal" label="日" width="70" />
      <el-table-column prop="monthVal" label="月" width="70" />
      <el-table-column prop="weekVal" label="周" width="70" />
      <el-table-column prop="yearVal" label="年" />
    </el-table>
  </div>
</template>

<script setup lang="ts">
import SecondAndMinute from "@/cron/secondAndMinute"
import hour from "@/cron/hour"
import day from "@/cron/day"
import month from "@/cron/month"
import week from "@/cron/week"
import year from "@/cron/year"
import { computed, ref, watchEffect } from "vue"

const prop = defineProps({
  value: {
    type: String
  }
})
const emit = defineEmits(['input'])
const activeName = ref("s")
const sVal = ref("")
const mVal = ref("")
const hVal = ref("")
const dVal = ref("")
const monthVal = ref("")
const weekVal = ref("")
const yearVal = ref("")

const tableData = computed(() => {
  return [
    {
      sVal: sVal,
      mVal: mVal,
      hVal: hVal,
      dVal: dVal,
      monthVal: monthVal,
      weekVal: weekVal,
      yearVal: yearVal
    }
  ]
})
const value_ = computed(() => {
  if (!dVal && !weekVal) {
    return ""
  }
  if (dVal.value === "?" && weekVal.value === "?") {
    Promise.reject(new Error("日期与星期不可以同时为“不指定”"))
  }
  if (dVal.value !== "?" && weekVal.value !== "?") {
    Promise.reject(new Error("日期与星期必须有一个为“不指定”"))
  }
  const v = `${sVal} ${mVal} ${hVal} ${dVal} ${monthVal} ${weekVal} ${yearVal}`
  if (v !== prop.value) {
    emit("input",v)
  }
  return v
})

const updateVal = () => {
  if (!prop.value) {
    return
  }
  const arrays = prop.value.split(" ")
  sVal.value = arrays[0]
  mVal.value = arrays[1]
  hVal.value = arrays[2]
  dVal.value = arrays[3]
  monthVal.value = arrays[4]
  weekVal.value = arrays[5]
  yearVal.value = arrays[6]
}
watchEffect(() => {
  updateVal()
})
</script>

<style lang="css">
.cron {
  text-align: left;
  padding: 10px;
  background: #fff;
  border: 1px solid #dcdfe6;
  box-shadow:
    0 2px 4px 0 rgba(0, 0, 0, 0.12),
    0 0 6px 0 rgba(0, 0, 0, 0.04);
}
</style>
