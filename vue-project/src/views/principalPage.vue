<template>
  <div id="app">
    <header>
      <div class="logo-section">
        <img src="../components/images/logo.jpeg" alt="Nombre de la pagina" height="60">
      </div>
      <nav>
        <ul>
          <li><a @click="navigateTo('/registro')">Registrate</a></li>
          <li><a @click="navigateTo('/login')">Ingresa</a></li>
          <li><a @click="navigateTo('/soporte')">Ayuda</a></li>
          <button class="publish-button" @click="navigateTo('/publicarPropiedad')">Publica tu propiedad</button>
        </ul>
      </nav>
    </header>
    <router-view></router-view>
    <div class="contenido">
      <div class="filtros">
        <h1>Lista de filtros</h1>
        <p class="flitro" onclick="toggleFilter(this)">Filtro 1</p>
        <p class="flitro" onclick="toggleFilter(this)">Filtro 2</p>
        <p class="flitro" onclick="toggleFilter(this)">Filtro 3</p>
        <p class="flitro" onclick="toggleFilter(this)">Filtro 4</p>
        <p class="flitro" onclick="toggleFilter(this)">Filtro 5</p>
        <p class="flitro" onclick="toggleFilter(this)">Filtro 6</p>
        <p class="flitro" onclick="toggleFilter(this)">Filtro 7</p>
      </div>
      <div class="propiedades"> 
        <Propiedad></Propiedad>
        <Propiedad></Propiedad>
        <Propiedad></Propiedad>
        <Propiedad></Propiedad>
      </div>
    </div>
  </div>
  <footer>
    <div class="info">
      <div class="tituloinfo">Título</div>
      <div class="links-container">
        <a href="#" class="link link1">Link 1</a>
        <a href="#" class="link link2">Link 2</a>
        <a href="#" class="link link3">Link 3</a>
      </div>
    </div>    
    <img class="imgfinal" src="../components/images/logo.jpeg" alt="Imagen universidad usach">
  </footer>
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
  padding: 5px 15px;
  background-color: #4CAF50;
  color: white;
}
header .logo-section img {
  border-radius: 40px;
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
.contenido {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-around;
  margin-top: 20px;
}
.filtros {
    border: 1px solid #ccc;
    padding: 10px;
    width: 200px;
    margin: 20px auto;
}

.filtros h1 {
    font-size: 18px;
    margin-bottom: 10px;
}

.filter {
    padding: 5px;
    cursor: pointer;
    border: 1px solid transparent;
    transition: background-color 0.3s, border 0.3s;
}

.filter:hover {
    background-color: #f0f0f0;
}

.filter.selected {
    background-color: #d0e6f6;
    border: 1px solid #007bff;
}

.propiedades {
  display: flex;
  justify-content: space-around;
  margin: 20px 0;
}

.propiedades > * {
  margin: 0 10px; 
}

footer {
  display: flex;
  justify-content: space-around;
  background-color: gainsboro;
}

footer img {
  height: 160px;
  width: 120px;
  place-self: center;
}

.info {
  display: flex;
  flex-direction: column;
  height: 200px; 
}

.tituloinfo {
  flex: 1 1 100px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 30px;
  color: black;
  font-weight: 300px;
}

.links-container {
  flex: 1 1 100px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  margin-bottom: 20px;
}

.link {
  display: flex;
  justify-content: space-between;
  text-decoration: none;
  font-weight: 500;
  color: black;
  font-size: 20px;
}

.logo-section {
  display: flex;
  align-items: center;
}

nav ul {
  display: flex;
  align-items: center;
  list-style-type: none;
  padding: 0;
  margin: 0;
}

nav ul li {
  font-size: 20px;
  margin-right: 15px;
  font-weight: bold;
}

nav ul li a {
  text-decoration: none;
  color: #333;
  cursor: pointer;
}

nav ul li a:hover {
  text-decoration: underline;
}

.publish-button {
  background-color: #3483fa;
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
}
.imgfinal{
  border-radius: 10px;
  height: 100px;
  width: 200px;
}
</style>
