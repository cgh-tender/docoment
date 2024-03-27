import { type App } from "vue"
import PerfectScrollbar from "vue3-perfect-scrollbar"

export function loadComponent(app: App) {
  /** Vxe Table 组件完整引入 */
  app.use(PerfectScrollbar, {
    watchOptions: true,
    options: {
      suppressScrollX: false,
      suppressScrollY: false
    }
  })
}
