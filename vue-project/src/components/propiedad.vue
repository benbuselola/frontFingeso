<template>
    <div class="prop">
      <a :href="'/propiedadPerfil/' + propiedad.id" target="_blank">
        <img class="imgprop" :src="propiedad.imagenUrl" alt="Imagen de la propiedad">
      </a>
      <p>{{ propiedad.tipo }}</p>
      <p>${{ propiedad.value }}</p>
      <p>{{ propiedad.description }}</p>
    </div>
  </template>
  
  <script>
  import { useRouter } from 'vue-router';
  
  export default {
    name: 'Propiedad',
    props: {
      propiedad: Object
    },
    setup(props) {
      const router = useRouter();
  
      return {};
    }
  }
  </script>
  
  <style scoped>
  .prop {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 100%;
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
    font-size: 1.2vw;
    text-align: center;
    margin: 8px 0;
    color: #34495e;
    font-weight: 500;
  }
  
  p:first-of-type {
    font-size: 1.5vw;
    font-weight: 700;
    color: #2c3e50;
  }
  
  p:last-of-type {
    font-size: 1vw;
    font-weight: 400;
    color: #7f8c8d;
  }
  </style>
  