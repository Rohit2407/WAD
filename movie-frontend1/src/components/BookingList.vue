<script setup>
import { ref, watch } from 'vue'

const props = defineProps({
  refresh: { type: Number, required: true }
})

const emit = defineEmits(['changed'])
const bookings = ref([])
const error = ref('')

async function getBookings() {
  try {
    const response = await fetch('http://localhost:8080/api/bookings')
    if (!response.ok) throw new Error('Request failed')
    bookings.value = await response.json()
    error.value = ''
  } catch {
    error.value = 'Could not load bookings. Check Spring Boot and CORS settings.'
  }
}

async function deleteBooking(id) {
  if (!confirm('Delete this booking?')) return

  try {
    const response = await fetch(
      'http://localhost:8080/api/bookings/' + encodeURIComponent(id),
      { method: 'DELETE' }
    )

    if (!response.ok) {
      alert('Failed to delete booking')
      return
    }

    alert('Booking deleted')
    emit('changed')
    await getBookings()
  } catch {
    alert('Could not connect to Spring Boot')
  }
}

watch(() => props.refresh, getBookings, { immediate: true })
</script>

<template>
  <h2>Bookings</h2>
  <p v-if="error" class="error">{{ error }}</p>

  <div class="table-wrap">
    <table>
      <thead>
        <tr>
          <th>Customer</th>
          <th>Movie ID</th>
          <th>Tickets</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="booking in bookings" :key="booking.id">
          <td>{{ booking.customername }}</td>
          <td class="id-cell">{{ booking.movieid }}</td>
          <td>{{ booking.tickets }}</td>
          <td>
            <button class="danger" @click="deleteBooking(booking.id)">Delete</button>
          </td>
        </tr>
        <tr v-if="bookings.length === 0 && !error">
          <td colspan="4">No bookings found.</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
