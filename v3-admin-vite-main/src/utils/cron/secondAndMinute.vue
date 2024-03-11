<!-- 秒,分钟 -->
<template lang="html">
  <div :val="value_">
    <div>
      <el-radio v-model="type" label="1" size="small" border>每{{ lable }}</el-radio>
    </div>
    <div>
      <el-radio v-model="type" label="2" size="small" border>周期</el-radio>
      <span style="margin-left: 10px; margin-right: 5px">从</span>
      <el-input-number
        @change="type = '2'"
        v-model="cycle.start"
        :min="1"
        :max="59"
        size="small"
        style="width: 100px"
      />
      <span style="margin-left: 5px; margin-right: 5px">至</span>
      <el-input-number @change="type = '2'" v-model="cycle.end" :min="2" :max="59" size="small" style="width: 100px" />
      {{ lable }}
    </div>
    <div>
      <el-radio v-model="type" label="3" size="small" border>循环</el-radio>
      <span style="margin-left: 10px; margin-right: 5px">从</span>
      <el-input-number @change="type = '3'" v-model="loop.start" :min="0" :max="59" size="small" style="width: 100px" />
      <span style="margin-left: 5px; margin-right: 5px">{{ lable }}开始，每</span>
      <el-input-number @change="type = '3'" v-model="loop.end" :min="1" :max="59" size="small" style="width: 100px" />
      {{ lable }}执行一次
    </div>
    <div>
      <el-radio v-model="type" label="4" size="small" border>指定</el-radio>
      <el-checkbox-group v-model="appoint">
        <div v-for="i in 6" :key="i" style="margin-left: 10px; line-height: 25px">
          <el-checkbox @change="type = '4'" v-for="j in 10" :key="j" :label="i - 1 + '' + (j - 1)" />
        </div>
      </el-checkbox-group>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, ref, watchEffect } from "vue"

const prop = defineProps({
  value: {
    type: String,
    default: "*"
  },
  lable: {
    type: String
  }
})
const emit = defineEmits(['input'])
const type = ref("1")
const cycle = ref({
  // 周期
  start: 0,
  end: 0
})
const loop = ref({
  // 循环
  start: 0,
  end: 0
})
const work = ref(0)
const week = ref({
  // 指定周
  start: 0,
  end: 0
})
const last = ref(0)
const appoint = ref<string []>([])
const value_ = computed(() => {
  const result = []
  switch (type.value) {
    case "1": // 每秒
      result.push("*")
      break
    case "2": // 年期
      result.push(`${cycle.value.start}-${cycle.value.end}`)
      break
    case "3": // 循环
      result.push(`${loop.value.start}/${loop.value.end}`)
      break
    case "4": // 指定
      result.push(appoint.value.join(","))
      break
    case "6": // 最后
      result.push(`${last.value === 0 ? "" : last.value}L`)
      break
    default: // 不指定
      result.push("?")
      break
  }
  emit("input", result.join(""))
  return result.join("")
})

const updateVal = () => {
  if (!prop.value) {
    return
  }
  if (prop.value === "?") {
    type.value = "5"
  } else if (prop.value.indexOf("-") !== -1) {
    // 2周期
    if (prop.value.split("-").length === 2) {
      type.value = "2"
      cycle.value.start = Number(prop.value.split("-")[0])
      cycle.value.end = Number(prop.value.split("-")[1])
    }
  } else if (prop.value.indexOf("/") !== -1) {
    // 3循环
    if (prop.value.split("/").length === 2) {
      type.value = "3"
      loop.value.start = Number(prop.value.split("/")[0])
      loop.value.end = Number(prop.value.split("/")[1])
    }
  } else if (prop.value.indexOf("*") !== -1) {
    // 1每
    type.value = "1"
  } else if (prop.value.indexOf("L") !== -1) {
    // 6最后
    type.value = "6"
    last.value = Number(prop.value.replace("L", ""))
  } else if (prop.value.indexOf("#") !== -1) {
    // 7指定周
    if (prop.value.split("#").length === 2) {
      type.value = "7"
      week.value.start = Number(prop.value.split("#")[0])
      week.value.end = Number(prop.value.split("#")[1])
    }
  } else if (prop.value.indexOf("W") !== -1) {
    // 8工作日
    type.value = "8"
    work.value = Number(prop.value.replace("W", ""))
  } else {
    // *
    type.value = "4"
    appoint.value = prop.value.split(",")
  }
}

watchEffect(() => {
  updateVal()
})
</script>

<style lang="css">
.el-checkbox + .el-checkbox {
  margin-left: 10px;
}
</style>
