import axios from 'axios';

export default {

    allExercises() {
        return axios.get('/exercises');
    },

    get(id) {
        return axios.get(`/exercises/${id}`);
    },

    addExercise(exercise, user) {
        return axios.post('/exercises', exercise, user);
    },

    updateExercise(exercise, user) {
        return axios.put('/exercises', exercise, user);
    },

    deleteExercise(exercise, user) {
        return axios.delete('/exercises', exercise, user);
    }
}