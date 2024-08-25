<template>
  <div>
    <header class="header">
      <div class="logo-section">
        <a @click="navigateTo('/principal')">
          <img src="../components/images/image.png" alt="Nombre de la página" height="60">
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
        <img class="user-image" src="../components/images/descarga.png" alt="Imagen de perfil">
      </div>
      <div class="user-details">
        <div class="userName">
          <p>Nombre completo:</p>
          <h1>{{ userFullName }}</h1>
        </div>
        <div class="userMail">
          <p>Correo electrónico:</p>
          <h1>{{ userEmail }}</h1>
        </div>
        <div class="userPhone">
          <p>Teléfono:</p>
          <h1>{{ userPhone }}</h1>
        </div>
        <div class="userUpdateButtons">
          <button class="update-email" @click="navigateTo('/actualizaCorreo')">Actualiza tu correo</button>
          <button class="update-password" @click="navigateTo('/actualizaContrasena')">Cambia tu contraseña</button>
          <button class="update-number" @click="navigateTo('/cambiarNumero')">Actualiza tu número</button>
        </div>

      </div>
    </div>

    <div class="property-list">
      <Propiedad v-for="propiedad in properties" :key="propiedad.id" :propiedad="propiedad" />
    </div>
  </div>
</template>

<script>
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import Propiedad from '../components/propiedad.vue';

export default {
  name: 'PropertyList',
  components: {
    Propiedad
  },
  setup() {
    const router = useRouter()
    const userFullName = ref('')
    const userEmail = ref('')
    const userPhone = ref('')
    const properties = ref([])

    onMounted(async () => {
      checkAuth();
      try {
        const userId = localStorage.getItem('usuario');
        const response = await axios.get(`http://localhost:8080/users/findbyID/${userId}`);
        const userData = response.data;

        userFullName.value = `${userData.firstName} ${userData.secondName} ${userData.lastName} ${userData.secondLastName}`;
        userEmail.value = userData.email;
        userPhone.value = userData.number;

        const propertiesResponse = await axios.get(`http://localhost:8080/users/getPropertiesbyUser/${userId}`);
        
        console.log('Respuesta de propiedades:', propertiesResponse.data);

        properties.value = propertiesResponse.data;
        
      } catch (error) {
        console.error('Error al obtener la información del usuario:', error);
      }
    });

    watch(() => router.currentRoute.value, () => {
      checkAuth()
    })

  const checkAuth = () => {
    const userId = localStorage.getItem('usuario')
    if (!userId) {
      router.push('/login')
    }
  }

    const navigateTo = (route) => {
      router.push(route)
    }

    return {
      navigateTo,
      userFullName,
      userEmail,
      userPhone,
      properties
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

.user-details h1 {
  font-size: 22px;
  margin: 10px 0;
  font-weight: 800;
  margin-left: 10px;
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
  border-radius: 70%;
  border: 2px solid #3483fa;}

.user-details {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.user-details h1{
  font-size: 22px;
  margin: 10px 0;
  color: black;
  font-weight: bolder;
}
.userUpdateButtons{
  display: flex;
  flex-direction: column;
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
.userUpdateButtons{
  list-style-type: none;
}

.update-email, .update-number {
  
  background-color: #3483fa;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  margin: 10px 0;
}
.update-password{
  background-color: beige;
  color: #3483fa;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  margin: 10px 0;
}
</style>
