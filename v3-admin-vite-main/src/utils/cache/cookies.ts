/** 统一处理 Cookie */

import CacheKey from "@/constants/cache-key"
import Cookies from "js-cookie"

export const getToken = () => {
  if (import.meta.env.VITE_CACHE_TYPE == "cookie") {
    return Cookies.get(CacheKey.TOKEN)
  } else if (import.meta.env.VITE_CACHE_TYPE == "local") {
    return localStorage.get(CacheKey.TOKEN)
  } else {
    return sessionStorage.getItem(CacheKey.TOKEN)
  }
}
export const setToken = (token: string) => {
  if (import.meta.env.VITE_CACHE_TYPE == "cookie") {
    return Cookies.set(CacheKey.TOKEN, token)
  } else if (import.meta.env.VITE_CACHE_TYPE == "local") {
    return localStorage.setItem(CacheKey.TOKEN, token)
  } else {
    return sessionStorage.setItem(CacheKey.TOKEN, token)
  }
}
export const removeToken = () => {
  if (import.meta.env.VITE_CACHE_TYPE == "cookie") {
    return Cookies.remove(CacheKey.TOKEN)
  } else if (import.meta.env.VITE_CACHE_TYPE == "local") {
    return localStorage.removeItem(CacheKey.TOKEN)
  } else {
    return sessionStorage.removeItem(CacheKey.TOKEN)
  }
}

export const getUuid = () => {
  if (import.meta.env.VITE_CACHE_TYPE == "cookie") {
    return Cookies.get(CacheKey.UUID)
  } else if (import.meta.env.VITE_CACHE_TYPE == "local") {
    return localStorage.getItem(CacheKey.UUID)
  } else {
    return sessionStorage.getItem(CacheKey.UUID)
  }
}
export const setUuid = (uuid: string) => {
  if (import.meta.env.VITE_CACHE_TYPE == "cookie") {
    return Cookies.set(CacheKey.UUID, uuid)
  } else if (import.meta.env.VITE_CACHE_TYPE == "local") {
    return localStorage.setItem(CacheKey.UUID, uuid)
  } else {
    return sessionStorage.setItem(CacheKey.UUID, uuid)
  }
}
export const removeUuid = () => {
  if (import.meta.env.VITE_CACHE_TYPE == "cookie") {
    return Cookies.remove(CacheKey.UUID)
  } else if (import.meta.env.VITE_CACHE_TYPE == "local") {
    return localStorage.removeItem(CacheKey.UUID)
  } else {
    return sessionStorage.removeItem(CacheKey.UUID)
  }
}
