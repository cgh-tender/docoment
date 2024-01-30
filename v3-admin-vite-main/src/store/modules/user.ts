import { ref } from "vue"
import store from "@/store"
import { defineStore } from "pinia"
import { usePermissionStoreHook } from "./permission"
import { useTagsViewStore } from "./tags-view"
import { useSettingsStore } from "./settings"
import { getToken, removeToken, setToken } from "@/utils/cache/cookies"
import router, { cleanRouter, Layouts } from "@/router"
import { getRouterApi, getUserInfoApi, loginApi, logOutApi } from "@/api/login"
import { type LoginRequestData } from "@/api/login/types/login"
import { type RouteRecordRaw } from "vue-router"
import routeSettings from "@/config/route"
import { flatMultiLevelRoutes } from "@/router/helper"
import { ElMessage } from "element-plus"
import { checkPermission } from "@/utils/permission"

const modules = import.meta.glob(`/src/views/**/*.vue`)
const isFlushRouter = ref<boolean>(false)
export const useUserStore = defineStore("user", () => {
  const token = ref<string>(getToken() || "")
  const roles = ref<string[]>([])
  const username = ref<string>("")

  const permissionStore = usePermissionStoreHook()
  const tagsViewStore = useTagsViewStore()
  const settingsStore = useSettingsStore()

  /** 设置角色数组 */
  const setRoles = (value: string[]) => {
    roles.value = value
  }
  /** 登录 */
  const login = async ({ username, password, code, rememberMe }: LoginRequestData) => {
    const { data } = await loginApi({ username, password, code, rememberMe })
    setToken(data.access_token)
    token.value = data.access_token
  }

  const _flushRouter = (routes: RouteRecordRaw[]): RouteRecordRaw[] => {
    const res: RouteRecordRaw[] = []
    if (!routes || routes.length == 0) return res
    routes.forEach((route) => {
      const tmp = { ...route } as any
      if (tmp.component == "Layouts") {
        tmp.component = Layouts
      } else {
        const component = modules[`/src/views/${tmp.component}.vue`]
        if (component) {
          tmp.component = component
        } else {
          tmp.component = modules[`/src/views/error-page/404.vue`]
        }
      }
      if (tmp.meta.roles?.length >= 0) {
        if (checkPermission(tmp.meta.roles)) {
          if (tmp.children?.length > 0) {
            tmp.children = _flushRouter(tmp.children)
            if (tmp.children?.length > 0) {
              res.push(tmp)
            }
          } else {
            res.push(tmp)
          }
        }
      } else {
        res.push(tmp)
      }
    })
    return res
  }

  const flushRoute = async () => {
    if (isFlushRouter.value) return
    isFlushRouter.value = true
    try {
      cleanRouter()
      const { data } = await getRouterApi()
      const rest: RouteRecordRaw[] = routeSettings.thirdLevelRouteCache
        ? flatMultiLevelRoutes(_flushRouter(data))
        : _flushRouter(data)
      permissionStore.QueryLocalRoute = rest
      permissionStore.resetRouter(rest)
      permissionStore.endRoutes.forEach((r) => router.addRoute(r))
      console.log("flushRoute", router.getRoutes())
    } finally {
      isFlushRouter.value = false
    }
  }

  /** 获取用户详情 */
  const getInfo = async () => {
    const { data } = await getUserInfoApi()
    username.value = data.username
    // 验证返回的 roles 是否为一个非空数组，否则塞入一个没有任何作用的默认角色，防止路由守卫逻辑进入无限循环
    roles.value = data.roles?.length > 0 ? data.roles : routeSettings.defaultRoles
  }

  /** 切换角色 */
  const changeRoles = async (role: string) => {
    const newToken = "token-" + role
    token.value = newToken
    setToken(newToken)
    await getInfo()
    await useUserStore()
      .flushRoute()
      .catch((e) => {
        console.log(e)
        ElMessage.error(e)
      })
    _resetTagsView()
    /** 跳转到首页*/
    await router.push("/")
  }
  /** 登出 */
  const logout = async (): Promise<string> => {
    const { message } = await logOutApi()
    removeToken()
    token.value = ""
    roles.value = []
    cleanRouter()
    _resetTagsView()
    return message
  }
  /** 重置 Token */
  const resetToken = () => {
    removeToken()
    token.value = ""
    roles.value = []
  }
  /** 重置 Visited Views 和 Cached Views */
  const _resetTagsView = () => {
    if (!settingsStore.cacheTagsView) {
      tagsViewStore.delAllVisitedViews()
      tagsViewStore.delAllCachedViews()
    }
  }

  return { token, roles, username, setRoles, login, getInfo, changeRoles, logout, resetToken, flushRoute }
})

/** 在 setup 外使用 */
export function useUserStoreHook() {
  return useUserStore(store)
}
