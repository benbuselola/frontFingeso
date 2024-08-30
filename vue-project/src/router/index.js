// router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import login from '../views/login.vue'
import PrincipalPage from '../views/principalPage.vue'
import registro from '../views/registro.vue'
import PublicarPropiedad from '../views/publicarPropiedad.vue'
import perfil from '../views/perfil.vue'
import propiedadPerfil from '../views/propiedadPerfil.vue'
import soporte from '../views/soporte.vue'
import actualizaCorreo from '../views/actualizarDatosPerfil.vue'
import actualizaContrasena from '../views/cambiarContrasena.vue'
import cambiarNumero from '../views/actualizarNumero.vue'
import ActualizarDatosPerfil from '../views/actualizarDatosPerfil.vue'

const routes = [
  {
    path: '/',
    redirect: '/principal',
  },
  {
    path: '/login',
    name: 'login',
    component: login,
  },
  {
    path: '/principal',
    name: 'principal',
    component: PrincipalPage,
  },
  {
    path: '/actualizaDatosPerfil',
    name: 'actualizaDatosPerfil',
    component: ActualizarDatosPerfil,
  },
  {
    path: '/actualizaContrasena',
    name: 'actualizaContrasena',
    component: actualizaContrasena,
  },
  {
    path: '/cambiarNumero',
    name: 'cambiarNumero',
    component: cambiarNumero,
  },
  {
    path: '/soporte',
    name: 'soporte',
    component: soporte,
  },

  {
    path: '/registro',
    name: 'registro',
    component: registro,
  },
    {
    path: '/perfil',
    name: 'perfil',
    component: perfil,
  },
  {
    path: '/publicarPropiedad',
    name: 'publicar',
    component: PublicarPropiedad,
  },
  {
    path: '/propiedadPerfil',
    name: 'propiedadPerfil',
    component: propiedadPerfil,
  },
  
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router