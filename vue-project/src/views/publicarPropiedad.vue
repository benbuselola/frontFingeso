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
  <div class="info-propiedad">
    <h2>Publica tu Propiedad</h2>
    <form @submit.prevent="registroProp">
      <div class="form-group">
        <label for="calle">Calle</label>
        <input type="text" id="calle" v-model="calle" placeholder="Ingrese la calle" required>
      </div>
      <div class="form-group">
        <label for="comuna">Comuna</label>
        <input type="text" id="comuna" v-model="comuna" placeholder="Ingrese la comuna" required>
      </div>
      <div class="form-group">
        <label for="region">Región</label>
        <input type="text" id="region" v-model="region" placeholder="Ingrese la región" required>
      </div>
      <div class="form-group">
        <label for="tipo_publicacion">Tipo de Publicación</label>
        <select id="tipo_publicacion" v-model="tipo_publicacion" required>
          <option value="" disabled selected>Seleccione el tipo</option>
          <option value="vender">Vender</option>
          <option value="arrendar">Arrendar</option>
        </select>
      </div>
      <div class="form-group">
        <label for="tipo_propiedad">Tipo de Propiedad</label>
        <select id="tipo_propiedad" v-model="tipo_propiedad" required>
          <option value="" disabled selected>Seleccione el tipo</option>
          <option value="casa">Casa</option>
          <option value="departamento">Departamento</option>
        </select>
      </div>
      <div class="form-group">
        <label for="tamano">Tamaño de la Vivienda (m²)</label>
        <input type="number" id="tamano" v-model="tamano" placeholder="Ingrese el tamaño en m²" required>
      </div>
      <div class="form-group">
        <label for="valor">Valor (UF)</label>
        <input type="number" id="valor" v-model="valor" placeholder="Ingrese el valor en UF" required>
      </div>
      <div class="form-group">
        <label for="banos">Cantidad de Baños</label>
        <input type="number" id="banos" v-model="banos" placeholder="Ingrese la cantidad de baños" required>
      </div>
      <div class="form-group">
        <label for="dormitorios">Cantidad de Dormitorios</label>
        <input type="number" id="dormitorios" v-model="dormitorios" placeholder="Ingrese la cantidad de dormitorios" required>
      </div>
      <div class="form-group">
        <label for="imagen">Imagen</label>
        <input type="file" id="imagen" class="file-input" required>
      </div>
      <div class="form-group">
        <label for="descripcion">Descripción</label>
        <textarea id="descripcion" v-model="descripcion" placeholder="Describa la propiedad" required></textarea>
      </div>
      <div class="form-group">
        <label for="telefono">Teléfono</label>
        <input type="tel" id="telefono" v-model="telefono" placeholder="Ingrese su número de teléfono" required>
      </div>
      <div class="form-group">
        <label for="email">Correo Electrónico</label>
        <input type="email" id="email" v-model="email" placeholder="Ingrese su correo electrónico" required>
      </div>
      <button type="submit" class="submit-button">Publicar</button>
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
    const calle = ref('')
    const comuna = ref('')
    const region = ref('')
    const tipo_publicacion = ref('')
    const tipo_propiedad = ref('')
    const tamano = ref('')
    const valor = ref('')
    const banos = ref('')
    const dormitorios = ref('')
    const descripcion = ref('')
    const telefono = ref('')
    const email = ref('')
    const message = ref('')
    const router = useRouter()

    const registroProp = async () => {
      try {
        const response = await axios.post('http://localhost:8080/api/propiedades', {
          calle: calle.value,
          comuna: comuna.value,
          region: region.value,
          tipo_publicacion: tipo_publicacion.value,
          tipo_propiedad: tipo_propiedad.value,
          tamano: tamano.value,
          valor: valor.value,
          banos: banos.value,
          dormitorios: dormitorios.value,
          descripcion: descripcion.value,
          telefono: telefono.value,
          email: email.value
        })
        if (response.data) {
          message.value = 'Propiedad publicada con éxito.'
          router.push('/principal')
        }
      } catch (error) {
        message.value = 'Error al publicar la propiedad.'
      }
    }

    const navigateTo = (route) => {
      router.push(route)
    }

    return {
      calle,
      comuna,
      region,
      tipo_publicacion,
      tipo_propiedad,
      tamano,
      valor,
      banos,
      dormitorios,
      descripcion,
      telefono,
      email,
      message,
      registroProp,
      navigateTo
    }
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
  padding: 10px 15px;
}

nav ul li a:hover {
  text-decoration: underline;
}
.info-propiedad {
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

.form-group input,
.form-group select,
.form-group textarea {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 20px;
}

textarea {
  resize: none;
}

.file-input {
  padding: 3px;
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
