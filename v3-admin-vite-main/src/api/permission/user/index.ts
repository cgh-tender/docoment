import { request } from "@/utils/service"
import { GetBaseUserTableData, checkPasswordRule, DefaultUserTableData } from "@/api/permission/user/types/base"

export function getUserTable(params: GetBaseUserTableData) {
  return request<TableResponseData<DefaultUserTableData>>({
    url: "resource/user",
    method: "get",
    params
  })
}

export function checkPassword(params: checkPasswordRule) {
  return request<ApiResponseData<string>>({
    url: `resource/user/checkPassword/${params.password}`,
    method: "get"
  })
}

export function resetquest(params: any) {
  return request<ApiResponseData<string>>({
    url: "resource/user/resetquest",
    method: "get",
    params
  })
}
