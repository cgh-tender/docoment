import { PaginationData } from "@/hooks/usePagination"

/**
 * 角色
 */
export interface Role {
  id: string | number
  name?: string
  description?: string
  parentId?: bigint
  children?: Role[]
}

/**
 * 组织
 */
export interface Organization {
  id: string | number
  name?: string
  description?: string
  parentId?: bigint
  children?: Organization[]
}

/**
 * 用户组
 */
export interface Group {
  id: string | number
  name?: string
}

/**
 * 职位
 */
export interface Position {
  id: string | number
  name?: string
  description?: string
  parentId?: bigint
  children?: Position[]
}

/**
 * 用户管理列表数据
 */
export interface DefaultUserTableData {
  id: string | number
  gender: string
  createTime?: string
  password?: string
  email: string
  phone: string
  status?: string
  username?: string
  realname: string
  roles: Role[]
  organizations: Organization[]
  groups: Group[]
  positions: Position[]
}

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
export interface GetBaseUserTableData extends PaginationData {
  /** 查询参数：用户名 */
  username?: string
  /** 查询参数：手机号 */
  phone?: string
}

/**
 * 认证密码是否符合规则
 */
export interface checkPasswordRule {
  password: string
}
