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
  <div class="info-propiedad">
    <h2>Publica tu Propiedad</h2>
    <form @submit.prevent="registroProp">
      <div class="form-group">
        <ComunasSelector v-model="neighboorhood" required />
      </div>
      <div class="form-group">
        <label for="region">Región</label>
        <select id="region" v-model="region" required>
          <option value="" disabled>Seleccione la región</option>
          <option value="metropolitana">Metropolitana</option>
        </select>
      </div>
      <div class="form-group">
        <label for="tipo_publicacion">Tipo de Publicación</label>
        <select id="tipo_publicacion" v-model="publicationType" required>
          <option value="" disabled>Seleccione el tipo</option>
          <option value="vender">Vender</option>
          <option value="arrendar">Arrendar</option>
        </select>
      </div>
      <div class="form-group">
        <label for="tipo_propiedad">Tipo de Propiedad</label>
        <select id="tipo_propiedad" v-model="propertyType" required>
          <option value="" disabled>Seleccione el tipo</option>
          <option value="casa">Casa</option>
          <option value="departamento">Departamento</option>
        </select>
      </div>
      <div class="form-group">
        <label for="tamano">Tamaño de la Vivienda (m²)</label>
        <input type="number" id="tamano" v-model="size" placeholder="Ingrese el tamaño en m²" required min="1">
      </div>
      <div class="form-group">
        <label for="valor">Valor (UF)</label>
        <input type="number" id="valor" v-model="value" placeholder="Ingrese el valor en UF" required>
      </div>
      <div class="form-group">
        <label for="banos">Cantidad de Baños</label>
        <input type="number" id="banos" v-model="bathrooms" placeholder="Ingrese la cantidad de baños" required min="1">
      </div>
      <div class="form-group">
        <label for="dormitorios">Cantidad de Dormitorios</label>
        <input type="number" id="dormitorios" v-model="bedrooms" placeholder="Ingrese la cantidad de dormitorios" required min="1">
      </div>
      <div class="form-group">
        <label for="descripcion">Descripción</label>
        <textarea id="descripcion" v-model="description" placeholder="Describa la propiedad" required></textarea>
      </div>
      <div class="form-group">
        <label for="telefono">Teléfono</label>
        <input type="tel" id="telefono" v-model="number" placeholder="Ingrese su número de teléfono" required>
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
import axios from 'axios';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import ComunasSelector from '../components/comunas.vue';

export default {
  components: {
    ComunasSelector,
  },
  setup() {
    const neighboorhood = ref(''); 
    const region = ref('');
    const publicationType = ref('');
    const propertyType = ref('');
    const size = ref('');
    const value = ref('');
    const bathrooms = ref('');
    const bedrooms = ref('');
    const description = ref('');
    const number = ref('');
    const email = ref('');
    const message = ref('');
    const router = useRouter();

    const registroProp = async () => {
      try {
        const userId = localStorage.getItem('usuario');
        if (!userId) {
          alert('Debes iniciar sesión para publicar una propiedad');
          router.push('/login');
          return;
        }
        const propertyData = {
          neighboorhood: neighboorhood.value,
          region: region.value,
          publicationType: publicationType.value,
          propertyType: propertyType.value,
          size: size.value,
          value: value.value,
          bathrooms: bathrooms.value,
          bedrooms: bedrooms.value,
          description: description.value,
          number: number.value,
          email: email.value,
        };

        const response = await axios.post(`http://localhost:8080/users/saveProperty/${userId}`, propertyData);

        if (response.data) {
          message.value = 'Propiedad publicada con éxito.';
          router.push('/principal');
        }
      } catch (error) {
        console.error('Error al publicar la propiedad:', error);
        message.value = 'Error al publicar la propiedad. Por favor, inténtelo de nuevo.';
      }
    };

    const navigateTo = (route) => {
      router.push(route);
    };

    return {
      neighboorhood,
      region,
      publicationType,
      propertyType,
      size,
      value,
      bathrooms,
      bedrooms,
      description,
      number,
      email,
      message,
      registroProp,
      navigateTo,
    };
  },
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
  cursor: pointer;
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
