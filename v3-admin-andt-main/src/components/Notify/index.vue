<script lang="ts" setup>
import { computed, ref } from "vue"
import NotifyList from "./NotifyList.vue"
import { type ListItem, messageData, notifyData, todoData } from "./data"
import { notification } from "antd"
import { NotificationPlacement } from "ant-design-vue"

type TabName = "通知" | "消息" | "待办"

interface DataItem {
  name: TabName
  type: "primary" | "success" | "warning" | "danger" | "info"
  list: ListItem[]
}

/** 角标当前值 */
const badgeValue = computed(() => {
  return data.value.reduce((sum, item) => sum + item.list.length, 0)
})
/** 角标最大值 */
const badgeMax = 99
/** 当前 Tab */
const activeName = ref<TabName>("通知")
/** 所有数据 */
const data = ref<DataItem[]>([
  // 通知数据
  {
    name: "通知",
    type: "primary",
    list: notifyData
  },
  // 消息数据
  {
    name: "消息",
    type: "danger",
    list: messageData
  },
  // 待办数据
  {
    name: "待办",
    type: "warning",
    list: todoData
  }
])

const handleHistory = (placement: NotificationPlacement) => {
  notification.success({
    message: `跳转到${activeName.value}历史页面`,
    placement: "top",
    duration: 100
  })
}
</script>
<template>
  <div class="notify">
    <a-popover placement="bottom" trigger="click">
      <a-badge
        :count="badgeValue"
        :max="badgeMax"
        :hidden="badgeValue === 0"
        :number-style="{ backgroundColor: '#52c41a' }"
      >
        <a-tooltip color="dark" title="消息通知" placement="bottom">
          <PlayCircleOutlined style="font-size: 20px" />
        </a-tooltip>
      </a-badge>
      <template #content>
        <a-tabs v-model:active-key="activeName" stretch>
          <a-tab-pane v-for="(item, index) in data" :key="item.name">
            <template #tab>
              <a-badge :count="item.list.length" :max="badgeMax" :number-style="{ backgroundColor: '#52c41a' }">
                <span>{{ item.name }}</span>
              </a-badge>
            </template>
            <perfect-scrollbar :heigh="400" :width="350">
              <NotifyList :list="item.list" />
            </perfect-scrollbar>
          </a-tab-pane>
        </a-tabs>
        <div class="notify-history">
          <a-button link @click="handleHistory">查看{{ activeName }}历史</a-button>
        </div>
      </template>
    </a-popover>
  </div>
</template>

<style lang="scss" scoped>
.notify {
  margin-right: 10px;
  color: var(--el-text-color-regular);
}

.notify-history {
  text-align: center;
  padding-top: 12px;
  border-top: 1px solid var(--el-border-color);
}
</style>
