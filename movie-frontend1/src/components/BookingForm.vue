<script setup>
import { ref, watch } from 'vue'

const props = defineProps({
  refresh: { type: Number, required: true }
})

const emit = defineEmits(['booked'])
const movies = ref([])
const movieid = ref('')
const customername = ref('')
const tickets = ref(1)

async function getMovies() {
  try {
    const response = await fetch('http://localhost:8080/api/movies')
    if (!response.ok) throw new Error('Request failed')
    movies.value = await response.json()
  } catch {
    movies.value = []
  }
}

async function bookTickets() {
  if (!movieid.value || !customername.value.trim() ||
      Number(tickets.value) < 1) {
    alert('Enter valid booking details')
    return
  }

  try {
    const response = await fetch('http://localhost:8080/api/bookings', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        movieid: movieid.value,
        customername: customername.value.trim(),
        tickets: Number(tickets.value)
      })
    })

    if (!response.ok) {
      alert('Booking failed. Check the backend.')
      return
    }

    alert('Tickets booked successfully')
    customername.value = ''
    tickets.value = 1
    emit('booked')
  } catch {
    alert('Could not connect to Spring Boot')
  }
}

watch(() => props.refresh, getMovies, { immediate: true })
</script>

<template>
  <h2>Book Tickets</h2>

  <form @submit.prevent="bookTickets" class="form-row">
    <select v-model="movieid" required>
      <option value="" disabled>Select movie</option>
      <option v-for="movie in movies" :key="movie.id" :value="movie.id">
        {{ movie.name }} - ₹{{ movie.ticketprice }}
      </option>
    </select>

    <input v-model="customername" placeholder="Customer name" required />

    <input
      v-model.number="tickets"
      type="number"
      min="1"
      placeholder="Number of tickets"
      required
    />

    <button type="submit">Book Tickets</button>
  </form>
</template>
