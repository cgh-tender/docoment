import { request } from "@/utils/service"
import { checkPasswordRule, DefaultUserTableData, GetBaseUserTableData } from "@/api/permission/user/types/base"
import { SelectOption } from "@/hooks/useFetchSelect"

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

export function addOrUpdateUser(data: DefaultUserTableData) {
  return request<ApiResponseData<string>>({
    url: "resource/user",
    method: "post",
    data
  })
}

function loadTree(url: string, node: SelectOption) {
  return request<ApiResponseData<SelectOption[]>>({
    url: url,
    method: "get",
    params: {
      parentId: node?.value
    }
  })
}

export function loadOrganization(node: SelectOption) {
  return loadTree("resource/organization/getTree", node)
}

export function loadPosition(node: SelectOption) {
  return loadTree("resource/position/getTree", node)
}

export function queryUserGroup() {
  return request<ApiResponseData<SelectOption[]>>({
    url: "resource/group",
    method: "get"
  })
}

export function loadRole(node: SelectOption) {
  return loadTree("resource/role/getTree", node)
}

export function deleteUserById(id: string | number) {
  return request<ApiResponseData<SelectOption[]>>({
    url: `resource/user/deleteUser/${id}`,
    method: "get",
    params: {
      id: id
    }
  })
}
