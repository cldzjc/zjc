<template>
  <div style="padding: 20px;">
    <h2>最新帖子</h2>
    <div v-if="posts.length === 0">暂无帖子，快来发第一贴！</div>
    <PostCard v-for="p in posts" :key="p.id" :post="p" @edit="openEdit(p)" @del="delPost(p)" />
    <el-dialog v-model="showEdit" title="编辑帖子">
      <el-form @submit.prevent="updatePost">
        <el-form-item label="标题"><el-input v-model="editForm.title" /></el-form-item>
        <el-form-item label="内容"><el-input v-model="editForm.content" type="textarea" :rows="4" /></el-form-item>
        <el-button type="primary" native-type="submit">保存</el-button>
        <el-button @click="showEdit = false">取消</el-button>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { request } from '@/api/request.js'
import PostCard from '@/components/PostCard.vue'
import { ElMessage } from 'element-plus'

const posts = reactive([])
const showEdit = ref(false)
const editForm = reactive({ id: null, title: '', content: '' })

onMounted(loadPosts)

async function loadPosts() {
  const res = await request('/posts')
  if (res.code === 200) posts.splice(0, posts.length, ...res.data)
}

async function delPost(p) {
  if (!confirm('确定删除？')) return
  const res = await request(`/posts/${p.id}`, 'DELETE')
  if (res.code === 200) {
    ElMessage.success('已删除')
    loadPosts()
  } else {
    ElMessage.error(res.msg || '删除失败')
  }
}

function openEdit(p) {
  editForm.id = p.id
  editForm.title = p.title
  editForm.content = p.content
  showEdit.value = true
}

async function updatePost() {
  const res = await request(`/posts/${editForm.id}`, 'PUT', editForm)
  if (res.code === 200) {
    ElMessage.success('已更新')
    showEdit.value = false
    loadPosts()
  } else {
    ElMessage.error(res.msg || '更新失败')
  }
}
</script>