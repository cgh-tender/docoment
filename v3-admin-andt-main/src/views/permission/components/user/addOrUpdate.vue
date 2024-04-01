<script setup lang="ts">
import { reactive, ref } from "vue"
import { DefaultUserTableData, Group, Organization, Position, Role } from "@/api/permission/user/types/base"
import { useTreeFunction } from "@/hooks/useTreeSelect"
import { SelectNode, SelectOption, useFetchSelect } from "@/hooks/useFetchSelect"
import { addOrUpdateUser, loadOrganization, loadPosition, loadRole, queryUserGroup } from "@/api/permission/user"

interface Props {
  dialogVisible: boolean
  formData: DefaultUserTableData
  titleName: string
  isDisabled?: boolean
}

const prop = defineProps<Props>()
const cProp = ref<Props>(prop)
const disabled = ref(!cProp.value.titleName.startsWith("新增"))
const dialogVisibleProp = ref(prop.dialogVisible)
const emit = defineEmits(["dialogVisibleClose", "dialogLoading"])

const {
  treeSelectLoading: OrganizationSelectLoading,
  treeLoadData: OrganizationLoadData,
  treeModelNode: OrganizationModelNode,
  treeFunction: OrganizationFunction,
  treeSelectNode: OrganizationSelectNode
} = useTreeFunction({
  api: loadOrganization,
  noInitQuery: cProp.value.isDisabled
})

const OrganizationLoadFunction = (n: SelectNode, r: any) => {
  OrganizationSelectNode.node = n.data
  OrganizationSelectNode.resolve = r
  OrganizationFunction()
}

const {
  treeSelectLoading: PositionSelectLoading,
  treeLoadData: PositionLoadData,
  treeModelNode: PositionModelNode,
  treeFunction: PositionFunction,
  treeSelectNode: PositionSelectNode
} = useTreeFunction({
  api: loadPosition,
  noInitQuery: cProp.value.isDisabled
})

const PositionLoadFunction = (n: SelectNode, r: any) => {
  PositionSelectNode.node = n.data
  PositionSelectNode.resolve = r
  PositionFunction()
}

const {
  loading: groupLoading,
  options: groupOptions,
  value: groupValue
} = useFetchSelect({
  api: queryUserGroup,
  noInitQuery: cProp.value.isDisabled
})

const {
  treeSelectLoading: RoleSelectLoading,
  treeLoadData: RoleLoadData,
  treeModelNode: RoleModelNode,
  treeFunction: RoleFunction,
  treeSelectNode: RoleSelectNode
} = useTreeFunction({
  api: loadRole,
  noInitQuery: cProp.value.isDisabled
})

const RoleLoadFunction = (n: SelectNode, r: any) => {
  RoleSelectNode.node = n.data
  RoleSelectNode.resolve = r
  RoleFunction()
}

const labelPosition = ref<FormProps["labelPosition"]>("right")

const formRules: FormRules = reactive({
  username: [{ required: true, trigger: "blur", message: "请输入账号" }],
  realname: [{ required: true, trigger: "blur", message: "请输入用户名" }],
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
  ],
  phone: [
    {
      required: true,
      trigger: "blur",
      message: "请输入手机号"
    },
    {
      required: true,
      trigger: "blur",
      message: "请输入手机号"
    }
  ]
})

prop.formData.organizations.map((item) => {
  OrganizationModelNode.value.push(<SelectOption>{
    label: item.name,
    value: item.id
  })
})

prop.formData.positions.map((item) => {
  PositionModelNode.value.push(<SelectOption>{
    label: item.name,
    value: item.id
  })
})

prop.formData.roles.map((item) => {
  RoleModelNode.value.push(<SelectOption>{
    label: item.name,
    value: item.id
  })
})

groupValue.value = prop.formData.groups.map((item) => {
  return <string>item.id
})

/**
 * 关闭 dialog
 */
const handleUpdate = () => {
  emit("dialogVisibleClose")
}

const save = () => {
  emit("dialogLoading", true)
  const data: DefaultUserTableData = {
    gender: cProp.value.formData.gender,
    id: cProp.value.formData.id,
    email: cProp.value.formData.email,
    phone: cProp.value.formData.phone,
    username: disabled ? cProp.value.formData.username : "",
    password: disabled ? cProp.value.formData.password : "",
    realname: cProp.value.formData.realname,
    organizations: OrganizationModelNode.value.flatMap(
      (item) =>
        <Organization>{
          id: typeof item === "object" && "label" in item && "value" in item ? item.value : item
        }
    ),
    positions: PositionModelNode.value.flatMap(
      (item) =>
        <Position>{
          id: typeof item === "object" && "label" in item && "value" in item ? item.value : item
        }
    ),
    roles: RoleModelNode.value.map(
      (item) =>
        <Role>{
          id: typeof item === "object" && "label" in item && "value" in item ? item.value : item
        }
    ),
    groups: groupValue.value.flatMap(
      (item) =>
        <Group>{
          id: item
        }
    )
  }
  addOrUpdateUser(data).finally(() => {
    emit("dialogLoading", false)
    handleUpdate()
  })
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
          <el-form-item prop="username" label="账号">
            <a-input v-model="cProp.formData.username" placeholder="请输入账号" :disabled />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="realname" label="用户名">
            <a-input v-model="cProp.formData.realname" placeholder="请输入用户名" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item prop="phone" label="手机号">
            <a-input v-model="cProp.formData.phone" placeholder="请输入手机号" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="email" label="邮箱">
            <a-input v-model="cProp.formData.email" placeholder="请输入邮箱" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" v-if="!disabled">
          <el-form-item prop="password" label="密码">
            <a-input type="password" show-password clearable v-model="cProp.formData.password">请输入密码：</a-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="gender" label="性别">
            <el-switch @change="changeGender" inline-prompt v-model="gender" active-text="男" inactive-text="女" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item prop="organizations" clearable label="组织">
            <el-tree-select
              v-loading="OrganizationSelectLoading"
              v-model="OrganizationModelNode"
              lazy
              :load="OrganizationLoadFunction"
              :data="OrganizationLoadData"
              placeholder="请选择组织"
              multiple
              show-checkbox
              check-strictly
              check-on-click-node
              :render-after-expand="false"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="positions" label="职位">
            <el-tree-select
              v-loading="PositionSelectLoading"
              v-model="PositionModelNode"
              lazy
              :load="PositionLoadFunction"
              :data="PositionLoadData"
              placeholder="请选择职位"
              multiple
              show-checkbox
              check-on-click-node
              check-strictly
              :render-after-expand="false"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item prop="groups" label="用户组">
            <a-select :loading="groupLoading" v-model="groupValue" filterable multiple placeholder="请选择用户组">
              <a-select-option v-for="(item, index) in groupOptions" v-bind="item" :key="item.value" />
            </a-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="roles" label="角色">
            <el-tree-select
              v-loading="RoleSelectLoading"
              v-model="RoleModelNode"
              lazy
              :load="RoleLoadFunction"
              :data="RoleLoadData"
              placeholder="请选择职位"
              show-checkbox
              check-strictly
              multiple
              check-on-click-node
              :render-after-expand="false"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label-width="60%">
        <a-button :icon="CircleClose" @click="handleUpdate">取消</a-button>
        <a-button type="primary" @click="save">确认</a-button>
      </el-form-item>
    </el-form>
  </el-drawer>
</template>

<style scoped lang="scss">
.el-col .el-form-item {
  width: 80%;
}
</style>
