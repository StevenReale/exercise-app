<template>
    <div>
      <article class="workout-card">
          <div class="workout-header">
              <div id="date">{{ formatDate(event.date) }}</div>
              <div id="action">Edit</div>
          </div>
          <div class="workout-details">
            <div v-for="(workout, index) in event.workouts" :key="index">
                <div class="each-workout">
                    <div class="exercise">{{ exerciseNames[workout.exerciseId] }}</div>
                    <div class="exercise-details">
                    <div class="exercise-number">{{ workout.sets }} x {{ workout.reps }} @ {{ workout.weight }}</div>
                </div>
              </div>
            </div>
        </div>
      </article>
    </div>
</template>
<script>
/* eslint-disable */
  import eventService from '../services/EventService';
  import exerciseService from '@/services/ExerciseService';
  export default {
      name: 'event-details',
      data() {
          return {
              event: {
                eventId: null,
                userId: null,
                workouts: [
                    {
                        workoutId: null,
                        exerciseId: null,
                        sets: null,
                        reps: null,
                        weight: null,
                        time: null,
                        distance: null
                    },
                ],
                date: ''
            },
            exercises: [],
          };
      },
      computed: {
            exerciseNames() {
                const exerciseNames = {};
                this.exercises.forEach(exercise => {
                    exerciseNames[exercise.exerciseId] = exercise.name;
                });
                return exerciseNames;
            },
    },
      created() {
          this.fetchEvent();
    }, 
      methods: {
        fetchEvent() {
            eventService.get(this.$route.params.eventId).then((response) => {
              this.event = response.data;
              this.getExerciseNames();
          })
          .catch(error => {
          console.error('Failed to retrieve event:', error);
        });
        },
        getExerciseNames() {
            const exerciseIds = this.event.workouts.map(workout => workout.exerciseId);
            this.exercises = []; // Clear previous exercise data
            exerciseIds.forEach(exerciseId => {
            exerciseService.get(exerciseId)
                .then((response) => {
                    this.exercises.push(response.data);
            });
        });
        },
        formatDate(dateString) {
            const months = ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL','AUG','SEP','OCT','NOV','DEC'];
            const daysOfWeek = ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'];

            const date = new Date(dateString);
            const weekday = daysOfWeek[date.getDay()];
            const month = months[date.getMonth()];
            const day = date.getDate();
            const year = date.getFullYear();

            const formattedDate = `${weekday}, ${month} ${day}, ${year}`;
            return formattedDate;
        }
    }
}
</script>
  
<style scoped>
  .workout-card {
      display: flex;
      flex-direction: column;
      width: 340px;
      border-radius: 10px;
      background: #202020;
      box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
      color: white;
      padding: 20px;
      font-size: 12px;
      gap: 10px;
  }

  #action {
    color: var(--red);
  }
  
  .workout-details {
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      gap: 5px;
  }

  .each-workout {
    display: flex;
    justify-content: space-between
  }
  
  .workout-header {
      display: flex;
      justify-content: space-between;
  }
  
  </style>