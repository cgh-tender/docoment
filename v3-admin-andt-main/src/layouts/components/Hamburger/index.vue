<script lang="ts" setup>
interface Props {
  isActive?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  isActive: false
})

/** Vue 3.3+ defineEmits 语法 */
const emit = defineEmits<{
  toggleClick: []
}>()

const toggleClick = () => {
  emit("toggleClick")
}
</script>

<template>
  <div @click="toggleClick">
    <MenuFoldOutlined v-if="props.isActive" />
    <MenuUnfoldOutlined v-else />
  </div>
</template>

<style lang="scss" scoped>
.icon {
  vertical-align: middle;
}
</style>
