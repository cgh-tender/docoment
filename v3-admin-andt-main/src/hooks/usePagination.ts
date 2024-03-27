import { reactive } from "vue";

export interface DefaultPaginationData extends PaginationData {
  total: number;
  currentPage: number;
  pageSizes: number[];
  pageSize: number;
  layout: string;
}

export interface PaginationData {
  total?: number;
  currentPage?: number;
  pageSizes?: number[];
  pageSize?: number;
  layout?: string;
}

/** 默认的分页参数 */
const defaultPaginationData: DefaultPaginationData = {
  total: 0,
  currentPage: 1,
  pageSizes: [10, 20, 50],
  pageSize: 10,
  layout: "total, sizes, prev, pager, next, jumper"
};

export function usePagination(initialPaginationData: PaginationData = {}) {
  /** 合并分页参数 */
  const paginationData = reactive({ ...defaultPaginationData, ...initialPaginationData });
  if (
    initialPaginationData &&
    initialPaginationData.pageSize &&
    !initialPaginationData.pageSizes &&
    initialPaginationData.pageSize < defaultPaginationData.pageSizes[0]
  ) {
    paginationData.pageSizes.unshift(initialPaginationData.pageSize);
  }
  /** 改变当前页码 */
  const handleCurrentChange = (value: number) => {
    paginationData.currentPage = value;
  };
  /** 改变页面大小 */
  const handleSizeChange = (value: number) => {
    paginationData.pageSize = value;
  };

  return { paginationData, handleCurrentChange, handleSizeChange };
}
