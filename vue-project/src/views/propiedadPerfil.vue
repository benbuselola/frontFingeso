<template>
  <div id="app">
    <header class="header">
      <div class="logo-section">
        <img src="../components/images/logoOficial.jpeg" alt="Nombre de la página" height="60">
      </div>
      <nav class="nav">
        <ul class="nav-list">
          <li class="buttonsNonUser"><router-link to="/principal" class="nav-link">Inicio</router-link></li>
          <li class="buttonsNonUser"><router-link to="/soporte" class="nav-link">Ayuda</router-link></li>
          
          <li v-if="isAuthenticated">
            <button class="publish-button" @click="navigateTo('/publicarPropiedad')">Publica tu propiedad</button>
            <button class="logout-button" @click="logout">Cerrar sesión</button>
          </li>
        </ul>
      </nav>
    </header>

    <div class="user-info" v-if="property">
      <div class="user-photo">
        <img :src="imagen" alt="Imagen de propiedad" class="user-image">
      </div>
      <div class="user-details">
        <div class="comunaProp">
          <p>Comuna: </p>
          <h1>{{ property.neighboorhood }}</h1>
          <transition name="fade">
            <img src="@/components/images/editar.webp" class="editButton" v-if="showEditButtons" @click="navigateToEdit('/actualizarComuna')"></img>
          </transition>
        </div>
        <div class="tipoProp">
          <p>Tipo de propiedad: </p>
          <h1>{{ property.propertyType }}</h1>
          <transition name="fade">
            <img src="@/components/images/editar.webp" class="editButton" v-if="showEditButtons" @click="navigateToEdit('/actualizarTipo')"></img>
          </transition>
        </div>
        <div class="m2Prop">
          <p>Metros cuadrados: </p>
          <h1>{{ property.size }} M²</h1>
          <transition name="fade">
            <img src="@/components/images/editar.webp" class="editButton" v-if="showEditButtons" @click="navigateToEdit('/actualizarMetros')"></img>
          </transition>
        </div>
        <div class="dormProp">
          <p>Dormitorios: </p>
          <h1>{{ property.bedrooms }} Dormitorios</h1>
          <transition name="fade">
            <img src="@/components/images/editar.webp" class="editButton" v-if="showEditButtons" @click="navigateToEdit('/actualizarDormitorios')"></img>
          </transition>
        </div>
        <div class="banioProp">
          <p>Baños: </p>
          <h1>{{ property.bathrooms }} Baños</h1>
          <transition name="fade">
            <img src="@/components/images/editar.webp" class="editButton" v-if="showEditButtons" @click="navigateToEdit('/actualizarBaños')"></img>
          </transition>
        </div>
        <div class="precioProp">
          <p>Precio: </p>
          <h1>{{ property.value }} UF</h1>
          <transition name="fade">
            <img src="@/components/images/editar.webp" class="editButton" v-if="showEditButtons" @click="navigateToEdit('/actualizarPrecio')"></img>
          </transition>
        </div>
        <div class="descipProp">
          <p>Descripción: </p>
          <h1>{{ property.description }}</h1>
          <transition name="fade">
            <img src="@/components/images/editar.webp" class="editButton" v-if="showEditButtons" @click="navigateToEdit('/actualizarDescripcion')"></img>
          </transition>
        </div>
        <div class="buttonEditContact">
          <button v-if="isPropertyOwner" @click="toggleEditButtons" class="edit-button">Editar Propiedad</button>
          <button class="contact-seller-button" @click="toggleAgenda">Horarios de visita</button>
        </div>
      </div>
    </div>

    <div v-if="showAgenda">
      <Agenda />
    </div>

    <footer>
      <p class="copyright">© 2024 HomeSphere Todos los derechos reservados. Prohibida su reproducción total o parcial por cualquier medio</p>
    </footer>
  </div>
</template>

<script>
import { ref, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import pordefectoCasa from "../components/images/casa.jpeg";
import pordefectoDepartamento from "../components/images/departamento.jpeg"; 
import Agenda from '../components/agenda.vue'

export default {
  name: 'Propiedad',
  components: {
    Agenda
  },
  setup() {
    const router = useRouter();
    const property = ref(null);
    const imagen = ref('');
    const isAuthenticated = ref(false);
    const showEditButtons = ref(false); 
    const isPropertyOwner = ref(false);
    const showAgenda = ref(false); 

    const toggleEditButtons = () => {
      showEditButtons.value = !showEditButtons.value;
    };

    const navigateTo = (route) => {
      router.push(route);
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
      fetchPropertyData();
      checkPropertyOwnership();
    });

    watch(() => router.currentRoute.value, () => {
      checkAuth();
      checkPropertyOwnership();
    });

    const checkPropertyOwnership = async () => {
      const userId = localStorage.getItem('usuario');
      const propertyId = localStorage.getItem('selectedPropertyId');
      if (userId && propertyId) {
        try {
          const response = await axios.get(`http://localhost:8080/users/userOwnProperty/${userId}/${propertyId}`);
          isPropertyOwner.value = response.data;
        } catch (error) {
          console.error('Error checking property ownership:', error);
          isPropertyOwner.value = false;
        }
      }
    };

    const fetchPropertyData = async () => {
      const propertyId = localStorage.getItem('selectedPropertyId');
      if (propertyId) {
        try {
          const response = await axios.get(`http://localhost:8080/property/findById/${propertyId}`);
          property.value = response.data;

          if (property.value.propertyType.toLowerCase() === 'casa') {
            imagen.value = pordefectoCasa;
          } else if (property.value.propertyType.toLowerCase() === 'departamento') {
            imagen.value = pordefectoDepartamento;
          } else {
            imagen.value = pordefectoCasa;
          }
        } catch (error) {
          console.error('Error fetching property data:', error);
        }
      }
    };
    
    const navigateToEdit = (edition) => {
      if (edition === '/actualizarComuna') localStorage.setItem('editionProperty', 1);
      if (edition === '/actualizarTipo') localStorage.setItem('editionProperty', 2);
      if (edition === '/actualizarMetros') localStorage.setItem('editionProperty', 3);
      if (edition === '/actualizarDormitorios') localStorage.setItem('editionProperty', 4);
      if (edition === '/actualizarBaños') localStorage.setItem('editionProperty', 5);
      if (edition === '/actualizarPrecio') localStorage.setItem('editionProperty', 6);
      if (edition === '/actualizarDescripcion') localStorage.setItem('editionProperty', 7);
      router.push('/actualizaDatosPropiedad');
    };

    const toggleAgenda = () => {
      showAgenda.value = !showAgenda.value;
    };

    const newEvent = ref({
      title: '',
      date: '',
      time: ''
    });

    const addEvent = () => {
      const event = {
        title: newEvent.value.title,
        start: `${newEvent.value.date}T${newEvent.value.time}`
      };
      console.log('Nuevo evento agregado:', event);
      newEvent.value.title = '';
      newEvent.value.date = '';
      newEvent.value.time = '';
    };

    return {
      navigateTo,
      toggleEditButtons,
      navigateToEdit,
      property,
      isAuthenticated,
      imagen,
      showEditButtons,
      isPropertyOwner,
      logout,
      toggleAgenda, 
      showAgenda,  
      addEvent,
      newEvent
    };
  }
};
</script>



<style scoped>
*{
  font-family: 'Poppins';
}
header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
  background-color: #4ca771;
  color: white;
  text-decoration: none
}
header .logo-section img {
  width: 100px;
  height: 80px;
  border-radius: 10px;
}

#app {
  background-color: #eaf3e6; 
  min-height: 100vh; 
}


nav ul {
  display: flex;
  align-items: center;
  list-style: none;
  padding: 0;
  margin: 0;
  text-decoration: none
}

nav ul li {
  margin-right: 20px;
  text-decoration: none
}

nav ul li a {
  color: #013237;
  text-decoration: none;
  font-size: 18px;
}

nav ul li a:hover {
  text-decoration: underline;
}
.buttonsNonUser,.publish-button,.logout-button {
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
.buttonsNonUser:hover,.publish-button:hover,.logout-button:hover {
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
.publish-button{
  margin-right: 20px;
}
.user-info {
  display: flex;
  justify-content: space-evenly;
  margin: 30px 20px;
  padding: 20px;
  background-color: #EAF9E7;
  border-radius: 15px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.user-photo {
  display: flex;
  justify-content: center;
  align-items: center;
}

.user-image {
  width: 400px;
  height: 300px;
  border-radius: 30px;
}

.comunaProp, .tipoProp, .m2Prop, .dormProp, .banioProp, .precioProp, .descipProp {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  text-align: left;
  margin-bottom: 15px;
}

.user-details {
  display: flex;
  text-align: justify;
  flex-direction: column;
  text-align: center;
}

p {
  font-size: 22px;
  margin: 10px 0;
  color: #555;
}

.user-details h1 {
  font-size: 22px;
  margin: 10px 0;
  font-weight: 800;
  margin-left: 10px;
}

.contact-seller-button,.edit-button {
  background-color: #4ca771;
  color: #013237;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}
.contact-seller-button:hover,.edit-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 8px rgba(0, 0, 0, 0.1);
}
.buttonEditContact{
  display: flex;
  gap :10px;

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
.editButton{
  width: 20px;
  height: 20px;
  cursor: pointer;
}
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.8s, transform 0.8s;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
  transform: scale(0.9);
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
  color: white;
  font-size: 14px;
  text-align: left;

}
</style>
