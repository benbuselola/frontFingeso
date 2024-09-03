<template>
    <div>
      <FullCalendar :options="calendarOptions"/>
    </div>
  </template>
  
  <script>
  import FullCalendar from '@fullcalendar/vue3'
  import dayGridPlugin from '@fullcalendar/daygrid'
  import interactionPlugin from '@fullcalendar/interaction'
  import timeGridPlugin from '@fullcalendar/timegrid'
  
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
          eventClick: this.handleEventClick,
          eventChange: this.handleEventChange
        }
      }
    },
    methods: {
      handleEventChange({ event }) {
        console.log('Event changed:', event)
      },
      handleEventClick({ event }) {
        console.log('Event clicked:', event)
      }
    }
  }
  </script>
  
  <style>
    
  </style>
  