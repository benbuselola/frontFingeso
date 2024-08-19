<template>
  <div class="prop">
    <a :href="'/propiedadPerfil/' + propiedad.id" target="_blank">
      <img class="imgprop" :src="imagenPropiedad" alt="Imagen de la propiedad">
    </a>
    <p>{{ propiedad.tipo_propiedad }}</p>
    <p class="precio">{{ propiedad.valor + "(UF)" }}</p>
    <p>{{ propiedad.comuna }}</p>
    <p>{{ propiedad.descripcion }}</p>
  </div>
</template>

<script>
import { computed } from 'vue';
import pordefectoCasa from "../components/images/casa.jpeg";
import pordefectoDepartamento from "../components/images/departamento.jpeg";

export default {
  name: 'Propiedad',
  props: {
    propiedad: {
      type: Object,
      required: true
    }
  },
  setup(props) {
    const imagenPropiedad = computed(() => {
      if (props.propiedad.tipo_propiedad.toLowerCase() === 'casa') {
        return pordefectoCasa;
      } else if (props.propiedad.tipo_propiedad.toLowerCase() === 'departamento') {
        return pordefectoDepartamento;
      } else {
        // Imagen por defecto si el tipo no es reconocido
        return pordefectoCasa;
      }
    });

    return {
      imagenPropiedad
    };
  }
}
</script>

<style scoped>
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