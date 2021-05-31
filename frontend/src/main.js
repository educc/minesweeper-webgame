import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App.vue'
import axios from 'axios'
import Game from './components/Game.vue'
import Home from './components/Home.vue'
import SavedGamesList from './components/SavedGamesList.vue'
import CreateGame from './components/CreateGame.vue'

Vue.use(VueRouter)
Vue.config.productionTip = false
Vue.prototype.$http = axios;


const routes = [
    { path: '/', name: 'home', component: Home },
    { path: '/create-game', component: CreateGame },
    { path: '/game/:id', name: 'game', component: Game },
    { path: '/saved-games', component: SavedGamesList } 
  ]
   
const router = new VueRouter({
    routes
})

new Vue({
  render: h => h(App),
  router
}).$mount('#app')
