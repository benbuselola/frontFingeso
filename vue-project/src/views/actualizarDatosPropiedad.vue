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

  <div class="form-container" v-if="editionProperty === 1">
    <div class="change-comuna">
      <h2>Cambia la comuna de tu propiedad</h2>
      <form @submit.prevent="updateNeighboorhood">
        <div class="form-group">
          <label for="nueva-comuna">Ingrese la nueva comuna</label>
          <input type="text" id="nueva-comuna" v-model="comunanueva" placeholder="Nueva Comuna" required>
        </div>
        <button type="submit" class="submit-button">Cambiar comuna</button>
        <p v-if="message" class="message">{{ message }}</p>
      </form>
    </div>
  </div>

  <div class="form-container" v-if="editionProperty === 2">
    <div class="change-tipo-propiedad">
      <h2>Cambia el tipo de propiedad de tu propiedad</h2>
      <form @submit.prevent="updatePropertyType">
        <div class="form-group">
          <label for="nuevo-tipo-propiedad">Ingrese el nuevo tipo de propiedad</label>
          <input type="text" id="nuevo-tipo-propiedad" v-model="tiponuevo" placeholder="Nuevo Tipo de propiedad" required>
        </div>
        <button type="submit" class="submit-button">Cambiar tipo de propiedad</button>
        <p v-if="message" class="message">{{ message }}</p>
      </form>
    </div>
  </div>

  <div class="form-container" v-if="editionProperty === 3">
    <div class="change-tamano">
      <h2>Cambia el tamaño de tu propiedad</h2>
      <form @submit.prevent="updateSize">
        <div class="form-group">
          <label for="nuevo-tamano">Ingrese el nuevo tamaño</label>
          <input type="number" id="nuevo-tamano" v-model="tamanonuevo" placeholder="Nuevo Tamaño" required>
        </div>
        <button type="submit" class="submit-button">Cambiar tamaño</button>
        <p v-if="message" class="message">{{ message }}</p>
      </form>
    </div>
  </div>

  <div class="form-container" v-if="editionProperty === 4">
    <div class="change-dormitorios">
      <h2>Cambia la cantidad de dormitorios de tu propiedad</h2>
      <form @submit.prevent="updateBedrooms">
        <div class="form-group">
          <label for="nuevos-dormitorios">Ingrese la nueva cantidad de dormitorios</label>
          <input type="number" id="nuevos-dormitorios" v-model="dormitoriosnuevos" placeholder="Nuevos Dormitorios" required>
        </div>
        <button type="submit" class="submit-button">Cambiar dormitorios</button>
        <p v-if="message" class="message">{{ message }}</p>
      </form>
    </div>
  </div>

  <div class="form-container" v-if="editionProperty === 5">
    <div class="change-banos">
      <h2>Cambia la cantidad de baños de tu propiedad</h2>
      <form @submit.prevent="updateBathrooms">
        <div class="form-group">
          <label for="nuevos-banos">Ingrese la nueva cantidad de baños</label>
          <input type="number" id="nuevos-banos" v-model="banosnuevos" placeholder="Nuevos Baños" required>
        </div>
        <button type="submit" class="submit-button">Cambiar baños</button>
        <p v-if="message" class="message">{{ message }}</p>
      </form>
    </div>
  </div>

  <div class="form-container" v-if="editionProperty === 6">
    <div class="change-precio">
      <h2>Cambia el precio de tu propiedad</h2>
      <form @submit.prevent="updatePrice">
        <div class="form-group">
          <label for="nuevo-precio">Ingrese el nuevo precio</label>
          <input type="number" id="nuevo-precio" v-model="precionuevo" placeholder="Nuevo Precio" required>
        </div>
        <button type="submit" class="submit-button">Cambiar precio</button>
        <p v-if="message" class="message">{{ message }}</p>
      </form>
    </div>
  </div>

  <div class="form-container" v-if="editionProperty === 7">
    <div class="change-descripcion">
      <h2>Cambia la descripción de tu propiedad</h2>
      <form @submit.prevent="updateDescription">
        <div class="form-group">
          <label for="nueva-descripcion">Ingrese la nueva descripción</label>
          <input type="text" id="nueva-descripcion" v-model="descripcionnueva" placeholder="Nueva Descripción" required>
        </div>
        <button type="submit" class="submit-button">Cambiar descripción</button>
        <p v-if="message" class="message">{{ message }}</p>
      </form>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

export default {
  setup() {
    const router = useRouter()
    const comunaantigua = ref('')
    const comunanueva = ref('')
    const tipoantiguo = ref('')
    const tiponuevo = ref('')
    const tamanoantiguo = ref('')
    const tamanonuevo = ref('')
    const dormitoriosantiguos = ref('')
    const dormitoriosnuevos = ref('')
    const banosantiguos = ref('')
    const banosnuevos = ref('')
    const precioantiguo = ref('')
    const precionuevo = ref('')
    const descripcionantigua = ref('')
    const descripcionnueva = ref('')
    const message = ref('')
    const editionProperty = ref(0)

    onMounted(() => {
      loadEditionProperty()
    })

    const loadEditionProperty = () => {
      const property = localStorage.getItem('editionProperty')
      if (!property) {
        alert('No se ha seleccionado una propiedad para editar')
        router.push('/principal')
        return
      }
      editionProperty.value = parseInt(property)
    }

    const updateNeighboorhood = async () => {
      try {
        const response = await axios.put('http://localhost:8080/property/updateNeighboorhood', {
          comunaantigua: comunaantigua.value,
          comunanueva: comunanueva.value
        })
        message.value = response.data
      } catch (error) {
        console.error('Error durante la actualización:', error.response ? error.response.data : error.message)
      }
    }

    const updatePropertyType = async () => {
      try {
        const response = await axios.put('http://localhost:8080/property/updatePropertyType', {
          tipoantiguo: tipoantiguo.value,
          tiponuevo: tiponuevo.value
        })
        message.value = response.data
      } catch (error) {
        console.error('Error durante la actualización:', error.response ? error.response.data : error.message)
      }
    }

    const updateSize = async () => {
      try {
        const response = await axios.put('http://localhost:8080/property/updateSize', {
          tamanoantiguo: tamanoantiguo.value,
          tamanonuevo: tamanonuevo.value
        })
        message.value = response.data
      } catch (error) {
        console.error('Error durante la actualización:', error.response ? error.response.data : error.message)
      }
    }

    const updateBedrooms = async () => {
      try {
        const response = await axios.put('http://localhost:8080/property/updateBedrooms', {
          dormitoriosantiguos: dormitoriosantiguos.value,
          dormitoriosnuevos: dormitoriosnuevos.value
        })
        message.value = response.data
      } catch (error) {
        console.error('Error durante la actualización:', error.response ? error.response.data : error.message)
      }
    }

    const updateBathrooms = async () => {
      try {
        const response = await axios.put('http://localhost:8080/property/updateBathrooms', {
          banosantiguos: banosantiguos.value,
          banosnuevos: banosnuevos.value
        })
        message.value = response.data
      } catch (error) {
        console.error('Error durante la actualización:', error.response ? error.response.data : error.message)
      }
    }

    const updatePrice = async () => {
      try {
        const response = await axios.put('http://localhost:8080/property/updatePrice', {
          precioantiguo: precioantiguo.value,
          precionuevo: precionuevo.value
        })
        message.value = response.data
      } catch (error) {
        console.error('Error durante la actualización:', error.response ? error.response.data : error.message)
      }
    }

    const updateDescription = async () => {
      try {
        const response = await axios.put('http://localhost:8080/property/updateDescription', {
          descripcionantigua: descripcionantigua.value,
          descripcionnueva: descripcionnueva.value
        })
        message.value = response.data
      } catch (error) {
        console.error('Error durante la actualización:', error.response ? error.response.data : error.message)
      }
    }

    const navigateTo = (path) => {
      router.push(path)
    }

    return {
      editionProperty,
      comunaantigua,
      comunanueva,
      tipoantiguo,
      tiponuevo,
      tamanoantiguo,
      tamanonuevo,
      dormitoriosantiguos,
      dormitoriosnuevos,
      banosantiguos,
      banosnuevos,
      precioantiguo,
      precionuevo,
      descripcionantigua,
      descripcionnueva,
      message,
      updateNeighboorhood,
      updatePropertyType,
      updateSize,
      updateBedrooms,
      updateBathrooms,
      updatePrice,
      updateDescription,
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
  padding: 10px 20px;
  background-color: #4CAF50; /* Color de fondo del header */
  color: white;
}

header .logo-section img {
  width: 100px; /* Ajustar según sea necesario */
  height: 60px;
  border-radius: 10px;
}

nav ul {
  display: flex;
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

.form-container {
  max-width: 600px;
  margin: 50px auto;
}

.update-neighborhood, .update-property-type, .update-size, .update-bedrooms, .update-bathrooms, .update-price, .update-description {
  padding: 20px;
  border: 2px solid #ccc;
  border-radius: 10px;
  background-color: #f9f9f9;
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
  background-color: #4CAF50; /* Botón con color de fondo igual al del header */
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
</style>
