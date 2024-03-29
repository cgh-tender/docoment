<script lang="ts" setup>
import { computed } from "vue"
import { type RouteRecordRaw } from "vue-router"
import SidebarItemLink from "./SidebarItemLink.vue"
import { isExternal } from "@/utils/validate"
import path from "path-browserify"
import XEUtils from "xe-utils"

interface Props {
  item: RouteRecordRaw
  isCollapse?: boolean
  isTop?: boolean
  isFirstLevel?: boolean
  basePath?: string
}

const props = withDefaults(defineProps<Props>(), {
  isCollapse: false,
  isTop: false,
  isFirstLevel: true,
  basePath: ""
})

/** 是否始终显示根菜单 */
const alwaysShowRootMenu = computed(() => props.item.meta?.alwaysShow)

/** 显示的子菜单 */
const showingChildren = computed(() => {
  return props.item.children?.filter((child) => !child.meta?.hidden) ?? []
})

/** 显示的子菜单数量 */
const showingChildNumber = computed(() => {
  return showingChildren.value.length
})

/** 唯一的子菜单项 */
const theOnlyOneChild = computed(() => {
  const number = showingChildNumber.value
  switch (true) {
    case number > 1:
      return null
    case number === 1:
      return showingChildren.value[0]
    default:
      return { ...props.item }
  }
})

/** 解析路径 */
const resolvePath = (routePath: string) => {
  switch (true) {
    case isExternal(routePath):
      return routePath
    case isExternal(props.basePath):
      return props.basePath
    default:
      return path.resolve(props.basePath, routePath)
  }
}
</script>

<template>
  <div
    v-if="!props.item.meta?.hidden"
    :class="{ 'simple-mode': props.isCollapse && !isTop, 'first-level': props.isFirstLevel }"
  >
    <template
      v-if="
        !alwaysShowRootMenu && theOnlyOneChild && (!theOnlyOneChild.children || theOnlyOneChild?.children?.length == 0)
      "
    >
      <SidebarItemLink v-if="theOnlyOneChild.meta" :to="resolvePath(theOnlyOneChild.path)">
        <a-menu-item
          :index="resolvePath(theOnlyOneChild.path)"
          :key="theOnlyOneChild.path"
          :label="theOnlyOneChild.meta.title"
        >
          <SvgIcon v-if="theOnlyOneChild.meta.svgIcon" :name="theOnlyOneChild.meta.svgIcon" />
          <component v-else-if="theOnlyOneChild.meta.elIcon" :is="theOnlyOneChild.meta.elIcon" class="el-icon" />
          <ChromeOutlined v-else />
          <span v-if="theOnlyOneChild.meta.title">{{ theOnlyOneChild.meta.title }} </span>
        </a-menu-item>
      </SidebarItemLink>
    </template>
    <a-sub-menu
      v-else-if="!XEUtils.isEmpty(props.item.children)"
      :index="resolvePath(props.item.path)"
      :label="props.item.meta?.title"
    >
      <template #title>
        <SvgIcon v-if="props.item.meta?.svgIcon" :name="props.item.meta.svgIcon" />
        <component v-else-if="props.item.meta?.elIcon" :is="props.item.meta.elIcon" class="el-icon" />
        <span v-if="props.item.meta?.title">{{ props.item.meta.title }}</span>
      </template>
      <sidebar-item
        v-for="child in props.item.children"
        :key="child.path"
        :item="child"
        :is-collapse="props.isCollapse"
        :is-first-level="false"
        :base-path="resolvePath(child.path)"
      />
    </a-sub-menu>
  </div>
</template>

<style lang="scss" scoped>
.ant-menu-item-active:hover {
  background-color: #c44343;
  color: #ffff;
}

.simple-mode {
  &.first-level {
    :deep(.a-sub-menu) {
      .a-sub-menu__icon-arrow {
        display: none;
      }

      span {
        visibility: hidden;
      }
    }
  }
}
</style>
