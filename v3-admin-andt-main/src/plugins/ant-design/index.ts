import { type App } from "vue"
import Antd from "ant-design-vue"

export function loadAntDesignPlus(app: App) {
  app.use(Antd)
}
