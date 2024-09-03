<template>
  <div id="app">
  <header>
    <div class="logo-section">
      <a @click="navegarA('/principal')">
        <img src="../components/images/logoOficial.jpeg" alt="Nombre de la página" height="60">
      </a>
    </div>
    <nav>
      <ul>
        <a class = "principal-button" @click="navegarA('/principal')">Inicio</a>
        <a class = "support-button"@click="navegarA('/soporte')">Ayuda</a>
      </ul>
    </nav>
  </header>
  <div class="login-container">
    <form @submit.prevent="iniciarSesion">
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
        <button class="botonLog" type="submit">Ingresar</button>
        <button class="botonReg" @click="irARegistro">Registrarse</button>
      </div>
      <p v-if="mensaje">{{ mensaje }}</p>
    </form>
  </div>

  <footer class>
      <p class = "copyright">© 2024 HomeSphere Todos los derechos reservados. Prohibida su reproducción total o parcial por cualquier medio</p>
    </footer>
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
    const mensaje = ref('')
    const router = useRouter()

    const navegarA = (ruta) => {
      router.push(ruta)
    }

    const irARegistro = () => {
      router.push('/registro')
    }

    const iniciarSesion = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/users/login/${encodeURIComponent(correo.value)}/${encodeURIComponent(password.value)}`)
        if (response.data) {
          mensaje.value = 'El usuario se encuentra registrado.'
          const idResponse = await axios.get(`http://localhost:8080/users/findById/${encodeURIComponent(correo.value)}`)
          localStorage.setItem('usuario', idResponse.data)
          router.push('/principal')
        } else {
          mensaje.value = 'Usuario no encontrado o credenciales incorrectas.'
        }
      } catch (error) {
        console.error('Error al verificar el usuario:', error)
        mensaje.value = 'Error al intentar iniciar sesión. Por favor, intente de nuevo.'
      }
    }

    return {
      correo,
      password,
      mensaje,
      iniciarSesion,
      irARegistro,
      navegarA
    }
  }
}
</script>

<style scoped>
*{
  font-family: 'Poppins';
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

.principal-button, .support-button {
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
.principal-button:hover, .support-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 8px rgba(0, 0, 0, 0.1);
}
.login-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 40px;
  border: 2px solid #ccc;
  border-radius: 15px;
  background-color: #EAF9E7;
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

