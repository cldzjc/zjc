const BASE_URL = 'http://localhost:8080'

export async function request(url, method = 'GET', body = null) {
  const headers = { 'Content-Type': 'application/json' }

  // ① 从 localStorage 拿 token（确保登录后已存）
  const token = localStorage.getItem('token')
  if (token) {
    headers['Authorization'] = 'Bearer ' + token
  }

  try {
    const res = await fetch(BASE_URL + url, {
      method,
      headers,
      body: body ? JSON.stringify(body) : undefined
    })

    // ② 如果响应码不是 2xx，抛出错误
    if (!res.ok) {
      const text = await res.text()
      throw new Error(`请求失败: ${res.status} - ${text}`)
    }

    // ③ 返回 JSON 结果
    return await res.json()
  } catch (err) {
    console.error('❌ request 错误:', err)
    throw err
  }
}
