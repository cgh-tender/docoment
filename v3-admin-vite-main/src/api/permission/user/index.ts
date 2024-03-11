import { request } from "@/utils/service";
import {
  GetBaseUserTableData,
  GetBaseUserTableResponseData
} from "@/api/permission/user/types/base";

export function getUserTable(params: GetBaseUserTableData) {
  return request<GetBaseUserTableResponseData>({
    url: "resource/user",
    method: "get",
    params
  })
}