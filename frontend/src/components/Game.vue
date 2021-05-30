<template>
  <div id='rootGame'>
    {{ title }}
    <div class='gameboard' v-if='rows > 0 && cols > 0'>
      <div v-for="row in rows" :key="row" class='gameRow'>
        <div v-for="col in cols" :key="col" class='gameCell' v-on:click="makeMove(row, col)">
          <div class='inner'  v-bind:class="renderClass(row-1,col-1)">
            <span>{{ renderText(row-1,col-1) }}</span>
          </div>
        </div>
      </div>
    </div>
    <p>State = {{ state }}</p>
  </div>
</template>

<script>

export default {
  name: 'Game',
  props: {
    msg: String
  },
  data() {
    return {
      board: [],
      state: "",
      rows: 0,
      cols: 0,
      title: "Minesweeper",
      gameId: null
    }
  },
  methods: {
    createMatrix( nRows, nCols, defaultValue){
      return Array.from(Array(nRows), 
        () => Array.from(Array(nCols), () => Object.assign({}, defaultValue))
      );
    },
    putFlag(){
      console.log('flag here');
    },
    makeMove(row, col){
      var data = {
        row, col
      }
      this.$http.post('/api/game/' + this.gameId + '/move', data).then(res => {
        this.renderBoard(res.data.state, res.data.cells);
      })
    },
    getAndRenderBoard(){
      this.$http.get("/api/game/" + this.gameId).then(res => {
        this.rows = res.data.rows;
        this.cols = res.data.cols;
        this.renderBoard(res.data.lastState.state, res.data.lastState.cells);
        console.log(res.data);
      });
    },
    renderBoard(state, cells) {
      this.state = state
      this.board = this.createMatrix(this.rows, this.cols, '');
      cells.forEach(it => {
        this.board[it.row][it.col] = it.value;
      });
    },
    renderText(row, col){
      var cellState = this.board[row][col];
      var number = parseInt(cellState);
      var text = '';

      if (cellState === 'B') {
        text = "💣";
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
    createGame(callback){
      this.$http.post("/api/game").then(res => {
        this.gameId = res.data.gameId;
        callback();
      })
    }
  },
  created() {
    this.createGame(this.getAndRenderBoard);
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

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

.gameCellStateA {
  background: rgb(200,200, 200);
}
.gameCellStateB, .gameCellStateB, .gameCellState1, .gameCellState2, .gameCellState3, .gameCellState4, .gameCellState5, .gameCellState6, .gameCellState7, .gameCellState8, .gameCellState9 {
  background: rgb(240,240, 240);
}
</style>
