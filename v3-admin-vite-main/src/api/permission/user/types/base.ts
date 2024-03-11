import { BasePage } from "@/api/table/types/baseTableTypes";
import { GetTableData } from "@/api/table/types/table";

export interface BaseUserTableData {
  id: bigint | undefined
  createTime: string
  email: string
  phone: string
  roles: string
  status: string
  username: string
  password: string
}

export interface GetBaseUserTableData extends BasePage{
  /** 查询参数：用户名 */
  username?: string
  /** 查询参数：手机号 */
  phone?: string
}

export type GetBaseUserTableResponseData = ApiResponseData<{
  records: BaseUserTableData[]
  total: number
}>