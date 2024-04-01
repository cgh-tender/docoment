<script lang="ts" setup>
import { ref } from "vue"
import { Refresh, Search, ZoomIn } from "@element-plus/icons-vue"
import { SelectOption } from "@/hooks/useFetchSelect"
import { useTable } from "@/hooks/useTable"
import { getResourceTable } from "@/api/permission/resource"

const searchData = ref({
  // 菜单id
  id: "",
  // 菜单名称
  name: "",
  // 菜单资源类别
  status: ""
})
const { loading, refresh, total, currentPage, handleCurrentChange, handleSizeChange, reset } = useTable(
  getResourceTable,
  searchData,
  {}
)

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
  }
])
</script>

<template>
  <div class="app-container" v-loading="loading">
    <el-card shadow="never">
      <el-form ref="searchFormRef" :inline="true" :model="searchData">
        <el-form-item prop="id" label="菜单CODE">
          <el-input v-model="searchData.id" placeholder="请输入菜单code" />
        </el-form-item>
        <el-form-item prop="name" label="菜单名称">
          <el-input v-model="searchData.name" placeholder="请输入菜单名称" />
        </el-form-item>
        <el-form-item prop="status" label="菜单类型">
          <el-input v-model="searchData.status" placeholder="请选择菜单类型" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :icon="Search" @click="refresh">查询</el-button>
          <el-button :icon="Refresh" @click="reset">重置</el-button>
          <el-button :icon="ZoomIn">新增</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <div class="app-main">
      <div class="app-aside">
        <el-scrollbar>
          <el-card>
            <el-tree :data="data" @node-click="refresh" />
          </el-card>
        </el-scrollbar>
      </div>
      <div class="app-center">
        <el-scrollbar>
          <el-text>ssss</el-text>
          <el-tree :data="data" @node-click="refresh" />
        </el-scrollbar>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.app-container {
  padding: 20px 20px 0 20px;

  .app-main {
    display: inline-flex;
    width: 100% !important;

    .app-aside {
      background-color: var(--v3-body-bg-color);
      height: calc(100vh - 200px);
      width: 20%;
      padding: 20px 20px 0 0;

      .el-card {
        flex: 1 1 auto;
        height: 100%;
      }
    }

    .app-center {
      background-color: var(--v3-body-bg-color);
      padding: 20px 0 0 0;
      width: 80%;

      .el-card {
        flex: 1 1 auto;
        height: 100%;
      }
    }
  }
}

.el-container {
  display: flex;
  height: calc(100vh - 200px); /* 设置容器高度为视口高度 */
}

.el-tree-node__label //设置字体大小
{
  font-size: 12px;
}

//修改前面展开的图标
.el-tree .el-tree-node__expand-icon.expanded {
  -webkit-transform: rotate(0deg);
  transform: rotate(0deg);
}

.el-tree .el-icon-caret-right:before {
  background: url("@/assets/layouts/logo.png") no-repeat;
  content: "";
  display: block;
  width: 18px;
  height: 18px;
  font-size: 18px;
  background-size: 15px;
}

//判断子节点设置不同的样式
.el-tree-node__expand-icon.is-leaf::before {
  background: url("@/assets/layouts/logo.png") no-repeat;
  content: "";
  display: block;
  width: 18px;
  height: 18px;
  font-size: 18px;
  background-size: 15px;
}

//设置每一项的背景颜色
.el-tree-node__content {
  background: #f5f8fa;
  height: 36px;
}

//设置当前选中项目的颜色
.el-tree-node.is-current > .el-tree-node__content {
  background-color: #fde9be !important; //背景颜色
  color: #333333; //字体颜色
}
</style>
