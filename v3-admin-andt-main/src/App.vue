<script lang="ts" setup>
import { useTheme } from "@/hooks/useTheme";
// 将 Ant-design-ue 的语言设置为中文
import { notification } from "antd";
import { computed } from "vue";

const { initTheme } = useTheme();

/** 初始化主题 */
initTheme();
const scrollbarStyle = computed(() => {
  return { height: "100vh", width: "100vw" };
});
setTimeout(() => {
  notification.config({
    placement: "bottomRight",
    bottom: 50,
    duration: 3,
    rtl: true
  });
}, 2000);
</script>

<template>
  <perfect-scrollbar :style="scrollbarStyle">
    <router-view />
  </perfect-scrollbar>
</template>
