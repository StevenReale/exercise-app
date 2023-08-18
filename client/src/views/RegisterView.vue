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
          <div>
            <button type="submit">Create Account</button>
          </div>
        </div>
        <hr />
        Have an account?
        <router-link v-bind:to="{ name: 'login' }">Sign in!</router-link>
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
    margin-top: 60px;
    flex-direction: column;
    gap: 10px;
    max-width: 350px;
    padding-left: 20px;
    background-color: white;
    border-radius: 20px;
  }
  
  form h1 {
    font-size: 28px;
    color: royalblue;
    font-weight: 600;
    letter-spacing: -1px;
    position: relative;
    display: flex;
    align-items: center;
  }
  
  .message, .signin {
    color: rgba(88, 87, 87, 0.822);
    font-size: 14px;
  }
  
  .signin a {
    color: royalblue;
  }
  
  .signin a:hover {
    text-decoration: underline royalblue;
  }
  
  input {
    width: 100%;
    padding: 20px 10px 20px 10px;
    margin-bottom: 10px;
    outline: 0;
    border: 1px solid rgba(105, 105, 105, 0.397);
    border-radius: 10px;
  }
  
  button {
    border: none;
    outline: none;
    background-color: royalblue;
    padding: 10px 30px;
    border-radius: 10px;
    color: #fff;
    font-size: 16px;
    transform: .3s ease;
  }
  
  button:hover {
    background-color: rgb(56, 90, 194);
  }
  </style>
  