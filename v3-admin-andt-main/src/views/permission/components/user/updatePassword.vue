<script setup lang="ts">
import { ref } from "vue"
import { updatePasswordData } from "@/api/permission/user/types/base"
import { checkPassword } from "@/api/permission/user"

interface Props {
  userId: string
  openUpdatePassword: boolean
}

const prop = defineProps<Props>()
const LocalOpenUpdatePassword = ref(prop.openUpdatePassword)
const LocalUserId = ref(prop.userId)
const emit = defineEmits(["update:openUpdatePassword"])

const updateFormData = ref<updatePasswordData>({
  onePassword: "",
  password: "",
  twoPassword: ""
})

function handleUpdatePassword() {
  emit("update:openUpdatePassword", false)
}

async function checkPasswordApi(rule: any, value: any, callback: any) {
  if (value === "" || value == undefined) {
    return callback("请输入新密码")
  } else {
    const resource = await checkPassword({
      password: value
    }).catch(() => {
      return callback(import.meta.env.ERROR_MESSAGE_ONE)
    })
    if ("true" !== resource.message) {
      return callback(resource.message)
    }
  }
}

function equalPassword(rule: any, value: any, callback: any) {
  if (value === "" || value == undefined || value !== updateFormData.value.onePassword) {
    return callback("两次密码不一致")
  } else {
    callback()
  }
}

const updatePasswordRoles = ref()
</script>

<template>
  <el-drawer v-model="LocalOpenUpdatePassword" title="修改密码" :before-close="handleUpdatePassword" size="50%">
    <el-form :rules="updatePasswordRoles" :model="updateFormData" label-width="auto">
      <el-form-item required label="请输入旧密码" prop="password">
        <a-input type="password" show-password clearable v-model="updateFormData.password">请输入旧密码：</a-input>
      </el-form-item>
      <el-form-item required label="请输入新密码" prop="onePassword">
        <a-input type="password" show-password clearable v-model="updateFormData.onePassword">请输入新密码：</a-input>
      </el-form-item>
      <el-form-item required label="请输再次入新密码" prop="twoPassword">
        <a-input type="password" show-password clearable v-model="updateFormData.twoPassword"
          >请输再次入新密码：
        </a-input>
      </el-form-item>
      <el-form-item>
        <a-button type="primary" :icon="Pointer">提交</a-button>
        <a-button type="primary" :icon="Refresh">重置</a-button>
      </el-form-item>
    </el-form>
  </el-drawer>
</template>

<style scoped lang="scss"></style>
