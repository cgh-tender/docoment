import { PaginationData } from "@/hooks/usePagination"

export interface ResourceQueryPojo extends PaginationData {
  // 菜单id
  id: string
  // 菜单名称
  name: string
  // 菜单资源类别
  status: string
}
