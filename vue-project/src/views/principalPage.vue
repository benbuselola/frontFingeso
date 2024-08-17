<template>
  <div id="app">
    <header>
      <div class="logo-section">
        <img src="../components/images/logo.jpeg" alt="Nombre de la pagina" height="60" @click="navigateTo('/principal')">
      </div>
      <nav>
        <ul>
          <li v-if="!isAuthenticated">
            <a @click="navigateTo('/registro')">Regístrate</a>
          </li>
          <li v-if="!isAuthenticated">
            <a @click="navigateTo('/login')">Ingresa</a>
          </li>
          <li>
            <a @click="navigateTo('/soporte')">Ayuda</a>
          </li>
          <li v-if="isAuthenticated">
            <button class="publish-button" @click="navigateTo('/publicarPropiedad')">Publica tu propiedad</button>
          </li>
          <li v-if="isAuthenticated">
            <button class="profile-button" @click="navigateTo('/perfil')">Mi Perfil</button>
          </li>
          <li v-if="isAuthenticated">
            <button class="logout-button" @click="logout">Cerrar sesión</button>
          </li>
        </ul>
      </nav>
    </header>
    
    <main>
      <router-view></router-view>
      <div class="contenido">
        <div class="filtros">
          <h2>Filtros</h2>
          <div class="filtro" v-for="filtro in filtros" :key="filtro" @click="toggleFilter(filtro)">
            {{ filtro }}
          </div>
        </div>
        <div class="propiedades">
          <Propiedad v-for="propiedad in propiedades" :key="propiedad.id" :propiedad="propiedad"/>
        </div>
      </div>
    </main>

    <footer>
      <!-- Footer content remains the same -->
    </footer>
  </div>
</template>

<script>
import { ref, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import Propiedad from '../components/propiedad.vue';

export default {
  name: 'App',
  components: {
    Propiedad
  },
  setup() {
    const router = useRouter();
    
    const isAuthenticated = ref(false);
    const propiedades = ref([]);
    const filtros = ['Filtro 1', 'Filtro 2', 'Filtro 3', 'Filtro 4', 'Filtro 5', 'Filtro 6', 'Filtro 7'];

    const navigateTo = (route) => {
      router.push(route);
    };

    const toggleFilter = (filtro) => {
      // Añade la lógica para manejar el cambio de filtros
    };

    const checkAuth = () => {
      const userId = localStorage.getItem('usuario');
      isAuthenticated.value = !!userId;
    };

    const logout = () => {
      localStorage.removeItem('usuario');
      isAuthenticated.value = false;
      router.push('/login');
    };

    onMounted(async () => {
      checkAuth();
      try {
        const response = await axios.get('http://localhost:8080/users/propiedades');
        propiedades.value = response.data;
      } catch (error) {
        console.error('Error fetching properties:', error.response ? error.response.data : error.message);
      }
    });

    watch(() => router.currentRoute.value, () => {
      checkAuth();
    });

    return {
      navigateTo,
      isAuthenticated,
      filtros,
      toggleFilter,
      propiedades,
      logout
    };
  }
}
</script>

<style scoped>
/* Header Styles */
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
  margin-left: 20px;
}

nav ul li a {
  color: white;
  text-decoration: none;
  font-size: 18px;
}

nav ul li a:hover {
  text-decoration: underline;
}

nav ul li a, .publish-button, .profile-button {
  color: white;
  text-decoration: none;
  background: none;
  border: none;
  font-size: 18px;
  cursor: pointer;
}

.publish-button {
  background-color: #3483fa;
  padding: 10px 15px;
  border-radius: 5px;
}

.profile-button {
  background-color: #007BFF;
  padding: 10px 15px;
  border-radius: 5px;
}

nav ul li a:hover, .publish-button:hover, .profile-button:hover {
  text-decoration: underline;
}

/* Main Content Styles */
main {
  padding: 20px;
}

.contenido {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
}

.filtros {
  border: 1px solid #ccc;
  padding: 10px;
  width: 200px;
  background-color: #f9f9f9;
  border-radius: 5px;
}

.filtros h2 {
  font-size: 18px;
  margin-bottom: 10px;
}

.filtro {
  padding: 5px;
  cursor: pointer;
  border: 1px solid transparent;
  transition: background-color 0.3s, border 0.3s;
}

.filtro:hover {
  background-color: #f0f0f0;
}

.filtro.selected {
  background-color: #d0e6f6;
  border: 1px solid #007bff;
}

.propiedades {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  gap: 15px;
  margin: 20px 0;
}

footer {
  display: flex;
  justify-content: space-between;
  padding: 20px;
  background-color: #f1f1f1;
  border-top: 1px solid #ccc;
}

.info {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.tituloinfo {
  font-size: 24px;
  margin-bottom: 10px;
}

.links-container {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.link {
  text-decoration: none;
  color: #007BFF;
  font-size: 18px;
}

.link:hover {
  text-decoration: underline;
}

.imgfinal {
  border-radius: 10px;
  height: 100px;
  width: 200px;
}

.logout-button {
  background-color: #dc3545;
  padding: 10px 15px;
  border-radius: 5px;
  color: white;
  border: none;
  cursor: pointer;
}

.logout-button:hover {
  background-color: #c82333;
}
</style>
