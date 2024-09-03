<template>
  <div id="app">
    <header>
      <div class="logo-section">
        <img src="../components/images/logoOficial.jpeg" alt="Nombre de la página" height="60" />
      </div>
      <nav>
        <ul>
          <li><a class="principal-button" @click="navigateTo('/principal')">Inicio</a></li>
          <li><a class="support-button" @click="navigateTo('/soporte')">Ayuda</a></li>
        </ul>
      </nav>
    </header>

    <section class="List-properties">
      <div v-for="propiedad in properties" :key="propiedad.id" class="property-item">
        <p><strong>Tipo:</strong> {{ propiedad.propertyType }}</p>
        <p><strong>Precio:</strong> {{ propiedad.value }}</p>
        <p><strong>Ubicación:</strong> {{ propiedad.neighboorhood }}</p>

        <div class="action-buttons">
          <button class="actionButton" @click="mostrarContenido('horario', propiedad)">Agregar Horario de Visita</button>
          <button class="actionButton" @click="mostrarContenido('contacto', propiedad)">Contactar Propietario</button>
          <button class="actionButton" @click="mostrarContenido('visitantes', propiedad)">Lista de Visitantes</button>
        </div>
      </div>
    </section>

    <section class="Content-section" v-if="selectedContent">
      <h2>{{ selectedTitle }}</h2>
      <div v-if="selectedContent === 'horario'">
        <p>Calendario: {{ selectedProperty.title }}</p>
        <Agenda @date-selected="handleDateSelected" />
        <p v-if="selectedDate">Fecha seleccionada: {{ selectedDate }}</p>
      </div>
      <div v-if="selectedContent === 'contacto'">
        <p>Contacto del propietario: {{ ownerEmail }}</p>
      </div>
      <div v-if="selectedContent === 'visitantes'">
        <p>lista de visitantes: {{ selectedProperty.title }}</p>
      </div>
    </section>

    <footer>
      <p class="copyright">© 2024 HomeSphere Todos los derechos reservados. Prohibida su reproducción total o parcial por cualquier medio</p>
    </footer>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
import Agenda from '../components/agenda.vue'

export default {
  name: 'PropertyList',
  components: {
    Agenda
  },
  setup() {
    const router = useRouter()
    const properties = ref([])
    const selectedContent = ref(null)
    const selectedProperty = ref(null)
    const selectedTitle = ref('')
    const selectedDate = ref('')
    const ownerEmail = ref('') // Añadido para almacenar el correo del propietario

    const newEvent = ref({
      title: '',
      date: '',
      time: ''
    });

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

    const mostrarContenido = async (tipo, propiedad) => {
      selectedContent.value = tipo
      selectedProperty.value = propiedad
      switch (tipo) {
        case 'horario':
          selectedTitle.value = 'Horario disponible para visitas'
          break
        case 'contacto':
          selectedTitle.value = 'Contactar propietario'
          // Realizar la solicitud para obtener el correo del propietario
          try {
            const response = await axios.get(`http://localhost:8080/brokers/findByCorreoProp/${propiedad.id}`)
            ownerEmail.value = response.data
          } catch (error) {
            console.error('Error al obtener el correo del propietario:', error)
            ownerEmail.value = 'No se pudo obtener el correo del propietario.'
          }
          break
        case 'visitantes':
          selectedTitle.value = 'Lista de visitantes'
          break
        default:
          selectedTitle.value = ''
      }
    }

    const handleDateSelected = (date) => {
      selectedDate.value = date
      console.log('Fecha seleccionada:', date)
      axios.post('http://localhost:8080/your-endpoint', { date })
        .then(response => {
          console.log('Fecha enviada correctamente:', response)
        })
        .catch(error => {
          console.error('Error al enviar la fecha:', error)
        })
    }

    const addEvent = () => {
      const event = {
        title: newEvent.value.title,
        start: `${newEvent.value.date}T${newEvent.value.time}`
      };
      console.log('Nuevo evento agregado:', event);
      newEvent.value.title = '';
      newEvent.value.date = '';
      newEvent.value.time = '';
    };

    return {
      properties,
      selectedContent,
      selectedProperty,
      selectedTitle,
      selectedDate,
      navigateTo,
      mostrarContenido,
      handleDateSelected,
      addEvent,
      newEvent,
      ownerEmail // Añadido para mostrar el correo del propietario
    }
  }
}
</script>

<style scoped>
* {
  font-family: 'Poppins';
}

header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
  background-color: #4ca771;
  color: white;
}

header .logo-section img {
  width: 100px;
  height: 80px;
  border-radius: 10px;
}

#app {
  background-color: #eaf3e6; 
  min-height: 100vh; 
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
  color: #013237;
  text-decoration: none;
  font-size: 18px;
}

nav ul li a:hover {
  text-decoration: underline;
}

.principal-button, .support-button {
  background-color: #EAF9E7;
  color: #013237;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.principal-button:hover, .support-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 8px rgba(0, 0, 0, 0.1);
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

.actionButton {
  height: 100px;
  width: 200px;
  background-color: #4ca771;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  font-size: 24px;
  color: white;
  border-radius: 5px;
  outline: none;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.actionButton:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 8px rgba(0, 0, 0, 0.1);
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

footer {
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 10px 20px;
  background-color: #4CA771;
  color: white;
}

.copyright {
  color: white;
  font-size: 14px;
  text-align: center;
  margin: 0;
}
</style>
