<template>
  <header class="header">
    <div class="logo-section">
      <a @click="navigateTo('/principal')">
        <img src="../components/images/logo.jpeg" alt="Nombre de la página" height="60">
      </a>
    </div>
    <nav class="nav">
      <ul class="nav-list">
        <li><a @click="navigateTo('/soporte')" class="nav-link">Ayuda</a></li>
        <li><button class="publish-button" @click="navigateTo('/publicarPropiedad')">Publica tu propiedad</button></li>
      </ul>
    </nav>
  </header>

  <div class="user-info">
    <div class="user-photo"> 
      <img class="user-image" src="../components/images/logo.jpeg" alt="Imagen de perfil">
    </div>
    <div class="user-details">
      <h1>Benjamin Andres Bustamante Elola</h1>
      <h2>Correo electronico</h2>
      <h3>Telefono</h3>
    </div>
  </div>

  <div class="property-list">
    <Propiedad />
    <Propiedad />
    <Propiedad />
    <Propiedad />
  </div>
</template>

<script>
import { useRouter } from 'vue-router'
import Propiedad from '../components/propiedad.vue';

export default {
  name: 'App',
  components: {
    Propiedad
  },
  setup() {
    const router = useRouter()

    const navigateTo = (route) => {
      router.push(route)
    }

    return {
      navigateTo
    }
  }
}
</script>

<style scoped>
header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 30px;
  background-color: #4CAF50;
  color: white;
}

header .logo-section img {
  border-radius: 50px;
}

nav ul {
  display: flex;
  align-items: center;
  list-style: none;
  padding: 0;
  margin: 0;
}

nav ul li {
  margin-right: 20px;
}

nav ul li a {
  color: white;
  text-decoration: none;
  font-size: 18px;
}

nav ul li a:hover {
  text-decoration: underline;
}

.publish-button {
  background-color: #3483fa;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

.user-info {
  display: flex;
  justify-content: space-evenly;
  margin: 30px 20px;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 15px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.user-photo {
  display: flex;
  justify-content: center;
  align-items: center;
}

.user-image {
  width: 600px;
  height: 400px;
  border-radius: 30px;
}

.user-details {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.user-details h1,
.user-details h2,
.user-details h3 {
  font-size: 22px;
  margin: 10px 0;
  color: #555;
}

.property-list {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
  margin: 20px 0;
}

.property-list > * {
  margin: 10px;
  flex-basis: calc(25% - 20px);
  box-sizing: border-box;
}
</style>
