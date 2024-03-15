<script setup lang="ts">
import { reactive, ref } from "vue"
import { FormProps, FormRules } from "element-plus"
import { DefaultUserTableData } from "@/api/permission/user/types/base"
import { CircleClose } from "@element-plus/icons-vue"

const labelPosition = ref<FormProps["labelPosition"]>("right")

const formRules: FormRules = reactive({
  username: [{ required: true, trigger: "blur", message: "请输入用户名" }],
  realname: [{ required: true, trigger: "blur", message: "请输入账号" }],
  email: [
    {
      required: true,
      trigger: "blur",
      message: "请输入邮箱",
      validator: (rule: any, value: any, callback: any) => {
        if (value == undefined || value == "") {
          return callback(rule.message)
        }
        return callback()
      }
    }
  ]
})

interface Props {
  dialogVisible: boolean
  formData: DefaultUserTableData
  titleName: string
  isDisabled: boolean
}

const prop = defineProps<Props>()

const cProp = ref<Props>(prop)

const emit = defineEmits(["dialogVisibleClose"])

const dialogVisibleProp = ref(prop.dialogVisible)

const handleUpdate = () => {
  emit("dialogVisibleClose")
}

const handleCreate = () => {
  console.log("create")
}

const gender = ref(cProp.value.formData.gender === "男")
const changeGender = (val: any) => {
  cProp.value.formData.gender = val ? "男" : "女"
}
</script>

<template>
  <el-drawer v-model="dialogVisibleProp" :title="prop.titleName" :before-close="handleUpdate" size="40%">
    <el-form
      ref="formRef"
      :disabled="prop.isDisabled"
      :model="cProp.formData"
      :rules="formRules"
      :label-position="labelPosition"
      label-width="40%"
    >
      <el-row>
        <el-col :span="12">
          <el-form-item prop="username" label="登录名">
            <el-input v-model="cProp.formData.username" placeholder="请输入登录名" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="realname" label="账号">
            <el-input v-model="cProp.formData.realname" placeholder="请输入账号" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item prop="email" label="邮箱">
            <el-input v-model="cProp.formData.email" placeholder="请输入邮箱" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="gender" label="性别">
            <el-switch @change="changeGender" inline-prompt v-model="gender" active-text="男" inactive-text="女" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label-width="60%">
        <el-button :icon="CircleClose" @click="handleUpdate">取消</el-button>
        <el-button type="primary" @click="handleCreate">确认</el-button>
      </el-form-item>
    </el-form>
  </el-drawer>
</template>

<style scoped lang="scss">
.el-col .el-form-item {
  width: 80%;
}
</style>
