<script lang="ts" setup>
import { computed, ref } from "vue";
import { GetBaseUserTableData } from "@/api/permission/user/types/base";
import { usePagination } from "@/hooks/usePagination";
import { SelectOption } from "@/hooks/useFetchSelect";

const loading = ref<boolean>(false);
const { paginationData, handleCurrentChange, handleSizeChange } = usePagination({
  currentPage: 1,
  pageSize: 2
});
const searchData = computed<GetBaseUserTableData>(() => {
  return {
    currentPage: paginationData.currentPage,
    pageSize: paginationData.pageSize
  };
});
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
]);
const handleNodeClick = (data: SelectOption) => {
  console.log(data);
};
const getTableData = () => {
};
// 默认选中
const selectedKeys = ref<string[]>([]);
// 默认打开
const expandedKeys = ref<string[]>([]);
</script>

<template>
  <div class="app-container" v-loading="loading">
    <a-card shadow="never">
      <a-form ref="searchFormRef" :inline="true" :model="searchData">
        <a-form-item prop="username" label="用户名">
          <a-input v-model="searchData.username" placeholder="请输入" />
        </a-form-item>
        <a-form-item prop="phone" label="手机号">
          <a-input v-model="searchData.phone" placeholder="请输入" />
        </a-form-item>
        <a-form-item>
          <a-button type="primary" :icon="Search" @click="getTableData">查询</a-button>
          <a-button :icon="Refresh">重置</a-button>
          <a-button :icon="ZoomIn">新增</a-button>
        </a-form-item>
      </a-form>
    </a-card>
    <a-layout>
      <a-layout-sider>
        <perfect-scrollbar>
          <a-card>
            <el-tree :data="data" @node-click="handleNodeClick" />
          </a-card>
          <a-directory-tree
            v-model:expandedKeys="expandedKeys"
            v-model:selectedKeys="selectedKeys"
            multiple
            :tree-data="data"
          />
        </perfect-scrollbar>
      </a-layout-sider>
      <a-layout-content />
    </a-layout>
  </div>
</template>

<style lang="scss" scoped>
.a-layout {
  display: flex;
  height: calc(100vh - var(--v3-header-height)); /* 设置容器高度为视口高度 */

  .el-aside {
    padding: 20px 20px 0 0;
  }

  .a-layout-content {
    padding: 20px 0 0 0;
  }

  .a-card {
    flex: 1 1 auto;
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
