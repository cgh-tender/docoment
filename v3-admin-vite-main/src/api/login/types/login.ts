import { RouteRecordRaw } from "vue-router"

export interface LoginRequestData {
  /** admin 或 editor */
  username: "admin" | "editor"
  /** 密码 */
  password: string
  /** 验证码 */
  code: string
  rememberMe: boolean
}

export type LoginCodeResponseData = ApiResponseData<{ data: string; url: string }>

export type LoginResponseData = ApiResponseData<{ token: string }>

export type UserInfoResponseData = ApiResponseData<{ username: string; roles: string[] }>

export type MenuInfoResponseData = ApiResponseData<RouteRecordRaw[]>
