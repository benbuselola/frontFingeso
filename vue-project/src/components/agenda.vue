<template>
  <div class="calendar-container">
    <FullCalendar :options="calendarOptions" />
  </div>
</template>

<script>
    <div class="calendar-container">
      <form class="dataEvent" @submit.prevent="addEvent">
        <input class="titleEvent" v-model="newEvent.title" type="text" placeholder="Título del evento" required>
        <input class="dateEvent" v-model="newEvent.date" type="date" required />
        <button class="summitEvent"type="submitEvent">Agregar Evento</button>
      </form>
      <FullCalendar :options="calendarOptions"/>
    </div>
  </template>
  
  <script>
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import timeGridPlugin from '@fullcalendar/timegrid'
import { defineComponent } from 'vue'

export default defineComponent({
import esLocale from '@fullcalendar/core/locales/es'

export default {
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
        events: [
          { title: 'event 1', date: '2021-09-01' },
          { title: 'event 2', date: '2021-09-02' }
        ],
        dateClick: this.handleDateClick
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
    handleDateClick(info) {
      this.$emit('date-selected', info.dateStr)
    }
  }
})
</script>

<style>
.calendar-container {
  max-width: 80%; 
  margin: 0 auto; 
}

.fc {
  max-width: 800px; 
  margin: 0 auto;   
}


.fc-daygrid-day {
  font-size: 14px; 
}

.fc-event {
  font-size: 12px; 
}
</style>
    addEvent() {
      this.calendarOptions.events.push({ 
        title: this.newEvent.title, 
        date: this.newEvent.date 
      })
      this.newEvent.title = ''
      this.newEvent.date = ''
    },
    handleEventChange({ event }) {
      console.log('Event changed:', event)
    },
    handleEventClick({ event }) {
      console.log('Event clicked:', event)
    }
  }
}
</script>
  
  <style scoped>
  *{
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
  
  .fc-button {
    background-color: #3498db;
    color: white;
    border: none;
    padding: 10px 15px;
    border-radius: 5px;
    transition: background-color 0.3s;
  }
  
  .fc-button:hover {
    background-color: #2980b9;
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
    border-radius: 4px;
    padding: 5px;
    font-size: 14px;
    border: none;
    transition: background-color 0.3s;
  }
  
  .fc-event:hover {
    background-color: #16a085;
  }
  
  .fc-day-sat, .fc-day-sun {
    background-color: #ecf0f1;
  }
  .dataEvent {
    display: flex;
    justify-content: space-between;
    margin-bottom: 20px;
  }
  .titleEvent, .dataEvent{
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
  
