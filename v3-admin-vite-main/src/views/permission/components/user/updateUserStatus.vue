<script setup lang="ts">
import { ref } from "vue"
import { Refresh } from "@element-plus/icons-vue"
import { getUserStatus, upUserStatus } from "@/api/permission/user"
import { SelectOption } from "@/hooks/useFetchSelect"

interface Props {
  dialogUserStatus: boolean
  status: any
  userId: number
}

const prop = defineProps<Props>()
const dialogUserStatusProp = ref(prop.dialogUserStatus)
const statusProp = ref(prop.status)
const LocalUserId = ref(prop.userId)

const emit = defineEmits(["update:dialogUserStatus", "handleUpdateUserStatus"])

const updateStatus = () => {
  if (!(statusProp instanceof String)) {
    upUserStatus(LocalUserId.value, statusProp.value)
  }
  handleClose()
}

const handleClose = () => {
  emit("update:dialogUserStatus", false)
}

const statusList = ref<SelectOption[]>([])
getUserStatus().then((data) => {
  statusList.value = data.data
})
</script>

<template>
  <el-dialog v-model="dialogUserStatusProp" title="更新用户状态" width="500" :before-close="handleClose">
    <el-card shadow="never" class="search-wrapper">
      <el-form-item label="请输选择更新状态">
        <el-select v-model="statusProp">
          <el-option :key="item.value" :label="item.label" :value="item.value" v-for="item in statusList" />
        </el-select>
      </el-form-item>
      <template #footer>
        <div class="my-el-button">
          <el-button :icon="Refresh" @click="updateStatus">更新</el-button>
        </div>
      </template>
    </el-card>
  </el-dialog>
</template>

<style scoped lang="scss">
.my-el-button {
  display: flex;
  justify-content: flex-end;
  padding: 0 85%;
}
</style>
