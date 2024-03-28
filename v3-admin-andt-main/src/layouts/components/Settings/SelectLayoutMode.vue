<script lang="ts" setup>
import { computed } from "vue"
import { storeToRefs } from "pinia"
import { useSettingsStore } from "@/store/modules/settings"

const settingsStore = useSettingsStore()

const { layoutMode } = storeToRefs(settingsStore)

const isLeft = computed(() => layoutMode.value === "left")
const isTop = computed(() => layoutMode.value === "top")
const isLeftTop = computed(() => layoutMode.value === "left-top")
</script>

<template>
  <div class="select-layout-mode">
    <a-tooltip title="左侧模式">
      <a-layout class="layout-mode left" :class="{ active: isLeft }" @click="layoutMode = 'left'">
        <a-layout-sider />
        <a-layout>
          <a-layout-header />
          <a-layout-content />
        </a-layout>
      </a-layout>
    </a-tooltip>
    <a-tooltip title="顶部模式">
      <a-layout class="layout-mode top" :class="{ active: isTop }" @click="layoutMode = 'top'">
        <a-layout-header />
        <a-layout-content />
      </a-layout>
    </a-tooltip>
    <a-tooltip title="混合模式">
      <a-layout class="layout-mode left-top" :class="{ active: isLeftTop }" @click="layoutMode = 'left-top'">
        <a-layout-header />
        <a-layout>
          <a-layout-sider />
          <a-layout-content />
        </a-layout>
      </a-layout>
    </a-tooltip>
  </div>
</template>

<style lang="scss" scoped>
.select-layout-mode {
  display: flex;
  justify-content: space-between;
}

.layout-mode {
  width: 60px;
  flex-grow: 0;
  overflow: hidden;
  cursor: pointer;
  border-radius: 6px;
  border: 2px solid #00000000;

  &:hover {
    border: 2px solid var(--el-color-primary);
  }
}

.active {
  border: 2px solid var(--el-color-primary);
}

.a-layout-header {
  height: 12px;
}

.a-layout-sider {
  width: 16px;
}

.left {
  .a-layout-header {
    background-color: var(--el-border-color);
  }

  .a-layout-sider {
    background-color: var(--el-color-primary);
  }

  .a-layout-content {
    background-color: var(--el-fill-color);
  }
}

.top {
  .a-layout-header {
    background-color: var(--el-color-primary);
  }

  .a-layout-content {
    background-color: var(--el-fill-color);
  }
}

.left-top {
  .a-layout-header {
    background-color: var(--el-border-color);
  }

  .a-layout-sider {
    background-color: var(--el-color-primary);
  }

  .a-layout-content {
    background-color: var(--el-fill-color);
  }
}
</style>
