<script setup lang="ts">
import { ref, watchEffect } from "vue"
import { Refresh } from "@element-plus/icons-vue"

interface Props {
  dialogUserStatus: boolean
  status: string
}

const prop = defineProps<Props>()
console.log(prop)
const dialogUserStatusProp = ref(prop.dialogUserStatus)
const statusProp = ref(prop.status)

const emit = defineEmits(["handleCloseUserStatus", "handleUpdateUserStatus"])

function handleCloseUser() {
  emit("handleCloseUserStatus")
}

function updateStatus() {
  emit("handleUpdateUserStatus", statusProp.value)
}

watchEffect(() => {
  console.log(dialogUserStatusProp.value)
})
const status1 = [{ value: "1", label: "成功" }]
</script>

<template>
  <el-dialog v-model="dialogUserStatusProp" title="Tips" width="500" :before-close="handleCloseUser">
    <el-card shadow="never" class="search-wrapper">
      <el-form :inline="true" label-width="auto">
        <el-form-item label="请输选择状态">
          <el-select v-model="statusProp">
            <el-option :key="item.value" :label="item.label" :value="item.value" v-for="item in status1" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button :icon="Refresh" @click="updateStatus">更新</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </el-dialog>
</template>

<style scoped lang="scss"></style>
