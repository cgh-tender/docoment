/** 声明 vite 环境变量的类型（如果未声明则默认是 any） */
declare interface ImportMetaEnv {
  readonly VITE_BASE_CODE_API: string
  readonly VITE_APP_TITLE: string /*项目标题*/
  readonly VITE_APP_TITLE_: string /*水印内容*/
  readonly VITE_BASE_API: string
  readonly VITE_BASE_TIMEOUT: bigint
  readonly VITE_ROUTER_HISTORY: "hash" | "html5"
  readonly VITE_PUBLIC_PATH: string
  readonly VITE_ERROR_MESSAGE_ONE: string
  readonly VITE_ADMIN_USER: string
}

interface ImportMeta {
  readonly env: ImportMetaEnv
}
