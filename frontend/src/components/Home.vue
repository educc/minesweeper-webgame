<template>
  <div id='rootHome'>
    <ul>
      <li><router-link to="/create-game">Create new game</router-link></li>
      <li><router-link to="/saved-games">Continue saved games</router-link></li>
    </ul>
  </div>
</template>

<script>

export default {
  name: 'Home', 
  data() { return {} }
}
</script>

<style scoped>

</style>
