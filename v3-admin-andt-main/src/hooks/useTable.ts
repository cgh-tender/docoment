import { defaultPaginationData, usePagination } from "@/hooks/usePagination"
import { ref } from "vue"
import { has } from "xe-utils"
import { defaults, get } from "lodash-es"

export function useTable<R extends TableResponseData<any>>(
  api: (params: any) => Promise<R>,
  params?: object | (() => object),
  options?: {
    path?: { records?: string; total?: string; currentPage?: string; pageSize?: string }
    immediate?: boolean
  }
) {
  defaults(options, {
    path: { records: "records", total: "total", currentPage: "currentPage", pageSize: "pageSize" },
    immediate: false
  })

  // 使用()=>fn()而不是fn()区别在于后者只是一个值且立即执行
  const pagination = usePagination(
    {
      ...defaultPaginationData,
      pageSize: 2
    },
    (extraData?: object) => (extraData ? refresh(extraData) : refresh())
  )

  const loading = ref(false)
  const data = ref([])

  function refresh(extraData?: object | (() => object)) {
    const requestData = {
      [options?.path?.currentPage]: pagination.currentPage.value,
      [options?.path?.pageSize]: pagination.pageSize.value
    }
    if (extraData) {
      if (typeof extraData === "function") {
        Object.assign(requestData, extraData())
      } else {
        Object.assign(requestData, extraData)
      }
    }
    if (params) {
      if (typeof params === "function") {
        Object.assign(requestData, params())
      } else {
        Object.assign(requestData, params)
      }
    }
    console.log(requestData)
    loading.value = true
    return api(requestData)
      .then((res) => {
        data.value = get(res, options!.path?.records || "records", [])
        pagination.total = get(res, options!.path?.total || "total", 0)
        // 友好提示
        if (!has(res, options!.path?.records || "records") || !has(res, options!.path?.total || "total")) {
          console.warn("useTable：响应数据缺少所需字段")
        }
      })
      .finally(() => {
        loading.value = false
      })
  }

  options!.immediate && refresh()

  return [data, refresh, pagination, loading]
}
