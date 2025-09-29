<template>
  <NavBar /> <!-- ← 必须渲染 -->
  <router-view />
</template>

<script setup>
import NavBar from '@/components/NavBar.vue'
</script>