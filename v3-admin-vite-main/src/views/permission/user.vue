<script lang="ts" setup>
import { ref, watchEffect } from "vue"
import { GetBaseResourceTableData } from "@/api/permission/user/types/base"
import { deleteUserById, getUserStatus, getUserTable } from "@/api/permission/user"
import { ElMessage } from "element-plus"
import { Compass, Delete, Edit, Lock, More, Refresh, Search, Unlock, View, ZoomIn } from "@element-plus/icons-vue"
import updatePassword from "@/views/permission/components/user/updatePassword.vue"
import AddOrUpdate from "@/views/permission/components/user/addOrUpdate.vue"
import { useFullscreenLoading } from "@/hooks/useFullscreenLoading"
import { useUserStoreHook } from "@/store/modules/user"
import { SelectOption } from "@/hooks/useFetchSelect"
import UpdateUserStatus from "@/views/permission/components/user/updateUserStatus.vue"
import { useTable } from "@/hooks/useTable"
import { DefaultUserTableData } from "@/api/permission/resource/types/base"

const dialogVisible = ref<boolean>(false)
const dialogDisable = ref<boolean>(false)
const handleUpdatePassword = ref<boolean>(false)
const titleName = ref<string>("")
const lock = ref<boolean>(false)
const { admin, isAdmin } = useUserStoreHook()
const userStatus = ref<SelectOption[]>([])
const dialogUserStatus = ref<boolean>(false)

const searchData = ref<GetBaseResourceTableData>({
  phone: "",
  username: ""
})

const initFormData: DefaultUserTableData = {
  createTime: "",
  gender: "",
  email: "",
  id: "",
  phone: "",
  realname: "",
  roles: [],
  groups: [],
  organizations: [],
  positions: [],
  status: "",
  username: ""
}

const formData = ref<DefaultUserTableData>(initFormData)

const {
  reset,
  refresh,
  currentPage,
  total,
  pageSize,
  pageSizes,
  layout,
  handleSizeChange,
  handleCurrentChange,
  loading,
  data: tableData
} = useTable(getUserTable, searchData, {})

/**
 * 该函数用于更新用户信息，通过设置相关变量的值，打开对话框以供用户修改用户信息。
 * @param row
 */
const handleUpdate = (row: DefaultUserTableData) => {
  if (row.username && isAdmin(row.username)) {
    ElMessage.error("该用户为系统管理员，无法修改！")
  } else {
    titleName.value = "修改用户"
    formData.value = row
    dialogVisible.value = true
  }
}

const handleAdd = () => {
  titleName.value = "新增用户"
  formData.value = initFormData
  dialogVisible.value = true
}

if (admin) {
  getUserStatus().then((data) => {
    userStatus.value = data.data
  })
}

/**
 * 重置表单状态为初始值。
 */
const resetForm = () => {
  titleName.value = ""
  formData.value = initFormData
}

/**
 * 该函数用于重置搜索条件，并重新获取表格数据。
 * resetForm()函数用于重置表单数据。
 * refresh()函数用于获取表格数据。
 */
const resetSearch = () => {
  resetForm()
  refresh()
}

const showUserDetail = (row: DefaultUserTableData) => {
  formData.value = row
  titleName.value = "用户详情"
  dialogVisible.value = true
  dialogDisable.value = true
}

const handleOpen = (row: DefaultUserTableData) => {
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
}

const deleteUserRow = async (row: DefaultUserTableData) => {
  await useFullscreenLoading(deleteUserById)(row.id)
  resetForm()
  refresh()
}

const handleUpdateUserStatus = (row: DefaultUserTableData) => {
  formData.value.status = row.status
  formData.value.id = row.id
  dialogUserStatus.value = true
}

const handleDialogVisibleClose = () => {
  resetForm()
  refresh()
  dialogVisible.value = false
  dialogDisable.value = false
}
const dialogLoading = (value: boolean) => {
  loading.value = value
}
const tagVisible = ref<boolean>(false)

const parserTagType = (status: string) => {
  if (status) {
    switch (status) {
      case "使用":
        return "success"
      case "锁定":
        return "warning"
      case "删除":
        return "danger"
    }
  } else {
    return "primary"
  }
}
// watchEffect(() => {
//   console.log(currentPage.value)
//   console.log(dialogUserStatus.value)
//   refresh()
// })
watchEffect(() => {
  if (!handleUpdatePassword.value) {
    resetForm()
  }
})
</script>
<template>
  <div class="app-container" v-loading="loading">
    <el-card shadow="never" class="search-wrapper">
      <el-form ref="searchFormRef" :inline="true" :model="searchData">
        <el-form-item prop="username" label="用户名">
          <el-input v-model="searchData.username" placeholder="请输入" />
        </el-form-item>
        <el-form-item prop="phone" label="手机号">
          <el-input v-model="searchData.phone" placeholder="请输入" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :icon="Search" @click="refresh">查询</el-button>
          <el-button :icon="Refresh" @click="reset">重置</el-button>
          <el-button :icon="ZoomIn" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card shadow="never">
      <el-table border :data="tableData">
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column fixed prop="username" label="账号" align="center" />
        <el-table-column prop="realname" label="用户名" align="center" />
        <el-table-column prop="phone" label="手机" align="center" />
        <el-table-column prop="email" label="邮箱" align="center" />
        <el-table-column prop="gender" label="性别" align="center" />
        <el-table-column prop="status" label="状态" align="center">
          <template #default="scope">
            <el-tag
              :style="{ cursor: admin && tagVisible ? 'pointer' : 'text' }"
              @mouseenter="tagVisible = true"
              @mouseleave="tagVisible = false"
              :type="parserTagType(scope.row.status)"
              effect="light"
              @click="admin && tagVisible ? handleUpdateUserStatus(scope.row) : ''"
              :key="scope.row.id"
              >{{ scope.row.status }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="roles" label="角色" align="center">
          <template #default="scope">
            <el-tag type="primary" effect="light" :key="role.id" v-for="role in scope.row.roles"
              >{{ role.name }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="150" align="center">
          <template #default="scope">
            <el-button type="primary" text plain :icon="Edit" size="small" @click="handleUpdate(scope.row)"
              >编辑
            </el-button>
            <el-dropdown @visible-change="handleOpen(scope.row)">
              <el-button type="primary" :icon="More" text plain size="small">
                <el-icon>
                  <arrow-down />
                </el-icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item :icon="View" @click="showUserDetail(scope.row)">详情</el-dropdown-item>
                  <el-dropdown-item :icon="Compass" @click="update_password(scope.row)">密码</el-dropdown-item>
                  <el-dropdown-item :icon="Delete" @click="deleteUserRow(scope.row)">删除</el-dropdown-item>
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
        :layout="layout"
        :page-sizes="pageSizes"
        :total="total"
        :page-size="pageSize"
        :currentPage="currentPage"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </el-card>
    <!-- 新增/修改 -->
    <transition name="el-zoom-in-center">
      <div v-if="dialogVisible">
        <add-or-update
          :dialog-visible="dialogVisible"
          :is-disabled="dialogDisable"
          v-model:form-data="formData"
          :title-name="titleName"
          @dialogVisibleClose="handleDialogVisibleClose"
          @dialogLoading="dialogLoading"
        />
      </div>
    </transition>
    <transition>
      <div v-if="handleUpdatePassword">
        <updatePassword v-model:openUpdatePassword="handleUpdatePassword" v-model:user-id="formData.id" />
      </div>
    </transition>
    <transition>
      <div v-if="dialogUserStatus">
        <update-user-status
          v-model:dialogUserStatus="dialogUserStatus"
          v-model:status="formData.status"
          v-model:user-id="formData.id"
          @getTableData="refresh"
        />
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
