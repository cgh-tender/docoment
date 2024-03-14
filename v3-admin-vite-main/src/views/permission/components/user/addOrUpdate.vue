<script setup lang="ts">
import { reactive, ref } from "vue"
import { FormProps, FormRules } from "element-plus"
import { DefaultUserTableData } from "@/api/permission/user/types/base"
import { CircleClose } from "@element-plus/icons-vue"

const labelPosition = ref<FormProps["labelPosition"]>("right")

const formRules: FormRules = reactive({
  username: [{ required: true, trigger: "blur", message: "请输入用户名" }],
  realname: [{ required: false, trigger: "blur", message: "请输入密码" }]
})

interface Props {
  dialogVisible: boolean
  formData: DefaultUserTableData
  titleName: string
  isDisabled: boolean
}

const prop = defineProps<Props>()

const disabled = ref(prop.isDisabled)

const emit = defineEmits(["dialogVisibleClose"])

const dialogVisibleProp = ref(prop.dialogVisible)

const handleUpdate = () => {
  emit("dialogVisibleClose")
}

const handleCreate = () => {
  console.log("create")
}
</script>

<template>
  <el-drawer v-model="dialogVisibleProp" :title="prop.titleName" :before-close="handleUpdate" size="50%">
    <el-form
      ref="formRef"
      :disabled
      :model="formData"
      :rules="formRules"
      :label-position="labelPosition"
      label-width="80px"
    >
      <el-row>
        <el-col :span="12">
          <el-form-item prop="username" label="登录名">
            <el-input v-model="formData.username" placeholder="请输入" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="username" label="账号">
            <el-input v-model="formData.realname" placeholder="请输入" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item prop="username" label="登录名xxxxxxx">
            <el-input v-model="formData.username" placeholder="请输入" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="username" label="账号">
            <el-input v-model="formData.realname" placeholder="请输入" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label-width="50%">
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
