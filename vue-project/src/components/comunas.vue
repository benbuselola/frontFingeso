<template>
    <div class="form-group">
      <select id="comunas" v-model="selectedComuna">
        <option v-for="comuna in comunas" :key="comuna" :value="comuna">
          {{ comuna }}
        </option>
      </select>
    </div>
  </template>
  
  <script>
  import { ref, computed } from 'vue';
  
  export default {
    name: 'ComunaSelector',
    props: {
      modelValue: {
        type: String,
        default: ''
      }
    },
    emits: ['update:modelValue'],
    setup(props, { emit }) {
      const comunas = [
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
      ];
  
      const selectedComuna = computed({
        get: () => props.modelValue,
        set: (value) => emit('update:modelValue', value)
      });
  
      return {
        comunas,
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