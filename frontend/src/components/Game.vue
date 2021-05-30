<template>
  <div>
    {{ title }}
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
      title: "Game Here",
      gameId: null
    }
  },
  methods: {
    putFlag(){
      console.log('flag here');
    },
    makeMove(){
      console.log('make move');
    },
    getAndRenderBoard(){
      this.$http.get("/api/game/" + this.gameId).then(res => {
        console.log(res.data);

      });
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

</style>
