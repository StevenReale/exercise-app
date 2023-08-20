<template>
    <div id="login">
      <form v-on:submit.prevent="login">
        <h1>Please Sign In</h1>
        <p class="message">Sign in now and get full access to our app. </p>
        <div id="fields">
          <label for="username"></label>
          <input
            type="text"
            id="username"
            placeholder="Username"
            v-model="user.username"
            required
            autofocus
          />
          <label for="password"></label>
          <input
            type="password"
            id="password"
            placeholder="Password"
            v-model="user.password"
            required
          />
          <div><button type="submit">Sign in</button></div>
        </div>
        <div class="signin">
        Need an account? <router-link v-bind:to="{ name: 'register' }">Register!</router-link>
        </div>
      </form>
    </div>
</template>

<script>
/* eslint-disable */
import authService from "../services/AuthService";

export default {
    name: "LoginView",
    data() {
        return {
            user: {
                username: "",
                password: "",
            },
        };
    },
    methods: {
        login() {
            authService
                .login(this.user)
                .then((response) => {
                    if (response.status == 200) {
                        this.$store.commit("SET_AUTH_TOKEN", response.data.token);
                        this.$store.commit("SET_USER", response.data.user);
                        this.$router.push("/");
                    }
                })
                .catch((error) => {
                    const response = error.response;
                    if (!response) {
                        alert(error);
                    } else if (response.status === 401) {
                        alert ("Invalid username and password");
                    } else {
                        alert(response.message);
                    }
                });
        },
    },
};
</script>

<style lang="scss" scoped>

form {
  display: flex;
  margin-top: 60px;
  flex-direction: column;
  max-width: 350px;
  padding-left: 20px;
  background-color: white;
  border-radius: 20px;
  gap: 10px;
}

form h1 {
  font-size: 1.5rem;
  color: var(--primary);
  font-weight: 600;
  position: relative;
  display: flex;
  align-items: center;
}

.message, .signin {
  color: rgba(88, 87, 87, 0.822);
  font-size: 14px;
}

.signin a {
  color: var(--primary);
}

.signin a:hover {
  text-decoration: underline var(--primary);
}

input {
  width: 100%;
  padding: 20px 10px 20px 10px;
  margin-bottom: 10px;
  outline: 0;
  border: 1px solid var(--grey);
  border-radius: 10px;
  font-family: Krona One;

  text {
    color: var(--primary);
  }
}

button {
  border: none;
  outline: none;
  background-color: var(--primary);
  padding: 10px 30px;
  border-radius: 10px;
  color: #fff;
  font-size: 0.75rem;
  transform: .3s ease;
  font-family: Krona One;
}

button:hover {
  background-color: var(--grey);
}

label {
  display: none;
  visibility: hidden;
}
</style>