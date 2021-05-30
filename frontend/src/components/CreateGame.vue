<template>
  <div id='rootCreateGame'>
      <button v-on:click='createGame()'>Create game</button>
  </div>
</template>

<script>

import {saveGame} from '../services/GameService'

export default {
  name: 'CreateGame', 
  data() { return {} },
  methods: {
    createGame(){
      this.$http.post("/api/game").then(res => {
        var gameId = res.data.gameId;
        saveGame(gameId);
        
        this.$router.push({ name: 'game', params: { id: gameId } })
      })
    }
  }
}
</script>

<style scoped>

</style>
