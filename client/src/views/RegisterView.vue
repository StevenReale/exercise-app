<template>
    <div id="register">
      <form v-on:submit.prevent="register">
        <h1>Create Account</h1>
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
          <label for="first_name">First Name</label>
          <input
            type="text"
            id="first-name"
            placeholder="First"
            v-model="user.first"
            required
          />
          <label for="last_name">Last Name</label>
          <input
            type="text"
            id="last-name"
            placeholder="Last"
            v-model="user.last"
            required
          />
          <label for="password">Password</label>
          <input
            type="password"
            id="password"
            placeholder="Password"
            v-model="user.password"
            required
          />
          <label for="confirmPassword">Confirm password</label>
          <input
            type="password"
            id="confirmPassword"
            placeholder="Confirm Password"
            v-model="user.confirmPassword"
            required
          />
          <div></div>
          <div class="button-area">
            <button type="submit">Create Account</button>
          </div>
        </div>
        <div class="signin">
        Have an account?
        <router-link v-bind:to="{ name: 'login' }">Sign in!</router-link>
      </div>
      </form>
    </div>
  </template>
<script>
/* eslint-disable */
  import authService from '../services/AuthService';
  
  export default {
    name: "RegisterView",
    data() {
      return {
        user: {
          username: "",
          password: "",
          confirmPassword: "",
          first: "",
          last: "",
          role: "user",
        },
      };
    },
    methods: {
      error(msg) {
        alert(msg);
      },
      success(msg) {
        alert(msg);
      },
      register() {
        if (this.user.password != this.user.confirmPassword) {
          this.error("Password & Confirm Password do not match");
        } else {
          authService
            .register(this.user)
            .then((response) => {
              if (response.status == 201) {
                this.success("Thank you for registering, please sign in.");
                this.$router.push({
                  path: "/login",
                });
              }
            })
            .catch((error) => {
              const response = error.response;
              if (!response) {
                this.error(error);
              } else if (response.status === 400) {
                if (response.data.errors) {
                  // Show the validation errors
                  let msg = "Validation error: ";
                  for (let err of response.data.errors) {
                    msg += `'${err.field}':${err.defaultMessage}. `;
                  }
                  this.error(msg);
                } else {
                  this.error(response.data.message);
                }
              } else {
                this.error(response.data.message);
              }
            });
        }
      },
    },
  };
  </script>
  
  <style scoped>
  
  label {
    display: none;
    visibility: hidden;
  }
  
  form {
    display: flex;
    flex-direction: column;
    max-width: 350px;
    background-color: white;
    border-radius: 20px;
    gap: 10px;
  }
  
  form h1 {
    font-size: 1rem;
    color: var(--primary);
    font-weight: 600;
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  
  .message, .signin {
    color: rgba(88, 87, 87, 0.822);
    font-size: 0.75rem;
  }

  .signin {
    
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
    font-size: .75rem;
  }

  .button-area {
    display: flex;
    justify-content: center;
  }
  
  button {
    border: none;
    outline: none;
    background-color: var(--primary);
    padding: 10px 30px;
    border-radius: 10px;
    color: var(--light);
    font-size: 0.75rem;
    transform: .3s ease;
    font-family: Krona One;
  }
  
  button:hover {
    background-color: var(--dark-grey);
  }
  </style>
  