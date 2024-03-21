<script lang="ts" setup>
import { computed, ref, watchEffect } from "vue"
import { Refresh, Search, ZoomIn } from "@element-plus/icons-vue"
import { GetBaseUserTableData, ResourceQueryPojo } from "@/api/permission/user/types/base"
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
  {
    value: "1",
    label: "1"
  },
  {
    value: "1",
    label: "1"
  },
  {
    value: "1",
    label: "1"
  },
  {
    value: "1",
    label: "1"
  },
  {
    value: "1",
    label: "1"
  },
  {
    value: "1",
    label: "1"
  },
  {
    value: "1",
    label: "1"
  },
  {
    value: "1",
    label: "1"
  },
  {
    value: "1",
    label: "1"
  },
  {
    value: "1",
    label: "1"
  },
  {
    value: "1",
    label: "1"
  },
  {
    value: "1",
    label: "1"
  },
  {
    value: "1",
    label: "1"
  },
  {
    value: "1",
    label: "1"
  }
])

const queryInfo = ref<ResourceQueryPojo>()
const handleNodeClick = (data: SelectOption) => {
  console.log(data)
}
watchEffect(() => {
  getResource
})
const getTableData = () => {}
</script>

<template>
  <div class="app-container" v-loading="loading">
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
          <el-button :icon="Refresh">重置</el-button>
          <el-button :icon="ZoomIn">新增</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-container>
      <el-aside>
        <el-scrollbar>
          <el-card>
            <el-tree :data="data" @node-click="handleNodeClick" />
          </el-card>
        </el-scrollbar>
      </el-aside>
      <el-main>
        <el-scrollbar>
          <el-card>
            <el-text>ssss</el-text>
            <el-tree :data="data" @node-click="handleNodeClick" />
          </el-card>
        </el-scrollbar>
      </el-main>
    </el-container>
  </div>
</template>

<style lang="scss" scoped>
.app-container {
  padding: 20px 20px 0 20px;
}

.el-container {
  display: flex;
  height: calc(100vh - 200px); /* 设置容器高度为视口高度 */

  .el-aside {
    padding: 20px 20px 0 0;

    .el-card {
      flex: 1 1 auto;
      height: 100%;
    }
  }

  .el-main {
    padding: 20px 0 0 0;

    .el-card {
      flex: 1 1 auto;
      height: 100%;
    }
  }
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
