import { ResourceQueryPojo } from "@/api/permission/user/types/base"
import { request } from "@/utils/service"

export function getResourceTable(params: ResourceQueryPojo) {
  return request<TableResponseData<>>({
    url: "resource/resource",
    method: "get",
    params
  })
}
