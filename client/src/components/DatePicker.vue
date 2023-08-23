<template>
    <section>
        <div class="form-group">
            <label>Select a date</label>
            <div>
                <flat-pickr 
                v-model="date"
                :config="config"
                class="form-control"
                placeholder="Select date"
                name="date"
                />
            </div>
        </div>
    </section>
</template>

<script setup>
    import {ref} from 'vue';
    import flatPickr from 'vue-flatpickr-component';
    import 'flatpickr/dist/flatpickr.css';

    const date = ref(null);

    const config = ref({
        wrap: false,
        altFormat: 'M j, Y',
        altInput: true,
        dateFormat: 'Y-m-d',  
    });
</script>

<style scoped>

.form-group {
    display: flex;
    flex-direction: column;
    align-items: center;
}
.form-control {
    left: 0;
    background-color: blue;
}

</style>