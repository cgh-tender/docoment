<script lang="ts" setup>
import { computed, ref } from "vue"
import { GetBaseUserTableData } from "@/api/permission/user/types/base"
import { SelectOption } from "@/hooks/useFetchSelect"
import { FileSearchOutlined, ReloadOutlined, UserAddOutlined } from "@ant-design/icons-vue"

const loading = ref<boolean>(false)

const searchData = computed<GetBaseUserTableData>(() => {
  return {
    currentPage: pagination.currentPage,
    pageSize: pagination.pageSize
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
// 默认选中
const selectedKeys = ref<string[]>([])
// 默认打开
const expandedKeys = ref<string[]>([])
</script>

<template>
  <div class="app-container" v-loading="loading">
    <a-card shadow="never">
      <a-form ref="searchFormRef" layout="inline" :model:value="searchData">
        <a-form-item prop="username" label="用户名">
          <a-input v-model="searchData.username" placeholder="请输入" />
        </a-form-item>
        <a-form-item prop="phone" label="手机号">
          <a-input v-model="searchData.phone" placeholder="请输入" />
        </a-form-item>
        <a-form-item style="position: absolute; right: 20px">
          <a-button>
            <template #icon>
              <FileSearchOutlined />
            </template>
            查询
          </a-button>
          <a-button>
            <template #icon>
              <ReloadOutlined />
            </template>
            重置
          </a-button>
          <a-button>
            <template #icon>
              <UserAddOutlined />
            </template>
            新增
          </a-button>
        </a-form-item>
      </a-form>
    </a-card>
    <a-layout style="padding: 20px 0 0 0">
      <perfect-scrollbar>
        <a-layout-sider>
          <a-directory-tree
            v-model:expandedKeys="expandedKeys"
            v-model:selectedKeys="selectedKeys"
            multiple
            :tree-data="data"
          />
        </a-layout-sider>
      </perfect-scrollbar>
    </a-layout>
  </div>
</template>

<style lang="scss" scoped>
.ps {
  height: calc(100vh - var(--v3-header-height) - 125px);
}

.ant-btn {
  margin: 0 10px 0 10px;
}

.ant-card .ant-card-body {
  padding: 10px;
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
