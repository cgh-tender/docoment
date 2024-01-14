/** 统一处理 Cookie */

import CacheKey from "@/constants/cache-key"
import Cookies from "js-cookie"

export const getToken = () => {
  return Cookies.get(CacheKey.TOKEN)
}
export const setToken = (token: string) => {
  Cookies.set(CacheKey.TOKEN, token)
}
export const removeToken = () => {
  Cookies.remove(CacheKey.TOKEN)
}

export const getUuid = () => {
  return Cookies.get(CacheKey.UUID)
}
export const setUuid = (uuid: string) => {
  Cookies.set(CacheKey.UUID, uuid)
}
export const removeUuid = () => {
  Cookies.remove(CacheKey.UUID)
}
