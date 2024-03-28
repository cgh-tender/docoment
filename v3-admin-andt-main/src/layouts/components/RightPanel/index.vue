<script lang="ts" setup>
import { ref } from "vue"
import { SettingOutlined } from "@ant-design/icons-vue"

interface Props {
  buttonTop?: number
}

const props = withDefaults(defineProps<Props>(), {
  buttonTop: 350
})

const buttonTopCss = props.buttonTop + "px"
const show = ref(false)
</script>

<template>
  <a-button class="handle-button" @click="show = true">
    <template #icon>
      <SettingOutlined :size="24" />
    </template>
  </a-button>
  <a-drawer v-model:open="show" size="300px" :with-header="false">
    <slot />
  </a-drawer>
</template>

<style lang="scss" scoped>
.handle-button {
  width: 48px;
  height: 48px;
  background-color: var(--v3-rightpana-button-bg-color);
  position: fixed;
  top: v-bind(buttonTopCss);
  right: 0;
  border-radius: 6px 0 0 6px;
  z-index: 10;
  cursor: pointer;
  pointer-events: auto;
  color: #fff;
  display: flex;
  align-items: center;

  span {
    display: flex;
    align-items: center;
    justify-content: center;
  }
}
</style>
