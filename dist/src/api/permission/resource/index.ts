import { request } from "@/utils/service"
import { ResourceQueryPojo } from "@/api/permission/resource/types/base"

/**
 * 查询用户菜单列表
 * @param params
 */
export function getResourceTable(params: ResourceQueryPojo) {
  return request<TableResponseData<any>>({
    url: "resource/resource/queryResourceList",
    method: "get",
    params
  })
}

/**
 * queryResourceTree
 * @param params
 */
export function queryResourceTree(params: ResourceQueryPojo) {
  return request<TableResponseData<any>>({
    url: "resource/resource/queryResourceTree",
    method: "get",
    params
  })
}
