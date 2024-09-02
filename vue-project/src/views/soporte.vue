<template>
    <header>
      <div class="logo-section">
        <img src="../components/images/image.png" alt="Nombre de la página" height="60">
      </div>
      <nav>
        <ul>
          <li class="buttonsNonUser"><a @click="navigateTo('/principal')">Inicio</a></li>
        </ul>
      </nav>
    </header>
  
    <div class="soporte-container">
      <h2>Soporte</h2>
      <form @submit.prevent="soporteForm">
        <div class="form-group">
          <label for="tipo_peticion">Tipo de Petición</label>
          <select id="tipo_peticion" v-model="tipo_peticion" required>
            <option value="" disabled selected>Seleccione el tipo</option>
            <option value="recuperacionC">Recuperar Contraseña</option>
            <option value="eliminarC">Eliminar Cuenta</option>
            <option value="solicitud">Realizar una solicitud</option>
            <option value="otro">Otro</option>
          </select>
        </div>
        <div class="form-group">
          <label for="descripcion">Descripción</label>
          <textarea id="descripcion" v-model="descripcion" placeholder="Describa la solicitud" required></textarea>
        </div>
        <button @click="navigateTo('/principal')" type="submit" class="submit-button">Enviar Solicitud</button>
        <p v-if="message" class="message">{{ message }}</p>
      </form>
    </div>
  </template>
  
  <script>
  import axios from 'axios'
  import { ref } from 'vue'
  import { useRouter } from 'vue-router'
  
  export default {
    setup() {
      const tipo_peticion = ref('')
      const descripcion = ref('')
      const message = ref('')
      const router = useRouter()
  
      const soporteForm = async () => {
        try {
          const response = await axios.post('http://localhost:8080/api/soporte', {
            tipo_peticion: tipo_peticion.value,
            descripcion: descripcion.value
          })
          if (response.data) {
            message.value = 'Solicitud enviada con éxito.'
            router.push('/principal')
          }
        } catch (error) {
          message.value = 'Error al enviar la solicitud.'
        }
      }
  
      const navigateTo = (route) => {
        router.push(route)
      }
  
      return {
        tipo_peticion,
        descripcion,
        message,
        soporteForm,
        navigateTo
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
  background-color: #C0E6BA;
  color: white;
  text-decoration: none
}
header .logo-section img {
  width: 100px;
  height: 60px;
  border-radius: 10px;
  text-decoration: none
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

  .soporte-container {
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
  
  .form-group {
    margin-bottom: 20px;
  }
  .form-group label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
    color: #555;
  }
  
  .form-group select,
  .form-group textarea {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 10px;
    box-sizing: border-box;
    font-size: 16px;
  }
  
  textarea {
    resize: vertical;
    height: 100px;
  }
  
  .submit-button {
    width: 100%;
    padding: 10px;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 10px;
    cursor: pointer;
    font-size: 18px;
  }
  
  .submit-button:hover {
    background-color: #45a049;
  }
  
  .message {
    margin-top: 20px;
    text-align: center;
    color: #4CAF50;
    font-weight: bold;
  }
  </style>
  