<template>
  <el-card style="max-width: 600px; margin: 20px auto;">
    <h2>发新贴</h2>
    <el-form @submit.prevent="submit">
      <el-form-item label="标题">
        <el-input v-model="form.title" required />
      </el-form-item>
      <el-form-item label="内容">
        <el-input v-model="form.content" type="textarea" :rows="6" required />
      </el-form-item>
      <el-button type="primary" native-type="submit">发布</el-button>
    </el-form>
  </el-card>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { request } from '@/api/request.js'

const form = reactive({ title: '', content: '' })
const router = useRouter()

async function submit() {
  try {
    const res = await request('/posts', 'POST', form)
    if (res.code === 200) {
      ElMessage.success('发布成功')
      router.push('/')
    } else {
      ElMessage.error(res.msg || '发布失败')
    }
  } catch (e) {
    ElMessage.error('网络错误')
  }
}
</script>