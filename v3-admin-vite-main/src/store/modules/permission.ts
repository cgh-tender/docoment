import { ref } from "vue"
import store from "@/store"
import { defineStore } from "pinia"
import { type RouteRecordRaw } from "vue-router"
import { constantRoutes, endRoutes as endRoute } from "@/router"

export const usePermissionStore = defineStore("permission", () => {
  const routes = ref<RouteRecordRaw[]>(constantRoutes)
  const endRoutes = ref<RouteRecordRaw[]>(endRoute)

  const LocalRoute = ref<RouteRecordRaw[]>([])

  const setRoutes = (route: RouteRecordRaw[]) => {
    if (route.length > 0) {
      routes.value = routes.value.concat(route)
    } else {
      routes.value = constantRoutes
    }
  }

  return { endRoutes, setRoutes, LocalRoute, routes }
})

/** 在 setup 外使用 */
export function usePermissionStoreHook() {
  return usePermissionStore(store)
}
