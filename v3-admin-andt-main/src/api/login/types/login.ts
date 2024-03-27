import { RouteRecordRaw } from "vue-router"

export interface LoginRequestData {
  /** admin 或 editor */
  username: string
  /** 密码 */
  password: string
  /** 验证码 */
  code: string
  rememberMe: boolean
}

// export type LoginCodeResponseData = ApiResponseData<{ data: string; url: string; type: string }>

export type LoginResponseData = ApiResponseData<{
  access_token: string
}>

export interface LoginCodeResponseData extends Blob {
  message: string
}

export type UserInfoResponseData = ApiResponseData<{ username: string; roles: string[] }>

export type MenuInfoResponseData = ApiResponseData<RouteRecordRaw[]>
