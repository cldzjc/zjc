<template>
  <el-card class="post" shadow="hover">
    <!-- 卡片头部：标题 + 操作按钮 -->
    <template #header>
      <div class="header">
        <span class="title">{{ post.title }}</span>

        <!-- 只有作者本人显示操作按钮 -->
        <el-dropdown v-if="isOwner" trigger="click">
          <el-icon style="cursor: pointer;"><MoreFilled /></el-icon>
          <template #dropdown>
            <el-dropdown-item @click="$emit('edit', post)">编辑</el-dropdown-item>
            <el-dropdown-item @click="$emit('del', post)">删除</el-dropdown-item>
          </template>
        </el-dropdown>
      </div>
    </template>

    <!-- 内容：保留换行和空格 -->
    <div class="content" v-html="renderContent"></div>

    <!-- 元信息 -->
    <small class="meta">
      作者：{{ post.author }} · {{ formatTime(post.createTime) }}
    </small>
  </el-card>
</template>

<script setup>
import { computed } from 'vue'
import { useUserStore } from '@/stores/user.js'
import { MoreFilled } from '@element-plus/icons-vue'

const props = defineProps({ post: Object })
const emit = defineEmits(['edit', 'del'])

const userStore = useUserStore()

// 判断是否是作者本人
const isOwner = computed(() => userStore.userInfo?.username === props.post.author)

// 格式化时间
function formatTime(t) {
  return new Date(t).toLocaleString()
}

// 把回车和空格转成 HTML
const renderContent = props.post.content
  ?.replace(/\n/g, '<br>')
  .replace(/ {2}/g, '&nbsp;&nbsp;') || ''
</script>

<style scoped>
.post { margin-bottom: 16px; }
.header { display: flex; justify-content: space-between; align-items: center; }
.title { font-weight: bold; }
.meta { color: #999; display: block; margin-top: 8px; }
.content { line-height: 1.6; white-space: pre-wrap; }
</style>
