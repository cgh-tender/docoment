import {onMounted, ref} from "vue"

/** Select 需要的数据格式 */
export interface SelectOption {
  value: string | number
  label: string
  disabled?: boolean
  children?: SelectOption[] | undefined
}

export interface SelectNode extends ApiResponseData<SelectOption> {}

/** 入参格式，暂时只需要传递 api 函数即可 */
interface FetchSelectProps {
  api: () => Promise<ApiResponseData<SelectOption[]>>
  noInitQuery?: boolean
}

export function useFetchSelect(props: FetchSelectProps) {
  const { api } = props

  const loading = ref<boolean>(false)
  const options = ref<SelectOption[]>([])
  const value = ref<string[] | number[]>([])

  /** 调用接口获取数据 */
  const loadData = () => {
    loading.value = true
    options.value = []
    api()
      .then((res) => {
        options.value = res.data
      })
      .finally(() => {
        loading.value = false
      })
  }

  onMounted(() => {
    if (props.noInitQuery) {
      props.noInitQuery = false
    } else {
      loadData()
    }
  })

  return {
    loading,
    options,
    value
  }
}
