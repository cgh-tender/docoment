import { SelectOption } from "@/hooks/useFetchSelect"
import { reactive, ref } from "vue"

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
): ((...args: any[]) => Promise<SelectOption[]>) => {
  return async (...args) => {
    if (node?.children) {
      resolve(node.children)
      return node.children
    } else {
      return fn(node, args).then((data) => {
        node.children = data.data
        resolve ? resolve(data.data) : ""
        return data.data
      })
    }
  }
}

interface treeSelectNode {
  node: SelectOption
  args: []
  resolve: (data: SelectOption[]) => void
}

export function useTreeFunction(api: (node: SelectOption) => Promise<ApiResponseData<any>>) {
  const treeSelectLoading = ref(false)
  const treeLoadData = ref<SelectOption[]>([])
  const treeModelNode = ref<any[]>([]) // 选择的节点
  const treeSelectNode = reactive<treeSelectNode>({
    args: [],
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
    )(treeSelectNode.args)
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
