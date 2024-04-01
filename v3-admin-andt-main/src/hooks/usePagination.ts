import { reactive, toRefs } from "vue"

export interface PaginationData {
  total: number
  currentPage: number
  pageSizes: number[]
  pageSize: number
  layout: string
}

/** 默认的分页参数 */
export const defaultPaginationData: PaginationData = {
  total: 0,
  currentPage: 1,
  pageSizes: [10, 20, 50],
  pageSize: 10,
  layout: "total, sizes, prev, pager, next, jumper"
}

export function usePagination(initialPaginationData?: PaginationData, cb?: any) {
  /** 合并分页参数 */
  const pagination = reactive<{
    total: number
    currentPage: number
    pageSizes: number[]
    pageSize: number
    layout: string
    reset: () => void
    handleCurrentChange: (value: number, extraData?: object) => void
    handleSizeChange: (value: number, extraData?: object) => void
  }>({
    ...defaultPaginationData,
    ...initialPaginationData,
    /** 改变当前页码 */
    handleCurrentChange: (value: number, extraData?: object) => {
      pagination.currentPage = value
      return cb && extraData ? cb(extraData) : cb()
    },
    /** 改变页面大小 */
    handleSizeChange: (value: number, extraData?: object) => {
      pagination.pageSize = value
      return cb && extraData ? cb(extraData) : cb()
    },
    reset() {
      pagination.currentPage = 1
      pagination.total = 0
      pagination.pageSize = pagination.pageSizes[0]
    }
  })
  if (
    initialPaginationData &&
    initialPaginationData.pageSize &&
    !initialPaginationData.pageSizes &&
    initialPaginationData.pageSize < pagination.pageSizes[0]
  ) {
    pagination.pageSizes.unshift(initialPaginationData.pageSize)
  }
  return toRefs(pagination)
}
