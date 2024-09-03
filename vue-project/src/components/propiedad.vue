<template>
  <div v-if="propiedad" class="prop">
    <img @click="guardarIdYRedirigir" class="imgprop" :src="imagenPropiedad" alt="Imagen de la propiedad">
    <p>{{ propiedad.propertyType || 'Tipo desconocido' }}</p>
    <p class="precio">{{ propiedad.value ? propiedad.value + " (UF)" : 'Valor no disponible' }}</p>
    <p>{{ propiedad.neighboorhood || 'Comuna no especificada' }}</p>
    <p>{{ propiedad.description || 'Descripción no disponible' }}</p>
    <img class="favorite" :src="imagenFavorito" v-if="acto" @click.stop="likeProperty" alt="Favorito">
  </div>
  <div v-else>
    <p>No hay información de la propiedad disponible.</p>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import pordefectoCasa from "../components/images/casa.jpeg";
import pordefectoDepartamento from "../components/images/departamento.jpeg";
import fav from "../components/images/fav.png";
import favClick from "../components/images/favourite.png"; 
import axios from 'axios';

export default {
  name: 'Propiedad',
  props: {
    propiedad: {
      type: Object,
      default: () => ({})
    }
  },
  setup(props) {
    const router = useRouter();
    const acto = ref(false);
    const imagenFavorito = ref(fav);
    const likedProperties = ref([]);

    const verificacion = () => {
      const usuario = localStorage.getItem('usuario');
      if (usuario) {
        acto.value = true;
        fetchLikedProperties(usuario);
      }
    };

    const fetchLikedProperties = async (userId) => {
      try {
        const response = await axios.get(`http://localhost:8080/users/showLikedProperties/${userId}`);
        likedProperties.value = response.data;
        updateFavoriteStatus();
      } catch (error) {
        console.error('Error fetching liked properties:', error);
      }
    };

    const updateFavoriteStatus = () => {
      if (props.propiedad && likedProperties.value.some(prop => prop.id === props.propiedad.id)) {
        imagenFavorito.value = favClick;
      } else {
        imagenFavorito.value = fav;
      }
    };

    const imagenPropiedad = computed(() => {
      if (props.propiedad.propertyType) {
        if (props.propiedad.propertyType.toLowerCase() === 'casa') {
          return pordefectoCasa;
        } else if (props.propiedad.propertyType.toLowerCase() === 'departamento') {
          return pordefectoDepartamento;
        }
      }
      return pordefectoCasa;
    });

    const guardarIdYRedirigir = () => {
      if (props.propiedad && props.propiedad.id) {
        localStorage.setItem('selectedPropertyId', props.propiedad.id);
        router.push('/propiedadPerfil');
      } else {
        console.error('ID de propiedad no disponible');
      }
    };

    const likeProperty = async () => {
      if (props.propiedad && props.propiedad.id) {
        const userid = localStorage.getItem('usuario');
        if (!userid) {
          console.error('Usuario no autenticado');
          return;
        }

        try {
          if (imagenFavorito.value === fav) {
            await axios.post(`http://localhost:8080/users/likeProperty/${props.propiedad.id}/${userid}`);
            imagenFavorito.value = favClick;
            likedProperties.value.push(props.propiedad);
          } else {
            await axios.post(`http://localhost:8080/users/removeLike/${props.propiedad.id}/${userid}`);
            imagenFavorito.value = fav;
            likedProperties.value = likedProperties.value.filter(prop => prop.id !== props.propiedad.id);
          }
        } catch (error) {
          console.error('Error al actualizar el estado de "me gusta":', error);
        }
      } else {
        console.error('ID de propiedad no disponible');
      }
    };

    onMounted(() => {
      verificacion();
    });

    return {
      imagenPropiedad,
      guardarIdYRedirigir,
      likeProperty,
      acto,
      imagenFavorito
    };
  }
}
</script>


<style scoped>
.favorite{
  display: flex;
  height: 30px;
  width: 30px;
  margin-bottom: 10px;
  margin-left: 300px;
  cursor: pointer;
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
}
.favorite:hover{
  transform: scale(1.1);
}
.prop {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: #ffffff;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1), 0 10px 20px rgba(0, 0, 0, 0.1);
  margin: 20px 0;
  border-radius: 20px;
  overflow: hidden;
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
}

.prop:hover {
  transform: translateY(-10px);
  box-shadow: 0 8px 12px rgba(0, 0, 0, 0.15), 0 16px 24px rgba(0, 0, 0, 0.15);
}

.imgprop {
  margin: 20px;
  height: 200px;
  width: 100%;
  max-width: 300px;
  border-radius: 10px;
  object-fit: cover;
  cursor: pointer;
  transition: opacity 0.3s ease-in-out;
}

.imgprop:hover {
  opacity: 0.9;
}

p {
  font-size: 1.2rem;
  text-align: center;
  margin: 8px 0;
  color: #34495e;
  font-weight: 500;
}

p:first-of-type {
  font-size: 1.8rem;
  font-weight: 700;
  color: #2c3e50;
}

p:last-of-type {
  font-size: 1rem;
  font-weight: 400;
  color: #7f8c8d;
}

.precio {
  font-size: 1.5rem;
  font-weight: 700;
  color: #2c3e50;
}
</style>
