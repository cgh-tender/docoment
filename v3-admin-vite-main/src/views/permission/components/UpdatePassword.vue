<script setup lang="ts">

import { reactive, ref } from "vue";
import { DefaultUserTableData, updatePasswordData } from "@/api/permission/user/types/base";
import { checkPassword } from "@/api/permission/user";
import { type FormInstance, FormRules } from "element-plus";
const updatePasswordRuleRef = ref<FormInstance>()
interface Props {
  user: DefaultUserTableData
  openUpdatePassword: boolean
}

const prop = defineProps<Props>()
const emit = defineEmits(['update:openUpdatePassword'])

const updateFormData = ref<updatePasswordData>({
  onePassword: "",
  password: "",
  twoPassword: ""
})

function handleUpdatePassword(){
  emit('update:openUpdatePassword',false)
}

async function checkPasswordApi(rule: any, value: any, callback: any) {
  if (value === '' || value == undefined) {
    return callback("请输入新密码")
  } else {
    const resource = await checkPassword({
      password: value
    }).catch((r) => {
      return callback(import.meta.env.ERROR_MESSAGE_ONE)
    })
    if ("true" !== resource.message) {
      return callback(resource.message)
    }
  }
}

function equalPassword(rule: any, value: any, callback: any){
  if (value === '' || value == undefined || value !== updateFormData.value.onePassword) {
    return callback('两次密码不一致')
  } else {
    callback()
  }
}

const updatePasswordRoles = reactive<FormRules<updatePasswordData>>({
  password: [
    {required: true, trigger: 'blur', message: "请输入密码"}
  ],
  onePassword: [
    {required: true, trigger: 'blur', asyncValidator: checkPasswordApi}
  ],
  twoPassword: [
    {required: true, trigger: 'blur', message: "请输再次入新密码"},
    {min: 8, max: 16, message: "请输入密码长度为8-16", trigger: 'blur'},
    {min: 8, max: 16, validator: equalPassword, trigger: 'blur'}
  ]
})


</script>

<template>
  <div>
    <el-drawer
      :ref="updatePasswordRuleRef"
      v-model="prop.openUpdatePassword"
      title="修改密码"
      :before-close="handleUpdatePassword">
      <el-form
        :rules="updatePasswordRoles"
        :model="updateFormData"
        label-width="auto"
        status-icon
      >
        <el-form-item required label="请输入旧密码" prop="password">
          <el-input v-model="updateFormData.password" >请输入旧密码：</el-input>
        </el-form-item>
        <el-form-item required label="请输入新密码" prop="onePassword">
          <el-input v-model="updateFormData.onePassword" >请输入新密码：</el-input>
        </el-form-item>
        <el-form-item required label="请输再次入新密码" prop="twoPassword">
          <el-input v-model="updateFormData.twoPassword" >请输再次入新密码：</el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary">Submit</el-button>
          <el-button>Reset</el-button>
        </el-form-item>
      </el-form>
    </el-drawer>
  </div>
</template>

<style scoped lang="scss">

</style>