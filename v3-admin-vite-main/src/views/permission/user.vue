<script lang="ts" setup>
import { reactive, ref, watchEffect } from "vue"
import { BaseUserTableData, GetBaseUserTableData } from "@/api/permission/user/types/base"
import { getUserTable } from "@/api/permission/user"
import { usePagination } from "@/hooks/usePagination"
import { type FormInstance, FormRules } from "element-plus"
import { Refresh, Search } from "@element-plus/icons-vue"

const loading = ref<boolean>(false)
const dialogVisible = ref<boolean>(false)
const { paginationData } = usePagination()

const currentUpdateId = ref<undefined | bigint | string>(undefined)

const formRef = ref<FormInstance | null>(null)
const formData = ref<BaseUserTableData>({
  password: "",
  createTime: "",
  email: "",
  id: "",
  phone: "",
  roles: "",
  status: "",
  username: ""
})
const formRules: FormRules = reactive({
  username: [{ required: true, trigger: "blur", message: "请输入用户名" }],
  password: [{ required: true, trigger: "blur", message: "请输入密码" }]
})
const handleUpdate = (row: BaseUserTableData) => {
  console.log(row)
  currentUpdateId.value = row.id
  formData.value.username = row.username
  formData.value.password = row.password
  dialogVisible.value = true
}

const resetForm = () => {
  currentUpdateId.value = undefined
  formData.value.username = ""
  formData.value.password = ""
}

const tableData = ref<BaseUserTableData[]>([])

const searchData = ref<GetBaseUserTableData>({
  currentPage: 1,
  size: 2
})

const getTableData = () => {
  loading.value = true
  getUserTable(searchData.value).then((res => {
    paginationData.total = res.data.total
    tableData.value = res.data.records
  })).catch(() => {
    tableData.value = []
  }).finally(() => {
    loading.value = false
  })
}

const resetSearch = () => {
  resetForm()
  getTableData()
}


const showUserDetail = (row: BaseUserTableData) => {
  console.log(row)
}
const handleCreate = () => {
  formRef.value?.validate((valid: boolean, fields) => {
    if (valid) {
      console.log(formData);
      // if (currentUpdateId.value === undefined) {
      //   createTableDataApi(formData)
      //     .then(() => {
      //       ElMessage.success("新增成功")
      //       getTableData()
      //     })
      //     .finally(() => {
      //       dialogVisible.value = false
      //     })
      // } else {
      //   updateTableDataApi({
      //     id: currentUpdateId.value,
      //     username: formData.username
      //   })
      //     .then(() => {
      //       ElMessage.success("修改成功")
      //       getTableData()
      //     })
      //     .finally(() => {
      //       dialogVisible.value = false
      //     })
      // }
    } else {
      console.error("表单校验不通过", fields)
    }
  })
}

watchEffect(getTableData)
</script>
<template>
  <div class="app-container">
    <el-card v-loading="loading" shadow="never" class="search-wrapper">
      <el-form ref="searchFormRef" :inline="true" :model="searchData">
        <el-form-item prop="username" label="用户名">
          <el-input v-model="searchData.username" placeholder="请输入" />
        </el-form-item>
        <el-form-item prop="phone" label="手机号">
          <el-input v-model="searchData.phone" placeholder="请输入" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :icon="Search" @click="getTableData">查询</el-button>
          <el-button :icon="Refresh" @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card v-loading="loading" shadow="never">
      <el-table border :data="tableData">
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column fixed prop="username" label="用户名" align="center" />
        <el-table-column prop="realname" label="用户名" align="center" />
        <el-table-column prop="email" label="邮箱" align="center" />
        <el-table-column prop="gender" label="性别" align="center" />
        <el-table-column prop="status" label="状态" align="center" />
        <el-table-column prop="roles" label="角色" align="center">
          <template #default="scope">
            <el-tag v-if="scope.row.roles === 'admin'" effect="plain">admin</el-tag>
            <el-tag v-else type="warning" effect="plain">{{ scope.row.roles }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="150" align="center">
          <template #default="scope">
            <el-button type="primary" text plain size="small" @click="handleUpdate(scope.row)">编辑</el-button>
            <el-dropdown>
              <el-button type="primary" text plain size="small">更多
                <el-icon class="el-icon--right">
                  <arrow-down />
                </el-icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="showUserDetail(scope.row)">详情</el-dropdown-item>
                  <el-dropdown-item>密码</el-dropdown-item>
                  <el-dropdown-item>删除</el-dropdown-item>
                  <el-dropdown-item>冻结</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!-- 新增/修改 -->
    <transition name="el-zoom-in-center">
      <div>
        <el-dialog
          v-model="dialogVisible"
          :title="currentUpdateId === undefined ? '新增用户' : '修改用户'"
          @closed="resetForm"
          width="30%"
        >
          <el-form ref="formRef" :model="formData" :rules="formRules" label-width="100px" label-position="left">
            <el-form-item prop="username" label="用户名">
              <el-input v-model="formData.username" placeholder="请输入" />
            </el-form-item>
            <el-form-item prop="password" label="密码" v-show="currentUpdateId === undefined">
              <el-input v-model="formData.password" placeholder="请输入" />
            </el-form-item>
          </el-form>
          <template #footer>
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="handleCreate">确认</el-button>
          </template>
        </el-dialog>
      </div>
    </transition>
  </div>
</template>

<!--  <div>❤️</div>-->
<style lang="scss" scoped>
.el-button.is-text:not(.is-disabled):focus-visible{
  outline: unset;
}

.search-wrapper {
  margin-bottom: 10px;

  :deep(.el-card__body) {
    padding-bottom: 1px;
  }
}

</style>