<script lang="ts" setup>
import { computed, reactive, ref, watchEffect } from "vue"
import {
  DefaultUserTableData,
  GetBaseUserTableData
} from "@/api/permission/user/types/base"
import { getUserTable } from "@/api/permission/user"
import { usePagination } from "@/hooks/usePagination"
import { type FormInstance, FormRules } from "element-plus"
import { Refresh, Search } from "@element-plus/icons-vue"
import UpdatePassword from "@/views/permission/components/UpdatePassword.vue";

const loading = ref<boolean>(false)
const dialogVisible = ref<boolean>(false)
const openUpdatePassword = ref<boolean>(false)

const { paginationData, handleCurrentChange, handleSizeChange } = usePagination({
  currentPage: 1,
  pageSize: 2
})

const currentUpdateId = ref<bigint | undefined>(BigInt(-1))

const formRef = ref<FormInstance | null>(null)
const formData = ref<DefaultUserTableData>({
  password: "",
  createTime: "",
  email: "",
  id: undefined,
  phone: "",
  roles: "",
  status: "",
  username: ""
})
const formRules: FormRules = reactive({
  username: [{ required: true, trigger: "blur", message: "请输入用户名" }],
  password: [{ required: true, trigger: "blur", message: "请输入密码" }]
})

const setFormData = (row: DefaultUserTableData) => {
  formData.value.username = row.username
  formData.value.password = row.password
}
const handleUpdate = (row: DefaultUserTableData) => {
  currentUpdateId.value = row.id
  setFormData(row)
  dialogVisible.value = true
}

const resetForm = () => {
  currentUpdateId.value = undefined
  formData.value.username = ""
  formData.value.password = ""
}

const tableData = ref<DefaultUserTableData[]>([])

const searchData = computed<GetBaseUserTableData>(() => {
  return {
    currentPage: paginationData.currentPage,
    pageSize: paginationData.pageSize
  }
})

const getTableData = () => {
  loading.value = true
  getUserTable(searchData.value).then((res => {
    paginationData.total = res.total
    tableData.value = res.records
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

const showUserDetail = (row: DefaultUserTableData) => {
  console.log(row)
}
const openUpdatePassword1 = (row: DefaultUserTableData) => {
  openUpdatePassword.value = true
  setFormData(row)
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


watchEffect(() => {
  paginationData
  getTableData()
})
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
                  <el-dropdown-item @click="openUpdatePassword1(scope.row)">密码</el-dropdown-item>
                  <el-dropdown-item>删除</el-dropdown-item>
                  <el-dropdown-item>冻结</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        background
        class="pager-wrapper"
        :layout="paginationData.layout"
        :page-sizes="paginationData.pageSizes"
        :total="paginationData.total"
        :page-size="paginationData.pageSize"
        :currentPage="paginationData.currentPage"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
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
    <transition>
      <UpdatePassword v-model:openUpdatePassword="openUpdatePassword" v-model:user="formData"/>
    </transition>
  </div>
</template>

<!--  <div>❤️</div>-->
<style lang="scss" scoped>
.el-button.is-text:not(.is-disabled):focus-visible {
  outline: unset;
}

.search-wrapper {
  margin-bottom: 10px;

  :deep(.el-card__body) {
    padding-bottom: 1px;
  }
}

.pager-wrapper {
  display: flex;
  justify-content: flex-end;
}
.list-enter-active,
.list-leave-active {
  transition: all 0.5s ease;
}
.list-enter-from,
.list-leave-to {
  opacity: 0;
  transform: translateX(30px);
}
</style>