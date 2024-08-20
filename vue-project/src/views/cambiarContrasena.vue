<template>
  <header>
    <div class="logo-section">
      <img src="../components/images/image.png" alt="Nombre de la página" height="60">
    </div>
    <nav>
      <ul>
        <li><a @click="navigateTo('/principal')">Inicio</a></li>
        <li><a @click="navigateTo('/soporte')">Ayuda</a></li>
      </ul>
    </nav>
  </header>

  <div class="change-password">
    <h2>Cambia tu contraseña</h2>
    <form @submit.prevent="registroProp">
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
</template>

<script>
import { useRouter } from 'vue-router'
import { ref } from 'vue'
import axios from 'axios'

export default {
  setup() {
    const router = useRouter()
    const contrasenaantigua = ref('')
    const contrasenanueva = ref('')
    const contrasenaconfirmada = ref('')
    const message = ref('')

    const registroProp = async () => {
  try {
    const userid = localStorage.getItem('usuario')
    if (!userid) {
      message.value = 'Usuario no encontrado'
      return
    }

    // Verificar la contraseña actual (opcional dependiendo de cómo esté implementado el backend)
    const userResponse = await axios.get(`http://localhost:8080/users/findbyID/${userid}`)
    const user = userResponse.data
    
    // Verifica la contraseña actual (debes ajustar esto dependiendo de cómo el backend gestiona esto)
    if (contrasenaantigua.value !== user.password) {
      message.value = 'Contraseña actual incorrecta'
      return 
    }

    if (contrasenanueva.value !== contrasenaconfirmada.value) {
      message.value = 'Las contraseñas no coinciden'
      return
    }

    // Actualizar la contraseña, pasando la contraseña nueva como parte de la URL
    const updateResponse = await axios.post(`http://localhost:8080/users/updatePassword/${userid}/${contrasenanueva.value}`)
    
    if (updateResponse.data === true) {
      message.value = 'Contraseña actualizada correctamente'
    } else {
      message.value = 'Error al actualizar la contraseña'
    }
  } catch (error) {
    console.error('Error al actualizar la contraseña:', error)
    message.value = 'Error al actualizar la contraseña. Por favor, intente de nuevo.'
  }
}
    
    const navigateTo = (route) => {
      router.push(route)
    }

    return { navigateTo, registroProp, message, contrasenaantigua, contrasenanueva, contrasenaconfirmada }
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

.change-password {
  max-width: 400px;
  margin: 50px auto;
  padding: 40px;
  border: 2px solid #ccc;
  border-radius: 15px;
  background-color: #f9f9f9;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
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
  margin-top: 20px;
  color: #d9534f;
}
</style>
