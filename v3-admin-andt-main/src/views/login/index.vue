<script lang="ts" setup>
import { reactive, ref, watchEffect } from "vue"
import { useRouter } from "vue-router"
import { useUserStore } from "@/store/modules/user"
import { getLoginCodeApi } from "@/api/login"
import { type LoginRequestData } from "@/api/login/types/login"
import ThemeSwitch from "@/components/ThemeSwitch/index.vue"
import { message } from "ant-design-vue"
import { BorderRightOutlined } from "@ant-design/icons-vue"
import Schema from "async-validator"

const router = useRouter()
/** 登录表单元素的引用 */
const loginFormRef = ref<any>()

/** 登录按钮 Loading */
const loading = ref(false)
/** 验证码图片 URL */
const codeBaseUri = ref(import.meta.env.VITE_BASE_CODE_API)
const src = ref("")
/** 登录表单数据 */
const loginFormData: LoginRequestData = reactive({
  username: "admin",
  password: "12345678",
  code: "",
  rememberMe: false
})
/** 登录表单校验规则 */
const loginFormRules = new Schema({
  username: [
    {
      required: true,
      message: "请输入用户名",
      trigger: "blur"
    }
  ],
  password: [
    {
      required: true,
      message: "请输入密码"
    }
  ],
  code: [
    {
      required: true,
      message: "请输入验证码"
    }
  ]
})
/** 登录逻辑 */
const handleLogin = () => {
  loginFormRules
    .validate(loginFormData)
    .then((p) => {
      useUserStore()
        .login(loginFormData)
        .then(() => {
          router.push({ path: "/" })
        })
        .catch(() => {
          codeBaseUri.value = import.meta.env.VITE_BASE_CODE_API + "?crt_=" + new Date().getTime()
        })
        .finally(() => {
          loading.value = false
        })
    })
    .catch((e) => {
      message.error(e?.error[0].message)
    })
}
const flushCode = () => {
  codeBaseUri.value = import.meta.env.VITE_BASE_CODE_API + "?crt_=" + new Date().getTime()
}
const usernameValiDateFileds = (filed, options, callback) => {
  console.log(options)
  return callback("223")
}
watchEffect(() => {
  flushCode()
  loginFormData.code = ""
  // 获取验证码
  getLoginCodeApi(codeBaseUri.value).then((response) => {
    if (response.type) {
      const blob = new Blob([response], { type: "image/jpg" })
      src.value = URL.createObjectURL(blob)
    } else {
      message.error(response.message)
    }
  })
})
</script>

<template>
  <div class="login-container">
    <ThemeSwitch class="theme-switch" />
    <div class="login-card">
      <div class="title">
        <img src="@/assets/layouts/logo-text-2.png" />
      </div>
      <div class="content">
        <a-form ref="loginFormRef" :model="loginFormData" :rules="loginFormRules" @keyup.enter="handleLogin">
          <a-form-item name="username" class="custom-form-item">
            <a-input
              ellipsis="true"
              v-model:value.trim="loginFormData.username"
              placeholder="用户名"
              type="text"
              tabindex="1"
              :prefix-icon="BorderRightOutlined"
              size="large"
            />
          </a-form-item>
          <a-form-item name="password" class="custom-form-item">
            <a-input-password
              v-model:value.trim="loginFormData.password"
              placeholder="密码"
              tabindex="2"
              :prefix-icon="BorderRightOutlined"
              size="large"
            />
          </a-form-item>
          <a-form-item name="code" class="custom-form-item">
            <a-input
              v-model:value.trim="loginFormData.code"
              placeholder="验证码"
              type="text"
              tabindex="3"
              :prefix-icon="BorderRightOutlined"
              :maxlength="7"
              size="large"
            >
              <template #addonAfter>
                <img :src="src" @click="flushCode" draggable="false" width="90px" />
              </template>
            </a-input>
          </a-form-item>
          <a-form-item name="rememberMe" v-if="false" class="custom-form-item">
            <span>记住我</span>
            <a-checkbox
              v-model.trim="loginFormData.rememberMe"
              type="checkbox"
              tabindex="2"
              :prefix-icon="BorderRightOutlined"
              size="large"
            />
          </a-form-item>
          <a-button :loading="loading" type="primary" size="large" @click.prevent="handleLogin">登 录</a-button>
        </a-form>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  min-height: 100%;

  .theme-switch {
    position: fixed;
    top: 5%;
    right: 5%;
    cursor: pointer;
  }

  .login-card {
    width: 480px;
    border-radius: 20px;
    box-shadow: 0 0 10px #dcdfe6;
    background-color: #fff;
    overflow: hidden;

    .title {
      display: flex;
      justify-content: center;
      align-items: center;
      height: 150px;

      img {
        height: 100%;
      }
    }

    .content {
      padding: 20px 50px 50px 50px;

      .custom-form-item {
        width: 100%;
        height: 40px;
        margin: 0 0 20px 0;
        user-select: none;
        cursor: pointer;
        text-align: initial;
        display: block;
      }

      .ant-btn {
        float: right;
        margin-right: 26px;
      }
    }
  }
}
</style>
