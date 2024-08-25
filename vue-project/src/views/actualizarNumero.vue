<template>
  <div>
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

    <div class="change-number">
      <h2>Cambia tu número telefónico</h2>
      <form @submit.prevent="registroProp">
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
</template>

<script>
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import axios from 'axios';

export default {
  setup() {
    const router = useRouter();
    const numeroantiguo = ref('');
    const numeronuevo = ref('');
    const message = ref('');

    const registroProp = async () => {
      try {
        const userid = localStorage.getItem('usuario');
        if (!userid) {
          message.value = 'Usuario no encontrado';
          return;
        }

        const userResponse = await axios.get(`http://localhost:8080/users/findbyID/${userid}`);
        const user = userResponse.data;

        if (user.number !== numeroantiguo.value) {
          message.value = 'Número actual incorrecto';
          return;
        }

        const response = await axios.post(`http://localhost:8080/users/updateNumber/${userid}/${numeronuevo.value}`);
        if (response.data) {
          message.value = 'Número actualizado correctamente';
        } else {
          message.value = 'Error al actualizar el número';
        }
      } catch (error) {
        message.value = 'Error al actualizar el número';
      }
    };

    const navigateTo = (route) => {
      router.push(route);
    };

    return {
      navigateTo,
      registroProp,
      message,
      numeroantiguo,
      numeronuevo
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


.change-number {
  max-width: 400px;
  margin: 50px auto;
  padding: 40px;
  border: 2px solid #ccc;
  border-radius: 15px;
  background-color: #f9f9f9;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 15px;
}

.form-group input {
  margin-top: 10px;
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
  color: #d9534f;
  margin-top: 10px;
}
</style>
