import axios from 'axios';

export default {

    get(id) {
        return axios.get(`/workouts/${id}`);
    },

    getWorkoutsByUser(user) {
        return axios.get('/workouts', user);
    },

    createWorkout(workout) {
        return axios.post('/workouts', workout);
    },

    updateWorkout(workout) {
        return axios.put('/workouts', workout);
    },

    deleteWorkout(workout, user) {
        return axios.delete('/workouts', workout, user);
    }

}