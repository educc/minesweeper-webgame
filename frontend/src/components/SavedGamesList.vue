<template>
  <div id='rootSavedGamesList'>
    <p><router-link to="/">Go back</router-link></p>
    <p>List of saved games, click anyone to continue the game:</p>
    <div v-if='gameList.length > 0'>
      <ul>
        <li v-for="item in gameList" :key='item'>
          
          <router-link :to="{ name: 'game', params: {id: item}}">Game ID = {{ item }}</router-link>
        </li>
      </ul>
    </div>
    <div v-else>
      No games saved.
    </div>
  </div>
</template>

<script>

import {loadGamesSaved} from '../services/GameService'

export default {
  name: 'SavedGamesList', 
  data() { 
    return {
      gameList: []
    } 
  },
  created() {
    var aux = loadGamesSaved();
    if(aux && typeof(aux) === typeof([])) {
      this.gameList = aux;
    }
  }
}
</script>

<style scoped>
ul li {
  padding: 5px 5px;
  font-size: 18px;
}
</style>
