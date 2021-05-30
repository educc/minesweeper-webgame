<template>
  <div id='rootGame'>
    
      <router-link to="/">Go home</router-link>
    <div class='gameboard' v-if='rows > 0 && cols > 0' :style="gameboardStyles" >
      <div class='gameboard-header'>
        <label>
          Make flag:
          <input type='checkbox' v-model='makeFlag'>
        </label>
      </div>

      <div v-for="row in rows" :key="row" class='gameRow'>
        <div v-for="col in cols" :key="col" class='gameCell' v-on:click="makeMove(row, col)">
          <div class='inner'  v-bind:class="renderClass(row-1,col-1)">
            <span>{{ renderText(row-1,col-1) }}</span>
          </div>
        </div>
      </div>

      <div class='gameboard-footer'> 
        <div v-if="state !== 'ON_PLAY'">
          <router-link to="/create-game">New Game</router-link>
        </div>
      </div>
    </div>
    <p>State = {{ state }}</p>
    <p>Time = {{ time }} seconds <i>(Refresh after each move)</i></p>
  </div>
</template>

<script>

import {removeGame} from '../services/GameService'

export default {
  name: 'Game',
  props: {
    msg: String
  },
  data() {
    return {
      board: [],
      state: "",
      time: 0,
      rows: 0,
      cols: 0,
      makeFlag: false,
      gameId: null,
    }
  },
  computed: {
    gameboardStyles () {
      return {
        width: `${this.cols * 50}px`,
        position: 'relative'
      }
    }
  },
  methods: {
    createMatrix( nRows, nCols, defaultValue){
      return Array.from(Array(nRows), 
        () => Array.from(Array(nCols), () => Object.assign({}, defaultValue))
      );
    },
    makeMove(row, col){
      var moveStr = "MOVE";
      if (this.makeFlag) {
        moveStr = "FLAG"
      }
      var data = {
        row, col,
        moveType: moveStr
      }
      this.$http.post('/api/game/' + this.gameId + '/move', data).then(res => {
        this.renderBoard(res.data);
      })
    },
    getAndRenderBoard(){
      this.$http.get("/api/game/" + this.gameId).then(res => {
        this.rows = res.data.rows;
        this.cols = res.data.cols;
        this.renderBoard(res.data.lastState);
        console.log(res.data);
      });
    },
    renderBoard(stateObject) {
      this.state = stateObject.state
      this.time = stateObject.timeElapsed
      this.board = this.createMatrix(this.rows, this.cols, '');
      stateObject.cells.forEach(it => {
        this.board[it.row][it.col] = it.value;
      });

      // remove saved game
      if (this.state !== 'ON_PLAY') {
        removeGame(this.gameId);
      }
    },
    renderText(row, col){
      var cellState = this.board[row][col];
      var number = parseInt(cellState);
      var text = '';

      if (cellState === 'B') {
        text = "💣";
      }
      if (cellState === 'F') {
        text = "🚩";
      }
      if (cellState === 'L') {
        text = "💣X";
      }

      if (!isNaN(number) && number > 0) {
        text = cellState.toString();
      }
      return text;
    },
    renderClass(row, col) {
      return "gameCellState" + this.board[row][col];
    },
    loadGame(myGameId) {
      this.$http.get("/api/game/" + myGameId).then(res => {
        this.rows = res.data.rows;
        this.cols = res.data.cols;
        this.renderBoard(res.data.lastState);
      })
    }
  },
  created() {
    this.gameId = this.$route.params.id;
    this.loadGame(this.gameId);
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.gameboard-header {
  display: flex;
  justify-content: flex-end;
  padding: 10px;
}
.gameboard-footer {
  display: flex;
  padding: 10px;
  justify-content: center;
}

#rootGame {
  margin: 20px auto;
}
.gameboard {
}
.gameRow {
  display: flex;
}
.gameCell {
  display: inline-block ;
  width: 50px;
  height: 50px;
  border: 1px solid black;

}
.gameCell .inner {
  display: flex;
  width: 100%;
  height: 100%;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.gameCellStateA, .gameCellStateF {
  background: rgb(200,200, 200);
}
.gameCellStateB, .gameCellStateB, .gameCellState1, .gameCellState2, .gameCellState3, .gameCellState4, .gameCellState5, .gameCellState6, .gameCellState7, .gameCellState8, .gameCellState9 {
  background: rgb(240,240, 240);
}
</style>
