import {SelectOption} from "@/hooks/useFetchSelect"
import {reactive, ref} from "vue"

/** 入参格式，暂时只需要传递 api 函数即可 */
interface TreeSelectProps {
  <T extends (node: SelectOption, ...args: any[]) => Promise<ApiResponseData<SelectOption[]>>>(
    fn: T,
    node: SelectOption,
    resolve: (data: SelectOption[]) => void
  ): (...args: any[]) => Promise<SelectOption[]>
}

const useTreeSelect: TreeSelectProps = (
  fn,
  node: SelectOption,
  resolve: (data: SelectOption[]) => void
): ((config: Params) => Promise<SelectOption[]>) => {
  return async (config) => {
    if (node?.children) {
      resolve(node.children)
      return node.children
    } else {
      if (config.noInitQuery) {
        config.noInitQuery = false
        return []
      } else {
        return fn(node, config).then((data) => {
          node.children = data.data
          resolve ? resolve(data.data) : ""
          return data.data
        })
      }
    }
  }
}

interface Params {
  noInitQuery?: boolean
  api: (node: SelectOption) => Promise<ApiResponseData<any>>
}

interface treeSelectNode {
  node: SelectOption
  config: Params
  resolve: (data: SelectOption[]) => void
}

export function useTreeFunction(config: Params) {
  const { api } = config
  const treeSelectLoading = ref(false)
  const treeLoadData = ref<SelectOption[]>([])
  const treeModelNode = ref<any[]>([]) // 选择的节点
  const treeSelectNode = reactive<treeSelectNode>({
    config: { ...config },
    node: <SelectOption>{},
    resolve(data: SelectOption[]): void {}
  }) // 选择的节点
  const treeModeExitsInfo = ref(true)
  const treeFunction = () => {
    treeSelectLoading.value = true
    return useTreeSelect(
      api,
      treeSelectNode.node,
      treeSelectNode.resolve
    )(treeSelectNode.config)
      .then((data: SelectOption[]) => {
        if (!treeLoadData.value) {
          treeLoadData.value = data
        }
        // 默认数据展示
        if (treeModeExitsInfo.value) {
          treeModelNode.value.forEach((item, index) => {
            if (!(item instanceof String || item instanceof Number)) {
              data.forEach((i) => {
                if (i.value == item.value) {
                  treeModelNode.value[index] = i.value
                  treeModeExitsInfo.value = false
                }
              })
            }
          })
          treeModelNode.value.forEach((item, index) => {
            if (!(item instanceof String || item instanceof Number)) {
              treeModeExitsInfo.value = true
            }
          })
        }
      })
      .finally(() => {
        treeSelectLoading.value = false
      })
  }
  /**
   * 如果有初始化值，需要将值赋值给treeModelNode
   */
  const getTreeModelNode = () => {
    if (treeModeExitsInfo.value) {
      treeModelNode.value.forEach((item, index) => {
        if (!(item instanceof String || item instanceof Number)) {
          treeModelNode.value[index] = item.value
        }
      })
    }
    return treeModelNode.value
  }
  return {
    treeSelectLoading,
    treeFunction,
    treeModelNode,
    treeLoadData,
    treeSelectNode,
    getTreeModelNode
  }
}
