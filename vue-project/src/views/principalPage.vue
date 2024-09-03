<template>
  <div id="app">
    <header>
      <div class="header-content">
        <div class="logo-section">
          <img src="../components/images/logoOficial.jpeg" alt="Nombre de la página" height="60" @click="navigateTo('/principal')">
        </div>
        <nav>
          <ul>
              <a v-if="!isAuthenticated" class = "login-button" @click="navigateTo('/registro')">Regístrate</a>
              <a v-if="!isAuthenticated" class = "register-button" @click="navigateTo('/login')">Ingresa</a>
              <a class = "help-button" @click="navigateTo('/soporte')">Ayuda</a>
            <div class="botonesPPL">
              <li v-if="isAuthenticated">
                <button class="publish-button" @click="navigateTo('/publicarPropiedad')">Publica tu propiedad</button>
              </li>
              <li v-if="isAuthenticated">
                <button class="profile-button" @click="navigateTo('/perfil')">Mi Perfil</button>
              </li>
              <li v-if="isAuthenticated">
                <button class="logout-button" @click="logout">Cerrar sesión</button>
              </li>
            </div>
          </ul>
        </nav>
      </div>
    </header>

    <div class="promo-image">
      <img src="@/components/images/fondo.png" alt="">
    </div>

    <div class="search-filters">
      <button @click="algo" class="mayorPrecio">Mayor Precio</button>
      <button @click="algo" class="menorPrecio">Menor Precio</button>
      <select v-model="saleType" class="filter-select">
        <option value="venta">Venta</option>
        <option value="arriendo">Arriendo</option>
      </select>
      <select v-model="propertyType" class="filter-select">
        <option value="departamentos">Departamentos</option>
        <option value="casas">Casas</option>
      </select>
      <ComunaSelect v-model="location" required class="filter-select comuna-selector"/>
      <button @click="search" class="search-button">Buscar</button>
    </div>
    <div class="contenido">
      <div class="propiedades">
        <Propiedad
          v-for="propiedad in propiedades"
          :key="propiedad.id"
          :propiedad="propiedad" 
        />
      </div>
    </div>

    <footer>
      <p class = "copyright">© 2024 HomeSphere Todos los derechos reservados. Prohibida su reproducción total o parcial por cualquier medio</p>
    </footer>
  </div>
</template>

<script>
import { ref, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import Propiedad from '../components/propiedad.vue';
import ComunaSelect from '../components/comunas.vue';

export default {
  name: 'App',
  components: {
    Propiedad,
    ComunaSelect
  },
  setup() {
    const router = useRouter();
    const isAuthenticated = ref(false);
    const propiedades = ref([]);
    const saleType = ref('venta');
    const propertyType = ref('departamentos');
    const location = ref('');
    const navigateTo = (route) => {
      router.push(route);
    };

    const search = async () => {
      try {
        const response = await axios.get('http://localhost:8080/property/search', {
          params: {
            saleType: saleType.value,
            propertyType: propertyType.value,
            location: location.value,
          }
        });
        propiedades.value = response.data;
      } catch (error) {
        console.error('Error during search:', error.response ? error.response.data : error.message);
      }
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

    onMounted(() => {
      checkAuth();
      axios.get('http://localhost:8080/property/obtainAll')
        .then(response => {
          propiedades.value = response.data;
          console.log('Propiedades obtenidas:', propiedades.value);  
        })
        .catch(error => {
          console.error('Error fetching properties:', error.response ? error.response.data : error.message);
        });
    });

    watch(() => router.currentRoute.value, () => {
      checkAuth();
    });

    return {
      navigateTo,
      isAuthenticated,
      saleType,
      propertyType,
      location,
      search,
      propiedades,
      logout
    };
  }
}
</script>

<style scoped>
*{
  font-family: 'Poppins';
}
#app {
  background-color: #eaf3e6; 
  min-height: 100vh; 
}


.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 5px 15px;
  background-color: #4ca771;
  color: white;
  text-decoration: none
}

header .logo-section img {
  width: 100px;
  height: 80px;
  border-radius: 10px;
}

nav ul {
  display: flex;
  align-items: center;
  justify-content: space-around;
  list-style: none;
  padding: 0;
  margin: 0;
  text-decoration: none;
}

nav ul li {
  margin-left: 20px;
  text-decoration: none;
}

nav ul li a {
  text-decoration: none;
  font-size: 18px;
}

nav ul li a:hover {
  text-decoration: underline;
}
.botonesPPL {
  display: flex;
}

.buttonsNonUser, .publish-button, .profile-button, .logout-button,.login-button, .register-button {
  background-color: #EAF9E7;
  color: #013237;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.publish-button:hover, .profile-button:hover, .logout-button:hover,.buttonsNonUser:hover, .login-button:hover, .register-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 8px rgba(0, 0, 0, 0.1);
}

.logout-button {
  background-color: #c62828;
  color: white;
}

.logout-button:hover {
  background-color: #c62828;
}

.help-button {
  background-color: #EAF9E7;
  color: black;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  margin-right: 10px;
  margin-left: 20px;
  transition: background-color 0.3s ease;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.help-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 8px rgba(0, 0, 0, 0.1);
}

.register-button{
  margin-left: 22px;
}

.promo-image {
  width: 100%;
  height: 500px;
  overflow: hidden;
  margin: 0;
}

.promo-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.search-filters {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  gap: 15px;
  padding: 15px 25px;
  background-color: #EAF9E7;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.25);
  border-radius: 10px;
  max-width: 800px;
  margin: 20px auto;
}

.filter-select,
.filter-input {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: white;
  transition: all 0.3s ease;
}

.filter-select:hover,
.filter-input:hover {
  border-color: #888;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
}
.mayorPrecio , .menorPrecio{
  height: 80px;
  background-color: #4CA771;
  color: #013237;
  border: none; 
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}
.comuna-selector {
  padding-top: 10px;
  max-width: 220px; 
  width: 100%; 
  
}

.filter-checkbox {
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #555;
}
.search-button {
  height: 80px;
  background-color: #4ca771;
  color: black;
  border: none;
  padding: 5px 20px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.search-button:hover {
  background-color: #4ca771;
}

.contenido {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.propiedades {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.prop{
  background-color: #EAF9E7;
}

footer {
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 10px 20px;
  background-color: #4CA771;
  color: white;
}
.copyright {
  font-size: 14px;
  text-align: left;

}
.promo-image {
  width: 100%;
  height: 500px;
  overflow: hidden;
  margin: 0;
}
</style>