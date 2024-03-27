import { type App } from "vue"
import { loadVxeTable } from "./vxe-table"
import { loadAntDesignPlus } from "@/plugins/ant-design"
import { loadAntDesignIocn } from "@/plugins/ant-design-icon"
import { loadTsxTable } from "@/plugins/tsx"
import { loadComponent } from "@/plugins/coms"

export function loadPlugins(app: App) {
  loadAntDesignPlus(app)
  loadAntDesignIocn(app)
  loadVxeTable(app)
  loadTsxTable(app)
  loadComponent(app)
}
