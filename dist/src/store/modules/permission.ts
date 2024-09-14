import { ref } from "vue"
import store from "@/store"
import { defineStore } from "pinia"
import { type RouteRecordRaw } from "vue-router"
import router, { asyncRoutes as asyncRoute, constantRoutes, endRoutes as endRoute } from "@/router"

export const usePermissionStore = defineStore("permission", () => {
  // 汇总
  const routes = ref<RouteRecordRaw[]>([])
  // 开头
  const constantRoute = ref<RouteRecordRaw[]>(constantRoutes)
  // 结束
  const endRoutes = ref<RouteRecordRaw[]>(endRoute)
  // 异步
  const asyncRoutes = ref<RouteRecordRaw[]>(asyncRoute)
  // 本地查询
  const queryLocalRoute = ref<RouteRecordRaw[]>([])

  const resetRouter = () => {
    if (constantRoute.value.length > 0) {
      routes.value = routes.value.concat(constantRoute.value)
      constantRoute.value.forEach((rout) => {
        router.addRoute(rout)
      })
    }
    try {
      if (queryLocalRoute.value.length > 0) {
        routes.value = routes.value.concat(queryLocalRoute.value)
        queryLocalRoute.value.forEach((rout) => {
          router.addRoute(rout)
        })
      }
    } catch (e) {
      console.log(e)
      // window.location.reload()
    }
    if (endRoutes.value.length > 0) {
      routes.value = routes.value.concat(endRoutes.value)
      endRoutes.value.forEach((rout) => {
        router.addRoute(rout)
      })
    }
  }
  return { endRoutes, resetRouter, queryLocalRoute, routes, asyncRoutes, constantRoute }
})

/** 在 setup 外使用 */
export function usePermissionStoreHook() {
  return usePermissionStore(store)
}
