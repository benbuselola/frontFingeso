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

  <div class="registro-container">
    <h2>Registro</h2>
    <form @submit.prevent="registerForm">
      <div class="form-group" v-for="(label, model) in formFields" :key="model">
        <label :for="model">{{ label }}</label>
        <input
          :type="inputType(model)"
          :id="model"
          v-model="formData[model]"
          :placeholder="label"
          required
        />
      </div>
      <button type="submit" class="submit-button">Registrarse</button>
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
    const formData = ref({
      firstName: '',
      secondName: '',
      lastName: '',
      secondLastName: '',
      rut: '',
      email: '',
      number: '',
      password: ''
    })

    const message = ref('')
    const router = useRouter()

    const formFields = {
      firstName: 'Nombre',
      secondName: 'Segundo Nombre',
      lastName: 'Apellido',
      secondLastName: 'Segundo Apellido',
      rut: 'RUT: (Ejemplo: 12345678-9)',
      email: 'Correo electrónico',
      number: 'Teléfono',
      password: 'Contraseña'
    }

    const inputType = (model) => {
      if (model === 'email') return 'email'
      if (model === 'number') return 'tel'
      if (model === 'password') return 'password'
      return 'text'
    }

    const navigateTo = (route) => {
      router.push(route)
    }

    const registerForm = async () => {
      try {
        const emailExists = await axios.get(`http://localhost:8080/users/findByEmail/${formData.value.email}`)
        if (emailExists.data) {
          message.value = 'El correo ya se encuentra registrado.'
        } else {
          const isValidRut = await axios.get(`http://localhost:8080/users/validateRut/${formData.value.rut}`)
          if (isValidRut.data) {
            await axios.post('http://localhost:8080/users', formData.value)
            message.value = 'Registro exitoso.'
            router.push('/principal')
          } else {
            message.value = 'El RUT ingresado no es válido.'
          }
        }
      } catch (error) {
        message.value = 'Ocurrió un error durante el registro.'
      }
    }

    return {
      formData,
      formFields,
      inputType,
      message,
      registerForm,
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

.registro-container {
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
  font-weight: bold;
  margin-bottom: 5px;
  color: #555;
}

input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 10px;
  box-sizing: border-box;
  font-size: 16px;
}

.submit-button {
  width: 100%;
  padding: 12px;
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
