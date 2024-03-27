import { type App } from "vue"
import { Typography } from "ant-design-vue"

const { Text } = Typography

export function loadTsxTable(app: App) {
  /** Vxe Table 组件完整引入 */
  app.component("TextContent", Text)
}
