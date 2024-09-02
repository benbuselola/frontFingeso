<template>
  <header>
    <div class="logo-section">
      <img src="../components/images/image.png" alt="Nombre de la página" height="60">
    </div>
    <nav>
      <ul>
        <li class="buttonsNonUser"><a @click="navigateTo('/principal')">Inicio</a></li>
        <li class="buttonsNonUser"><a @click="navigateTo('/soporte')">Ayuda</a></li>
      </ul>
    </nav>
  </header>

  <div class="form-container" v-if="editionProperty === 1">
    <div class="change-comuna">
      <h2>Cambia la comuna de tu propiedad</h2>
      <form @submit.prevent="updateNeighboorhood">
        <div class="form-group">
          <label>Ingrese la nueva comuna</label>
          <ComunaSelector v-model="comunanueva" required />
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
          <select id="nuevo-tipo-propiedad" v-model="tiponuevo" required>
            <option value="" disabled>Seleccione el tipo</option>
            <option value="casa">Casa</option>
            <option value="departamento">Departamento</option>
          </select>
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
  <footer class>
      <p class = "copyright">© 2024 HomeSphere Todos los derechos reservados. Prohibida su reproducción total o parcial por cualquier medio</p>
    </footer>
</template>

<script>
import axios from 'axios'
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import ComunaSelector from '../components/comunas.vue'

export default {
  components: {
    ComunaSelector
  },
  setup() {
    const router = useRouter()
    const editionProperty = ref(null)
    const comunanueva = ref('')
    const tiponuevo = ref('')
    const message = ref('')
    const tamanonuevo = ref('')
    const dormitoriosnuevos = ref('')
    const banosnuevos = ref('')
    const precionuevo = ref('')
    const descripcionnueva = ref('')


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
        const propertyId = localStorage.getItem('selectedPropertyId')
        const response = await axios.put(`http://localhost:8080/property/updateNeighboorhood/${propertyId}/${comunanueva.value}`)
        if (response.data) {
          message.value = 'Comuna actualizada exitosamente'
          router.push('/propiedadPerfil')
        } else {
          message.value = 'Error al actualizar la comuna'
        }
      } catch (error) {
        console.error('Error durante la actualización:', error.response ? error.response.data : error.message)
        message.value = 'Error durante la actualización'
      }
    }

    const updatePropertyType = async () => {
      try {
        const propertyId = localStorage.getItem('selectedPropertyId')
        const response = await axios.put(`http://localhost:8080/property/updatePropertyType/${propertyId}/${tiponuevo.value}`)
        if (response.data) {
          message.value = 'Tipo de propiedad actualizado exitosamente'
          router.push('/propiedadPerfil')

        } else {
          message.value = 'Error al actualizar el tipo de propiedad'
        }
      } catch (error) {
        console.error('Error durante la actualización:', error.response ? error.response.data : error.message)
        message.value = 'Error durante la actualización'
      }
    }

    const updateSize = async () => {
      try {
        const propertyId = localStorage.getItem('selectedPropertyId')
        const response = await axios.put(`http://localhost:8080/property/updateSize/${propertyId}/${tamanonuevo.value}`)
        if (response.data) {
          message.value = 'Tamaño actualizado exitosamente'
          router.push('/propiedadPerfil')

        } else {
          message.value = 'Error al actualizar el tamaño'
        }
      } catch (error) {
        console.error('Error durante la actualización:', error.response ? error.response.data : error.message)
        message.value = 'Error durante la actualización'
      }
    }

    const updateBedrooms = async () => {
      try {
        const propertyId = localStorage.getItem('selectedPropertyId')
        const response = await axios.put(`http://localhost:8080/property/updateBedrooms/${propertyId}/${dormitoriosnuevos.value}`)
        if (response.data) {
          message.value = 'Dormitorios actualizados exitosamente'
          router.push('/propiedadPerfil')

        } else {
          message.value = 'Error al actualizar los dormitorios'
        }
      } catch (error) {
        console.error('Error durante la actualización:', error.response ? error.response.data : error.message)
        message.value = 'Error durante la actualización'
      }
    }

    const updateBathrooms = async () => {
      try {
        const propertyId = localStorage.getItem('selectedPropertyId')
        const response = await axios.put(`http://localhost:8080/property/updateBathrooms/${propertyId}/${banosnuevos.value}`)
        if (response.data) {
          message.value = 'Baños actualizados exitosamente'
          router.push('/propiedadPerfil')

        } else {
          message.value = 'Error al actualizar los baños'
        }
      } catch (error) {
        console.error('Error durante la actualización:', error.response ? error.response.data : error.message)
        message.value = 'Error durante la actualización'
      }
    }

    const updatePrice = async () => {
      try {
        const propertyId = localStorage.getItem('selectedPropertyId')
        const response = await axios.put(`http://localhost:8080/property/updatePrice/${propertyId}/${precionuevo.value}`)
        if (response.data) {
          message.value = 'Precio actualizado exitosamente'
          router.push('/propiedadPerfil')

        } else {
          message.value = 'Error al actualizar el precio'
        }
      } catch (error) {
        console.error('Error durante la actualización:', error.response ? error.response.data : error.message)
        message.value = 'Error durante la actualización'
      }
    }

    const updateDescription = async () => {
      try {
        const propertyId = localStorage.getItem('selectedPropertyId')
        const response = await axios.put(`http://localhost:8080/property/updateDescription/${propertyId}/${descripcionnueva.value}`)
        if (response.data) {
          message.value = 'Descripción actualizada exitosamente'
          router.push('/propiedadPerfil')

        } else {
          message.value = 'Error al actualizar la descripción'
        }
      } catch (error) {
        console.error('Error durante la actualización:', error.response ? error.response.data : error.message)
        message.value = 'Error durante la actualización'
      }
    }

    const navigateTo = (route) => {
      router.push(route)
    }





    onMounted(() => {
      loadEditionProperty()
    })

    return {
      editionProperty,
      comunanueva,
      tiponuevo,
      message,
      updateNeighboorhood,
      updatePropertyType,
      tamanonuevo,
      updateSize,
      dormitoriosnuevos,
      updateBedrooms,
      banosnuevos,
      updateBathrooms,
      precionuevo,
      updatePrice,
      descripcionnueva,
      updateDescription,
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
