<<<<<<< Updated upstream
import { PaginationData } from "@/hooks/usePagination";
=======
import { BasePage } from "@/api/table/types/baseTableTypes"
>>>>>>> Stashed changes

/**
 * 用户管理列表数据
 */
export interface DefaultUserTableData {
  id: bigint | undefined
  createTime: string
  email: string
  phone: string
  roles: string
  status: string
  username: string
  password: string
}

<<<<<<< Updated upstream
/**
 * 更改密码实体
 */
export interface updatePasswordData {
  password: string
  onePassword: string
  twoPassword: string
}
/**
 * 请求用户管理列表数据入参
 */
export interface GetBaseUserTableData extends PaginationData{
=======
export interface GetBaseUserTableData extends BasePage {
>>>>>>> Stashed changes
  /** 查询参数：用户名 */
  username?: string
  /** 查询参数：手机号 */
  phone?: string
}
<<<<<<< Updated upstream
/**
 * 认证密码是否符合规则
 */
export interface checkPasswordRule{
  password: string
}
=======

export type GetBaseUserTableResponseData = ApiResponseData<{
  records: BaseUserTableData[]
  total: number
}>
>>>>>>> Stashed changes
