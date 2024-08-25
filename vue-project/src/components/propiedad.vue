<template>
  <div v-if="propiedad" class="prop" @click="guardarIdYRedirigir">
    <img class="imgprop" :src="imagenPropiedad" alt="Imagen de la propiedad">
    <p>{{ propiedad.tipo_propiedad || 'Tipo desconocido' }}</p>
    <p class="precio">{{ propiedad.valor ? propiedad.valor + " (UF)" : 'Valor no disponible' }}</p>
    <p>{{ propiedad.comuna || 'Comuna no especificada' }}</p>
    <p>{{ propiedad.descripcion || 'Descripción no disponible' }}</p>
  </div>
  <div v-else>
    <p>No hay información de la propiedad disponible.</p>
  </div>
</template>

<script>
import { computed } from 'vue';
import { useRouter } from 'vue-router';
import pordefectoCasa from "../components/images/casa.jpeg";
import pordefectoDepartamento from "../components/images/departamento.jpeg";

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

    const imagenPropiedad = computed(() => {
      if (props.propiedad.tipo_propiedad) {
        if (props.propiedad.tipo_propiedad.toLowerCase() === 'casa') {
          return pordefectoCasa;
        } else if (props.propiedad.tipo_propiedad.toLowerCase() === 'departamento') {
          return pordefectoDepartamento;
        }
      }
      return pordefectoCasa; // Imagen por defecto si el tipo no es reconocido
    });

    const guardarIdYRedirigir = () => {
      if (props.propiedad && props.propiedad.id) {
        localStorage.setItem('selectedPropertyId', props.propiedad.id);
        router.push('/propiedadPerfil');
      } else {
        console.error('ID de propiedad no disponible');
      }
    };

    return {
      imagenPropiedad,
      guardarIdYRedirigir
    };
  }
}
</script>

<style scoped>
/* Estilos del componente */
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