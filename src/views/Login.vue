<template>
  <el-card class="box">
    <h2>用户登录</h2>
    <el-form @submit.prevent="handleLogin">
      <el-form-item label="邮箱">
        <el-input v-model="form.email" type="email" required />
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.password" type="password" required />
      </el-form-item>
      <el-button type="primary" native-type="submit">登录</el-button>
    </el-form>
  </el-card>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user.js'
import { request } from '@/api/request.js'
import {jwtDecode} from 'jwt-decode'

const form = reactive({
  email: 'zjc@example.com',
  password: '121800'
})

const router = useRouter()
const userStore = useUserStore()

async function handleLogin() {
  try {
    const res = await request('/auth/login', 'POST', form)

    if (res.code === 200) {
      const token = res.data
      const payload = jwtDecode(token) // 解析 JWT
      payload.username = payload.sub

      // 存储到本地
      localStorage.setItem('token', token)
      localStorage.setItem('userInfo', JSON.stringify(payload))

      // 更新 Pinia
      userStore.setUserInfo(payload)
      userStore.setToken(token)

      console.log('登录后 Pinia userInfo:', userStore.userInfo) // 应该输出 zjc

      ElMessage.success('登录成功')
      router.push('/')
    } else {
      ElMessage.error(res.msg || '登录失败')
    }
  } catch (e) {
    console.error(e)
    ElMessage.error('网络错误，请稍后再试')
  }
}
</script>

<style scoped>
.box {
  max-width: 400px;
  margin: 100px auto;
}
</style>
