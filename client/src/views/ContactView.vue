<template>
    <div id="login">
      <form>
        <h1>Contact Us</h1>
        <p class="message">Questions about this app? Fill out the form below.</p>
        <div id="fields">
          <label for="email"></label>
          <input
            type="email"
            id="email"
            placeholder="Email"
            required
          />
          <label for="name"></label>
          <input
            type="text"
            id="name"
            placeholder="Name"
          />
          <textarea name="query" id="query" cols="30" rows="10" placeholder="Get in touch"></textarea>
          <div><button type="submit">Contact Us</button></div>
        </div>
      </form>
    </div>
</template>

<script>

</script>

<style lang="scss" scoped>

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
} 

.message, .signin {
  color: rgba(88, 87, 87, 0.822);
  font-size: 0.75rem;
}

.signin a {
  color: var(--primary);
}

.signin a:hover {
  text-decoration: underline var(--primary);
}

input, textarea {
  width: 100%;
  padding: 20px 10px 20px 10px;
  margin-bottom: 10px;
  outline: 0;
  border: 1px solid var(--grey);
  border-radius: 10px;
  font-family: Krona One;
  font-size: .75rem;
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
  background-color: var(--grey);
}

label {
  display: none;
  visibility: hidden;
}
</style>