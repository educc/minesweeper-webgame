<template>
  <div id='rootCreateGame'>
      <div>
          <label for="txtRows">Rows: </label>
          <input type="number" name='txtRows' v-model='rows' required pattern="^\d{1,2}$" />
      </div>
      <div>
          <label for="txtCols">Cols: </label>
          <input type="number" name='txtCols' v-model='cols' required pattern="^\d{1,2}$" />
      </div>
      <div>
          <label for="txtBombs">Bombs: </label>
          <input type="number" name='txtBombs' v-model='bombs' required pattern="^\d{1,2}$" />
      </div>
      <div>
        <button v-on:click='createGame()'>Create game</button>
      </div>
  </div>
</template>

<script>

import {saveGame} from '../services/GameService'

export default {
  name: 'CreateGame', 
  data() { return {
      rows: 8,
      cols: 8,
      bombs: 10
  } },
  methods: {
    buildFormObject() {
        var obj = {
            rows: parseInt(this.rows),
            cols: parseInt(this.cols),
            bombs: parseInt(this.bombs)
        }
        var invalid = ['rows', 'cols', 'bombs'].map(it => obj[it]).some(it => isNaN(it))
        if (!invalid) return obj;
        return false;
    },
    createGame(){
      var obj = this.buildFormObject();
      if (!obj) {
          alert("Invalid input");
          return;
      }
      this.$http.post("/api/game", obj).then(res => {
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
