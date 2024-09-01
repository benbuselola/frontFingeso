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

  <section class="List-properties">
    <div v-for="propiedad in properties" :key="propiedad.id" class="property-item">
      <p><strong>Tipo:</strong>{{ propiedad.propertyType }}</p>
      <p><strong>Precio:</strong> {{ propiedad.value }}</p>
      <p><strong>Ubicación:</strong> {{ propiedad.neighboorhood }}</p>

      <div class="action-buttons">
        <button @click="mostrarContenido('horario', propiedad)">Agregar Horario de Visita</button>
        <button @click="mostrarContenido('pago', propiedad)">Formulario de Pago</button>
        <button @click="mostrarContenido('contacto', propiedad)">Contactar Propietario</button>
        <button @click="mostrarContenido('visitantes', propiedad)">Lista de Visitantes</button>
      </div>
    </div>
  </section>

  <section class="Content-section" v-if="selectedContent">
    <h2>{{ selectedTitle }}</h2>
    <div v-if="selectedContent === 'horario'">
      <p>Calendario: {{ selectedProperty.title }}</p>
    </div>
    <div v-if="selectedContent === 'pago'">
      <p>formulario de pagos: {{ selectedProperty.title }}</p>
    </div>
    <div v-if="selectedContent === 'contacto'">
      <p>contacto del propietario: {{ selectedProperty.title }}</p>
    </div>
    <div v-if="selectedContent === 'visitantes'">
      <p>lista de visitantes: {{ selectedProperty.title }}</p>
    </div>
  </section>
</template>

<script>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

export default {
  name: 'PropertyList',
  setup() {
    const router = useRouter()
    const properties = ref([])
    const selectedContent = ref(null)
    const selectedProperty = ref(null)
    const selectedTitle = ref('')

    onMounted(async () => {
      const userId = localStorage.getItem('usuario')
      if (!userId) {
        router.push('/login')
        return
      }

      try {
        const response = await axios.get(`http://localhost:8080/users/getPropertiesbyUser/${userId}`)
        properties.value = response.data
      } catch (error) {
        console.error('Error al obtener las propiedades del usuario:', error)
      }
    })

    const navigateTo = (route) => {
      router.push(route)
    }

    const mostrarContenido = (tipo, propiedad) => {
      selectedContent.value = tipo
      selectedProperty.value = propiedad
      switch (tipo) {
        case 'horario':
          selectedTitle.value = 'hacer algo con el horario'
          break
        case 'pago':
          selectedTitle.value = 'hacer algo con el formulario de pago pago'
          break
        case 'contacto':
          selectedTitle.value = 'hacer algo con el contacto'
          break
        case 'visitantes':
          selectedTitle.value = 'hacer algo con la lista de visitantes'
          break
        default:
          selectedTitle.value = ''
      }
    }

    return {
      properties,
      selectedContent,
      selectedProperty,
      selectedTitle,
      navigateTo,
      mostrarContenido
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
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
}

.logo-section img {
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

.List-properties {
  margin: 20px 0;
  padding: 10px;
  background-color: #f5f5f5;
}

.property-item {
  background-color: #fff;
  padding: 15px;
  margin-bottom: 15px;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.property-item h2 {
  margin: 0 0 10px 0;
  color: #333;
}

.property-item p {
  margin: 5px 0;
  color: #555;
}

.property-item strong {
  color: #000;
}

.action-buttons {
  display: flex;
  justify-content: space-around;
  margin-top: 15px;
}

.action-buttons button {
  padding: 10px 15px;
  font-size: 16px;
  color: white;
  background-color: #4CAF50;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.action-buttons button:hover {
  background-color: #45a049;
}

.Content-section {
  margin-top: 40px;
  padding: 20px;
  background-color: #e0e0e0;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.Content-section h2 {
  margin-bottom: 20px;
  color: #333;
}
</style>
