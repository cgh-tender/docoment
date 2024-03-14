<script lang="ts" setup>
import { computed, ref, watchEffect } from "vue"
import { DefaultUserTableData, GetBaseUserTableData } from "@/api/permission/user/types/base"
import { getUserTable } from "@/api/permission/user"
import { usePagination } from "@/hooks/usePagination"
import { type FormInstance } from "element-plus"
import { Compass, Delete, Edit, Lock, More, Refresh, Search, Unlock, View, ZoomIn } from "@element-plus/icons-vue"
import updatePassword from "@/views/permission/components/user/updatePassword.vue"
import AddOrUpdate from "@/views/permission/components/user/addOrUpdate.vue"

const loading = ref<boolean>(false)
const dialogVisible = ref<boolean>(false)
const handleUpdatePassword = ref<boolean>(false)
const titleName = ref<string>("")
const tableData = ref<DefaultUserTableData[]>([])
const lock = ref<boolean>(false)

const searchData = computed<GetBaseUserTableData>(() => {
  return {
    currentPage: paginationData.currentPage,
    pageSize: paginationData.pageSize
  }
})
const { paginationData, handleCurrentChange, handleSizeChange } = usePagination({
  currentPage: 1,
  pageSize: 2
})

const formRef = ref<FormInstance | null>(null)
const initFormData = {
  createTime: "",
  email: "",
  id: undefined,
  password: "",
  phone: "",
  roles: "",
  status: "",
  username: ""
}
const formData = ref<DefaultUserTableData>(initFormData)

/**
 * 该函数用于更新用户信息，通过设置相关变量的值，打开对话框以供用户修改用户信息。
 * @param row
 */
const handleUpdate = (row: DefaultUserTableData) => {
  titleName.value = "修改用户"
  formData.value = row
  dialogVisible.value = true
}

/**
 * 重置表单状态为初始值。
 */
const resetForm = () => {
  titleName.value = ""
  formData.value = initFormData
}

/**
 * 该Vue template函数getTableData用于加载表格数据，
 * 通过调用getUserTable接口获取数据并更新分页和表格显示内容，
 * 同时处理loading状态，加载中为true，
 * 加载完毕后根据请求结果设置为false。
 */
const getTableData = () => {
  loading.value = true
  getUserTable(searchData.value)
    .then((res) => {
      paginationData.total = res.total
      tableData.value = res.records
    })
    .catch(() => {
      tableData.value = []
    })
    .finally(() => {
      loading.value = false
    })
}

/**
 * 该函数用于重置搜索条件，并重新获取表格数据。
 * resetForm()函数用于重置表单数据。
 * getTableData()函数用于获取表格数据。
 */
const resetSearch = () => {
  resetForm()
  getTableData()
}

const showUserDetail = (row: DefaultUserTableData) => {
  formData.value = row
  titleName.value = "用户详情"
  dialogVisible.value = true
}

const handleOpen = (row: DefaultUserTableData) => {
  console.log(handleUpdatePassword.value)
  lock.value = "正常" === row.status
}

/**
 * 该函数接收一个用户数据对象作为参数，
 * 其功能是打开密码更新界面并把传入的用户数据赋值给formData。
 * @param row
 */
const update_password = (row: DefaultUserTableData) => {
  formData.value = row
  handleUpdatePassword.value = true
  console.log(handleUpdatePassword.value)
}

const handleCreate = () => {
  formRef.value?.validate((valid: boolean, fields) => {
    if (valid) {
      console.log(formData)
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

const handleDialogVisibleClose = () => {
  console.log(111)
  resetForm()
  getTableData()
  dialogVisible.value = false
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
          <el-button :icon="ZoomIn" @click="resetSearch">新增</el-button>
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
            <el-button type="primary" text plain :icon="Edit" size="small" @click="handleUpdate(scope.row)"
              >编辑
            </el-button>
            <el-dropdown @visible-change="handleOpen(scope.row)">
              <el-button type="primary" :icon="More" text plain size="small">
                <el-icon class="el-icon--right">
                  <arrow-down />
                </el-icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item :icon="View" @click="showUserDetail(scope.row)">详情</el-dropdown-item>
                  <el-dropdown-item :icon="Compass" @click="update_password(scope.row)">密码</el-dropdown-item>
                  <el-dropdown-item :icon="Delete">删除</el-dropdown-item>
                  <el-dropdown-item v-if="lock" :icon="Lock">冻结</el-dropdown-item>
                  <el-dropdown-item v-if="!lock" :icon="Unlock">解冻</el-dropdown-item>
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
      <div v-if="dialogVisible">
        <add-or-update
          v-model:dialog-visible="dialogVisible"
          v-model:form-data="formData"
          v-model:title-name="titleName"
          @dialogVisibleClose="handleDialogVisibleClose"
        />
      </div>
    </transition>
    <transition>
      <div v-if="handleUpdatePassword">
        <updatePassword v-model:openUpdatePassword="handleUpdatePassword" v-model:user="formData" />
      </div>
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
