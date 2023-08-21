<template>
  <div class="app">
    
    <AppHeader />
    <MainNav />
    <main>
      <router-view/>
    </main>
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
  font-family: "Krona One", sans-serif;

  main {
    flex: 1 1 0;
    padding: 2rem;
    margin-top: 50px;

    @media (max-width: 1024px) {
        padding-left: 3rem;
    }
  }
}

</style>
