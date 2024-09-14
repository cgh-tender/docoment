/** 所有 api 接口的响应数据都应该准守该格式 */
interface ApiResponseData<T> {
  // 基础数据
  code: number
  data: T
  message?: string
  type?: string
}

interface TableResponseData<T> extends ApiResponseData<T> {
  // 分页
  total: number
  records: T[]
}
