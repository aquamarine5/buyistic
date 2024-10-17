import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import VWave from 'v-wave'
import { createWebHistory, createRouter } from 'vue-router'
import MainPage from './mainpage/MainPage.vue'
import App from './App.vue'

const app = createApp(App)

const routes = [
    { path: '/', component: MainPage, name:"app" },
    { path: '/buy', component: ()=>import('@/buypage/BuyPage.vue') ,name:"buy"},
    { path: '/background', component: ()=>import('@/background/BackgroundPage.vue'),name:"background" }
]
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes:routes
})
app.use(router)
app.use(ElementPlus)
app.use(VWave)
app.mount('#app')

