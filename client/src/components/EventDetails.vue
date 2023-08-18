<template>
    <div>
      <article class="workout-card">
          <div class="workout-header">
              <div id="date">{{ event.date }}</div>
              <div id="action">Save</div>
          </div>
          <div class="workout-details">
              <div class="exercise">{{ exercise.name }}</div>
              <div class="exercise-details">
                  <div class="exercise-number">{{ event.workout.sets }} x {{ event.workout.reps }} @ {{ event.workout.weight }}</div>
              </div>
          </div>
      </article>
    </div>
  </template>
  
  <script>
  import eventService from '../services/EventService';
  import exerciseService from '@/services/ExerciseService';
  export default {
      name: 'event-details',
      data() {
          return {
              event: {
                "eventId": null,
                "userId": null,
                "workout": {
                    "workoutId": null,
                    "exerciseId": null,
                    "sets": null,
                    "reps": null,
                    "weight": null,
                    "time": null,
                    "distance": null
                },
                "date": ''
            },
            exercise: {
                "exerciseId": null,
                "name": ''
            }
          };
      },
      created() {
          this.fetchEvent();
      }, 
      methods: {
        fetchEvent() {
            eventService.get(this.$route.params.eventId).then((response) => {
              this.event = response.data;
              this.getExercise();
          })
          .catch(error => {
          console.error('Failed to retrieve event:', error);
        });
        },
        getExercise() {
            exerciseService.get(this.event.workout.exerciseId).then((response) => {
                this.exercise = response.data;
            });
        }
    },
    }
  </script>
  
  <style scoped>
  .workout-card {
      display: grid;
      width: 340px;
      height: 150px;
      border-radius: 10px;
      background: #202020;
      box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
      color: white;
      padding: 20px;
      font-size: 12px;
  }
  
  .workout-details {
      display: flex;
      justify-content: space-between;
  }
  
  .workout-header {
      display: flex;
      justify-content: space-between;
  }
  
  </style>