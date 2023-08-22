<template>
    <div id="event-content">
      <div id="workout-details">
        <EventEdit />
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
  