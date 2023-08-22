<template>
    <div id="event-content">
      <div id="workout-details" v-if="$store.state.token">
        <EventEdit />
      </div>
      <div class="class" v-else>
        <h1>Please sign in to view your workout information</h1>
      </div>
    </div>
  </template>
  
  <script>
  /* eslint-disable */
  import EventEdit from '@/components/EventEdit.vue';
  
  export default {
    components: {
        EventEdit
    }
  
  }
  </script>
  
  <style>
  #event-content {
    display: flex;
    flex-direction: column;
    justify-content: center;
  }

  .header {
    font-size: 16px;
    padding-bottom: 10px;
    text-align: center;
  }
  
  </style>
  