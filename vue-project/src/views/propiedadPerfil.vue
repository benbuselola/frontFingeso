<template>
  <header class="header">
    <div class="logo-section">
        <img src="../components/images/image.png" alt="Nombre de la página" height="60">
    </div>
    <nav class="nav">
      <ul class="nav-list">
        <li><router-link to="/principal" class="nav-link">Inicio</router-link></li>
        <li><router-link to="/soporte" class="nav-link">Ayuda</router-link></li>
        <li><button class="publish-button" @click="navigateTo('/publicarPropiedad')">Publica tu propiedad</button></li>
      </ul>
    </nav>
  </header>

  <div class="user-info" v-if="property">
    <div class="user-photo"> 
      <img :src="imagen" alt="Imagen de perfil" class="user-image">
    </div>
    <div class="user-details">
      <div class="nombreProp">
        <p>Nombre: </p>
        <h1>{{ property.nombre }}</h1>
      </div>
      <div class="direcProp">
        <p>Dirección: </p>
        <h1>{{ property.direccion }}</h1>
      </div>
      <div class="comunaProp">
        <p>Comuna: </p>
        <h1>{{ property.comuna }}</h1>
      </div>
      <div class="tipoProp">
        <p>Tipo de propiedad: </p>
        <h1>{{ property.tipo_propiedad }}</h1>
      </div>
      <div class="m2Prop">
        <p>Metros cuadrados: </p>
        <h1>{{ property.metros_cuadrados }} M²</h1>
      </div>
      <div class="dormProp">
        <p>Dormitorios: </p>
        <h1>{{ property.dormitorios }} Dormitorios</h1>
      </div>
      <div class="banioProp">
        <p>Baños: </p>
        <h1>{{ property.banios }} Baños</h1>
      </div>
      <div class="precioProp">
        <p>Precio: </p>
        <h1>{{ property.precio }} UF</h1>
      </div>
      
      <a :href="'mailto:' + property.email">
        <button class="contact-seller-button">Contactar vendedor!</button>
      </a>
    </div>
  </div>

  <div class="property-list">
    <!-- Aquí puedes mostrar otras propiedades relacionadas si lo deseas -->
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import pordefectoCasa from "../components/images/casa.jpeg";
import pordefectoDepartamento from "../components/images/departamento.jpeg";

export default {
  name: 'Propiedad',
  props: {
    propiedad: {
      type: Object,
      required: true
    }
  },
  setup() {
    const router = useRouter();
    const property = ref(null);
    const imagen = ref('');  // Variable para almacenar la imagen por defecto

    const navigateTo = (route) => {
      router.push(route);
    };

    const fetchPropertyData = async () => {
      const propertyId = localStorage.getItem('propiedadId');
      if (propertyId) {
        try {
          const response = await axios.get(`http://localhost:8080/users/obtainProperties/${propertyId}`);
          property.value = response.data;

          // Asignar imagen predeterminada en función del tipo de propiedad
          if(property.value.tipo_propiedad.toLowerCase() === 'casa') {
            imagen.value = pordefectoCasa;
          } else if(property.value.tipo_propiedad.toLowerCase() === 'departamento') {
            imagen.value = pordefectoDepartamento;
          } else {
            // Imagen por defecto si el tipo no es reconocido
            imagen.value = pordefectoCasa;
          }
        } catch (error) {
          console.error('Error fetching property data:', error);
        }
      }
    };

    onMounted(() => {
      fetchPropertyData();
    });

    return {
      navigateTo,
      property,
      imagen  // Retornar la variable imagen
    };
  }
};
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
  cursor: pointer;
  width: 100px;
  height: 60px;
  border-radius: 10px;
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
  width: 400px;
  height: 300px;
  border-radius: 30px;
}
.nombreProp, .direcProp, .comunaProp, .tipoProp, .m2Prop, .dormProp, .banioProp, .precioProp {
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
p{
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
