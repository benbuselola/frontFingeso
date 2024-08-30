<template>
  <div class="form-group">
    <select id="comunas" v-model="selectedComuna">
      <option value="" disabled>Seleccione una comuna</option>
      <slot>
        <option v-for="comuna in comunas" :key="comuna" :value="comuna">
          {{ comuna }}
        </option>
      </slot>
    </select>
  </div>
</template>

<script>
import { ref, watch } from 'vue';

export default {
  name: 'ComunasSelector',
  props: {
    modelValue: {
      type: String,
      default: ''
    },
    comunasList: {
      type: Array,
      default: () => [
        "Colina", "Lampa", "Til Til", "Pirque", "Puente Alto", "San José de Maipo", 
        "Buin", "Calera de Tango", "Paine", "San Bernardo", "Alhué", "Curacaví", 
        "María Pinto", "Melipilla", "San Pedro", "Cerrillos", "Cerro Navia", 
        "Conchalí", "El Bosque", "Estación Central", "Huechuraba", "Independencia", 
        "La Cisterna", "La Granja", "La Florida", "La Pintana", "La Reina", 
        "Las Condes", "Lo Barnechea", "Lo Espejo", "Lo Prado", "Macul", "Maipú", 
        "Ñuñoa", "Pedro Aguirre Cerda", "Peñalolén", "Providencia", "Pudahuel", 
        "Quilicura", "Quinta Normal", "Recoleta", "Renca", "San Miguel", "San Joaquín", 
        "San Ramón", "Santiago", "Vitacura", "El Monte", "Isla de Maipo", 
        "Padre Hurtado", "Peñaflor", "Talagante"
      ]
    }
  },
  emits: ['update:modelValue'],
  setup(props, { emit }) {
    const selectedComuna = ref(props.modelValue);

    watch(selectedComuna, (newValue) => {
      emit('update:modelValue', newValue);
    });

    watch(() => props.modelValue, (newValue) => {
      selectedComuna.value = newValue;
    });

    return {
      comunas: props.comunasList,
      selectedComuna
    };
  }
}
</script>

<style scoped>
.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
}

.form-group select {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 20px;
}
</style>
