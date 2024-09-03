<template>
  <div id="app">
  <div>
    <header class="header">
      <div class="logo-section">
          <img src="../components/images/logoOficial.jpeg" alt="Nombre de la página" height="60">
      </div>
      <nav class="nav">
        <ul class="nav-list">
          <a class = "principal-button" @click="navigateTologo('/principal')">Inicio</a>
          <a class="help-button" @click="navigateTologo('/soporte')">Ayuda</a>
          <button class="publish-button" @click="navigateTologo('/publicarPropiedad')">Publica tu propiedad</button>
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

    <div class="action-buttons">
      <button class="view-properties" :class="{ active: currentSection === 'properties' }" @click="showSection('properties')">Ver propiedades</button>
      <button class="view-likes" :class="{ active: currentSection === 'likes' }" @click="showSection('likes')">Ver me gusta</button>
    </div>

    <div v-if="currentSection === 'properties'" class="property-list">
      <Propiedad v-for="propiedad in properties" :key="propiedad.id" :propiedad="propiedad" />
    </div>

    <div v-if="currentSection === 'likes'" class="likes-list">
      <Propiedad v-for="like in likes" :key="like.id" :propiedad="like" />
    </div>
  </div>
  
  <footer class>
      <p class = "copyright">© 2024 HomeSphere Todos los derechos reservados. Prohibida su reproducción total o parcial por cualquier medio</p>
    </footer>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
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
    const likes = ref([])
    const currentSection = ref('properties')

    onMounted(async () => {
      checkAuth();
      try {
        const userId = localStorage.getItem('usuario');
        const userResponse = await axios.get(`http://localhost:8080/users/findbyID/${userId}`);
        const userData = userResponse.data;

        userFullName.value = `${userData.firstName} ${userData.secondName} ${userData.lastName} ${userData.secondLastName}`;
        userEmail.value = userData.email;
        userPhone.value = userData.number;

        const propertiesResponse = await axios.get(`http://localhost:8080/users/getPropertiesbyUser/${userId}`);
        properties.value = propertiesResponse.data;

        const likesResponse = await axios.get(`http://localhost:8080/users/showLikedProperties/${userId}`);
        likes.value = likesResponse.data;
        
      } catch (error) {
        console.error('Error al obtener la información del usuario:', error);
      }
    });

    const checkAuth = () => {
      const userId = localStorage.getItem('usuario')
      if (!userId) {
        router.push('/login')
      }
    }

    const navigateTo = (route) => {
      if(route === '/actualizaCorreo') localStorage.setItem('editionUser', 1)
      if(route === '/actualizaContrasena') localStorage.setItem('editionUser', 2)
      if(route === '/cambiarNumero') localStorage.setItem('editionUser', 3)
      router.push('/actualizaDatosPerfil')
    }

    const showSection = (section) => {
      currentSection.value = section;
    }

    const navigateTologo = (route) => {
      router.push(route)
    }
    return {
      navigateTo,
      showSection,
      userFullName,
      userEmail,
      userPhone,
      properties,
      likes,
      currentSection,
      navigateTologo
    }
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

header {
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
.help-button, .publish-button,.principal-button {
  background-color: #EAF9E7;
  color: #013237;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  margin-right: 10px;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}
.help-button:hover, .publish-button:hover, .principal-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 8px rgba(0, 0, 0, 0.1);
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
  height: 400px;
  border-radius: 70%;
  border: 2px solid #4CAF50;
}

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

.property-list, .likes-list {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
  margin: 20px 0;
}

.property-list > * , .likes-list > * {
  margin: 10px;
  flex-basis: calc(25% - 20px);
  box-sizing: border-box;
}


.userUpdateButtons{
  list-style-type: none;
}

.update-email, .update-number {
  background-color: #4ca771;
  color: #013237;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  margin: 10px 0;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.update-password{
  background-color: #4ca771;
  color: #013237;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  margin: 10px 0;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}
.update-email:hover, .update-number:hover, .update-password:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 8px rgba(0, 0, 0, 0.1);
}
.action-buttons {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
}
.view-properties, .view-likes {
  width: 300px;
  background-color: #4ca771;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  font-size: 24px;
  color: black;
  border-radius: 5px;
  outline: none;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}
.view-likes:hover, .view-properties:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 8px rgba(0, 0, 0, 0.1);
}
.view-properties.active, .view-likes.active {
  transition: background-color 0.8s ease, color 0.5s ease;
  background-color: #013237; 
  color: white; 
}
footer {
  display: flex;
  padding: 10px 20px;
  background-color: #4CA771;
  color: white;
}

.copyrigth {
  font-size: 14px;
  text-align: left;

}
</style>
