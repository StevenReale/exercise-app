<template>
  <div>
    <article class="workout-card">
        <div class="workout-header">
            <div id="date">FRI, AUG 18, 2023</div>
            <div id="action">Save</div>
        </div>
        <div class="workout-details">
            <div class="exercise">{{ workout.exerciseId }}</div>
            <div class="exercise-details">
                <div class="exercise-number">{{ workout.sets }} x {{ workout.reps }} @ {{ workout.weight }}</div>
            </div>
        </div>
    </article>
  </div>
</template>

<script>
import workoutService from '../services/WorkoutService';
export default {
    name: 'workout-details',
    data() {
        return {
            workout: {
                workoutId: null,
                exerciseId: null,
                sets: null,
                reps: null,
                weight: null,
                time: null,
                distance: null
            }
        };
    },
    created() {
        workoutService.get(this.$route.params.workoutId).then((response) => {
            this.workout = response.data;
        });
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