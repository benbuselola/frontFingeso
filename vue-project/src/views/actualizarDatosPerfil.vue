<template>
  <div id="app">
  <header>
    <div class="logo-section">
      <img src="../components/images/logoOficial.jpeg" alt="Nombre de la página" height="60">
    </div>
    <nav>
      <ul>
        <li><a class = "principal-button" @click="navigateTo('/principal')">Inicio</a></li>
        <li><a class ="support-button" @click="navigateTo('/soporte')">Ayuda</a></li>
      </ul>
    </nav>
  </header>

  <div class="form-container">
    <div v-if="editionUser === 1" class="change-email">
      <h2>Cambia tu correo</h2>
      <form @submit.prevent="updateEmail">
        <div class="form-group">
          <label for="correo-actual">Ingrese su correo actual</label>
          <input type="text" id="correo-actual" v-model="correoantiguo" placeholder="Correo actual" required>
        </div>
        <div class="form-group">
          <label for="nuevo-correo">Ingrese su nuevo correo</label>
          <input type="text" id="nuevo-correo" v-model="correonuevo" placeholder="Nuevo Correo" required>
        </div>
        <button type="submit" class="submit-button">Cambiar correo</button>
        <p v-if="message" class="message">{{ message }}</p>
      </form>
    </div>

    <div v-if="editionUser === 2" class="change-password">
      <h2>Cambia tu contraseña</h2>
      <form @submit.prevent="updatePassword">
        <div class="form-group">
          <label for="contrasena-actual">Ingrese su contraseña actual</label>
          <input type="password" id="contrasena-actual" v-model="contrasenaantigua" placeholder="Contraseña actual" required>
        </div>
        <div class="form-group">
          <label for="nueva-contraseña">Ingrese su nueva contraseña</label>
          <input type="password" id="nueva-contraseña" v-model="contrasenanueva" placeholder="Nueva Contraseña" required>
        </div>
        <div class="form-group">
          <label for="confirmar-contraseña">Confirme su nueva contraseña</label>
          <input type="password" id="confirmar-contraseña" v-model="contrasenaconfirmada" placeholder="Confirme su nueva contraseña" required>
        </div>
        <button type="submit" class="submit-button">Cambiar contraseña</button>
        <p v-if="message" class="message">{{ message }}</p>
      </form>
    </div>

    <div v-if="editionUser === 3" class="change-number">
      <h2>Cambia tu número telefónico</h2>
      <form @submit.prevent="updateNumber">
        <div class="form-group">
          <label for="numeroActual">Ingrese su número actual</label>
          <input type="text" id="numeroActual" v-model="numeroantiguo" placeholder="Número actual" required>
        </div>
        <div class="form-group">
          <label for="numeroNuevo">Ingrese su nuevo número</label>
          <input type="text" id="numeroNuevo" v-model="numeronuevo" placeholder="Nuevo número" required>
        </div>
        <button type="submit" class="submit-button">Cambiar número</button>
        <p v-if="message" class="message">{{ message }}</p>
      </form>
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

export default {
  setup() {
    const router = useRouter()
    const correoantiguo = ref('')
    const correonuevo = ref('')
    const contrasenaantigua = ref('')
    const contrasenanueva = ref('')
    const contrasenaconfirmada = ref('')
    const numeroantiguo = ref('')
    const numeronuevo = ref('')
    const message = ref('')
    const editionUser = ref(1) // Valor predeterminado

    // Función para recuperar editionUser de localStorage
    const loadEditionUser = () => {
      checkAuth();
      const storedEditionUser = localStorage.getItem('editionUser')
      editionUser.value = storedEditionUser ? parseInt(storedEditionUser, 10) : 1
    }

    onMounted(() => {
      loadEditionUser()
    })

    const updateEmail = async () => {
      try {
        const userid = localStorage.getItem('usuario')
        if (!userid) {
          message.value = 'Usuario no encontrado'
          return
        }

        const userResponse = await axios.get(`http://localhost:8080/users/findbyID/${userid}`)
        const user = userResponse.data

        if (user.email !== correoantiguo.value) {
          message.value = 'Correo actual incorrecto'
          return
        }

        const response = await axios.post(`http://localhost:8080/users/updateEmail/${userid}/${correonuevo.value}`)
        
        message.value = response.data ? 'Correo actualizado' : 'Correo no actualizado'
      } catch (error) {
        console.error(error)
        message.value = 'Error al actualizar el correo'
      }
    }

    const updatePassword = async () => {
      try {
        const userid = localStorage.getItem('usuario')
        if (!userid) {
          message.value = 'Usuario no encontrado'
          return
        }

        const userResponse = await axios.get(`http://localhost:8080/users/findbyID/${userid}`)
        const user = userResponse.data

        if (user.password !== contrasenaantigua.value) {
          message.value = 'Contraseña actual incorrecta'
          return
        }

        if (contrasenanueva.value !== contrasenaconfirmada.value) {
          message.value = 'Las contraseñas no coinciden'
          return
        }

        const response = await axios.post(`http://localhost:8080/users/updatePassword/${userid}/${contrasenanueva.value}`)
        
        message.value = response.data ? 'Contraseña actualizada' : 'Contraseña no actualizada'
      } catch (error) {
        console.error(error)
        message.value = 'Error al actualizar la contraseña'
      }
    }

    const updateNumber = async () => {
      try {
        const userid = localStorage.getItem('usuario')
        if (!userid) {
          message.value = 'Usuario no encontrado'
          return
        }

        const response = await axios.post(`http://localhost:8080/users/updateNumber/${userid}/${numeronuevo.value}`)
        
        message.value = response.data ? 'Número actualizado' : 'Número no actualizado'
      } catch (error) {
        console.error(error)
        message.value = 'Error al actualizar el número'
      }
    }

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
      checkAuth,
      updateEmail,
      updatePassword,
      updateNumber,
      navigateTo,
      correoantiguo,
      correonuevo,
      contrasenaantigua,
      contrasenanueva,
      contrasenaconfirmada,
      numeroantiguo,
      numeronuevo,
      message,
      editionUser
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

.principal-button {
  background-color: #EAF9E7;
  color: black;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

.support-button {
  background-color: #EAF9E7;
  color: black;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
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
.buttonsNonUser {
  background-color: #4CA771;
  color: #013237;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}
.buttonsNonUser:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 8px rgba(0, 0, 0, 0.1);
}
.form-container {
  max-width: 600px;
  margin: 50px auto;
}

.change-email, .change-password, .change-number {
  padding: 20px;
  border: 2px solid #ccc;
  border-radius: 10px;
  background-color: #EAF9E7;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

h2 {
  text-align: center;
  color: #333;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.form-group input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.submit-button {
  display: block;
  width: 100%;
  padding: 10px;
  background-color: #4ca771;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
}

.submit-button:hover {
  background-color: #45a049;
}

.message {
  margin-top: 15px;
  color: red;
  text-align: center;
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
