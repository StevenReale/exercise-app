<template>
  <h1>Logout</h1>
</template>

<script>
/* eslint-disable */
export default {
    name: "LogoutView",
    created() {
        this.$store.commit("LOGOUT");
        this.$router.push("/login");
    }
}
</script>