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
    display: flex;
    flex: 1 1 0;
    padding: 2rem;
    margin-top: 50px;
    justify-content: center;
    align-items: flex-start;

    @media (min-width: 481px) {
        padding-left: 3rem;
    }

    @media (max-width: 768px) {
        padding-left: 95px;
    }

    @media (max-width: 480px) {
      padding-left: 95px;
    }
  }
}

</style>
