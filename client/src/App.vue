<template>
  <div class="app">
    <nav>
      <app-header id="app-header" />
      <main-nav id="nav-menu"></main-nav>
    </nav>
    <router-view/>
  </div>
</template>

<script>
/* eslint-disable */
import AppHeader from './components/AppHeader.vue';
import MainNav from './components/MainNav.vue';
export default {
  components: {AppHeader, MainNav}
}
</script>

<style>
.app {
  display: flex;
}

</style>
