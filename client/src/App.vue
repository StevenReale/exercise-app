<template>
  <div id="app">
    <nav>
      <app-header id="app-header" />
      <main-nav id="nav-menu"></main-nav>
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link>
    </nav>
    <router-view/>
  </div>
</template>

<script>
/* eslint-disable */
import AppHeader from './components/AppHeader.vue';
// import MainNav from './components/MainNav.vue';
export default {
  components: {AppHeader}
  // add MainNav back
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

</style>
