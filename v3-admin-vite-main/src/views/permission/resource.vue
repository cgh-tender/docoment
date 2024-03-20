<script lang="ts" setup>
import { computed, ref } from "vue"
import { Refresh, Search, ZoomIn } from "@element-plus/icons-vue"
import { GetBaseUserTableData } from "@/api/permission/user/types/base"
import { usePagination } from "@/hooks/usePagination"
import { SelectOption } from "@/hooks/useFetchSelect"

const loading = ref<boolean>(false)
const { paginationData, handleCurrentChange, handleSizeChange } = usePagination({
  currentPage: 1,
  pageSize: 2
})
const searchData = computed<GetBaseUserTableData>(() => {
  return {
    currentPage: paginationData.currentPage,
    pageSize: paginationData.pageSize
  }
})
const data = ref<SelectOption[]>([
  {
    value: "1",
    label: "1",
    children: [
      {
        value: "1",
        label: "1"
      }
    ]
  },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" },
  { value: "1", label: "1" }
])
const handleNodeClick = (data: SelectOption) => {
  console.log(data)
}
const getTableData = () => {}
</script>

<template>
  <el-container v-loading="loading">
    <el-header>
      <el-card shadow="never">
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
            <el-button :icon="ZoomIn" @click="handleAdd">新增</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-header>
    <el-container>
      <el-scrollbar>
        <el-aside>
          <el-card>
            <el-tree :data="data" @node-click="handleNodeClick" />
          </el-card>
        </el-aside>
      </el-scrollbar>

      <el-container>
        <el-main>
          <el-card></el-card>
        </el-main>
      </el-container>
    </el-container>
  </el-container>
</template>

<style lang="scss" scoped>
.el-container .el-header {
  height: auto;
}

.el-container .el-card {
  height: 100%;
}

.el-container .el-aside {
  padding: 20px 10px 10px 20px;
  height: 100%;
}

.el-container .el-main {
  padding: 20px 20px 10px 10px;
  height: 100%;
}

/* 全局样式表 */
.el-tree {
  background-color: yellow;
  font-size: 14px;
  height: auto;
}

.el-tree .el-tree-node__content {
  padding: 10px;
  margin: 2px;
  cursor: pointer;
}

.el-tree .el-tree-node.is-expanded .el-tree-node__children {
  background: #fff;
}

.scrollbar-custom .el-scrollbar__wrap {
  width: 100%;
  height: 100%;
}
</style>
