<script lang="ts" setup>
import { h } from "vue"
import { useTheme } from "@/hooks/useTheme"
import { ElNotification } from "element-plus"
// 将 Element Plus 的语言设置为中文
import zhCn from "element-plus/es/locale/lang/zh-cn"
const { initTheme } = useTheme()

/** 初始化主题 */
initTheme()

setTimeout(() => {
  ElNotification({
    title: "代办事项",
    message: h("a", { style: "color: teal", target: "_blank" }, "测试弹出内容"),
    type: "success",
    duration: 0,
    position: "bottom-right"
  })
}, 2000)
</script>

<template>
  <el-config-provider :locale="zhCn">
    <router-view />
  </el-config-provider>
</template>
