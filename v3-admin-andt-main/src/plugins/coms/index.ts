import { type App } from "vue"
import PerfectScrollbar from "vue3-perfect-scrollbar"

export function loadComponent(app: App) {
  app.use(PerfectScrollbar, {
    watchOptions: true,
    options: {
      suppressScrollX: false,
      suppressScrollY: false
    }
  })
}
