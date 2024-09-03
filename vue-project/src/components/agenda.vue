<template>
  <div class="calendar-container">
    <form class="dataEvent" @submit.prevent="addEvent">
      <input class="titleEvent" v-model="newEvent.title" type="text" placeholder="Título del evento" required />
      <input class="dateEvent" v-model="newEvent.date" type="date" required />
      <button class="summitEvent" type="submit">Agregar Evento</button>
    </form>
    <FullCalendar :options="calendarOptions" />
  </div>
</template>

<script>
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import timeGridPlugin from '@fullcalendar/timegrid'
import esLocale from '@fullcalendar/core/locales/es'
import { defineComponent } from 'vue'

export default defineComponent({
  components: {
    FullCalendar
  },
  data() {
    return {
      calendarOptions: {
        plugins: [dayGridPlugin, interactionPlugin, timeGridPlugin],
        initialView: 'dayGridMonth',
        headerToolbar: {
          left: 'prev,next today',
          center: 'title',
          right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        events: [],
        locale: esLocale,
        eventClick: this.handleEventClick,
        eventChange: this.handleEventChange
      },
      newEvent: { 
        title: '',
        date: ''
      }
    }
  },
  methods: {
    addEvent() {
      this.calendarOptions.events.push({
        title: this.newEvent.title,
        date: this.newEvent.date
      })
      this.newEvent.title = ''
      this.newEvent.date = ''
    },
    handleEventClick({ event }) {
      console.log('Event clicked:', event)
    },
    handleEventChange({ event }) {
      console.log('Event changed:', event)
    }
  }
})
</script>

<style scoped>
* {
  font-family: 'Poppins';
}

.calendar-container {
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
  background-color: #EAF9E7;
  border-radius: 10px;
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.1);
}

.fc-toolbar-title {
  color: #2c3e50;
  font-size: 22px;
  font-weight: bold;
}


.fc-day-today {
  background-color: #f39c12;
  color: white;
}

.fc-daygrid-day {
  border: 1px solid #ecf0f1;
  padding: 10px;
  transition: background-color 0.3s;
}

.fc-daygrid-day:hover {
  background-color: #f0f8ff;
}

.fc-event {
  background-color: #1abc9c; 
  color: white; 
  border: none;
  border-radius: 6px; 
  padding: 10px; 
  font-size: 16px; 
  font-weight: bold;
  transition: background-color 0.3s, transform 0.3s; 
}

.fc-event:hover {
  background-color: #16a085; 
  transform: scale(1.05); 
  cursor: pointer; 
}

.fc-day-sat, .fc-day-sun {
  background-color: #ecf0f1;
}

.dataEvent {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.titleEvent, .dateEvent {
  padding: 10px;
  border: none;
  border-radius: 5px;
  font-size: 16px;
}

.summitEvent {
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

.summitEvent:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 8px rgba(0, 0, 0, 0.1);
}
</style>
