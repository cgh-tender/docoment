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
const data = ref([])
const handleNodeClick = (data: SelectOption) => {
  console.log(data)
}
const getTableData = () => {}
</script>

<template>
  <div v-loading="loading">
    <el-container>
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
        <el-aside width="200px">
          <el-card>
            <el-tree style="max-width: 600px" :data="data" @node-click="handleNodeClick" />
          </el-card>
        </el-aside>
        <el-container>
          <el-main>
            <el-card>
              <el-tree style="max-width: 600px" :data="data" @node-click="handleNodeClick" />
            </el-card>
          </el-main>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<style lang="scss" scoped></style>
