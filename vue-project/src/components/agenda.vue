<template>
  <div class="calendar-container">
    <FullCalendar :options="calendarOptions" />
  </div>
</template>

<script>
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import timeGridPlugin from '@fullcalendar/timegrid'
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
        events: [
          { title: 'event 1', date: '2021-09-01' },
          { title: 'event 2', date: '2021-09-02' }
        ],
        dateClick: this.handleDateClick
      }
    }
  },
  methods: {
    handleDateClick(info) {
      // Emitir la fecha seleccionada al componente padre
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
