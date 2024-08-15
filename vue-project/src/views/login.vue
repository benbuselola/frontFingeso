<template>
  <header>
    <div class="logo-section">
      <a @click="navigateTo('/principal')">
        <img src="../components/images/logo.jpeg" alt="Nombre de la página" height="60">
      </a>
    </div>
    <nav>
      <ul>
        <li><a @click="navigateTo('/principal')">Inicio</a></li>
        <li><a @click="navigateTo('/soporte')">Ayuda</a></li>
      </ul>
    </nav>
  </header>
  <div class="login-container">
    <form @submit.prevent="login">
      <h2>Iniciar sesión</h2>
      <div>
        <label for="correo">Correo del usuario:</label>
        <input v-model="correo" id="correo" type="text" required>
      </div>
      <div>
        <label for="password">Contraseña:</label>
        <input v-model="password" id="password" type="password" required>
      </div>
      <div class="botonesLogReg">
      <button class="botonLog"type="submit">Ingresar</button>
      <p v-if="message">{{ message }}</p>
      <button class="botonReg" @click="goToRegister">Registrarse</button>
      </div>
    </form>
  </div>
</template>


<script>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

export default {
  setup() {
    const correo = ref('')
    const password = ref('')
    const message = ref('')
    const router = useRouter()

    const goToRegister = () => {
      router.push('/registro')
    }

    const navigateTo = (route) => {
      router.push(route)
    }

    const login = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/buscar/${correo.value}/${password.value}`)
        if (response.data) {
          message.value = 'El usuario se encuentra registrado.'
          // Redirigir al usuario a la página principal
          router.push('/principal')
        } else {
          message.value = 'Usuario no encontrado o credenciales incorrectas.'
        }
      } catch (error) {
        console.error('Error al verificar el usuario:', error)
        message.value = 'Error al intentar iniciar sesión. Por favor, intente de nuevo.'
      }
    }

    return {
      correo,
      password,
      message,
      login,
      goToRegister,
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
.login-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 40px;
  border: 2px solid #ccc;
  border-radius: 15px;
  background-color: #f9f9f9;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
}

form div {
  margin-bottom: 10px;
}

label {
  display: block;
  margin-bottom: 5px;
}

input {
  width: calc(100% - 20px);
  padding: 8px 10px;
  border: 1px solid #ccc;
  border-radius: 50px;
  box-sizing: border-box;
}

button {
  width: 100%;
  padding: 8px;
  background-color: #00a6ff;
  color: white;
  border: none;
  border-radius: 30px;
  cursor: pointer;
  margin-top: 10px;
}

button:hover {
  background-color: #388e9b;
}

button + button {
  margin-top: 10px;
  background-color: #2196F3;
}

button + button:hover {
  background-color: #0b7dda;
}
.botonLog{
  margin-right: 10px;
  background-color: rgb(35, 139, 35);
  color: white;
}

.botonReg{
  background-color: rgb(35, 35, 139);
  color: white;

}
.botonesLogReg{
  display: flex;
  justify-content: space-between;
}
</style>

