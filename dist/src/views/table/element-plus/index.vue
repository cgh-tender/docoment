<script lang="ts" setup>
import { reactive, ref } from "vue"
import { createTableDataApi, deleteTableDataApi, getTableDataApi, updateTableDataApi } from "@/api/table"
import { type GetTableData } from "@/api/table/types/table"
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from "element-plus"
import { CirclePlus, Delete, Download, Refresh, RefreshRight, Search } from "@element-plus/icons-vue"
import { useTable } from "@/hooks/useTable"

defineOptions({
  // 命名当前组件
  name: "ElementPlus"
})

const searchData = ref({
  username: "",
  phone: ""
})
const {
  data: tableData,
  total,
  currentPage,
  refresh,
  reset,
  loading,
  layout,
  pageSizes,
  pageSize,
  handleSizeChange,
  handleCurrentChange
} = useTable(getTableDataApi, searchData, {})

//#region 增
const dialogVisible = ref<boolean>(false)
const formRef = ref<FormInstance | null>(null)
const formData = reactive({
  username: "",
  password: ""
})
const formRules: FormRules = reactive({
  username: [{ required: true, trigger: "blur", message: "请输入用户名" }],
  password: [{ required: true, trigger: "blur", message: "请输入密码" }]
})
const handleCreate = () => {
  formRef.value?.validate((valid: boolean, fields) => {
    if (valid) {
      if (currentUpdateId.value === undefined) {
        createTableDataApi(formData)
          .then(() => {
            ElMessage.success("新增成功")
            refresh()
          })
          .finally(() => {
            dialogVisible.value = false
          })
      } else {
        updateTableDataApi({
          id: currentUpdateId.value,
          username: formData.username
        })
          .then(() => {
            ElMessage.success("修改成功")
            refresh()
          })
          .finally(() => {
            dialogVisible.value = false
          })
      }
    } else {
      console.error("表单校验不通过", fields)
    }
  })
}
const resetForm = () => {
  currentUpdateId.value = undefined
  formData.username = ""
  formData.password = ""
}
//#endregion

//#region 删
const handleDelete = (row: GetTableData) => {
  ElMessageBox.confirm(`正在删除用户：${row.username}，确认删除？`, "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(() => {
    deleteTableDataApi(row.id).then(() => {
      ElMessage.success("删除成功")
      refresh()
    })
  })
}
//#endregion

//#region 改
const currentUpdateId = ref<undefined | string>(undefined)
const handleUpdate = (row: GetTableData) => {
  currentUpdateId.value = row.id
  formData.username = row.username
  dialogVisible.value = true
}
//#endregion

//#region 查
const searchFormRef = ref<FormInstance | null>(null)
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
          <el-button type="primary" :icon="Search" @click="refresh">查询</el-button>
          <el-button :icon="Refresh" @click="reset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card v-loading="loading" shadow="never">
      <div class="toolbar-wrapper">
        <div>
          <el-button type="primary" :icon="CirclePlus" @click="dialogVisible = true">新增用户</el-button>
          <el-button type="danger" :icon="Delete">批量删除</el-button>
        </div>
        <div>
          <el-tooltip content="下载">
            <el-button type="primary" :icon="Download" circle />
          </el-tooltip>
          <el-tooltip content="刷新当前页">
            <el-button type="primary" :icon="RefreshRight" circle @click="refresh" />
          </el-tooltip>
        </div>
      </div>
      <div class="table-wrapper">
        <el-table :data="tableData">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column prop="username" label="用户名" align="center" />
          <el-table-column prop="roles" label="角色" align="center">
            <template #default="scope">
              <el-tag v-if="scope.row.roles === 'admin'" effect="plain">admin</el-tag>
              <el-tag v-else type="warning" effect="plain">{{ scope.row.roles }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="phone" label="手机号" align="center" />
          <el-table-column prop="email" label="邮箱" align="center" />
          <el-table-column prop="status" label="状态" align="center">
            <template #default="scope">
              <el-tag v-if="scope.row.status" type="success" effect="plain">启用</el-tag>
              <el-tag v-else type="danger" effect="plain">禁用</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" align="center" />
          <el-table-column fixed="right" label="操作" width="150" align="center">
            <template #default="scope">
              <el-button type="primary" text bg size="small" @click="handleUpdate(scope.row)">修改</el-button>
              <el-button type="danger" text bg size="small" @click="handleDelete(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="pager-wrapper">
        <el-pagination
          background
          :layout="layout"
          :page-sizes="pageSizes"
          :total="total"
          :page-size="pageSize"
          :currentPage="currentPage"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
    <!-- 新增/修改 -->
    <el-dialog
      v-model="dialogVisible"
      :title="currentUpdateId === undefined ? '新增用户' : '修改用户'"
      @close="resetForm"
      width="30%"
    >
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="100px" label-position="left">
        <el-form-item prop="username" label="用户名">
          <el-input v-model="formData.username" placeholder="请输入" />
        </el-form-item>
        <el-form-item prop="password" label="密码" v-if="currentUpdateId === undefined">
          <el-input v-model="formData.password" placeholder="请输入" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleCreate">确认</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style lang="scss" scoped>
.search-wrapper {
  margin-bottom: 20px;

  :deep(.el-card__body) {
    padding-bottom: 2px;
  }
}

.toolbar-wrapper {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.table-wrapper {
  margin-bottom: 20px;
}

.pager-wrapper {
  display: flex;
  justify-content: flex-end;
}
</style>
