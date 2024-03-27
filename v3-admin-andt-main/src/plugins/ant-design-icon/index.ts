import { type App } from "vue"
import * as AntDesignIcon from "@ant-design/icons-vue"

export function loadAntDesignIocn(app: App) {
  /** 注册所有 Element Plus Icon */
  for (const [key, component] of Object.entries(AntDesignIcon)) {
    app.component(key, component)
  }
}
