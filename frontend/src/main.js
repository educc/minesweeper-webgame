import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App.vue'
import axios from 'axios'
import Game from './components/Game.vue'
import Home from './components/Home.vue'

Vue.use(VueRouter)
Vue.config.productionTip = false
Vue.prototype.$http = axios;


const routes = [
    { path: '/', component: Home },
    { path: '/create-game', component: Game } 
  ]
   
const router = new VueRouter({
    routes
})

new Vue({
  render: h => h(App),
  router
}).$mount('#app')
