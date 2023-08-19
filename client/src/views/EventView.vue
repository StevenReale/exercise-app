<template>
    <div>
      <h1>Workout Details</h1>
      <div id="workout-details">
        <EventDetails />
      </div>
    </div>
  </template>
  
  <script>
  /* eslint-disable */
  import EventDetails from '../components/EventDetails.vue';
  
  export default {
    components: {
      EventDetails
    }
  
  }
  </script>
  
  <style>
  h1 {
    font-size: 20px;
  }
  
  </style>
  