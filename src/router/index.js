import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Home from '@/views/Home.vue'
import PostForm from '../views/PostForm.vue'
import { useUserStore } from '@/stores/user.js'
const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/login', name: 'Login', component: Login },
  { path: '/register', name: 'Register', component: Register },
  { path: '/post/new', name: 'PostForm', component: PostForm, meta: { requireAuth: true } },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 简单路由守卫
router.beforeEach((to, from, next) => {
  const userStore = useUserStore() // 下面建
  if (to.meta.requireAuth && !userStore.token) next('/login')
  else next()
})

export default router