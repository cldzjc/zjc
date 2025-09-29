<template>
  <el-card class="box">
    <h2>用户注册</h2>
    <el-form @submit.prevent="handleRegister">
      <el-form-item label="用户名">
        <el-input v-model="form.username" required />
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" type="email" required />
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.password" type="password" required />
      </el-form-item>
      <el-button type="primary" native-type="submit">注册</el-button>
      <el-link @click="$router.push('/login')">去登录</el-link>
    </el-form>
  </el-card>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { request } from '@/api/request.js'

const form = reactive({ username: '', email: '', password: '' })
const router = useRouter()

async function handleRegister() {
  try {
    const res = await request('/auth/register', 'POST', form)
    if (res.code === 200) {
      ElMessage.success('注册成功')
      router.push('/login')
    } else {
      ElMessage.error(res.msg || '注册失败')
    }
  } catch (e) {
    ElMessage.error('网络错误')
  }
}
</script>

<style scoped>
.box { max-width: 400px; margin: 100px auto; }
</style>