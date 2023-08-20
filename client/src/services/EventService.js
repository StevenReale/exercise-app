/* eslint-disable */
import axios from 'axios';

export default {
    
    get(id) {
        return axios.get(`/events/${id}`);
    },

}