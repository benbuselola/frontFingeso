<template>
  <div id="app">
    <div class="container">
      <header>
        <div class="logo-section">
          <img src="../components/images/logoOficial.jpeg" alt="Nombre de la página" height="60">
        </div>
        <nav>
          <ul>
            <li><a class = "principal-button" @click="navigateTo('/principal')">Inicio</a></li>
          </ul>
        </nav>
      </header>
  
      <div class="main-content">
        <aside class="user-list">
          <h2>Usuarios</h2>
          <div v-for="user in users" :key="user.id" class="user-item">
            <p><strong>Nombre:</strong> {{ user.firstName }} {{ user.secondName }} {{ user.lastName }} {{ user.secondLastName }}</p>
            <p><strong>Correo:</strong> {{ user.email }}</p>
            <p><strong>Rol:</strong> {{ user.role }}</p>
            <div class="action-buttons">
              <button class="btn-view" @click="showSection('properties', user)">Ver propiedades</button>
              <button class="btn-view" @click="showSection('movements', user)">Ver movimientos</button>
              <button class="btn-view" @click="showSection('likes', user)">Ver me gusta</button>
              <button class="btn-edit" @click="showSection('edit', user)">Editar usuario</button>
              <button class="btn-delete" @click="showSection('delete', user)">Eliminar usuario</button>
            </div>
          </div>
        </aside>
  
        <section class="details-section">
          <div v-if="currentSection === 'properties'" class="property-list">
            <h2>Propiedades</h2>
            <div v-for="property in properties" :key="property.id" class="property-item">
              <p><strong>Tipo:</strong> {{ property.propertyType }}</p>
              <p><strong>Precio:</strong> {{ property.value }}</p>
              <p><strong>Ubicación:</strong> {{ property.neighboorhood }}</p>
              <div class="action-buttons">
                <button class="btn-edit" @click="selection('edit', property)">Editar</button>
                <button class="btn-delete" @click="selection('delete', property)">Eliminar</button>
              </div>
            </div>
          </div>
  
          <div v-if="currentSection === 'likes'" class="likes-list">
            <h2>Me gusta</h2>
            <div v-for="like in likes" :key="like.id" class="like-item">
              <p>{{ like.description }}</p>
            </div>
          </div>
  
          <div v-if="currentSection === 'movements'" class="movements-list">
            <h2>Movimientos</h2>
            <p>Movimientos del usuario...</p>
          </div>
  
          <div v-if="currentSection === 'edit'" class="edit-section">
            <h2>Editar usuario</h2>
            <div class="form-group">
              <label>Primer Nombre:</label>
              <input type="text" v-model="userDatos.firstName">
            </div>
            <div class="form-group">
              <label>Segundo Nombre:</label>
              <input type="text" v-model="userDatos.secondName">
            </div>
            <div class="form-group">
              <label>Apellido:</label>
              <input type="text" v-model="userDatos.lastName">
            </div>
            <div class="form-group">
              <label>Segundo Apellido:</label>
              <input type="text" v-model="userDatos.secondLastName">
            </div>
            <div class="form-group">
              <label>RUT:</label>
              <input type="text" v-model="userDatos.rut">
            </div>
            <div class="form-group">
              <label>Correo:</label>
              <input type="text" v-model="userDatos.email">
            </div>
            <div class="form-group">
              <label>Número:</label>
              <input type="text" v-model="userDatos.number">
            </div>
            <div class="form-group">
              <label>Contraseña:</label>
              <input type="password" v-model="userDatos.password">
            </div>
            <button @click="updateUser(userDatos)">Guardar cambios</button>
          </div>
          <div v-if="currentSection === 'delete'" class="delete-section">
            <h2>Eliminar usuario</h2>
            <p>¿Estás seguro de que deseas eliminar este usuario?</p>
            <button>Eliminar</button>
            </div>

        </section>
      </div>
    </div>


  <footer>
      <p class = "copyright">© 2024 HomeSphere Todos los derechos reservados. Prohibida su reproducción total o parcial por cualquier medio</p>
  </footer>
</div>
</template>
  
  <script>
  import { ref, onMounted } from 'vue';
  import axios from 'axios';
  import { useRouter } from 'vue-router';
  
  export default {
    setup() {
      const router = useRouter();
      const users = ref([]);
      const currentSection = ref(null);
      const properties = ref([]);
      const likes = ref([]);
      const movements = ref([]);
      const userDatos = ref({});
  
      const navigateTo = (ruta) => {
        router.push(ruta);
      };
  
      const showSection = async (section, user) => {
        currentSection.value = section;
        if (section === 'properties') {
          const response = await axios.get(`http://localhost:8080/users/getPropertiesbyUser/${user.id}`);
          properties.value = response.data;
        } else if (section === 'likes') {
          const response = await axios.get(`http://localhost:8080/users/${user.id}/likes`);
          likes.value = response.data;
        } else if (section === 'movements') {
          const response = await axios.get(`http://localhost:8080/users/${user.id}/movements`);
          movements.value = response.data;
        } else if (section === 'edit') {
          const response = await axios.get(`http://localhost:8080/users/findbyID/${user.id}`);
          userDatos.value = response.data;
        } else if (section === 'delete') {
          // Lógica para eliminar el usuario
        }
      };

        const updateUser = async (user) => {
            const response = await axios.put(`http://localhost:8080/users/update/${user.id}`, user);
            if (response.status === 200) {
            alert('Usuario actualizado correctamente');
            } else {
            alert('Error al actualizar el usuario');
            }
        };
  
  
      onMounted(async () => {
        const response = await axios.get('http://localhost:8080/users/obtainAll');
        users.value = response.data;
      });
  
      return {
        users,
        currentSection,
        userDatos,
        properties,
        likes,
        navigateTo,
        showSection,
        updateUser,
      };
    },
  };
  </script>
  
  <style scoped>
  .container {
    display: flex;
    flex-direction: column;
    height: 100vh;
  }
  #app {
  background-color: #eaf3e6; 
  min-height: 100vh; 
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
  .principal-button {
  background-color: #EAF9E7;
  color: black;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

  
  nav ul {
    display: flex;
    align-items: center;
    list-style: none;
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
  
  .main-content {
    display: flex;
    flex-grow: 1;
    padding: 20px;
    background-color: #f4f4f4;
  }
  
  .user-list {
    width: 30%;
    margin-right: 20px;
  }
  
  .user-list h2 {
    margin-bottom: 15px;
    color: #333;
  }
  
  .user-item {
    background-color: #EAF9E7;
    padding: 15px;
    margin-bottom: 15px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  
  .action-buttons {
    display: flex;
    flex-wrap: wrap;
  }
  
  .action-buttons button {
    margin: 5px 10px 0 0;
    padding: 8px 12px;
    font-size: 14px;
    border-radius: 5px;
    cursor: pointer;
    border: none;
    transition: background-color 0.3s;
  }
  
  .btn-view {
    background-color: #4ca771;
    color: white;
  }
  
  .btn-view:hover {
    background-color: #45a049;
  }
  
  .btn-edit {
    background-color: #ffa500;
    color: white;
  }
  
  .btn-edit:hover {
    background-color: #e69500;
  }
  
  .btn-delete {
    background-color: #f44336;
    color: white;
  }
  
  .btn-delete:hover {
    background-color: #d32f2f;
  }
  
  .details-section {
    width: 70%;
    padding: 20px;
    background-color: #EAF9E7;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  
  .details-section h2 {
    margin-bottom: 15px;
    color: #333;
  }
  
  .property-list, .likes-list, .movements-list {
    margin-top: 20px;
  }
  
  .form-group {
    margin-bottom: 10px;
  }
  
  .form-group label {
    display: block;
    margin-bottom: 5px;
  }
  
  .form-group input {
    width: 100%;
    padding: 8px;
    border-radius: 4px;
    border: 1px solid #ccc;
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
  