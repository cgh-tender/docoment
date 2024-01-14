import { request } from "@/utils/service"
import type * as Login from "./types/login"
import { getUuid } from "@/utils/cache/cookies";

/** 获取登录验证码 */
export function getLoginCodeApi(url: string) {
  return request<Login.LoginCodeResponseData>({
    url: url + "?crt_"+ new Date().getTime(),
    method: "get",
    responseType: 'blob'
  })
}

/** 登录并返回 Token */
export function loginApi(data: Login.LoginRequestData) {
  return request<Login.LoginResponseData>({
    url: "auth/login",
    method: "post",
    headers:{
      uuid: getUuid(),
      "Content-Type": "application/x-www-form-urlencoded"
    },
    data
  })
}

/** 获取用户详情 */
export function getUserInfoApi() {
  return request<Login.UserInfoResponseData>({
    url: "login/info",
    method: "get"
  })
}

export function getRouterApi() {
  return request<Login.MenuInfoResponseData>({
    url: "login/getMenu",
    method: "get"
  })
}
