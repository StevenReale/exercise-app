<template>
    <div>
      <div class="date">{{ formatDate(event.date) }}</div>
      <article class="workout-card" v-for="(workout, index) in event.workouts" :key="index">
          <div class="workout-header">
              <div class="exercise">{{ exerciseNames[workout.exerciseId] }}</div>
              <div id="action">Save</div>
          </div>
          <div class="workout-details">
            <div class="exercise-details">
              <div class="exercise-number">
                <template v-if="workout.reps !== 0">
                <div class="input-area">
                  <input class="number-input" id="sets" type="text" v-model="workout.sets" />
                  <label for="sets">Sets</label>
                </div>
                <div class="symbol">X</div>
                </template>
                <template v-if="workout.reps !== 0">
                  <div class="input-area">
                    <input class="number-input" id="reps" type="text" v-model="workout.reps" />
                    <label for="reps">Reps</label>
                  </div>
                  <div class="symbol">@</div> 
                </template>
                <template v-if="workout.weight !== 0">
                  <div class="input-area">
                    <input class="number-input weight" id="weight" type="text" v-model="workout.weight" />
                    <label for="weight">Weight</label>
                  </div>
                </template>
                <template v-if="workout.time !== 0">
                  <div class="input-area">
                    <input class="number-input" id="time" type="text" v-model="workout.time" />
                    <label for="time">Time</label>
                  </div>
                </template>
                <template v-if="workout.distance !== 0">
                  <div class="input-area">
                    <input class="number-input" id="distance" type="text" v-model="workout.distance" />
                    <label for="distance">Distance</label>
                  </div>
                </template>
              </div>
            </div>
          <div>
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
      name: 'event-edit',
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
      width: fit-content;
      border-radius: 10px;
      background: #202020;
      box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
      color: white;
      padding: 20px;
      font-size: 12px;
      gap: 10px;
      margin-bottom: 10px;
  }

  #action {
    color: var(--red);
    font-size: 10px;
    justify-content: flex-end;
  }

  .date {
    font-size: 12px;
    color: var(--dark-grey);
    padding-bottom: 10px;
    text-align: center;
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

  .exercise-number {
    display: flex;
    justify-content: space-evenly;
  }
  
  .workout-header {
      display: flex;
      justify-content: space-between;
  }

  .input-area {
    display: flex;
    flex-direction: column;
    margin: 0;
    text-align: center;
    text-transform: uppercase;
    font-size: 8px;
    color: var(--grey);
  }
  .number-input {
    width: 50px;
    height: 40px;
    border-radius: 5px;
    border-style: none;
    font-size: 20px;
    font-family: Krona One;
    color: var(--dark-grey);
    padding: 5px;
    text-align: center;
    margin-bottom: 5px;
  }

  .weight {
    width: 80px;
  }

  .symbol {
    margin: 10px 20px;
    font-size: 14px;
  }
  
  </style>