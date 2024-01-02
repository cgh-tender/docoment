import router from "@/router"
import { useUserStore, useUserStoreHook } from "@/store/modules/user"
import { ElMessage } from "element-plus"
import { setRouteChange } from "@/hooks/useRouteListener"
import { useTitle } from "@/hooks/useTitle"
import { getToken } from "@/utils/cache/cookies"
import { fixBlankPage } from "@/utils/fix-blank-page"
import routeSettings from "@/config/route"
import isWhiteList from "@/config/white-list"
import NProgress from "nprogress"
import "nprogress/nprogress.css"

const { setTitle } = useTitle()
NProgress.configure({ showSpinner: false })

router.beforeEach(async (to, _from, next) => {
  console.log("beforeEach")
  fixBlankPage()
  NProgress.start()
  const userStore = useUserStoreHook()
  const token = getToken()

  // 判断该用户是否已经登录
  if (!token) {
    // 如果在免登录的白名单中，则直接进入
    if (isWhiteList(to)) {
      next()
    } else {
      // 其他没有访问权限的页面将被重定向到登录页面
      NProgress.done()
      next("/login")
    }
    return
  }

  // 如果已经登录，并准备进入 Login 页面，则重定向到主页
  if (to.path === "/login") {
    NProgress.done()
    return next({ path: "/" })
  }

  // 如果用户已经获得其权限角色
  if (userStore.roles.length !== 0) return next()
  // 否则要重新获取权限角色
  try {
    if (routeSettings.async) {
      console.log("query userInfo and menuRouter")
      // 注意：角色必须是一个数组！ 例如: ['admin'] 或 ['developer', 'editor']
      await userStore.getInfo()
      await useUserStore()
        .flushRoute()
        .catch((e) => {
          console.log(e)
          ElMessage.error(e)
        })
    } else {
      // 没有开启动态路由功能，则启用默认角色
      userStore.setRoles(routeSettings.defaultRoles)
    }
    // 将'有访问权限的动态路由' 添加到 Router 中
    // 确保添加路由已完成
    // 设置 replace: true, 因此导航将不会留下历史记录
    next({ ...to, replace: true })
  } catch (err: any) {
    // 过程中发生任何错误，都直接重置 Token，并重定向到登录页面
    userStore.resetToken()
    ElMessage.error(err.message || "路由守卫过程发生错误")
    NProgress.done()
    next("/login")
  }
})

router.afterEach((to) => {
  console.log("router.afterEach")
  setRouteChange(to)
  setTitle(to.meta.title)
  NProgress.done()
})
