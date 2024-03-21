import { PaginationData } from "@/hooks/usePagination"

export interface ResourceQueryPojo {
  // 菜单id
  id: string
  // 菜单名称
  name: string
  // 菜单资源类别
  status: string
}

/**
 * 请求菜单管理列表数据入参
 */
export interface GetBaseResourceTableData extends PaginationData {
  /** 查询参数：用户名 */
  username?: string
  /** 查询参数：手机号 */
  phone?: string
}
