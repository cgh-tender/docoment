<script lang="ts" setup>
import { useTheme } from "@/hooks/useTheme"
import PF from "@/icons/svg/pf.svg?component"
const { themeList, activeThemeName, setTheme } = useTheme()
</script>

<template>
  <div>
    <a-dropdown trigger="click" @command="setTheme">
      <a-tooltip placement="bottom">
        <template #title>
          <span>主题模式</span>
        </template>
        <PF w5 h5 />
      </a-tooltip>
      <template #overlay>
        <a-menu>
          <a-menu-item
            v-for="(theme, index) in themeList"
            :key="index"
            :disabled="activeThemeName === theme.name"
            :command="theme.name"
          >
            <span>{{ theme.title }}</span>
          </a-menu-item>
        </a-menu>
      </template>
    </a-dropdown>
  </div>
</template>

<style lang="css">
.a-tooltip {
}
</style>
