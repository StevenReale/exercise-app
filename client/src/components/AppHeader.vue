<template>
    <header>
         <div class="header-info">
          <h1>{{ headerText }}</h1>
         </div>
    </header>
  </template>
<script>
/* eslint-disable */

import { mapState } from 'vuex';

export default {
  computed: {
    ...mapState(['headerText'])
  }
}
</script>

<style scoped>
header {
    display: flex;
    height: 50px;
    width: 100%;
    background:black;
    position: fixed;
    align-items: center;
    justify-content: center;
    top: 0;
    z-index: 1;
}

h1 {
  color: white;
  margin: 10px;
  font-size: 16px;
  margin-left: 70px;
}
</style>