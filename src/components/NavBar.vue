<template>
  <el-menu mode="horizontal" router>
    <el-menu-item index="/">首页</el-menu-item>
    <el-menu-item v-if="!user.token" index="/login">登录</el-menu-item>
    <el-menu-item v-if="!user.token" index="/register">注册</el-menu-item>
    <el-menu-item v-if="user.token" index="/post/new">发贴</el-menu-item>
    <el-sub-menu v-if="user.token" index="user">
      <template #title>{{ user.userInfo?.username }}</template>
      <el-menu-item @click="user.logout">退出</el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<script setup>
import { useUserStore } from '@/stores/user.js'
const user = useUserStore()
</script>