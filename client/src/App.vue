<template>
  <div class="app">
    
    <AppHeader />
    <MainNav />
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

<style lang="scss">

.app {
  display: flex;

  main {
    flex: 1 1 0;
    padding: 2rem;

    @media (max-width: 1024px) {
        padding-left: 6rem;
    }
  }
}

</style>
