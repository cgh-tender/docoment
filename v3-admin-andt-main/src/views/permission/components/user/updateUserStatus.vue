<script setup lang="ts">
import { ref } from "vue"
import { getUserStatus, upUserStatus } from "@/api/permission/user"
import { SelectOption } from "@/hooks/useFetchSelect"

interface Props {
  dialogUserStatus: boolean
  status: any
  userId: string
}

const prop = defineProps<Props>()
const dialogUserStatusProp = ref(prop.dialogUserStatus)
const statusProp = ref(prop.status)
const LocalUserId = ref(prop.userId)

const emit = defineEmits(["update:dialogUserStatus", "getTableData"])

const updateStatus = () => {
  if (!/[\u4e00-\u9fff]/.test(statusProp.value)) {
    upUserStatus(LocalUserId.value, statusProp.value).finally(() => {
      handleClose()
      emit("getTableData")
    })
  } else {
    handleClose()
  }
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
  <a-modal v-model="dialogUserStatusProp" title="更新用户状态" width="500" :before-close="handleClose">
    <a-card shadow="never" class="search-wrapper">
      <a-form-item label="请输选择更新状态">
        <a-select v-model="statusProp">
          <a-option :key="item.value" :label="item.label" :value="item.value" v-for="item in statusList" />
        </a-select>
      </a-form-item>
      <template #footer>
        <div class="my-a-button">
          <a-button :icon="Refresh" @click="updateStatus">更新</a-button>
        </div>
      </template>
    </a-card>
  </a-modal>
</template>

<style scoped lang="scss">
.my-a-button {
  display: flex;
  justify-content: flex-end;
  padding: 0 85%;
}
</style>
