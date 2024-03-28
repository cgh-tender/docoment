<script lang="ts" setup>
import { computed, watchEffect } from "vue"
import { useRoute } from "vue-router"
import { storeToRefs } from "pinia"
import { useAppStore } from "@/store/modules/app"
import { usePermissionStore } from "@/store/modules/permission"
import { useSettingsStore } from "@/store/modules/settings"
import SidebarItem from "./SidebarItem.vue"
import Logo from "../Logo/index.vue"
import { getCssVariableValue } from "@/utils"
import { DeviceEnum } from "@/constants/app-key"
import { useTheme } from "@/hooks/useTheme"

const v3SidebarMenuBgColor = getCssVariableValue("--v3-sidebar-menu-bg-color")
const v3SidebarMenuTextColor = getCssVariableValue("--v3-sidebar-menu-text-color")
const v3SidebarMenuActiveTextColor = getCssVariableValue("--v3-sidebar-menu-active-text-color")

const route = useRoute()
const appStore = useAppStore()
const permissionStore = usePermissionStore()
const settingsStore = useSettingsStore()

const { sidebar, device } = storeToRefs(appStore)
const { layoutMode, showLogo } = storeToRefs(settingsStore)

const activeMenu = computed(() => {
  const {
    meta: { activeMenu },
    path
  } = route
  return activeMenu ? activeMenu : path
})
const isCollapse = computed(() => !sidebar.value.opened)
const isLeft = computed(() => layoutMode.value === "left")
const isTop = computed(() => layoutMode.value === "top")
const isMobile = computed(() => device.value === DeviceEnum.Mobile)
const isLogo = computed(() => isLeft.value && showLogo.value)
const backgroundColor = computed(() => (isLeft.value ? v3SidebarMenuBgColor : undefined))
const activeTextColor = computed(() => (isLeft.value ? v3SidebarMenuActiveTextColor : undefined))
const sidebarMenuItemHeight = computed(() => {
  return layoutMode.value !== "top" ? "var(--v3-sidebar-menu-item-height)" : "var(--v3-navigationbar-height)"
})
const sidebarMenuHoverBgColor = computed(() => {
  return layoutMode.value !== "top" ? "var(--v3-sidebar-menu-hover-bg-color)" : "transparent"
})
const tipLineWidth = computed(() => {
  return layoutMode.value !== "top" ? "2px" : "0px"
})
// 当为顶部模式时隐藏垂直滚动条
const hiddenScrollbarVerticalBar = computed(() => {
  return layoutMode.value === "top" ? "none" : "block"
})
const { activeThemeName } = useTheme()
const id = import.meta.env.VITE_APP_PROJECT_NAME
const scrollbarStyle = computed(() => {
  return isTop.value && !isMobile.value
    ? {
        width: "100%",
        height: "var(--v3-navigationbar-height)"
      }
    : { height: "calc(100.5vh - var(--v3-sidebar-hide-width))" }
})
const style = computed(() => {
  return isTop.value && !isMobile.value
    ? {
        width: "100%",
        height: "var(--v3-navigationbar-height)"
      }
    : { width: "var(--v3-sidebar-width)" }
})
watchEffect(() => {
  console.log(isTop.value)
  console.log(!isMobile.value)
})
</script>

<template>
  <div :class="{ 'has-logo': isLogo }">
    <Logo v-if="isLogo" :collapse="isCollapse" />
    <perfect-scrollbar :style="scrollbarStyle">
      <a-menu
        :style="style"
        :id="id"
        :theme="activeThemeName"
        :mode="isTop && !isMobile ? 'horizontal' : 'inline'"
        :inlineCollapsed="isCollapse"
        :inlineIndent="30"
      >
        <SidebarItem
          v-for="route in permissionStore.routes"
          :key="route.path"
          :item="route"
          :base-path="route.path"
          :is-top="isTop"
        />
      </a-menu>
    </perfect-scrollbar>
  </div>
</template>

<style lang="scss" scoped>
%tip-line {
  &::before {
    content: "";
    position: absolute;
    top: 0;
    left: 0;
    width: v-bind(tipLineWidth);
    height: 100%;
    background-color: var(--v3-sidebar-menu-tip-line-bg-color);
  }
}

.el-menu {
  border: none;
  min-height: 100%;
  width: 100% !important;
}

.el-menu--horizontal {
  height: v-bind(sidebarMenuItemHeight);
}

:deep(.a-menu-item),
:deep(.a-sub-menu__title),
:deep(.a-sub-menu .a-menu-item),
:deep(.el-menu--horizontal .a-menu-item) {
  height: v-bind(sidebarMenuItemHeight);
  line-height: v-bind(sidebarMenuItemHeight);

  &.is-active,
  &:hover {
    background-color: v-bind(sidebarMenuHoverBgColor);
  }

  display: block;

  * {
    horizontal-align: middle;
  }
}

:deep(.a-sub-menu) {
  &.is-active {
    > .a-sub-menu__title {
      color: v-bind(activeTextColor) !important;
    }
  }
}

:deep(.a-menu-item.is-active) {
  @extend %tip-line;
}

.el-menu--collapse {
  :deep(.a-sub-menu.is-active) {
    .a-sub-menu__title {
      @extend %tip-line;
    }
  }
}
</style>
