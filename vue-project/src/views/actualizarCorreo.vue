<template>
  <header>
    <div class="logo-section">
      <img src="../components/images/logo.jpeg" alt="Nombre de la página" height="60">
    </div>
    <nav>
      <ul>
        <li><a @click="navigateTo('/principal')">Inicio</a></li>
        <li><a @click="navigateTo('/soporte')">Ayuda</a></li>
      </ul>
    </nav>
  </header>

  <div class="change-email">
    <h2>Cambia tu correo</h2>
    <form @submit.prevent="registroProp">
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
</template>

<script>
import { useRouter } from 'vue-router'
import { ref } from 'vue'
import axios from 'axios'

export default {
  setup() {
    const router = useRouter()
    const correoantiguo = ref('')
    const correonuevo = ref('')
    const message = ref('')

    const registroProp = async () => {
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
        
        if (response.data === true) {
          message.value = 'Correo actualizado'
        } else {
          message.value = 'Correo no actualizado'
        }
      } catch (error) {
        console.error(error)
        message.value = 'Error al actualizar el correo'
      }
    }

    const navigateTo = (route) => {
      router.push(route)
    }

    return {
      registroProp,
      navigateTo,
      correoantiguo,
      correonuevo,
      message
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

.change-email {
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
  color: #333;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
}

.form-group input {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 20px;
}

.submit-button {
  width: 100%;
  padding: 12px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  margin-top: 20px;
}

.submit-button:hover {
  background-color: #45a049;
}

.message {
  margin-top: 15px;
  text-align: center;
  color: #4CAF50;
  font-weight: bold;
}
</style>
