import { ref } from "vue"
import store from "@/store"
import { defineStore } from "pinia"
import { type RouteRecordRaw } from "vue-router"
import router, { constantRoutes, endRoutes as endRoute } from "@/router"

export const usePermissionStore = defineStore("permission", () => {
  const routes = ref<RouteRecordRaw[]>(constantRoutes)
  const endRoutes = ref<RouteRecordRaw[]>(endRoute)

  const QueryLocalRoute = ref<RouteRecordRaw[]>([])

  const resetRouter = (route: RouteRecordRaw[]) => {
    try {
      if (route.length > 0) {
        routes.value = routes.value.concat(route)
        route.forEach((rout) => {
          router.addRoute(rout)
        })
      } else {
        routes.value = constantRoutes
      }
    } catch (e) {
      console.log(e)
      window.location.reload()
    }
  }

  return { endRoutes, resetRouter, QueryLocalRoute, routes }
})

/** 在 setup 外使用 */
export function usePermissionStoreHook() {
  return usePermissionStore(store)
}
