<template>
    <header>
         <div class="header-info">
          <h1>My Logbook</h1>
         </div>
    </header>
  </template>
  
  <script>
  export default {
  
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
    margin-bottom: 10px;
    font-size: 20px;
  }
  </style>