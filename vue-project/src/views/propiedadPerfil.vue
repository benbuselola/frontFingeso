<template>
  <div>
    <header class="header">
      <div class="logo-section">
        <img src="../components/images/image.png" alt="Nombre de la página" height="60">
      </div>
      <nav class="nav">
        <ul class="nav-list">
          <li><router-link to="/principal" class="nav-link">Inicio</router-link></li>
          <li><router-link to="/soporte" class="nav-link">Ayuda</router-link></li>
          <li v-if="isAuthenticated">
            <button class="publish-button" @click="navigateTo('/publicarPropiedad')">Publica tu propiedad</button>
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
          <button v-if="showEditButtons" @click="navigateToEdit('/actualizarComuna')">Editar</button>
        </div>
        <div class="tipoProp">
          <p>Tipo de propiedad: </p>
          <h1>{{ property.propertyType }}</h1>
          <button v-if="showEditButtons" @click="navigateToEdit('/actualizarTipo')">Editar</button>
        </div>
        <div class="m2Prop">
          <p>Metros cuadrados: </p>
          <h1>{{ property.size }} M²</h1>
          <button v-if="showEditButtons" @click="navigateToEdit('/actualizarMetros')">Editar</button>
        </div>
        <div class="dormProp">
          <p>Dormitorios: </p>
          <h1>{{ property.bedrooms }} Dormitorios</h1>
          <button v-if="showEditButtons" @click="navigateToEdit('/actualizarDormitorios')">Editar</button>
        </div>
        <div class="banioProp">
          <p>Baños: </p>
          <h1>{{ property.bathrooms }} Baños</h1>
          <button v-if="showEditButtons" @click="navigateToEdit('/actualizarBaños')">Editar</button>
        </div>
        <div class="precioProp">
          <p>Precio: </p>
          <h1>{{ property.value }} UF</h1>
          <button v-if="showEditButtons" @click="navigateToEdit('/actualizarPrecio')">Editar</button>
        </div>
        <div class="descipProp">
          <p>Descripción: </p>
          <h1>{{ property.description }}</h1>
          <button v-if="showEditButtons" @click="navigateToEdit('/actualizarDescripcion')">Editar</button>
        </div>
        <button @click="toggleEditButtons" class="edit-button">Editar Propiedad</button>
        <a :href="'mailto:' + property.email">
          <button class="contact-seller-button">Contactar vendedor!</button>
        </a>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import pordefectoCasa from "../components/images/casa.jpeg";
import pordefectoDepartamento from "../components/images/departamento.jpeg";

export default {
  name: 'Propiedad',
  setup() {
    const router = useRouter();
    const property = ref(null);
    const imagen = ref('');
    const isAuthenticated = ref(false);
    const showEditButtons = ref(false); 

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
    });

    watch(() => router.currentRoute.value, () => {
      checkAuth();
    });

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

    return {
      navigateTo,
      toggleEditButtons,
      navigateToEdit,
      property,
      isAuthenticated,
      imagen,
      showEditButtons
    };
  }
};
</script>


<style scoped>
*{
  font-family: 'Poppins';
}
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 5px 15px;
  background-color: #4CAF50;
  color: white;
}

.logo-section img {
  cursor: pointer;
  width: 100px;
  height: 60px;
  border-radius: 10px;
}

.nav ul {
  display: flex;
  align-items: center;
  list-style: none;
  padding: 0;
  margin: 0;
}

.nav ul li {
  margin-right: 20px;
}

.nav ul li a {
  color: white;
  text-decoration: none;
  font-size: 18px;
}

.nav ul li a:hover {
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
  width: 400px;
  height: 300px;
  border-radius: 30px;
}

.comunaProp, .tipoProp, .m2Prop, .dormProp, .banioProp, .precioProp, .descipProp {
  display: flex;
  flex-direction: row;
  justify-content: center;
  text-align: start;
}

.user-details {
  display: flex;
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

.contact-seller-button {
  background-color: #4CAF50;
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  margin-top: 20px;
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
